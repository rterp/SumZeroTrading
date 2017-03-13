/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sumzerotrading.broker.ib;

import com.sumzerotrading.broker.order.TradeDirection;
import com.sumzerotrading.broker.order.TradeOrder;
import com.sumzerotrading.data.CurrencyTicker;
import com.sumzerotrading.data.FuturesTicker;
import com.sumzerotrading.data.StockTicker;
import com.sumzerotrading.data.SumZeroException;
import com.sumzerotrading.data.Ticker;
import com.sumzerotrading.j4c2.signal.SignalInfo.Action;
import com.sumzerotrading.j4c2.signal.SignalInfo.OrderType;
import com.sumzerotrading.j4c2.signal.SignalInfo.SymbolType;
import com.sumzerotrading.util.FuturesUtil;

/**
 *
 * @author RobTerpilowski
 */
public class C2Util {


    public static String getFuturesSymbol(FuturesTicker ticker) {
        String symbol = FuturesUtil.getFullFuturesSymbolWithTwoDigitYear(ticker.getSymbol(), ticker.getExpiryMonth(), ticker.getExpiryYear());
        return "@" + symbol;
    }
    
    
    public static Action getTradeAction( TradeOrder order ) {
        if( order.getTradeDirection() == TradeDirection.BUY ) {
            return Action.BTO;
        } else if( order.getTradeDirection() == TradeDirection.SELL ) {
            return Action.STC;
        } else if( order.getTradeDirection() == TradeDirection.BUY_TO_COVER ) {
            return Action.BTC;
        } else if( order.getTradeDirection() == TradeDirection.SELL_SHORT ) {
            return Action.STO;
        } else {
            throw new SumZeroException("Unknown trade direction: " + order.getTradeDirection() );
        }
    }
    
    public static SymbolType getSymbolType( TradeOrder order ) {
        Ticker ticker = order.getTicker();
        if( ticker instanceof StockTicker ) {
            return SymbolType.stock;
        } else if( ticker instanceof FuturesTicker ) {
            return SymbolType.future;
        } else if( ticker instanceof CurrencyTicker ) {
            return SymbolType.forex;
        } else {
            throw new SumZeroException("Unsupported symbol type: " + ticker.getClass());
        }
    }
    
    public static OrderType getOrderType( TradeOrder order ) {
        if( order.getType() == TradeOrder.Type.MARKET ) {
            return OrderType.MARKET;
        } else if( order.getType() == TradeOrder.Type.LIMIT ) {
            return OrderType.LIMIT;
        } else if( order.getType() == TradeOrder.Type.STOP ) {
            return OrderType.STOP;
        } else {
            throw new SumZeroException("Unsupported order type: " + order.getType());
        }
    }
}
