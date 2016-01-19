/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
