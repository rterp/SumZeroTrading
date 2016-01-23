package com.limituptrading.marketdata;

import java.util.Date;
import com.limituptrading.data.Ticker;

public interface IQuote {
	
	public Ticker getTicker();
	
	public QuoteType getType();
	
	public Date getTimeStamp();
}
