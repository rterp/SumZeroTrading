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


package com.sumzerotrading.broker;

import com.sumzerotrading.data.Ticker;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author Rob Terpilowski
 */
public class PortfolioPosition implements Serializable {
    
    protected Ticker ticker;
    protected BigDecimal positionSize;
    
    
    public PortfolioPosition( Ticker ticker, BigDecimal positionSize ) {
        this.ticker = ticker;
        this.positionSize = positionSize;
    }

    
    public BigDecimal getPositionSize() {
        return positionSize;
    }

    
    public Ticker getTicker() {
        return ticker;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + (this.ticker != null ? this.ticker.hashCode() : 0);
        hash = 53 * hash + (this.positionSize != null ? this.positionSize.hashCode() : 0);
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
        final PortfolioPosition other = (PortfolioPosition) obj;
        if (this.ticker != other.ticker && (this.ticker == null || !this.ticker.equals(other.ticker))) {
            return false;
        }
        if (this.positionSize != other.positionSize && (this.positionSize == null || !this.positionSize.equals(other.positionSize))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PortfolioPosition{" + "ticker=" + ticker + ", positionSize=" + positionSize + '}';
    }
    
    
    
    
    
}
