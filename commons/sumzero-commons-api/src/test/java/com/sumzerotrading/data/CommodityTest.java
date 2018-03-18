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
package com.sumzerotrading.data;

import java.math.BigDecimal;
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
public class CommodityTest {

    public CommodityTest() {
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
    public void testSP500Index() {
        Commodity c = Commodity.SP500_INDEX_MINI_GLOBEX;

        assertEquals("ES", c.getExchangeSymbol());
        assertEquals("USD", c.getCurrency());
        assertEquals(Commodity.TWO_DECIMALS, c.getDecimalFormat());
        assertEquals(Exchange.GLOBEX, c.getExchange());
        assertEquals(new BigDecimal(0.25), c.getMinimumTickSize());
        assertEquals(new BigDecimal(50), c.getContractMultiplier());
    }

    @Test
    public void testNasdaq100Index() {
        Commodity c = Commodity.NASDAQ100_INDEX_MINI_GLOBEX;

        assertEquals("NQ", c.getExchangeSymbol());
        assertEquals("USD", c.getCurrency());
        assertEquals(Commodity.TWO_DECIMALS, c.getDecimalFormat());
        assertEquals(Exchange.GLOBEX, c.getExchange());
        assertEquals(new BigDecimal("0.25"), c.getMinimumTickSize());
        assertEquals(new BigDecimal(20), c.getContractMultiplier());
    }

    @Test
    public void testRussellNyBotIndex() {
        Commodity c = Commodity.RUSSELL_2000_INDEX_MINI_NYBOT;

        assertEquals("TF", c.getExchangeSymbol());
        assertEquals("USD", c.getCurrency());
        assertEquals(Commodity.TWO_DECIMALS, c.getDecimalFormat());
        assertEquals(Exchange.NYBOT, c.getExchange());
        assertEquals(new BigDecimal("0.10"), c.getMinimumTickSize());
        assertEquals(new BigDecimal(50), c.getContractMultiplier());
    }
    
    @Test
    public void testRussellGlobexIndex() {
        Commodity c = Commodity.RUSSELL_2000_INDEX_MINI_GLOBEX;

        assertEquals("RTY", c.getExchangeSymbol());
        assertEquals("USD", c.getCurrency());
        assertEquals(Commodity.TWO_DECIMALS, c.getDecimalFormat());
        assertEquals(Exchange.GLOBEX, c.getExchange());
        assertEquals(new BigDecimal("0.10"), c.getMinimumTickSize());
        assertEquals(new BigDecimal(50), c.getContractMultiplier());
    }    
    

    @Test
    public void testDowIndex() {
        Commodity c = Commodity.DOW_INDEX_MINI_ECBOT;

        assertEquals("YM", c.getExchangeSymbol());
        assertEquals("USD", c.getCurrency());
        assertEquals(Commodity.NO_DECIMALS, c.getDecimalFormat());
        assertEquals(Exchange.ECBOT, c.getExchange());
        assertEquals(new BigDecimal(1), c.getMinimumTickSize());
        assertEquals(new BigDecimal(5), c.getContractMultiplier());
    }

    @Test
    public void test30YearBond() {
        Commodity c = Commodity.BOND_30_YEAR_ECBOT;

        assertEquals("ZB", c.getExchangeSymbol());
        assertEquals("USD", c.getCurrency());
        assertEquals(Commodity.FIVE_DECIMALS, c.getDecimalFormat());
        assertEquals(Exchange.ECBOT, c.getExchange());
        assertEquals(new BigDecimal(0.03125), c.getMinimumTickSize());
        assertEquals(new BigDecimal(1000), c.getContractMultiplier());
    }

    @Test
    public void test10YearBond() {
        Commodity c = Commodity.BOND_10_YEAR_ECBOT;

        assertEquals("ZN", c.getExchangeSymbol());
        assertEquals("USD", c.getCurrency());
        assertEquals(Commodity.FIVE_DECIMALS, c.getDecimalFormat());
        assertEquals(Exchange.ECBOT, c.getExchange());
        assertEquals(new BigDecimal(0.03125), c.getMinimumTickSize());
        assertEquals(new BigDecimal(1000), c.getContractMultiplier());
    }

    @Test
    public void testEuro() {
        Commodity c = Commodity.EURO_GLOBEX;

        assertEquals("6E", c.getExchangeSymbol());
        assertEquals("USD", c.getCurrency());
        assertEquals(Commodity.FOUR_DECIMALS, c.getDecimalFormat());
        assertEquals(Exchange.GLOBEX, c.getExchange());
        assertEquals(new BigDecimal("0.0001"), c.getMinimumTickSize());
        assertEquals(new BigDecimal("125000"), c.getContractMultiplier());
    }

    @Test
    public void testEuroMini() {
        Commodity c = Commodity.EURO_MINI_GLOBEX;

        assertEquals("E7", c.getExchangeSymbol());
        assertEquals("USD", c.getCurrency());
        assertEquals(Commodity.FOUR_DECIMALS, c.getDecimalFormat());
        assertEquals(Exchange.GLOBEX, c.getExchange());
        assertEquals(new BigDecimal("0.0001"), c.getMinimumTickSize());
        assertEquals(new BigDecimal(62500), c.getContractMultiplier());
    }

    @Test
    public void testJapaneseYen() {
        Commodity c = Commodity.JAPANESE_YEN_GLOBEX;

        assertEquals("6J", c.getExchangeSymbol());
        assertEquals("USD", c.getCurrency());
        assertEquals(Commodity.SIX_DECIMALS, c.getDecimalFormat());
        assertEquals(Exchange.GLOBEX, c.getExchange());
        assertEquals(new BigDecimal("0.000001"), c.getMinimumTickSize());
        assertEquals(new BigDecimal("12500000"), c.getContractMultiplier());
    }

    @Test
    public void testJapaneseYenMini() {
        Commodity c = Commodity.JAPANESE_YEN_MINI_GLOBEX;

        assertEquals("J7", c.getExchangeSymbol());
        assertEquals("USD", c.getCurrency());
        assertEquals(Commodity.SIX_DECIMALS, c.getDecimalFormat());
        assertEquals(Exchange.GLOBEX, c.getExchange());
        assertEquals(new BigDecimal("0.000001"), c.getMinimumTickSize());
        assertEquals(new BigDecimal("6250000"), c.getContractMultiplier());
    }

    @Test
    public void testCanadianDollar() {
        Commodity c = Commodity.CANADIAN_DOLLAR_GLOBEX;

        assertEquals("6C", c.getExchangeSymbol());
        assertEquals("USD", c.getCurrency());
        assertEquals(Commodity.FOUR_DECIMALS, c.getDecimalFormat());
        assertEquals(Exchange.GLOBEX, c.getExchange());
        assertEquals(new BigDecimal("0.0001"), c.getMinimumTickSize());
        assertEquals(new BigDecimal(100000), c.getContractMultiplier());
    }

    @Test
    public void testSwissFranc() {
        Commodity c = Commodity.SWISS_FRANC_GLOBEX;

        assertEquals("6S", c.getExchangeSymbol());
        assertEquals("USD", c.getCurrency());
        assertEquals(Commodity.FOUR_DECIMALS, c.getDecimalFormat());
        assertEquals(Exchange.GLOBEX, c.getExchange());
        assertEquals(new BigDecimal("0.0001"), c.getMinimumTickSize());
        assertEquals(new BigDecimal("125000"), c.getContractMultiplier());
    }
    
    @Test
    public void testGold() {
        Commodity c = Commodity.GOLD_NYMEX;

        assertEquals("GC", c.getExchangeSymbol());
        assertEquals("USD", c.getCurrency());
        assertEquals(Commodity.TWO_DECIMALS, c.getDecimalFormat());
        assertEquals(Exchange.NYMEX, c.getExchange());
        assertEquals(new BigDecimal("0.10"), c.getMinimumTickSize());
        assertEquals(new BigDecimal(100), c.getContractMultiplier());
    }    
    
    @Test
    public void testGoldMini() {
        Commodity c = Commodity.GOLD_MINI_NYSE_LIFFE;

        assertEquals("YG", c.getExchangeSymbol());
        assertEquals("USD", c.getCurrency());
        assertEquals(Commodity.TWO_DECIMALS, c.getDecimalFormat());
        assertEquals(Exchange.NYSE_LIFFE, c.getExchange());
        assertEquals(new BigDecimal("0.10"), c.getMinimumTickSize());
        assertEquals(new BigDecimal("32.15"), c.getContractMultiplier());
    }        
    
    @Test
    public void testSilver() {
        Commodity c = Commodity.SILVER_NYMEX;

        assertEquals("SI", c.getExchangeSymbol());
        assertEquals("USD", c.getCurrency());
        assertEquals(Commodity.THREE_DECIMALS, c.getDecimalFormat());
        assertEquals(Exchange.NYMEX, c.getExchange());
        assertEquals(new BigDecimal("0.005"), c.getMinimumTickSize());
        assertEquals(new BigDecimal(5000), c.getContractMultiplier());
    }            
    
    @Test
    public void testSilverMini() {
        Commodity c = Commodity.SILVER_MINI_NYSE_LIFFE;

        assertEquals("YI", c.getExchangeSymbol());
        assertEquals("USD", c.getCurrency());
        assertEquals(Commodity.THREE_DECIMALS, c.getDecimalFormat());
        assertEquals(Exchange.NYSE_LIFFE, c.getExchange());
        assertEquals(new BigDecimal("0.005"), c.getMinimumTickSize());
        assertEquals(new BigDecimal(1000), c.getContractMultiplier());
    }       
    
    @Test
    public void testCopper() {
        Commodity c = Commodity.COPPER_NYMEX;

        assertEquals("HG", c.getExchangeSymbol());
        assertEquals("USD", c.getCurrency());
        assertEquals(Commodity.THREE_DECIMALS, c.getDecimalFormat());
        assertEquals(Exchange.NYMEX, c.getExchange());
        assertEquals(new BigDecimal("0.0005"), c.getMinimumTickSize());
        assertEquals(new BigDecimal(25000), c.getContractMultiplier());
    }        
    
    @Test
    public void testCrudeNymex() {
        Commodity c = Commodity.CRUDE_OIL_NYMEX;

        assertEquals("CL", c.getExchangeSymbol());
        assertEquals("USD", c.getCurrency());
        assertEquals(Commodity.TWO_DECIMALS, c.getDecimalFormat());
        assertEquals(Exchange.NYMEX, c.getExchange());
        assertEquals(new BigDecimal("0.01"), c.getMinimumTickSize());
        assertEquals(new BigDecimal(1000), c.getContractMultiplier());
    }       
    
    @Test
    public void testCrudeNymexMini() {
        Commodity c = Commodity.CRUDE_OIL_MINI_NYMEX;

        assertEquals("QM", c.getExchangeSymbol());
        assertEquals("USD", c.getCurrency());
        assertEquals(Commodity.THREE_DECIMALS, c.getDecimalFormat());
        assertEquals(Exchange.NYMEX, c.getExchange());
        assertEquals(new BigDecimal("0.025"), c.getMinimumTickSize());
        assertEquals(new BigDecimal(500), c.getContractMultiplier());
    }            

    @Test
    public void testNaturalGasNymex() {
        Commodity c = Commodity.NATURAL_GAS_NYMEX;

        assertEquals("NG", c.getExchangeSymbol());
        assertEquals(new BigDecimal(10000), c.getContractMultiplier());
        assertEquals("USD", c.getCurrency());
        assertEquals(Commodity.THREE_DECIMALS, c.getDecimalFormat());
        assertEquals(Exchange.NYMEX, c.getExchange());
        assertEquals(new BigDecimal("0.001"), c.getMinimumTickSize());
    }
    
    @Test
    public void testNaturalGasNymexMini() {
        Commodity c = Commodity.NATURAL_GAS_MINI_NYMEX;

        assertEquals("QG", c.getExchangeSymbol());
        assertEquals(new BigDecimal(2500), c.getContractMultiplier());
        assertEquals("USD", c.getCurrency());
        assertEquals(Commodity.THREE_DECIMALS, c.getDecimalFormat());
        assertEquals(Exchange.NYMEX, c.getExchange());
        assertEquals(new BigDecimal("0.005"), c.getMinimumTickSize());
    }    
    
    @Test
    public void testHeatingOilNymex() {
        Commodity c = Commodity.HEATING_OIL_NYMEX;

        assertEquals("HO", c.getExchangeSymbol());
        assertEquals(new BigDecimal(42000), c.getContractMultiplier());
        assertEquals("USD", c.getCurrency());
        assertEquals(Commodity.FOUR_DECIMALS, c.getDecimalFormat());
        assertEquals(Exchange.NYMEX, c.getExchange());
        assertEquals(new BigDecimal("0.0001"), c.getMinimumTickSize());
    }        
    
    
  @Test
    public void testGetCorn() {
        Commodity c = Commodity.CORN_ECBOT;

        assertEquals("ZC", c.getExchangeSymbol());
        assertEquals(new BigDecimal(50), c.getContractMultiplier());
        assertEquals("USD", c.getCurrency());
        assertEquals(Commodity.TWO_DECIMALS, c.getDecimalFormat());
        assertEquals(Exchange.ECBOT, c.getExchange());
        assertEquals(new BigDecimal("0.25"), c.getMinimumTickSize());
    }    
    
  @Test
    public void testGetSoybeans() {
        Commodity c = Commodity.SOYBEANS_ECBOT;

        assertEquals("ZS", c.getExchangeSymbol());
        assertEquals(new BigDecimal(50), c.getContractMultiplier());
        assertEquals("USD", c.getCurrency());
        assertEquals(Commodity.TWO_DECIMALS, c.getDecimalFormat());
        assertEquals(Exchange.ECBOT, c.getExchange());
        assertEquals(new BigDecimal("0.25"), c.getMinimumTickSize());
    }      
    
  @Test
    public void testGetWheat() {
        Commodity c = Commodity.WHEAT_ECBOT;

        assertEquals("ZW", c.getExchangeSymbol());
        assertEquals(new BigDecimal(50), c.getContractMultiplier());
        assertEquals("USD", c.getCurrency());
        assertEquals(Commodity.TWO_DECIMALS, c.getDecimalFormat());
        assertEquals(Exchange.ECBOT, c.getExchange());
        assertEquals(new BigDecimal("0.25"), c.getMinimumTickSize());
    }          
    
    
    @Test
    public void testGetVix() {
        Commodity c = Commodity.VIX_FUTURES_CFE;
        
        assertEquals("VX", c.getExchangeSymbol() );
        assertEquals(new BigDecimal(1000), c.getContractMultiplier());
        assertEquals("USD", c.getCurrency());
        assertEquals(Commodity.TWO_DECIMALS, c.getDecimalFormat());
        assertEquals(Exchange.CFE, c.getExchange());
        assertEquals(new BigDecimal("0.10"), c.getMinimumTickSize());
    }

    
    @Test
    public void testGetByExchangeSymbol() {
        assertEquals( Commodity.WHEAT_ECBOT, Commodity.getByExchangeSymbol("ZW"));
    }
    
    @Test
    public void testGetByExchangeSymbol_SymbolNotFound() {
        try {
            Commodity.getByExchangeSymbol("Foo");
            fail();
        } catch( IllegalStateException ex ) {
            //this should happen
        }
    }
}
