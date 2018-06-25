/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sumzerotrading.realtime.bar.ib;

import com.sumzerotrading.data.BarData;
import com.sumzerotrading.data.Ticker;
import com.sumzerotrading.historicaldata.IHistoricalDataProvider;
import com.sumzerotrading.marketdata.IQuoteEngine;
import com.sumzerotrading.realtime.bar.IRealtimeBarEngine;
import com.sumzerotrading.realtime.bar.RealtimeBarListener;
import com.sumzerotrading.realtime.bar.RealtimeBarRequest;
import java.util.HashMap;
import java.util.Map;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

/**
 *
 * @author Rob Terpilowski
 */
public class IBRealTimeBarEngine implements IRealtimeBarEngine {

    protected IQuoteEngine quoteEngine;
    protected IHistoricalDataProvider historicalDataProvider;
    protected Map<RealtimeBarRequest, IBarBuilder> barMap = new HashMap<RealtimeBarRequest, IBarBuilder>();
    protected SchedulerFactory schedulerFactory;
    //Mocked out by unit tests.
    protected IBarBuilder testBarBuilder = null;

    
    public IBRealTimeBarEngine(IQuoteEngine quoteEngine, IHistoricalDataProvider historicalDataProvider) {
        this.quoteEngine = quoteEngine;
        this.historicalDataProvider = historicalDataProvider;
        schedulerFactory = new StdSchedulerFactory();

    }

    @Override
    public boolean isConnected() {
        return quoteEngine.isConnected() && historicalDataProvider.isConnected();
    }

    
    
    @Override
    public void subscribeRealtimeBars(RealtimeBarRequest request, RealtimeBarListener listener) {
        if( request.getTimeUnit() != BarData.LengthUnit.MINUTE ) {
            if( request.getTimeUnit() == BarData.LengthUnit.HOUR ) {
                if( request.getTimeInterval() != 1 ) {
                    throw new IllegalStateException( "Only 1 hour and 1-60 minute bars are supported by IB realtime bar engine" );
                }
            } else {
                throw new IllegalStateException( "Only 1 hour and 1-60 minute bars are supported by IB realtime bar engine" );
            }
        } else {
            if( request.getTimeInterval() > 60 || request.getTimeInterval() < 1 ) {
                throw new IllegalStateException( "Only 1 hour and 1-60 minute bars are supported by IB realtime bar engine" );
            }
        }
        
        Ticker ticker = request.getTicker();
        IBarBuilder builder = barMap.get(request);
        if (builder == null) {
            builder = buildBarBuilder(schedulerFactory, request, historicalDataProvider);
            quoteEngine.subscribeLevel1(ticker, builder);
            barMap.put(request, builder);
        }
        builder.addBarListener(listener);

    }


    @Override
    public void unsubscribeRealtimeBars(RealtimeBarRequest request, RealtimeBarListener listener) {
        Ticker ticker = request.getTicker();
        IBarBuilder builder = barMap.get(request);
        if( builder != null ) {
            builder.removeBarListener(listener);
            if( builder.getListenerCount() == 0 ) {
                quoteEngine.unsubscribeLevel1(ticker, builder);
                barMap.remove(request);
            }
        }
    }
    
    
    protected IBarBuilder buildBarBuilder( SchedulerFactory schedulerFactory, RealtimeBarRequest request, IHistoricalDataProvider historicalDataProvider ) {
        if( testBarBuilder == null ) {
            return new BarBuilder(schedulerFactory, request, historicalDataProvider);
        } else {
            return testBarBuilder;
        }
    }
}
