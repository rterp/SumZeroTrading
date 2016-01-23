/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.limituptrading.marketdata;

import java.math.BigDecimal;
import junit.framework.TestCase;
import org.junit.Test;

/**
 *
 * @author robbob
 */
public class MarketDepthLevelTest extends TestCase {

    public MarketDepthLevelTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    @Test
    public void testCompareBidLevels() {
        MarketDepthLevel bidLevel1 = new MarketDepthLevel(MarketDepthBook.Side.BID, BigDecimal.ONE, BigDecimal.ZERO);
        MarketDepthLevel bidLevel2 = new MarketDepthLevel(MarketDepthBook.Side.BID, BigDecimal.ZERO, BigDecimal.ZERO);

        assertEquals(-1, bidLevel1.compareTo(bidLevel2));
    }

    @Test
    public void testCompareBidLevels_secondHigher() {
        MarketDepthLevel bidLevel1 = new MarketDepthLevel(MarketDepthBook.Side.BID, BigDecimal.ZERO, BigDecimal.ZERO);
        MarketDepthLevel bidLevel2 = new MarketDepthLevel(MarketDepthBook.Side.BID, BigDecimal.ONE, BigDecimal.ZERO);

        assertEquals(1, bidLevel1.compareTo(bidLevel2));
    }

    @Test
    public void testCompareAskLevels() {
        MarketDepthLevel askLevel1 = new MarketDepthLevel(MarketDepthBook.Side.ASK, BigDecimal.ONE, BigDecimal.ZERO);
        MarketDepthLevel askLevel2 = new MarketDepthLevel(MarketDepthBook.Side.ASK, BigDecimal.ZERO, BigDecimal.ZERO);

        assertEquals(1, askLevel1.compareTo(askLevel2));
    }

    @Test
    public void testCompareAskLevels_secondLower() {
        MarketDepthLevel askLevel1 = new MarketDepthLevel(MarketDepthBook.Side.ASK, BigDecimal.ZERO, BigDecimal.ZERO);
        MarketDepthLevel askLevel2 = new MarketDepthLevel(MarketDepthBook.Side.ASK, BigDecimal.ONE, BigDecimal.ZERO);

        assertEquals(-1, askLevel1.compareTo(askLevel2));
    }
}
