/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sumzerotrading.util;

import com.sumzerotrading.data.SumZeroException;
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
public class UtilTest {

    public UtilTest() {
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
    public void testRoundLot_invalidRounding() throws Exception {
        try {
            Util.roundLot(499, 0);
            fail();
        } catch (SumZeroException ex) {
            //this should happen
        }
    }
    
    @Test
    public void testRoundLot() {
        assertEquals(24000, Util.roundLot(23864, 2000) );
    }

    @Test
    public void testParseTicker() {
        assertEquals("700", Util.parseTicker("00700"));
        assertEquals("700", Util.parseTicker("700"));
        assertEquals("700", Util.parseTicker("00700.HK"));
        assertEquals("701", Util.parseTicker("00701.SG"));
        assertEquals("QQQ", Util.parseTicker("QQQ"));
    }
}
