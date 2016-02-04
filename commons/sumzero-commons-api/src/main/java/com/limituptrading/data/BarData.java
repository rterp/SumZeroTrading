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


package com.zerosumtrading.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Represents one bar of market data.
 *
 * @version $Revision: 1.3 $
 * @author Rob Terpilowski
 */
public class BarData implements Serializable {
    
    public static long serialVersionUID = 1L;

    public static final double NULL = -9D;
    public static final int OPEN = 1;
    public static final int HIGH = 2;
    public static final int LOW = 3;
    public static final int CLOSE = 4;

    public enum LengthUnit {

        TICK, SECOND, MINUTE, HOUR, DAY, WEEK, MONTH, YEAR
    };

    protected Ticker ticker;
    protected double open;
    protected BigDecimal formattedOpen;
    protected double high;
    protected BigDecimal formattedHigh;
    protected double low;
    protected BigDecimal formattedLow;
    protected double close;
    protected BigDecimal formattedClose;
    protected long volume = 0;
    protected long openInterest = 0;
    protected int barLength = 1;
    protected LocalDateTime dateTime;
    protected LengthUnit lengthUnit;
    //protected Logger logger = Logger.getLogger( BarData.class );

    public BarData() {
    }

    public BarData(Ticker ticker, LocalDateTime dateTime, double open, double high, double low, double close, long volume, int barLength, LengthUnit lengthUnit) {
        this.ticker = ticker;
        this.dateTime = dateTime;
        this.open = open;
        this.close = close;
        this.low = low;
        this.high = high;
        this.volume = volume;
    }

    /**
     * Creates a new instance of a Bar
     *
     * @param date The date of this bar.
     * @param open The open price.
     * @param high The high price.
     * @param low The low price.
     * @param close The closing price.
     * @param volume The volume for the bar.
     */
    public BarData(LocalDateTime dateTime, double open, double high, double low, double close, long volume) {
        this(null, dateTime, open, high, low, close, volume, 1, LengthUnit.DAY);
    }//constructor()

    /**
     * Creates a new instance of a Bar
     *
     * @param date The date of this bar.
     * @param open The open price.
     * @param high The high price.
     * @param low The low price.
     * @param close The closing price.
     * @param volume The volume for the bar.
     * @param openInterest The open interest for the bar.
     */
    public BarData(LocalDateTime dateTime, double open, double high, double low, double close, long volume, long openInterest) {
        this(dateTime, open, high, low, close, volume);
        this.openInterest = openInterest;
    }//constructor()

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    /**
     * @return the open price of this bar.
     */
    public double getOpen() {
        return open;
    }

    /**
     * @return the High price of this bar.
     */
    public double getHigh() {
        return high;
    }

    /*
     * @return the Low price of this BarData.
     */
    public double getLow() {
        return low;
    }

    /**
     * @return the close price for this bar.
     */
    public double getClose() {
        return close;
    }

    /**
     * @return the Volume for this bar.
     */
    public long getVolume() {
        return volume;
    }

    /**
     * @return the open interest for this bar.
     */
    public long getOpenInterest() {
        return openInterest;
    }

    /**
     * Sets the open price for this bar.
     *
     * @param open The open price for this bar.
     */
    public void setOpen(double open) {
        this.open = open;
    }

    /**
     * Sets the high price for this bar.
     *
     * @param high The high price for this bar.
     */
    public void setHigh(double high) {
        this.high = high;
    }

    /**
     * Sets the low price for this bar.
     *
     * @param low The low price for this bar.
     */
    public void setLow(double low) {
        this.low = low;
    }

    /**
     * Sets the closing price for this bar.
     *
     * @param close The closing price for this bar.
     */
    public void setClose(double close) {
        this.close = close;
    }

    /**
     * Sets the volume for this bar.
     *
     * @param volume Sets the volume for this bar.
     */
    public void setVolume(long volume) {
        this.volume = volume;
    }
    
    
    /**
     * Updates the last price, adjusting the high and low
     * @param close The last price
     */
    public void update( double close ) {
        if( close > high ) {
            high = close;
        }
        
        if( close < low ) {
            low = close;
        }
        this.close = close;
    }

    /**
     * Sets the open interest for this bar.
     *
     * @param openInterest The open interest for this bar.
     */
    public void setOpenInterest(long openInterest) {
        this.openInterest = openInterest;
    }
    

    public Ticker getTicker() {
        return ticker;
    }

    public int getBarLength() {
        return barLength;
    }

    public LengthUnit getLengthUnit() {
        return lengthUnit;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + (this.ticker != null ? this.ticker.hashCode() : 0);
        hash = 23 * hash + (int) (Double.doubleToLongBits(this.open) ^ (Double.doubleToLongBits(this.open) >>> 32));
        hash = 23 * hash + (this.formattedOpen != null ? this.formattedOpen.hashCode() : 0);
        hash = 23 * hash + (int) (Double.doubleToLongBits(this.high) ^ (Double.doubleToLongBits(this.high) >>> 32));
        hash = 23 * hash + (this.formattedHigh != null ? this.formattedHigh.hashCode() : 0);
        hash = 23 * hash + (int) (Double.doubleToLongBits(this.low) ^ (Double.doubleToLongBits(this.low) >>> 32));
        hash = 23 * hash + (this.formattedLow != null ? this.formattedLow.hashCode() : 0);
        hash = 23 * hash + (int) (Double.doubleToLongBits(this.close) ^ (Double.doubleToLongBits(this.close) >>> 32));
        hash = 23 * hash + (this.formattedClose != null ? this.formattedClose.hashCode() : 0);
        hash = 23 * hash + (int) (this.volume ^ (this.volume >>> 32));
        hash = 23 * hash + (int) (this.openInterest ^ (this.openInterest >>> 32));
        hash = 23 * hash + this.barLength;
        hash = 23 * hash + (this.dateTime != null ? this.dateTime.hashCode() : 0);
        hash = 23 * hash + (this.lengthUnit != null ? this.lengthUnit.hashCode() : 0);
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
        final BarData other = (BarData) obj;
        if (this.ticker != other.ticker && (this.ticker == null || !this.ticker.equals(other.ticker))) {
            return false;
        }
        if (Double.doubleToLongBits(this.open) != Double.doubleToLongBits(other.open)) {
            return false;
        }
        if (this.formattedOpen != other.formattedOpen && (this.formattedOpen == null || !this.formattedOpen.equals(other.formattedOpen))) {
            return false;
        }
        if (Double.doubleToLongBits(this.high) != Double.doubleToLongBits(other.high)) {
            return false;
        }
        if (this.formattedHigh != other.formattedHigh && (this.formattedHigh == null || !this.formattedHigh.equals(other.formattedHigh))) {
            return false;
        }
        if (Double.doubleToLongBits(this.low) != Double.doubleToLongBits(other.low)) {
            return false;
        }
        if (this.formattedLow != other.formattedLow && (this.formattedLow == null || !this.formattedLow.equals(other.formattedLow))) {
            return false;
        }
        if (Double.doubleToLongBits(this.close) != Double.doubleToLongBits(other.close)) {
            return false;
        }
        if (this.formattedClose != other.formattedClose && (this.formattedClose == null || !this.formattedClose.equals(other.formattedClose))) {
            return false;
        }
        if (this.volume != other.volume) {
            return false;
        }
        if (this.openInterest != other.openInterest) {
            return false;
        }
        if (this.barLength != other.barLength) {
            return false;
        }
        if (this.dateTime != other.dateTime && (this.dateTime == null || !this.dateTime.equals(other.dateTime))) {
            return false;
        }
        if (this.lengthUnit != other.lengthUnit) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Bar{" + "ticker=" + ticker + ", open=" + open + ", formattedOpen=" + formattedOpen + ", high=" + high + ", formattedHigh=" + formattedHigh + ", low=" + low + ", formattedLow=" + formattedLow + ", close=" + close + ", formattedClose=" + formattedClose + ", volume=" + volume + ", openInterest=" + openInterest + ", barLength=" + barLength + ", dateTime=" + dateTime + ", lengthUnit=" + lengthUnit + '}';
    }

    
    
    
    
}//class BarData
