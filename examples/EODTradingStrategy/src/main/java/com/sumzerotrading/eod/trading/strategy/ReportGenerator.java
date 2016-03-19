/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sumzerotrading.eod.trading.strategy;

import com.sumzerotrading.broker.order.OrderEvent;
import com.sumzerotrading.broker.order.OrderEventListener;
import com.sumzerotrading.broker.order.OrderStatus.Status;
import com.sumzerotrading.broker.order.TradeOrder;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author RobTerpilowski
 */
public class ReportGenerator implements OrderEventListener {

    protected Map<String, RoundTrip> roundTripMap = new HashMap<>();
    protected String outputFile;

    //For unit tests
    protected ReportGenerator() {
    }

    public ReportGenerator(String outputDir) {
        StringBuilder sb = new StringBuilder();
        sb.append(outputDir);
        if( ! outputDir.endsWith("/") ) {
            sb.append("/");
        }
        
        try {
            Files.createDirectories(Paths.get(sb.toString()));
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        }
        
        sb.append("report.csv");
        outputFile = sb.toString();
    }

    @Override
    public void orderEvent(OrderEvent event) {
        TradeOrder order = event.getOrder();
        if (order.getCurrentStatus() == Status.FILLED) {
            TradeReferenceLine line = getTradeReferenceLine(order.getReference());
            RoundTrip roundTrip = roundTripMap.get(line.getCorrelationId());
            if (roundTrip == null) {
                roundTrip = new RoundTrip();
                roundTripMap.put(line.getCorrelationId(), roundTrip);
            }
            roundTrip.addTradeReference(order, line);
            if (roundTrip.isComplete()) {
                writeRoundTripToFile(roundTrip);
                roundTripMap.remove(line.getCorrelationId());
            } else {
                throw new IllegalStateException("Write this transaction to a temp file");
            }
        }
    }

    protected synchronized void writeRoundTripToFile(RoundTrip roundTrip) {
        String resultString = roundTrip.getResults() + "\n";
        try {
            Files.write(Paths.get(outputFile), resultString.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

    }

    protected TradeReferenceLine getTradeReferenceLine(String string) {
        TradeReferenceLine line = new TradeReferenceLine();
        line.parse(string);
        return line;
    }

}
