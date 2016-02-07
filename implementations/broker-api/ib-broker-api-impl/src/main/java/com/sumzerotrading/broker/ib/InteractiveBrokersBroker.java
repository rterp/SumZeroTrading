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

package com.sumzerotrading.broker.ib;

import com.ib.client.ClientSocketInterface;
import com.ib.client.Contract;
import com.ib.client.ContractDetails;
import com.ib.client.Execution;
import com.ib.client.Order;
import com.ib.client.OrderState;
import com.sumzerotrading.broker.BrokerError;
import com.sumzerotrading.broker.BrokerErrorListener;
import com.sumzerotrading.broker.IBroker;
import com.sumzerotrading.broker.order.OrderEvent;
import com.sumzerotrading.broker.order.OrderEventListener;
import com.sumzerotrading.broker.order.OrderStatus;
import com.sumzerotrading.broker.order.TradeOrder;
import com.sumzerotrading.data.ComboTicker;
import com.sumzerotrading.data.InstrumentType;
import com.sumzerotrading.data.Ticker;
import com.sumzerotrading.ib.ContractBuilderFactory;
import com.sumzerotrading.ib.IBConnectionInterface;
import com.sumzerotrading.ib.IBSocket;
import com.sumzerotrading.ib.IbUtils;
import com.sumzerotrading.ib.OrderStatusListener;
import com.sumzerotrading.ib.TimeListener;
import com.sumzerotrading.ib.historical.ContractDetailsListener;
import com.sumzerotrading.time.TimeUpdatedListener;
import com.sumzerotrading.util.QuoteUtil;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
//import org.apache.log4j.Logger;

/**
 * Supported Order types are: Market, Stop and Limit Supported order parameters 
 * are parent/child, OCA, Good-after-time, good-till-date. Supported
 * Time-in-force: DAY, Good-till-canceled, Good-till-time, Immediate-or-cancel
 *
 * @author Rob Terpilowski
 */
public class InteractiveBrokersBroker implements IBroker, OrderStatusListener, TimeListener, ContractDetailsListener {

    protected ClientSocketInterface ibConnection;
    protected IBSocket ibSocket;
    protected IBConnectionInterface callbackInterface;
    protected Set<TradeOrder> currencyOrderList = new HashSet<TradeOrder>();
    protected BlockingQueue<Integer> nextIdQueue = new LinkedBlockingQueue<Integer>();
    protected BlockingQueue<GregorianCalendar> brokerTimeQueue = new LinkedBlockingQueue<GregorianCalendar>();
    protected BlockingQueue<BrokerError> brokerErrorQueue = new LinkedBlockingQueue<BrokerError>();
    protected BlockingQueue<OrderEvent> orderEventQueue = new LinkedBlockingQueue<OrderEvent>();
    protected BlockingQueue<ContractDetails> contractDetailsQueue = new LinkedBlockingDeque<ContractDetails>();
    // protected Logger logger;
    protected int nextOrderId = -1;
    protected SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
    protected Map<String, TradeOrder> openOrderMap = new HashMap<String, TradeOrder>();
    protected List<OrderEventListener> orderEventListeners = new ArrayList<OrderEventListener>();
    protected IBOrderEventProcessor orderProcessor;
    protected Set<String> filledOrderSet = new HashSet<String>();
    protected Timer currencyOrderTimer;
    protected Object lock = new Object();
    protected Semaphore semaphore = new Semaphore(1);
    protected boolean started = false;

    public InteractiveBrokersBroker(IBSocket ibSocket) {
        this.ibSocket = ibSocket;

        callbackInterface = ibSocket.getConnection();
        ibConnection = ibSocket.getClientSocket();

        callbackInterface.addOrderStatusListener(this);
        callbackInterface.addTimeListener(this);
        callbackInterface.addContractDetailsListener(this);
        //    logger = Logger.getLogger(getClass());
        orderProcessor = new IBOrderEventProcessor(orderEventQueue, this);
        currencyOrderTimer = new Timer(true);
        currencyOrderTimer.schedule(getCurrencyOrderMonitor(), 0, 1000 * 60);
    }

    public LocalDateTime getCurrentDateTime() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void addTimeUpdateListener(TimeUpdatedListener listener) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void removeTimeUpdateListener(TimeUpdatedListener listener) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    
    @Override
    public boolean isConnected() {
        return ibSocket.isConnected();
    }

    public void connect() {
        if (!isConnected()) {
            ibSocket.connect();
        }
        if (!started) {
            orderProcessor.startProcessor();
            started = true;
        }
    }

    public void disconnect() {
        if (started) {
            orderProcessor.stopProcessor();
        }
    }

    public void execDetails(int orderId, Contract contract, Execution execution) {
        //not currently implemented
    }

    public void nextValidId(int orderId) {
        try {
            nextIdQueue.put(orderId);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void openOrder(int orderId, Contract contract, Order order, OrderState orderState) {
        //not currently implemented;
    }

    public void timeReceived(GregorianCalendar time) {
        try {
            brokerTimeQueue.put(time);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void orderStatus(int orderId, String status, int filled, int remaining, double avgFillPrice, int permId, int parentId, double lastFillPrice, int clientId, String whyHeld) {

        TradeOrder order = openOrderMap.get(Integer.toString(orderId));
        if (order == null) {
            System.out.println("Open Order with ID: " + orderId + " not found");
            //logger.error();
            return;
        }
        try {
            OrderEvent event = OrderManagmentUtil.createOrderEvent(order, status, filled, remaining, avgFillPrice, permId, parentId, lastFillPrice, clientId, whyHeld);
            if (event.getOrderStatus().getStatus() == OrderStatus.Status.FILLED) {
                if (filledOrderSet.contains(event.getOrder().getOrderId())) {
                    filledOrderSet.remove(event.getOrder().getOrderId());
                    return;
                } else {
                    filledOrderSet.add(event.getOrder().getOrderId());
                }
            }
            orderEventQueue.put(event);
        } catch (Exception ex) {
            ex.printStackTrace();
            //logger.error(ex, ex);
        }
    }

    public void addOrderEventListener(OrderEventListener listener) {
        synchronized (orderEventListeners) {
            orderEventListeners.add(listener);
        }
    }

    public void removeOrderEventListener(OrderEventListener listener) {
        synchronized (orderEventListeners) {
            orderEventListeners.remove(listener);
        }
    }

    public void error(Exception e) {
        putOnErrorQueue(new BrokerError(e));
    }

    public void error(String str) {
        putOnErrorQueue(new BrokerError(str));
    }

    public void error(int id, int errorCode, String errorMsg) {
        putOnErrorQueue(new BrokerError(id, errorCode, errorMsg));
    }

    protected void putOnErrorQueue(BrokerError error) {
        try {
            brokerErrorQueue.put(error);
        } catch (Exception ex) {
            //logger.error(ex, ex);
            ex.printStackTrace();
        }
    }

    public void cancelOrder(String idString) {
        int id = Integer.parseInt(idString);
        ibConnection.cancelOrder(id);
    }

    public void cancelOrder(TradeOrder order) {
        int id = Integer.parseInt(order.getOrderId());
        ibConnection.cancelOrder(id);
    }

    public GregorianCalendar getCurrentTime() {
        ibConnection.reqCurrentTime();
        try {
            return brokerTimeQueue.poll(2, TimeUnit.SECONDS);
        } catch (InterruptedException ex) {
            System.out.println("Time Out waiting to get current time from broker, returning local current time");
            //logger.error("Time Out waiting to get current time from broker, returning local current time");
            return new GregorianCalendar();
        }
    }

    public String getFormattedDate(int hour, int minute, int second) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getFormattedDate(Date date) {
        return dateFormatter.format(date);
    }

    public synchronized String getNextOrderId() {
        if (nextOrderId == -1) {
            try {
                ibConnection.reqIds(1);
                nextOrderId = nextIdQueue.take();
                return nextOrderId + "";
            } catch (InterruptedException ex) {
                ex.printStackTrace();
                return -1 + "";
            }
        }
        return ++nextOrderId + "";
    }

    public void placeOrder(TradeOrder order) {
        if (order.getTicker().getInstrumentType() == InstrumentType.FOREX) {
            //IDEALPro is closed from 14:00-14:15 PST, and does not 
            //accept orders from 14:00-14:05, queue orders that are placed
            //during that time.
            if (isIdealProClosed()) {
                queueOrder(order);
                return;
            }
        }

        List<IbOrderAndContract> orders = buildOrderAndContract(order);
        orders.get(orders.size() - 1).getOrder().m_transmit = true;
        for (IbOrderAndContract ibOrder : orders) {
            ibConnection.placeOrder(ibOrder.getOrder().m_orderId, ibOrder.getContract(), ibOrder.getOrder());
        }
    }

    public void aquireLock() {
        try {
            semaphore.acquire();
        } catch (InterruptedException ex) {
            throw new IllegalStateException(ex);
        }
    }

    public void releaseLock() {
        semaphore.release();
    }

    public ComboTicker buildComboTicker(Ticker ticker1, Ticker ticker2) {
        return buildComboTicker(ticker1, 1, ticker2, 1);
    }

    public ComboTicker buildComboTicker(Ticker ticker1, int ratio1, Ticker ticker2, int ratio2) {
        ContractDetails details1 = getContractDetails(ticker1);
        ContractDetails details2 = getContractDetails(ticker2);

        ComboTicker combo = new ComboTicker(ticker1, ticker2);
        combo.setContractId1(details1.m_summary.m_conId);
        combo.setFirstTickerRatio(ratio1);
        combo.setContractId2(details2.m_summary.m_conId);
        combo.setSecondTickerRatio(ratio2);

        return combo;
    }

    public void contractDetailsReceived(ContractDetails details) {
        try {
            contractDetailsQueue.put(details);
        } catch (Exception ex) {
//logger.error(ex, ex);
            ex.printStackTrace();
        }
    }

    public ContractDetails getContractDetails(Ticker ticker) {
        final Contract contract = ContractBuilderFactory.getContractBuilder(ticker).buildContract(ticker);
//        Thread thread = new Thread( new Runnable() {
//            public void run() {
//                ibConnection.reqContractDetails(contract);
//            }
//        });
//        thread.start();
        ibConnection.reqContractDetails(contract);
        try {
            return contractDetailsQueue.poll(2, TimeUnit.SECONDS);
        } catch (InterruptedException ex) {
//logger.error(ex, ex);
            ex.printStackTrace();
            throw new IllegalStateException(ex);
        }
    }

    
    @Override
    public void requestOrderStatus(TradeOrder tradeOrder) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void requestOrderStatus(List<TradeOrder> tradeOrders) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    

    protected void queueOrder(TradeOrder order) {
        currencyOrderList.add(order);
    }

    protected void processCurrencyOrders() {
        if (currencyOrderList.isEmpty()) {
            return;
        }

        if (!isIdealProClosed()) {
            synchronized (currencyOrderList) {
                for (TradeOrder order : currencyOrderList) {
                    placeOrder(order);
                }
                currencyOrderList.clear();
            }
        }
    }

    protected boolean isIdealProClosed() {
//        GregorianCalendar currentTime = getCurrentTime();
//        return (currentTime.get( Calendar.HOUR_OF_DAY ) == 14 &&
//            currentTime.get( Calendar.MINUTE ) < 5 );
        return false;
    }

    protected TimerTask getCurrencyOrderMonitor() {
        return new TimerTask() {
            @Override
            public void run() {
                processCurrencyOrders();
            }
        };
    }

    protected void fireOrderEvent(OrderEvent event) {
        synchronized (orderEventListeners) {
            for (OrderEventListener listener : orderEventListeners) {
                if (listener.getOrderEventFilter().matches(event.getOrder().getOrderId())) {
                    listener.orderEvent(event);
                }
            }
        }
    }

//    
//    protected IbOrderAndContract buildComboOrderAndContract(TradeOrder order) {
//        TradeOrder comboOrder = order.getComboOrder();
//        
//        if( comboOrder == null ) {
//            throw new IllegalStateException( "Order ID: " + order.getOrderId() + " is not a combo order" );
//        }
//              
//        
//        openOrderMap.put( order.getOrderId(), order );
//        openOrderMap.put( comboOrder.getOrderId(), comboOrder);
//        
//        Contract leg1Contract = ContractBuilderFactory.getContractBuilder(order.getTicker()).buildContract(order.getTicker());
//        Contract leg2Contract = ContractBuilderFactory.getContractBuilder(comboOrder.getTicker()).buildContract(comboOrder.getTicker());
//        
//        ibConnection.reqContractDetails(leg1Contract);
//        
//        
//        
//        return null;
//        
//        
//    }
    protected List<IbOrderAndContract> buildOrderAndContract(TradeOrder order) {
        List<IbOrderAndContract> orderList = new ArrayList<IbOrderAndContract>();
        openOrderMap.put(order.getOrderId(), order);

        Contract contract = ContractBuilderFactory.getContractBuilder(order.getTicker()).buildContract(order.getTicker());

        Order ibOrder = new Order();

        ibOrder.m_action = IbUtils.getAction(order.getTradeDirection());
        ibOrder.m_orderType = IbUtils.getOrderType(order.getType());
        ibOrder.m_tif = IbUtils.getTif(order.getDuration());
        ibOrder.m_orderId = Integer.parseInt(order.getOrderId());
        ibOrder.m_totalQuantity = order.getSize();

        if (order.getGoodAfterTime() != null) {
            ibOrder.m_goodAfterTime = getFormattedDate(order.getGoodAfterTime());
        }

        if (order.getParentOrderId() != null && order.getParentOrderId().length() > 0) {
            ibOrder.m_parentId = Integer.parseInt(order.getParentOrderId());
        }

        if (order.getDuration() == TradeOrder.Duration.GOOD_UTNIL_TIME) {
            if (order.getGoodUntilTime() != null) {
                ibOrder.m_goodTillDate = getFormattedDate(order.getGoodUntilTime());
            } else {
                throw new IllegalStateException("Duration is good-until-time, but no time has been set in the Order");
            }
        }

        if (order.getType() == TradeOrder.Type.LIMIT) {
            if (order.getLimitPrice() == null) {
                throw new IllegalStateException("Limit price not set for LMT order: " + order.getOrderId());
            }
            double limitPrice = QuoteUtil.getBigDecimalValue(order.getTicker(), order.getLimitPrice()).doubleValue();
            ibOrder.m_lmtPrice = limitPrice;
        } else if (order.getType() == TradeOrder.Type.STOP) {
            if (order.getStopPrice() == null) {
                throw new IllegalStateException("Stop price not set for StopLoss order: " + order.getOrderId());
            }
            double stopPrice = QuoteUtil.getBigDecimalValue(order.getTicker(), order.getStopPrice()).doubleValue();
            ibOrder.m_auxPrice = stopPrice;
        }

        if (order.getOcaGroup() != null) {
            ibOrder.m_ocaGroup = order.getOcaGroup();
        }

        if (order.getReference() != null) {
            ibOrder.m_orderRef = order.getReference();
        }

        //If the order has already been submitted, have TWS transmit immediately, otherwise this order may be 
        //attched to another order which needs to be submitted first.
        ibOrder.m_transmit = order.isSubmitted();

        //the order has now been submitted.
        order.setSubmitted(true);

        orderList.add(new IbOrderAndContract(ibOrder, contract));
        for (TradeOrder tradeOrder : order.getChildOrders()) {
            orderList.addAll(buildOrderAndContract(tradeOrder));
        }

        if (order.isSubmitChildOrdersFirst()) {
            Collections.reverse(orderList);
        }

        return orderList;
    }

    public void addBrokerErrorListener(BrokerErrorListener listener) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void removeBrokerErrorListener(BrokerErrorListener listener) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void cancelAndReplaceOrder(String originalOrderId, TradeOrder newOrder) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    

    protected static class IbOrderAndContract {

        protected Order order;
        protected Contract contract;

        protected IbOrderAndContract(Order order, Contract contract) {
            this.order = order;
            this.contract = contract;
        }

        public Contract getContract() {
            return contract;
        }

        public Order getOrder() {
            return order;
        }
    }
}
