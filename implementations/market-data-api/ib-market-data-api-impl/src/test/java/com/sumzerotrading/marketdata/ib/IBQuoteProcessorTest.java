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
import com.sumzerotrading.marketdata.QuoteEngine;
import java.util.concurrent.BlockingQueue;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Rob Terpilowski
 */
public class IBQuoteProcessorTest {
    
    protected Mockery mockery;
    
    public IBQuoteProcessorTest() {
    }

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
    
    @After
    public void tearDown() {
    }

    
    @Test
    public void testConstructor() {
        BlockingQueue<Object> mockQueue = mockery.mock(BlockingQueue.class);
        IQuoteEngine mockQuoteEngine = mockery.mock( IQuoteEngine.class );
        
        IBQuoteProcessor<Object> quoteProcessor = new MockIBQuoteProcessor(mockQueue, mockQuoteEngine);
        assertEquals( mockQueue, quoteProcessor.quoteBlockingQueue );
        assertEquals( mockQuoteEngine, quoteProcessor.quoteEngine );
        assertFalse( quoteProcessor.isRunning() );
    }
    
    
    @Test
    public void testStartProcessor() throws Exception {
        final BlockingQueue<Object> mockQueue = mockery.mock(BlockingQueue.class);
        IQuoteEngine mockQuoteEngine = mockery.mock( IQuoteEngine.class );
        
        IBQuoteProcessor<Object> quoteProcessor = new MockIBQuoteProcessor(mockQueue, mockQuoteEngine);
       
        
        mockery.checking( new Expectations() {{
            allowing(mockQueue).take();
            will(returnValue( getNewObject() ));
        
        }} );
        
        
        quoteProcessor.startProcessor();
        assertTrue( quoteProcessor.isRunning() );
        
        quoteProcessor.stopProcessor();
        assertFalse( quoteProcessor.isRunning() );
    }
    
    
    protected Object getNewObject() {
        try {
            Thread.sleep(1000);
        } catch( Exception ex )  {
            
        }
            
        return new Object();
    }
    
    public static class MockIBQuoteProcessor extends IBQuoteProcessor<Object> {

        
        
        public MockIBQuoteProcessor(BlockingQueue<Object> queue, IQuoteEngine quoteEngine) {
            super(queue, quoteEngine);
        }

        
        
        @Override
        protected void processData(Object data) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
        
        
    }
}
