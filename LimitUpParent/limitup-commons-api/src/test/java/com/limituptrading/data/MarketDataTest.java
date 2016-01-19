package com.limituptrading.data;

import java.time.LocalDateTime;
import java.util.GregorianCalendar;
import junit.framework.TestCase;

public class MarketDataTest extends TestCase {

	
	
	public void testAppendBar() {
		MarketData data = new MarketData();
                LocalDateTime cal = LocalDateTime.now();
                
		
		BarData bar1 = new BarData(cal, 1,1,1,1,1);
		BarData bar2 = new BarData(cal, 2,2,2,2,2);
		
		data.appendBar( bar1 );
		
		BarData[] bars = data.getBars();
		assertEquals( 1, data.getBars().length );
		assertEquals( bar1, data.getBars()[0] );
		
		data.appendBar( bar2 );
		bars = data.getBars();
		assertEquals(2, data.getBars().length );
		assertEquals( bar1, data.getBars()[0] );
		assertEquals( bar2, data.getBars()[1] );
		
	}
	
	
	public void testInsertBar() {
		MarketData data = new MarketData();
                LocalDateTime cal = LocalDateTime.now();
		BarData bar1 = new BarData(cal, 1,1,1,1,1);
		BarData bar2 = new BarData(cal, 2,2,2,2,2);
		
		data.insertBar(bar1);
		
		BarData[] bars = data.getBars();
		assertEquals( 1, data.getBars().length );
		assertEquals( bar1, data.getBars()[0] );
		
		data.insertBar( bar2 );
		bars = data.getBars();
		assertEquals(2, data.getBars().length );
		assertEquals( bar2, data.getBars()[0] );
		assertEquals( bar1, data.getBars()[1] );
		
	}
	
	
	public void testFoo() {
		MarketData data = new MarketData();
                LocalDateTime cal = LocalDateTime.now();
		BarData bar1 = new BarData(cal, 1,1,1,1,1);
		BarData bar2 = new BarData(cal, 2,2,2,2,2);
		BarData bar3 = new BarData(cal, 3,3,3,3,3);
		BarData bar4 = new BarData(cal, 4,4,4,4,4);
		
		
		data.appendBar(bar1);
		data.appendBar(bar2);
		data.appendBar(bar3);
		
		
		
		BarData[] bars = data.getBars();
		assertEquals(3, data.getBars().length );
		assertEquals( bar1, data.getBars()[0] );
		assertEquals( bar2, data.getBars()[1] );
		assertEquals( bar3, data.getBars()[2] );
		
		
		data.replaceBar(bar2, bar4);
		
		bars = data.getBars();
		assertEquals(3, data.getBars().length );
		assertEquals( bar1, data.getBars()[0] );
		assertEquals( bar4, data.getBars()[1] );
		assertEquals( bar3, data.getBars()[2] );
		
		
	}
	
	
	
	
}
