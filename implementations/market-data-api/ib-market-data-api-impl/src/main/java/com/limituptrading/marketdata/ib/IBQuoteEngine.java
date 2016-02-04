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


package com.zerosumtrading.marketdata.ib;

import com.ib.client.ClientSocketInterface;
import com.ib.client.Contract;
import com.zerosumtrading.data.Ticker;
import com.zerosumtrading.ib.ContractBuilderFactory;
import com.zerosumtrading.ib.IBConnectionInterface;
import com.zerosumtrading.ib.IBDataQueue;
import com.zerosumtrading.ib.IBSocket;
import com.zerosumtrading.ib.MarketDepthListener;
import com.zerosumtrading.ib.TickListener;
import com.zerosumtrading.marketdata.*;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
//import org.apache.log4j.Logger;

/**
 *
 * @author Rob Terpilowski
 */
public class IBQuoteEngine extends QuoteEngine implements TickListener, MarketDepthListener {

    protected IBDataQueue dataQueue;
    protected IBSocket ibSocket;
    protected IBConnectionInterface callbackInterface;
    protected BlockingQueue<Level1QuoteData> level1QuoteQueue = new LinkedBlockingQueue<Level1QuoteData>();
    protected BlockingQueue<Level2QuoteData> level2QuoteQueue = new LinkedBlockingQueue<Level2QuoteData>();
    protected Map<Ticker, ILevel1Quote> closeQuoteMap = new HashMap<>();
    protected Map<Ticker, ILevel1Quote> openQuoteMap = new HashMap<>();
    protected BlockingQueue<QuoteError> quoteErrorQueue = new LinkedBlockingQueue<QuoteError>();
    protected ClientSocketInterface ibConnection;
    protected Map<Ticker, Integer> tickerMap = new HashMap<Ticker, Integer>();
    protected Map<Integer, Ticker> idToTickerMap = new HashMap<Integer, Ticker>();
    protected Map<Ticker, Integer> level2TickerMap = new HashMap<Ticker, Integer>();
    protected Map<Integer, Ticker> level2IdToTickerMap = new HashMap<Integer, Ticker>();
    protected int nextQuoteId = 1;
    protected IBQuoteProcessor level1QuoteProcessor;
    protected IBQuoteProcessor level2QuoteProcessor;
    protected IBQuoteProcessor errorQuoteProcessor;
    //  protected Logger logger = Logger.getLogger(IBQuoteEngine.class);
    protected boolean started = false;
    protected String name = "";

    public IBQuoteEngine(IBSocket ibSocket) {
        this.ibSocket = ibSocket;

        callbackInterface = ibSocket.getConnection();
        callbackInterface.addTickListener(this);
        callbackInterface.addMarketDepthListener(this);
        ibConnection = ibSocket.getClientSocket();
        level1QuoteProcessor = new IBLevel1QuoteProcessor(level1QuoteQueue, this);
        level2QuoteProcessor = new IBLevel2QuoteProcessor(level2QuoteQueue, this);
        errorQuoteProcessor = new IBQuoteErrorProcessor(quoteErrorQueue, this);
    }

    @Override
    public boolean isConnected() {
        return ibSocket.isConnected();
    }

    public void tickSize(int tickerId, int field, int size) {
        buildLevel1QuoteDataAndSend(tickerId, field, 0, 0, size);
    }

    public void tickPrice(int tickerId, int field, double price, int canAutoExecute) {
        buildLevel1QuoteDataAndSend(tickerId, field, price, canAutoExecute, 0);
    }

    protected void buildLevel1QuoteDataAndSend(int tickerId, int field, double price, int canAutoExecute, int size) {
        Ticker ticker = idToTickerMap.get(tickerId);

        if (ticker != null) {
            Level1QuoteData level1Data = new Level1QuoteData(ticker, field, price, canAutoExecute, size);
            try {
                level1QuoteQueue.put(level1Data);
            } catch (InterruptedException ex) {
                //logger.error(ex, ex);
                ex.printStackTrace();
            }
        } else {
            //logger.error("Ticker with id: " + tickerId + " not found");
            System.out.println("Ticker with id: " + tickerId + " not found");
        }
    }

    public void updateMktDepth(int tickerId, int position, int operation, int side, double price, int size) {
        Ticker ticker = level2IdToTickerMap.get(tickerId);
        if (ticker != null) {
            Level2QuoteData data = new Level2QuoteData(ticker, position, operation, side, price, size);
            try {
                level2QuoteQueue.put(data);
            } catch (InterruptedException ex) {
                //logger.error(ex, ex);
                ex.printStackTrace();
            }
        } else {
            //logger.error("Ticker with id: " + tickerId + " not found");
            System.out.println("Ticker with id: " + tickerId + " not found");
        }
    }

    public void updateMktDepth2(int tickerId, int position, String marketMaker, int operation, int side, double price, int size) {
        System.out.println("");
    }

    public void error(Exception e) {
        putOnErrorQueue(new QuoteError(e));
    }

    public void error(String str) {
        putOnErrorQueue(new QuoteError(str));
    }

    public void error(int id, int errorCode, String errorMsg) {
        putOnErrorQueue(new QuoteError(id, errorCode, errorMsg));
    }

    protected void putOnErrorQueue(QuoteError error) {
        try {
            quoteErrorQueue.put(error);
        } catch (Exception ex) {
            //logger.error(ex, ex);
            ex.printStackTrace();
        }
    }

    @Override
    public void subscribeMarketDepth(Ticker ticker, Level2QuoteListener listener) {
        super.subscribeMarketDepth(ticker, listener);
        Integer quoteId = level2TickerMap.get(ticker);
        if (quoteId == null) {
            quoteId = ++nextQuoteId;
            level2TickerMap.put(ticker, quoteId);
            level2IdToTickerMap.put(quoteId, ticker);
            Contract contract = ContractBuilderFactory.getContractBuilder(ticker).buildContract(ticker);
            ibConnection.reqMktDepth(quoteId, contract, 100);

        }
    }

    @Override
    public void unsubscribeMarketDepth(Ticker ticker, Level2QuoteListener listener) {
        super.unsubscribeMarketDepth(ticker, listener);
        //if there are no more listeners cancel the subscription
        List<Level2QuoteListener> listeners = level2ListenerMap.get(ticker);
        if ((listeners == null) || (listeners.isEmpty())) {
            Integer requestId = level2TickerMap.remove(ticker);
            level2IdToTickerMap.remove(requestId);
            if (requestId != null) {
                ibConnection.cancelMktDepth(requestId);
            }
        }
    }

    @Override
    public synchronized void subscribeLevel1(Ticker ticker, Level1QuoteListener listener) {
        super.subscribeLevel1(ticker, listener);
        Integer quoteId = tickerMap.get(ticker);
        if (quoteId == null) {
            quoteId = ++nextQuoteId;
            tickerMap.put(ticker, quoteId);
            idToTickerMap.put(quoteId, ticker);
            Contract contract = ContractBuilderFactory.getContractBuilder(ticker).buildContract(ticker);
            ibConnection.reqMktData(nextQuoteId, contract, "", false);
        }

        if (closeQuoteMap.get(ticker) != null) {
            listener.quoteRecieved(closeQuoteMap.get(ticker));
        }

        if (openQuoteMap.get(ticker) != null) {
            listener.quoteRecieved(openQuoteMap.get(ticker));
        }

    }

    @Override
    public void unsubscribeLevel1(Ticker ticker, Level1QuoteListener listener) {
        super.unsubscribeLevel1(ticker, listener);

        //if there are no more listeners cancel the subscription
        List<Level1QuoteListener> listeners = level1ListenerMap.get(ticker);
        if ((listeners == null) || (listeners.isEmpty())) {
            Integer requestId = tickerMap.remove(ticker);
            idToTickerMap.remove(requestId);
            if (requestId != null) {
                ibConnection.cancelMktData(requestId);
            }
        }
    }

    public Date getServerTime() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void startEngine(Properties props) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void stopEngine() {
    }

    public void startEngine() {
        if (!started) {
            ibSocket.connect();
            level1QuoteProcessor.startProcessor();
            level2QuoteProcessor.startProcessor();
            errorQuoteProcessor.startProcessor();
            started = true;
        }
    }

    public boolean started() {
        return ibSocket.isConnected();
    }

    public IBSocket getIbSocket() {
        return ibSocket;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void fireLevel1Quote(ILevel1Quote quote) {
        super.fireLevel1Quote(quote); 
        if (quote.getType() == QuoteType.CLOSE) {
            closeQuoteMap.put(quote.getTicker(), quote);
        } else if (quote.getType() == QuoteType.OPEN) {
            openQuoteMap.put(quote.getTicker(), quote);
        }
    }

    protected Map<Ticker, List<Level1QuoteListener>> getLevel1ListenerMap() {
        return level1ListenerMap;
    }

    protected void setLevel1ListenerMap(Map<Ticker, List<Level1QuoteListener>> map) {
        this.level1ListenerMap = map;
    }
}
