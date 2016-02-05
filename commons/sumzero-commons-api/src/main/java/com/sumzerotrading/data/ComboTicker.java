/**
 * MIT License

Copyright (c) 2015  Rob Terpilowski

Permission is hereby granted, free of charge, to any person obtaining a copy of this software 
and associated documentation files (the "Software"), to deal in the Software without restriction, 
including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, 
and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, 
subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING 
BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. 
IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, 
WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE 
OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */


package com.sumzerotrading.data;

/**
 *
 * @author Rob Terpilowski
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
