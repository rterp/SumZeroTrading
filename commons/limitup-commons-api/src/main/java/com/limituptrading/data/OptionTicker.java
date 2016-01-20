/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.limituptrading.data;

/**
 *
 * @author robbob
 */
public class OptionTicker extends Ticker {

    protected int expiryMonth;
    protected int expiryYear;
    
    public OptionTicker( String symbol, int expiryMonth, int expiryYear ) {
        super(symbol);
    }
    
    
    @Override
    public InstrumentType getInstrumentType() {
        return InstrumentType.OPTION;
    }

    public int getExpiryMonth() {
        return expiryMonth;
    }

    public int getExpiryYear() {
        return expiryYear;
    }
    
    
    
    
}
