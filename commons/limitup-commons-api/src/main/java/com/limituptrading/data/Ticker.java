/*
 * Created on Jun 24, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.limituptrading.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.*;

/**
 * @author robbob
 *
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public abstract class Ticker implements Serializable {
    
    

    public static long serialVersionUID = 1L;
    protected String symbol;
    protected Exchange exchange;
    protected Exchange primaryExchange;
    protected String currency = "USD";
    protected DecimalFormat decimalFormat = new DecimalFormat("00");
    protected BigDecimal minimumTickSize = new BigDecimal("0.01");
    protected BigDecimal contractMultiplier;

    public Ticker() {
    }

    public Ticker(String symbol) {
        this.symbol = symbol;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Exchange getExchange() {
        return exchange;
    }

    public void setExchange(Exchange exchange) {
        this.exchange = exchange;
    }

    public abstract InstrumentType getInstrumentType();

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public BigDecimal getMinimumTickSize() {
        return minimumTickSize;
    }

    public void setMinimumTickSize(BigDecimal minimumTickSize) {
        this.minimumTickSize = minimumTickSize;
    }

    public BigDecimal getContractMultiplier() {
        return contractMultiplier;
    }

    public void setContractMultiplier(BigDecimal contractMultiplier) {
        this.contractMultiplier = contractMultiplier;
    }

    public boolean supportsHalfTick() {
        return false;
    }

    protected String padMonth(int month) {
        if (month < 10) {
            return "0" + month;
        } else {
            return Integer.toString(month);
        }
    }

    public DecimalFormat getDecimalFormat() {
        return decimalFormat;
    }

    public void setDecimalFormat(DecimalFormat decimalFormat) {
        this.decimalFormat = decimalFormat;
    }

    public Exchange getPrimaryExchange() {
        return primaryExchange;
    }

    public void setPrimaryExchange(Exchange primaryExchange) {
        this.primaryExchange = primaryExchange;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + (this.symbol != null ? this.symbol.hashCode() : 0);
        hash = 29 * hash + (this.exchange != null ? this.exchange.hashCode() : 0);
        hash = 29 * hash + (this.primaryExchange != null ? this.primaryExchange.hashCode() : 0);
        hash = 29 * hash + (this.currency != null ? this.currency.hashCode() : 0);
        hash = 29 * hash + (this.decimalFormat != null ? this.decimalFormat.hashCode() : 0);
        hash = 29 * hash + (this.minimumTickSize != null ? this.minimumTickSize.hashCode() : 0);
        hash = 29 * hash + (this.contractMultiplier != null ? this.contractMultiplier.hashCode() : 0);
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
        final Ticker other = (Ticker) obj;
        if ((this.symbol == null) ? (other.symbol != null) : !this.symbol.equals(other.symbol)) {
            return false;
        }
        if (this.exchange != other.exchange && (this.exchange == null || !this.exchange.equals(other.exchange))) {
            return false;
        }
        if (this.primaryExchange != other.primaryExchange && (this.primaryExchange == null || !this.primaryExchange.equals(other.primaryExchange))) {
            return false;
        }
        if ((this.currency == null) ? (other.currency != null) : !this.currency.equals(other.currency)) {
            return false;
        }
        if (this.decimalFormat != other.decimalFormat && (this.decimalFormat == null || !this.decimalFormat.equals(other.decimalFormat))) {
            return false;
        }
        if (this.minimumTickSize != other.minimumTickSize && (this.minimumTickSize == null || !this.minimumTickSize.equals(other.minimumTickSize))) {
            return false;
        }
        if (this.contractMultiplier != other.contractMultiplier && (this.contractMultiplier == null || !this.contractMultiplier.equals(other.contractMultiplier))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Ticker{" + "symbol=" + symbol + ", exchange=" + exchange + ", primaryExchange=" + primaryExchange + ", currency=" + currency + ", decimalFormat=" + decimalFormat + ", minimumTickSize=" + minimumTickSize + ", contractMultiplier=" + contractMultiplier + '}';
    }

    

    

}
