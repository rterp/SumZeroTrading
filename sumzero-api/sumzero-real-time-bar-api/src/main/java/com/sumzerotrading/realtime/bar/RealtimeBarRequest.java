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
import com.sumzerotrading.historicaldata.IHistoricalDataProvider;
import com.sumzerotrading.historicaldata.IHistoricalDataProvider.ShowProperty;
import java.util.Objects;

/**
 *
 * @author Rob Terpilowski
 */
public class RealtimeBarRequest {
    
    protected int requestId;
    protected Ticker ticker;
    protected int timeInterval;
    protected LengthUnit timeUnit;
    protected IHistoricalDataProvider.ShowProperty showProperty = IHistoricalDataProvider.ShowProperty.TRADES;

    
    public RealtimeBarRequest(int requestId, Ticker ticker, int timeInteval, LengthUnit timeUnit) {
        this.requestId = requestId;
        this.ticker = ticker;
        this.timeInterval = timeInteval;
        this.timeUnit = timeUnit;
    }
    
    public RealtimeBarRequest(int requestId, Ticker ticker, int timeInterval, LengthUnit timeUnit, ShowProperty showProperty ) {
        this(requestId, ticker, timeInterval, timeUnit);
        this.showProperty = this.showProperty;
    }

    public int getRequestId() {
        return requestId;
    }

    public Ticker getTicker() {
        return ticker;
    }

    public int getTimeInterval() {
        return timeInterval;
    }

    public LengthUnit getTimeUnit() {
        return timeUnit;
    }

    public ShowProperty getShowProperty() {
        return showProperty;
    }

    public void setShowProperty(ShowProperty showProperty) {
        this.showProperty = showProperty;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + this.requestId;
        hash = 79 * hash + Objects.hashCode(this.ticker);
        hash = 79 * hash + this.timeInterval;
        hash = 79 * hash + Objects.hashCode(this.timeUnit);
        hash = 79 * hash + Objects.hashCode(this.showProperty);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RealtimeBarRequest other = (RealtimeBarRequest) obj;
        if (this.requestId != other.requestId) {
            return false;
        }
        if (this.timeInterval != other.timeInterval) {
            return false;
        }
        if (!Objects.equals(this.ticker, other.ticker)) {
            return false;
        }
        if (this.timeUnit != other.timeUnit) {
            return false;
        }
        if (this.showProperty != other.showProperty) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "RealtimeBarRequest{" + "requestId=" + requestId + ", ticker=" + ticker + ", timeInteval=" + timeInterval + ", timeUnit=" + timeUnit + ", showProperty=" + showProperty + '}';
    }
    
}
