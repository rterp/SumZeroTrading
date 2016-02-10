/**
 * MIT License

Copyright (c) 2015  Rob Terpilowski

Permission is hereby granted, free of charge, to any person obtaining a copy of this software 
and associated documentation files (the "Software"), to deal in the Software without restriction, 
including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, 
and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, 
subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING 
BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. 
IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, 
WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE 
OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 
*/

package com.sumzerotrading.marketdata;

import java.math.BigDecimal;
import junit.framework.TestCase;
import org.junit.Test;

/**
 *
 * @author Rob Terpilowski
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
