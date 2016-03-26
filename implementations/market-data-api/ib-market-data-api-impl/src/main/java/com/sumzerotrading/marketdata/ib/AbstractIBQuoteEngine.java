/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sumzerotrading.marketdata.ib;

import com.ib.client.CommissionReport;
import com.ib.client.Contract;
import com.ib.client.ContractDetails;
import com.ib.client.EWrapper;
import com.ib.client.Execution;
import com.ib.client.Order;
import com.ib.client.OrderState;
import com.ib.client.UnderComp;
import com.sumzerotrading.marketdata.QuoteEngine;

/**
 *
 * @author RobTerpilowski
 */
public abstract class AbstractIBQuoteEngine extends QuoteEngine implements EWrapper {

    @Override
    public void tickPrice(int tickerId, int field, double price, int canAutoExecute) {
        //not implemented
    }

    @Override
    public void tickSize(int tickerId, int field, int size) {
        //not implemented
    }

    @Override
    public void tickOptionComputation(int tickerId, int field, double impliedVol, double delta, double optPrice, double pvDividend, double gamma, double vega, double theta, double undPrice) {
        //not implemented
    }

    @Override
    public void tickGeneric(int tickerId, int tickType, double value) {
        //not implemented
    }

    @Override
    public void tickString(int tickerId, int tickType, String value) {
        //not implemented
    }

    @Override
    public void tickEFP(int tickerId, int tickType, double basisPoints, String formattedBasisPoints, double impliedFuture, int holdDays, String futureExpiry, double dividendImpact, double dividendsToExpiry) {
        //not implemented
    }

    @Override
    public void orderStatus(int orderId, String status, int filled, int remaining, double avgFillPrice, int permId, int parentId, double lastFillPrice, int clientId, String whyHeld) {
        //not implemented
    }

    @Override
    public void openOrder(int orderId, Contract contract, Order order, OrderState orderState) {
        //not implemented
    }

    @Override
    public void openOrderEnd() {
        //not implemented
    }

    @Override
    public void updateAccountValue(String key, String value, String currency, String accountName) {
        //not implemented
    }

    @Override
    public void updatePortfolio(Contract contract, int position, double marketPrice, double marketValue, double averageCost, double unrealizedPNL, double realizedPNL, String accountName) {
        //not implemented
    }

    @Override
    public void updateAccountTime(String timeStamp) {
        //not implemented
    }

    @Override
    public void accountDownloadEnd(String accountName) {
        //not implemented
    }

    @Override
    public void nextValidId(int orderId) {
        //not implemented
    }

    @Override
    public void contractDetails(int reqId, ContractDetails contractDetails) {
        //not implemented
    }

    @Override
    public void bondContractDetails(int reqId, ContractDetails contractDetails) {
        //not implemented
    }

    @Override
    public void contractDetailsEnd(int reqId) {
        //not implemented
    }

    @Override
    public void execDetails(int reqId, Contract contract, Execution execution) {
        //not implemented
    }

    @Override
    public void execDetailsEnd(int reqId) {
        //not implemented
    }

    @Override
    public void updateMktDepth(int tickerId, int position, int operation, int side, double price, int size) {
        //not implemented
    }

    @Override
    public void updateMktDepthL2(int tickerId, int position, String marketMaker, int operation, int side, double price, int size) {
        //not implemented
    }

    @Override
    public void updateNewsBulletin(int msgId, int msgType, String message, String origExchange) {
        //not implemented
    }

    @Override
    public void managedAccounts(String accountsList) {
        //not implemented
    }

    @Override
    public void receiveFA(int faDataType, String xml) {
        //not implemented
    }

    @Override
    public void historicalData(int reqId, String date, double open, double high, double low, double close, int volume, int count, double WAP, boolean hasGaps) {
        //not implemented
    }

    @Override
    public void scannerParameters(String xml) {
        //not implemented
    }

    @Override
    public void scannerData(int reqId, int rank, ContractDetails contractDetails, String distance, String benchmark, String projection, String legsStr) {
        //not implemented
    }

    @Override
    public void scannerDataEnd(int reqId) {
        //not implemented
    }

    @Override
    public void realtimeBar(int reqId, long time, double open, double high, double low, double close, long volume, double wap, int count) {
        //not implemented
    }

    @Override
    public void currentTime(long time) {
        //not implemented
    }

    @Override
    public void fundamentalData(int reqId, String data) {
        //not implemented
    }

    @Override
    public void deltaNeutralValidation(int reqId, UnderComp underComp) {
        //not implemented
    }

    @Override
    public void tickSnapshotEnd(int reqId) {
        //not implemented
    }

    @Override
    public void marketDataType(int reqId, int marketDataType) {
        //not implemented
    }

    @Override
    public void commissionReport(CommissionReport commissionReport) {
        //not implemented
    }

    @Override
    public void position(String account, Contract contract, int pos, double avgCost) {
        //not implemented
    }

    @Override
    public void positionEnd() {
        //not implemented
    }

    @Override
    public void accountSummary(int reqId, String account, String tag, String value, String currency) {
        //not implemented
    }

    @Override
    public void accountSummaryEnd(int reqId) {
        //not implemented
    }

    @Override
    public void verifyMessageAPI(String apiData) {
        //not implemented
    }

    @Override
    public void verifyCompleted(boolean isSuccessful, String errorText) {
        //not implemented
    }

    @Override
    public void displayGroupList(int reqId, String groups) {
        //not implemented
    }

    @Override
    public void displayGroupUpdated(int reqId, String contractInfo) {
        //not implemented
    }

    @Override
    public void error(Exception e) {
        //not implemented
    }

    @Override
    public void error(String str) {
        //not implemented
    }

    @Override
    public void error(int id, int errorCode, String errorMsg) {
        //not implemented
    }

    @Override
    public void connectionClosed() {
        //not implemented
    }


    
}
