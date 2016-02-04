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

/**
 *
 * @author RobTerpilowski
 */
public class FuturesUtil {
    
    
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
                throw new IllegalStateException("Invalid Month: " + month);

        }
    }
}
