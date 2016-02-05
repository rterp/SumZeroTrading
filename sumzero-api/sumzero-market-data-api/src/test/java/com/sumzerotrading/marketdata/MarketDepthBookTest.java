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
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Rob Terpilowski
 */
public class MarketDepthBookTest {
    
    public MarketDepthBookTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
	
    
    
    @Test
    public void testGetTotalSize() {
		MarketDepthBook book = new MarketDepthBook( );
		book.addLevel( new MarketDepthLevel( MarketDepthBook.Side.ASK, new BigDecimal("3.00"), 400 ) );
		book.addLevel( new MarketDepthLevel( MarketDepthBook.Side.ASK, new BigDecimal("3.01"), 1000 ) );
		book.addLevel( new MarketDepthLevel( MarketDepthBook.Side.ASK, new BigDecimal("3.02"), 200 ) );
		
		assertEquals( 1600, book.getTotalSize().intValue() );
		assertEquals( 400, book.getCumulativeSize(0).intValue() );
		assertEquals( 1400, book.getCumulativeSize(1).intValue() );
		assertEquals( 1600, book.getCumulativeSize(2).intValue() );
		assertEquals( 1600, book.getCumulativeSize(5).intValue() );
                assertEquals( 3, book.getLevelCount() );
                assertEquals( 3, book.getLevels().length );
                
                book.clearLevels();
                assertEquals( 0, book.getLevelCount() );
	}
    
    @Test
    public void testSetGetSide() {
        MarketDepthBook book = new MarketDepthBook();
        book.setSide(MarketDepthBook.Side.BID);
        
        assertEquals( MarketDepthBook.Side.BID, book.getSide() );
    }
    
    @Test
    public void testInsertLevel() {
        MarketDepthBook book = new MarketDepthBook();
        book.setSide(MarketDepthBook.Side.BID);
        MarketDepthLevel level = new MarketDepthLevel(MarketDepthBook.Side.BID, BigDecimal.ZERO, BigDecimal.ZERO);
        
        book.insertLevel(0, level);
        assertEquals( 1, book.getLevelCount() );
        assertEquals( level, book.getLevelAt(0));
        
        book.insertLevel(5, level);
        assertEquals( 1, book.getLevelCount() );
        assertEquals( level, book.getLevelAt(0));
    }
    
    @Test
    public void updateLevel() {
        MarketDepthBook book = new MarketDepthBook();
        book.setSide(MarketDepthBook.Side.BID);
        
        MarketDepthLevel level1 = new MarketDepthLevel(MarketDepthBook.Side.BID, BigDecimal.ZERO, BigDecimal.ZERO);
        MarketDepthLevel level2 = new MarketDepthLevel(MarketDepthBook.Side.BID, BigDecimal.ONE, BigDecimal.ZERO);
        MarketDepthLevel level3 = new MarketDepthLevel(MarketDepthBook.Side.BID, BigDecimal.ZERO, BigDecimal.ONE);
        
        book.addLevel(level1);
        book.addLevel(level2);
        book.updateLevel(0, level3);
        
        assertEquals( 2, book.getLevelCount() );
        assertEquals( level3, book.getLevelAt(0) );
        assertEquals( level2, book.getLevelAt(1));
        
        book.updateLevel(5, level3);
        assertEquals( 2, book.getLevelCount() );
        assertEquals( level3, book.getLevelAt(0) );
        assertEquals( level2, book.getLevelAt(1));        
        
    }
    
    @Test
    public void testDeleteLevel() {
        MarketDepthBook book = new MarketDepthBook();
        book.setSide(MarketDepthBook.Side.BID);
        
        MarketDepthLevel level1 = new MarketDepthLevel(MarketDepthBook.Side.BID, BigDecimal.ZERO, BigDecimal.ZERO);
        MarketDepthLevel level2 = new MarketDepthLevel(MarketDepthBook.Side.BID, BigDecimal.ONE, BigDecimal.ZERO);
        MarketDepthLevel level3 = new MarketDepthLevel(MarketDepthBook.Side.BID, BigDecimal.ZERO, BigDecimal.ONE);
        
        book.addLevel(level1);
        book.addLevel(level2);
        
        book.deleteLevel(2);
        assertEquals( 2, book.getLevelCount() );
        assertEquals( level1, book.getLevelAt(0));
        assertEquals( level2, book.getLevelAt(1));
        
        book.deleteLevel(0);
        assertEquals(1, book.getLevelCount());
        assertEquals( level2, book.getLevelAt(0));
        
        
    }
    
    
    @Test
    public void testSetLevels() {
        MarketDepthBook book = new MarketDepthBook();
        book.setSide(MarketDepthBook.Side.BID);
        
        MarketDepthLevel level1 = new MarketDepthLevel(MarketDepthBook.Side.BID, BigDecimal.ZERO, BigDecimal.ZERO);
        MarketDepthLevel level2 = new MarketDepthLevel(MarketDepthBook.Side.BID, BigDecimal.ONE, BigDecimal.ZERO);
        MarketDepthLevel level3 = new MarketDepthLevel(MarketDepthBook.Side.BID, BigDecimal.ZERO, BigDecimal.ONE);
        MarketDepthLevel[] levels = {level1, level2, level3};
        
        
        book.setLevels( levels );
        
        assertEquals( 3, book.getLevelCount() );
        assertEquals( level1, book.getLevelAt(0 ) );
        assertEquals( level2, book.getLevelAt(1));
        assertEquals( level3, book.getLevelAt(2));
    }
}
