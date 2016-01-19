/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.limituptrading.data;

/**
 *
 * @author robbob
 */
public class ComboTicker extends Ticker {

    protected Ticker firstTicker;
    protected Ticker secondTicker;
    
    protected int contractId1 = 0;
    protected int contractId2 = 0;
    protected int firstTickerRatio = 1;
    protected int secondTickerRatio = 1;
    
    public ComboTicker( Ticker firstTicker, Ticker secondTicker ) {
        this( firstTicker, secondTicker, 1, 1 );
    }
    
    public ComboTicker( Ticker firstTicker, Ticker secondTicker, int firstTickerRatio, int secondTickerRatio ) {
        this.firstTicker = firstTicker;
        this.secondTicker = secondTicker;
        this.firstTickerRatio = firstTickerRatio;
        this.secondTickerRatio = secondTickerRatio;
        this.symbol = firstTicker.getSymbol() + "-" + secondTicker.getSymbol();
    }

    public int getContractId1() {
        return contractId1;
    }

    public void setContractId1(int contractId1) {
        this.contractId1 = contractId1;
    }

    public int getContractId2() {
        return contractId2;
    }

    public void setContractId2(int contractId2) {
        this.contractId2 = contractId2;
    }
    
        
    @Override
    public InstrumentType getInstrumentType() {
        return InstrumentType.COMBO;
    }

    public Ticker getFirstTicker() {
        return firstTicker;
    }

    public void setFirstTicker(Ticker firstTicker) {
        this.firstTicker = firstTicker;
    }

    public int getFirstTickerRatio() {
        return firstTickerRatio;
    }

    public void setFirstTickerRatio(int firstTickerRatio) {
        this.firstTickerRatio = firstTickerRatio;
    }

    public int getSecondTickerRatio() {
        return secondTickerRatio;
    }

    public void setSecondTickerRatio(int secondTickerRatio) {
        this.secondTickerRatio = secondTickerRatio;
    }



    public Ticker getSecondTicker() {
        return secondTicker;
    }

    public void setSecondTicker(Ticker secondTicker) {
        this.secondTicker = secondTicker;
    }
    
    
    
    
}
