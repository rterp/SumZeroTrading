/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sumzerotrading.eod.trading.strategy;

import com.sumzerotrading.broker.order.TradeDirection;
import com.sumzerotrading.broker.order.TradeOrder;
import com.sumzerotrading.data.StockTicker;
import com.sumzerotrading.data.Ticker;
import com.sumzerotrading.interactive.brokers.client.InteractiveBrokersClient;
import com.sumzerotrading.interactive.brokers.client.InteractiveBrokersClientInterface;
import com.sumzerotrading.marketdata.ILevel1Quote;
import com.sumzerotrading.marketdata.Level1QuoteListener;
import com.sumzerotrading.marketdata.QuoteType;
import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 *
 * @author RobTerpilowski
 */
public class EODTradingStrategyTest {

    protected InteractiveBrokersClientInterface mockIbClient;
    protected String ibHost = "myHost";
    protected int ibPort = 9999;
    protected int ibClientId = 1111;
    protected Ticker longTicker = new StockTicker("LONG");
    protected Ticker shortTicker = new StockTicker("SHORT");
    protected EODTradingStrategy strategy;

    public EODTradingStrategyTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        mockIbClient = mock(InteractiveBrokersClientInterface.class);
        InteractiveBrokersClient.setMockInteractiveBrokersClient(mockIbClient, ibHost, ibPort, ibClientId);
        strategy = spy(EODTradingStrategy.class);
        strategy.ibHost = ibHost;
        strategy.ibPort = ibPort;
        strategy.ibClientId = ibClientId;
    }

    @After
    public void tearDown() {
    }


    @Test
    public void testStart() {
        
        TradeOrder order = new TradeOrder("123", longTicker, 100, TradeDirection.SELL);
        List<TradeOrder> openOrders = new ArrayList<>();
        openOrders.add(order);
        when(mockIbClient.getOpenOrders()).thenReturn(openOrders);
        doReturn(true).when(strategy).checkOpenOrders(openOrders, strategy.longShortPairMap);
        
        strategy.start();
        
        verify(mockIbClient).subscribeLevel1(eq(new StockTicker("QQQ")), any(Level1QuoteListener.class));
        verify(mockIbClient, times(2)).subscribeLevel1(eq(new StockTicker("SPY")), any(Level1QuoteListener.class));
        verify(mockIbClient, times(2)).subscribeLevel1(eq(new StockTicker("IWM")), any(Level1QuoteListener.class));
        verify(mockIbClient).subscribeLevel1(eq(new StockTicker("DIA")), any(Level1QuoteListener.class));
        
        
    }
    
    
    
    @Test
    public void testPlaceMOCOrders() {
        String corrId = "123";
        int longSize = 100;
        int shortSize = 200;
        ZonedDateTime orderTime = ZonedDateTime.now();
        strategy.ibClient = mockIbClient;
        
        doReturn(corrId).when(strategy).getUUID();
        doReturn(longSize).when(strategy).getOrderSize(longTicker);
        doReturn(shortSize).when(strategy).getOrderSize(shortTicker);
        doReturn(orderTime).when(strategy).getNextBusinessDay(any(ZonedDateTime.class));
        when(mockIbClient.getNextOrderId()).thenReturn("100", "101", "102", "103", "104", "105");
        
        TradeOrder expectedLongOrder = new TradeOrder("100", longTicker, longSize, TradeDirection.BUY);
        expectedLongOrder.setType(TradeOrder.Type.MARKET_ON_CLOSE);
        expectedLongOrder.setReferenceString("EOD-Pair-Strategy:123:Entry:LongSide*");
        
        TradeOrder expectedLongExitOrder = new TradeOrder("101", longTicker, longSize, TradeDirection.SELL);
        expectedLongExitOrder.setType(TradeOrder.Type.MARKET_ON_OPEN);  
        expectedLongExitOrder.setGoodAfterTime(orderTime);
        expectedLongExitOrder.setReferenceString("EOD-Pair-Strategy:123:Exit:LongSide*");
        
        expectedLongOrder.addChildOrder(expectedLongExitOrder);
        
        TradeOrder expectedShortOrder = new TradeOrder("102", shortTicker, shortSize, TradeDirection.SELL_SHORT);
        expectedShortOrder.setType(TradeOrder.Type.MARKET_ON_CLOSE);
        expectedShortOrder.setReferenceString("EOD-Pair-Strategy:123:Entry:ShortSide*");
        
        TradeOrder expectedShortExitOrder = new TradeOrder("103", shortTicker, shortSize,TradeDirection.BUY_TO_COVER);
        expectedShortExitOrder.setType(TradeOrder.Type.MARKET_ON_OPEN);
        expectedShortExitOrder.setGoodAfterTime(orderTime);
        expectedShortExitOrder.setReferenceString("EOD-Pair-Strategy:123:Exit:ShortSide*");
        
        expectedShortOrder.addChildOrder(expectedShortExitOrder);
        
        strategy.placeMOCOrders(longTicker, shortTicker);
        
        verify(mockIbClient).placeOrder(expectedLongOrder);
        verify(mockIbClient).placeOrder(expectedShortOrder);
    }
    
    
    @Test
    public void testQuoteRecieved_NotLast() {
        ILevel1Quote mockQuote = mock(ILevel1Quote.class);
        when(mockQuote.getType()).thenReturn(QuoteType.VOLUME);
        strategy.quoteRecieved(mockQuote);
        
        verify(strategy, never()).setAllPricesInitialized();
        verify(mockQuote, never()).getTimeStamp();
    }
    
    @Test
    public void testQuoteReceived_NotAllPricesInitialized_OrdersNotPlaced() {
        ZonedDateTime zdt = ZonedDateTime.of(2015, 6, 15, 15, 30, 0, 0, ZoneId.of("Z"));
        ILevel1Quote mockQuote = mock(ILevel1Quote.class);
        Ticker ticker = new StockTicker("ABC");
        when(mockQuote.getType()).thenReturn(QuoteType.LAST);
        when(mockQuote.getTicker()).thenReturn(ticker);
        when(mockQuote.getValue()).thenReturn(BigDecimal.ONE);
        when(mockQuote.getTimeStamp()).thenReturn(zdt);
        doReturn(false).when(strategy).setAllPricesInitialized();
        strategy.ordersPlaced = false;
        
        strategy.quoteRecieved(mockQuote);
        
        assertFalse(strategy.ordersPlaced);
        verify(strategy).setAllPricesInitialized();
        verify(strategy,never()).placeMOCOrders(any(Ticker.class), any(Ticker.class));
    }
    
   @Test
    public void testQuoteReceived_AllPricesInitialized_OrdersNotPlaced_IsBeforeTradeTime() {
        ZonedDateTime zdt = ZonedDateTime.of(2015, 6, 15, 15, 30, 0, 0, ZoneId.of("Z"));
        ILevel1Quote mockQuote = mock(ILevel1Quote.class);
        Ticker ticker = new StockTicker("ABC");
        when(mockQuote.getType()).thenReturn(QuoteType.LAST);
        when(mockQuote.getTicker()).thenReturn(ticker);
        when(mockQuote.getValue()).thenReturn(BigDecimal.ONE);
        when(mockQuote.getTimeStamp()).thenReturn(zdt);
        doReturn(true).when(strategy).setAllPricesInitialized();
        strategy.ordersPlaced = false;
        
        strategy.quoteRecieved(mockQuote);
        
        assertFalse(strategy.ordersPlaced);
        verify(strategy).setAllPricesInitialized();
        verify(strategy,never()).placeMOCOrders(any(Ticker.class), any(Ticker.class));
    }    
    

@Test
    public void testQuoteReceived_AllPricesInitialized_OrdersPacePlaced_BeforeTradeTime() {
        ZonedDateTime zdt = ZonedDateTime.of(2015, 6, 15, 21, 30, 0, 0, ZoneId.of("Z"));
        ILevel1Quote mockQuote = mock(ILevel1Quote.class);
        Ticker ticker = new StockTicker("ABC");
        when(mockQuote.getType()).thenReturn(QuoteType.LAST);
        when(mockQuote.getTicker()).thenReturn(ticker);
        when(mockQuote.getValue()).thenReturn(BigDecimal.ONE);
        when(mockQuote.getTimeStamp()).thenReturn(zdt);
        doReturn(true).when(strategy).setAllPricesInitialized();
        strategy.ordersPlaced = true;
        
        strategy.quoteRecieved(mockQuote);
        
        assertTrue(strategy.ordersPlaced);
        verify(strategy).setAllPricesInitialized();
        verify(strategy,never()).placeMOCOrders(any(Ticker.class), any(Ticker.class));
    }    
    
    @Test
    public void testQuoteReceived_AllPricesInitialized_OrdersNotPlaced_AfterTradeTime() {
        ZonedDateTime zdt = ZonedDateTime.of(2015, 6, 15, 23, 30, 0, 0, ZoneId.of("Z"));
        ILevel1Quote mockQuote = mock(ILevel1Quote.class);
        strategy.longShortPairMap.put(longTicker,shortTicker);
        
        when(mockQuote.getType()).thenReturn(QuoteType.LAST);
        when(mockQuote.getTicker()).thenReturn(longTicker);
        when(mockQuote.getValue()).thenReturn(BigDecimal.ONE);
        when(mockQuote.getTimeStamp()).thenReturn(zdt);
        doReturn(true).when(strategy).setAllPricesInitialized();
        doNothing().when(strategy).placeMOCOrders(any(Ticker.class), any(Ticker.class));
        strategy.ordersPlaced = false;
        
        strategy.quoteRecieved(mockQuote);
        
        assertTrue(strategy.ordersPlaced);
        verify(strategy).setAllPricesInitialized();
        verify(strategy).placeMOCOrders(longTicker,shortTicker);
    }   

 

    @Test
    public void testGetNextBusinessDay_Thursday() {
        ZonedDateTime ldt = ZonedDateTime.of(2016, 2, 25, 6, 45, 55, 0, ZoneId.systemDefault());
        ZonedDateTime expectedDatetime = ZonedDateTime.of(2016, 2, 26, 5, 30, 0, 0, ZoneId.systemDefault());

        assertEquals(expectedDatetime, strategy.getNextBusinessDay(ldt));
    }

    @Test
    public void testGetNextBusinessDay_Friday() {
        ZonedDateTime ldt = ZonedDateTime.of(2016, 2, 26, 6, 45, 55, 0, ZoneId.systemDefault());
        ZonedDateTime expectedDatetime = ZonedDateTime.of(2016, 2, 29, 5, 30, 0, 0, ZoneId.systemDefault());

        assertEquals(expectedDatetime, strategy.getNextBusinessDay(ldt));
    }

    @Test
    public void testGetNextBusinessDay_Satruday() {
        ZonedDateTime ldt = ZonedDateTime.of(2016, 2, 27, 6, 45, 55, 0, ZoneId.systemDefault());
        ZonedDateTime expectedDatetime = ZonedDateTime.of(2016, 2, 29, 5, 30, 0, 0, ZoneId.systemDefault());

        assertEquals(expectedDatetime, strategy.getNextBusinessDay(ldt));
    }

    @Test
    public void testGetOrderSize() {
        strategy.lastPriceMap.put(longTicker, 50.00);
        int expectedSize = 20;

        assertEquals(expectedSize, strategy.getOrderSize(longTicker));
    }

    @Test
    public void testSetAllPricesInitialized() {
        Map mockLastPriceMap = mock(Map.class);
        strategy.lastPriceMap = mockLastPriceMap;
        strategy.longShortPairMap.put(longTicker, shortTicker);
        when(mockLastPriceMap.get(longTicker)).thenReturn(55.5);
        when(mockLastPriceMap.get(shortTicker)).thenReturn(20.5);

        assertFalse(strategy.allPricesInitialized);
        assertTrue(strategy.setAllPricesInitialized());
    }

    @Test
    public void testSetAllPricesInitialized_LongNotInitialized() {
        Map mockLastPriceMap = mock(Map.class);
        strategy.lastPriceMap = mockLastPriceMap;
        strategy.longShortPairMap.put(longTicker, shortTicker);
        when(mockLastPriceMap.get(longTicker)).thenReturn(null);
        when(mockLastPriceMap.get(shortTicker)).thenReturn(20.5);

        assertFalse(strategy.allPricesInitialized);
        assertFalse(strategy.setAllPricesInitialized());
    }

    @Test
    public void testSetAllPricesInitialized_ShortNotInitialized() {
        Map mockLastPriceMap = mock(Map.class);
        strategy.lastPriceMap = mockLastPriceMap;
        strategy.longShortPairMap.put(longTicker, shortTicker);
        when(mockLastPriceMap.get(longTicker)).thenReturn(50.0);
        when(mockLastPriceMap.get(shortTicker)).thenReturn(null);

        assertFalse(strategy.allPricesInitialized);
        assertFalse(strategy.setAllPricesInitialized());
    }

    @Test
    public void testSetAllPricesInitialized_AlreadyInitialized() {
        strategy.allPricesInitialized = true;
        Map mockLastPriceMap = mock(Map.class);
        strategy.lastPriceMap = mockLastPriceMap;

        assertTrue(strategy.setAllPricesInitialized());
        verify(mockLastPriceMap, never()).get(longTicker);
        verify(mockLastPriceMap, never()).get(shortTicker);
    }

    @Test
    public void testCheckOpenOrders_OrderMapEmpty() {
        assertFalse(strategy.checkOpenOrders(new ArrayList<>(), new HashMap<>()));
    }

    @Test
    public void testCheckOpenOrders_NoMocOrders() {
        List<TradeOrder> orders = new ArrayList<>();
        orders.add(new TradeOrder("123", new StockTicker("ABC"), 10, TradeDirection.SELL));

        assertFalse(strategy.checkOpenOrders(orders, new HashMap<>()));
    }

    @Test
    public void testCheckOpenOrders_MocOrdersDontMatchTickers() {
        List<TradeOrder> orders = new ArrayList<>();
        TradeOrder order = new TradeOrder("123", new StockTicker("ABC"), 10, TradeDirection.SELL);
        order.setType(TradeOrder.Type.MARKET_ON_CLOSE);
        orders.add(order);
        Map<Ticker, Ticker> tickerMap = new HashMap<>();
        tickerMap.put(new StockTicker("AAA"), new StockTicker("XYZ"));

        assertFalse(strategy.checkOpenOrders(orders, tickerMap));
    }

    @Test
    public void testCheckOpenOrders_HappyPath() {
        List<TradeOrder> orders = new ArrayList<>();
        TradeOrder order = new TradeOrder("123", new StockTicker("ABC"), 10, TradeDirection.SELL);
        order.setType(TradeOrder.Type.MARKET_ON_CLOSE);
        orders.add(order);
        Map<Ticker, Ticker> tickerMap = new HashMap<>();
        tickerMap.put(new StockTicker("ABC"), new StockTicker("XYZ"));

        assertTrue(strategy.checkOpenOrders(orders, tickerMap));
    }

}
