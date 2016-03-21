/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sumzerotrading.eod.trading.strategy;

import com.sumzerotrading.broker.order.OrderEvent;
import com.sumzerotrading.broker.order.OrderStatus;
import com.sumzerotrading.broker.order.TradeDirection;
import com.sumzerotrading.broker.order.TradeOrder;
import com.sumzerotrading.data.StockTicker;
import com.sumzerotrading.data.Ticker;
import com.sumzerotrading.eod.trading.strategy.TradeReferenceLine.Direction;
import static com.sumzerotrading.eod.trading.strategy.TradeReferenceLine.Direction.LONG;
import static com.sumzerotrading.eod.trading.strategy.TradeReferenceLine.Direction.SHORT;
import com.sumzerotrading.eod.trading.strategy.TradeReferenceLine.Side;
import static com.sumzerotrading.eod.trading.strategy.TradeReferenceLine.Side.ENTRY;
import static com.sumzerotrading.eod.trading.strategy.TradeReferenceLine.Side.EXIT;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

/**
 *
 * @author RobTerpilowski
 */
public class ReportGeneratorTest {
    
    protected ReportGenerator reportGenerator;
    protected TradeOrder order;
    protected String tmpDir;
    protected String partialDir;
    
    public ReportGeneratorTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws Exception {
        reportGenerator = spy(ReportGenerator.class);
        order = new TradeOrder("123", new StockTicker("QQQ"), 100, TradeDirection.BUY);
        tmpDir = System.getProperty("java.io.tmpdir") + "rg-test/";
        partialDir  = tmpDir + "partial/";
        FileUtils.deleteDirectory(new File(tmpDir));
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void testConstructor_NoSlashInPath() {
        ReportGenerator generator = new ReportGenerator(tmpDir);
        assertEquals( tmpDir + "report.csv", generator.outputFile);
        assertEquals( tmpDir, generator.outputDir);
        assertEquals( tmpDir + "partial/", generator.partialDir);
        assertTrue( Files.exists(Paths.get(generator.outputDir)));
        assertTrue( Files.exists(Paths.get(generator.partialDir)));
        
        
    }
    
    
    @Test
    public void testLoadSaveDeletePartial() throws Exception {
        ReportGenerator generator = new ReportGenerator(tmpDir);
        
        RoundTrip roundTrip = new RoundTrip();
        TradeReferenceLine tradeReferenceLine = buildReferenceLine("123", LONG, ENTRY);
        tradeReferenceLine.correlationId = "123";
        tradeReferenceLine.direction = TradeReferenceLine.Direction.LONG;
        tradeReferenceLine.side = TradeReferenceLine.Side.ENTRY;
        roundTrip.addTradeReference(order, tradeReferenceLine);
        
        order.setCurrentStatus(OrderStatus.Status.FILLED);
        OrderEvent orderEvent = new OrderEvent(order, new OrderStatus(OrderStatus.Status.NEW, "", "", new StockTicker("QQQ"), ZonedDateTime.now()));
        
        TradeReferenceLine longExitLine = buildReferenceLine("123", LONG, EXIT);
        TradeReferenceLine shortEntryLine = buildReferenceLine("123", SHORT, ENTRY);
        TradeReferenceLine shortExitLine = buildReferenceLine("123", SHORT, EXIT);
        
        roundTrip.addTradeReference(order, longExitLine);
        roundTrip.addTradeReference(order, shortEntryLine);
        
        File[] files = new File(partialDir).listFiles();
        assertEquals(0, files.length);
        
        generator.savePartial("123", roundTrip);
        
        files = new File(partialDir).listFiles();
        assertEquals(1, files.length);
        

        generator.roundTripMap.clear();
        assertTrue(generator.roundTripMap.isEmpty());
        
        generator.loadPartialRoundTrips();
        assertEquals(1, generator.roundTripMap.size());
        assertEquals(roundTrip, generator.roundTripMap.get("123"));
        
        generator.deletePartial("123");
        files = new File(partialDir).listFiles();
        assertEquals(0, files.length);
        
        
    }

    
    @Test
    public void testOrderEvent_NotFilled() {
        order.setCurrentStatus(OrderStatus.Status.NEW);
        OrderEvent orderEvent = new OrderEvent(order, new OrderStatus(OrderStatus.Status.NEW, "", "", new StockTicker("QQQ"), ZonedDateTime.now()));
        reportGenerator.orderEvent(orderEvent);
        
        verify(reportGenerator, never()).writeRoundTripToFile(any(RoundTrip.class));
    }
    
    @Test
    public void testOrderEvent_FirstRoundTrip() {
        TradeReferenceLine tradeReferenceLine = new TradeReferenceLine();
        tradeReferenceLine.correlationId = "123";
        order.setCurrentStatus(OrderStatus.Status.FILLED);
        OrderEvent orderEvent = new OrderEvent(order, new OrderStatus(OrderStatus.Status.NEW, "", "", new StockTicker("QQQ"), ZonedDateTime.now()));
        
        doReturn(tradeReferenceLine).when(reportGenerator).getTradeReferenceLine(any(String.class));
        assertTrue(reportGenerator.roundTripMap.isEmpty());
        
        try {
            reportGenerator.orderEvent(orderEvent);
            fail();
        } catch (IllegalStateException ex) {
            //this should happen
        }
        
        verify(reportGenerator, never()).writeRoundTripToFile(any(RoundTrip.class));
        assertEquals(1, reportGenerator.roundTripMap.size());
    }
    
    @Test
    public void testOrderEvent_RoundTripExists_ButNotComplete() {
        RoundTrip roundTrip = new RoundTrip();
        TradeReferenceLine tradeReferenceLine = new TradeReferenceLine();
        tradeReferenceLine.correlationId = "123";
        tradeReferenceLine.direction = TradeReferenceLine.Direction.LONG;
        tradeReferenceLine.side = TradeReferenceLine.Side.ENTRY;
        roundTrip.addTradeReference(order, tradeReferenceLine);
        
        order.setCurrentStatus(OrderStatus.Status.FILLED);
        OrderEvent orderEvent = new OrderEvent(order, new OrderStatus(OrderStatus.Status.NEW, "", "", new StockTicker("QQQ"), ZonedDateTime.now()));
        
        TradeReferenceLine longExitLine = new TradeReferenceLine();
        longExitLine.correlationId = "123";
        longExitLine.direction = TradeReferenceLine.Direction.LONG;
        longExitLine.side = TradeReferenceLine.Side.EXIT;
        
        reportGenerator.roundTripMap.put("123", roundTrip);
        
        doReturn(longExitLine).when(reportGenerator).getTradeReferenceLine(any(String.class));
        assertEquals(1, reportGenerator.roundTripMap.size());
        
        try {
            reportGenerator.orderEvent(orderEvent);
            fail();
        } catch (IllegalStateException ex) {
            //this should happen
        }
        
        verify(reportGenerator, never()).writeRoundTripToFile(any(RoundTrip.class));
        
        assertEquals(1, reportGenerator.roundTripMap.size());
    }    
    
    @Test
    public void testOrderEvent_RoundTripComplete() {
        RoundTrip roundTrip = new RoundTrip();
        TradeReferenceLine tradeReferenceLine = buildReferenceLine("123", LONG, ENTRY);
        tradeReferenceLine.correlationId = "123";
        tradeReferenceLine.direction = TradeReferenceLine.Direction.LONG;
        tradeReferenceLine.side = TradeReferenceLine.Side.ENTRY;
        roundTrip.addTradeReference(order, tradeReferenceLine);
        
        order.setCurrentStatus(OrderStatus.Status.FILLED);
        OrderEvent orderEvent = new OrderEvent(order, new OrderStatus(OrderStatus.Status.NEW, "", "", new StockTicker("QQQ"), ZonedDateTime.now()));
        
        TradeReferenceLine longExitLine = buildReferenceLine("123", LONG, EXIT);
        TradeReferenceLine shortEntryLine = buildReferenceLine("123", SHORT, ENTRY);
        TradeReferenceLine shortExitLine = buildReferenceLine("123", SHORT, EXIT);
        
        roundTrip.addTradeReference(order, longExitLine);
        roundTrip.addTradeReference(order, shortEntryLine);
        
        reportGenerator.roundTripMap.put("123", roundTrip);
        
        doReturn(shortExitLine).when(reportGenerator).getTradeReferenceLine(any(String.class));
        doNothing().when(reportGenerator).writeRoundTripToFile(any(RoundTrip.class));
        assertEquals(1, reportGenerator.roundTripMap.size());
        
        reportGenerator.orderEvent(orderEvent);
        
        verify(reportGenerator).writeRoundTripToFile(roundTrip);
        assertTrue(reportGenerator.roundTripMap.isEmpty());
        
    }   
    
    
    @Test
    public void testWriteRoundTrip() throws Exception{
        Path path = Files.createTempFile("ReportGeneratorUnitTest", ".txt");
        reportGenerator.outputFile = path.toString();
        String expected = "2016-03-19T07:01:10,ABC,100,100.23,0,2016-03-20T06:01:10,101.23,0,XYZ,50,250.34,0,251.34,0";
        
        Ticker longTicker = new StockTicker("ABC");
        Ticker shortTicker = new StockTicker("XYZ");
        int longSize = 100;
        int shortSize = 50;
        double longEntryFillPrice = 100.23;
        double longExitFillPrice = 101.23;
        double shortEntryFillPrice = 250.34;
        double shortExitFillPrice = 251.34;
        ZonedDateTime entryTime = ZonedDateTime.of(2016, 3, 19, 7, 1, 10, 0, ZoneId.systemDefault());        
        ZonedDateTime exitTime = ZonedDateTime.of(2016, 3, 20, 6, 1, 10, 0, ZoneId.systemDefault());
        
        TradeOrder longEntry = new TradeOrder("123", longTicker, longSize, TradeDirection.BUY);
        longEntry.setFilledPrice(longEntryFillPrice);
        longEntry.setOrderEntryTime(entryTime);
        
        TradeOrder longExit = new TradeOrder("123", longTicker, longSize, TradeDirection.SELL);
        longExit.setFilledPrice(longExitFillPrice);
        longExit.setOrderEntryTime(exitTime);
        
        TradeOrder shortEntry = new TradeOrder("123", shortTicker, shortSize, TradeDirection.SELL);
        shortEntry.setFilledPrice(shortEntryFillPrice);
        shortEntry.setOrderEntryTime(entryTime);
        
        TradeOrder shortExit = new TradeOrder("123", shortTicker, shortSize, TradeDirection.BUY);
        shortExit.setFilledPrice(shortExitFillPrice);
        shortExit.setOrderEntryTime(exitTime);
        
        RoundTrip roundTrip = new RoundTrip();
        roundTrip.longEntry = longEntry;
        roundTrip.longExit = longExit;
        roundTrip.shortEntry = shortEntry;
        roundTrip.shortExit = shortExit;
        
        System.out.println("Writing out to file: " + path);
        
        
        
        
        reportGenerator.writeRoundTripToFile(roundTrip);
        
        List<String> lines = Files.readAllLines(path);
        assertEquals(1, lines.size());
        assertEquals(expected, lines.get(0));
        
        Files.deleteIfExists(path);
        
    }
    
    protected TradeReferenceLine buildReferenceLine(String id, Direction direction, Side side) {
        TradeReferenceLine line = new TradeReferenceLine();
        line.correlationId = id;
        line.direction = direction;
        line.side = side;
        
        return line;
    }
    
}
