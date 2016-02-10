/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sumzerotrading.realtime.bar.ib.util;

import com.sumzerotrading.data.BarData.LengthUnit;
import com.sumzerotrading.data.StockTicker;
import com.sumzerotrading.data.Ticker;
import com.sumzerotrading.realtime.bar.RealtimeBarRequest;
import com.sumzerotrading.realtime.bar.ib.BarBuilderJob;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;

/**
 *
 * @author Rob Terpilowski
 */
public class RealtimeBarUtilTest extends TestCase {

    public RealtimeBarUtilTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testBuildJob() {
        String jobName = "myJob";
                JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put( RealtimeBarUtil.BAR_BUILDER_DATA, null );
        JobDetail expectedJob = JobBuilder.newJob(BarBuilderJob.class).withIdentity(jobName).usingJobData(jobDataMap).build();
        assertEquals(expectedJob, RealtimeBarUtil.buildJob(jobName, null));
    }

    @Test
    public void testGetFirstScheduledDate() {
        int interval = 1;
        Date date = new Date();
        
        LocalDateTime expected = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        expected = expected.withNano(0).withSecond(0).plusMinutes(1);

        while (expected.getMinute() % interval != 0) {
            expected = expected.plusMinutes(1);
        }

        assertEquals(Date.from(expected.atZone(ZoneId.systemDefault()).toInstant()), RealtimeBarUtil.getFirstScheduledDate(date, interval));
    }
    

    @Test
    public void testGetFirstScheduledDate_60MinuteBar() {
        int interval = 60;
        Date date = new Date();

        GregorianCalendar expected = new GregorianCalendar();
        expected.setTime(date);
        expected.set(Calendar.MILLISECOND, 0);
        expected.set(Calendar.SECOND, 0);
        expected.add(Calendar.MINUTE, 1);

        expected.set( Calendar.MINUTE, 0 );
        expected.add( Calendar.HOUR, 1);

        assertEquals(expected.getTime(), RealtimeBarUtil.getFirstScheduledDate(date, interval));
    }    

    @Test
    public void testGetBarName() {
        int requestId = 1;
        Ticker ticker = new StockTicker("qqq");
        int timeInterval = 1;
        RealtimeBarRequest realtimeBarRequest = new RealtimeBarRequest(requestId, ticker, timeInterval, LengthUnit.MINUTE);
        String expected = "ticker: " + realtimeBarRequest.getTicker().getSymbol() + " barLength:" + realtimeBarRequest.getTimeInteval() + realtimeBarRequest.getTimeUnit();

        assertEquals(expected, RealtimeBarUtil.getJobName(realtimeBarRequest));
    }

    
    @Test
    public void testGetBarDate_ZeroSecond() {
        LocalDateTime expected = LocalDateTime.of(2012, 6, 1, 5, 30, 0, 0);
        
        RealtimeBarUtil.testDateTime = expected;

        assertEquals(expected, RealtimeBarUtil.getBarDate());
    }
    
        @Test
    public void testGetBarDate_minus3Seconds() {
        LocalDateTime testDateTime = LocalDateTime.of(2012, 6, 1, 5, 59, 57, 0);

        
        LocalDateTime expected = testDateTime.withSecond(0).withMinute(0).withHour(6);
        
        RealtimeBarUtil.testDateTime = testDateTime;

        assertEquals(expected, RealtimeBarUtil.getBarDate());
    }
    
     @Test
    public void testGetBarDate_plus3Seconds() {
        LocalDateTime testDateTime = LocalDateTime.of(2012, 6, 1, 5, 1, 3, 0);
        
        LocalDateTime expected = testDateTime.withSecond(0).withMinute(1).withHour(5);
        
        RealtimeBarUtil.testDateTime = testDateTime;

        assertEquals(expected, RealtimeBarUtil.getBarDate());
    }    
}
