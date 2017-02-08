/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sumzerotrading.reporting.csv;

import com.sumzerotrading.reporting.IRoundTrip;
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
public class PairTradeRoundTripBuilderTest {
    
    public PairTradeRoundTripBuilderTest() {
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
    public void testBuildRoundTrip() {
        PairTradeRoundTripBuilder builder = new PairTradeRoundTripBuilder();
        IRoundTrip roundTrip = builder.buildRoundTrip();
        assertTrue( roundTrip instanceof PairTradeRoundTrip );
    }
}
