package fr.stefangeorgesco.reactive_playground.sec04;

import fr.stefangeorgesco.reactive_playground.common.Util;
import fr.stefangeorgesco.reactive_playground.sec04.assignment.FileReaderServiceImpl;

import java.nio.file.Path;

public class Lec09Assignment {

    public static void main(String[] args) {
        var fileReaderService = new FileReaderServiceImpl();
        var path = Path.of("src/main/resources/sec04/file.txt");

        fileReaderService.read(path)
                .subscribe(Util.subscriber("File reader subscriber"));
    }
}
