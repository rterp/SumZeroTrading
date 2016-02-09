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

package com.sumzerotrading.ib.historical;

import com.sumzerotrading.data.BarData;
import com.sumzerotrading.ib.historical.HistoricalData;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.logging.Level;
//import org.apache.log4j.Logger;

/**
 *
 * @author Rob Terpilowski
 */
public class IBHistoricalDataEventProcessor {

    protected volatile boolean shouldRun = false;
    protected int requestId;
    protected List<BarData> historicalData = new ArrayList<BarData>();
    protected BlockingQueue<List<BarData>> dataQueue = new SynchronousQueue<List<BarData>>();
    protected List<BarData> dataList = new ArrayList<BarData>();
  //  protected Logger logger = Logger.getLogger( IBHistoricalDataEventProcessor.class );
    protected BarData.LengthUnit barSizeUnit;
    protected SimpleDateFormat dateFormatter;
    
    public IBHistoricalDataEventProcessor( int requestId, BarData.LengthUnit barSizeUnit ) {
        this.requestId = requestId;
        if( barSizeUnit == BarData.LengthUnit.DAY ) {
            dateFormatter = new SimpleDateFormat("yyyyMMdd");
        } else {
            dateFormatter = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        }
    }
    
    
    
    public List<BarData> getHistoricalData() {
        try {
            return dataQueue.take();
        } catch (InterruptedException ex) {
           // logger.error( ex, ex );
            ex.printStackTrace();
            throw new IllegalStateException( ex );
        }
    }
    
    
    public void addHistoricalData( HistoricalData data ) {
        if( data.getOpen() == -1 ) {
            try {
            //    logger.debug( data );
                dataQueue.put(dataList);
            } catch (InterruptedException ex) {
              // logger.error( ex, ex );
            ex.printStackTrace();
            }
        } else {
            dataList.add( HistoricalDataUtils.buildBarData(data));
        }
    }
    
    public void finished() {
        try {
            dataQueue.put(dataList);
        } catch (InterruptedException ex) {
            // logger.error( ex, ex );
            ex.printStackTrace();
        }
    }

    public SimpleDateFormat getDateFormatter() {
        return dateFormatter;
    }
    
    
}
