/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sumzerotrading.broker.c2;

import com.sumzerotrading.broker.order.TradeOrder;
import com.sumzerotrading.data.FuturesTicker;
import com.sumzerotrading.data.Ticker;
import com.sumzerotrading.j4c2.signal.SignalInfo;
import com.sumzerotrading.j4c2.signal.SubmitSignalRequest;

/**
 *
 * @author RobTerpilowski
 */
public class TradeSignalBuilder {



    public SubmitSignalRequest buildSignalRequest( String systemId, TradeOrder order ) {
        Ticker ticker = order.getTicker();
        String symbol;
        if( ticker instanceof FuturesTicker ) {
            symbol = C2Util.getFuturesSymbol((FuturesTicker)ticker);
        } else {
            symbol = ticker.getSymbol();
        }
        
        SignalInfo signalInfo = new SignalInfo();
        signalInfo.setTicker(symbol);
        signalInfo.setAction(C2Util.getTradeAction(order));
        signalInfo.setIsMarketOrder( (order.getType() == TradeOrder.Type.MARKET || order.getType() == TradeOrder.Type.MARKET_ON_OPEN ));
        signalInfo.setSymbolType(C2Util.getSymbolType(order));
        signalInfo.setOrderType(C2Util.getOrderType(order));
        signalInfo.setQuantity(order.getSize());
        signalInfo.setDuration( getDuration(order.getDuration() ));
        if( order.getGoodAfterTime() != null ) {
            signalInfo.setParkUntil(C2Util.getTime(order.getGoodAfterTime()));
        }
        
        if( order.getType() == TradeOrder.Type.STOP ) {
            signalInfo.setStopPrice(order.getStopPrice());
        } else if( order.getType() == TradeOrder.Type.LIMIT ) { 
            signalInfo.setLimitPrice(order.getLimitPrice() );
        }
        
        SubmitSignalRequest request = new SubmitSignalRequest(systemId, signalInfo);
        return request;
    }
    
    
    protected SignalInfo.Duration getDuration( TradeOrder.Duration tradeDuration ) {
        if( tradeDuration == TradeOrder.Duration.DAY ) {
            return SignalInfo.Duration.DAY;
        } else if( tradeDuration == TradeOrder.Duration.GOOD_UNTIL_CANCELED ) {
            return SignalInfo.Duration.GTC;
        } else {
            throw new IllegalStateException("Unknown duration: " + tradeDuration );
        }
    }
}
