/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.limituptrading.data;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 *
 * @author robbob
 */
public class StockTicker extends Ticker {



    public StockTicker( String symbol ) {
        super(symbol);
        contractMultiplier = new BigDecimal( "1" );
        minimumTickSize = new BigDecimal( "0.01" );
        exchange = Exchange.INTERACTIVE_BROKERS_SMART;
    }
    
    
    
    @Override
    public InstrumentType getInstrumentType() {
        return InstrumentType.STOCK;
    }
    
    
    
    
    
}
