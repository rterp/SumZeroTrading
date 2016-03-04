/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sumzerotrading.interactive.brokers.client;

import com.sumzerotrading.broker.IBroker;
import com.sumzerotrading.broker.order.OrderEventListener;
import com.sumzerotrading.broker.order.TradeDirection;
import com.sumzerotrading.broker.order.TradeOrder;
import com.sumzerotrading.data.BarData;
import com.sumzerotrading.data.StockTicker;
import com.sumzerotrading.data.Ticker;
import com.sumzerotrading.historicaldata.IHistoricalDataProvider;
import com.sumzerotrading.ib.IBConnectionUtil;
import com.sumzerotrading.ib.IBSocket;
import com.sumzerotrading.marketdata.ILevel1Quote;
import com.sumzerotrading.marketdata.ILevel2Quote;
import com.sumzerotrading.marketdata.Level1QuoteListener;
import com.sumzerotrading.marketdata.Level2QuoteListener;
import com.sumzerotrading.marketdata.QuoteEngine;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 *
 * @author RobTerpilowski
 */
public class InteractiveBrokersClientTest {

    protected InteractiveBrokersClient client;
    protected IBSocket mockIbSocket;
    protected QuoteEngine mockQuoteEngine;
    protected IBroker mockBroker;
    protected IHistoricalDataProvider mockHistoricalDataProvider;
    protected static IBConnectionUtil mockIbConnectionUtil;

    protected String host = "myHost";
    protected int port = 123;
    protected int clientId = 99;

    public InteractiveBrokersClientTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        mockIbSocket = mock(IBSocket.class);
        mockQuoteEngine = mock(QuoteEngine.class);
        mockBroker = mock(IBroker.class);
        mockHistoricalDataProvider = mock(IHistoricalDataProvider.class);
        mockIbConnectionUtil = mock(IBConnectionUtil.class);
        client = new InteractiveBrokersClient(host, port, clientId);
        client.broker = mockBroker;
        client.historicalDataProvider = mockHistoricalDataProvider;
        client.ibSocket = mockIbSocket;
        client.quoteEngine = mockQuoteEngine;
    }

    @After
    public void tearDown() {
    }

    
    @Test
    public void testConnect() {
        client.connect();
        verify(mockIbSocket).connect();
        verify(mockBroker).connect();
        verify(mockHistoricalDataProvider).connect();
        verify(mockQuoteEngine).startEngine();
    }

    @Test
    public void disconnect() {
        client.disconnect();
        verify(mockBroker).disconnect();
        verify(mockQuoteEngine).stopEngine();
    }

    @Test
    public void testSubscribeUnsubscribeLevel1() {
        Ticker ticker = new StockTicker("ABC");
        Level1QuoteListener listener = (ILevel1Quote quote) -> {
        };
        client.subscribeLevel1(ticker, listener);
        client.unsubscribeLevel1(ticker, listener);
        verify(mockQuoteEngine).subscribeLevel1(ticker, listener);
        verify(mockQuoteEngine).unsubscribeLevel1(ticker, listener);
    }

    @Test
    public void testSubscribeMarketDepth() {
        Ticker ticker = new StockTicker("ABC");
        Level2QuoteListener listener = (ILevel2Quote quote) -> {
        };
        client.subscribeMarketDepth(ticker, listener);
        client.unsubscribeMarketDepth(ticker, listener);
        verify(mockQuoteEngine).subscribeMarketDepth(ticker, listener);
        verify(mockQuoteEngine).unsubscribeMarketDepth(ticker, listener);

    }
    
 
    @Test
    public void testPlaceOrder() {
        TradeOrder order = new TradeOrder("123", new StockTicker("ABC"), 10, TradeDirection.SELL);
        client.placeOrder(order);
        verify(mockBroker).placeOrder(order);
    }

    @Test
    public void testAddOrderStatusListener() {
        OrderEventListener listener = mock(OrderEventListener.class);
        client.addOrderStatusListener(listener);
        verify(mockBroker).addOrderEventListener(listener);
    }
    
    @Test
    public void testGetNextOrderId() {
        when(mockBroker.getNextOrderId()).thenReturn("123");
        assertEquals("123", client.getNextOrderId());
    }
    
    @Test
    public void testGetOpenOrders() {
        List<TradeOrder> list = new ArrayList<>();
        list.add( new TradeOrder("123", new StockTicker("ABC"), 123, TradeDirection.SELL));
        when(mockBroker.getOpenOrders()).thenReturn(list);
        assertEquals(list, mockBroker.getOpenOrders());
    }
    
    @Test
    public void testRequestGetHistoricalData() {
        List<BarData> barData = new ArrayList<>();
        barData.add( new BarData());
        Ticker ticker = new StockTicker("ABC");
        int duration = 1;
        BarData.LengthUnit unit = BarData.LengthUnit.DAY;
        int barSize = 1;
        BarData.LengthUnit sizeUnit = BarData.LengthUnit.HOUR;
        IHistoricalDataProvider.ShowProperty showProperty = IHistoricalDataProvider.ShowProperty.TRADES;
        
        when(mockHistoricalDataProvider.requestHistoricalData(ticker, duration, unit, barSize, sizeUnit, showProperty, true)).thenReturn(barData);
        assertEquals(barData, client.requestHistoricalData(ticker, duration, unit, barSize, sizeUnit, showProperty));
    }
    
    
    
}
