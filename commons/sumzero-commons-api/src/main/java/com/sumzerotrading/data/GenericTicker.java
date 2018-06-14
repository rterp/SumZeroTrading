/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sumzerotrading.data;

/**
 *
 * @author RobTerpilowski
 */
public class GenericTicker extends Ticker {

    public GenericTicker(String symbol) {
        super(symbol);
    }

    @Override
    public InstrumentType getInstrumentType() {
        return InstrumentType.NONE;
    }

    
    
    
    @Override
    public String toString() {
        return "GenericTicker{" + super.toString() + '}';
    }



    
    
    
}
