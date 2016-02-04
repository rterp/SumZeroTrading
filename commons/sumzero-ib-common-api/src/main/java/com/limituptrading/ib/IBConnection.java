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


package com.zerosumtrading.ib;

import com.ib.client.Contract;
import com.ib.client.ContractDetails;
import com.ib.client.EClientSocket;
import com.ib.client.Execution;
import com.ib.client.Order;
import com.ib.client.OrderState;
import com.ib.client.TickType;
import com.zerosumtrading.ib.historical.ContractDetailsListener;
import com.zerosumtrading.ib.historical.HistoricalDataListener;
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
//import org.apache.log4j.Logger;

/**
 *
 * @author Rob Terpilowski
 */
public class IBConnection implements IBConnectionInterface {
    
    private static IBConnectionInterface connection = null;
    
    protected List<TickListener> tickListeners = new ArrayList<TickListener>();
    protected List<MarketDepthListener> marketDepthListeners = new ArrayList<MarketDepthListener>();
    protected List<OrderStatusListener> orderStatusListeners = new ArrayList<OrderStatusListener>();
    protected List<HistoricalDataListener> historicalDataListeners = new ArrayList<HistoricalDataListener>();
    protected List<TimeListener> timeListeners = new ArrayList<TimeListener>();
    protected List<ContractDetailsListener> contractDetailsListeners = new ArrayList<ContractDetailsListener>();
    protected List<ErrorListener> errorListeners = new ArrayList<>();
    protected EClientSocket eclientSocket;
    //  protected Logger logger;
    protected String host;
    protected int port;
    protected int clientId;
    
    public synchronized static IBConnectionInterface getInstance() {
        if (connection == null) {
            connection = new IBConnection();
        }
        return connection;
    }
    
    public IBConnection() {
        // logger = Logger.getLogger( getClass() );
        eclientSocket = new EClientSocket(this);
    }
    
    public void bondContractDetails(ContractDetails contractDetails) {
        //Do nothing for now.
    }
    
    public void contractDetails(ContractDetails contractDetails) {
        fireContractDetailsEvent(contractDetails);
    }
    
    public void currentTime(long time) {
        GregorianCalendar date = new GregorianCalendar();
        date.setTimeInMillis(time * 1000);
        fireTimeEvent(date);
    }
    
    public void execDetails(int orderId, Contract contract, Execution execution) {
        fireExecDetails(orderId, contract, execution);
    }
    
    public void historicalData(int reqId, String date, double open, double high, double low, double close, int volume, int count, double WAP, boolean hasGaps) {
        fireHistoricalDataEvent(reqId, date, open, high, low, close, volume, count, WAP, hasGaps);
    }
    
    public void managedAccounts(String accountsList) {
        //Do nothing for now.
    }
    
    public void nextValidId(int orderId) {
        fireNextOrderIdEvent(orderId);
    }
    
    public void openOrder(int orderId, Contract contract, Order order, OrderState orderState) {
        fireOpenOrderEvent(orderId, contract, order, orderState);
    }
    
    public void orderStatus(int orderId, String status, int filled, int remaining, double avgFillPrice, int permId, int parentId, double lastFillPrice, int clientId, String whyHeld) {
        fireOrderStatusEvent(orderId, status, filled, remaining, avgFillPrice, permId, parentId, lastFillPrice, clientId, whyHeld);
    }
    
    public void realtimeBar(int reqId, long time, double open, double high, double low, double close, long volume, double wap, int count) {
        //Do nothing for now.
    }
    
    public void receiveFA(int faDataType, String xml) {
        //Do nothing for now.
    }
    
    public void scannerData(int reqId, int rank, ContractDetails contractDetails, String distance, String benchmark, String projection, String legsStr) {
        //Do nothing for now.
    }
    
    public void scannerDataEnd(int reqId) {
        //Do nothing for now.
    }
    
    public void scannerParameters(String xml) {
        //Do nothing for now.
    }
    
    public void tickEFP(int tickerId, int tickType, double basisPoints, String formattedBasisPoints, double impliedFuture, int holdDays, String futureExpiry, double dividendImpact, double dividendsToExpiry) {
        //Do nothing for now.
    }
    
    public void tickGeneric(int tickerId, int tickType, double value) {
        System.out.println(" ");
        //Do nothing for now.
    }
    
    public void tickOptionComputation(int tickerId, int field, double impliedVol, double delta, double modelPrice, double pvDividend) {
        //Do nothing for now.
    }
    
    public void tickPrice(int tickerId, int field, double price, int canAutoExecute) {
        synchronized (tickListeners) {
            for (TickListener listener : tickListeners) {
                listener.tickPrice(tickerId, field, price, canAutoExecute);
            }
        }
    }
    
    public void tickSize(int tickerId, int field, int size) {
        synchronized (tickListeners) {
            for (TickListener listener : tickListeners) {
                listener.tickSize(tickerId, field, size);
            }
        }
    }
    
    public void tickString(int tickerId, int tickType, String value) {
        System.out.println("");
    }
    
    public void updateAccountTime(String timeStamp) {
        //Do nothing for now.
    }
    
    public void updateAccountValue(String key, String value, String currency, String accountName) {
        //Do nothing for now.
    }
    
    public void updateMktDepth(int tickerId, int position, int operation, int side, double price, int size) {
        fireMarketDepthEvent(tickerId, position, operation, side, price, size);
    }
    
    public void updateMktDepthL2(int tickerId, int position, String marketMaker, int operation, int side, double price, int size) {
        fireMarketDepth2Event(tickerId, position, marketMaker, operation, side, price, size);
    }
    
    public void updateNewsBulletin(int msgId, int msgType, String message, String origExchange) {
        //Do nothing for now.
    }
    
    public void updatePortfolio(Contract contract, int position, double marketPrice, double marketValue, double averageCost, double unrealizedPNL, double realizedPNL, String accountName) {
        //Do nothing for now.
    }
    
    public void connectionClosed() {
        //Do nothing for now.
    }
    
    public void error(Exception e) {
        fireErrorEvent(e);
    }
    
    public void error(String str) {
        fireErrorEvent(str);
    }
    
    public void error(int id, int errorCode, String errorMsg) {
        fireErrorEvent(id, errorCode, errorMsg);
    }
    
    protected void fireHistoricalDataEvent(int reqId, String date, double open, double high, double low, double close, int volume, int count, double WAP, boolean hasGaps) {
        synchronized (historicalDataListeners) {
            for (HistoricalDataListener listener : historicalDataListeners) {
                listener.historicalData(reqId, date, open, high, low, close, volume, count, WAP, true);
            }
        }
    }
    
    protected void fireExecDetails(int orderId, Contract contract, Execution execution) {
        synchronized (orderStatusListeners) {
            for (OrderStatusListener listener : orderStatusListeners) {
                listener.execDetails(orderId, contract, execution);
            }
        }        
    }
    
    protected void fireOpenOrderEvent(int orderId, Contract contract, Order order, OrderState orderState) {
        synchronized (orderStatusListeners) {
            for (OrderStatusListener listener : orderStatusListeners) {
                listener.openOrder(orderId, contract, order, orderState);
            }
        }        
    }
    
    protected void fireOrderStatusEvent(int orderId, String status, int filled, int remaining, double avgFillPrice, int permId, int parentId, double lastFillPrice, int clientId, String whyHeld) {
        synchronized (orderStatusListeners) {
            for (OrderStatusListener listener : orderStatusListeners) {
                listener.orderStatus(orderId, status, filled, remaining, avgFillPrice, permId, parentId, lastFillPrice, clientId, whyHeld);
            }
        }
    }
    
    protected void fireNextOrderIdEvent(int nextOrderId) {
        synchronized (orderStatusListeners) {
            for (OrderStatusListener listener : orderStatusListeners) {
                listener.nextValidId(nextOrderId);
            }
        }
    }
    
    protected void fireErrorEvent(Exception e) {
        fireErrorEvent(errorListeners, e);
        fireErrorEvent(tickListeners, e);
        fireErrorEvent(orderStatusListeners, e);
    }    
    
    protected void fireErrorEvent(List<? extends ErrorListener> listeners, Exception e) {
        synchronized (listeners) {
            for (ErrorListener listener : listeners) {
                listener.error(e);
            }
        }
    }
    
    protected void fireErrorEvent(String errorString) {
        fireErrorEvent(tickListeners, errorString);
        fireErrorEvent(orderStatusListeners, errorString);
    }    
    
    protected void fireErrorEvent(List<? extends ErrorListener> listeners, String str) {
        synchronized (listeners) {
            for (ErrorListener listener : listeners) {
                listener.error(str);
            }
        }
    }    
    
    protected void fireErrorEvent(int id, int errorCode, String errorMsg) {
        fireErrorEvent(tickListeners, id, errorCode, errorMsg);
        fireErrorEvent(orderStatusListeners, id, errorCode, errorMsg);
    }    
    
    protected void fireErrorEvent(List<? extends ErrorListener> listeners, int id, int errorCode, String errorMsg) {
        synchronized (listeners) {
            for (ErrorListener listener : listeners) {
                listener.error(id, errorCode, errorMsg);
            }
        }
    }
    
    protected void fireTimeEvent(GregorianCalendar time) {
        synchronized (timeListeners) {
            for (TimeListener listener : timeListeners) {
                listener.timeReceived(time);
            }
        }
    }
    
    protected void fireMarketDepthEvent(int tickerId, int position, int operation, int side, double price, int size) {
        synchronized (marketDepthListeners) {
            for (MarketDepthListener listener : marketDepthListeners) {
                listener.updateMktDepth(tickerId, position, operation, side, price, size);
            }
        }
    }    
    
    protected void fireMarketDepth2Event(int tickerId, int position, String marketMaker, int operation, int side, double price, int size) {
        synchronized (marketDepthListeners) {
            for (MarketDepthListener listener : marketDepthListeners) {
                listener.updateMktDepth2(tickerId, position, marketMaker, operation, side, price, size);
            }
        }
    }    
    
    protected void fireContractDetailsEvent(ContractDetails details) {
        synchronized (contractDetailsListeners) {
            for (ContractDetailsListener listener : contractDetailsListeners) {
                listener.contractDetailsReceived(details);
            }
        }
    }
    
    public void addTickListener(TickListener listener) {
        synchronized (tickListeners) {
            tickListeners.add(listener);
        }
    }
    
    public void removeTickListener(TickListener listener) {
        synchronized (tickListeners) {
            tickListeners.remove(listener);
        }
    }
    
    public void addErrorListener( ErrorListener listener ) {
        synchronized(errorListeners) {
            errorListeners.add(listener);
        }
    }
    
    public void removeErrorListener( ErrorListener listener ) {
        synchronized(errorListeners ) {
            errorListeners.remove(listener);
        }
    }
    
    
    
    public void addMarketDepthListener(MarketDepthListener listener) {
        synchronized (marketDepthListeners) {
            marketDepthListeners.add(listener);
        }
    }
    
    public void removeMarketDepthListener(MarketDepthListener listener) {
        synchronized (marketDepthListeners) {
            marketDepthListeners.remove(listener);
        }
    }
    
    public void addOrderStatusListener(OrderStatusListener listener) {
        synchronized (orderStatusListeners) {
            orderStatusListeners.add(listener);
        }
    }
    
    public void removeOrderStatusListener(OrderStatusListener listener) {
        synchronized (orderStatusListeners) {
            orderStatusListeners.remove(listener);
        }
    }
    
    public void addHistoricalDataListener(HistoricalDataListener listener) {
        synchronized (historicalDataListeners) {
            historicalDataListeners.add(listener);
        }
    }
    
    public void removeHistoricalDataListener(HistoricalDataListener listener) {
        synchronized (historicalDataListeners) {
            historicalDataListeners.remove(listener);
        }
    }
    
    public void addTimeListener(TimeListener listener) {
        synchronized (timeListeners) {
            timeListeners.add(listener);
        }
    }
    
    public void removeTimeListener(TimeListener listener) {
        synchronized (timeListeners) {
            timeListeners.remove(listener);
        }
    }
    
    public void addContractDetailsListener(ContractDetailsListener listener) {
        synchronized (contractDetailsListeners) {
            contractDetailsListeners.add(listener);
        }
    }
    
    public void removeContractDetailsListener(ContractDetailsListener listener) {
        synchronized (contractDetailsListeners) {
            contractDetailsListeners.remove(listener);
        }
    }
    
    @Override
    public String getHost() {
        return host;
    }
    
    public void setHost(String host) {
        this.host = host;
    }
    
    @Override
    public int getPort() {
        return port;
    }
    
    public void setPort(int port) {
        this.port = port;
    }
    
    @Override
    public int getClientId() {
        return clientId;
    }
    
    public void setClientId(int clientId) {
        this.clientId = clientId;
    }
    
}
