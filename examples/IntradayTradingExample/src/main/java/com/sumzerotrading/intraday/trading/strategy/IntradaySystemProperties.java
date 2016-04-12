package com.sumzerotrading.intraday.trading.strategy;


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
public class IntradaySystemProperties {
    

    protected LocalTime startTime;
    protected LocalTime marketCloseTime;
    protected String twsHost;
    protected int twsPort;
    protected int twsClientId;
    protected int tradeSizeDollars;
    protected String strategyDirectory;
    protected int exitSeconds = 0;
    
    
    //For Unit tests
    protected IntradaySystemProperties() {
        
    }
    
    public IntradaySystemProperties(String fileName) throws IOException {
        this( new FileInputStream(fileName) );
        
    }
    
    public IntradaySystemProperties(InputStream inputStream) throws IOException {
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


    public LocalTime getMarketCloseTime() {
        return marketCloseTime;
    }

    public String getStrategyDirectory() {
        return strategyDirectory;
    }


    public int getExitSeconds() {
        return exitSeconds;
    }
    
    


    protected void parseProps( Properties props ) {
        twsHost = props.getProperty("tws.host");
        twsPort = Integer.parseInt(props.getProperty("tws.port"));
        twsClientId = Integer.parseInt(props.getProperty("tws.client.id"));
        tradeSizeDollars = Integer.parseInt(props.getProperty("trade.size.dollars"));
        startTime = LocalTime.parse(props.getProperty("start.time"));
        marketCloseTime = LocalTime.parse(props.getProperty("market.close.time"));
        strategyDirectory = props.getProperty("strategy.directory");
        exitSeconds = Integer.parseInt(props.getProperty("exit.seconds", "0"));
    }
    

    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.startTime);
        hash = 83 * hash + Objects.hashCode(this.marketCloseTime);
        hash = 83 * hash + Objects.hashCode(this.twsHost);
        hash = 83 * hash + this.twsPort;
        hash = 83 * hash + this.twsClientId;
        hash = 83 * hash + this.tradeSizeDollars;
        hash = 83 * hash + Objects.hashCode(this.strategyDirectory);
        hash = 83 * hash + this.exitSeconds;
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
        final IntradaySystemProperties other = (IntradaySystemProperties) obj;
        if (this.twsPort != other.twsPort) {
            return false;
        }
        if (this.twsClientId != other.twsClientId) {
            return false;
        }
        if (this.tradeSizeDollars != other.tradeSizeDollars) {
            return false;
        }
        if (this.exitSeconds != other.exitSeconds) {
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
        return true;
    }

    @Override
    public String toString() {
        return "IntradaySystemProperties{" + "startTime=" + startTime + ", marketCloseTime=" + marketCloseTime + ", twsHost=" + twsHost + ", twsPort=" + twsPort + ", twsClientId=" + twsClientId + ", tradeSizeDollars=" + tradeSizeDollars + ", strategyDirectory=" + strategyDirectory + ", exitSeconds=" + exitSeconds + '}';
    }

    
    
    
}
