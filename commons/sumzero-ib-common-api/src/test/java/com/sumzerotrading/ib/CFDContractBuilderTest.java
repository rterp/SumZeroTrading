/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sumzerotrading.ib;

import com.ib.client.Contract;
import com.sumzerotrading.data.CFDTicker;
import com.sumzerotrading.data.Exchange;
import com.sumzerotrading.data.InstrumentType;
import com.sumzerotrading.data.StockTicker;
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
public class CFDContractBuilderTest {
    
    public CFDContractBuilderTest() {
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
    public void testBuildContract() {
        CFDTicker ticker = getTicker();
        CFDContractBuilder builder = new CFDContractBuilder();
        Contract contract = builder.buildContract(ticker);

        assertEquals( ticker.getCurrency(), contract.m_currency );
        assertEquals( ticker.getExchange().getExchangeName(), contract.m_exchange );
        assertEquals( ticker.getSymbol(), contract.m_symbol );
        assertEquals( InstrumentType.CFD,  ticker.getInstrumentType());
        assertNull( contract.m_primaryExch );
    }
    
    @Test
    public void testBuildContract_primaryExchange() {
        CFDTicker ticker = getTicker();
        ticker.setPrimaryExchange(Exchange.NASDAQ);
        CFDContractBuilder builder = new CFDContractBuilder();
        Contract contract = builder.buildContract(ticker);

        assertEquals( ticker.getCurrency(), contract.m_currency );
        assertEquals( ticker.getExchange().getExchangeName(), contract.m_exchange );
        assertEquals( ticker.getSymbol(), contract.m_symbol );
        assertEquals( InstrumentType.CFD,  ticker.getInstrumentType());
        assertEquals( ticker.getPrimaryExchange().getExchangeName(), contract.m_primaryExch );
    }
    
    
    
  
    protected CFDTicker getTicker() {
        CFDTicker ticker = new CFDTicker("SBUX");
        ticker.setCurrency("USD");
        ticker.setExchange(Exchange.INTERACTIVE_BROKERS_SMART);
        return ticker;
    }    
}
