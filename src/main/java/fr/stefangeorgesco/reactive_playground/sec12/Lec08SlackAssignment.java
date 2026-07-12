package fr.stefangeorgesco.reactive_playground.sec12;


import fr.stefangeorgesco.reactive_playground.common.Util;
import fr.stefangeorgesco.reactive_playground.sec12.assignment.SlackMember;
import fr.stefangeorgesco.reactive_playground.sec12.assignment.SlackRoom;

import java.time.Duration;

public class Lec08SlackAssignment {

    public static void main(String[] args) {

        var room = new SlackRoom("reactor");
        var sam = new SlackMember("sam");
        var jake = new SlackMember("jake");
        var mike = new SlackMember("mike");

        // add 2 members
        room.addMember(sam);
        room.addMember(jake);

        sam.says("Hi all..");

        Util.sleep(Duration.ofSeconds(4));

        jake.says("Hey!");
        sam.says("I simply wanted to say hi..");

        Util.sleep(Duration.ofSeconds(4));

        // add new member
        room.addMember(mike);
        mike.says("Hey guys..glad to be here...");
    }
}
