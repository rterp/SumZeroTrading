/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.limituptrading.data;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.GregorianCalendar;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author robbob
 */
public class SerializationTest {
    
    public SerializationTest() {
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
    public void testStockTicker() throws Exception {
        Ticker ticker = new StockTicker("SEA");
        test( ticker );
    }
    
    @Test
    public void testSerializeFuturesTicker() throws Exception {
        FuturesTicker ticker = new FuturesTicker();
        ticker.setSymbol("ES");
        ticker.setContractMultiplier(BigDecimal.ONE);
        ticker.setCurrency("USD");
        ticker.setExchange(Exchange.ARCA);
        ticker.setExpiryMonth(1);
        ticker.setExpiryYear(2015);
        ticker.setMinimumTickSize(BigDecimal.TEN);

        test( ticker );
    }
    
    
    @Test
    public void testSerializeBar() throws Exception {
        BarData bar = new BarData(LocalDateTime.now(), 1, 2, 3, 4, 5, 6);
        test( bar );
    }
    
    
    @Test
    public void testSerializeComboTicker() throws Exception {
        Ticker firstTicker = new StockTicker("ABC");
        Ticker secondTicker = new StockTicker("DEF");
        ComboTicker ticker = new ComboTicker(firstTicker, secondTicker, 1,2 );
        
        test( ticker );
    }
    
    
    @Test
    public void testSerializeCurrencyTicker() throws Exception {
        CurrencyTicker ticker = new CurrencyTicker();
        ticker.setContractMultiplier(BigDecimal.ZERO);
        ticker.setCurrency("USD");
        ticker.setExchange(Exchange.ARCA);
        ticker.setMinimumTickSize(BigDecimal.TEN);
        ticker.setSupporthalfTick(true);
        ticker.setSymbol("EUR");
        
        
        test( ticker );
        
    }
    
    @Test
    public void testSerializeExchange() throws Exception {
        test( Exchange.GLOBEX );
    }
    
    @Test
    public void testIndexTicker() throws Exception {
        IndexTicker ticker = new IndexTicker();
        ticker.setContractMultiplier(BigDecimal.ZERO);
        ticker.setCurrency("USD");
        ticker.setExchange(Exchange.ARCA);
        ticker.setMinimumTickSize(BigDecimal.TEN);
        ticker.setSymbol("SPX");
        
        test(ticker);
    }
    
    @Test
    public void testOptionTicker() throws Exception {
        OptionTicker ticker = new OptionTicker("ES", 1, 2015);
        ticker.setContractMultiplier(BigDecimal.ZERO);
        ticker.setCurrency("USD");
        ticker.setExchange(Exchange.ARCA);
        ticker.setMinimumTickSize(BigDecimal.TEN);
        ticker.setSymbol("ES");
        
        test(ticker);
    }
    
    
    public void test( Object object ) throws Exception {
        byte[] serialized = serialize(object);
        assertTrue( serialized.length > 0 );
        
        Object object2 = deserialize(object.getClass(), serialized);
        
        assertEquals( object, object2 );
    }
    
    
    
    protected byte[] serialize( Object object ) throws Exception {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ObjectOutputStream objectOut = new ObjectOutputStream( output );
        
        objectOut.writeObject(object);
        return output.toByteArray();
        
    }
    
    
    protected <T> T deserialize( Class<T> clazz, byte[] bytes ) throws Exception {
        ByteArrayInputStream input = new ByteArrayInputStream(bytes);
        ObjectInputStream objectIn = new ObjectInputStream(input);
        return (T) objectIn.readObject();
    }
    
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
