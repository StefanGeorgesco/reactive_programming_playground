package fr.stefangeorgesco.reactive_playground.sec02;

import fr.stefangeorgesco.reactive_playground.common.Util;
import fr.stefangeorgesco.reactive_playground.sec02.assignment.FileServiceImpl;

public class Lec12Assignment {
    private static final String FILE_NAME = "file.txt";

    public static void main(String[] args) {
        var fileService = new FileServiceImpl();

        fileService.write(FILE_NAME,
                        "This file has been written by the reactive application.")
                .subscribe(Util.subscriber("Write file client"));

        fileService.read(FILE_NAME)
                .subscribe(Util.subscriber("Read file client"));

        fileService.delete(FILE_NAME)
                .subscribe(Util.subscriber("Delete file client"));
    }
}
