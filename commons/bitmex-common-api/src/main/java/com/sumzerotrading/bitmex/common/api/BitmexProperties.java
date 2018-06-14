/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sumzerotrading.bitmex.common.api;

import com.sumzerotrading.data.SumZeroException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author RobTerpilowski
 */
public class BitmexProperties {

    protected String apiKeyName;
    protected String apiKeyValue;
    protected boolean useProduction;

    public String getApiKeyName() {
        return apiKeyName;
    }

    public void setApiKeyName(String apiKeyName) {
        this.apiKeyName = apiKeyName;
    }

    public String getApiKeyValue() {
        return apiKeyValue;
    }

    public void setApiKeyValue(String apiKeyValue) {
        this.apiKeyValue = apiKeyValue;
    }

    public boolean isUseProduction() {
        return useProduction;
    }

    public void setUseProduction(boolean useProduction) {
        this.useProduction = useProduction;
    }

    public void readProperties(Properties properties) {
        useProduction = Boolean.parseBoolean(properties.getProperty(BitmexPropKey.USE_PRODUCTION));
        apiKeyName = properties.getProperty(BitmexPropKey.API_KEY_NAME, "");
        apiKeyValue = properties.getProperty(BitmexPropKey.API_KEY_VALUE, "");
    }
    
    
    public void readProperties(String filename) {
        try {
            readProperties( new FileInputStream( filename ) );
        } catch (FileNotFoundException ex) {
            throw new SumZeroException(ex);
        }
    }
    
    public void readProperties(InputStream input) {
        Properties properties = new Properties();
        try {
            properties.load(input);
            readProperties(properties);
        } catch (IOException ex) {
            throw new SumZeroException(ex);
        }
    }

}
