/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.limituptrading.marketdata.timeAndSales;

import com.limituptrading.data.Exchange;
import com.limituptrading.data.Ticker;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author robbob
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
