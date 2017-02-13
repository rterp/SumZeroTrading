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
public class LocalSymbolBuilderFactory {


    

    public static ILocalSymbolBuilder getLocalSymbolBuilder( Exchange exchange ) {
        if( exchange == Exchange.HKFE ) {
            return new HKFELocalSymbolBuilder();
        } else if( exchange == Exchange.ECBOT ) {
            return new CBOTLocalSymbolBuilder();
        } else if( exchange == Exchange.OSE ) {
            return new OSELocalSymbolBuilder();
        } else {
            return new DefaultLocalSymbolBuilder();
        }
    }
}
