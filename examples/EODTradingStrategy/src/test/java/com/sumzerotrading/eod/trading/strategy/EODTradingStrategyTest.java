/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sumzerotrading.eod.trading.strategy;

import com.sumzerotrading.data.StockTicker;
import com.sumzerotrading.data.Ticker;
import com.sumzerotrading.interactive.brokers.client.InteractiveBrokersClient;
import com.sumzerotrading.interactive.brokers.client.InteractiveBrokersClientInterface;
import com.sumzerotrading.marketdata.ILevel1Quote;
import com.sumzerotrading.marketdata.QuoteType;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 *
 * @author RobTerpilowski
 */
public class EODTradingStrategyTest {
    
    protected InteractiveBrokersClientInterface mockIbClient;
    protected String ibHost = "myHost";
    protected int ibPort = 9999;
    protected int ibClientClientId = 1111;
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
        InteractiveBrokersClient.setMockInteractiveBrokersClient(mockIbClient, ibHost, ibPort, ibPort);
        strategy = spy(EODTradingStrategy.class);
    }
    
    @After
    public void tearDown() {
    }
    
    
    
    /**
     *     @Override
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
     */
    
    @Test
    public void testQuoteRecieved() {
//        ILevel1Quote mockQuote = mock(ILevel1Quote.class);
//        when(mockQuote.getType()).thenReturn(QuoteType.VOLUME);
//
//        strategy.quoteRecieved(mockQuote);
//        
//        verify(strategy, never()).setAllPricesInitialized();
    }
    
    @Test
    public void testGetNextBusinessDay_Thursday() {
        ZonedDateTime ldt = ZonedDateTime.of(2016, 2, 25, 6, 45, 55, 0, ZoneId.systemDefault());
        ZonedDateTime expectedDatetime = ZonedDateTime.of(2016,2,26,5,30,0,0, ZoneId.systemDefault());
        
        assertEquals( expectedDatetime, strategy.getNextBusinessDay(ldt));
    }
    
    @Test
    public void testGetNextBusinessDay_Friday() {
        ZonedDateTime ldt = ZonedDateTime.of(2016, 2, 26, 6, 45, 55, 0, ZoneId.systemDefault());
        ZonedDateTime expectedDatetime = ZonedDateTime.of(2016,2,29,5,30,0, 0, ZoneId.systemDefault());
        
        assertEquals( expectedDatetime, strategy.getNextBusinessDay(ldt));
    }    
    
    @Test
    public void testGetNextBusinessDay_Satruday() {
        ZonedDateTime ldt = ZonedDateTime.of(2016, 2, 27, 6, 45, 55, 0, ZoneId.systemDefault());
        ZonedDateTime expectedDatetime = ZonedDateTime.of(2016,2,29,5,30,0, 0, ZoneId.systemDefault());
        
        assertEquals( expectedDatetime, strategy.getNextBusinessDay(ldt));
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
    
}
