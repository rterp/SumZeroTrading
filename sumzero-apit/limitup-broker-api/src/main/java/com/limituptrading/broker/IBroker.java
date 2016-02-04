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


package com.limituptrading.broker;

import com.limituptrading.broker.order.OrderEventListener;
import com.limituptrading.broker.order.TradeOrder;
import com.limituptrading.data.ComboTicker;
import com.limituptrading.data.Ticker;
import com.limituptrading.time.TimeUpdatedListener;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * @author Rob Terpilowski
 *
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public interface IBroker {

    /**
     * Cancels the order with the specified ID
     *
     * @param id The id of the order to cancel
     */
    public abstract void cancelOrder(String id);

    /**
     * Cancels the specified order
     *
     * @param order The order to cancel
     */
    public abstract void cancelOrder(TradeOrder order);

    /**
     * places the specified order with the broker
     *
     * @param order The order to place with the broker.
     */
    public abstract void placeOrder(TradeOrder order);

    /**
     * Gets the next order ID, or -1 if there was a problem getting the orderId
     *
     * @return the next order id or -1 if there was a problem getting the
     * orderId
     */
    public abstract String getNextOrderId();

    /**
     * Add a listener to receive OrderEvents.
     *
     * @param listener
     */
    public abstract void addOrderEventListener(OrderEventListener listener);

    /**
     * Remove a listener from receiving order events.
     *
     * @param listener
     */
    public abstract void removeOrderEventListener(OrderEventListener listener);
    
    
    /**
     * Add a listener to receive broker errors
     * @param listener 
     */
    public abstract void addBrokerErrorListener( BrokerErrorListener listener );
    
    /**
     * Remove the specified BrokerErrorListener
     * @param listener 
     */
    public abstract void removeBrokerErrorListener( BrokerErrorListener listener );

    //public abstract void addBrokerListener( BrokerListener listener );
    //public abstract void removeBrokerListener( BrokerListener listener );
    /**
     * Returns a properly formatted string for today's date with the specified
     * hour and minute
     *
     * @param hour
     * @param minute
     * @return a properly formatted string for today's date with the specified
     * hour and minute. Formatted to the broker's specs.
     */
    public abstract String getFormattedDate(int hour, int minute, int second);

    /**
     * Returns a properly formatted string for the specified date
     *
     * @param date The date to format
     * @return A String in the brokers format for the specified date.
     */
    public abstract String getFormattedDate(Date date);

    /**
     * Gets the current date/time from the broker
     *
     * @return the current date/time from the broker.
     */
    public abstract GregorianCalendar getCurrentTime();
    
    
    /**
     * Gets the current Time and Date as a LocalDateTime
     * @return The current date/Time
     */
    public abstract LocalDateTime getCurrentDateTime();
    

    /**
     * Establishes the initial connection to the broker.
     */
    public void connect();

    /**
     * Disconnects from the broker.
     */
    public void disconnect();

    /**
     *
     * @return true if connected to the broker
     */
    public boolean isConnected();

    /**
     * Used to lock the broker in order to create order IDs and transmit orders,
     * since IB does not tolerate orders IDs that are transmitted out of order.
     *
     * @throws InterruptedException
     */
    public void aquireLock();

    /**
     * Used to release the broker from the lock
     *
     * @throws InterruptedException
     */
    public void releaseLock();

    public ComboTicker buildComboTicker(Ticker ticker1, Ticker ticker2);

    public ComboTicker buildComboTicker(Ticker ticker1, int ratio1, Ticker ticker2, int ratio2);

    /**
     * Request the status of a specific order. The request will cause an
     * ExecutionReport message to be fired.
     *
     * @param tradeOrder The order to get the status of
     */
    public void requestOrderStatus(TradeOrder tradeOrder);

    
    /**
     * Request the status of the specified orders. The request will cause an
     * ExecutionReport message to be fired for each order
     *
     * @param tradeOrders The orders to get the status of
     */
    public void requestOrderStatus(List<TradeOrder> tradeOrders);
    
    
    /**
     * Cancels the order with the specified ID, and replaced it with the specified order
     * @param originalOrderId The ID of the order to cancel
     * @param newOrder The order to replace the canceled order with.
     */
    public void cancelAndReplaceOrder(String originalOrderId, TradeOrder newOrder );
    
    
    /**
     * The broker will send out updates once per second so that the trading strategies are 
     * synced off the broker's time, and not from the PC's time.  Useful when backtestings
     * @param listener The listener to update.
     */
    public void addTimeUpdateListener( TimeUpdatedListener listener );
    
    /**
     * Remove the specified timeUpdateListener
     * @param listener 
     */
    public void removeTimeUpdateListener( TimeUpdatedListener listener );

}
