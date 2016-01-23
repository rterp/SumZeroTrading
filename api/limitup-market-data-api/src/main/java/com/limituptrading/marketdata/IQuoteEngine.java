package com.limituptrading.marketdata;

import java.util.Date;
import java.util.Properties;

import com.limituptrading.data.Ticker;

public interface IQuoteEngine {

    public static enum Property {
        BID, ASK, MIDPOINT, TRADES
    };

    public static enum Side {
        BID, ASK
    };

    public abstract void subscribeLevel1(Ticker ticker, Level1QuoteListener listener);

    public abstract void unsubscribeLevel1(Ticker ticker, Level1QuoteListener listener);
    
    public abstract void startEngine();

    public abstract void startEngine(Properties props);

    public abstract void stopEngine();

    public abstract void subscribeMarketDepth(Ticker ticker, Level2QuoteListener listener);

    public abstract void unsubscribeMarketDepth(Ticker ticker, Level2QuoteListener listener);

    public abstract Date getServerTime();

    public abstract void addErrorListener(ErrorListener listener);

    public abstract void removeErrorListener(ErrorListener listener);

    public abstract boolean started();
    
    public abstract boolean isConnected();
    
    public void fireLevel1Quote( ILevel1Quote quote );
    
    public void fireMarketDepthQuote( ILevel2Quote quote );
    
    public void fireErrorEvent( QuoteError error );
}
