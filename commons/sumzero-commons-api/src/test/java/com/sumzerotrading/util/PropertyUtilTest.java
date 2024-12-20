/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sumzerotrading.util;

import java.util.Properties;
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
public class PropertyUtilTest {
    
    protected PropertyUtil testPropertyUtil;
    protected Properties testProperties;
    
    public PropertyUtilTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        testProperties = new Properties();
        testPropertyUtil = new PropertyUtil(testProperties);
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testReadInt() {
        testProperties.setProperty("myIntProp", "2");
        assertEquals(2, testPropertyUtil.readInt("myIntProp"));
    }
    
    @Test
    public void testReadInt_DefaultValueNotNeeded() {
        testProperties.setProperty("myIntProp", "2");
        assertEquals(2, testPropertyUtil.readInt("myIntProp", 3));
    }    
    
    @Test
    public void testReadInt_DefaultValueNeeded() {
        assertEquals(3, testPropertyUtil.readInt("myIntProp", 3));
    }        
    
    
    @Test
    public void testReadDouble() {
        testProperties.setProperty("myDoubleProp", "2.0");
        assertEquals(2.0, testPropertyUtil.readDouble("myDoubleProp"),0);
    }
    
    @Test
    public void testReadDouble_DefaultValueNotNeeded() {
        testProperties.setProperty("myDoubleProp", "2.0");
        assertEquals(2.0, testPropertyUtil.readDouble("myDoubleProp", 3.0),0);
    }    
    
    @Test
    public void testReadDouble_DefaultValueNeeded() {
        assertEquals(3.0, testPropertyUtil.readDouble("myDoubleProp", 3.0),0);
    }            
    
    
    
}
