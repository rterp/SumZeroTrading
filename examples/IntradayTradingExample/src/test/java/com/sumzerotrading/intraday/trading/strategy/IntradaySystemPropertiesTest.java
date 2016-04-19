/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sumzerotrading.intraday.trading.strategy;

import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
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
public class IntradaySystemPropertiesTest {

    public IntradaySystemPropertiesTest() {
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
    public void testGetFilenameConstructor() throws Exception {
        Path path = Paths.get(getClass().getResource("/intraday.test.properties").toURI());
        IntradaySystemProperties props = new IntradaySystemProperties(path.toString());
        assertEquals(getExpected(), props);
    }

    @Test
    public void testInputStreamConstructor() throws Exception {
        InputStream input = getClass().getResourceAsStream("/intraday.test.properties");
        IntradaySystemProperties props = new IntradaySystemProperties(input);

        assertEquals(getExpected(), props);
    }

    @Test
    public void testParsePropsAndGetMethods() throws Exception {
        IntradaySystemProperties expected = getExpected();
        IntradaySystemProperties actual = new IntradaySystemProperties(getClass().getResourceAsStream("/intraday.test.properties"));

        assertEquals(expected, actual);
        assertEquals(expected.getLongExitTime(), expected.longExitTime);
        assertEquals(expected.getLongStartTime(), expected.longStartTime);
        assertEquals(expected.getLongStopTime(), expected.longStopTime);
        assertEquals(expected.getShortExitTime(), expected.shortExitTime);
        assertEquals(expected.getShortStartTime(), expected.shortStartTime);
        assertEquals(expected.getShortStopTime(), expected.shortStopTime);
        assertEquals(expected.getStrategyDirectory(), expected.strategyDirectory);
        assertEquals(expected.getSystemStartTime(), expected.systemStartTime);
        assertEquals(expected.getTicker(), expected.ticker);
        assertEquals(expected.getTradeSizeDollars(), expected.tradeSizeDollars);
        assertEquals(expected.getTwsClientId(), expected.twsClientId);
        assertEquals(expected.getTwsHost(), expected.getTwsHost());
        assertEquals(expected.getTwsPort(), expected.getTwsPort());

    }

    protected IntradaySystemProperties getExpected() {
        IntradaySystemProperties expected = new IntradaySystemProperties();
        expected.longExitTime = LocalTime.of(5, 40, 0);
        expected.longStartTime = LocalTime.of(5, 38, 0);
        expected.longStopTime = LocalTime.of(5, 39, 0);
        expected.shortExitTime = LocalTime.of(5, 43, 0);
        expected.shortStartTime = LocalTime.of(5, 41, 0);
        expected.shortStopTime = LocalTime.of(5, 42, 0);
        expected.strategyDirectory = "/my/dir";
        expected.systemStartTime = LocalTime.of(5, 37, 0);
        expected.ticker = "XYZ";
        expected.tradeSizeDollars = 1000;
        expected.twsClientId = 10;
        expected.twsHost = "localhost";
        expected.twsPort = 9999;

        return expected;
    }
}
