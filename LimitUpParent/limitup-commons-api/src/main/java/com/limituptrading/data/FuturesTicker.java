/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.limituptrading.data;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Objects;

/**
 *
 * @author robbob
 */
public class FuturesTicker extends Ticker {

    public static long serialVersionUID = 1L;

 

    protected int expiryMonth = 0;
    protected int expiryYear = 0;
    protected Commodity commodity;

    
    public Commodity getCommodity() {
        return commodity;
    }

    public void setCommodity(Commodity commodity) {
        this.commodity = commodity;
    }

    @Override
    public InstrumentType getInstrumentType() {
        return InstrumentType.FUTURES;
    }

    public int getExpiryMonth() {
        return expiryMonth;
    }

    public void setExpiryMonth(int expiryMonth) {
        this.expiryMonth = expiryMonth;
    }

    public int getExpiryYear() {
        return expiryYear;
    }

    public void setExpiryYear(int expiryYear) {
        this.expiryYear = expiryYear;
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

        return ticker;
    }


}
