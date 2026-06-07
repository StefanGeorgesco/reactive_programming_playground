package fr.stefangeorgesco.reactive_playground.sec10;

import fr.stefangeorgesco.reactive_playground.common.Util;
import fr.stefangeorgesco.reactive_playground.sec10.assignment.groupby.OrderProcessingService;
import fr.stefangeorgesco.reactive_playground.sec10.assignment.groupby.PurchaseOrder;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class Lec06GroupedFluxAssignment {

    public static void main(String[] args) {
        orderStream()
                .filter(OrderProcessingService.canProcess())
                .groupBy(PurchaseOrder::category)
                .flatMap(groupedFlux ->
                        groupedFlux.transform(OrderProcessingService.getProcessor(groupedFlux.key())))
                .subscribe(Util.subscriber("Purchase order subscriber"));

        Util.sleep(Duration.ofSeconds(30));
    }

    private static Flux<PurchaseOrder> orderStream() {
        return Flux.interval(Duration.ofMillis(100))
                .map(i -> PurchaseOrder.create());
    }
}
