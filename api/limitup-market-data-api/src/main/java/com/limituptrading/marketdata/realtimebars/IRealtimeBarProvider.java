/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.limituptrading.marketdata.realtimebars;

import com.limituptrading.data.Bar;
import com.limituptrading.data.TickType;
import com.limituptrading.data.Ticker;
import com.limituptrading.marketdata.IQuoteEngine.Property;

/**
 *
 * @author robbob
 */
public interface IRealtimeBarProvider {
    public abstract void requestRealTimeBars( int id, Ticker ticker, int timeInterval, Bar.LENGTH_UNIT timeUnit, TickType tickType, RealtimeBarListener listener );
}
