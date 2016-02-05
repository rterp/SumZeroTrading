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

package com.sumzerotrading.marketdata;

import java.util.Date;

import com.sumzerotrading.data.Ticker;

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
