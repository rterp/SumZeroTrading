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
import java.util.Objects;

/**
 *
 * @author Rob Terpilowski
 */
public class OptionTicker extends Ticker {

    public enum Right { Call, Put };
    
    protected int expiryMonth;
    protected int expiryYear;
    protected int expiryDay;
    protected BigDecimal strike;
    protected Right right;
   
    
    
    
    public OptionTicker( String symbol ) {
        super(symbol);
        setContractMultiplier(new BigDecimal(100));
    }
    
    
    @Override
    public InstrumentType getInstrumentType() {
        return InstrumentType.OPTION;
    }

    public int getExpiryMonth() {
        return expiryMonth;
    }

    public OptionTicker setExpiryMonth(int expiryMonth) {
        this.expiryMonth = expiryMonth;
        return this;
    }

    public int getExpiryYear() {
        return expiryYear;
    }

    public OptionTicker setExpiryYear(int expiryYear) {
        this.expiryYear = expiryYear;
        return this;
    }

    public int getExpiryDay() {
        return expiryDay;
    }

    public OptionTicker setExpiryDay(int expiryDay) {
        this.expiryDay = expiryDay;
        return this;
    }

    public BigDecimal getStrike() {
        return strike;
    }

    public OptionTicker setStrike(BigDecimal strike) {
        this.strike = strike;
        return this;
    }

    public Right getRight() {
        return right;
    }

    public OptionTicker setRight(Right right) {
        this.right = right;
        return this;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + this.expiryMonth;
        hash = 67 * hash + this.expiryYear;
        hash = 67 * hash + this.expiryDay;
        hash = 67 * hash + Objects.hashCode(this.strike);
        hash = 67 * hash + Objects.hashCode(this.right);
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
        final OptionTicker other = (OptionTicker) obj;
        if (this.expiryMonth != other.expiryMonth) {
            return false;
        }
        if (this.expiryYear != other.expiryYear) {
            return false;
        }
        if (this.expiryDay != other.expiryDay) {
            return false;
        }
        if (!Objects.equals(this.strike, other.strike)) {
            return false;
        }
        if (this.right != other.right) {
            return false;
        }
        return true;
    }


    
    
    
    
    
    
}
