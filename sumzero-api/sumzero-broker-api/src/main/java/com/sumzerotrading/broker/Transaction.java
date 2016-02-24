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

package com.sumzerotrading.broker;

import com.sumzerotrading.broker.order.TradeDirection;
import com.sumzerotrading.data.Ticker;
import java.io.Serializable;
import java.time.ZonedDateTime;



public class Transaction implements Serializable {

	
	protected Ticker ticker;
	protected Double transactionPrice = 0d;
	protected Double commission = 0d;
	protected ZonedDateTime transactionDate = ZonedDateTime.now();
	protected TradeDirection tradeDirection = TradeDirection.BUY;
	protected int transactionSize = 0;
	protected String positionId = "";
	
	
	public Ticker getTicker() {
		return ticker;
	}
	public void setTicker(Ticker ticker) {
		this.ticker = ticker;
	}
	public Double getTransactionPrice() {
		return transactionPrice;
	}
	public void setTransactionPrice(Double transactionPrice) {
		this.transactionPrice = transactionPrice;
	}
	public Double getCommission() {
		return commission;
	}
	public void setCommission(Double commission) {
		this.commission = commission;
	}
	public ZonedDateTime getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(ZonedDateTime transactionDate) {
		this.transactionDate = transactionDate;
	}
	public TradeDirection getTradeDirection() {
		return tradeDirection;
	}
	public void setTradeDirection(TradeDirection action) {
		this.tradeDirection = action;
	}
	public int getTransactionSize() {
		return transactionSize;
	}
	public void setTransactionSize(int transactionSize) {
		this.transactionSize = transactionSize;
	}
	public String getPositionId() {
		return positionId;
	}
	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}
	
	@Override
	public int hashCode() {
		int hashCode = 7;
		hashCode += commission.intValue();
		hashCode += positionId.hashCode();
		hashCode += ticker.hashCode();
		hashCode += tradeDirection.hashCode();
		hashCode += transactionDate.hashCode();
		hashCode += transactionPrice.hashCode();
		hashCode += transactionSize;
		return hashCode;
	}
	
	@Override
	public boolean equals( Object otherObject ) {
		if( ! (otherObject instanceof Transaction) ) {
			return false;
		}
		Transaction otherTrans = (Transaction) otherObject;
		return this.commission.equals(otherTrans.commission) &&
				this.positionId.equals(otherTrans.positionId) &&
				this.ticker.equals( otherTrans.ticker) &&
				this.tradeDirection.equals(otherTrans.tradeDirection) &&
				this.transactionDate.equals(otherTrans.transactionDate) &&
				this.transactionPrice.equals(otherTrans.transactionPrice) &&
				this.transactionSize == otherTrans.transactionSize;
		
	}
	
	
}
