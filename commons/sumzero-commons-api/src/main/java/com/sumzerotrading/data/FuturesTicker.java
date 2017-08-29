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

import java.util.Objects;

/**
 *
 * @author Rob Terpilowski
 */
public class FuturesTicker extends Ticker {

    public final static long serialVersionUID = 1L;

    protected int expiryMonth = 0;
    protected int expiryYear = 0;
    protected Commodity commodity;

    
    public Commodity getCommodity() {
        return commodity;
    }

    public FuturesTicker setCommodity(Commodity commodity) {
        this.commodity = commodity;
        return this;
    }

    @Override
    public InstrumentType getInstrumentType() {
        return InstrumentType.FUTURES;
    }

    public int getExpiryMonth() {
        return expiryMonth;
    }

    public FuturesTicker setExpiryMonth(int expiryMonth) {
        this.expiryMonth = expiryMonth;
        return this;
    }

    public int getExpiryYear() {
        return expiryYear;
    }

    public FuturesTicker setExpiryYear(int expiryYear) {
        this.expiryYear = expiryYear;
        return this;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + this.expiryMonth;
        hash = 59 * hash + this.expiryYear;
        hash = 59 * hash + Objects.hashCode(this.commodity);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final FuturesTicker other = (FuturesTicker) obj;
        if (this.expiryMonth != other.expiryMonth) {
            return false;
        }
        if (this.expiryYear != other.expiryYear) {
            return false;
        }
        if (!Objects.equals(this.commodity, other.commodity)) {
            return false;
        }
        if( ! super.equals(obj) ) {
            return false;
        }
        return true;
    }

    
    
    @Override
    public String toString() {
        return "FuturesTicker{" + "expiryMonth=" + expiryMonth + ", expiryYear=" + expiryYear + '}' + " : " + super.toString();
    }
    
        public static FuturesTicker getInstance(String exchangeSymbol) {
        FuturesTicker ticker = null;
        for (Commodity commodity : Commodity.ALL) {
            if (exchangeSymbol.equals(commodity.getExchangeSymbol())) {
                ticker = getInstance(commodity);
            }
        }

        if (ticker == null) {
            throw new IllegalStateException("Ticker not found for exchange symbol " + exchangeSymbol);
        }

        return ticker;
    }

    public static FuturesTicker getInstance(Commodity commodity) {
        return getInstance(commodity, 0, 0);
    }

    public static FuturesTicker getInstance(Commodity commodity, int expirationMonth, int expirationYear) {
        FuturesTicker ticker = new FuturesTicker();
        ticker.setSymbol(commodity.getExchangeSymbol());
        ticker.setExpiryMonth(expirationMonth);
        ticker.setExpiryYear(expirationYear);
        ticker.setExchange(commodity.getExchange());
        ticker.setMinimumTickSize(commodity.getMinimumTickSize());
        ticker.setDecimalFormat(commodity.getDecimalFormat());
        ticker.setContractMultiplier(commodity.getContractMultiplier());
        ticker.setCommodity(commodity);
        ticker.setCurrency(commodity.getCurrency());
        

        return ticker;
    }


}
