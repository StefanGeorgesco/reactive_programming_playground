package fr.stefangeorgesco.reactive_playground.sec03;

import fr.stefangeorgesco.reactive_playground.common.Util;
import fr.stefangeorgesco.reactive_playground.sec03.assignment.StockInvestor;
import fr.stefangeorgesco.reactive_playground.sec03.client.ExternalServiceClient;
import org.slf4j.Logger;

public class Lec12Assignment {

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(Lec12Assignment.class);

    public static void main(String[] args) throws InterruptedException {
        var client = new ExternalServiceClient();
        var stockInvestor = new StockInvestor();
        logger.info("Subscribing to stock price stream...");
        client.getStockPrice().subscribe(stockInvestor);
        Util.sleepSeconds(20);
        logger.info("Done");
    }
}
