package com.limituptrading.marketdata;

import java.util.Date;
import com.limituptrading.data.Ticker;
import java.math.BigDecimal;

public class Level1Quote extends AbstractQuote implements ILevel1Quote {

	protected BigDecimal value;
	
	public Level1Quote( Ticker ticker, QuoteType type, Date timeStamp, BigDecimal value ){
		 super( ticker, type, timeStamp );
		 this.value = value;
	}
	
	

	public BigDecimal getValue() {
		return value;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		long temp;
		temp = Double.doubleToLongBits(value.doubleValue());
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		Level1Quote other = (Level1Quote) obj;
                if( ! value.equals(other.value) )
			return false;
		return true;
	}



	@Override
	public String toString() {
		return "Level1Quote [ticker=" + ticker.getSymbol() + ",type=" + type + ",value=" + value + ",timestamp=" + timeStamp + "]";
	}

	
	
	
}
