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

/**
 * Defines common functionality of all Quote objects.
 * 
 * @author RobTerpilowski
 */
public abstract class AbstractQuote implements IQuote {

	protected Ticker ticker;
	protected QuoteType type;
	protected Date timeStamp;
        
	
        /**
         * Constructs a new Quote object. 
         *
         * @param ticker The ticker this quote is for.
         * @param type The type of quote.
         * @param timeStamp The time of the quote.
         */
	public AbstractQuote( Ticker ticker, QuoteType type, Date timeStamp ) {
		this.ticker = ticker;
		this.type = type;
		this.timeStamp = timeStamp;
	}
        

        /**
         * Gets the ticker for this quote
         * @return The ticker for this quote.
         */
        @Override
	public Ticker getTicker() {
		return ticker;
	}

        /**
         * Gets the type of quote (bid,ask,etc)
         * @return The type of quote.
         */
        @Override
	public QuoteType getType() {
		return type;
	}
	
        /**
         * Gets the time of this quote
         * @return The time of the quote
         */
        @Override
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
