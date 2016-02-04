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

package com.zerosumtrading.interactive.brokers.client;

import com.zerosumtrading.data.Ticker;
import com.zerosumtrading.ib.IBConnectionUtil;
import com.zerosumtrading.ib.IBSocket;
import com.zerosumtrading.marketdata.Level1QuoteListener;
import com.zerosumtrading.marketdata.Level2QuoteListener;
import com.zerosumtrading.marketdata.QuoteEngine;
import com.zerosumtrading.marketdata.ib.IBQuoteEngine;

/**
 *
 * @author RobTerpilowski
 */
public class InteractiveBrokersClient {
    
    protected String host;
    protected int port;
    protected int clientId;
    protected IBSocket ibSocket;
    protected IBQuoteEngine quoteEngine;

    public InteractiveBrokersClient(String host, int port, int clientId) {
        this.host = host;
        this.port = port;
        this.clientId = clientId;
        IBConnectionUtil util = new IBConnectionUtil(host, port, clientId);
        ibSocket = util.getIBSocket();
        quoteEngine = new IBQuoteEngine(ibSocket);
    }


    public void connect() {
        ibSocket.connect();
        quoteEngine.startEngine();
    }
    
    
    public void subscribeLevel1( Ticker ticker, Level1QuoteListener listener ) {
        quoteEngine.subscribeLevel1(ticker, listener);
    }
    
    public void subscribeMarketDepth( Ticker ticker, Level2QuoteListener listener ) {
        quoteEngine.subscribeMarketDepth(ticker, listener);
    }
    
    public void unsubscribeLevel1( Ticker ticker, Level1QuoteListener listener ) {
        quoteEngine.unsubscribeLevel1(ticker, listener);
    }
    
    public void unsubscribeMarketDepth( Ticker ticker, Level2QuoteListener listener ) {
        quoteEngine.unsubscribeMarketDepth(ticker, listener);
    }
    
    
    public QuoteEngine getQuoteEngine() {
        return quoteEngine;
    }
    
    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public int getClientId() {
        return clientId;
    }
    
    
    
    
    
}
