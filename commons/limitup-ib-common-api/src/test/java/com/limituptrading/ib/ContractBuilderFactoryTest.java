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

package com.limituptrading.ib;

import com.limituptrading.data.ComboTicker;
import com.limituptrading.data.CurrencyTicker;
import com.limituptrading.data.FuturesTicker;
import com.limituptrading.data.IndexTicker;
import com.limituptrading.data.OptionTicker;
import com.limituptrading.data.StockTicker;
import com.limituptrading.data.Ticker;
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
public class ContractBuilderFactoryTest {
    
    public ContractBuilderFactoryTest() {
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
    public void testGetContractBuilder() {
        Ticker stockTicker = new StockTicker("ABC");
        Ticker currencyTicker = new CurrencyTicker();
        Ticker futurTicker = new FuturesTicker();
        Ticker optionsTicker = new OptionTicker("ABC", 1, 2014);
        Ticker combTicker = new ComboTicker(futurTicker, stockTicker);
        Ticker indexTicker = new IndexTicker();
        
        assertTrue( ContractBuilderFactory.getContractBuilder(stockTicker) instanceof StockContractBuilder );
        assertTrue( ContractBuilderFactory.getContractBuilder(currencyTicker) instanceof CurrencyContractBuilder );
        assertTrue( ContractBuilderFactory.getContractBuilder(futurTicker) instanceof FuturesContractBuilder );
        assertTrue( ContractBuilderFactory.getContractBuilder(combTicker) instanceof ComboContractBuilder );
        assertTrue( ContractBuilderFactory.getContractBuilder(indexTicker) instanceof IndexContractBuilder );
        
    }
    
   
    
    @Test
    public void testGetContractBuilder_UnknownType() {
        Ticker ticker = new OptionTicker("", 1, 1);
        try {
            ContractBuilderFactory.getContractBuilder(ticker);
            fail();
        } catch( IllegalStateException ex ) {
            //this should happen.
        }
    }

}
