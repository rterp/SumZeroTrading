/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sumzerotrading.reporting;

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
        
        return "";
    }
    
}
