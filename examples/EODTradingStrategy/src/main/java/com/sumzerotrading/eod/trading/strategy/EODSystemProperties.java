package com.sumzerotrading.eod.trading.strategy;


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
    
    
    


    protected void parseProps( Properties props ) {
        twsHost = props.getProperty("tws.host");
        twsPort = Integer.parseInt(props.getProperty("tws.port"));
        twsClientId = Integer.parseInt(props.getProperty("tws.client.id"));
        tradeSizeDollars = Integer.parseInt(props.getProperty("trade.size.dollars"));
        startTime = LocalTime.parse(props.getProperty("start.time"));
        marketCloseTime = LocalTime.parse(props.getProperty("market.close.time"));
        strategyDirectory = props.getProperty("strategy.directory");
        longShortTickerMap = getLongShortTickers(props);
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
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.startTime);
        hash = 37 * hash + Objects.hashCode(this.marketCloseTime);
        hash = 37 * hash + Objects.hashCode(this.twsHost);
        hash = 37 * hash + this.twsPort;
        hash = 37 * hash + this.twsClientId;
        hash = 37 * hash + this.tradeSizeDollars;
        hash = 37 * hash + Objects.hashCode(this.longShortTickerMap);
        hash = 37 * hash + Objects.hashCode(this.strategyDirectory);
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
        return true;
    }

    @Override
    public String toString() {
        return "EODSystemProperties{" + "startTime=" + startTime + ", marketCloseTime=" + marketCloseTime + ", twsHost=" + twsHost + ", twsPort=" + twsPort + ", twsClientId=" + twsClientId + ", tradeSizeDollars=" + tradeSizeDollars + ", longShortTickerMap=" + longShortTickerMap + ", strategyDirectory=" + strategyDirectory + '}';
    }


        
    
}
