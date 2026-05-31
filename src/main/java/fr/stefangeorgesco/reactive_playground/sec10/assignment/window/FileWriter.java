package fr.stefangeorgesco.reactive_playground.sec10.assignment.window;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileWriter {
    private static final Logger log = LoggerFactory.getLogger(FileWriter.class);

    private final Path path;
    private BufferedWriter writer;

    private FileWriter(Path path) {
        this.path = path;
    }

    private void createFile() {
        try {
            this.writer = Files.newBufferedWriter(this.path);
            log.info("created file: {}", this.path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void writeLine(String line) {
        try {
            this.writer.write(line);
            this.writer.newLine();
            this.writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void closeFile() {
        try {
            this.writer.close();
            log.info("closed file: {}", this.path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Mono<Void> create(Flux<String> flux, Path path) {
        var writer = new FileWriter(path);
        return flux
                .doFirst(writer::createFile)
                .doOnNext(writer::writeLine)
                .doFinally(s -> writer.closeFile())
                .then();
    }
}
