package fr.stefangeorgesco.reactive_playground.sec09;

import fr.stefangeorgesco.reactive_playground.common.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;

/*
    Zip
    - we will subscribe to all the producers at the same time
    - all or nothing
    - all producers will have to emit an item
 */
public class Lec07Zip {

    public static void main(String[] args) {

        Flux.zip(getCarBodies(), getEngines(), getWheelSets())
                .map(parts ->
                        new Car(parts.getT1(), parts.getT2(), parts.getT3()))
                .subscribe(Util.subscriber("car subscriber"));

        Util.sleep(Duration.ofSeconds(1));
    }

    private static Flux<CarBody> getCarBodies() {
        return Flux.range(1, 5)
                .map(CarBody::new)
                .delayElements(Duration.ofMillis(100));
    }

    private static Flux<Engine> getEngines() {
        return Flux.range(1, 3)
                .map(Engine::new)
                .delayElements(Duration.ofMillis(200));
    }

    private static Flux<WheelSet> getWheelSets() {
        return Flux.range(1, 10)
                .map(WheelSet::new)
                .delayElements(Duration.ofMillis(75));
    }

    private record CarBody(int number) {
        public String toString() {
            return "car-body-" + number;
        }
    }

    private record Engine(int number) {
        public String toString() {
            return "engine-" + number;
        }
    }

    private record WheelSet(int number) {
        public String toString() {
            return "wheel-set-" + number;
        }
    }

    private record Car(CarBody carBody, Engine engine, WheelSet wheelSet) {
        public String toString() {
            return "car[" + carBody + "," + engine + "," + wheelSet + "]";
        }
    }
}
