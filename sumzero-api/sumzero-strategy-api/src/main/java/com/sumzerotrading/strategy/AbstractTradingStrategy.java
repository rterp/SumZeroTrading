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

package com.sumzerotrading.strategy;

import com.sumzerotrading.broker.IBroker;
import com.sumzerotrading.historicaldata.IHistoricalDataProvider;
import com.sumzerotrading.marketdata.IQuoteEngine;
import com.sumzerotrading.realtime.bar.IRealtimeBarEngine;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.GregorianCalendar;

/**
 *
 * @author Rob Terpilowski
 */
public abstract class AbstractTradingStrategy implements ITradingStrategy {
    
    protected IQuoteEngine quoteEngine;
    protected IHistoricalDataProvider historicalDataProvider;
    protected IBroker broker;
    protected IRealtimeBarEngine realtimeBarEngine;

    @Override
    public boolean isInitialized() {
        return (broker!=null && broker.isConnected()) &&
                (historicalDataProvider != null && historicalDataProvider.isConnected() ) &&
                (quoteEngine != null && quoteEngine.isConnected() ) &&
                (realtimeBarEngine != null && realtimeBarEngine.isConnected() );
                
    }

    @Override
    public ZonedDateTime getCurrentTime() {
            throw new IllegalStateException( "Not Implemented" );
        
    }

    @Override
    public LocalTime getAutoStopTime() {
        return null;
    }
    
    
    
    
    
    @Override
    public void setBroker(IBroker broker) {
        this.broker = broker;
    }

    
    @Override
    public void setHistoricalDataProvider(IHistoricalDataProvider historicalDataProvider) {
        this.historicalDataProvider = historicalDataProvider;
    }

    

    @Override
    public void setMarketDataProvider(IQuoteEngine quoteEngine) {
        this.quoteEngine = quoteEngine;
    }

    @Override
    public void setRealTimeBarProvider(IRealtimeBarEngine realtimeBarProvider) {
        this.realtimeBarEngine = realtimeBarProvider;
    }
    
    
    protected ZonedDateTime getBrokerTime() {
        if( ! isInitialized() ) {
            throw new IllegalStateException( "The Strategy has not yet been initialized" );
        }
                return broker.getCurrentTime();
    }
    
    
}
