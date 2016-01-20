/*
 * MarketData.java
 *
 * Created on August 21, 2002, 12:39 PM
 */

package com.limituptrading.data;

import java.util.Vector;
import java.util.Date;
import java.text.SimpleDateFormat;


/**
 * MarketData contains an array of Bars that represent a time series 
 * of data.  A bar can either be inserted at the beginning or appended to
 * the end of this time series.
 *
 * @version $Revision: 1.1 $
 * @author  robbob
 */
public class MarketData {
    
    private Vector barVector = new Vector();
    private SimpleDateFormat dateFormatter =  new SimpleDateFormat( "dd/MM/yy" );
    
    /**
     * Inserts the specified bar at the front of the market data.
     * Useful for data providers such as Yahoo that sort their data
     * from most recent to oldest.
     * @param bar the bar to insert.
     */
    public void insertBar( BarData bar ) {
        barVector.insertElementAt( bar, 0 );
    }//insertBar()
    
    
    /**
     * Appends the specified bar to the end of the market data.
     * @param bar the bar to append.
     */
    public void appendBar( BarData bar ) {
        barVector.add( bar );
    }//appendBar()
    
    
    public void replaceBar( BarData oldBar, BarData newBar ) {
    	int oldIndex = barVector.indexOf( oldBar );
    	barVector.add(oldIndex, newBar );
    	barVector.remove( oldBar );
    }
    
    /**
     * Gets all the Bars available for this Market Data.
     * @return All bars available for this market data.
     */
    public BarData[] getBars() {
        BarData[] bars = new BarData[ barVector.size() ];
        barVector.copyInto( bars );
        return bars;
    }//getBars()
    
    
 
    
  
    
    /**
     * Gets the open data for the MarketDAta with the oldest data
     * at the beginning of the array.
     * @return an array of open data.
     */
    public double[] getOpenData() {
        BarData[] bars = getBars();
        double[] d = new double[ bars.length ];
        for( int i = 0; i < bars.length; i++ ) {
            d[i] = bars[i].getOpen();
        }//for
        
        return d;
    }//getOpenData()

    /**
     * Gets the high data for the MarketDAta with the oldest data
     * at the beginning of the array.
     * @return an array of high data.
     */    
    public double[] getHighData() {
        BarData[] bars = getBars();
        double[] d = new double[ bars.length ];
        for( int i = 0; i < bars.length; i++ ) {
            d[i] = bars[i].getHigh();
        }//for
        
        return d;
    }//getOpenData()
    
    
    /**
     * Gets the low data for the MarketDAta with the oldest data
     * at the beginning of the array.
     * @return an array of low data.
     */        
    public double[] getLowData() {
        BarData[] bars = getBars();
        double[] d = new double[ bars.length ];
        for( int i = 0; i < bars.length; i++ ) {
            d[i] = bars[i].getLow();
        }//for
        
        return d;
    }//getOpenData()

    
    /**
     * Gets the close data for the MarketDAta with the oldest data
     * at the beginning of the array.
     * @return an array of close data.
     */       
    public double[] getCloseData() {
        BarData[] bars = getBars();
        double[] d = new double[ bars.length ];
        for( int i = 0; i < bars.length; i++ ) {
            d[i] = bars[i].getClose();
        }//for
        
        return d;
    }//getOpenData()

    
    /**
     * Gets the volume data for the MarketDAta with the oldest data
     * at the beginning of the array.
     * @return an array of volume data.
     */       
    public double[] getVolumeData() {
        BarData[] bars = getBars();
        double[] d = new double[ bars.length ];
        for( int i = 0; i < bars.length; i++ ) {
            d[i] = bars[i].getVolume();
        }//for
        
        return d;
    }//getOpenData()              
        

    /**
     * Gets the open interest data for the MarketDAta with the oldest data
     * at the beginning of the array.
     * @return an array of open interest data.
     */           
    public double[] getOpenInterestData() {
        BarData[] bars = getBars();
        double[] d = new double[ bars.length ];
        for( int i = 0; i < bars.length; i++ ) {
            d[i] = bars[i].getOpenInterest();
        }//for
        
        return d;
    }//getOpenData()          

}//class MarketData
