/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sumzerotrading.data;

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
public class ExchangeTest {
    
    public ExchangeTest() {
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
    public void testGetExchange() {
        assertEquals( Exchange.INTERACTIVE_BROKERS_SMART, Exchange.getExchangeFromString("SMART"));
    }
    
    @Test
    public void testGetExchange_throwsException() {
        
        try {
            Exchange.getExchangeFromString("Foo");
            fail();
        } catch( SumZeroException ex ) {
            //this should happen
        }
        
    }
}
