/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sumzerotrading.broker.c2;

import com.sumzerotrading.broker.order.TradeOrder;
import com.sumzerotrading.j4c2.Collective2Client;
import com.sumzerotrading.j4c2.signal.CancelSignalRequest;
import com.sumzerotrading.j4c2.signal.SubmitSignalRequest;
import com.sumzerotrading.j4c2.signal.SubmitSignalResponse;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.runners.MockitoJUnit44Runner;

/**
 *
 * @author RobTerpilowski
 */
@RunWith(MockitoJUnit44Runner.class)
public class Collective2BrokerTest {


    @Mock
    protected Collective2Client mockC2Client;
    
    @Mock
    protected TradeSignalBuilder mockSignalBuilder;
    
    protected String apiKey = "myApiKey";
    
    protected String systemId = "mySystemId";
    
    protected Collective2Broker testBroker;
    
    public Collective2BrokerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        testBroker = new Collective2Broker();
        testBroker.c2Client = mockC2Client;
        testBroker.signalBuilder = mockSignalBuilder;
        testBroker.apiKey = apiKey;
        testBroker.systemid = systemId;
    }
    
    @After
    public void tearDown() {
    }


    @Test
    public void testPlaceOrder() {
        TradeOrder parentOrder = new TradeOrder();
        TradeOrder childOrder = new TradeOrder();
        parentOrder.addChildOrder(childOrder);
        
        SubmitSignalRequest request1 = mock(SubmitSignalRequest.class);
        SubmitSignalRequest request2 = mock(SubmitSignalRequest.class);
        
        SubmitSignalResponse parentReponse = new SubmitSignalResponse();
        parentReponse.setSignalid("parent-123");
        
        SubmitSignalResponse childResponse = new SubmitSignalResponse();
        childResponse.setSignalid("child-123");
        
        when(mockSignalBuilder.buildSignalRequest(systemId, parentOrder)).thenReturn(request1);
        when(mockSignalBuilder.buildSignalRequest(systemId, childOrder)).thenReturn(request2);
        when(mockC2Client.submitTradeSignal(request1)).thenReturn(parentReponse);
        when(mockC2Client.submitTradeSignal(request2)).thenReturn(childResponse);
        
        testBroker.placeOrder(parentOrder);
        
        assertEquals("parent-123", parentOrder.getOrderId());
        assertEquals("child-123", childOrder.getOrderId());
        
    }
    
    
    @Test
    public void testGetNextOrderId() {
        assertEquals( "", testBroker.getNextOrderId() );
        assertEquals( "", testBroker.getNextOrderId() );
    }
    
    @Test
    public void testCancelOrder() {
        String orderId = "123";
        TradeOrder order = new TradeOrder();
        order.setOrderId(orderId);;
        
        CancelSignalRequest request = new CancelSignalRequest(systemId, orderId);
        
        testBroker.cancelOrder(order);
        
        verify(mockC2Client, times(1)).submitCancelSignal(request);
        
    }
    
    @Test
    public void testCancelOrderById() {
        String orderId = "123";
        
        CancelSignalRequest request = new CancelSignalRequest(systemId, orderId);
        
        testBroker.cancelOrder(orderId);
        
        verify(mockC2Client, times(1)).submitCancelSignal(request);        
    }
}
