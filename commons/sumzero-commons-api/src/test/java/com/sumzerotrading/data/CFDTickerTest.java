/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sumzerotrading.data;

import java.math.BigDecimal;
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
public class CFDTickerTest {
    
    public CFDTickerTest() {
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
    public void testConstructor() {
        CFDTicker ticker = new CFDTicker("XYZ");
        assertEquals(new BigDecimal(1), ticker.getContractMultiplier() );
        assertEquals(new BigDecimal("0.01"), ticker.getMinimumTickSize());
        assertEquals( Exchange.INTERACTIVE_BROKERS_SMART, ticker.getExchange());
        assertEquals( InstrumentType.CFD, ticker.getInstrumentType());
        assertEquals( "XYZ", ticker.getSymbol());
    }
}
