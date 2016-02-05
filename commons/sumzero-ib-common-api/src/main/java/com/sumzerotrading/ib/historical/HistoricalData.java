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


package com.sumzerotrading.ib.historical;

import java.util.GregorianCalendar;

/**
 *
 * @author Rob Terpilowski
 */
public class HistoricalData {
    
    
   protected int requestId;
   protected GregorianCalendar date;
   protected double open;
   protected double high;
   protected double low;
   protected double close;
   protected int volume;
   protected int count;
   protected double wap;
   protected boolean hasGaps;

    
    public HistoricalData(int requestId, GregorianCalendar date, double open, double high, double low, double close, int volume, int count, double wap, boolean hasGaps) {
        this.requestId = requestId;
        this.date = date;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.volume = volume;
        this.count = count;
        this.wap = wap;
        this.hasGaps = hasGaps;
    }

    public double getClose() {
        return close;
    }

    public int getCount() {
        return count;
    }

    public GregorianCalendar getDate() {
        return date;
    }

    public boolean isHasGaps() {
        return hasGaps;
    }

    public double getHigh() {
        return high;
    }

    public double getLow() {
        return low;
    }

    public double getOpen() {
        return open;
    }

    public int getRequestId() {
        return requestId;
    }

    public int getVolume() {
        return volume;
    }

    public double getWap() {
        return wap;
    }

    @Override
    public String toString() {
        return "HistoricalData{" + "requestId=" + requestId + ", date=" + date + ", open=" + open + ", high=" + high + ", low=" + low + ", close=" + close + ", volume=" + volume + ", count=" + count + ", wap=" + wap + ", hasGaps=" + hasGaps + '}';
    }
   
   
   
}
