/**
 * MIT License

Copyright (c) 2015  Rob Terpilowski

Permission is hereby granted, free of charge, to any person obtaining a copy of this software 
and associated documentation files (the "Software"), to deal in the Software without restriction, 
including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, 
and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, 
subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING 
BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. 
IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, 
WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE 
OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.limituptrading.util;

import com.limituptrading.data.Ticker;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 
 * @author Rob Terpilowski
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
