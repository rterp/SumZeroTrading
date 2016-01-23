/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.limituptrading.marketdata;

import java.util.List;
import java.util.Map;
import com.limituptrading.data.Ticker;
import com.limituptrading.data.StockTicker;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author robbob
 */
public class QuoteEngineTest {
    
    Mockery mockery;
    
    public QuoteEngineTest() {
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
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    @Test
    public void testAddAndRemoveErrorListener() {
        QuoteEngine engine = createNewQuoteEngine();
        ErrorListener listener = new ErrorListener() {

            public void quoteEngineError(QuoteError error) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };
        
        
        engine.addErrorListener( listener );
        assertEquals( 1, engine.errorListeners.size() );
        
        engine.removeErrorListener(listener);
        assertEquals( 0, engine.errorListeners.size() );
    }
    
    
    @Test
    public void testFireErrorEvent() {
        QuoteEngine engine = createNewQuoteEngine();
        QuoteError error = new QuoteError( "foo" );
        final StringBuffer sb = new StringBuffer();
        
        ErrorListener listener = new ErrorListener() {

            public void quoteEngineError(QuoteError error) {
                sb.append( error.getMessage() );
            }
            
        };
        
        engine.addErrorListener(listener);
        engine.fireErrorEvent(error);
        assertEquals( "foo", sb.toString() );
        
    }
    
    
    @Test
    public void testSubscribeAndUnsubscribeLevel1() {
        QuoteEngine engine = createNewQuoteEngine();
        Level1QuoteListener listener1 = new Level1QuoteListener() {

            public void quoteRecieved(ILevel1Quote quote) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };
        
        Level1QuoteListener listener2 = new Level1QuoteListener() {

            public void quoteRecieved(ILevel1Quote quote) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };
        
        Ticker ticker = new StockTicker( "ABC" );
        
        assertEquals( 0, engine.level1ListenerMap.size() );
        
        engine.subscribeLevel1(ticker, listener1);
        assertEquals( 1, engine.level1ListenerMap.size() );
        assertEquals( 1, engine.level1ListenerMap.get(ticker).size() );
        
        engine.subscribeLevel1(ticker, listener2);
        assertEquals( 1, engine.level1ListenerMap.size() );
        assertEquals( 2, engine.level1ListenerMap.get(ticker).size() );
        
        engine.unsubscribeLevel1(ticker, listener2);
        assertEquals( 1, engine.level1ListenerMap.size() );
        assertEquals( 1, engine.level1ListenerMap.get(ticker).size() );        
        
        engine.unsubscribeLevel1(ticker, listener1);
        assertEquals( 1, engine.level1ListenerMap.size() );
        assertEquals( 0, engine.level1ListenerMap.get(ticker).size() );                
        
    }
    
    
    
    @Test
    public void testSubscribeAndUnsubscribeMarketDepth() {
        QuoteEngine engine = createNewQuoteEngine();
        Level2QuoteListener listener1 = new Level2QuoteListener() {

            public void level2QuoteReceived(ILevel2Quote level2Quote) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };
        
        Level2QuoteListener listener2 = new Level2QuoteListener() {

            public void level2QuoteReceived(ILevel2Quote level2Quote) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };
        
        
        Ticker ticker = new StockTicker( "ABC" );
        
        assertEquals( 0, engine.level1ListenerMap.size() );
        
        engine.subscribeMarketDepth(ticker, listener1);
        assertEquals( 1, engine.level2ListenerMap.size() );
        assertEquals( 1, engine.level2ListenerMap.get(ticker).size() );
        
        engine.subscribeMarketDepth(ticker, listener2);
        assertEquals( 1, engine.level2ListenerMap.size() );
        assertEquals( 2, engine.level2ListenerMap.get(ticker).size() );
        
        engine.unsubscribeMarketDepth(ticker, listener2);
        assertEquals( 1, engine.level2ListenerMap.size() );
        assertEquals( 1, engine.level2ListenerMap.get(ticker).size() );        
        
        engine.unsubscribeMarketDepth(ticker, listener1);
        assertEquals( 1, engine.level2ListenerMap.size() );
        assertEquals( 0, engine.level2ListenerMap.get(ticker).size() );               
    }
    
    
    @Test
    public void testFireLevel1Quote() {
        QuoteEngine quoteEngine = createNewQuoteEngine();
        final ILevel1Quote mockQuote = mockery.mock( ILevel1Quote.class );
        final Map mockLevel1ListenerMap = mockery.mock( Map.class );
        final Ticker ticker = new StockTicker( "ABC" );
        final Level1QuoteListener mockListener = mockery.mock( Level1QuoteListener.class );
        final List<Level1QuoteListener> mockListenerList = new ArrayList<Level1QuoteListener>();
        mockListenerList.add( mockListener );
        quoteEngine.level1ListenerMap = mockLevel1ListenerMap;
        
        
        mockery.checking( new Expectations() {{
            one(mockQuote).getTicker();
            will(returnValue(ticker));
            
            one(mockLevel1ListenerMap).get(ticker);
            will(returnValue(mockListenerList));
            
            one(mockListener).quoteRecieved(mockQuote);
            
        }});
        
        quoteEngine.fireLevel1Quote(mockQuote);
        mockery.assertIsSatisfied();
        
    }    
    
    
    @Test
    public void testFireLevel1Quote_ThrowsException() {
        QuoteEngine quoteEngine = createNewQuoteEngine();
        final ILevel1Quote mockQuote = mockery.mock( ILevel1Quote.class );
        final Map mockLevel1ListenerMap = mockery.mock( Map.class );
        final Ticker ticker = new StockTicker( "ABC" );
        final Level1QuoteListener mockListener = mockery.mock( Level1QuoteListener.class );
        final List<Level1QuoteListener> mockListenerList = new ArrayList<Level1QuoteListener>();
        mockListenerList.add( mockListener );
        quoteEngine.level1ListenerMap = mockLevel1ListenerMap;
        
        
        mockery.checking( new Expectations() {{
            one(mockQuote).getTicker();
            will(returnValue(ticker));
            
            one(mockLevel1ListenerMap).get(ticker);
            will(returnValue(mockListenerList));
            
            one(mockListener).quoteRecieved(mockQuote);
            will(throwException( new Exception("bogus") ) );
            
        }});
        
        try {
            quoteEngine.fireLevel1Quote(mockQuote);
        } catch( Exception ex ) {
            fail();
        }
        mockery.assertIsSatisfied();
        
    }        
    
    @Test
    public void testFireLevel1Quote_NoListeners() {
        QuoteEngine quoteEngine = createNewQuoteEngine();
        final ILevel1Quote mockQuote = mockery.mock( ILevel1Quote.class );
        final Map mockLevel1ListenerMap = mockery.mock( Map.class );
        final Ticker ticker = new StockTicker( "ABC" );
        quoteEngine.level1ListenerMap = mockLevel1ListenerMap;
        
        
        mockery.checking( new Expectations() {{
            one(mockQuote).getTicker();
            will(returnValue(ticker));
            
            one(mockLevel1ListenerMap).get(ticker);
            will(returnValue(null));
            
        }});
        
        quoteEngine.fireLevel1Quote(mockQuote);
        mockery.assertIsSatisfied();
        
    }
    
    
    
    @Test
    public void testFireMarketDepthQuote() {
        QuoteEngine quoteEngine = createNewQuoteEngine();
        final ILevel2Quote mockQuote = mockery.mock( ILevel2Quote.class );
        final Map mockLevel2ListenerMap = mockery.mock( Map.class );
        final Ticker ticker = new StockTicker( "ABC" );
        final Level2QuoteListener mockListener = mockery.mock( Level2QuoteListener.class );
        final List<Level2QuoteListener> mockListenerList = new ArrayList<Level2QuoteListener>();
        mockListenerList.add( mockListener );
        quoteEngine.level2ListenerMap = mockLevel2ListenerMap;
        
        
        mockery.checking( new Expectations() {{
            one(mockQuote).getTicker();
            will(returnValue(ticker));
            
            one(mockLevel2ListenerMap).get(ticker);
            will(returnValue(mockListenerList));
            
            one(mockListener).level2QuoteReceived(mockQuote);
            
        }});
        
        try {
            quoteEngine.fireMarketDepthQuote(mockQuote);
        } catch( Exception ex ) {
            fail();
        }
        mockery.assertIsSatisfied();
        
    }         
    
    
    @Test
    public void testFireMarketDepthQuote_ThrowsException() {
        QuoteEngine quoteEngine = createNewQuoteEngine();
        final ILevel2Quote mockQuote = mockery.mock( ILevel2Quote.class );
        final Map mockLevel2ListenerMap = mockery.mock( Map.class );
        final Ticker ticker = new StockTicker( "ABC" );
        final Level2QuoteListener mockListener = mockery.mock( Level2QuoteListener.class );
        final List<Level2QuoteListener> mockListenerList = new ArrayList<Level2QuoteListener>();
        mockListenerList.add( mockListener );
        quoteEngine.level2ListenerMap = mockLevel2ListenerMap;
        
        
        mockery.checking( new Expectations() {{
            one(mockQuote).getTicker();
            will(returnValue(ticker));
            
            one(mockLevel2ListenerMap).get(ticker);
            will(returnValue(mockListenerList));
            
            one(mockListener).level2QuoteReceived(mockQuote);
            will(throwException( new Exception("bogus") ) );
            
        }});
        
        try {
            quoteEngine.fireMarketDepthQuote(mockQuote);
        } catch( Exception ex ) {
            fail();
        }
        mockery.assertIsSatisfied();
        
    }            
    
    
    @Test
    public void testFireLevelMarketDepthQuote_NoListeners() {
        QuoteEngine quoteEngine = createNewQuoteEngine();
        final ILevel2Quote mockQuote = mockery.mock( ILevel2Quote.class );
        final Map mockLevel2ListenerMap = mockery.mock( Map.class );
        final Ticker ticker = new StockTicker( "ABC" );
        quoteEngine.level2ListenerMap = mockLevel2ListenerMap;
        
        
        mockery.checking( new Expectations() {{
            one(mockQuote).getTicker();
            will(returnValue(ticker));
            
            one(mockLevel2ListenerMap).get(ticker);
            will(returnValue(null));
            
        }});
        
        quoteEngine.fireMarketDepthQuote(mockQuote);
        mockery.assertIsSatisfied();
        
    }
        
    
    
    
    protected QuoteEngine createNewQuoteEngine() {
        return new QuoteEngine() {

            public boolean isConnected() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
            
            public void startEngine() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            public void startEngine(Properties props) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            public void stopEngine() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            public Date getServerTime() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            public boolean started() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };
    }


}
