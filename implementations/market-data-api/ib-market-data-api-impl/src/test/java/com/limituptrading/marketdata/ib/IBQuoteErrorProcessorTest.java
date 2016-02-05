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


package com.sumzerotrading.marketdata.ib;

import com.sumzerotrading.marketdata.IQuoteEngine;
import com.sumzerotrading.marketdata.QuoteError;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.AfterClass;
import org.junit.Test;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 *
 * @author Rob Terpilowski
 */
public class IBQuoteErrorProcessorTest {
    protected Mockery mockery;
    
    public IBQuoteErrorProcessorTest() {
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
        @Before
    public void setUp() {
        mockery = new Mockery();
    }
            
            
    
    
    @Test
    public void testProcessError() {
        final IQuoteEngine mockQuoteEngine = mockery.mock( IQuoteEngine.class );
        IBQuoteErrorProcessor procesor = new IBQuoteErrorProcessor(null, mockQuoteEngine);
        final QuoteError error = new QuoteError("foo");
        
        mockery.checking( new Expectations() {{
            one(mockQuoteEngine).fireErrorEvent(error);
        }});
        
        
        procesor.processData(error);
        mockery.assertIsSatisfied();
        
    }
}
