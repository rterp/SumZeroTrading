/**
 * MIT License
 *
 * Copyright (c) 2015  Rob Terpilowski
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING
 * BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.sumzerotrading.ib.example.trading;

import com.sumzerotrading.broker.order.TradeDirection;
import com.sumzerotrading.broker.order.TradeOrder;
import com.sumzerotrading.data.Exchange;
import com.sumzerotrading.data.FuturesTicker;
import com.sumzerotrading.interactive.brokers.client.InteractiveBrokersClient;
import com.sumzerotrading.interactive.brokers.client.InteractiveBrokersClientInterface;

public class TradingExample {

    public void start() {
        //Connect to the Interactive Brokers TWS Client
        InteractiveBrokersClientInterface ibClient = InteractiveBrokersClient.getInstance("localhost", 7999, 1);
        ibClient.connect();

        //Create an S&P 500 Futures ticker
        FuturesTicker esTicker = new FuturesTicker();
        esTicker.setSymbol("ES");
        esTicker.setExpiryMonth(3);
        esTicker.setExpiryYear(2016);
        esTicker.setExchange(Exchange.GLOBEX);
        

        String orderId = ibClient.getNextOrderId();
        int contracts = 5;

        //Create the order and send to Interactive Brokers
        TradeOrder order = new TradeOrder(orderId, esTicker, contracts, TradeDirection.BUY);
        order.setType(TradeOrder.Type.LIMIT);
        order.setLimitPrice(1955.50);
        ibClient.placeOrder(order);

    }

    public static void main(String[] args) {
        new TradingExample().start();
    }
}
