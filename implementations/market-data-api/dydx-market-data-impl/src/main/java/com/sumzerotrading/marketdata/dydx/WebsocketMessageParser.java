package com.sumzerotrading.marketdata.dydx;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebsocketMessageParser {

    protected static Logger logger = LoggerFactory.getLogger(WebsocketMessageParser.class);

    protected WebsocketEventListener listener;
    protected boolean connected = false;
    protected ThreadPoolExecutor threadPool = (ThreadPoolExecutor) Executors.newCachedThreadPool();
    protected List<String> subscribedTickers = new ArrayList<>();

    public WebsocketMessageParser(WebsocketEventListener listener) {
        this.listener = listener;
    }

    public void messageReceived(String message) {
        try {
            JSONObject json = new JSONObject(message);

            // Extract common fields
            String type = json.getString("type");
            String connectionId = json.getString("connection_id");
            int messageId = json.getInt("message_id");
            // String channelId = json.getString("id");
            // String channel = json.getString("channel");

            logger.debug("Type: " + type);
            logger.debug("Connection ID: " + connectionId);
            logger.debug("Message ID: " + messageId);
            // logger.debug("Channel: " + channel);

            if ("subscribed".equalsIgnoreCase(type)) {
                handleInitialOrderBook(json.getJSONObject("contents"));
            }

            if ("connected".equalsIgnoreCase(type)) {
                connected = true;
                handleIsConnected();
            }

            // Extract the contents field
            if ("channel_data".equalsIgnoreCase(type)) {
                JSONObject contents = json.getJSONObject("contents");
                handleOrderBookUpdate(contents);
            }

        } catch (Exception e) {
            logger.error("Failed to process message: " + message);
            logger.error(e.getMessage(), e);

        }
    }

    protected void handleIsConnected() {
        threadPool.submit(() -> {
            listener.isConnected(connected);
        });
    }

    public void handleInitialOrderBook(JSONObject orderBookArray) {
        try {
            OrderBook orderBook = new OrderBook();
            JSONArray bidsArray = orderBookArray.getJSONArray("bids");
            JSONArray asksArray = orderBookArray.getJSONArray("asks");

            for (int i = 0; i < bidsArray.length(); i++) {
                JSONObject bid = bidsArray.getJSONObject(i);
                String price = bid.getString("price");
                String size = bid.getString("size");
                orderBook.updateBid(price, size);
            }

            // Populate asks TreeMap
            for (int i = 0; i < asksArray.length(); i++) {
                JSONObject ask = asksArray.getJSONObject(i);
                String price = ask.getString("price");
                String size = ask.getString("size");
                orderBook.updateAsk(price, size);
            }

            listener.initialBook("", orderBook);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    protected void handleOrderBookUpdate(JSONObject orderbookArray) {
        try {

            JSONArray bidsArray = orderbookArray.getJSONArray("bids");
            JSONArray asksArray = orderbookArray.getJSONArray("asks");

            for (int i = 0; i < bidsArray.length(); i++) {
                JSONObject bid = bidsArray.getJSONObject(i);
                String price = bid.getString("price");
                String size = bid.getString("size");
                threadPool.submit(() -> {
                    listener.updateBid("", price, size);
                });

            }

            // Populate asks TreeMap
            for (int i = 0; i < asksArray.length(); i++) {
                JSONObject ask = asksArray.getJSONObject(i);
                String price = ask.getString("price");
                String size = ask.getString("size");
                threadPool.submit(() -> {
                    listener.updateAsk("", price, size);
                });

            }

        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

}
