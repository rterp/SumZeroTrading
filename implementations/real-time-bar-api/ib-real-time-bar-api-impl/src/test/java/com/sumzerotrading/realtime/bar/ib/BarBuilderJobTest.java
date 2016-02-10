/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sumzerotrading.realtime.bar.ib;

import com.sumzerotrading.realtime.bar.ib.util.RealtimeBarUtil;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import static org.junit.Assert.*;

/**
 *
 * @author Rob Terpilowski
 */
public class BarBuilderJobTest {
    
    protected Mockery mockery;
    
    public BarBuilderJobTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
        mockery = new Mockery();
    }
    
    @After
    public void tearDown() {
    }

    
    @Test
    public void testExecute() throws Exception {
        final JobExecutionContext mockContext = mockery.mock( JobExecutionContext.class);
        final JobDetail mockJobDetail = mockery.mock( JobDetail.class );
        final JobDataMap dataMap = new JobDataMap();
        
        final IBarBuilder mockBarBuilder = mockery.mock( IBarBuilder.class );
        dataMap.put( RealtimeBarUtil.BAR_BUILDER_DATA, mockBarBuilder );
        
        
        mockery.checking( new Expectations() {{
            one(mockContext).getJobDetail();
            will(returnValue(mockJobDetail));
            
            one(mockJobDetail).getJobDataMap();
            will(returnValue( dataMap ) );
            
            one(mockBarBuilder).buildBarAndFireEvents();
            
        }}
        );
        
        new BarBuilderJob().execute(mockContext);
        mockery.assertIsSatisfied();
    }
}
