/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sumzerotrading.reporting.csv;

import com.sumzerotrading.reporting.IRoundTrip;

/**
 *
 * @author RobTerpilowski
 */
public class PairTradeRoundTripBuilder implements IRoundTripBuilder {

    @Override
    public IRoundTrip buildRoundTrip() {
        return new PairTradeRoundTrip();
    }
    
    
}
