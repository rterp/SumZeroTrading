/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sumzerotrading.intraday.trading.strategy;

import com.sumzerotrading.broker.BrokerError;
import com.sumzerotrading.broker.BrokerErrorListener;
import com.sumzerotrading.broker.order.OrderEvent;
import com.sumzerotrading.broker.order.OrderEventListener;
import com.sumzerotrading.broker.order.TradeDirection;
import com.sumzerotrading.broker.order.TradeOrder;
import com.sumzerotrading.data.BarData;
import com.sumzerotrading.data.Ticker;
import com.sumzerotrading.historicaldata.IHistoricalDataProvider;
import com.sumzerotrading.interactive.brokers.client.InteractiveBrokersClient;
import com.sumzerotrading.interactive.brokers.client.InteractiveBrokersClientInterface;
import com.sumzerotrading.marketdata.ILevel1Quote;
import com.sumzerotrading.marketdata.Level1QuoteListener;
import com.sumzerotrading.marketdata.QuoteType;
import com.sumzerotrading.realtime.bar.RealtimeBarListener;
import com.sumzerotrading.realtime.bar.RealtimeBarRequest;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author RobTerpilowski
 */
public class IntradayTradingStrategy implements OrderEventListener, BrokerErrorListener, RealtimeBarListener {

    protected static Logger logger = LoggerFactory.getLogger(IntradayTradingStrategy.class);
    enum Bias { LONG, SHORT, NONE };
    

    protected InteractiveBrokersClientInterface ibClient;
    protected Ticker mainTicker;
    protected List<Ticker> tickersToTrade = new ArrayList<>();
    protected String ibHost;
    protected int ibPort;
    protected int ibClientId;
    protected String strategyDirectory;

    protected int orderSizeInDollars;
    protected Bias bias;
    protected double yesterdayClose;
    protected BarData firstBar;
    protected LocalTime systemStartTime;
    protected boolean ordersPlaced = false;
    protected boolean allPricesInitialized = false;
    protected LocalTime shortStartTime;
    protected LocalTime shortStopTime;
    protected LocalTime shortCloseTime;
    protected LocalTime longStartTime;
    protected LocalTime longStopTime;
    protected LocalTime longCloseTime;

    protected IntradaySystemProperties systemProperties;
    protected IReportGenerator reportGenerator;
    
    

    public void start(String propFile) {
        initProps(propFile);
        ibClient = InteractiveBrokersClient.getInstance(ibHost, ibPort, ibClientId);
        logger.info("Connecting to IB client at:" + ibHost + ":" + ibPort + " with clientID: " + ibClientId);

        reportGenerator = getReportGenerator(strategyDirectory);
        ibClient.addOrderStatusListener(this);
        ibClient.addOrderStatusListener(reportGenerator);
        ibClient.connect();

        List<TradeOrder> openOrders = ibClient.getOpenOrders();
        logger.debug("Found " + openOrders.size() + " open orders");

        ordersPlaced = checkOpenOrders(openOrders, tickersToTrade);
        logger.info("Checking if orders have already been placed today: " + ordersPlaced);

        logger.info( "Requesting historical data for: " + mainTicker );
        
        List<BarData> data = ibClient.requestHistoricalData(mainTicker, 5, BarData.LengthUnit.DAY, 1, BarData.LengthUnit.DAY, IHistoricalDataProvider.ShowProperty.TRADES);
        for( BarData bar : data ) {
            logger.info( "Historical Data: " + bar );
        }
        
        yesterdayClose = data.get(data.size()-1).getClose();
        if( yesterdayClose > data.get(data.size()-2).getClose() ) {
            bias = Bias.LONG;
        } else if (yesterdayClose < data.get(data.size()-2).getClose() ) {
            bias = Bias.SHORT;
        } else {
            bias = Bias.NONE;
        }
        
        logger.info( "Bias for today is: " + bias );
        
        
        RealtimeBarRequest request = new RealtimeBarRequest(1, mainTicker, 1, BarData.LengthUnit.MINUTE);
        ibClient.subscribeRealtimeBar(request, this);
    }

    @Override
    public void realtimeBarReceived(int requestId, Ticker ticker, BarData bar) {
        logger.info( "Realtime bar received for ticker: " + ticker + " data: " + bar );
        LocalTime barTime = bar.getDateTime().toLocalTime();
        if( barTime == systemStartTime ) {
            firstBar = bar;
            logger.info( "System Start time, first bar is: " + firstBar );
        }
        
        if( firstBar == null ) {
            logger.info( "First bar has not yet been set, returning");
            return;
        }
        
        if( bias == Bias.LONG ) {            
            if( bar.getDateTime().toLocalTime() == systemStartTime ) {
                if( bar.getClose() < yesterdayClose * 1.01 ) {
                    placeOrder( ticker, TradeDirection.BUY, (int)Math.round(orderSizeInDollars/bar.getClose()), longCloseTime);
                }
            }
        } else if( bias == Bias.SHORT ) {
            if( ! (barTime.isBefore(shortStartTime) || barTime.isAfter(shortStopTime)) ) {
                if( firstBar.getClose() < yesterdayClose ) {
                    if( bar.getClose() < firstBar.getClose() * 1.01 ) {
                        placeOrder( ticker, TradeDirection.SELL, (int)Math.round(orderSizeInDollars/bar.getClose()), shortCloseTime);
                    }
                }
            }
        }
    }
    
    
    
    
    public synchronized void placeOrder( Ticker ticker, TradeDirection direction, int size, LocalTime closeTime ) {
        String correlationId = getUUID();
        TradeDirection exitDirection;
        if( direction == TradeDirection.BUY ) {
            exitDirection = TradeDirection.SELL;
        } else {
            exitDirection = TradeDirection.BUY;
        }
        
        ZonedDateTime zdt = ZonedDateTime.of(LocalDate.now(ZoneId.systemDefault()), closeTime, ZoneId.systemDefault());
        
        TradeOrder entryOrder = new TradeOrder(ibClient.getNextOrderId(), ticker, size, direction);
        entryOrder.setReference("Intraday-Strategy-" + ticker.getSymbol() + ":" + correlationId + ":Entry:" + direction + "*" );
        TradeOrder exitOrder = new TradeOrder(ibClient.getNextOrderId(), ticker, size, exitDirection);
        exitOrder.setReference("Intraday-Strategy-" + ticker.getSymbol() + ":" + correlationId + ":Exit:" + direction + "*" );
        exitOrder.setGoodAfterTime(zdt);
        entryOrder.addChildOrder(exitOrder);
        
        ibClient.placeOrder(entryOrder);
    }

    

    @Override
    public void orderEvent(OrderEvent event) {
        logger.info("Received order event: " + event);
    }


    @Override
    public void brokerErrorFired(BrokerError error) {
        logger.error("Error: " + error.getErrorCode() + ": " + error.getMessage());
    }

    protected IReportGenerator getReportGenerator(String homeDir) {
        return new ReportGenerator(homeDir);
    }

    protected void initProps(String filename) {
        try {
            IntradaySystemProperties props = new IntradaySystemProperties(filename);
            ibHost = props.getTwsHost();
            ibPort = props.getTwsPort();
            ibClientId = props.getTwsClientId();
            orderSizeInDollars = props.getTradeSizeDollars();
            systemStartTime = props.getSystemStartTime();
            longStartTime = props.getLongStartTime();
            longStopTime = props.getLongStopTime();
            longCloseTime = props.getLongExitTime();
            shortStartTime = props.getShortStopTime();
            shortStopTime = props.getShortStopTime();
            shortCloseTime = props.getShortExitTime();
            strategyDirectory = props.getStrategyDirectory();

            logger.info("Loaded properties: " + props);

        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        }

    }

    protected String getUUID() {
        return UUID.randomUUID().toString();
    }

    protected boolean setAllPricesInitialized() {
        if (allPricesInitialized) {
            return true;
        }

        return true;
    }

    protected boolean checkOpenOrders(List<TradeOrder> orders, List<Ticker> tickers) {
        if (orders.isEmpty()) {
            return false;
        }
        for (TradeOrder order : orders) {
            if (order.getType() == TradeOrder.Type.MARKET_ON_CLOSE) {
                for (Ticker ticker : tickers) {
                    if (order.getTicker().equals(ticker)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        String propFile = args[0];
        //String propFile = "/Users/RobTerpilowski/Downloads/ZoiData/EodTest/eod.props";
        IntradayTradingStrategy strategy = new IntradayTradingStrategy();
        strategy.start(propFile);
    }

}
