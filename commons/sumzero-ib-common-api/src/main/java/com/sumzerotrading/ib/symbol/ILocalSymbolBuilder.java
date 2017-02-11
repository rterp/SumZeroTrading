/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sumzerotrading.ib.symbol;

import com.sumzerotrading.data.Exchange;

/**
 *
 * @author RobTerpilowski
 */
public interface ILocalSymbolBuilder {
    
    
    public String buildLocalSymbol( String symbol, int expirationMonth, int expirationYear );

    
}
