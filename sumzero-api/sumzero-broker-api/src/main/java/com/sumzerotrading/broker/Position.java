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
import java.util.Objects;

/**
 * Defines a Position held at the broker.
 * 
 * @author RobTerpilowski
 */
public class Position implements Serializable {
    
    
    protected Ticker ticker;
    protected double size;

    /**
     * The ticker and the position size.  Negative position sizes indicate a short position
     * 
     * @param ticker The ticker held.
     * @param size The size of the position
     */
    public Position(Ticker ticker, double size) {
        this.ticker = ticker;
        this.size = size;
    }

    
    /**
     * Get the ticker for this position
     * @return The ticker for this position
     */
    public Ticker getTicker() {
        return ticker;
    }

    /**
     * Gets the size of the position.  Negative sizes indicate short positions.
     * @return The position size.
     */
    public double getSize() {
        return size;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.ticker);
        hash = 47 * hash + (int) (Double.doubleToLongBits(this.size) ^ (Double.doubleToLongBits(this.size) >>> 32));
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
        final Position other = (Position) obj;
        if (Double.doubleToLongBits(this.size) != Double.doubleToLongBits(other.size)) {
            return false;
        }
        if (!Objects.equals(this.ticker, other.ticker)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Position{" + "ticker=" + ticker + ", size=" + size + '}';
    }
    

    
    
}
