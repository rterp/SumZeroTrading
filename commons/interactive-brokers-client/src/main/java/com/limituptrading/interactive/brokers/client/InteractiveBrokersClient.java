/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.limituptrading.interactive.brokers.client;

import com.limituptrading.data.Ticker;
import com.limituptrading.ib.IBConnectionUtil;
import com.limituptrading.ib.IBSocket;
import com.limituptrading.ib.MarketDepthListener;
import com.limituptrading.marketdata.Level1QuoteListener;
import com.limituptrading.marketdata.Level2QuoteListener;
import com.limituptrading.marketdata.QuoteEngine;
import com.limituptrading.marketdata.ib.IBQuoteEngine;

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
