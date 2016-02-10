/**
 MIT License

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

package com.sumzerotrading.realtime.bar;

import com.sumzerotrading.data.BarData.LengthUnit;
import com.sumzerotrading.data.Ticker;

/**
 *
 * @author Rob Terpilowski
 */
public class RealtimeBarRequest {
    
    protected int requestId;
    protected Ticker ticker;
    protected int timeInteval;
    protected LengthUnit timeUnit;

    
    public RealtimeBarRequest(int requestId, Ticker ticker, int timeInteval, LengthUnit timeUnit) {
        this.requestId = requestId;
        this.ticker = ticker;
        this.timeInteval = timeInteval;
        this.timeUnit = timeUnit;
    }

    public int getRequestId() {
        return requestId;
    }

    public Ticker getTicker() {
        return ticker;
    }

    public int getTimeInteval() {
        return timeInteval;
    }

    public LengthUnit getTimeUnit() {
        return timeUnit;
    }

    
    
    
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RealtimeBarRequest other = (RealtimeBarRequest) obj;
        if (this.ticker != other.ticker && (this.ticker == null || !this.ticker.equals(other.ticker))) {
            return false;
        }
        if (this.timeInteval != other.timeInteval) {
            return false;
        }
        if (this.timeUnit != other.timeUnit) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + (this.ticker != null ? this.ticker.hashCode() : 0);
        hash = 61 * hash + this.timeInteval;
        hash = 61 * hash + (this.timeUnit != null ? this.timeUnit.hashCode() : 0);
        return hash;
    }
    
    
    
    
}
