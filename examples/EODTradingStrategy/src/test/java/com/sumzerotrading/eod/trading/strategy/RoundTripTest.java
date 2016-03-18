/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sumzerotrading.eod.trading.strategy;

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
public class RoundTripTest {
    
    public RoundTripTest() {
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
    public void testAddTradeReferenceLine() {
        
        /**
         * Test if order events get fired under the following conditions.
         * - App disconnects from TWS, order is filled, app reconnects to TWS.
         * - TWS shuts down, order is filled, tws starts back up, app reconnects to TWS.
         * --If above test works, test how long TWS can be off for, before the app does
         * --not receive the order event.
         */
        
        fail();
    }
}
