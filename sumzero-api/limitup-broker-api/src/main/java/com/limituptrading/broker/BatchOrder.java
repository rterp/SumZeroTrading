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


package com.limituptrading.broker;

import java.io.Serializable;
import java.util.*;


/**
 * @author Rob Terpilowski
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class BatchOrder implements Serializable {

	private ArrayList idList = new ArrayList();
	private ArrayList orderList = new ArrayList();
	private ArrayList tickerList = new ArrayList();
	
	
	
	public void addOrder( int id, String ticker, Order order  ) {
		idList.add( new Integer( id ) );
		tickerList.add( ticker );
		orderList.add( order );
	}
	
	public int getId( int index ) {
		return ( (Integer) idList.get( index ) ).intValue();
	}
	
	public String getTicker( int index ) {
		return (String) tickerList.get( index );
	}
	
	public Order getOrder( int index ) {
		return (Order) orderList.get( index );
	}
	
	public int size() {
		return orderList.size();
	}

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + (this.idList != null ? this.idList.hashCode() : 0);
        hash = 59 * hash + (this.orderList != null ? this.orderList.hashCode() : 0);
        hash = 59 * hash + (this.tickerList != null ? this.tickerList.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BatchOrder other = (BatchOrder) obj;
        if (this.idList != other.idList && (this.idList == null || !this.idList.equals(other.idList))) {
            return false;
        }
        if (this.orderList != other.orderList && (this.orderList == null || !this.orderList.equals(other.orderList))) {
            return false;
        }
        if (this.tickerList != other.tickerList && (this.tickerList == null || !this.tickerList.equals(other.tickerList))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "BatchOrder{" + "idList=" + idList + ", orderList=" + orderList + ", tickerList=" + tickerList + '}';
    }
        
        
    
	
}
