/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sumzerotrading.eod.trading.strategy;

import com.sumzerotrading.interactive.brokers.client.InteractiveBrokersClient;
import com.sumzerotrading.interactive.brokers.client.InteractiveBrokersClientInterface;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import static org.mockito.Mockito.mock;

/**
 *
 * @author RobTerpilowski
 */
public class EODTradingStrategyTest {
    
    protected InteractiveBrokersClientInterface mockIbClient;
    protected String ibHost = "myHost";
    protected int ibPort = 9999;
    protected int ibClientClientId = 1111;
    
    public EODTradingStrategyTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        mockIbClient = mock(InteractiveBrokersClientInterface.class);
        InteractiveBrokersClient.setMockInteractiveBrokersClient(mockIbClient, ibHost, ibPort, ibPort);
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
