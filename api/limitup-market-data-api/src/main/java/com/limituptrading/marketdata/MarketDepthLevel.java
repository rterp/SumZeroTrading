package com.limituptrading.marketdata;

import java.math.BigDecimal;
import java.math.BigInteger;

public class MarketDepthLevel implements Comparable<MarketDepthLevel> {

	private BigDecimal price;
	private BigDecimal size;
	private MarketDepthBook.Side side;
	private int deciamlPlaces = 0;
        
	
	public MarketDepthLevel(MarketDepthBook.Side side, BigDecimal price, int size ) {
		this.side = side;
		this.price = price;
		this.size = new BigDecimal(size);
        }
        
        
        public MarketDepthLevel(MarketDepthBook.Side side, BigDecimal price, BigDecimal size ) {
		this.side = side;
		this.price = price;
		this.size = size;
	}
	
	/**
	 * @return the price
	 */
	public BigDecimal getPrice() {
		return price;
	}
	/**
	 * @param price the price to set
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	/**
	 * @return the size
	 */
	public BigDecimal getSize() {
		return size;
	}
	/**
	 * @param size the size to set
	 */
	public void setSize(BigDecimal size) {
		this.size = size;
	}
	/**
	 * @return the side
	 */
	public MarketDepthBook.Side getSide() {
		return side;
	}
	/**
	 * @param side the side to set
	 */
	public void setSide(MarketDepthBook.Side side) {
		this.side = side;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(price.doubleValue());
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((side == null) ? 0 : side.hashCode());
		result = prime * result + size.intValue();
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
		MarketDepthLevel other = (MarketDepthLevel) obj;
		if (! price.equals(other.price) ) 
			return false;
		if (side == null) {
			if (other.side != null)
				return false;
		} else if (!side.equals(other.side))
			return false;
		if ( !(size.equals(other.size) ) )
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MarketDepthLevel [side=" + side + " price=" + price + ", size=" + size + "]";
	}

    public int compareTo(MarketDepthLevel otherLevel ) {
        if( side == MarketDepthBook.Side.ASK ) {
            return price.compareTo( otherLevel.getPrice() );
        } else {
            return otherLevel.getPrice().compareTo( price );
        }
    }
	
}
