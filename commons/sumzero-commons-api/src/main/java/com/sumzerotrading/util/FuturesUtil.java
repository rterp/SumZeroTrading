/**
 * MIT License
 *
 * Copyright (c) 2015  Rob Terpilowski
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING
 * BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.sumzerotrading.util;

import com.sumzerotrading.data.SumZeroException;

/**
 *
 * @author RobTerpilowski
 */
public class FuturesUtil {


    public static String getFullFuturesSymbolWithTwoDigitYear( String ticker, int month, int year ) {
        StringBuilder sb = new StringBuilder();
        sb.append(ticker)
                .append(getContractMonthSymbol(month))
                .append(getTwoDigitYearString(year));
        
        return sb.toString();
    }    
    
    
    
    public static String getFullFuturesSymbolWithOneDigitYear( String ticker, int month, int year ) {
        StringBuilder sb = new StringBuilder();
        sb.append(ticker)
                .append(getContractMonthSymbol(month))
                .append(getOneDigitYearString(year));
        
        return sb.toString();
    }
    
    
    public static String getTwoDigitYearString(int year) {
        String yearString = Integer.toString(year);
        return yearString.substring(yearString.length() - 2);        
    }
    
    public static String getOneDigitYearString(int year) {
        String yearString = Integer.toString(year);
        return yearString.substring(yearString.length() - 1);
    }

    
    public static String getContractMonthSymbol(int month) {
        switch (month) {
            case 1:
                return "F";
            case 2:
                return "G";
            case 3:
                return "H";
            case 4:
                return "J";
            case 5:
                return "K";
            case 6:
                return "M";
            case 7:
                return "N";
            case 8:
                return "Q";
            case 9:
                return "U";
            case 10:
                return "V";
            case 11:
                return "X";
            case 12:
                return "Z";
            default:
                throw new SumZeroException("Invalid Month: " + month);

        }
    }
    
    
    public static String getMonthAbbreviation(int month) {
        switch(month) {
            case 1:
                return "JAN";
            case 2:
                return "FEB";
            case 3:
                return "MAR";
            case 4:
                return "APR";
            case 5:
                return "MAY";
            case 6:
                return "JUN";
            case 7:
                return "JUL";
            case 8:
                return "AUG";
            case 9:
                return "SEP";
            case 10:
                return "OCT";
            case 11:
                return "NOV";
            case 12:
                return "DEC";
            default:
                throw new SumZeroException("Invalid Month: " + month);
        }
    }
}
