package com.sumzerotrading.marketdata.dydx;

import java.math.BigDecimal;
import java.util.Map;
import java.util.TreeMap;

public class OrderBook {
    private final TreeMap<BigDecimal, String> bids; // Price -> Size
    private final TreeMap<BigDecimal, String> asks; // Price -> Size

    public OrderBook() {
        // Bids: Highest price first
        bids = new TreeMap<>((a, b) -> b.compareTo(a));

        // Asks: Lowest price first
        asks = new TreeMap<>();
    }

    // Add or update a bid
    public void updateBid(String price, String size) {
        BigDecimal priceKey = new BigDecimal(price);
        if (size.equals("0")) {
            bids.remove(priceKey); // Remove if size is zero
        } else {
            bids.put(priceKey, size);
        }
    }

    // Add or update an ask
    public void updateAsk(String price, String size) {
        BigDecimal priceKey = new BigDecimal(price);
        if (size.equals("0")) {
            asks.remove(priceKey); // Remove if size is zero
        } else {
            asks.put(priceKey, size);
        }
    }

    // Get best bid (highest price)
    public Map.Entry<BigDecimal, String> getBestBid() {
        return bids.firstEntry();
    }

    // Get best ask (lowest price)
    public Map.Entry<BigDecimal, String> getBestAsk() {
        return asks.firstEntry();
    }

    // Debug print for the entire order book
    public void printOrderBook() {
        System.out.println("Bids:");
        bids.forEach((price, size) -> System.out.println(price + " -> " + size));
        System.out.println("Asks:");
        asks.forEach((price, size) -> System.out.println(price + " -> " + size));
    }
}
