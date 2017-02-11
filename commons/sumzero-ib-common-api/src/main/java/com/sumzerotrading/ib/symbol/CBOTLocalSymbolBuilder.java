/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sumzerotrading.ib.symbol;

import com.sumzerotrading.util.FuturesUtil;

/**
 *
 * @author RobTerpilowski
 */
public class CBOTLocalSymbolBuilder implements ILocalSymbolBuilder {

    @Override
    public String buildLocalSymbol(String symbol, int expirationMonth, int expirationYear) {
        StringBuilder sb = new StringBuilder();
        sb.append(symbol)
                .append("   ")
                .append(FuturesUtil.getMonthAbbreviation(expirationMonth))
                .append(" ")
                .append(FuturesUtil.getTwoDigitYearString(expirationYear));
        return sb.toString();
    }
    
    
}
