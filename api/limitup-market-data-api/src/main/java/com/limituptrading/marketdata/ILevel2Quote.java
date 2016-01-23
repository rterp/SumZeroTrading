/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.limituptrading.marketdata;

/**
 *
 * @author robbob
 */
public interface ILevel2Quote extends IQuote {
    
    public IMarketDepthBook getMarketDepthBook();
}
