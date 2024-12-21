/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sumzerotrading.marketdata.hyperliquid;

import java.math.BigDecimal;
import java.net.URI;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

// import org.eclipse.jetty.websocket.client.WebSocketClient;
// import com.sumzerotrading.bitmex.client.BitmexRestClient;
// import com.sumzerotrading.bitmex.client.BitmexWebsocketClient;
// import com.sumzerotrading.bitmex.common.api.BitmexClientRegistry;
// import com.sumzerotrading.bitmex.entity.BitmexQuote;
// import com.sumzerotrading.bitmex.entity.BitmexTrade;
// import com.sumzerotrading.bitmex.listener.IQuoteListener;
// import com.sumzerotrading.bitmex.listener.ITradeListener;
// import com.sumzerotrading.data.Ticker;
// import com.sumzerotrading.marketdata.Level1Quote;
// import com.sumzerotrading.marketdata.Level1QuoteListener;
// import com.sumzerotrading.marketdata.QuoteEngine;
//import com.sumzerotrading.marketdata.QuoteType;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

/**
 *
 * @author RobTerpilowski
 */
public class WebSocketTestDyDxLevel1QuoteEngine extends WebSocketClient { // extends QuoteEngine implements
                                                                          // IQuoteListener, ITradeListener {

    public WebSocketTestDyDxLevel1QuoteEngine(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        System.out.println("Connected to dYdX WebSocket.");
        // Subscribe to market data
        String subscriptionMessage = """
                    {
                        "type": "subscribe",
                        "channel": "v4_orderbook",
                        "id": "XRP-USD"
                    }
                """;
        send(subscriptionMessage);
    }

    @Override
    public void onMessage(String message) {
        System.out.println("Received message: " + message);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("Connection closed: " + reason);
    }

    @Override
    public void onError(Exception ex) {
        ex.printStackTrace();
    }

    public static void main(String[] args) {
        try {
            URI uri = new URI("wss://indexer.dydx.trade/v4/ws");
            WebSocketTestDyDxLevel1QuoteEngine client = new WebSocketTestDyDxLevel1QuoteEngine(uri);
            client.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}