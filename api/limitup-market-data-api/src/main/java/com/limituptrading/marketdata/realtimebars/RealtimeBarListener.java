package com.limituptrading.marketdata.realtimebars;

import com.limituptrading.data.Bar;
import com.limituptrading.data.Ticker;

public interface RealtimeBarListener {

	
	public void realtimeBarReceived( int requestId, Ticker ticker, Bar bar );
	
}
