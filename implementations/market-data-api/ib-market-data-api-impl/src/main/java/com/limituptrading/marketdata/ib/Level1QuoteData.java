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


package com.zerosumtrading.marketdata.ib;

import com.zerosumtrading.data.Ticker;

/**
 *
 * @author Rob Terpilowski
 */
public class Level1QuoteData {
    protected Ticker ticker;
    protected int field;
    protected double price = 0;
    protected int canAutoExecute = 0;
    protected int size = 0;
            
    public Level1QuoteData(Ticker ticker, int field, double price, int canAutoExecute, int size) {
        this.ticker = ticker;
        this.field = field;
        this.price = price;
        this.canAutoExecute = canAutoExecute;
        this.size = size;
    }

    public int getCanAutoExecute() {
        return canAutoExecute;
    }

    public int getField() {
        return field;
    }

    public double getPrice() {
        return price;
    }

    public Ticker getTicker() {
        return ticker;
    }

    public int getSize() {
        return size;
    }
    
    

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Level1QuoteData other = (Level1QuoteData) obj;
        if (this.ticker != other.ticker && (this.ticker == null || !this.ticker.equals(other.ticker))) {
            return false;
        }
        if (this.field != other.field) {
            return false;
        }
        if (Double.doubleToLongBits(this.price) != Double.doubleToLongBits(other.price)) {
            return false;
        }
        if (this.canAutoExecute != other.canAutoExecute) {
            return false;
        }
        if( this.size != other.size ) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + (this.ticker != null ? this.ticker.hashCode() : 0);
        hash = 37 * hash + this.field;
        hash = 37 * hash + (int) (Double.doubleToLongBits(this.price) ^ (Double.doubleToLongBits(this.price) >>> 32));
        hash = 37 * hash + this.canAutoExecute;
        hash = 37 * hash + this.size;
        return hash;
    }
    
    
    
    
    
}
