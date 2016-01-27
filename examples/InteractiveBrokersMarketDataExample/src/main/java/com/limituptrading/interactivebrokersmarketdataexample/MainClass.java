/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.limituptrading.interactivebrokersmarketdataexample;

import com.limituptrading.data.Exchange;
import com.limituptrading.data.FuturesTicker;
import com.limituptrading.data.StockTicker;
import com.limituptrading.interactive.brokers.client.InteractiveBrokersClient;
import com.limituptrading.marketdata.ILevel1Quote;
import com.limituptrading.marketdata.QuoteEngine;

/**
 *
 * @author RobTerpilowski
 */
public class MainClass {
    
    
    public void start() {
        InteractiveBrokersClient ibClient = new InteractiveBrokersClient("localhost", 4001, 1);
        ibClient.connect();
        
//        FuturesTicker esTicker=  new FuturesTicker();
//        esTicker.setSymbol("ES");
//        esTicker.setExpiryMonth(3);
//        esTicker.setExpiryYear(2016);
//        esTicker.setExchange(Exchange.GLOBEX);

        StockTicker esTicker=  new StockTicker("MSFT");
        esTicker.setExchange(Exchange.INTERACTIVE_BROKERS_SMART);
        
        QuoteEngine quoteEngine = ibClient.getQuoteEngine();
        quoteEngine.subscribeLevel1(esTicker, (ILevel1Quote quote) -> {
            System.out.println("Received Quote: " + quote );
        });
        
    }
    
    public static void main(String[] args) {
        new MainClass().start();
    }
}
