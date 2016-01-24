/*
 * Util.java
 */
package com.ib.client;

public class Util {

    public static String NormalizeString(String str) {
    	return str != null ? str : "";
    }

    public static int StringCompare(String lhs, String rhs) {
    	return NormalizeString(lhs).compareTo(NormalizeString(rhs));
    }

    public static int StringCompareIgnCase(String lhs, String rhs) {
    	return NormalizeString(lhs).compareToIgnoreCase(NormalizeString(rhs));
    }
    
    public static String IntMaxString(int value) {
    	return (value == Integer.MAX_VALUE) ? "" : "" + value;
    }
    
    public static String DoubleMaxString(double value) {
    	return (value == Double.MAX_VALUE) ? "" : "" + value;
    }

}
