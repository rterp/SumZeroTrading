package com.limituptrading.marketdata;

import java.util.Date;

import com.limituptrading.data.Ticker;

public class Level2Quote extends AbstractQuote implements ILevel2Quote {

	protected IMarketDepthBook book;
	
	public Level2Quote( Ticker ticker, QuoteType type, Date timeStamp, IMarketDepthBook book ) {
		super( ticker, type, timeStamp );
		this.book = book;
	}
	
	public IMarketDepthBook getMarketDepthBook() {
		return book;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((book == null) ? 0 : book.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Level2Quote other = (Level2Quote) obj;
		if (book == null) {
			if (other.book != null)
				return false;
		} else if (!book.equals(other.book))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Level2Quote [book=" + book + ", ticker=" + ticker
				+ ", timeStamp=" + timeStamp + ", type=" + type + "]";
	}
	
	
	
	
}
