/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sumzerotrading.bitmex.common.api;

import com.sumzerotrading.bitmex.client.BitmexRestClient;
import com.sumzerotrading.bitmex.client.BitmexWebsocketClient;
import java.util.Properties;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 *
 * @author RobTerpilowski
 */
@RunWith(MockitoJUnitRunner.class)
public class BitmexClientRegistryTest {
    
    
    BitmexClientRegistry registry;
    
    public BitmexClientRegistryTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        registry = BitmexClientRegistry.getInstance();
    }
    
    @After
    public void tearDown() {
    }


    @Test
    public void testGetInstance() {
        
        assertEquals( registry, BitmexClientRegistry.registry);
        assertEquals( registry, BitmexClientRegistry.getInstance());
    }
    
    @Test
    public void testSetProperties() {
        String apiKeyName = "myApiKeyName";
        String apiKeyValue = "myApiKeyValue";
        BitmexProperties properties = new BitmexProperties();
        
        properties.readProperties(getClass().getResourceAsStream("/testprops.properties"));
        
        
        registry.setProperties(properties);
        
        assertTrue(registry.useProduction);
        assertEquals(apiKeyName, registry.apiKeyName);
        assertEquals(apiKeyValue, registry.apiKeyValue);
    }
    
    
    @Test
    public void testGetRestClient() {
        registry.apiKeyName = "myApiKey";
        registry.apiKeyValue = "myApiValue";
        registry.useProduction = true;
        
        BitmexRestClient expectedClient = new BitmexRestClient(true, "myApiKey", "myApiValue");
        BitmexRestClient actualClient = registry.getRestClient();
        
        assertEquals( expectedClient, actualClient );
        assertEquals( actualClient, registry.getRestClient() );
    }
    
    @Test
    public void testGetWebsocketClient() {
        assertNull( registry.websocketClient );
        BitmexWebsocketClient client = registry.getWebsocketClient();
        
        assertEquals(client, registry.websocketClient );
        assertTrue( client.isConnected() );
        assertEquals(client, registry.getWebsocketClient());
        
        client.disconnect();
    }
    
    
}
