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

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Objects;

/**
 *
 * @author Rob Terpilowski
 */
public class Commodity implements Serializable {
    
    public static final long serialVersionUID = 1L;
    
    public static final DecimalFormat NO_DECIMALS = new DecimalFormat("#####0");
    public static final DecimalFormat ONE_DECIMAL = new DecimalFormat("#####0.0");
    public static final DecimalFormat TWO_DECIMALS = new DecimalFormat("####0.00");
    public static final DecimalFormat THREE_DECIMALS = new DecimalFormat("####0.000");
    public static final DecimalFormat FOUR_DECIMALS = new DecimalFormat("####0.0000");
    public static final DecimalFormat FIVE_DECIMALS = new DecimalFormat("####0.00000");
    public static final DecimalFormat SIX_DECIMALS = new DecimalFormat("####0.000000");
            
    
    public static final Commodity SP500_INDEX_MINI_GLOBEX = new Commodity( "ES", Exchange.GLOBEX, TWO_DECIMALS, createBd(0.25, 2), new BigDecimal(50));
    public static final Commodity NASDAQ100_INDEX_MINI_GLOBEX = new Commodity( "NQ", Exchange.GLOBEX, TWO_DECIMALS, createBd(0.25,2), new BigDecimal(20));
    public static final Commodity RUSSELL_2000_INDEX_MINI_NYBOT = new Commodity( "TF", Exchange.NYBOT, TWO_DECIMALS, createBd(0.10,2), new BigDecimal(50));
    public static final Commodity RUSSELL_2000_INDEX_MINI_GLOBEX = new Commodity( "RTY", Exchange.GLOBEX, TWO_DECIMALS, createBd(0.10,2), new BigDecimal(50));
    public static final Commodity DOW_INDEX_MINI_ECBOT = new Commodity( "YM", Exchange.ECBOT, NO_DECIMALS, createBd(1,0), new BigDecimal(5));
    public static final Commodity VIX_FUTURES_CFE = new Commodity( "VX", Exchange.CFE,TWO_DECIMALS, createBd(0.10,2), new BigDecimal(1000));
    public static final Commodity BOND_30_YEAR_ECBOT = new Commodity( "ZB", Exchange.ECBOT, FIVE_DECIMALS, createBd(0.03125,5), new BigDecimal(1000));
    public static final Commodity BOND_10_YEAR_ECBOT = new Commodity( "ZN", Exchange.ECBOT, FIVE_DECIMALS, createBd(0.03125,5), new BigDecimal(1000));
    public static final Commodity EURO_GLOBEX = new Commodity( "6E", Exchange.GLOBEX, FOUR_DECIMALS, createBd(0.0001,4), new BigDecimal(125000));
    public static final Commodity EURO_MINI_GLOBEX = new Commodity( "E7", Exchange.GLOBEX, FOUR_DECIMALS, createBd(0.0001,4), new BigDecimal(62500));
    public static final Commodity SWISS_FRANC_GLOBEX = new Commodity( "6S", Exchange.GLOBEX, FOUR_DECIMALS, createBd(0.0001,4), new BigDecimal(125000));
    public static final Commodity JAPANESE_YEN_GLOBEX = new Commodity( "6J", Exchange.GLOBEX, SIX_DECIMALS, createBd(0.000001,6), new BigDecimal(12500000));
    public static final Commodity JAPANESE_YEN_MINI_GLOBEX = new Commodity( "J7", Exchange.GLOBEX, SIX_DECIMALS, createBd(0.000001,6), new BigDecimal(6250000));
    public static final Commodity CANADIAN_DOLLAR_GLOBEX = new Commodity( "6C", Exchange.GLOBEX, FOUR_DECIMALS, createBd(0.0001,4), new BigDecimal(100000));
    public static final Commodity GOLD_NYMEX = new Commodity( "GC", Exchange.NYMEX, TWO_DECIMALS, createBd(0.10,2), new BigDecimal(100));
    public static final Commodity GOLD_MINI_NYSE_LIFFE = new Commodity( "YG", Exchange.NYSE_LIFFE, TWO_DECIMALS, createBd(0.10,2), createBd(32.15,2));
    public static final Commodity SILVER_NYMEX = new Commodity( "SI", Exchange.NYMEX, THREE_DECIMALS, createBd(0.005,3), new BigDecimal(5000));
    public static final Commodity SILVER_MINI_NYSE_LIFFE = new Commodity( "YI", Exchange.NYSE_LIFFE, THREE_DECIMALS, createBd(0.005,3), new BigDecimal(1000));
    public static final Commodity COPPER_NYMEX = new Commodity( "HG", Exchange.NYMEX, THREE_DECIMALS, createBd(0.0005,4), new BigDecimal(25000));
    
    public static final Commodity CRUDE_OIL_NYMEX = new Commodity("CL", Exchange.NYMEX, TWO_DECIMALS, createBd(0.01,2), new BigDecimal(1000));
    public static final Commodity CRUDE_OIL_MINI_NYMEX = new Commodity("QM", Exchange.NYMEX, THREE_DECIMALS, createBd(0.025,3), new BigDecimal(500));
    public static final Commodity NATURAL_GAS_NYMEX = new Commodity("NG", Exchange.NYMEX, THREE_DECIMALS, createBd(0.001,3), new BigDecimal(10000));
    public static final Commodity NATURAL_GAS_MINI_NYMEX = new Commodity("QG", Exchange.NYMEX, THREE_DECIMALS, createBd(0.005,3), new BigDecimal(2500));
    public static final Commodity HEATING_OIL_NYMEX = new Commodity("HO", Exchange.NYMEX, FOUR_DECIMALS, createBd(0.0001,4), new BigDecimal(42000));
    
    public static final Commodity CORN_ECBOT = new Commodity("ZC", Exchange.ECBOT, TWO_DECIMALS, createBd(0.25,2), new BigDecimal(50));
    public static final Commodity SOYBEANS_ECBOT = new Commodity("ZS", Exchange.ECBOT, TWO_DECIMALS, createBd(0.25,2), new BigDecimal(50));
    public static final Commodity WHEAT_ECBOT = new Commodity("ZW", Exchange.ECBOT, TWO_DECIMALS, createBd(0.25,2), new BigDecimal(50));
    
    public static final Commodity HANG_SENG_INDEX = new Commodity("HSI", Exchange.HKFE, NO_DECIMALS, BigDecimal.ONE, new BigDecimal(50), "HKD");
    public static final Commodity HANG_SENG_MINI_INDEX = new Commodity("MHI", Exchange.HKFE, NO_DECIMALS, BigDecimal.ONE, new BigDecimal(10), "HKD");
    
    public static final Commodity[] ALL = { SP500_INDEX_MINI_GLOBEX, NASDAQ100_INDEX_MINI_GLOBEX, RUSSELL_2000_INDEX_MINI_NYBOT, RUSSELL_2000_INDEX_MINI_GLOBEX, DOW_INDEX_MINI_ECBOT,
        BOND_10_YEAR_ECBOT, BOND_30_YEAR_ECBOT, EURO_GLOBEX, EURO_MINI_GLOBEX, CANADIAN_DOLLAR_GLOBEX, SWISS_FRANC_GLOBEX, JAPANESE_YEN_GLOBEX, JAPANESE_YEN_MINI_GLOBEX,
        GOLD_NYMEX, GOLD_MINI_NYSE_LIFFE, SILVER_NYMEX, SILVER_MINI_NYSE_LIFFE, COPPER_NYMEX, CRUDE_OIL_NYMEX, CRUDE_OIL_MINI_NYMEX, NATURAL_GAS_NYMEX,
        NATURAL_GAS_MINI_NYMEX, HEATING_OIL_NYMEX, CORN_ECBOT, SOYBEANS_ECBOT, WHEAT_ECBOT, HANG_SENG_INDEX, HANG_SENG_MINI_INDEX };
    
    
    protected String exchangeSymbol;
    protected Exchange exchange;
    protected DecimalFormat decimalFormat;
    protected BigDecimal minimumTickSize;
    protected BigDecimal contractMultiplier;
    protected String currency;

    
    public Commodity(String exchangeSymbol, Exchange exchange, DecimalFormat decimalFormat, BigDecimal minimumTickSize, BigDecimal contractMultiplier ) {
        this(exchangeSymbol, exchange, decimalFormat, minimumTickSize, contractMultiplier, "USD");
    }
    
    public Commodity(String exchangeSymbol, Exchange exchange, DecimalFormat decimalFormat, BigDecimal minimumTickSize, BigDecimal contractMultiplier, String currency) {
        this.exchangeSymbol = exchangeSymbol;
        this.exchange = exchange;
        this.decimalFormat = decimalFormat;
        this.minimumTickSize = minimumTickSize;
        this.contractMultiplier = contractMultiplier;
        this.currency = currency;
    }

    public String getExchangeSymbol() {
        return exchangeSymbol;
    }

    public Exchange getExchange() {
        return exchange;
    }

    public DecimalFormat getDecimalFormat() {
        return decimalFormat;
    }

    public BigDecimal getMinimumTickSize() {
        return minimumTickSize;
    }

    public BigDecimal getContractMultiplier() {
        return contractMultiplier;
    }

    public String getCurrency() {
        return currency;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.exchangeSymbol);
        hash = 89 * hash + Objects.hashCode(this.exchange);
        hash = 89 * hash + Objects.hashCode(this.minimumTickSize);
        hash = 89 * hash + Objects.hashCode(this.contractMultiplier);
        hash = 89 * hash + Objects.hashCode(this.currency);
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
        final Commodity other = (Commodity) obj;
        if (!Objects.equals(this.exchangeSymbol, other.exchangeSymbol)) {
            return false;
        }
        if (!Objects.equals(this.exchange, other.exchange)) {
            return false;
        }
        if (!Objects.equals(this.minimumTickSize, other.minimumTickSize)) {
            return false;
        }
        if (!Objects.equals(this.contractMultiplier, other.contractMultiplier)) {
            return false;
        }
        if (!Objects.equals(this.currency, other.currency)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Commodity{" + "exchangeSymbol=" + exchangeSymbol + ", exchange=" + exchange + ", decimalFormat=" + decimalFormat + ", minimumTickSize=" + minimumTickSize + ", contractMultiplier=" + contractMultiplier + ", currency=" + currency + '}';
    }
    
    
    public static Commodity getByExchangeSymbol( String symbol ) {
        for( Commodity commodity : ALL ) {
            if( commodity.getExchangeSymbol().equalsIgnoreCase(symbol) ) {
                return commodity;
            }
        }
        
        throw new IllegalStateException( "No Commodity found for symbol: " + symbol );
    }
    
    
    public static BigDecimal createBd(double value, int decimals) {
        return new BigDecimal(value).setScale(decimals, RoundingMode.HALF_UP);
    }
    
    
    
    
    
    
}
