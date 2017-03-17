/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sumzerotrading.broker.ib;

import com.sumzerotrading.broker.order.TradeDirection;
import com.sumzerotrading.broker.order.TradeOrder;
import com.sumzerotrading.data.Commodity;
import com.sumzerotrading.data.FuturesTicker;
import com.sumzerotrading.data.StockTicker;
import com.sumzerotrading.j4c2.signal.SignalInfo;
import com.sumzerotrading.j4c2.signal.SubmitSignalRequest;
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
        
        SignalInfo expectedSignalInfo = new SignalInfo();
        SubmitSignalRequest expectedRequest = new SubmitSignalRequest(systemId, expectedSignalInfo);
        expectedSignalInfo.setTicker("QQQ");
        expectedSignalInfo.setAction(SignalInfo.Action.BTO);
        expectedSignalInfo.setIsMarketOrder(true);
        expectedSignalInfo.setSymbolType(SignalInfo.SymbolType.stock);
        expectedSignalInfo.setOrderType(SignalInfo.OrderType.MARKET);
        expectedSignalInfo.setQuantity(100);
        
        assertEquals(expectedRequest, testBuilder.buildSignalRequest(systemId, order));
        
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
        
        assertEquals(expectedRequest, testBuilder.buildSignalRequest(systemId, order));        
    }
}
