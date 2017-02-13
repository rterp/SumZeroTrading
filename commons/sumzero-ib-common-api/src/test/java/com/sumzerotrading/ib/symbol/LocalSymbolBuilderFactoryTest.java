/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sumzerotrading.ib.symbol;

import com.sumzerotrading.data.Exchange;
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
public class LocalSymbolBuilderFactoryTest {
    
    public LocalSymbolBuilderFactoryTest() {
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
    public void testGetLocalSymbolBuilder() {
        assertTrue( LocalSymbolBuilderFactory.getLocalSymbolBuilder(Exchange.HKFE) instanceof HKFELocalSymbolBuilder );
        assertTrue( LocalSymbolBuilderFactory.getLocalSymbolBuilder(Exchange.ECBOT) instanceof CBOTLocalSymbolBuilder );
        assertTrue( LocalSymbolBuilderFactory.getLocalSymbolBuilder(Exchange.GLOBEX) instanceof DefaultLocalSymbolBuilder );
        assertTrue( LocalSymbolBuilderFactory.getLocalSymbolBuilder(Exchange.OSE) instanceof OSELocalSymbolBuilder );
    }
}
