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


package com.limituptrading.marketdata.ib;

import com.ib.client.Contract;
import com.limituptrading.ib.ContractBuilderFactory;
import com.limituptrading.data.StockTicker;
import com.ib.client.ClientSocketInterface;
import com.limituptrading.data.Ticker;
import com.limituptrading.ib.IBConnectionInterface;
import com.limituptrading.ib.IBSocket;
import com.limituptrading.marketdata.ILevel1Quote;
import com.limituptrading.marketdata.Level1QuoteListener;
import com.limituptrading.marketdata.QuoteError;
import com.limituptrading.marketdata.QuoteType;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;
import org.junit.Ignore;
import org.mockito.Mockito;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 *
 * @author Rob Terpilowski
 */
public class IBQuoteEngineTest {

    protected Mockery mockery;
    protected IBConnectionInterface ibconnection;
    protected IBSocket socket;
    protected IBQuoteEngine ibQuoteEngine;

    public IBQuoteEngineTest() {
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
        ibconnection = Mockito.mock(IBConnectionInterface.class);
        socket = Mockito.mock(IBSocket.class);
        when(socket.getConnection()).thenReturn(ibconnection);

        ibQuoteEngine = new IBQuoteEngine(socket);
    }

    @After
    public void tearDown() {
    }

    @Test
    @Ignore
    public void testConstructor() {
        final ClientSocketInterface mockSocketInterface = mockery.mock(ClientSocketInterface.class);
        final IBConnectionInterface mockConnectionInterface = mockery.mock(IBConnectionInterface.class);

        mockery.checking(new Expectations() {
            {
                one(mockConnectionInterface).addTickListener(with(any(IBQuoteEngine.class)));
                one(mockConnectionInterface).addMarketDepthListener(with(any(IBQuoteEngine.class)));
            }
        });

//        IBQuoteEngine ibQuoteEngine = new IBQuoteEngine(mockSocketInterface, mockConnectionInterface);
//        mockery.assertIsSatisfied();
        //    
        //      assertEquals( mockSocketInterface, ibQuoteEngine.ibConnection );
        fail();
    }

    @Test
    @Ignore
    public void testTickPrice() throws Exception {
        final ClientSocketInterface mockSocketInterface = mockery.mock(ClientSocketInterface.class);
        final IBConnectionInterface mockConnectionInterface = mockery.mock(IBConnectionInterface.class);
        final int tickerId = 1;
        final int field = 2;
        final double price = 1.23;
        final int canAutoExecute = 1;
        final Ticker ticker = new StockTicker("LUT");

        mockery.checking(new Expectations() {
            {
                one(mockConnectionInterface).addTickListener(with(any(IBQuoteEngine.class)));
                one(mockConnectionInterface).addMarketDepthListener(with(any(IBQuoteEngine.class)));
            }
        });

//        IBQuoteEngine ibQuoteEngine = new IBQuoteEngine(mockSocketInterface, mockConnectionInterface);
//        
//        ibQuoteEngine.idToTickerMap.put(tickerId, ticker);
//        ibQuoteEngine.tickPrice(tickerId, field, price, canAutoExecute);
//        Level1QuoteData quote = ibQuoteEngine.level1QuoteQueue.take();
//        assertEquals( ticker, quote.getTicker() );
//        assertEquals( field, quote.getField() );
//        assertEquals( price, quote.getPrice(), 0 );
//        assertEquals( canAutoExecute, quote.getCanAutoExecute() );
//        assertEquals( 0, quote.getSize());
//        mockery.assertIsSatisfied();
        fail();
    }

    @Test
    @Ignore
    public void testTickPrice_NullTicker() throws Exception {
        final ClientSocketInterface mockSocketInterface = mockery.mock(ClientSocketInterface.class);
        final IBConnectionInterface mockConnectionInterface = mockery.mock(IBConnectionInterface.class);
        final int tickerId = 1;
        final int field = 99;
        final double price = 1.23;
        final int canAutoExecute = 1;

        mockery.checking(new Expectations() {
            {
                one(mockConnectionInterface).addTickListener(with(any(IBQuoteEngine.class)));
                one(mockConnectionInterface).addMarketDepthListener(with(any(IBQuoteEngine.class)));
            }
        });

//        IBQuoteEngine ibQuoteEngine = new IBQuoteEngine(mockSocketInterface, mockConnectionInterface);
//        
//        ibQuoteEngine.tickPrice(tickerId, field, price, canAutoExecute);
//        assertEquals( 0, ibQuoteEngine.level1QuoteQueue.size() );
//        mockery.assertIsSatisfied();
        fail();
    }

    @Test
    @Ignore
    public void testTickSize() throws Exception {
        final ClientSocketInterface mockSocketInterface = mockery.mock(ClientSocketInterface.class);
        final IBConnectionInterface mockConnectionInterface = mockery.mock(IBConnectionInterface.class);
        final int tickerId = 1;
        final int field = 2;
        final int size = 2;
        int expectedSize = 200;
        final int canAutoExecute = 1;
        final double price = 0;
        final Ticker ticker = new StockTicker("LUT");

        mockery.checking(new Expectations() {
            {
                one(mockConnectionInterface).addTickListener(with(any(IBQuoteEngine.class)));
                one(mockConnectionInterface).addMarketDepthListener(with(any(IBQuoteEngine.class)));
            }
        });

//        IBQuoteEngine ibQuoteEngine = new IBQuoteEngine(mockSocketInterface, mockConnectionInterface);
//        
//        ibQuoteEngine.idToTickerMap.put(tickerId, ticker);
//        ibQuoteEngine.tickSize(tickerId, field, size);
//        Level1QuoteData quote = ibQuoteEngine.level1QuoteQueue.take();
//        assertEquals( ticker, quote.getTicker() );
//        assertEquals( field, quote.getField() );
//        assertEquals( price, quote.getPrice(), 0 );
//        assertEquals( 0, quote.getCanAutoExecute() );
//        assertEquals( size, quote.getSize() );
//        mockery.assertIsSatisfied();
        fail();
    }

    @Test
    @Ignore
    public void testTickSize_NullTicker() throws Exception {
        final ClientSocketInterface mockSocketInterface = mockery.mock(ClientSocketInterface.class);
        final IBConnectionInterface mockConnectionInterface = mockery.mock(IBConnectionInterface.class);
        final int tickerId = 1;
        final int field = 99;
        final int size = 5;

        mockery.checking(new Expectations() {
            {
                one(mockConnectionInterface).addTickListener(with(any(IBQuoteEngine.class)));
                one(mockConnectionInterface).addMarketDepthListener(with(any(IBQuoteEngine.class)));
            }
        });

//        IBQuoteEngine ibQuoteEngine = new IBQuoteEngine(mockSocketInterface, mockConnectionInterface);
//        
//        ibQuoteEngine.tickSize(tickerId, field, size);
//        assertEquals( 0, ibQuoteEngine.level1QuoteQueue.size() );
//       
//                
//        mockery.assertIsSatisfied();
        fail();
    }

    @Test
    @Ignore
    public void testOverloadedTickPrice() throws Exception {
        final ClientSocketInterface mockSocketInterface = mockery.mock(ClientSocketInterface.class);
        final IBConnectionInterface mockConnectionInterface = mockery.mock(IBConnectionInterface.class);
        final int tickerId = 1;
        final int field = 99;
        final int size = 5;
        final int canAutoExecute = 1;
        final double price = 4.00;
        Ticker ticker = new StockTicker("ABC");

        mockery.checking(new Expectations() {
            {
                one(mockConnectionInterface).addTickListener(with(any(IBQuoteEngine.class)));
                one(mockConnectionInterface).addMarketDepthListener(with(any(IBQuoteEngine.class)));
            }
        });

//        IBQuoteEngine ibQuoteEngine = new IBQuoteEngine(mockSocketInterface, mockConnectionInterface);
//        ibQuoteEngine.idToTickerMap.put(tickerId, ticker);
//        ibQuoteEngine.tickPrice(tickerId, field, price , 1);
//        assertEquals( 1, ibQuoteEngine.level1QuoteQueue.size() );
// Level1QuoteData data = ibQuoteEngine.level1QuoteQueue.take();
//        assertEquals( canAutoExecute, data.getCanAutoExecute() );
//        assertEquals( field, data.getField());
//        assertEquals( price, data.getPrice(), 0 );
//        assertEquals( 0, data.getSize() );
//        assertEquals( ticker, data.getTicker());
        fail();
    }

    @Test
    @Ignore
    public void testErrorException() throws Exception {
        final ClientSocketInterface mockSocketInterface = mockery.mock(ClientSocketInterface.class);
        final IBConnectionInterface mockConnectionInterface = mockery.mock(IBConnectionInterface.class);
        Exception e = new Exception("Bogus Exception");
        int id = 1;
        int code = 1;
        String message = "foo";
        QuoteError quoteError = new QuoteError(id, code, message);

        mockery.checking(new Expectations() {
            {
                one(mockConnectionInterface).addTickListener(with(any(IBQuoteEngine.class)));
                one(mockConnectionInterface).addMarketDepthListener(with(any(IBQuoteEngine.class)));
            }
        });

//        
//        IBQuoteEngine ibQuoteEngine = new IBQuoteEngine(mockSocketInterface, mockConnectionInterface);
//        ibQuoteEngine.error( id, code, message );
//        assertEquals( quoteError, ibQuoteEngine.quoteErrorQueue.take() );
//        assertEquals( 0, ibQuoteEngine.quoteErrorQueue.size() );
        fail();
    }

    @Test
    @Ignore
    public void testErrorString() throws Exception {
        final ClientSocketInterface mockSocketInterface = mockery.mock(ClientSocketInterface.class);
        final IBConnectionInterface mockConnectionInterface = mockery.mock(IBConnectionInterface.class);
        final String errorString = "Bogus Error";
        QuoteError quoteError = new QuoteError(errorString);

        mockery.checking(new Expectations() {
            {
                one(mockConnectionInterface).addTickListener(with(any(IBQuoteEngine.class)));
                one(mockConnectionInterface).addMarketDepthListener(with(any(IBQuoteEngine.class)));
            }
        });

//        
//        IBQuoteEngine ibQuoteEngine = new IBQuoteEngine(mockSocketInterface, mockConnectionInterface);
//        ibQuoteEngine.error( errorString );
//        assertEquals( quoteError, ibQuoteEngine.quoteErrorQueue.take() );
//        assertEquals( 0, ibQuoteEngine.quoteErrorQueue.size() );
        fail();
    }

    @Test
    @Ignore
    public void testErrorCode_Exception() throws Exception {
        final ClientSocketInterface mockSocketInterface = mockery.mock(ClientSocketInterface.class);
        final IBConnectionInterface mockConnectionInterface = mockery.mock(IBConnectionInterface.class);
        final int id = 1;
        final int errorCode = 2;
        Exception ex = new IllegalAccessException();
        QuoteError quoteError = new QuoteError(ex);

        mockery.checking(new Expectations() {
            {
                one(mockConnectionInterface).addTickListener(with(any(IBQuoteEngine.class)));
                one(mockConnectionInterface).addMarketDepthListener(with(any(IBQuoteEngine.class)));
            }
        });

//        
//        IBQuoteEngine ibQuoteEngine = new IBQuoteEngine(mockSocketInterface, mockConnectionInterface);
//        ibQuoteEngine.error( ex );
//        assertEquals( quoteError, ibQuoteEngine.quoteErrorQueue.take() );
//        assertEquals( 0, ibQuoteEngine.quoteErrorQueue.size() );        
        fail();

    }

    @Test
    @Ignore
    public void testSubscribeLevel1() {
        final ClientSocketInterface mockSocketInterface = mockery.mock(ClientSocketInterface.class);
        final IBConnectionInterface mockConnectionInterface = mockery.mock(IBConnectionInterface.class);
        final Level1QuoteListener mockQuoteListener = mockery.mock(Level1QuoteListener.class);
        final Ticker ticker = new StockTicker("LUT");
        final int requestId = 1;
        final Contract contract = ContractBuilderFactory.getContractBuilder(ticker).buildContract(ticker);

        mockery.checking(new Expectations() {
            {
                one(mockConnectionInterface).addTickListener(with(any(IBQuoteEngine.class)));
                one(mockConnectionInterface).addMarketDepthListener(with(any(IBQuoteEngine.class)));
                one(mockSocketInterface).reqMktData(requestId + 1, contract, "", false);
            }
        });

//        IBQuoteEngine quoteEngine = new IBQuoteEngine(mockSocketInterface, mockConnectionInterface);
//        quoteEngine.subscribeLevel1(ticker, mockQuoteListener);
//        assertEquals( (requestId+1), quoteEngine.nextQuoteId );
//        assertEquals( (Integer)(requestId+1), quoteEngine.tickerMap.get(ticker) );
//        assertEquals(  ticker, quoteEngine.idToTickerMap.get(requestId+1) );
//        mockery.assertIsSatisfied();
        fail();
    }

    @Test
    @Ignore
    public void testSubscribeLevel1_alreadySubscribed() {
        final ClientSocketInterface mockSocketInterface = mockery.mock(ClientSocketInterface.class);
        final IBConnectionInterface mockConnectionInterface = mockery.mock(IBConnectionInterface.class);
        final Level1QuoteListener mockQuoteListener = mockery.mock(Level1QuoteListener.class);
        final Ticker ticker = new StockTicker("LUT");
        final int requestId = 1;
        final Contract contract = ContractBuilderFactory.getContractBuilder(ticker).buildContract(ticker);

        mockery.checking(new Expectations() {
            {
                one(mockConnectionInterface).addTickListener(with(any(IBQuoteEngine.class)));
                one(mockConnectionInterface).addMarketDepthListener(with(any(IBQuoteEngine.class)));
            }
        });

//        IBQuoteEngine quoteEngine = new IBQuoteEngine(mockSocketInterface, mockConnectionInterface);
//        quoteEngine.tickerMap.put(ticker, requestId);
//        quoteEngine.subscribeLevel1(ticker, mockQuoteListener);
//        mockery.assertIsSatisfied();
        fail();
    }
    
    @Test
    public void testSubscribeLevel1_ClosePriceExists() {
        Level1QuoteListener listener = Mockito.mock(Level1QuoteListener.class);
        ILevel1Quote quote = Mockito.mock(ILevel1Quote.class);
        Ticker ticker = new StockTicker("abc");
        ibQuoteEngine.closeQuoteMap.put(ticker, quote);
        ibQuoteEngine.tickerMap.put(ticker, 1);
        
        ibQuoteEngine.subscribeLevel1(ticker, listener);
        verify(listener).quoteRecieved(quote);
    }
    
    @Test
    public void testSubscribeLevel1_OpenPriceExists() {
        Level1QuoteListener listener = Mockito.mock(Level1QuoteListener.class);
        ILevel1Quote quote = Mockito.mock(ILevel1Quote.class);
        Ticker ticker = new StockTicker("abc");
        ibQuoteEngine.openQuoteMap.put(ticker, quote);
        ibQuoteEngine.tickerMap.put(ticker, 1);
        
        ibQuoteEngine.subscribeLevel1(ticker, listener);
        verify(listener).quoteRecieved(quote);
    }    

    @Test
    @Ignore
    public void testUnsubscribeLevel1() {
        final ClientSocketInterface mockSocketInterface = mockery.mock(ClientSocketInterface.class);
        final IBConnectionInterface mockConnectionInterface = mockery.mock(IBConnectionInterface.class);
        final Level1QuoteListener mockQuoteListener = mockery.mock(Level1QuoteListener.class);
        final Ticker ticker = new StockTicker("LUT");
        final int requestId = 1;
        final Contract contract = ContractBuilderFactory.getContractBuilder(ticker).buildContract(ticker);

        mockery.checking(new Expectations() {
            {
                one(mockConnectionInterface).addTickListener(with(any(IBQuoteEngine.class)));
                one(mockConnectionInterface).addMarketDepthListener(with(any(IBQuoteEngine.class)));
                one(mockSocketInterface).cancelMktData(requestId);
            }
        });
//        
//        IBQuoteEngine quoteEngine = new IBQuoteEngine(mockSocketInterface, mockConnectionInterface);
//        quoteEngine.tickerMap.put(ticker, requestId);
//        quoteEngine.idToTickerMap.put(requestId, ticker);
//        quoteEngine.unsubscribeLevel1(ticker, mockQuoteListener);
//
//        assertNull( quoteEngine.tickerMap.get(ticker) );
//        assertNull( quoteEngine.idToTickerMap.get( requestId ) );
//
//        mockery.assertIsSatisfied();
        fail();
    }

    @Test
    @Ignore
    public void testUnsubscribeLevel1_NoRequestIdFound() {
        final ClientSocketInterface mockSocketInterface = mockery.mock(ClientSocketInterface.class);
        final IBConnectionInterface mockConnectionInterface = mockery.mock(IBConnectionInterface.class);
        final Level1QuoteListener mockQuoteListener = mockery.mock(Level1QuoteListener.class);
        final Ticker ticker = new StockTicker("LUT");
        final int requestId = 1;
        final Contract contract = ContractBuilderFactory.getContractBuilder(ticker).buildContract(ticker);

        mockery.checking(new Expectations() {
            {
                one(mockConnectionInterface).addTickListener(with(any(IBQuoteEngine.class)));
                one(mockConnectionInterface).addMarketDepthListener(with(any(IBQuoteEngine.class)));

            }
        });

//        IBQuoteEngine quoteEngine = new IBQuoteEngine(mockSocketInterface, mockConnectionInterface);
//        quoteEngine.unsubscribeLevel1(ticker, mockQuoteListener);
//
//        assertNull( quoteEngine.tickerMap.get(ticker) );
//        assertNull( quoteEngine.idToTickerMap.get( requestId ) );
//
//        mockery.assertIsSatisfied();
        fail();
    }

    @Test
    @Ignore
    public void testUnsubscribeLevel1_EmptyListenerList() {
        final ClientSocketInterface mockSocketInterface = mockery.mock(ClientSocketInterface.class);
        final IBConnectionInterface mockConnectionInterface = mockery.mock(IBConnectionInterface.class);
        final Level1QuoteListener mockQuoteListener = mockery.mock(Level1QuoteListener.class);
        final Ticker ticker = new StockTicker("LUT");
        final int requestId = 1;
        final Contract contract = ContractBuilderFactory.getContractBuilder(ticker).buildContract(ticker);

        mockery.checking(new Expectations() {
            {
                one(mockConnectionInterface).addTickListener(with(any(IBQuoteEngine.class)));
                one(mockConnectionInterface).addMarketDepthListener(with(any(IBQuoteEngine.class)));
                one(mockSocketInterface).cancelMktData(requestId);
            }
        });
//        
//        IBQuoteEngine quoteEngine = new IBQuoteEngine(mockSocketInterface, mockConnectionInterface);
//        quoteEngine.getLevel1ListenerMap().put(ticker, new ArrayList<Level1QuoteListener>() );
//        quoteEngine.tickerMap.put(ticker, requestId);
//        quoteEngine.unsubscribeLevel1(ticker, mockQuoteListener);
//
//        assertNull( quoteEngine.tickerMap.get(ticker) );
//        assertNull( quoteEngine.idToTickerMap.get( requestId ) );
//
//        mockery.assertIsSatisfied();
        fail();
    }

    @Test
    @Ignore
    public void testUnsubscribeLevel1_ListenerListNotEmpty() {
        final ClientSocketInterface mockSocketInterface = mockery.mock(ClientSocketInterface.class);
        final IBConnectionInterface mockConnectionInterface = mockery.mock(IBConnectionInterface.class);
        final Level1QuoteListener mockQuoteListener = mockery.mock(Level1QuoteListener.class);
        final Ticker ticker = new StockTicker("LUT");
        final int requestId = 1;
        final Contract contract = ContractBuilderFactory.getContractBuilder(ticker).buildContract(ticker);

        mockery.checking(new Expectations() {
            {
                one(mockConnectionInterface).addTickListener(with(any(IBQuoteEngine.class)));
                one(mockConnectionInterface).addMarketDepthListener(with(any(IBQuoteEngine.class)));
            }
        });

//        IBQuoteEngine quoteEngine = new IBQuoteEngine(mockSocketInterface, mockConnectionInterface);
//        List<Level1QuoteListener> listenerList = new ArrayList<Level1QuoteListener>();
//        quoteEngine.tickerMap.put(ticker, requestId);
//        quoteEngine.idToTickerMap.put(requestId, ticker);
//        listenerList.add( mockQuoteListener );
//        listenerList.add(mockQuoteListener);
//        
//        quoteEngine.getLevel1ListenerMap().put(ticker, listenerList );
//        quoteEngine.unsubscribeLevel1(ticker, mockQuoteListener);
//
//        
//        assertEquals( (Integer) requestId, quoteEngine.tickerMap.get(ticker) );
//        assertEquals( ticker, quoteEngine.idToTickerMap.get(requestId ) );
//        
//        mockery.assertIsSatisfied();
        fail();
    }

    @Test
    @Ignore
    public void testGetServerTime() {
        final ClientSocketInterface mockSocketInterface = mockery.mock(ClientSocketInterface.class);
        final IBConnectionInterface mockConnectionInterface = mockery.mock(IBConnectionInterface.class);

        mockery.checking(new Expectations() {
            {
                one(mockConnectionInterface).addTickListener(with(any(IBQuoteEngine.class)));
                one(mockConnectionInterface).addMarketDepthListener(with(any(IBQuoteEngine.class)));
            }
        });

//        IBQuoteEngine quoteEngine = new IBQuoteEngine(mockSocketInterface, mockConnectionInterface);
//        try {
//            quoteEngine.getServerTime();
//            fail();
//        } catch( UnsupportedOperationException ex ) {
//            //this should happen
//        }
//        mockery.assertIsSatisfied();
        fail();
    }

    @Test
    @Ignore
    public void testStartEngine() {
        final ClientSocketInterface mockSocketInterface = mockery.mock(ClientSocketInterface.class);
        final IBConnectionInterface mockConnectionInterface = mockery.mock(IBConnectionInterface.class);

        mockery.checking(new Expectations() {
            {
                one(mockConnectionInterface).addTickListener(with(any(IBQuoteEngine.class)));
                one(mockConnectionInterface).addMarketDepthListener(with(any(IBQuoteEngine.class)));
            }
        });

//        IBQuoteEngine quoteEngine = new IBQuoteEngine(mockSocketInterface, mockConnectionInterface);
//        try {
//            quoteEngine.startEngine(null);
//            fail();
//        } catch( UnsupportedOperationException ex ) {
//            //this should happen
//        }
//        mockery.assertIsSatisfied();
        fail();
    }

    @Test
    @Ignore
    public void testStartEngine_NoArgs() {
        final ClientSocketInterface mockSocketInterface = mockery.mock(ClientSocketInterface.class);
        final IBConnectionInterface mockConnectionInterface = mockery.mock(IBConnectionInterface.class);

        mockery.checking(new Expectations() {
            {
                one(mockConnectionInterface).addTickListener(with(any(IBQuoteEngine.class)));
                one(mockConnectionInterface).addMarketDepthListener(with(any(IBQuoteEngine.class)));
            }
        });
//        
//        IBQuoteEngine quoteEngine = new IBQuoteEngine(mockSocketInterface, mockConnectionInterface);
//        quoteEngine.startEngine();
//        assertTrue( quoteEngine.level1QuoteProcessor.isRunning() );
//        assertTrue( quoteEngine.level2QuoteProcessor.isRunning() );
//        assertTrue( quoteEngine.errorQuoteProcessor.isRunning() );
//        
//        mockery.assertIsSatisfied();
        fail();
    }

    @Test
    @Ignore
    public void testStopEngine() {
        final ClientSocketInterface mockSocketInterface = mockery.mock(ClientSocketInterface.class);
        final IBConnectionInterface mockConnectionInterface = mockery.mock(IBConnectionInterface.class);

        mockery.checking(new Expectations() {
            {
                one(mockConnectionInterface).addTickListener(with(any(IBQuoteEngine.class)));
                one(mockConnectionInterface).addMarketDepthListener(with(any(IBQuoteEngine.class)));
            }
        });

//        IBQuoteEngine quoteEngine = new IBQuoteEngine(mockSocketInterface, mockConnectionInterface);
//        try {
//            quoteEngine.stopEngine();
//            fail();
//        } catch( UnsupportedOperationException ex ) {
//            //this should happen
//        }
//        mockery.assertIsSatisfied();
        fail();
    }

    @Test
    @Ignore
    public void testStarted() {
        final ClientSocketInterface mockSocketInterface = mockery.mock(ClientSocketInterface.class);
        final IBConnectionInterface mockConnectionInterface = mockery.mock(IBConnectionInterface.class);

        mockery.checking(new Expectations() {
            {
                one(mockConnectionInterface).addTickListener(with(any(IBQuoteEngine.class)));
                one(mockConnectionInterface).addMarketDepthListener(with(any(IBQuoteEngine.class)));
                one(mockSocketInterface).isConnected();
                will(returnValue(true));
            }
        });

//        IBQuoteEngine quoteEngine = new IBQuoteEngine(mockSocketInterface, mockConnectionInterface);
//        assertTrue( quoteEngine.started() );
//        mockery.assertIsSatisfied();
        fail();
    }

    @Test
    public void testFireLevel1Quote_Close() {
        ILevel1Quote quote = Mockito.mock(ILevel1Quote.class);
        Ticker ticker = new StockTicker("abc");

        when(socket.getConnection()).thenReturn(ibconnection);
        when(quote.getType()).thenReturn(QuoteType.CLOSE);
        when(quote.getTicker()).thenReturn(ticker);

        ibQuoteEngine.fireLevel1Quote(quote);
        assertEquals(1, ibQuoteEngine.closeQuoteMap.size());
        assertEquals(quote, ibQuoteEngine.closeQuoteMap.get(ticker));
        assertTrue(ibQuoteEngine.openQuoteMap.isEmpty());
    }

    @Test
    public void testFireLevel1Quote_Open() {
        ILevel1Quote quote = Mockito.mock(ILevel1Quote.class);
        Ticker ticker = new StockTicker("abc");

        when(socket.getConnection()).thenReturn(ibconnection);
        when(quote.getType()).thenReturn(QuoteType.OPEN);
        when(quote.getTicker()).thenReturn(ticker);

        ibQuoteEngine.fireLevel1Quote(quote);
        assertEquals(1, ibQuoteEngine.openQuoteMap.size());
        assertEquals(quote, ibQuoteEngine.openQuoteMap.get(ticker));
        assertTrue(ibQuoteEngine.closeQuoteMap.isEmpty());
    }

}
