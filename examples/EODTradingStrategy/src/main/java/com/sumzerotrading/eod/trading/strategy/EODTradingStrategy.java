/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sumzerotrading.eod.trading.strategy;

import com.sumzerotrading.broker.order.OrderEvent;
import com.sumzerotrading.broker.order.OrderEventFilter;
import com.sumzerotrading.broker.order.OrderEventListener;
import com.sumzerotrading.broker.order.TradeDirection;
import com.sumzerotrading.broker.order.TradeOrder;
import com.sumzerotrading.data.StockTicker;
import com.sumzerotrading.interactive.brokers.client.InteractiveBrokersClient;
import com.sumzerotrading.marketdata.ILevel1Quote;
import com.sumzerotrading.marketdata.Level1QuoteListener;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author RobTerpilowski
 */
public class EODTradingStrategy implements Level1QuoteListener, OrderEventListener  {
    
    protected InteractiveBrokersClient ibClient;
    protected String ibHost = "localhost";
    protected int ibPort = 10000;
    protected int ibClientId = 3;
    
    protected double longOrderAmount = 1000;
    protected double shortOrderAmount = 1000;
    protected StockTicker longTicker;
    protected StockTicker shortTicker;
    protected Date lastReportedTime;
    
    
    
    
    public void start() {
        ibClient = new InteractiveBrokersClient(ibHost, ibPort, ibClientId);
        ibClient.connect();
        
        longTicker = new StockTicker("QQQ");
        shortTicker = new StockTicker("SPY");
        
        ibClient.subscribeLevel1(longTicker, this);
        ibClient.subscribeLevel1(shortTicker, this);
        
    }
    
    public void placeMOCOrders() {
        
        TradeOrder longOrder = new TradeOrder(ibClient.getNextOrderId(), longTicker, 123, TradeDirection.BUY);
        longOrder.setType(TradeOrder.Type.MARKET_ON_CLOSE);
        longOrder.setReferenceString("EOD-Pair-Strategy:1:Entry:LongSide*");
        
        TradeOrder longExitOrder = new TradeOrder(ibClient.getNextOrderId(), longTicker, 123, TradeDirection.SELL);
        longExitOrder.setType(TradeOrder.Type.MARKET_ON_OPEN);
        longExitOrder.setGoodAfterTime(getNextBusinessDay(lastReportedTime));
        longExitOrder.setReferenceString("EOD-Pair-Strategy:1:Exit:LongSide*");
        
        longOrder.addChildOrder(longExitOrder);
        
        TradeOrder shortOrder = new TradeOrder(ibClient.getNextOrderId(), shortTicker, 234, TradeDirection.SELL_SHORT);
        shortOrder.setType(TradeOrder.Type.MARKET_ON_CLOSE);
        shortOrder.setReferenceString("EOD-Pair-Strategy:1:Entry:ShortSide*");
        
        TradeOrder shortExitOrder = new TradeOrder(ibClient.getNextOrderId(), shortTicker, 234, TradeDirection.BUY_TO_COVER);
        shortExitOrder.setType(TradeOrder.Type.MARKET_ON_OPEN);
        shortExitOrder.setGoodAfterTime(lastReportedTime);
        shortExitOrder.setReferenceString("EOD-Pair-Strategy:1:Exit:ShortSide*");
        
        shortOrder.addChildOrder(shortExitOrder);
    
    }

    

    @Override
    public OrderEventFilter getOrderEventFilter() {
        return new OrderEventFilter(false);
    }

    @Override
    public void orderEvent(OrderEvent event) {
        System.out.println("OrderEvent");
    }



    @Override
    public void quoteRecieved(ILevel1Quote quote) {
    //    if( quote.getTimeStamp() )
    }
    
    
    public Date getNextBusinessDay(Date date) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(Calendar.DATE, 1);
        
        while( cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ||
               cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY ) {
            cal.add(Calendar.DATE, 1);
        }
        
        cal.set(Calendar.HOUR_OF_DAY, 5);
        cal.set(Calendar.MINUTE, 30);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        
        return cal.getTime();
    }
    
    
    
}
