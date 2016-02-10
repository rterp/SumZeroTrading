/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ib.client;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author Rob Terpilowski
 */
public interface ClientSocketInterface {

    String TwsConnectionTime();

    void cancelHistoricalData(int tickerId);

    void cancelMktData(int tickerId);

    void cancelMktDepth(int tickerId);

    void cancelNewsBulletins();

    void cancelOrder(int id);

    void cancelRealTimeBars(int tickerId);

    void cancelScannerSubscription(int tickerId);

    EReader createReader(EClientSocket socket, DataInputStream dis);

    void eConnect(String host, int port, int clientId);

    void eConnect(Socket socket, int clientId) throws IOException;

    void eDisconnect();

    void exerciseOptions(int tickerId, Contract contract, int exerciseAction, int exerciseQuantity, String account, int override);

    boolean isConnected();

    void placeOrder(int id, Contract contract, Order order);

    EReader reader();

    void replaceFA(int faDataType, String xml);

    void reqAccountUpdates(boolean subscribe, String acctCode);

    void reqAllOpenOrders();

    void reqAutoOpenOrders(boolean bAutoBind);

    void reqContractDetails(Contract contract);

    void reqCurrentTime();

    void reqExecutions(ExecutionFilter filter);

    void reqHistoricalData(int tickerId, Contract contract, String endDateTime, String durationStr, String barSizeSetting, String whatToShow, int useRTH, int formatDate);

    void reqIds(int numIds);

    void reqManagedAccts();

    void reqMktData(int tickerId, Contract contract, String genericTickList, boolean snapshot);

    void reqMktDepth(int tickerId, Contract contract, int numRows);

    void reqNewsBulletins(boolean allMsgs);

    void reqOpenOrders();

    void reqRealTimeBars(int tickerId, Contract contract, int barSize, String whatToShow, boolean useRTH);

    void reqScannerParameters();

    void reqScannerSubscription(int tickerId, ScannerSubscription subscription);

    void requestFA(int faDataType);

    int serverVersion();

    void setServerLogLevel(int logLevel);

    AnyWrapper wrapper();
    
}
