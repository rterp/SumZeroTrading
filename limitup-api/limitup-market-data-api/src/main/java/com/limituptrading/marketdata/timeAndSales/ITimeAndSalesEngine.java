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

package com.limituptrading.marketdata.timeAndSales;

import com.limituptrading.data.Ticker;
import java.util.Date;
import java.util.List;

/**
 * Subscribes to time and sales details.
 * 
 * @author Rob Terpilowski
 */
public interface ITimeAndSalesEngine {
    
    /**
     * Gets historical time and sales details.
     * @param ticker The ticker for which to fetch the time and sales.
     * @param startTime The start time of the query.  The query will pull back everything from the start time to the current time
     * @return A list of TimeAndSalesDetail objects.
     */
    public List<TimeAndSalesDetail> getHistoricalTimeAndSales( Ticker ticker, Date startTime );
 
    
    /**
     * Gets updated time and sales details as they are published
     * @param ticker The ticker for which to subscribe to time and sales.
     * @param listener The listener that will listen for time and sales events. 
     */
    public void subscribeToTimeAndSales( Ticker ticker, TimeAndSalesListener listener );
    
    
}
