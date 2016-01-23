/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.limituptrading.marketdata.timeAndSales;

import com.limituptrading.data.Ticker;
import java.util.Date;
import java.util.List;

/**
 * Subscribes to time and sales details.
 * 
 * @author robbob
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
