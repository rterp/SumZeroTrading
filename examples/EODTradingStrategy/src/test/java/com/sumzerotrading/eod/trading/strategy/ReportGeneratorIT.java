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
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.ZonedDateTime;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author RobTerpilowski
 */
public class ReportGeneratorIT {

    public ReportGeneratorIT() {
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
    public void testReportGenerator() throws Exception {
        StockTicker longTicker = new StockTicker("ABC");
        StockTicker shortTicker = new StockTicker("XYZ");

        String directory = System.getProperty("java.io.tmpdir");
        if (!directory.endsWith("/")) {
            directory += "/";
        }
        Files.deleteIfExists(Paths.get(directory + "report.csv"));
        System.out.println("Creating directory at: " + directory);
        ReportGenerator generator = new ReportGenerator(directory);

        TradeOrder longEntryOrder = new TradeOrder("123", longTicker, 100, TradeDirection.BUY);
        longEntryOrder.setFilledPrice(100.00);
        longEntryOrder.setReference("EOD-Pair-Strategy:guid-123:Entry:Long*");
        longEntryOrder.setCurrentStatus(OrderStatus.Status.FILLED);

        TradeOrder shortEntryOrder = new TradeOrder("234", shortTicker, 50, TradeDirection.SELL);
        shortEntryOrder.setFilledPrice(50.00);
        shortEntryOrder.setReference("EOD-Pair-Strategy:guid-123:Entry:Short*");
        shortEntryOrder.setCurrentStatus(OrderStatus.Status.FILLED);

        
        generator.orderEvent(new OrderEvent(longEntryOrder, null));
        generator.orderEvent(new OrderEvent(shortEntryOrder, null));

    }
}
