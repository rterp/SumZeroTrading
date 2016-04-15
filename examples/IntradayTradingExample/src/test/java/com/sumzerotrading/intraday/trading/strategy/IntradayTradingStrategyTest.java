/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sumzerotrading.intraday.trading.strategy;

import com.sumzerotrading.data.StockTicker;
import java.io.File;
import java.time.LocalTime;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author RobTerpilowski
 */
public class IntradayTradingStrategyTest {
    
    IntradayTradingStrategy strategy;
    
    public IntradayTradingStrategyTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        strategy = new IntradayTradingStrategy();
    }
    
    @After
    public void tearDown() {
    }


    @Test
    public void testInitProps() throws Exception {
        File file = new File( getClass().getResource("/intraday.test.properties").toURI() );
        String path = file.getAbsolutePath();
        strategy.initProps(path);
        
        assertEquals( "localhost", strategy.ibHost );
        assertEquals( 10, strategy.ibClientId );
        assertEquals( 9999, strategy.ibPort );
        assertEquals( 1000, strategy.orderSizeInDollars );
        assertEquals( LocalTime.of(5, 37, 0), strategy.systemStartTime );
        assertEquals( LocalTime.of(5,37,0), strategy.longStartTime);
        assertEquals( LocalTime.of(5,37,0), strategy.longStopTime);
        assertEquals( LocalTime.of(5,38,0), strategy.longCloseTime);
        assertEquals( LocalTime.of(5,39,0), strategy.shortStartTime);
        assertEquals( LocalTime.of(5,41,0), strategy.shortStopTime);
        assertEquals( LocalTime.of(5,42,0), strategy.shortCloseTime);
        assertEquals("/my/dir", strategy.strategyDirectory);
        assertEquals( new StockTicker("XYZ"), strategy.mainTicker);
    
    }
}
