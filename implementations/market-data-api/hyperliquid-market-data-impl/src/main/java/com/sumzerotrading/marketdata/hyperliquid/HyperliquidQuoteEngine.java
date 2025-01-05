package com.sumzerotrading.marketdata.hyperliquid;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sumzerotrading.data.Ticker;
import com.sumzerotrading.marketdata.Level1Quote;
import com.sumzerotrading.marketdata.Level1QuoteListener;
import com.sumzerotrading.marketdata.QuoteEngine;
import com.sumzerotrading.marketdata.QuoteType;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HyperliquidQuoteEngine extends QuoteEngine implements Runnable {

    public static final String SLEEP_TIME_PROPERTY_KEY = "sleep.time.in.seconds";
    protected volatile boolean started = false;
    protected boolean threadCompleted = false;
    protected Thread thread = new Thread(this);
    private final OkHttpClient httpClient;
    private final ObjectMapper objectMapper;

    protected int sleepTimeInSeconds = 10;
    protected ArrayList<String> urlStrings = new ArrayList<>();
    private OrderBookResponse orderBook;
    protected Map<String, FundingData> allFundingRates;
    private static final String BASE_URL = "https://api.hyperliquid.xyz/info";

    public HyperliquidQuoteEngine() {
        this.httpClient = new OkHttpClient();
        this.objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    }

    public OrderBookResponse getOrderBook(String coin) {
        // Create the JSON body for the request
        String jsonRequest = String.format("{\"type\":\"l2Book\", \"coin\":\"%s\"}", coin);

        RequestBody requestBody = RequestBody.create(jsonRequest, MediaType.parse("application/json"));
        Request request = new Request.Builder().url(BASE_URL).post(requestBody)
                .addHeader("Content-Type", "application/json").build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new RuntimeException("Unexpected response code: " + response.code());
            }

            // Parse the JSON response
            String jsonResponse = response.body().string();
            return objectMapper.readValue(jsonResponse, OrderBookResponse.class);
        } catch (IOException e) {
            throw new RuntimeException("Error fetching l2Book for coin: " + coin, e);
        }
    }

    public static void main(String[] args) throws Exception {
        HyperliquidQuoteEngine client = new HyperliquidQuoteEngine();
        Map<String, FundingData> fundingRates = client.getAllFundingRates();

        // Print funding rates
        fundingRates.forEach(
                (name, rate) -> System.out.println("Asset: " + name + ", Funding Rate: " + rate.funding + "%"));

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
        System.out.println("Not supported for hyperliquid market data");
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
                    List<Map<String, String>> bids = orderBook.getLevels().get(0);
                    List<Map<String, String>> asks = orderBook.getLevels().size() > 1 ? orderBook.getLevels().get(1)
                            : List.of();

                    String bidPriceString = bids.get(0).get("px");
                    String bidSizeString = bids.get(0).get("sz");

                    String askPriceString = asks.get(0).get("px");
                    String askSizeString = asks.get(0).get("sz");

                    String fundingString = allFundingRates.get(ticker.getSymbol()).funding;
                    Double annualFunding = Double.parseDouble(fundingString) * 24.0 * 365.0 * 100.0;

                    HashMap<QuoteType, BigDecimal> quoteMap = new HashMap<>();
                    quoteMap.put(QuoteType.ASK, new BigDecimal(askPriceString));
                    quoteMap.put(QuoteType.ASK_SIZE, new BigDecimal(askSizeString));
                    quoteMap.put(QuoteType.BID, new BigDecimal(bidPriceString));
                    quoteMap.put(QuoteType.BID_SIZE, new BigDecimal(bidSizeString));
                    quoteMap.put(QuoteType.FUNDING_RATE, new BigDecimal(annualFunding));
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

    public Map<String, FundingData> getAllFundingRates() throws Exception {
        return parseResponse(fetchFundingRates());
    }

    public String fetchFundingRates() throws Exception {
        OkHttpClient client = new OkHttpClient();

        // Define the JSON request body
        String jsonBody = "{ \"type\": \"metaAndAssetCtxs\" }";

        // Build the HTTP request
        Request request = new Request.Builder().url(BASE_URL)
                .post(RequestBody.create(jsonBody, MediaType.get("application/json"))).build();

        // Execute the request
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new RuntimeException("Failed to fetch funding rates: " + response.code());
            }
            return response.body().string();
        }
    }

    public Map<String, FundingData> parseResponse(String jsonResponse) throws Exception {
        // Deserialize the response into two parts: Universe and FundingData
        List<Object> response = objectMapper.readValue(jsonResponse, List.class);

        UniverseData universeData = objectMapper.convertValue(response.get(0), UniverseData.class);
        List<FundingData> fundingDataList = objectMapper.convertValue(response.get(1),
                objectMapper.getTypeFactory().constructCollectionType(List.class, FundingData.class));

        // Map funding data by asset name
        Map<String, FundingData> fundingRates = new HashMap<>();
        for (int i = 0; i < fundingDataList.size(); i++) {
            FundingData fundingData = fundingDataList.get(i);
            fundingData.name = universeData.assets.get(i).name; // Assign name from universe data
            fundingRates.put(fundingData.name, fundingData);
        }

        return fundingRates;
    }

    // public MarketsResponse getAllFundingRates() {
    // Request request = new Request.Builder()
    // .url(FUNDING_URL)
    // .get()
    // .build();

    // try (Response response = httpClient.newCall(request).execute()) {
    // if (!response.isSuccessful()) {
    // throw new RuntimeException("Unexpected response code: " + response.code());
    // }
    // String jsonResponse = response.body().string();
    // return objectMapper.readValue(jsonResponse, MarketsResponse.class); // Parse
    // JSON
    // } catch (Exception e) {
    // throw new RuntimeException("Error fetching funding rates", e);
    // }
    // }

    // Classes for mapping JSON response
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class OrderBookResponse {
        private List<List<Map<String, String>>> levels;

        public List<List<Map<String, String>>> getLevels() {
            return levels;
        }

        public void setLevels(List<List<Map<String, String>>> levels) {
            this.levels = levels;
        }
    }

    // Define UniverseData and FundingData classes for JSON parsing
    public static class UniverseData {
        @JsonProperty("universe")
        public List<Asset> assets;
    }

    public static class Asset {
        @JsonProperty("szDecimals")
        public int szDecimals;
        @JsonProperty("name")
        public String name;
        @JsonProperty("maxLeverage")
        public int maxLeverage;
    }

    public static class FundingData {
        @JsonProperty("funding")
        public String funding;
        @JsonProperty("openInterest")
        public String openInterest;
        @JsonProperty("prevDayPx")
        public String prevDayPx;
        @JsonProperty("dayNtlVlm")
        public String dayNtlVlm;
        @JsonProperty("premium")
        public String premium;
        @JsonProperty("oraclePx")
        public String oraclePx;
        @JsonProperty("markPx")
        public String markPx;
        @JsonProperty("midPx")
        public String midPx;
        @JsonProperty("impactPxs")
        public List<String> impactPxs;
        @JsonProperty("dayBaseVlm")
        public String dayBaseVlm;

        // Added to link funding data with asset name
        public String name;
    }

    public void setSleepTimeInSeconds(int seconds) {
        this.sleepTimeInSeconds = seconds;
    }

    public int getSleepTimeInSeconds() {
        return sleepTimeInSeconds;
    }

}
