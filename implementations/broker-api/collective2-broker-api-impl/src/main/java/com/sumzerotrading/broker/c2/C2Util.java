/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sumzerotrading.broker.c2;

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
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author RobTerpilowski
 */
public class C2Util {

    protected static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddkkmm");
    public static final String ROOT_SYMBOL_RUSSEL2K_NYBOT = "@TFS";
    public static final String ROOT_SYMBOL_RUSSEL2K_GLOBEX = "@RTY";
    public static final String ROOT_SYMBOL_NASDAQ_100 = "@NQ";
    public static final String ROOT_SYMBOL_MINI_DOW = "@YM";
    public static final String ROOT_SYMBOL_SP500 = "@ES";
    public static final String ROOT_SYMBOL_CORN = "@C";

    
    public static String getFuturesSymbol(FuturesTicker ticker) {
        String c2Symbol = C2Util.getC2RootSymbol(ticker.getSymbol());
        String symbol = FuturesUtil.getFullFuturesSymbolWithOneDigitYear(c2Symbol, ticker.getExpiryMonth(), ticker.getExpiryYear());
        return symbol;
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
        if( order.getType() == TradeOrder.Type.MARKET || order.getType() == TradeOrder.Type.MARKET_ON_OPEN ) {
            return OrderType.MARKET;
        } else if( order.getType() == TradeOrder.Type.LIMIT ) {
            return OrderType.LIMIT;
        } else if( order.getType() == TradeOrder.Type.STOP ) {
            return OrderType.STOP;
        } else {
            throw new SumZeroException("Unsupported order type: " + order.getType());
        }
    }
    
    
    public static String getC2RootSymbol(String symbol) {
        switch(symbol) {
            case "NQ":
                return ROOT_SYMBOL_NASDAQ_100;
            case "ES":
                return ROOT_SYMBOL_SP500;
            case "TF":
                return ROOT_SYMBOL_RUSSEL2K_NYBOT;
            case "RTY":
                return ROOT_SYMBOL_RUSSEL2K_GLOBEX;
            case "YM":
                return ROOT_SYMBOL_MINI_DOW;
            case "C":
            case "ZC":
                return ROOT_SYMBOL_CORN;
            default:
                throw new SumZeroException("Unsupported C2 symbol: " + symbol );
        }
    }
    
    
    public static String getTime( ZonedDateTime zonedTime ) {
        ZonedDateTime newYorkTime = zonedTime.withZoneSameInstant(ZoneId.of("America/New_York"));
        return formatter.format(newYorkTime);
    }
    
    
    
}
