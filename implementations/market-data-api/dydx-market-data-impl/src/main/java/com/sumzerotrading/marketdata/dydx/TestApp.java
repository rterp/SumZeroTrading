package com.sumzerotrading.marketdata.dydx;

import com.sumzerotrading.data.CryptoTicker;
import com.sumzerotrading.data.Exchange;
import com.sumzerotrading.marketdata.ILevel1Quote;
import com.sumzerotrading.marketdata.Level1QuoteListener;
import com.sumzerotrading.marketdata.QuoteEngine;

public class TestApp implements Level1QuoteListener {

    public void startApp() {
        QuoteEngine quoteEngine = new DyDxLevel1QuoteEngine();
        CryptoTicker ticker = new CryptoTicker("BTC-USD", Exchange.DYDX);
        CryptoTicker eth = new CryptoTicker("ETH-USD", Exchange.DYDX);
        CryptoTicker xrp = new CryptoTicker("XRP-USD", Exchange.DYDX);
        CryptoTicker sol = new CryptoTicker("SOL-USD", Exchange.DYDX);

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
