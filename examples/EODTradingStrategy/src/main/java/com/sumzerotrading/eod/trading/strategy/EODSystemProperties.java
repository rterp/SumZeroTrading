package com.sumzerotrading.eod.trading.strategy;


import com.sumzerotrading.broker.order.TradeOrder;
import com.sumzerotrading.broker.order.TradeOrder.Type;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author RobTerpilowski
 */
public class EODSystemProperties {
    

    protected LocalTime startTime;
    protected LocalTime marketCloseTime;
    protected String twsHost;
    protected int twsPort;
    protected int twsClientId;
    protected int tradeSizeDollars;
    protected Map<String,String> longShortTickerMap = new HashMap<>();
    protected String strategyDirectory;
    protected TradeOrder.Type orderType;
    
    
    //For Unit tests
    protected EODSystemProperties() {
        
    }
    
    public EODSystemProperties(String fileName) throws IOException {
        this( new FileInputStream(fileName) );
        
    }
    
    public EODSystemProperties(InputStream inputStream) throws IOException {
        Properties props = new Properties();
        props.load(inputStream);
        inputStream.close();
        parseProps(props);
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public String getTwsHost() {
        return twsHost;
    }

    public int getTwsPort() {
        return twsPort;
    }

    public int getTwsClientId() {
        return twsClientId;
    }

    public int getTradeSizeDollars() {
        return tradeSizeDollars;
    }

    public Map<String, String> getLongShortTickerMap() {
        return longShortTickerMap;
    }

    public LocalTime getMarketCloseTime() {
        return marketCloseTime;
    }

    public String getStrategyDirectory() {
        return strategyDirectory;
    }

    public TradeOrder.Type getOrderType() {
        return orderType;
    }


    protected void parseProps( Properties props ) {
        twsHost = props.getProperty("tws.host");
        twsPort = Integer.parseInt(props.getProperty("tws.port"));
        twsClientId = Integer.parseInt(props.getProperty("tws.client.id"));
        tradeSizeDollars = Integer.parseInt(props.getProperty("trade.size.dollars"));
        startTime = LocalTime.parse(props.getProperty("start.time"));
        marketCloseTime = LocalTime.parse(props.getProperty("market.close.time"));
        strategyDirectory = props.getProperty("strategy.directory");
        orderType = getOrderType(props.getProperty("order.type", "MOC"));
        longShortTickerMap = getLongShortTickers(props);
    }
    
    protected TradeOrder.Type getOrderType( String orderType ) {
        if( "MKT".equalsIgnoreCase(orderType) ) {
            return Type.MARKET;
        } else if( "MOC".equalsIgnoreCase(orderType) ) {
            return Type.MARKET_ON_CLOSE;
        } else {
            throw new IllegalStateException( "Unknown order.type: " + orderType );
        }
    }

    
    protected Map<String,String> getLongShortTickers( Properties props ) {
        Map<String,String> map = new HashMap<>();
        for( Object key : props.keySet()) {
            String keyString = (String) key;
            if( keyString.startsWith("pair") ) {
                String[] tickers = props.getProperty(keyString).split(":");
                map.put(tickers[0], tickers[1]);
            }
        }
        
        return map;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.startTime);
        hash = 29 * hash + Objects.hashCode(this.marketCloseTime);
        hash = 29 * hash + Objects.hashCode(this.twsHost);
        hash = 29 * hash + this.twsPort;
        hash = 29 * hash + this.twsClientId;
        hash = 29 * hash + this.tradeSizeDollars;
        hash = 29 * hash + Objects.hashCode(this.longShortTickerMap);
        hash = 29 * hash + Objects.hashCode(this.strategyDirectory);
        hash = 29 * hash + Objects.hashCode(this.orderType);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final EODSystemProperties other = (EODSystemProperties) obj;
        if (this.twsPort != other.twsPort) {
            return false;
        }
        if (this.twsClientId != other.twsClientId) {
            return false;
        }
        if (this.tradeSizeDollars != other.tradeSizeDollars) {
            return false;
        }
        if (!Objects.equals(this.twsHost, other.twsHost)) {
            return false;
        }
        if (!Objects.equals(this.strategyDirectory, other.strategyDirectory)) {
            return false;
        }
        if (!Objects.equals(this.startTime, other.startTime)) {
            return false;
        }
        if (!Objects.equals(this.marketCloseTime, other.marketCloseTime)) {
            return false;
        }
        if (!Objects.equals(this.longShortTickerMap, other.longShortTickerMap)) {
            return false;
        }
        if (this.orderType != other.orderType) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "EODSystemProperties{" + "startTime=" + startTime + ", marketCloseTime=" + marketCloseTime + ", twsHost=" + twsHost + ", twsPort=" + twsPort + ", twsClientId=" + twsClientId + ", tradeSizeDollars=" + tradeSizeDollars + ", longShortTickerMap=" + longShortTickerMap + ", strategyDirectory=" + strategyDirectory + ", orderType=" + orderType + '}';
    }



        
    
}
