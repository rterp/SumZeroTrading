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


package com.sumzerotrading.marketdata.ib;

import com.sumzerotrading.data.Ticker;

/**
 *
 * @author Rob Terpilowski
 */
public class Level2QuoteData {
    
    protected Ticker ticker;
    protected int position;
    protected int operation;
    protected int side;
    protected double price;
    protected int size;

    public Level2QuoteData(Ticker ticker, int position, int operation, int side, double price, int size) {
        this.ticker = ticker;
        this.position = position;
        this.operation = operation;
        this.side = side;
        this.price = price;
        this.size = size;
    }

    public int getOperation() {
        return operation;
    }

    public int getPosition() {
        return position;
    }

    public double getPrice() {
        return price;
    }

    public int getSide() {
        return side;
    }

    public int getSize() {
        return size;
    }

    public Ticker getTicker() {
        return ticker;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Level2QuoteData other = (Level2QuoteData) obj;
        if (this.ticker != other.ticker && (this.ticker == null || !this.ticker.equals(other.ticker))) {
            return false;
        }
        if (this.position != other.position) {
            return false;
        }
        if (this.operation != other.operation) {
            return false;
        }
        if (this.side != other.side) {
            return false;
        }
        if (Double.doubleToLongBits(this.price) != Double.doubleToLongBits(other.price)) {
            return false;
        }
        if (this.size != other.size) {
            return false;
        }
        return true;
    }

    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + (this.ticker != null ? this.ticker.hashCode() : 0);
        hash = 61 * hash + this.position;
        hash = 61 * hash + this.operation;
        hash = 61 * hash + this.side;
        hash = 61 * hash + (int) (Double.doubleToLongBits(this.price) ^ (Double.doubleToLongBits(this.price) >>> 32));
        hash = 61 * hash + this.size;
        return hash;
    }
    
    
    
    
}
