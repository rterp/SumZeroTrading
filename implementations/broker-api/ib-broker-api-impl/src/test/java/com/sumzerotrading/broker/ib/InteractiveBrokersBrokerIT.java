/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sumzerotrading.broker.ib;

import com.sumzerotrading.broker.order.OrderEvent;
import com.sumzerotrading.broker.order.OrderEventListener;
import com.sumzerotrading.broker.order.OrderStatus;
import com.sumzerotrading.broker.order.TradeDirection;
import com.sumzerotrading.broker.order.TradeOrder;
import com.sumzerotrading.data.StockTicker;
import com.sumzerotrading.data.Ticker;
import com.sumzerotrading.ib.IBConnectionUtil;
import com.sumzerotrading.ib.IBSocket;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author RobTerpilowski
 */
public class InteractiveBrokersBrokerIT implements OrderEventListener {
    
    
    IBConnectionUtil util = new IBConnectionUtil("localhost", 7999, 1);
    IBSocket socket;
    InteractiveBrokersBroker broker;
    List<OrderEvent> eventList = new ArrayList<>();
    Ticker qqqTicker = new StockTicker("QQQ");
    
    public InteractiveBrokersBrokerIT() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        socket = util.getIBSocket();
        broker = new InteractiveBrokersBroker(socket);
        broker.addOrderEventListener(this);
        eventList.clear();
    }
    
    @After
    public void tearDown() {
    }

    
    @Test
    public void testConnect() {
        assertFalse(broker.isConnected());
        assertFalse(broker.started);
        assertFalse(broker.orderProcessor.shouldRun);
        
        broker.connect();
        
        assertTrue(broker.started);
        assertTrue(broker.isConnected());
        assertTrue(broker.orderProcessor.shouldRun);
    }
    
    @Test
    public void testGetNextOrderId() {
        assertEquals("1", broker.getNextOrderId());
        assertEquals("2", broker.getNextOrderId());
    }
    
    @Test
    public void testSubmitMarketOrder() throws Exception {
        broker.connect();
        
        String orderId = broker.getNextOrderId();
        System.out.println("Order id: " + orderId );
        TradeOrder order = new TradeOrder(orderId, qqqTicker, 100, TradeDirection.BUY);
        broker.placeOrder(order);
        
        //If the order is executed immediately we problably won't get a 'presubmitted', or 'submitted'
        //status from IB, it will go straight to filled;
        
        Thread.sleep(10000);
        
        boolean filledSizeCorrect = false;
        boolean filledOrderStatusCorrect = false;
        boolean remainingCorrect = false;
        
        for( OrderEvent event : eventList ) {
            System.out.println("Event for orderId: " + event.getOrderStatus().getOrderId() );
            
            System.out.println("Status: " + event.getOrder().getCurrentStatus());
            if( (event.getOrder().getCurrentStatus() == OrderStatus.Status.FILLED ) &&
                 event.getOrderStatus().getStatus() == OrderStatus.Status.FILLED ) {
                filledOrderStatusCorrect = true;
            }

            System.out.println("Filled size: " + event.getOrder().getFilledSize());
            if( event.getOrder().getFilledSize() == 100 ) {
                filledSizeCorrect = true;
            }
            

            System.out.println("Remaining: " + event.getOrderStatus().getRemaining());
            if( event.getOrderStatus().getRemaining() == 0 ) {
                remainingCorrect = true;
            }
        }

        assertTrue(filledSizeCorrect);
        assertTrue(remainingCorrect);
        assertTrue(filledOrderStatusCorrect);
        
        
    }
    

    @Test
    public void testDisconnect() {
        assertTrue(broker.isConnected());
        broker.disconnect();
        assertFalse(broker.orderProcessor.shouldRun);
    }

    @Override
    public void orderEvent(OrderEvent event) {
        eventList.add(event);
    }
    
    
    
    
    
}
