/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sumzerotrading.data;

import java.math.BigDecimal;

/**
 *
 * @author RobTerpilowski
 */
public class CFDTicker extends Ticker {

    public static final long serialVersionUID = 1L;

    public CFDTicker(String symbol) {
        super(symbol);
        contractMultiplier = new BigDecimal( "1" );
        minimumTickSize = new BigDecimal( "0.01" );
        exchange = Exchange.INTERACTIVE_BROKERS_SMART;
    }



    @Override
    public InstrumentType getInstrumentType() {
        return InstrumentType.CFD;
    }

    
    
}
