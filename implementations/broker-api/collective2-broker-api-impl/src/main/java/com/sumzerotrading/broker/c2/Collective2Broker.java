/**
 * MIT License
 *
 * Copyright (c) 2015  Rob Terpilowski
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING
 * BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.sumzerotrading.broker.c2;


import com.sumzerotrading.broker.BrokerErrorListener;
import com.sumzerotrading.broker.IBroker;
import com.sumzerotrading.broker.Position;
import com.sumzerotrading.broker.order.OrderEventListener;
import com.sumzerotrading.broker.order.TradeOrder;
import com.sumzerotrading.data.ComboTicker;
import com.sumzerotrading.data.Ticker;
import com.sumzerotrading.j4c2.Collective2Client;
import com.sumzerotrading.j4c2.signal.CancelSignalRequest;
import com.sumzerotrading.j4c2.signal.SubmitSignalRequest;
import com.sumzerotrading.j4c2.signal.SubmitSignalResponse;
import com.sumzerotrading.time.TimeUpdatedListener;
import java.time.ZonedDateTime;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * @author Rob Terpilowski
 */
public class Collective2Broker implements IBroker {
    
    
    protected Collective2Client c2Client;
    protected TradeSignalBuilder signalBuilder;
    protected String systemid;
    protected String apiKey;
    protected Logger logger = Logger.getLogger(Collective2Broker.class);
    
    
    //For unit tests
    protected Collective2Broker() {
        
    }
    
    public Collective2Broker(String apiKey, String systemid ) {
        this.apiKey = apiKey;
        this.systemid = systemid;
        signalBuilder = new TradeSignalBuilder();
        c2Client = new Collective2Client(apiKey);
    }

    @Override
    public void cancelOrder(String id) {
        logger.info("Canceling signal: " + id );
        CancelSignalRequest request = new CancelSignalRequest(systemid, id);
        c2Client.submitCancelSignal(request);
        
    }

    @Override
    public void cancelOrder(TradeOrder order) {
        cancelOrder(order.getOrderId());
    }

    @Override
    public void placeOrder(TradeOrder order) {
        logger.info("Submitting order to C2 Broker: " + order);
        SubmitSignalResponse submitTradeSignalResponse = c2Client.submitTradeSignal(signalBuilder.buildSignalRequest(systemid, order));
        logger.info("C2 parent response: " + submitTradeSignalResponse.toString() );
        order.setOrderId(submitTradeSignalResponse.getSignalid());
        for( TradeOrder child : order.getChildOrders() ) {
            logger.info("Submitting child order");
            SubmitSignalRequest buildSignalRequest = signalBuilder.buildSignalRequest(systemid, child);
            logger.info("C2 Child Signal: " + buildSignalRequest );
            submitTradeSignalResponse = c2Client.submitTradeSignal(buildSignalRequest);
            logger.info("C2 child response: " + submitTradeSignalResponse.toString() );
            child.setOrderId(submitTradeSignalResponse.getSignalid());
        }
    }
    
    

    /**
     * Always returns an empty string.  Collective2 automatically assigns an order id when 
     * the order is submitted
     * @return an empty string.
     */
    @Override
    public String getNextOrderId() {
        return "";
    }

    /**
     * Currently does nothing
     * @param listener 
     */
    @Override
    public void addOrderEventListener(OrderEventListener listener) {
    }

    /**
     * Currently does nothing
     * @param listener 
     */
    @Override
    public void removeOrderEventListener(OrderEventListener listener) {
    }

    /**
     * Currently does nothing
     * @param listener 
     */
    @Override
    public void addBrokerErrorListener(BrokerErrorListener listener) {
    }

    
    /**
     * Currently does nothing
     * @param listener 
     */
    @Override
    public void removeBrokerErrorListener(BrokerErrorListener listener) {
    }

    @Override
    public String getFormattedDate(int hour, int minute, int second) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getFormattedDate(ZonedDateTime date) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ZonedDateTime getCurrentTime() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Does nothing
     */
    @Override
    public void connect() {
        //does nothing
    }

    
    /**
     * Does nothing
     */
    @Override
    public void disconnect() {
        //does nothing
    }

    /**
     * Always returns true
     * @return true
     */
    @Override
    public boolean isConnected() {
        return true;
    }

    
    /**
     * Does nothing
     */
    @Override
    public void aquireLock() {
    }

    
    /**
     * Does nothing
     */
    @Override
    public void releaseLock() {
    }

    @Override
    public ComboTicker buildComboTicker(Ticker ticker1, Ticker ticker2) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ComboTicker buildComboTicker(Ticker ticker1, int ratio1, Ticker ticker2, int ratio2) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public TradeOrder requestOrderStatus(String orderId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<TradeOrder> getOpenOrders() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void cancelAndReplaceOrder(String originalOrderId, TradeOrder newOrder) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addTimeUpdateListener(TimeUpdatedListener listener) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeTimeUpdateListener(TimeUpdatedListener listener) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Position> getAllPositions() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


}