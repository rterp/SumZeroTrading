/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sumzerotrading.interactive.brokers.client;

import com.sumzerotrading.broker.BrokerErrorListener;
import com.sumzerotrading.broker.order.OrderEventListener;
import com.sumzerotrading.broker.order.TradeOrder;
import com.sumzerotrading.data.BarData;
import com.sumzerotrading.data.Ticker;
import com.sumzerotrading.historicaldata.IHistoricalDataProvider;
import com.sumzerotrading.marketdata.Level1QuoteListener;
import com.sumzerotrading.marketdata.Level2QuoteListener;
import java.util.List;

/**
 *
 * @author RobTerpilowski
 */
public interface InteractiveBrokersClientInterface {

    void addOrderStatusListener(OrderEventListener listener);

    void connect();

    void disconnect();

    int getClientId();

    String getHost();

    String getNextOrderId();

    int getPort();

    void placeOrder(TradeOrder order);

    List<BarData> requestHistoricalData(Ticker ticker, int duration, BarData.LengthUnit lengthUnit, int barSize, BarData.LengthUnit barSizeUnit, IHistoricalDataProvider.ShowProperty showProperty);

    void subscribeLevel1(Ticker ticker, Level1QuoteListener listener);

    void subscribeMarketDepth(Ticker ticker, Level2QuoteListener listener);

    void unsubscribeLevel1(Ticker ticker, Level1QuoteListener listener);

    void unsubscribeMarketDepth(Ticker ticker, Level2QuoteListener listener);
    
    void addBrokerErrorListener( BrokerErrorListener listener );
    
    void removeBrokerErrorListener( BrokerErrorListener listener );
    
    List<TradeOrder> getOpenOrders();
    
}
