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
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author RobTerpilowski
 */
public class ReportGenerator implements OrderEventListener {

    protected Map<String, RoundTrip> roundTripMap = new HashMap<>();
    protected String outputFile;
    
    
    public ReportGenerator(String outputFile) {
        this.outputFile = outputFile;
    }

    @Override  
    public void orderEvent(OrderEvent event) {
        TradeOrder order = event.getOrder();
        if( order.getCurrentStatus() == Status.FILLED ) {
            TradeReferenceLine line = new TradeReferenceLine();
            line.parse(order.getReference());
            RoundTrip roundTrip = roundTripMap.get(line.getCorrelationId());
            if( roundTrip == null ) {
                roundTrip = new RoundTrip();
                roundTripMap.put(line.getCorrelationId(), roundTrip);
            }
            roundTrip.addTradeReference(order, line);
            if( roundTrip.isComplete() ) {
                writeRoundTripToFile(roundTrip);
            }
        }
    }
    
    
    protected synchronized void writeRoundTripToFile(RoundTrip roundTrip) {
        
        
    }
    
    
    
}
