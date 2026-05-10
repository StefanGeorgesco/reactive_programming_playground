package fr.stefangeorgesco.reactive_playground.sec02.assignment;

import org.slf4j.Logger;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@SuppressWarnings("BlockingMethodInNonBlockingContext")
public class FileServiceImpl implements FileService {
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(FileServiceImpl.class);
    private static final Path PATH = Path.of("src/main/resources/sec02");

    @Override
    public Mono<String> read(String fileName) {
        return Mono.fromCallable(() -> Files.readString(PATH.resolve(fileName)));
    }

    @Override
    public Mono<Void> write(String fileName, String content) {
        return Mono.fromRunnable(() -> {
            try {
                Files.writeString(PATH.resolve(fileName), content);
                logger.info("file '{}' written", fileName);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public Mono<Void> delete(String fileName) {
        return Mono.fromRunnable(()-> {
            try {
                Files.delete(PATH.resolve(fileName));
                logger.info("file '{}' deleted", fileName);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
