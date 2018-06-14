/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sumzerotrading.broker.c2;

import com.sumzerotrading.broker.order.TradeDirection;
import com.sumzerotrading.broker.order.TradeOrder;
import com.sumzerotrading.data.CFDTicker;
import com.sumzerotrading.data.Commodity;
import com.sumzerotrading.data.CurrencyTicker;
import com.sumzerotrading.data.FuturesTicker;
import com.sumzerotrading.data.StockTicker;
import com.sumzerotrading.data.SumZeroException;
import com.sumzerotrading.j4c2.signal.SignalInfo;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnit44Runner;

/**
 *
 * @author RobTerpilowski
 */
@RunWith(MockitoJUnit44Runner.class)
public class C2UtilTest {
    
    public C2UtilTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }


    
    @Test
    public void testGetC2RootSymbol() {
        
        assertEquals( C2Util.ROOT_SYMBOL_NASDAQ_100, C2Util.getC2RootSymbol("NQ"));
        assertEquals( C2Util.ROOT_SYMBOL_MINI_DOW, C2Util.getC2RootSymbol("YM"));
        assertEquals(C2Util.ROOT_SYMBOL_RUSSEL2K_NYBOT, C2Util.getC2RootSymbol("TF"));
        assertEquals(C2Util.ROOT_SYMBOL_RUSSEL2K_GLOBEX, C2Util.getC2RootSymbol("RTY"));
        assertEquals( C2Util.ROOT_SYMBOL_SP500, C2Util.getC2RootSymbol("ES"));
        assertEquals( C2Util.ROOT_SYMBOL_CORN, C2Util.getC2RootSymbol("ZC"));
        assertEquals( C2Util.ROOT_SYMBOL_CORN, C2Util.getC2RootSymbol("C"));
        
        
        try {
            C2Util.getC2RootSymbol("QQQ");
            fail();
        } catch( SumZeroException ex ) {
            //this should happen
        }
    }
    
    
    @Test
    public void testGetOrderType() {
        TradeOrder order = new TradeOrder();
        order.setType(TradeOrder.Type.STOP);
        
        assertEquals(SignalInfo.OrderType.STOP, C2Util.getOrderType(order));
        
        order.setType(TradeOrder.Type.LIMIT);
        assertEquals(SignalInfo.OrderType.LIMIT, C2Util.getOrderType(order));
        
        order.setType(TradeOrder.Type.MARKET);
        assertEquals(SignalInfo.OrderType.MARKET, C2Util.getOrderType(order));      
        
        order.setType(TradeOrder.Type.MARKET_ON_OPEN);
        assertEquals(SignalInfo.OrderType.MARKET, C2Util.getOrderType(order));          
        
        try {
            order.setType(TradeOrder.Type.MARKET_ON_CLOSE);
            C2Util.getOrderType(order);
            fail();
        } catch( Exception ex ) {
            //this should happen
        }
    }
    
    
    @Test
    public void testGetSymbolType() {
        StockTicker stockTicker = new StockTicker("A");
        FuturesTicker futuresTicker = new FuturesTicker();
        CurrencyTicker fxTicker = new CurrencyTicker();
        CFDTicker cfdTicker = new CFDTicker("A");
        
        TradeOrder order = new TradeOrder();
        
        order.setTicker(stockTicker);
        assertEquals(SignalInfo.SymbolType.stock, C2Util.getSymbolType(order));
        
        order.setTicker(futuresTicker);
        assertEquals(SignalInfo.SymbolType.future, C2Util.getSymbolType(order));
        
        order.setTicker(fxTicker);
        assertEquals(SignalInfo.SymbolType.forex, C2Util.getSymbolType(order));        
        
        try {
            order.setTicker(cfdTicker);
            C2Util.getSymbolType(order);
            fail();
        } catch( SumZeroException ex ) {
            //this should happen
        }
        
    }
    
    
    @Test
    public void testGetTradeAction() {
        TradeOrder order = new TradeOrder();
        
        order.setTradeDirection(TradeDirection.BUY);
        assertEquals(SignalInfo.Action.BTO, C2Util.getTradeAction(order));
        
        order.setTradeDirection(TradeDirection.SELL);
        assertEquals(SignalInfo.Action.STC, C2Util.getTradeAction(order));        
        
        order.setTradeDirection(TradeDirection.BUY_TO_COVER);
        assertEquals(SignalInfo.Action.BTC, C2Util.getTradeAction(order));        
        
        order.setTradeDirection(TradeDirection.SELL_SHORT);
        assertEquals(SignalInfo.Action.STO, C2Util.getTradeAction(order));        
        
        try {
            order.setTradeDirection(null);
            C2Util.getTradeAction(order);
            fail();
        } catch( SumZeroException ex ) {
            //this should happen
        }
        
    }
    
    
    @Test
    public void testGetFuturesSymbol() {
        FuturesTicker ticker = FuturesTicker.getInstance(Commodity.NASDAQ100_INDEX_MINI_GLOBEX, 3, 2018);
        assertEquals("@NQH8", C2Util.getFuturesSymbol(ticker));
    }
    
    
    @Test
    public void testGetTime() {
        ZonedDateTime time = ZonedDateTime.of(2018, 4, 11, 6, 40, 0, 0, ZoneId.of("America/Los_Angeles"));
        assertEquals("201804110940", C2Util.getTime(time));
    }
}
