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
package com.sumzerotrading.ib;

import com.ib.client.Bar;
import com.ib.client.CommissionAndFeesReport;
import com.ib.client.Contract;
import com.ib.client.ContractDescription;
import com.ib.client.ContractDetails;
import com.ib.client.Decimal;
import com.ib.client.DeltaNeutralContract;
import com.ib.client.DepthMktDataDescription;
import com.ib.client.EClientSocket;
import com.ib.client.EWrapper;
import com.ib.client.Execution;
import com.ib.client.FamilyCode;
import com.ib.client.HistogramEntry;
import com.ib.client.HistoricalSession;
import com.ib.client.HistoricalTick;
import com.ib.client.HistoricalTickBidAsk;
import com.ib.client.HistoricalTickLast;
import com.ib.client.NewsProvider;
import com.ib.client.Order;
import com.ib.client.OrderState;
import com.ib.client.PriceIncrement;
import com.ib.client.SoftDollarTier;
import com.ib.client.TickAttrib;
import com.ib.client.TickAttribBidAsk;
import com.ib.client.TickAttribLast;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;

/**
 *
 * @author Rob Terpilowski
 */
public class IBConnection implements IBConnectionInterface {

    protected Logger logger = Logger.getLogger(IBConnection.class);
    protected static IBConnection connection = null;

    protected EClientSocket eclientSocket;
    protected List<IBConnectionInterface> ibConnectionDelegates = new ArrayList<>();
    protected int clientId;
    protected String host;
    protected int port;

    public synchronized static EWrapper getInstance() {
        if (connection == null) {
            connection = new IBConnection();
        }
        return connection;
    }

    public IBConnection() {
        eclientSocket = new EClientSocket(this, this);
    }

    @Override
    public void addIbConnectionDelegate(IBConnectionInterface delegate) {
        ibConnectionDelegates.add(delegate);
    }

    @Override
    public void removeIbConnectionDelegate(IBConnectionInterface delegate) {
        ibConnectionDelegates.remove(delegate);
    }

    @Override
    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    @Override
    public void setHost(String host) {
        this.host = host;
    }

    @Override
    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public int getClientId() {
        return clientId;
    }

    @Override
    public String getHost() {
        return host;
    }

    @Override
    public int getPort() {
        return port;
    }
    
    
    
    

    // @Override
    // public void tickPrice(int tickerId, int field, double price, int canAutoExecute) {
    //     ibConnectionDelegates.stream().forEach((delegate) -> {
    //         delegate.tickPrice(tickerId, field, price, canAutoExecute);
    //     });
    // }

    // @Override
    // public void tickSize(int tickerId, int field, int size) {
    //     ibConnectionDelegates.stream().forEach((delegate) -> {
    //         delegate.tickSize(tickerId, field, size);
    //     });
    // }

    // @Override
    // public void tickOptionComputation(int tickerId, int field, double impliedVol, double delta, double optPrice, double pvDividend, double gamma, double vega, double theta, double undPrice) {
    //     ibConnectionDelegates.stream().forEach((delegate) -> {
    //         delegate.tickOptionComputation(tickerId, field, impliedVol, delta, optPrice, pvDividend, gamma, vega, theta, undPrice);
    //     });
    // }

    @Override
    public void tickGeneric(int tickerId, int tickType, double value) {
        ibConnectionDelegates.stream().forEach((delegate) -> {
            delegate.tickGeneric(tickerId, tickType, value);
        });
    }

    @Override
    public void tickString(int tickerId, int tickType, String value) {
        ibConnectionDelegates.stream().forEach((delegate) -> {
            delegate.tickString(tickerId, tickType, value);
        });
    }

    @Override
    public void tickEFP(int tickerId, int tickType, double basisPoints, String formattedBasisPoints, double impliedFuture, int holdDays, String futureExpiry, double dividendImpact, double dividendsToExpiry) {
        ibConnectionDelegates.stream().forEach((delegate) -> {
            delegate.tickEFP(tickerId, tickType, basisPoints, formattedBasisPoints, impliedFuture, holdDays, futureExpiry, dividendImpact, dividendsToExpiry);
        });
    }

    // @Override
    // public void orderStatus(int orderId, String status, int filled, int remaining, double avgFillPrice, int permId, int parentId, double lastFillPrice, int clientId, String whyHeld) {
    //     ibConnectionDelegates.stream().forEach((delegate) -> {
    //         delegate.orderStatus(orderId, status, filled, remaining, avgFillPrice, permId, parentId, lastFillPrice, clientId, whyHeld);
    //     });
    // }

    @Override
    public void openOrder(int orderId, Contract contract, Order order, OrderState orderState) {
        ibConnectionDelegates.stream().forEach((delegate) -> {
            delegate.openOrder(orderId, contract, order, orderState);
        });
    }

    @Override
    public void openOrderEnd() {
        ibConnectionDelegates.stream().forEach((delegate) -> {
            delegate.openOrderEnd();
        });
    }

    @Override
    public void updateAccountValue(String key, String value, String currency, String accountName) {
        ibConnectionDelegates.stream().forEach((delegate) -> {
            delegate.updateAccountValue(key, value, currency, accountName);
        });
    }

    // @Override
    // public void updatePortfolio(Contract contract, int position, double marketPrice, double marketValue, double averageCost, double unrealizedPNL, double realizedPNL, String accountName) {
    //     ibConnectionDelegates.stream().forEach((delegate) -> {
    //         delegate.updatePortfolio(contract, position, marketPrice, marketValue, averageCost, unrealizedPNL, realizedPNL, accountName);
    //     });
    // }

    @Override
    public void updateAccountTime(String timeStamp) {
        ibConnectionDelegates.stream().forEach((delegate) -> {
            delegate.updateAccountTime(timeStamp);
        });
    }

    @Override
    public void accountDownloadEnd(String accountName) {
        ibConnectionDelegates.stream().forEach((delegate) -> {
            delegate.accountDownloadEnd(accountName);
        });
    }

    @Override
    public void nextValidId(int orderId) {
        ibConnectionDelegates.stream().forEach((delegate) -> {
            delegate.nextValidId(orderId);
        });
    }

    @Override
    public void contractDetails(int reqId, ContractDetails contractDetails) {
        ibConnectionDelegates.stream().forEach((delegate) -> {
            delegate.contractDetails(reqId, contractDetails);
        });
    }

    @Override
    public void bondContractDetails(int reqId, ContractDetails contractDetails) {
        ibConnectionDelegates.stream().forEach((delegate) -> {
            delegate.bondContractDetails(reqId, contractDetails);
        });
    }

    @Override
    public void contractDetailsEnd(int reqId) {
        ibConnectionDelegates.stream().forEach((delegate) -> {
            delegate.contractDetailsEnd(reqId);
        });
    }

    @Override
    public void execDetails(int reqId, Contract contract, Execution execution) {
        ibConnectionDelegates.stream().forEach((delegate) -> {
            delegate.execDetails(reqId, contract, execution);
        });
    }

    @Override
    public void execDetailsEnd(int reqId) {
        ibConnectionDelegates.stream().forEach((delegate) -> {
            delegate.execDetailsEnd(reqId);
        });
    }

    // @Override
    // public void updateMktDepth(int tickerId, int position, int operation, int side, double price, int size) {
    //     ibConnectionDelegates.stream().forEach((delegate) -> {
    //         delegate.updateMktDepth(tickerId, position, operation, side, price, size);
    //     });
    // }

    // @Override
    // public void updateMktDepthL2(int tickerId, int position, String marketMaker, int operation, int side, double price, int size) {
    //     ibConnectionDelegates.stream().forEach((delegate) -> {
    //         delegate.updateMktDepthL2(tickerId, position, marketMaker, operation, side, price, size);
    //     });
    // }

    @Override
    public void updateNewsBulletin(int msgId, int msgType, String message, String origExchange) {
        ibConnectionDelegates.stream().forEach((delegate) -> {
            delegate.updateNewsBulletin(msgId, msgType, message, origExchange);
        });
    }

    @Override
    public void managedAccounts(String accountsList) {
        ibConnectionDelegates.stream().forEach((delegate) -> {
            delegate.managedAccounts(accountsList);
        });
    }

    @Override
    public void receiveFA(int faDataType, String xml) {
        ibConnectionDelegates.stream().forEach((delegate) -> {
            delegate.receiveFA(faDataType, xml);
        });
    }

    // @Override
    // public void historicalData(int reqId, String date, double open, double high, double low, double close, int volume, int count, double WAP, boolean hasGaps) {
    //     ibConnectionDelegates.stream().forEach((delegate) -> {
    //         delegate.historicalData(reqId, date, open, high, low, close, volume, count, WAP, hasGaps);
    //     });
    // }

    @Override
    public void scannerParameters(String xml) {
        ibConnectionDelegates.stream().forEach((delegate) -> {
            delegate.scannerParameters(xml);
        });
    }

    @Override
    public void scannerData(int reqId, int rank, ContractDetails contractDetails, String distance, String benchmark, String projection, String legsStr) {
        ibConnectionDelegates.stream().forEach((delegate) -> {
            delegate.scannerData(reqId, rank, contractDetails, distance, benchmark, projection, legsStr);
        });
    }

    @Override
    public void scannerDataEnd(int reqId) {
        ibConnectionDelegates.stream().forEach((delegate) -> {
            delegate.scannerDataEnd(reqId);
        });
    }

    // @Override
    // public void realtimeBar(int reqId, long time, double open, double high, double low, double close, long volume, double wap, int count) {
    //     ibConnectionDelegates.stream().forEach((delegate) -> {
    //         delegate.realtimeBar(reqId, time, open, high, low, close, volume, wap, count);
    //     });
    // }

    @Override
    public void currentTime(long time) {
        ibConnectionDelegates.stream().forEach((delegate) -> {
            delegate.currentTime(time);
        });
    }

    @Override
    public void fundamentalData(int reqId, String data) {
        ibConnectionDelegates.stream().forEach((delegate) -> {
            delegate.fundamentalData(reqId, data);
        });
    }

    // @Override
    // public void deltaNeutralValidation(int reqId, UnderComp underComp) {
    //     ibConnectionDelegates.stream().forEach((delegate) -> {
    //         delegate.deltaNeutralValidation(reqId, underComp);
    //     });
    // }

    @Override
    public void tickSnapshotEnd(int reqId) {
        ibConnectionDelegates.stream().forEach((delegate) -> {
            delegate.tickSnapshotEnd(reqId);
        });
    }

    @Override
    public void marketDataType(int reqId, int marketDataType) {
        ibConnectionDelegates.stream().forEach((delegate) -> {
            delegate.marketDataType(reqId, marketDataType);
        });
    }

    // @Override
    // public void commissionReport(CommissionReport commissionReport) {
    //     ibConnectionDelegates.stream().forEach((delegate) -> {
    //         delegate.commissionReport(commissionReport);
    //     });
    // }

    // @Override
    // public void position(String account, Contract contract, int pos, double avgCost) {
    //     ibConnectionDelegates.stream().forEach((delegate) -> {
    //         delegate.position(account, contract, pos, avgCost);
    //     });
    // }

    @Override
    public void positionEnd() {
        ibConnectionDelegates.stream().forEach((delegate) -> {
            delegate.positionEnd();
        });
    }

    @Override
    public void accountSummary(int reqId, String account, String tag, String value, String currency) {
        ibConnectionDelegates.stream().forEach((delegate) -> {
            delegate.accountSummary(reqId, account, tag, value, currency);
        });
    }

    @Override
    public void accountSummaryEnd(int reqId) {
        ibConnectionDelegates.stream().forEach((delegate) -> {
            delegate.accountSummaryEnd(reqId);
        });
    }

    @Override
    public void verifyMessageAPI(String apiData) {
        ibConnectionDelegates.stream().forEach((delegate) -> {
            delegate.verifyMessageAPI(apiData);
        });
    }

    @Override
    public void verifyCompleted(boolean isSuccessful, String errorText) {
        ibConnectionDelegates.stream().forEach((delegate) -> {
            delegate.verifyCompleted(isSuccessful, errorText);
        });
    }

    @Override
    public void displayGroupList(int reqId, String groups) {
        ibConnectionDelegates.stream().forEach((delegate) -> {
            delegate.displayGroupList(reqId, groups);
        });
    }

    @Override
    public void displayGroupUpdated(int reqId, String contractInfo) {
        ibConnectionDelegates.stream().forEach((delegate) -> {
            delegate.displayGroupUpdated(reqId, contractInfo);
        });
    }

    @Override
    public void error(Exception e) {
        ibConnectionDelegates.stream().forEach((delegate) -> {
            delegate.error(e);
        });
    }

    @Override
    public void error(String str) {
        ibConnectionDelegates.stream().forEach((delegate) -> {
            delegate.error(str);
        });
    }

        // @Override
        // public void error(int id, int errorCode, String errorMsg) {
        //     ibConnectionDelegates.stream().forEach((delegate) -> {
        //         delegate.error(id, errorCode, errorMsg);
        //     });
        // }

    @Override
    public void connectionClosed() {
        ibConnectionDelegates.stream().forEach((delegate) -> {
            delegate.connectionClosed();
        });
    }

    @Override
    public void accountUpdateMulti(int arg0, String arg1, String arg2, String arg3, String arg4, String arg5) {
        throw new UnsupportedOperationException();
        
    }

    @Override
    public void accountUpdateMultiEnd(int arg0) {
        throw new UnsupportedOperationException();
        
    }

    @Override
    public void commissionAndFeesReport(CommissionAndFeesReport arg0) {
        throw new UnsupportedOperationException();
        
    }

    @Override
    public void completedOrder(Contract arg0, Order arg1, OrderState arg2) {
        throw new UnsupportedOperationException();
        
    }

    @Override
    public void completedOrdersEnd() {
        throw new UnsupportedOperationException();
        
    }

    @Override
    public void connectAck() {
        throw new UnsupportedOperationException();
        
    }

    @Override
    public void deltaNeutralValidation(int arg0, DeltaNeutralContract arg1) {
        throw new UnsupportedOperationException();
        
    }

    @Override
    public void error(int arg0, long arg1, int arg2, String arg3, String arg4) {
        throw new UnsupportedOperationException();
        
    }

    @Override
    public void familyCodes(FamilyCode[] arg0) {
        throw new UnsupportedOperationException();
        
    }

    @Override
    public void headTimestamp(int arg0, String arg1) {
        throw new UnsupportedOperationException();
        
    }

    @Override
    public void histogramData(int arg0, List<HistogramEntry> arg1) {
        throw new UnsupportedOperationException();
        
    }

    @Override
    public void historicalData(int arg0, Bar arg1) {
        throw new UnsupportedOperationException();
        
    }

    @Override
    public void historicalDataEnd(int arg0, String arg1, String arg2) {
        throw new UnsupportedOperationException();
        
    }

    @Override
    public void historicalDataUpdate(int arg0, Bar arg1) {
        throw new UnsupportedOperationException();
        
    }

    @Override
    public void historicalNews(int arg0, String arg1, String arg2, String arg3, String arg4) {
        throw new UnsupportedOperationException();
        
    }

    @Override
    public void historicalNewsEnd(int arg0, boolean arg1) {
        throw new UnsupportedOperationException();
        
    }

    @Override
    public void historicalSchedule(int arg0, String arg1, String arg2, String arg3, List<HistoricalSession> arg4) {
        throw new UnsupportedOperationException();
        
    }

    @Override
    public void historicalTicks(int arg0, List<HistoricalTick> arg1, boolean arg2) {
        throw new UnsupportedOperationException();
        
    }

    @Override
    public void historicalTicksBidAsk(int arg0, List<HistoricalTickBidAsk> arg1, boolean arg2) {
        throw new UnsupportedOperationException();
        
    }

    @Override
    public void historicalTicksLast(int arg0, List<HistoricalTickLast> arg1, boolean arg2) {
        throw new UnsupportedOperationException();
        
    }

    @Override
    public void marketRule(int arg0, PriceIncrement[] arg1) {
        throw new UnsupportedOperationException();
        
    }

    @Override
    public void mktDepthExchanges(DepthMktDataDescription[] arg0) {
        throw new UnsupportedOperationException();
        
    }

    @Override
    public void newsArticle(int arg0, int arg1, String arg2) {
        throw new UnsupportedOperationException();
        
    }

    @Override
    public void newsProviders(NewsProvider[] arg0) {
        throw new UnsupportedOperationException();
        
    }

    @Override
    public void orderBound(long arg0, int arg1, int arg2) {
        throw new UnsupportedOperationException();
        
    }

    @Override
    public void orderStatus(int arg0, String arg1, Decimal arg2, Decimal arg3, double arg4, long arg5, int arg6,
            double arg7, int arg8, String arg9, double arg10) {
        throw new UnsupportedOperationException();
        
    }

    @Override
    public void pnl(int arg0, double arg1, double arg2, double arg3) {
        throw new UnsupportedOperationException();
        
    }

    @Override
    public void pnlSingle(int arg0, Decimal arg1, double arg2, double arg3, double arg4, double arg5) {
        throw new UnsupportedOperationException();
        
    }

    @Override
    public void position(String arg0, Contract arg1, Decimal arg2, double arg3) {
        throw new UnsupportedOperationException();
        
    }

    @Override
    public void positionMulti(int arg0, String arg1, String arg2, Contract arg3, Decimal arg4, double arg5) {
        throw new UnsupportedOperationException();
        
    }

    @Override
    public void positionMultiEnd(int arg0) {
        throw new UnsupportedOperationException();
        
    }

    @Override
    public void realtimeBar(int arg0, long arg1, double arg2, double arg3, double arg4, double arg5, Decimal arg6,
            Decimal arg7, int arg8) {
        throw new UnsupportedOperationException();
        
    }

    @Override
    public void replaceFAEnd(int arg0, String arg1) {
        throw new UnsupportedOperationException();
        
    }

    @Override
    public void rerouteMktDataReq(int arg0, int arg1, String arg2) {
        throw new UnsupportedOperationException();
        
    }

    @Override
    public void rerouteMktDepthReq(int arg0, int arg1, String arg2) {
        throw new UnsupportedOperationException();
        
    }

    @Override
    public void securityDefinitionOptionalParameter(int arg0, String arg1, int arg2, String arg3, String arg4,
            Set<String> arg5, Set<Double> arg6) {
        throw new UnsupportedOperationException();
        
    }

    @Override
    public void securityDefinitionOptionalParameterEnd(int arg0) {
        throw new UnsupportedOperationException();
        
    }

    @Override
    public void smartComponents(int arg0, Map<Integer, Entry<String, Character>> arg1) {
        throw new UnsupportedOperationException();
        
    }

    @Override
    public void softDollarTiers(int arg0, SoftDollarTier[] arg1) {
        throw new UnsupportedOperationException();
        
    }

    @Override
    public void symbolSamples(int arg0, ContractDescription[] arg1) {
        throw new UnsupportedOperationException();
        
    }

    @Override
    public void tickByTickAllLast(int arg0, int arg1, long arg2, double arg3, Decimal arg4, TickAttribLast arg5,
            String arg6, String arg7) {
        throw new UnsupportedOperationException();
        
    }

    @Override
    public void tickByTickBidAsk(int arg0, long arg1, double arg2, double arg3, Decimal arg4, Decimal arg5,
            TickAttribBidAsk arg6) {
        throw new UnsupportedOperationException();
        
    }

    @Override
    public void tickByTickMidPoint(int arg0, long arg1, double arg2) {
        throw new UnsupportedOperationException();
        
    }

    @Override
    public void tickNews(int arg0, long arg1, String arg2, String arg3, String arg4, String arg5) {
        throw new UnsupportedOperationException();
        
    }

    @Override
    public void tickOptionComputation(int arg0, int arg1, int arg2, double arg3, double arg4, double arg5, double arg6,
            double arg7, double arg8, double arg9, double arg10) {
        throw new UnsupportedOperationException();
        
    }

    @Override
    public void tickPrice(int arg0, int arg1, double arg2, TickAttrib arg3) {
        throw new UnsupportedOperationException();
        
    }

    @Override
    public void tickReqParams(int arg0, double arg1, String arg2, int arg3) {
        throw new UnsupportedOperationException();
        
    }

    @Override
    public void tickSize(int arg0, int arg1, Decimal arg2) {
        throw new UnsupportedOperationException();
        
    }

    @Override
    public void updateMktDepth(int arg0, int arg1, int arg2, int arg3, double arg4, Decimal arg5) {
        throw new UnsupportedOperationException();
        
    }

    @Override
    public void updateMktDepthL2(int arg0, int arg1, String arg2, int arg3, int arg4, double arg5, Decimal arg6,
            boolean arg7) {
        throw new UnsupportedOperationException();
        
    }

    @Override
    public void updatePortfolio(Contract arg0, Decimal arg1, double arg2, double arg3, double arg4, double arg5,
            double arg6, String arg7) {
        throw new UnsupportedOperationException();
        
    }

    @Override
    public void userInfo(int arg0, String arg1) {
        throw new UnsupportedOperationException();
        
    }

    @Override
    public void verifyAndAuthCompleted(boolean arg0, String arg1) {
        throw new UnsupportedOperationException();
        
    }

    @Override
    public void verifyAndAuthMessageAPI(String arg0, String arg1) {
        throw new UnsupportedOperationException();
        
    }

    @Override
    public void wshEventData(int arg0, String arg1) {
        throw new UnsupportedOperationException();
        
    }

    @Override
    public void wshMetaData(int arg0, String arg1) {
        throw new UnsupportedOperationException();
        
    }

    @Override
    public void issueSignal() {
        throw new UnsupportedOperationException();
        
    }

    @Override
    public void waitForSignal() {
        throw new UnsupportedOperationException();
        
    }



    

}
