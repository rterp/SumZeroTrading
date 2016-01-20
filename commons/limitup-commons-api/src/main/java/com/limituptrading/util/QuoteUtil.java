package com.limituptrading.util;

import com.limituptrading.data.Ticker;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 
 * @author robbob
 * @version $Revision: 1.1 $
 * @date $Date: 2006/12/29 16:06:34 $
 */
public class QuoteUtil {

    
    /**
     * Takes the double value and converts it into a BigDecimal with the correct min tick size for
     * the specified ticker.
     *
     * @param ticker The ticker to calc the formatted price for
     * @param value The value to scale
     * @return A properly scaled BigDecimal for this ticker.
     */
    public static BigDecimal getBigDecimalValue(Ticker ticker, double value) {
        return roundPrice(new BigDecimal(value), ticker.getMinimumTickSize());

    }

    /**
     * Creates a new BigDecimal value with 0 decimal places for the specified int.
     * @param value The int to convert into a BigDecimal
     * @return A BigDecimal representation of the specified int.
     */
    public static BigDecimal getBigDecimalValue(int value) {
        return new BigDecimal(value).setScale(0, RoundingMode.HALF_UP);
    }

    
    /**
     * Rounds the specified price to the specified tick size
     * @param price The price to round
     * @param ticksize The ticksize to round to
     * @return A properly formatted BigDecimal for the given price and tick size.
     */
    public static BigDecimal roundPrice(BigDecimal price, BigDecimal ticksize) {
        return roundPrice(price, ticksize, ticksize.scale());
    }

    
    /**
     * Rounds the price to the specified tick size and number of decimal places
     * 
     * @param price The price to round
     * @param ticksize The tick size to round to
     * @param decimalPlaces The number of decimal places to round to.
     * @return A properly formatted BigDecimal for the given tick size and decimal places.
     */
    public static BigDecimal roundPrice(BigDecimal price, BigDecimal ticksize, int decimalPlaces) {
        int intValue = price.intValue();
        double fraction = price.doubleValue() - intValue;
        long ticks = Math.round(fraction / ticksize.doubleValue());
        BigDecimal returnValue = new BigDecimal(intValue).add(new BigDecimal(ticks).multiply(ticksize));
        returnValue.setScale(decimalPlaces, BigDecimal.ROUND_HALF_UP);
        return returnValue;
    }
}
