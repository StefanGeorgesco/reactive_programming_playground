package fr.stefangeorgesco.reactive_playground.sec09;

import fr.stefangeorgesco.reactive_playground.common.Util;
import fr.stefangeorgesco.reactive_playground.sec09.helper.NameGenerator;

public class Lec02StartWithUseCase {

    public static void main(String[] args) {

        var nameGenerator = new NameGenerator();

        nameGenerator.generateNames()
                .take(2)
                .subscribe(Util.subscriber("Sam"));

        nameGenerator.generateNames()
                .take(2)
                .subscribe(Util.subscriber("Mike"));

        nameGenerator.generateNames()
                .take(3)
                .subscribe(Util.subscriber("Jake"));
    }
}
