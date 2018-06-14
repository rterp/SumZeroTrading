/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sumzerotrading.bitmex.common.api;

import java.util.Properties;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author RobTerpilowski
 */
public class BitmexPropertiesTest {
    
    public BitmexPropertiesTest() {
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
    public void testReadProperties_Properties() {
        Properties props = new Properties();
        props.setProperty(BitmexPropKey.USE_PRODUCTION, "true");
        props.setProperty(BitmexPropKey.API_KEY_NAME, "myApiKeyName");
        props.setProperty(BitmexPropKey.API_KEY_VALUE, "myApiKeyValue");
        
        BitmexProperties bitmexProps = new BitmexProperties();
        bitmexProps.readProperties(props);
        
        assertValues(bitmexProps);
    }
    
    @Test
    public void testReadProperties_InputStream() {
        BitmexProperties props = new BitmexProperties();
        props.readProperties(getClass().getResourceAsStream("/testprops.properties"));
        
        assertValues(props);
    }
    
    
    @Test
    public void testReadProperties_File() throws Exception {
        BitmexProperties props = new BitmexProperties();
        props.readProperties(getClass().getResource("/testprops.properties").toURI().getPath());
        
        assertValues(props);
    }
    
    
    
    protected void assertValues(BitmexProperties props) {
        assertTrue(props.isUseProduction());
        assertEquals("myApiKeyName", props.getApiKeyName());
        assertEquals("myApiKeyValue", props.getApiKeyValue());
    }

}
