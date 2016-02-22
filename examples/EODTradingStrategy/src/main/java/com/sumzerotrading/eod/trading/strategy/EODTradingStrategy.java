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
import com.sumzerotrading.data.Ticker;
import com.sumzerotrading.interactive.brokers.client.InteractiveBrokersClient;
import com.sumzerotrading.interactive.brokers.client.InteractiveBrokersClientInterface;
import com.sumzerotrading.marketdata.ILevel1Quote;
import com.sumzerotrading.marketdata.Level1QuoteListener;
import com.sumzerotrading.marketdata.QuoteType;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author RobTerpilowski
 */
public class EODTradingStrategy implements Level1QuoteListener, OrderEventListener {

    protected static Logger logger = LoggerFactory.getLogger(EODTradingStrategy.class);
    
    protected InteractiveBrokersClientInterface ibClient;
    protected Map<Ticker, Ticker> longShortPairMap = new HashMap<>();
    protected Map<Ticker, Double> lastPriceMap = new HashMap<>();
    protected String ibHost = "localhost";
    protected int ibPort = 10000;
    protected int ibClientId = 3;

    protected double orderSizeInDollars = 1000;
    protected Date lastReportedTime;
    protected boolean ordersPlaced = false;
    protected boolean allPricesInitialized = false;
    protected LocalTime timeToPlaceOrders;

    public void start() {
        ibClient = InteractiveBrokersClient.getInstance(ibHost, ibPort, ibClientId);
        logger.info( "Connecting to IB client at:" + ibHost + ":" + ibPort + " with clientID: " + ibClientId);
        ibClient.connect();
        
        longShortPairMap.put(new StockTicker("QQQ"), new StockTicker("SPY"));
        longShortPairMap.put(new StockTicker("SPY"), new StockTicker("IWM"));
        longShortPairMap.put(new StockTicker("DIA"), new StockTicker("IWM"));
        timeToPlaceOrders = LocalTime.of(12, 40, 0);
        logger.info( "Strategy will place orders after: " + timeToPlaceOrders.toString());
        

        longShortPairMap.keySet().stream().map((ticker) -> {
            ibClient.subscribeLevel1(ticker, this);
            return ticker;
        }).forEach((ticker) -> {
            ibClient.subscribeLevel1(longShortPairMap.get(ticker), this);
        });

    }

    public synchronized void placeMOCOrders(Ticker longTicker, Ticker shortTicker) { 
        if (ordersPlaced) {
            return;
        } else {
            ordersPlaced = true;
        }
        
        UUID correlationId = UUID.randomUUID();
        int longSize = getOrderSize(longTicker);
        int shortSize = getOrderSize(shortTicker);
        
        logger.info("Placing long orders for: " + longSize + " shares of: " + longTicker );
        logger.info("Placing short orders for: " + shortSize + " shares of: " + shortTicker );

        TradeOrder longOrder = new TradeOrder(ibClient.getNextOrderId(), longTicker, longSize, TradeDirection.BUY);
        longOrder.setType(TradeOrder.Type.MARKET_ON_CLOSE);
        longOrder.setReferenceString("EOD-Pair-Strategy:" + correlationId + ":Entry:LongSide*");
        

        TradeOrder longExitOrder = new TradeOrder(ibClient.getNextOrderId(), longTicker, longSize, TradeDirection.SELL);
        longExitOrder.setType(TradeOrder.Type.MARKET_ON_OPEN);
        longExitOrder.setGoodAfterTime(getNextBusinessDay(lastReportedTime));
        longExitOrder.setReferenceString("EOD-Pair-Strategy:" + correlationId + ":Exit:LongSide*");
        
        logger.info( "Placing long orders: " + longOrder);
        longOrder.addChildOrder(longExitOrder);

        TradeOrder shortOrder = new TradeOrder(ibClient.getNextOrderId(), shortTicker, shortSize, TradeDirection.SELL_SHORT);
        shortOrder.setType(TradeOrder.Type.MARKET_ON_CLOSE);
        shortOrder.setReferenceString("EOD-Pair-Strategy:" + correlationId + ":Entry:ShortSide*");

        TradeOrder shortExitOrder = new TradeOrder(ibClient.getNextOrderId(), shortTicker, shortSize, TradeDirection.BUY_TO_COVER);
        shortExitOrder.setType(TradeOrder.Type.MARKET_ON_OPEN);
        shortExitOrder.setGoodAfterTime(lastReportedTime);
        shortExitOrder.setReferenceString("EOD-Pair-Strategy:" + correlationId + ":Exit:ShortSide*");

        logger.info( "Placing short orders: " + shortOrder);
        shortOrder.addChildOrder(shortExitOrder);

    }

    @Override
    public OrderEventFilter getOrderEventFilter() {
        return new OrderEventFilter(false);
    }

    @Override
    public void orderEvent(OrderEvent event) {
        logger.info("Received order event: " + event);
    }

    @Override
    public void quoteRecieved(ILevel1Quote quote) {
        if (quote.getType() == QuoteType.LAST) {
            lastPriceMap.put(quote.getTicker(), quote.getValue().doubleValue());
            if (!allPricesInitialized) {
                setAllPricesInitialized();
            }
        }

        quote.getTimeStamp().toInstant();
        LocalTime currentTime = LocalTime.from(quote.getTimeStamp().toInstant());

        if (currentTime.isAfter(timeToPlaceOrders) && !ordersPlaced) {
            if (allPricesInitialized) {
                longShortPairMap.keySet().stream().forEach((longTicker) -> {
                    placeMOCOrders(longTicker, longShortPairMap.get(longTicker));
                });
            }
        }
    }

    public Date getNextBusinessDay(Date date) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(Calendar.DATE, 1);

        while (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
                || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            cal.add(Calendar.DATE, 1);
        }

        cal.set(Calendar.HOUR_OF_DAY, 5);
        cal.set(Calendar.MINUTE, 30);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return cal.getTime();
    }

    protected int getOrderSize(Ticker ticker) {
        double lastPrice = lastPriceMap.get(ticker);
        return (int) Math.round(orderSizeInDollars / lastPrice);
    }

    protected boolean setAllPricesInitialized() {
        if (allPricesInitialized) {
            return true;
        }

        for (Ticker ticker : longShortPairMap.keySet()) {
            if (lastPriceMap.get(ticker) == null) {
                return false;
            }
            if (lastPriceMap.get(longShortPairMap.get(ticker)) == null) {
                return false;
            }
        }
        return true;
    }

}
