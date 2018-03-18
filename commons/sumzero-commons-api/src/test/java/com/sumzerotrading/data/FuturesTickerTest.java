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


package com.sumzerotrading.data;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static com.sumzerotrading.data.Commodity.*;
import static org.junit.Assert.*;


/**
 *
 * @author RobTerpilowski
 */
public class FuturesTickerTest {

    public FuturesTickerTest() {
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
    public void testGetInstance_BySymbol() {
        FuturesTicker expected = new FuturesTicker();
        expected.setContractMultiplier( new BigDecimal(100000));
        expected.setDecimalFormat( Commodity.FOUR_DECIMALS );
        expected.setExchange(Exchange.GLOBEX);
        expected.setMinimumTickSize( new BigDecimal("0.0001"));
        expected.setSymbol("6C");
        expected.setCommodity(CANADIAN_DOLLAR_GLOBEX);
        
        assertEquals( expected, FuturesTicker.getInstance("6C") );
        System.out.println("Expected: " + expected);
        System.out.println("Actual: " + FuturesTicker.getInstance("6C"));
    }
    
    
    
    @Test
    public void testGetInstance() {
        int experiationMonth = 12;
        int expirationYear = 2014;
        FuturesTicker expected = new FuturesTicker();
        expected.setSymbol("ES");
        expected.setExpiryMonth(experiationMonth);
        expected.setExpiryYear(expirationYear);
        expected.setExchange(Exchange.GLOBEX);
        expected.setDecimalFormat( Commodity.TWO_DECIMALS );
        expected.setMinimumTickSize( new BigDecimal( 0.25 ));
        expected.setContractMultiplier( new BigDecimal(50) );
        expected.setCommodity(SP500_INDEX_MINI_GLOBEX);

        assertEquals(expected, FuturesTicker.getInstance(SP500_INDEX_MINI_GLOBEX, experiationMonth, expirationYear));
    }
    

    
   

    

}
