/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.limituptrading.data;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static com.limituptrading.data.Commodity.*;
import static org.junit.Assert.*;


/**
 *
 * @author RobTerpilowski
 */
public class FuturesTickerTest {

    public FuturesTickerTest() {
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
    public void testGetInstance_BySymbol() {
        FuturesTicker expected = new FuturesTicker();
        expected.setContractMultiplier( new BigDecimal(100000));
        expected.setDecimalFormat( Commodity.FOUR_DECIMALS );
        expected.setExchange(Exchange.GLOBEX);
        expected.setMinimumTickSize( new BigDecimal(0.0001));
        expected.setSymbol("6C");
        expected.setCommodity(CANADIAN_DOLLAR_GLOBEX);
        
        assertEquals( expected, FuturesTicker.getInstance("6C") );
    }
    
    
    
    @Test
    public void testGetInstance() {
        int experiationMonth = 12;
        int expirationYear = 2014;
        FuturesTicker expected = new FuturesTicker();
        expected.setSymbol("ES");
        expected.setExpiryMonth(experiationMonth);
        expected.setExpiryYear(expirationYear);
        expected.setExchange(Exchange.GLOBEX);
        expected.setDecimalFormat( Commodity.TWO_DECIMALS );
        expected.setMinimumTickSize( new BigDecimal( 0.25 ));
        expected.setContractMultiplier( new BigDecimal(50) );
        expected.setCommodity(SP500_INDEX_MINI_GLOBEX);

        assertEquals(expected, FuturesTicker.getInstance(SP500_INDEX_MINI_GLOBEX, experiationMonth, expirationYear));
    }
    

    
   

    

}
