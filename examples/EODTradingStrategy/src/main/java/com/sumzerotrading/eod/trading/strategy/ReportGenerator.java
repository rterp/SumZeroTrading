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

    protected Map<String, RoundTrip> map = new HashMap<>();

    @Override  
    public void orderEvent(OrderEvent event) {
        TradeOrder order = event.getOrder();
        if( order.getCurrentStatus() == Status.FILLED ) {
            TradeReferenceLine line = new TradeReferenceLine();
            line.parse(order.getReference());
        }
    }
    
    
    
}
