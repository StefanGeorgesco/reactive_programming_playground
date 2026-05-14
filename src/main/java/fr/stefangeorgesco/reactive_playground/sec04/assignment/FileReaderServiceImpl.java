package fr.stefangeorgesco.reactive_playground.sec04.assignment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.SynchronousSink;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class FileReaderServiceImpl implements FileReaderService {

    private static final Logger logger = LoggerFactory.getLogger(FileReaderServiceImpl.class);

    @Override
    public Flux<String> read(Path path) {
        return Flux.generate(
                () -> getFileReader(path),
                this::readLine,
                this::closeFileReader
        );
    }

    /**
     * Opens a new buffered reader for the given path.
     *
     * @param path the path to the file to read
     * @return a new buffered reader
     * @throws IOException if an I/O error occurs
     */
    private BufferedReader getFileReader(Path path) throws IOException {
        logger.info("opening file: {}", path);
        return Files.newBufferedReader(path);
    }

    /**
     * Reads a line from the buffered reader and sends it to the sink.
     * If the end of the file is reached, the sink is completed.
     *
     * @param reader the buffered reader
     * @param sink   the sink to send the line to
     * @return the same buffered reader
     */
    private BufferedReader readLine(BufferedReader reader, SynchronousSink<String> sink) {
        try {
            String line = reader.readLine();
            if (Objects.nonNull(line)) {
                logger.info("emitting line: {}", line);
                sink.next(line);
            } else {
                logger.info("end of file reached");
                sink.complete();
            }
        } catch (IOException e) {
            logger.error("error reading file", e);
            sink.error(e);
        }
        return reader;
    }

    /**
     * Closes the buffered reader.
     *
     * @param reader the buffered reader to close
     */
    private void closeFileReader(BufferedReader reader) {
        try {
            reader.close();
            logger.info("file closed");
        } catch (IOException e) {
            logger.error("error closing file", e);
        }
    }
}
