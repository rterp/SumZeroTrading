/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sumzerotrading.reporting;

import com.sumzerotrading.data.SumZeroException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 *
 * @author RobTerpilowski
 */
public class TradeUIDProvider {
    
    protected static TradeUIDProvider provider = null;
    
    public synchronized static TradeUIDProvider getInstance() {
        if( provider == null ) {
            provider = new TradeUIDProvider();
        }
        
        return provider;
    }
    
    
    public String getUID() {
        try {
            SecureRandom prng = SecureRandom.getInstance("SHA1PRNG");
            //generate a random number
            return new Integer(Math.abs(prng.nextInt())).toString();
        } catch (NoSuchAlgorithmException ex) {
            throw new SumZeroException(ex);
        }
    }
    
    
    public static void setMockTradeUIDProvider( TradeUIDProvider mockProvider ) {
        provider = mockProvider;
    }
    
}
