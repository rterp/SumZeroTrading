/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.limituptrading.util;

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
public class FuturesUtilTest {
    
    public FuturesUtilTest() {
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
    public void testGetContractMonthSymbol() {
        assertEquals( "F", FuturesUtil.getContractMonthSymbol(1) );
        assertEquals( "G", FuturesUtil.getContractMonthSymbol(2) );
        assertEquals( "H", FuturesUtil.getContractMonthSymbol(3) );
        assertEquals( "J", FuturesUtil.getContractMonthSymbol(4) );
        assertEquals( "K", FuturesUtil.getContractMonthSymbol(5) );
        assertEquals( "M", FuturesUtil.getContractMonthSymbol(6) );
        assertEquals( "N", FuturesUtil.getContractMonthSymbol(7) );
        assertEquals( "Q", FuturesUtil.getContractMonthSymbol(8) );
        assertEquals( "U", FuturesUtil.getContractMonthSymbol(9) );
        assertEquals( "V", FuturesUtil.getContractMonthSymbol(10) );
        assertEquals( "X", FuturesUtil.getContractMonthSymbol(11) );
        assertEquals( "Z", FuturesUtil.getContractMonthSymbol(12) );
        
        try {
            FuturesUtil.getContractMonthSymbol(0);
            fail();
        } catch( IllegalStateException ex ) {
            //this should happen
        }
    }
}
