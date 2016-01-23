package com.limituptrading.marketdata;

import java.util.Date;
import com.limituptrading.data.Ticker;

public abstract class AbstractQuote implements IQuote {

	protected Ticker ticker;
	protected QuoteType type;
	protected Date timeStamp;
        
	
	public AbstractQuote( Ticker ticker, QuoteType type, Date timeStamp ) {
		this.ticker = ticker;
		this.type = type;
		this.timeStamp = timeStamp;
	}
        

	public Ticker getTicker() {
		return ticker;
	}

	public QuoteType getType() {
		return type;
	}
	
	public Date getTimeStamp() {
		return timeStamp;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ticker == null) ? 0 : ticker.hashCode());
		result = prime * result + ((timeStamp == null) ? 0 : timeStamp.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractQuote other = (AbstractQuote) obj;
		if (ticker == null) {
			if (other.ticker != null)
				return false;
		} else if (!ticker.equals(other.ticker))
			return false;
		if (timeStamp == null) {
			if (other.timeStamp != null)
				return false;
		} else if (!timeStamp.equals(other.timeStamp))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AbstractQuote [ticker=" + ticker + ", timeStamp=" + timeStamp
				+ ", type=" + type + "]";
	}
	
	
	
	
	
	
	
}
