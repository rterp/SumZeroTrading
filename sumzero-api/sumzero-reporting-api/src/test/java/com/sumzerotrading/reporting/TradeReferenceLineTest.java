package com.sumzerotrading.reporting;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import static com.sumzerotrading.reporting.TradeReferenceLine.Direction.LONG;
import static com.sumzerotrading.reporting.TradeReferenceLine.Direction.SHORT;
import static com.sumzerotrading.reporting.TradeReferenceLine.Side.ENTRY;
import static com.sumzerotrading.reporting.TradeReferenceLine.Side.EXIT;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

/**
 *
 * @author RobTerpilowski
 */
public class TradeReferenceLineTest {
    
    protected TradeReferenceLine testLine;
    
    protected TradeUIDProvider mockUIDProvider;
    
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
        mockUIDProvider = mock(TradeUIDProvider.class);
        TradeUIDProvider.setMockTradeUIDProvider(mockUIDProvider);
    }
    
    @After
    public void tearDown() {
    }


    @Test
    public void testParse() {
     String e = "EOD-Pair-Strategy:123:Exit:Long*MyAdditionalInfo";
     TradeReferenceLine expected = new TradeReferenceLine();
     expected.correlationId = "123";
     expected.strategy = "EOD-Pair-Strategy";
     expected.side = EXIT;
     expected.direction = LONG;
     expected.additionalInfo = "MyAdditionalInfo";
     
     
     doReturn(LONG).when(testLine).parseDirection("Long");
     doReturn(EXIT).when(testLine).parseSide("Exit");
     
     testLine.parse(e);
     
     assertEquals(expected.toString(),testLine.toString());
     
    }
    
    @Test
    public void testParse_NoAdditionalInfo() {
     String e = "EOD-Pair-Strategy:123:Exit:Long*";
     TradeReferenceLine expected = new TradeReferenceLine();
     expected.correlationId = "123";
     expected.strategy = "EOD-Pair-Strategy";
     expected.side = EXIT;
     expected.direction = LONG;
     expected.additionalInfo = "";
     
     
     doReturn(LONG).when(testLine).parseDirection("Long");
     doReturn(EXIT).when(testLine).parseSide("Exit");
     
     testLine.parse(e);
     
     assertEquals(expected.toString(),testLine.toString());
     
    }    
    
    @Test
    public void testStaticParse() {
     String e = "EOD-Pair-Strategy:123:Exit:Long*MyAdditionalInfo";
     TradeReferenceLine expected = new TradeReferenceLine();
     expected.correlationId = "123";
     expected.strategy = "EOD-Pair-Strategy";
     expected.side = EXIT;
     expected.direction = LONG;
     expected.additionalInfo = "MyAdditionalInfo";
     
     
     TradeReferenceLine actualLine = TradeReferenceLine.parseLine(e);
     
     assertEquals(expected.toString(),actualLine.toString());
     
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
    
    @Test
    public void testSettersAndToString() {
        TradeReferenceLine line = new TradeReferenceLine();
        
        line.setStrategy("MyStrategy")
                .setCorrelationId("MyCorrId")
                .setDirection(TradeReferenceLine.Direction.LONG)
                .setSide(EXIT)
                .setAdditionalInfo("MyAdditionalInfo");
        
        String expectedString = "MyStrategy:MyCorrId:EXIT:LONG*MyAdditionalInfo";
        
        assertEquals(expectedString, line.toString());
                
    }
    
    @Test
    public void testCreateEntryLine_NoAdditionalInfo() {
        when(mockUIDProvider.getUID()).thenReturn("MyUID");
        TradeReferenceLine line = TradeReferenceLine.createEntryLine("MyStrategy", LONG);
        String expectedString = "MyStrategy:MyUID:ENTRY:LONG*";
        
        assertEquals(expectedString, line.toString());
        
    }
    
    @Test
    public void testCreateEntryLine_WithAdditionalInfo() {
        when(mockUIDProvider.getUID()).thenReturn("MyUID");
        TradeReferenceLine line = TradeReferenceLine.createEntryLine("MyStrategy", SHORT, "Additional");
        String expectedString = "MyStrategy:MyUID:ENTRY:SHORT*Additional";
        
        assertEquals(expectedString, line.toString());
        
    }    
}
