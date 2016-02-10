/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sumzerotrading.realtime.bar.ib;

import com.sumzerotrading.data.BarData;
import com.sumzerotrading.data.CurrencyTicker;
import com.sumzerotrading.historicaldata.IHistoricalDataProvider;
import com.sumzerotrading.marketdata.ILevel1Quote;
import com.sumzerotrading.marketdata.QuoteType;
import com.sumzerotrading.realtime.bar.RealtimeBarListener;
import com.sumzerotrading.realtime.bar.RealtimeBarRequest;
import com.sumzerotrading.realtime.bar.ib.util.RealtimeBarUtil;
import java.util.ArrayList;
import java.util.List;
//import org.apache.log4j.Logger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;

/**
 *
 * @author Rob Terpilowski
 */
public class BarBuilder implements IBarBuilder {

    protected double open = -1;
    protected double high = -1;
    protected double low = -1;
    protected double close = -1;
    protected int volume = 0;
    protected Scheduler scheduler;
    protected List<RealtimeBarListener> listenerList = new ArrayList<RealtimeBarListener>();
    protected RealtimeBarRequest realtimeBarRequest;
  //  protected Logger logger = Logger.getLogger(BarBuilder.class);
    protected JobDetail job;
    protected boolean openInitialized = false;
    protected boolean highInitialized = false;
    protected boolean lowInitialized = false;
    protected double lastBid;
    protected double lastAsk;
    protected boolean isCurrency = false;
    protected int timeInterval = 0;

    public BarBuilder(SchedulerFactory schedulerFactory, RealtimeBarRequest request, IHistoricalDataProvider historicalDataProvider) {
        try {
            timeInterval = request.getTimeInteval();
            BarData.LengthUnit lengthUnit = request.getTimeUnit();
            
            if( lengthUnit == lengthUnit.HOUR ) {
                lengthUnit = BarData.LengthUnit.MINUTE;
                timeInterval = timeInterval * 60;
            }
            
            
            IHistoricalDataProvider.ShowProperty showProperty = IHistoricalDataProvider.ShowProperty.TRADES;
            if( request.getTicker() instanceof CurrencyTicker ) {
                showProperty = IHistoricalDataProvider.ShowProperty.MIDPOINT;
                isCurrency = true;
            }
            this.realtimeBarRequest = request;
            String jobName = RealtimeBarUtil.getJobName(request);
            scheduler = schedulerFactory.getScheduler();
            job = RealtimeBarUtil.buildJob(jobName, this);
            List<BarData> bars = historicalDataProvider.requestHistoricalData(request.getTicker(), 1, BarData.LengthUnit.DAY, request.getTimeInteval(), request.getTimeUnit(), showProperty, false);
            BarData firstBar = bars.get( bars.size()-1);
            setOpen(firstBar.getOpen());
            setHigh(firstBar.getHigh());
            setLow(firstBar.getLow());
            setClose( firstBar.getClose() );
            setVolume( (int) firstBar.getVolume() );
            scheduler.scheduleJob(job, RealtimeBarUtil.getTrigger(jobName, timeInterval, lengthUnit));
            if( ! scheduler.isStarted() ) {
                scheduler.start();
            }
        } catch (SchedulerException ex) {
            //logger.error(ex, ex);
            ex.printStackTrace();
            throw new IllegalStateException(ex);
        }
    }

    public void quoteRecieved(ILevel1Quote quote) {
        if (quote.getType() == QuoteType.VOLUME) {
            setVolume( (int) quote.getValue().doubleValue() );
        } else if( validQuote( quote ) ) {
            double value = getValue( quote );
            setHigh(value);
            setLow(value);
            setClose(value);
            setOpen(value);
        }
    }
    
    
    protected double getValue( ILevel1Quote quote ) {
        if( isCurrency ) {
            if( quote.getType() == QuoteType.BID ) {
                lastBid = quote.getValue().doubleValue();
            } else if( quote.getType() == QuoteType.ASK ) {
                lastAsk = quote.getValue().doubleValue();
            }
            if( lastBid > 0 && lastAsk > 0 ) {
                return lastAsk - ((lastAsk-lastBid)/2.0);
            } else {
                return 0;
            }
        } else {
            return quote.getValue().doubleValue();
        }
    }
    
    protected boolean validQuote( ILevel1Quote quote ) {
        if( isCurrency ) {
            return quote.getType() == QuoteType.BID ||
                    quote.getType() == QuoteType.ASK;
        } else {
            return quote.getType() == QuoteType.LAST;
        }
    }

    @Override
    public void addBarListener(RealtimeBarListener listener) {
        synchronized (listenerList) {
            listenerList.add(listener);
        }
    }

    @Override
    public void removeBarListener(RealtimeBarListener listener) {
        synchronized (listenerList) {
            listenerList.remove(listener);
        }
    }

    protected final void setHigh(double price) {
        if( price == 0 ) {
            return;
        }
        if (! highInitialized ) {
            high = price;
            highInitialized = true;
        } else if (price > high) {
            high = price;
        }
    }

    protected final void setLow(double price) {
        if( price == 0 ) {
            return;
        }
        if (! lowInitialized ) {
            low = price;
            lowInitialized = true;
        } else if (price < low) {
            low = price;
        }
    }

    protected final void setOpen(double price) {
        if( price == 0 ) {
            return;
        }
        if (! openInitialized ) {
            this.open = price;
            openInitialized = true;
        }
    }

    protected final void setClose(double price) {
        if( price == 0 ) {
            return;
        }
        close = price;
    }

    protected final void setVolume(int volume) {
        this.volume += volume;
    }
    

    public void buildBarAndFireEvents() {

        BarData bar = new BarData();
        bar.setOpen(open);
        bar.setHigh(high);
        bar.setLow(low);
        bar.setClose(close);
        bar.setVolume(volume);
        bar.setDateTime(RealtimeBarUtil.getBarDate());
        open = close;
        high = close;
        low = close;
        volume = 0;
        openInitialized = false;
        highInitialized = false;
        lowInitialized = false;
        
        fireEvent(bar);
    }

    protected void fireEvent(BarData bar) {
        synchronized (listenerList) {
            for (RealtimeBarListener listener : listenerList) {
                listener.realtimeBarReceived(realtimeBarRequest.getRequestId(), realtimeBarRequest.getTicker(), bar);
            }
        }
    }


    public int getListenerCount() {
        return listenerList.size();
    }

    public void stop() {
        try {
            scheduler.deleteJob( job.getKey() );
        } catch (SchedulerException ex) {
            throw new IllegalStateException( ex );
        }
    }
    
    
}
