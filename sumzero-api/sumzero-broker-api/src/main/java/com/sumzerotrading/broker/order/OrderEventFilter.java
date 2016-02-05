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


package com.sumzerotrading.broker.order;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Rob Terpilowski
 */
public class OrderEventFilter implements Serializable {

    protected Set<String> orderIds = new HashSet<String>();
    protected boolean filterOrders;
    
    
    public OrderEventFilter() {
        this(true);
    } 
    
    public OrderEventFilter( boolean filterOrders ) {
        this.filterOrders = filterOrders;
    }
    
    
    public boolean filterOrders() {
        return filterOrders;
    }
    
    
    public void addOrderId( String orderId ) {
        orderIds.add(orderId);
    }
    
    
    public void removeOrderId( String orderId ) {
        orderIds.remove(orderId);
    }
    
    
    public boolean matches( String orderId ) {
        return (!filterOrders) || orderIds.contains(orderId);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + (this.orderIds != null ? this.orderIds.hashCode() : 0);
        hash = 53 * hash + (this.filterOrders ? 1 : 0);
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
        final OrderEventFilter other = (OrderEventFilter) obj;
        if (this.orderIds != other.orderIds && (this.orderIds == null || !this.orderIds.equals(other.orderIds))) {
            return false;
        }
        if (this.filterOrders != other.filterOrders) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "OrderEventFilter{" + "orderIds=" + orderIds + ", filterOrders=" + filterOrders + '}';
    }
    
    
    
}
