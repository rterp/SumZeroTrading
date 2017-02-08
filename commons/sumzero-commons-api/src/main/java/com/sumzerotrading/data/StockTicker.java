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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;

/**
 *
 * @author Rob Terpilowski
 */
public class StockTicker extends Ticker {

    public static final long serialVersionUID = 1L;
    
    protected InstrumentType instrumentType = InstrumentType.STOCK;

    public StockTicker( String symbol ) {
        super(symbol);
        contractMultiplier = new BigDecimal( "1" );
        minimumTickSize = new BigDecimal( "0.01" );
        exchange = Exchange.INTERACTIVE_BROKERS_SMART;
    }
    
    
    
    @Override
    public InstrumentType getInstrumentType() {
        return instrumentType;
    }

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = 11 * hash + Objects.hashCode(this.instrumentType);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final StockTicker other = (StockTicker) obj;
        if (this.instrumentType != other.instrumentType) {
            return false;
        }
        
        return super.equals(obj);
    }
    
    
    
    
    
    
}
