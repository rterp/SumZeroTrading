/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sumzerotrading.ib.symbol;

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
public class DefaultLocalSymbolBuilderTest {
    
    public DefaultLocalSymbolBuilderTest() {
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
    public void testBuildLocalSymbol() {
        DefaultLocalSymbolBuilder builder = new DefaultLocalSymbolBuilder();
        assertEquals( "GCH7", builder.buildLocalSymbol("GC", 3, 2017));
    }
}
