/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.limituptrading.data;

import java.math.BigDecimal;

/**
 *
 * @author robbob
 */
public class CurrencyTicker extends Ticker {
    protected boolean halfTickSupported = true;
    
    public CurrencyTicker() {
        super();
        minimumTickSize = new BigDecimal("0.0001");
    }
    
    public void setSupporthalfTick( boolean supported ) {
        this.halfTickSupported = supported;
    }
    
    
    @Override
    public InstrumentType getInstrumentType() {
        return InstrumentType.FOREX;
    }
    
    
    public boolean supportsHalfTick() {
        return halfTickSupported;
    }
    
    
}
