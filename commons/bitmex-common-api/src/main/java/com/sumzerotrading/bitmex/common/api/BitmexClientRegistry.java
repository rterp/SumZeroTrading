/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sumzerotrading.bitmex.common.api;

import com.sumzerotrading.bitmex.client.BitmexClient;
import com.sumzerotrading.bitmex.client.BitmexRestClient;
import com.sumzerotrading.bitmex.client.BitmexWebsocketClient;
import com.sumzerotrading.bitmex.client.IBitmexClient;

/**
 *
 * @author RobTerpilowski
 */

public class BitmexClientRegistry {

    protected static BitmexClientRegistry registry = null;
    protected BitmexRestClient restClient;
    protected BitmexWebsocketClient websocketClient;
    protected IBitmexClient bitmexClient;
    protected boolean useProduction = false;
    protected String apiKeyName = "";
    protected String apiKeyValue = "";
    
    
    protected BitmexClientRegistry() {}
    
    
    public static BitmexClientRegistry getInstance() {
        if( registry == null ) {
            registry = new BitmexClientRegistry();
        }
        
        return registry;
    }
    
    public static void setTestClientRegistry( BitmexClientRegistry testRegistry ) {
        registry = testRegistry;
    }
    
    
    public void setProperties( BitmexProperties properties ) {
        useProduction = properties.isUseProduction();
        apiKeyName = properties.getApiKeyName();
        apiKeyValue = properties.getApiKeyValue();
    }
    
    
    public IBitmexClient getBitmexClient() {
        if( bitmexClient == null ) {
            bitmexClient = new BitmexClient(useProduction, apiKeyName, apiKeyValue);
        }
        return bitmexClient;
    }
    
    public BitmexRestClient getRestClient() {
        if( restClient == null ) {
            restClient = new BitmexRestClient(useProduction, apiKeyName, apiKeyValue);
        }
        return restClient;
    }
    
    public BitmexWebsocketClient getWebsocketClient() {
        if( websocketClient == null ) {
            websocketClient = new BitmexWebsocketClient(useProduction);
            websocketClient.connect(apiKeyName, apiKeyValue);
        }
        return websocketClient;
    }
    
}
