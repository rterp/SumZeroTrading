/*
 * Created on Jan 9, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.limituptrading.marketdata;

import java.util.*;
import java.util.concurrent.TimeUnit;

import com.limituptrading.data.Ticker;
//import org.apache.log4j.Logger;

/**
 * @author robbob
 *
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public abstract class QuoteEngine implements IQuoteEngine {

    protected List<ErrorListener> errorListeners;
    protected Map<Ticker, List<Level1QuoteListener>> level1ListenerMap = Collections.synchronizedMap(new HashMap<Ticker, List<Level1QuoteListener>>());
    protected Map<Ticker, List<Level2QuoteListener>> level2ListenerMap = Collections.synchronizedMap(new HashMap<Ticker, List<Level2QuoteListener>>());
    //  protected Logger logger = Logger.getLogger( QuoteEngine.class );

    public QuoteEngine() {
        errorListeners = new ArrayList<ErrorListener>();
    }

    public void addErrorListener(ErrorListener listener) {
        synchronized (errorListeners) {
            errorListeners.add(listener);
        }
    }

    public void removeErrorListener(ErrorListener listener) {
        synchronized (errorListeners) {
            errorListeners.remove(listener);
        }
    }

    public void fireErrorEvent(QuoteError error) {
        synchronized (errorListeners) {
            for (int i = 0; i < errorListeners.size(); i++) {
                ((ErrorListener) errorListeners.get(i)).quoteEngineError(error);
            }
        }
    }

    public void subscribeLevel1(Ticker ticker, Level1QuoteListener listener) {
        synchronized (level1ListenerMap) {
            List<Level1QuoteListener> listeners = level1ListenerMap.get(ticker);
            if (listeners == null) {
                listeners = Collections.synchronizedList(new ArrayList<Level1QuoteListener>());
                level1ListenerMap.put(ticker, listeners);
            }
            synchronized (listeners) {
                listeners.add(listener);
            }
        }
    }

    public void unsubscribeLevel1(Ticker ticker, Level1QuoteListener listener) {
        synchronized (level1ListenerMap) {
            List<Level1QuoteListener> listeners = level1ListenerMap.get(ticker);
            if (listeners != null) {
                synchronized (listeners) {
                    listeners.remove(listener);
                }
            }
        }
    }

    @Override
    public void fireLevel1Quote(final ILevel1Quote quote) {
        synchronized (level1ListenerMap) {
            List<Level1QuoteListener> listeners = level1ListenerMap.get(quote.getTicker());
            if (listeners == null) {
                return;
            }
            for (final Level1QuoteListener listener : listeners) {
                try {
                    synchronized (listeners) {
                        Thread thread = new Thread( new Runnable() {
                            public void run() {
                                listener.quoteRecieved(quote);
                            }
                        });
                        thread.start();
                    }
                } catch (Exception ex) {
                    //don't let 1 listener blowing up prevent other listeners from getting the quote.
                    //     logger.error(ex, ex);
                }
            }
        }
    }

    @Override
    public void fireMarketDepthQuote(ILevel2Quote quote) {
        synchronized (level2ListenerMap) {
            List<Level2QuoteListener> listeners = level2ListenerMap.get(quote.getTicker());
            if (listeners == null) {
                return;
            }
            for (Level2QuoteListener listener : listeners) {
                try {
                    listener.level2QuoteReceived(quote);
                } catch (Exception ex) {
                    //   logger.error(ex, ex);
                }
            }
        }
    }

    public void subscribeMarketDepth(Ticker ticker, Level2QuoteListener listener) {
        synchronized (ticker) {
            List<Level2QuoteListener> listeners = level2ListenerMap.get(ticker);
            if (listeners == null) {
                listeners = Collections.synchronizedList(new ArrayList<Level2QuoteListener>());
                level2ListenerMap.put(ticker, listeners);
            }
            listeners.add(listener);
        }
    }

    public void unsubscribeMarketDepth(Ticker ticker, Level2QuoteListener listener) {
        synchronized (ticker) {
            List<Level2QuoteListener> listeners = level2ListenerMap.get(ticker);
            if (listeners != null) {
                listeners.remove(listener);
            }
        }
    }
}
