/**
 * MIT License

Copyright (c) 2015  Rob Terpilowski

Permission is hereby granted, free of charge, to any person obtaining a copy of this software 
and associated documentation files (the "Software"), to deal in the Software without restriction, 
including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, 
and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, 
subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING 
BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. 
IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, 
WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE 
OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.sumzerotrading.ib;

import com.ib.client.Contract;
import com.sumzerotrading.data.Exchange;
import com.sumzerotrading.data.FuturesTicker;
import java.math.BigDecimal;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author RobTerpilowski
 */
public class FuturesContractBuilderTest {
    
    protected FuturesContractBuilder builder;
    
    public FuturesContractBuilderTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        builder = new FuturesContractBuilder();
    }
    
    @After
    public void tearDown() {
    }

    
        @Test
    public void testBuildContract_NoMultiplier() {
        FuturesTicker ticker = new FuturesTicker();
        ticker.setCurrency("USD");
        ticker.setExchange(Exchange.GLOBEX);
        ticker.setSymbol("HG");
        ticker.setExpiryMonth(6);
        ticker.setExpiryYear(2015);

        Contract expected = new Contract();
        expected.m_currency = "USD";
        expected.m_exchange = Exchange.GLOBEX.getExchangeName();
        expected.m_secType = "FUT";
        expected.m_symbol = "HG";
        expected.m_expiry = "201506";
        expected.m_localSymbol = "HGM5";
        
        assertEquals(expected, builder.buildContract(ticker) );
    }
    
    
    @Test
    public void testBuildContract_NoMultiplier_Grains() {
        FuturesTicker ticker = new FuturesTicker();
        ticker.setCurrency("USD");
        ticker.setExchange(Exchange.ECBOT);
        ticker.setSymbol("ZW");
        ticker.setExpiryMonth(6);
        ticker.setExpiryYear(2015);

        Contract expected = new Contract();
        expected.m_currency = "USD";
        expected.m_exchange = Exchange.ECBOT.getExchangeName();
        expected.m_secType = "FUT";
        expected.m_symbol = "ZW";
        expected.m_expiry = "201506";
        expected.m_multiplier = "5000";
        expected.m_localSymbol = "ZW   JUN 15";
        
        assertEquals(expected, builder.buildContract(ticker) );
    }

    
    @Test
    public void testBuildContract_WithMultiplier() {
        FuturesTicker ticker = new FuturesTicker();
        ticker.setCurrency("USD");
        ticker.setExchange(Exchange.ECBOT);
        ticker.setSymbol("ZW");
        ticker.setExpiryMonth(6);
        ticker.setExpiryYear(2015);
        ticker.setContractMultiplier(new BigDecimal(50));

        Contract expected = new Contract();
        expected.m_currency = "USD";
        expected.m_exchange = Exchange.ECBOT.getExchangeName();
        expected.m_secType = "FUT";
        expected.m_symbol = "ZW";
        expected.m_expiry = "201506";
        expected.m_multiplier = "5000";
        expected.m_localSymbol = "ZW   JUN 15";
        
        assertEquals(expected, builder.buildContract(ticker) );
    }
    
}
