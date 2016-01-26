/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.limituptrading.ib;

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
public class IBClientTest {
    
    public IBClientTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }


    @Test
    public void testConstructor() {
        String host = "myHost";
        int port = 123;
        int clientId = 234;
        IBClient client = new IBClient(host, port, clientId);
        
        assertEquals( host, client.getHost() );
        assertEquals( port, client.getPort() );
        assertEquals( clientId, client.getClientId() );
    }
    


}
