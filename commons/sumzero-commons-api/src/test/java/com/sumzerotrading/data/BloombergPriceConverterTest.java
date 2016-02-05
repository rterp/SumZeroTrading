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
public class BloombergPriceConverterTest {
    
    public BloombergPriceConverterTest() {
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
    public void testGetMultiplier() {
        
        checkValue(100.0, CANADIAN_DOLLAR_GLOBEX);
        checkValue(100.0, COPPER_NYMEX);
        checkValue(100.0, HEATING_OIL_NYMEX );
        checkValue(100.0, SWISS_FRANC_GLOBEX );
        checkValue(10000.0, JAPANESE_YEN_GLOBEX );
        checkValue(1.0, CORN_ECBOT );
        checkValue(1.0, CRUDE_OIL_NYMEX );
        checkValue(1.0, DOW_INDEX_MINI_ECBOT );
        checkValue(1.0, EURO_GLOBEX );
        checkValue(1.0, GOLD_NYMEX );
        checkValue(1.0, NASDAQ100_INDEX_MINI_GLOBEX );
        checkValue(1.0, NATURAL_GAS_NYMEX );
        checkValue(1.0, SILVER_NYMEX );
        checkValue(1.0, SOYBEANS_ECBOT );
        checkValue(1.0, BOND_30_YEAR_ECBOT );
        checkValue(1.0, BOND_10_YEAR_ECBOT );
        checkValue(1.0, WHEAT_ECBOT );
        
        
        try {
            BloombergPriceConverter.getMultiplier(null);
            fail();
        } catch( IllegalStateException ex ) {
            //this should happen.
        }
        
        
    }
    
    
    @Test
    public void testNativeToBloomberg() {
        assertEquals(109.000, BloombergPriceConverter.nativeToBloomberg(1.0900, SWISS_FRANC_GLOBEX),0.00001);
        
    }
    
    @Test
    public void testBloombergToNative() {
        assertEquals(1.0900, BloombergPriceConverter.bloombergToNative(109.000, SWISS_FRANC_GLOBEX), 0.00001);
    }
    
    @Test
    public void testNativeToBloomberg6J() {
        assertEquals(85.00, BloombergPriceConverter.nativeToBloomberg(0.0085, JAPANESE_YEN_GLOBEX ), 0.0  );
    }
    
    @Test
    public void testBloombergToNative6J() {
        assertEquals(0.0085, BloombergPriceConverter.bloombergToNative(85.00, JAPANESE_YEN_GLOBEX), 0.0);
    }
    
    protected void checkValue( double value, Commodity commodity ) {
        assertEquals(value, BloombergPriceConverter.getMultiplier(commodity), 0 );
    }
    
}
