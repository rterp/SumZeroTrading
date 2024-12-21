package com.sumzerotrading.marketdata.hyperliquid;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sumzerotrading.data.Ticker;
import com.sumzerotrading.marketdata.Level1Quote;
import com.sumzerotrading.marketdata.Level1QuoteListener;
import com.sumzerotrading.marketdata.QuoteEngine;
import com.sumzerotrading.marketdata.QuoteType;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HyperliquidQuoteEngine extends QuoteEngine implements Runnable {

    public static final String SLEEP_TIME_PROPERTY_KEY = "sleep.time.in.seconds";
    protected volatile boolean started = false;
    protected boolean threadCompleted = false;
    protected Thread thread = new Thread(this);
    private static final String BASE_URL = "https://indexer.dydx.trade/v4/";
    private static final String ORDER_BOOK_URL = BASE_URL + "orderbooks/perpetualMarket/";
    private static final String FUNDING_URL = BASE_URL + "perpetualMarkets/";
    private final OkHttpClient httpClient;
    private final ObjectMapper objectMapper;
    protected int sleepTimeInSeconds = 10;
    protected ArrayList<String> urlStrings = new ArrayList<>();
    private OrderBookResponse orderBook;
    protected MarketsResponse allFundingRates;

    public HyperliquidQuoteEngine() {
        this.httpClient = new OkHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    public OrderBookResponse getOrderBook(String market) {
        String url = ORDER_BOOK_URL + market;
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new RuntimeException("Unexpected response code: " + response.code());
            }
            String jsonResponse = response.body().string();
            return objectMapper.readValue(jsonResponse, OrderBookResponse.class);
        } catch (Exception e) {
            throw new RuntimeException("Error fetching order book for market: " + market, e);
        }
    }

    public static void main(String[] args) {
        HyperliquidQuoteEngine client = new HyperliquidQuoteEngine();
        System.out.println(client.getAllFundingRates());

        // // Fetch order book data for BTC-USD market
        // String market = "BTC-USD";
        // OrderBookResponse orderBook = client.getOrderBook(market);

        // // Print the order book data
        // System.out.println("Order Book for Market: " + market);
        // // System.out.println("Bids: " + Arrays.toString(orderBook.getBids()));
        // // System.out.println("Asks: " + Arrays.toString(orderBook.getAsks()));
        // System.out.println("Best Bid: " + orderBook.getBids()[0]);
        // System.out.println("Best Ask: " + orderBook.getAsks()[0]);
    }

    @Override
    public Date getServerTime() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isConnected() {
        return started;
    }

    @Override
    public void startEngine() {
        if (threadCompleted) {
            throw new IllegalStateException("Quote Engine was already stopped");
        }
        System.out.println("starting engine with " + sleepTimeInSeconds + " second interval");
        started = true;
        thread.start();
    }

    @Override
    public void startEngine(Properties props) {
        String sleepTimeString = props.getProperty(SLEEP_TIME_PROPERTY_KEY);
        if (sleepTimeString != null) {
            sleepTimeInSeconds = Integer.parseInt(sleepTimeString);
        }
        startEngine();
    }

    @Override
    public boolean started() {
        return started;
    }

    @Override
    public void stopEngine() {
        started = false;
    }

    @Override
    public void useDelayedData(boolean useDelayed) {
        System.out.println("Not supported for dYdX market data");
    }

    @Override
    public void subscribeLevel1(Ticker ticker, Level1QuoteListener listener) {
        super.subscribeLevel1(ticker, listener);
    }

    @Override
    public void unsubscribeLevel1(Ticker ticker, Level1QuoteListener listener) {
        urlStrings.remove(BASE_URL + ticker.getSymbol());
        super.unsubscribeLevel1(ticker, listener);
    }

    @Override
    public void run() {
        while (started) {
            System.out.println("Reading quotes");
            getQuotes();
            try {
                Thread.sleep(sleepTimeInSeconds * 1000);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        threadCompleted = true;
    }

    protected void getQuotes() {
        try {
            allFundingRates = getAllFundingRates();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        for (Ticker ticker : level1ListenerMap.keySet()) {
            try {
                if (!level1ListenerMap.get(ticker).isEmpty()) {
                    // System.out.println("Getting ticker: " + ticker);
                    orderBook = getOrderBook(ticker.getSymbol());
                    HashMap<QuoteType, BigDecimal> quoteMap = new HashMap<>();
                    quoteMap.put(QuoteType.ASK, new BigDecimal(orderBook.asks[0].price));
                    quoteMap.put(QuoteType.ASK_SIZE, new BigDecimal(orderBook.asks[0].size));
                    quoteMap.put(QuoteType.BID, new BigDecimal(orderBook.bids[0].price));
                    quoteMap.put(QuoteType.BID_SIZE, new BigDecimal(orderBook.bids[0].size));
                    quoteMap.put(QuoteType.FUNDING_RATE,
                            allFundingRates.getMarkets().get(ticker.getSymbol()).getAnnualizedFundingRate());
                    Level1Quote quote = new Level1Quote(ticker, ZonedDateTime.now(), quoteMap);
                    fireLevel1Quote(quote);
                }
            } catch (Exception ex) {
                System.err
                        .println("DyDxLevel1 Quote Engine Caught an exception, but will continue:  " + ex.getMessage());
                ex.printStackTrace();
            }
        }

    }

    public MarketsResponse getAllFundingRates() {
        Request request = new Request.Builder()
                .url(FUNDING_URL)
                .get()
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new RuntimeException("Unexpected response code: " + response.code());
            }
            String jsonResponse = response.body().string();
            return objectMapper.readValue(jsonResponse, MarketsResponse.class); // Parse JSON
        } catch (Exception e) {
            throw new RuntimeException("Error fetching funding rates", e);
        }
    }

    // Classes for mapping JSON response
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class OrderBookResponse {
        private Order[] bids;
        private Order[] asks;

        public Order[] getBids() {
            return bids;
        }

        public void setBids(Order[] bids) {
            this.bids = bids;
        }

        public Order[] getAsks() {
            return asks;
        }

        public void setAsks(Order[] asks) {
            this.asks = asks;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class Order {
        private String price;
        private String size;

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        @Override
        public String toString() {
            return "Price: " + price + ", Size: " + size;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class MarketsResponse {
        private Map<String, Market> markets;

        public Map<String, Market> getMarkets() {
            return markets;
        }

        public void setMarkets(Map<String, Market> markets) {
            this.markets = markets;
        }

        @Override
        public String toString() {
            return "MarketsResponse [markets=" + markets + "]";
        }

    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class Market {
        private String nextFundingRate;
        private BigDecimal annualizedFundingRate;

        public String getNextFundingRate() {
            return nextFundingRate;
        }

        public void setNextFundingRate(String nextFundingRate) {
            this.nextFundingRate = nextFundingRate;
            annualizedFundingRate = new BigDecimal(Double.parseDouble(nextFundingRate) * 24 * 365 * 100);
        }

        @Override
        public String toString() {
            return "Market [nextFundingRate=" + annualizedFundingRate + "]";
        }

        public BigDecimal getAnnualizedFundingRate() {
            return annualizedFundingRate;
        }

    }
}
