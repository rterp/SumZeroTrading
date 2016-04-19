package com.sumzerotrading.intraday.trading.strategy;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalTime;
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
    

    protected String twsHost;
    protected int twsPort;
    protected int twsClientId;
    protected int tradeSizeDollars;
    protected LocalTime systemStartTime;
    protected LocalTime longStartTime;
    protected LocalTime longStopTime;
    protected LocalTime longExitTime;
    protected LocalTime shortStartTime;
    protected LocalTime shortStopTime;
    protected LocalTime shortExitTime;
    protected String ticker;
    protected String strategyDirectory;
    
    
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



    protected void parseProps( Properties props ) {
        twsHost = props.getProperty("tws.host");
        twsPort = Integer.parseInt(props.getProperty("tws.port"));
        twsClientId = Integer.parseInt(props.getProperty("tws.client.id"));
        tradeSizeDollars = Integer.parseInt(props.getProperty("trade.size.dollars"));
        systemStartTime = LocalTime.parse(props.getProperty("system.start.time"));
        ticker = props.getProperty("ticker");
        longStartTime = LocalTime.parse(props.getProperty("long.start.time"));
        longStopTime = LocalTime.parse(props.getProperty("long.stop.time"));
        longExitTime = LocalTime.parse(props.getProperty("long.exit.time"));
        shortStartTime = LocalTime.parse(props.getProperty("short.start.time"));
        shortStopTime = LocalTime.parse(props.getProperty("short.stop.time"));
        shortExitTime = LocalTime.parse(props.getProperty("short.exit.time"));
        strategyDirectory = props.getProperty("strategy.dir");

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

    public LocalTime getSystemStartTime() {
        return systemStartTime;
    }

    public LocalTime getLongStartTime() {
        return longStartTime;
    }

    public LocalTime getLongStopTime() {
        return longStopTime;
    }

    public LocalTime getLongExitTime() {
        return longExitTime;
    }

    public LocalTime getShortStartTime() {
        return shortStartTime;
    }

    public LocalTime getShortStopTime() {
        return shortStopTime;
    }

    public LocalTime getShortExitTime() {
        return shortExitTime;
    }

    public String getTicker() {
        return ticker;
    }

    public String getStrategyDirectory() {
        return strategyDirectory;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.twsHost);
        hash = 37 * hash + this.twsPort;
        hash = 37 * hash + this.twsClientId;
        hash = 37 * hash + this.tradeSizeDollars;
        hash = 37 * hash + Objects.hashCode(this.systemStartTime);
        hash = 37 * hash + Objects.hashCode(this.longStartTime);
        hash = 37 * hash + Objects.hashCode(this.longStopTime);
        hash = 37 * hash + Objects.hashCode(this.longExitTime);
        hash = 37 * hash + Objects.hashCode(this.shortStartTime);
        hash = 37 * hash + Objects.hashCode(this.shortStopTime);
        hash = 37 * hash + Objects.hashCode(this.shortExitTime);
        hash = 37 * hash + Objects.hashCode(this.ticker);
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
        if (!Objects.equals(this.twsHost, other.twsHost)) {
            return false;
        }
        if (!Objects.equals(this.ticker, other.ticker)) {
            return false;
        }
        if (!Objects.equals(this.strategyDirectory, other.strategyDirectory)) {
            return false;
        }
        if (!Objects.equals(this.systemStartTime, other.systemStartTime)) {
            return false;
        }
        if (!Objects.equals(this.longStartTime, other.longStartTime)) {
            return false;
        }
        if (!Objects.equals(this.longStopTime, other.longStopTime)) {
            return false;
        }
        if (!Objects.equals(this.longExitTime, other.longExitTime)) {
            return false;
        }
        if (!Objects.equals(this.shortStartTime, other.shortStartTime)) {
            return false;
        }
        if (!Objects.equals(this.shortStopTime, other.shortStopTime)) {
            return false;
        }
        if (!Objects.equals(this.shortExitTime, other.shortExitTime)) {
            return false;
        }
        return true;
    }



}


