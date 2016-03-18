/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sumzerotrading.eod.trading.strategy;

import com.sumzerotrading.broker.order.TradeOrder;
import java.time.format.DateTimeFormatter;

/** 
 *    
 * @author RobTerpilowski
 */
public class RoundTrip {

    
    protected TradeOrder longEntry;
    protected TradeOrder longExit;
    protected TradeOrder shortEntry;
    protected TradeOrder shortExit;
    
    
    public void addTradeReference( TradeOrder order, TradeReferenceLine tradeReference ) {
        if( tradeReference.getDirection() == TradeReferenceLine.Direction.LONG ) {
            if( tradeReference.getSide() == TradeReferenceLine.Side.ENTRY ) {
                longEntry = order;
            } else {
                longExit = order;
            }
        } else {
            if( tradeReference.getSide() == TradeReferenceLine.Side.ENTRY ) {
                shortEntry = order;
            } else {
                shortExit = order;
            }
        }
    }
    
    
    
    public String getResults() {
        // Long-entry-date, longTicker, LongShares, Long Entry price, Long Entry Commission, 
        //LongExitDate, Long Exit Price, shortTicker, shortShares, shortEntryPrice, shortEntryCommissions,
        //shortExitPrice, shortExitCommissions
        //2016-03-20T12:40:00PST,QQQ,200,100.23,1.45,2016-03-21T12:40:00PST,
        StringBuilder sb = new StringBuilder();
        sb.append(longEntry.getOrderEntryTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)).append(",")
                .append(longEntry.getTicker().getSymbol()).append(",")
                .append(longEntry.getSize()).append(",")
                .append(longEntry.getFilledPrice()).append(",")
                //long entry commissions
                .append(0).append(",")
                .append(longExit.getOrderEntryTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)).append(",")
                .append(longExit.getFilledPrice()).append(",")
                //long exit commissions
                .append(0).append(",")
                .append(shortEntry.getTicker().getSymbol()).append(",")
                .append(shortEntry.getSize()).append(",")
                .append(shortEntry.getFilledPrice()).append(",")
                //short entry commissions
                .append(0).append(",")
                .append(shortExit.getFilledPrice()).append(",")
                //short exit commissions.
                .append(0);
        
        return sb.toString();
    }
    
    
    
    
    public boolean isComplete() {
        return longEntry != null &&
                longExit != null &&
                shortEntry != null &&
                shortExit != null;
    }
    
    
    
}
