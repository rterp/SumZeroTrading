package com.sumzerotrading.marketdata.dydx;

public interface WebsocketEventListener {

    public void isConnected(boolean isConnected);

    public void disconnected();

    public void updateBid(String ticker, String bid, String size);

    public void updateAsk(String ticker, String ask, String size);

    public void initialBook(String ticker, OrderBook orderBook);
}
