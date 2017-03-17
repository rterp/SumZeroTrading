/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sumzerotrading.broker.ib;

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
        signalInfo.setIsMarketOrder( order.getType() == TradeOrder.Type.MARKET );
        signalInfo.setSymbolType(C2Util.getSymbolType(order));
        signalInfo.setOrderType(C2Util.getOrderType(order));
        signalInfo.setQuantity(order.getSize());
        
        SubmitSignalRequest request = new SubmitSignalRequest(systemId, signalInfo);
        return request;
    }
}
