/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.limituptrading.data;

import static com.limituptrading.data.Commodity.*;


/**
 *
 * @author RobTerpilowski
 */
public class BloombergPriceConverter {

    
    
    
    public static double nativeToBloomberg( double nativePrice, Commodity commodity ) {
    
            return nativePrice * getMultiplier(commodity);
    }
    
    
    public static double bloombergToNative( double bloombergPrice, Commodity commodity ) {
        return bloombergPrice / getMultiplier(commodity);
    }
    
    
    public static double getMultiplier( Commodity commodity ) {
    
        double multiplier = 0;
        
        
        
        if( commodity == JAPANESE_YEN_GLOBEX ) {
                multiplier = 10000.0;
        } else if( commodity == CANADIAN_DOLLAR_GLOBEX ||
                commodity == COPPER_NYMEX ||
                commodity == HEATING_OIL_NYMEX ||
                commodity == SWISS_FRANC_GLOBEX ) {
            multiplier  = 100.0;
        } else if( commodity == CORN_ECBOT ||
                commodity == CRUDE_OIL_NYMEX ||
                commodity == DOW_INDEX_MINI_ECBOT ||
                commodity == EURO_GLOBEX ||
                commodity == GOLD_NYMEX ||
                commodity == NASDAQ100_INDEX_MINI_GLOBEX ||
                commodity == NATURAL_GAS_NYMEX ||
                commodity == SILVER_NYMEX ||
                commodity == SOYBEANS_ECBOT ||
                commodity == SP500_INDEX_MINI_GLOBEX ||
                commodity == BOND_30_YEAR_ECBOT ||
                commodity == BOND_10_YEAR_ECBOT ||
                commodity == WHEAT_ECBOT ) {
            multiplier = 1.0;
        } else {
            throw new IllegalStateException( "Unknown Commodity: " + commodity );
        }
        
        return multiplier;
    }        
    
}
