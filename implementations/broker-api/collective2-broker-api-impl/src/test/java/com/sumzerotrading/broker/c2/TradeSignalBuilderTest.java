/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sumzerotrading.broker.c2;

import com.sumzerotrading.broker.c2.TradeSignalBuilder;
import com.sumzerotrading.broker.order.TradeDirection;
import com.sumzerotrading.broker.order.TradeOrder;
import com.sumzerotrading.data.Commodity;
import com.sumzerotrading.data.FuturesTicker;
import com.sumzerotrading.data.StockTicker;
import com.sumzerotrading.j4c2.signal.SignalInfo;
import com.sumzerotrading.j4c2.signal.SubmitSignalRequest;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author RobTerpilowski
 */
public class TradeSignalBuilderTest {
    
    protected TradeSignalBuilder testBuilder;
    
    public TradeSignalBuilderTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        testBuilder = new TradeSignalBuilder();
    }
    
    @After
    public void tearDown() {
    }


    @Test
    public void testBuildSignalRequest() {
        String systemId = "123";
        StockTicker ticker = new StockTicker("QQQ");
        
        TradeOrder order = new TradeOrder("", ticker, 100, TradeDirection.BUY);
        order.setDuration(TradeOrder.Duration.GOOD_UNTIL_CANCELED);
        
        SignalInfo expectedSignalInfo = new SignalInfo();
        SubmitSignalRequest expectedRequest = new SubmitSignalRequest(systemId, expectedSignalInfo);
        expectedSignalInfo.setTicker("QQQ");
        expectedSignalInfo.setAction(SignalInfo.Action.BTO);
        expectedSignalInfo.setIsMarketOrder(true);
        expectedSignalInfo.setSymbolType(SignalInfo.SymbolType.stock);
        expectedSignalInfo.setOrderType(SignalInfo.OrderType.MARKET);
        expectedSignalInfo.setQuantity(100);
        expectedSignalInfo.setDuration(SignalInfo.Duration.GTC);
        
        
        SubmitSignalRequest submitSignalRequest = testBuilder.buildSignalRequest(systemId, order);
        
        assertEquals( expectedSignalInfo, submitSignalRequest.getSignal() );
        assertEquals(expectedRequest,submitSignalRequest );  
        
    }
    
    @Test
    public void testBuildSignalRequest_MarketOnOpen() {
        String systemId = "123";
        StockTicker ticker = new StockTicker("QQQ");
        
        TradeOrder order = new TradeOrder("", ticker, 100, TradeDirection.BUY);
        order.setType(TradeOrder.Type.MARKET_ON_OPEN);
        order.setDuration(TradeOrder.Duration.GOOD_UNTIL_CANCELED);
        
        SignalInfo expectedSignalInfo = new SignalInfo();
        SubmitSignalRequest expectedRequest = new SubmitSignalRequest(systemId, expectedSignalInfo);
        expectedSignalInfo.setTicker("QQQ");
        expectedSignalInfo.setAction(SignalInfo.Action.BTO);
        expectedSignalInfo.setIsMarketOrder(true);
        expectedSignalInfo.setSymbolType(SignalInfo.SymbolType.stock);
        expectedSignalInfo.setOrderType(SignalInfo.OrderType.MARKET);
        expectedSignalInfo.setQuantity(100);
        expectedSignalInfo.setDuration(SignalInfo.Duration.GTC);
        
        
        SubmitSignalRequest submitSignalRequest = testBuilder.buildSignalRequest(systemId, order);
        
        assertEquals( expectedSignalInfo, submitSignalRequest.getSignal() );
        assertEquals(expectedRequest,submitSignalRequest );  
        
    }    
    
    
    @Test
    public void testBuildSignalRequest_GoodAfterTime() {
        String systemId = "123";
        StockTicker ticker = new StockTicker("QQQ");
        ZonedDateTime zoneTime = ZonedDateTime.of(2018, 4, 11, 6, 30, 0, 0, ZoneId.of("America/Los_Angeles"));        
        
        TradeOrder order = new TradeOrder("", ticker, 100, TradeDirection.BUY);
        order.setDuration(TradeOrder.Duration.GOOD_UNTIL_CANCELED);
        order.setGoodAfterTime(zoneTime);
        
        SignalInfo expectedSignalInfo = new SignalInfo();
        SubmitSignalRequest expectedRequest = new SubmitSignalRequest(systemId, expectedSignalInfo);
        expectedSignalInfo.setTicker("QQQ");
        expectedSignalInfo.setAction(SignalInfo.Action.BTO);
        expectedSignalInfo.setIsMarketOrder(true);
        expectedSignalInfo.setSymbolType(SignalInfo.SymbolType.stock);
        expectedSignalInfo.setOrderType(SignalInfo.OrderType.MARKET);
        expectedSignalInfo.setQuantity(100);
        expectedSignalInfo.setDuration(SignalInfo.Duration.GTC);
        expectedSignalInfo.setParkUntil("201804110930");
        
        
        
        
        SubmitSignalRequest submitSignalRequest = testBuilder.buildSignalRequest(systemId, order);
        
        assertEquals( expectedSignalInfo, submitSignalRequest.getSignal() );
        assertEquals(expectedRequest,submitSignalRequest );  
        
    }    
    
    @Test
    public void testBuildSignalRequest_Futures() {
        String systemId = "123";
        FuturesTicker ticker = FuturesTicker.getInstance(Commodity.CORN_ECBOT, 6, 2017);
        
        TradeOrder order = new TradeOrder("", ticker, 1, TradeDirection.BUY);
        
        SignalInfo expectedSignalInfo = new SignalInfo();
        SubmitSignalRequest expectedRequest = new SubmitSignalRequest(systemId, expectedSignalInfo);
        expectedSignalInfo.setTicker("@CM7");
        expectedSignalInfo.setAction(SignalInfo.Action.BTO);
        expectedSignalInfo.setIsMarketOrder(true);
        expectedSignalInfo.setSymbolType(SignalInfo.SymbolType.future);
        expectedSignalInfo.setOrderType(SignalInfo.OrderType.MARKET);
        expectedSignalInfo.setQuantity(1);
        expectedSignalInfo.setDuration(SignalInfo.Duration.DAY);
        
        SubmitSignalRequest submitSignalRequest = testBuilder.buildSignalRequest(systemId, order);
        
        assertEquals( expectedSignalInfo, submitSignalRequest.getSignal() );
        assertEquals(expectedRequest,submitSignalRequest );        
    }
    
    
    @Test
    public void testBuildSignalRequest_StopOrder() {
        String systemId = "123";
        StockTicker ticker = new StockTicker("QQQ");
        
        TradeOrder order = new TradeOrder("", ticker, 100, TradeDirection.BUY);
        order.setType(TradeOrder.Type.STOP);
        order.setDuration(TradeOrder.Duration.GOOD_UNTIL_CANCELED);
        order.setStopPrice(10.00);
        
        SignalInfo expectedSignalInfo = new SignalInfo();
        SubmitSignalRequest expectedRequest = new SubmitSignalRequest(systemId, expectedSignalInfo);
        expectedSignalInfo.setTicker("QQQ");
        expectedSignalInfo.setAction(SignalInfo.Action.BTO);
        expectedSignalInfo.setIsMarketOrder(false);
        expectedSignalInfo.setSymbolType(SignalInfo.SymbolType.stock);
        expectedSignalInfo.setOrderType(SignalInfo.OrderType.MARKET);
        expectedSignalInfo.setQuantity(100);
        expectedSignalInfo.setDuration(SignalInfo.Duration.GTC);
        expectedSignalInfo.setOrderType(SignalInfo.OrderType.STOP);
        expectedSignalInfo.setStopPrice(10.00);
        
        SubmitSignalRequest submitSignalRequest = testBuilder.buildSignalRequest(systemId, order);
        
        assertEquals( expectedSignalInfo, submitSignalRequest.getSignal() );
        assertEquals(expectedRequest, submitSignalRequest);
        
    }    
    
    @Test
    public void testBuildSignalRequest_LimitOrder() {
        String systemId = "123";
        StockTicker ticker = new StockTicker("QQQ");
        
        TradeOrder order = new TradeOrder("", ticker, 100, TradeDirection.BUY);
        order.setType(TradeOrder.Type.LIMIT);
        order.setDuration(TradeOrder.Duration.GOOD_UNTIL_CANCELED);
        order.setLimitPrice(10.00);
        
        SignalInfo expectedSignalInfo = new SignalInfo();
        SubmitSignalRequest expectedRequest = new SubmitSignalRequest(systemId, expectedSignalInfo);
        expectedSignalInfo.setTicker("QQQ");
        expectedSignalInfo.setAction(SignalInfo.Action.BTO);
        expectedSignalInfo.setIsMarketOrder(false);
        expectedSignalInfo.setSymbolType(SignalInfo.SymbolType.stock);
        expectedSignalInfo.setOrderType(SignalInfo.OrderType.MARKET);
        expectedSignalInfo.setQuantity(100);
        expectedSignalInfo.setDuration(SignalInfo.Duration.GTC);
        expectedSignalInfo.setOrderType(SignalInfo.OrderType.LIMIT);
        expectedSignalInfo.setLimitPrice(10.00);
        
        SubmitSignalRequest submitSignalRequest = testBuilder.buildSignalRequest(systemId, order);
        
        assertEquals( expectedSignalInfo, submitSignalRequest.getSignal() );
        assertEquals(expectedRequest, submitSignalRequest);
        
    }        
}
