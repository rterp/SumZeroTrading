package com.sumzerotrading.marketdata.hyperliquid;

import com.sumzerotrading.data.CryptoTicker;
import com.sumzerotrading.data.Exchange;
import com.sumzerotrading.marketdata.ILevel1Quote;
import com.sumzerotrading.marketdata.Level1QuoteListener;
import com.sumzerotrading.marketdata.QuoteEngine;

public class TestApp implements Level1QuoteListener {

    public void startApp() {
        QuoteEngine quoteEngine = new HyperliquidQuoteEngine();
        CryptoTicker ticker = new CryptoTicker("BTC", Exchange.HYPERLIQUID);
        CryptoTicker eth = new CryptoTicker("ETH", Exchange.HYPERLIQUID);
        CryptoTicker xrp = new CryptoTicker("XRP", Exchange.HYPERLIQUID);
        CryptoTicker sol = new CryptoTicker("SOL", Exchange.HYPERLIQUID);

        quoteEngine.startEngine();
        quoteEngine.subscribeLevel1(ticker, this);
        quoteEngine.subscribeLevel1(eth, this);
        quoteEngine.subscribeLevel1(xrp, this);
        quoteEngine.subscribeLevel1(sol, this);

    }

    @Override
    public void quoteRecieved(ILevel1Quote quote) {
        System.out.println("Got quote: " + quote);

    }

    public static void main(String[] args) throws Exception {
        new TestApp().startApp();
        Thread.sleep(100000);
    }

}
