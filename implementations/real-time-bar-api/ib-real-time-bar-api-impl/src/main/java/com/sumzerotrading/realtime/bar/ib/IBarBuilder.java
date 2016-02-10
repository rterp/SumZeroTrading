/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sumzerotrading.realtime.bar.ib;

import com.sumzerotrading.marketdata.Level1QuoteListener;
import com.sumzerotrading.realtime.bar.RealtimeBarListener;

/**
 *
 * @author Rob Terpilowski
 */
public interface IBarBuilder extends Level1QuoteListener {

    public void addBarListener(RealtimeBarListener listener);

    public void removeBarListener(RealtimeBarListener listener);
    
    public int getListenerCount();
    
    public void stop();
    
    public void buildBarAndFireEvents();
    
}
