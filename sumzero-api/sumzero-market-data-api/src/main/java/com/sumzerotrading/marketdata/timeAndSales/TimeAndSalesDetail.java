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

package com.sumzerotrading.marketdata.timeAndSales;

import com.sumzerotrading.data.Exchange;
import com.sumzerotrading.data.Ticker;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Rob Terpilowski
 */
public class TimeAndSalesDetail {
    
    protected Date tradeDate;
    protected Ticker ticker;
    protected BigDecimal price;
    protected BigDecimal size;

    public TimeAndSalesDetail(Date tradeDate, Ticker ticker, BigDecimal price, BigDecimal size) {
        this.tradeDate = tradeDate;
        this.ticker = ticker;
        this.price = price;
        this.size = size;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getSize() {
        return size;
    }

    public Ticker getTicker() {
        return ticker;
    }

    public Date getTradeDate() {
        return tradeDate;
    }
    
    public Exchange getExchange() {
        return ticker.getExchange();
    }
    
    public String getCurrency() {
        return ticker.getCurrency();
    }
    
    

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TimeAndSalesDetail other = (TimeAndSalesDetail) obj;
        if (this.tradeDate != other.tradeDate && (this.tradeDate == null || !this.tradeDate.equals(other.tradeDate))) {
            return false;
        }
        if (this.ticker != other.ticker && (this.ticker == null || !this.ticker.equals(other.ticker))) {
            return false;
        }
        if (this.price != other.price && (this.price == null || !this.price.equals(other.price))) {
            return false;
        }
        if (this.size != other.size && (this.size == null || !this.size.equals(other.size))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + (this.tradeDate != null ? this.tradeDate.hashCode() : 0);
        hash = 17 * hash + (this.ticker != null ? this.ticker.hashCode() : 0);
        hash = 17 * hash + (this.price != null ? this.price.hashCode() : 0);
        hash = 17 * hash + (this.size != null ? this.size.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "TimeAndSalesDetail{" + "tradeDate=" + tradeDate + ", ticker=" + ticker + ", price=" + price + ", size=" + size + '}';
    }
    
    
}
