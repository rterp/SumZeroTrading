/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.limituptrading.marketdata;

/**
 *
 * @author robbob
 */
public interface Level2QuoteListener {
    
    public void level2QuoteReceived( ILevel2Quote level2Quote );
}
