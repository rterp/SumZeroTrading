/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sumzerotrading.eod.trading.strategy;

import static com.sumzerotrading.eod.trading.strategy.TradeReferenceLine.Direction.LONG;
import static com.sumzerotrading.eod.trading.strategy.TradeReferenceLine.Direction.SHORT;
import static com.sumzerotrading.eod.trading.strategy.TradeReferenceLine.Side.ENTRY;
import static com.sumzerotrading.eod.trading.strategy.TradeReferenceLine.Side.EXIT;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

/**
 *
 * @author RobTerpilowski
 */
public class TradeReferenceLineTest {
    
    protected TradeReferenceLine testLine;
    
    public TradeReferenceLineTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        testLine = spy(TradeReferenceLine.class);
    }
    
    @After
    public void tearDown() {
    }


    @Test
    public void testParse() {
     String e = "EOD-Pair-Strategy:123:Exit:Long*";
     TradeReferenceLine expected = new TradeReferenceLine();
     expected.correlationId = "123";
     expected.strategy = "EOD-Pair-Strategy";
     expected.side = EXIT;
     expected.direction = LONG;
     
     
     doReturn(LONG).when(testLine).parseDirection("Long");
     doReturn(EXIT).when(testLine).parseSide("Exit");
     
     testLine.parse(e);
     
     assertEquals(expected.toString(),testLine.toString());
     
    }
    
    
    @Test
    public void testParseDirection() {
        assertEquals(testLine.parseDirection("Long"), LONG);
        assertEquals(testLine.parseDirection("Short"), SHORT);
        
        try {
            testLine.parseDirection("foo");
            fail();
        } catch( IllegalStateException ex ) {
            //this should happen
        }
    }
    
    @Test
    public void testParseSide() {
        assertEquals(testLine.parseSide("Entry"), ENTRY);
        assertEquals(testLine.parseSide("Exit"), EXIT);
        
        try {
            testLine.parseSide("foo");
        } catch( IllegalStateException ex ) {
            //this should happend
        }
    }
}
