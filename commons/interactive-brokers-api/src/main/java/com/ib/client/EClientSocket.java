/*
 * EClientSocket.java
 *
 */
package com.ib.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class EClientSocket implements ClientSocketInterface {

    // Client version history
    //
    // 	6 = Added parentId to orderStatus
    // 	7 = The new execDetails event returned for an order filled status and reqExecDetails
    //     Also market depth is available.
    // 	8 = Added lastFillPrice to orderStatus() event and permId to execution details
    //  9 = Added 'averageCost', 'unrealizedPNL', and 'unrealizedPNL' to updatePortfolio event
    // 10 = Added 'serverId' to the 'open order' & 'order status' events.
    //      We send back all the API open orders upon connection.
    //      Added new methods reqAllOpenOrders, reqAutoOpenOrders()
    //      Added FA support - reqExecution has filter.
    //                       - reqAccountUpdates takes acct code.
    // 11 = Added permId to openOrder event.
    // 12 = requsting open order attributes ignoreRth, hidden, and discretionary
    // 13 = added goodAfterTime
    // 14 = always send size on bid/ask/last tick
    // 15 = send allocation description string on openOrder
    // 16 = can receive account name in account and portfolio updates, and fa params in openOrder
    // 17 = can receive liquidation field in exec reports, and notAutoAvailable field in mkt data
    // 18 = can receive good till date field in open order messages, and request intraday backfill
    // 19 = can receive rthOnly flag in ORDER_STATUS
    // 20 = expects TWS time string on connection after server version >= 20.
    // 21 = can receive bond contract details.
    // 22 = can receive price magnifier in version 2 contract details message
    // 23 = support for scanner
    // 24 = can receive volatility order parameters in open order messages
	// 25 = can receive HMDS query start and end times
	// 26 = can receive option vols in option market data messages
	// 27 = can receive delta neutral order type and delta neutral aux price in place order version 20: API 8.85
	// 28 = can receive option model computation ticks: API 8.9
	// 29 = can receive trail stop limit price in open order and can place them: API 8.91
	// 30 = can receive extended bond contract def, new ticks, and trade count in bars
	// 31 = can receive EFP extensions to scanner and market data, and combo legs on open orders
	//    ; can receive RT bars 
	// 32 = can receive TickType.LAST_TIMESTAMP
	//    ; can receive "whyHeld" in order status messages 
	// 33 = can receive ScaleNumComponents and ScaleComponentSize is open order messages 
	// 34 = can receive whatIf orders / order state
	// 35 = can receive contId field for Contract objects
	// 36 = can receive outsideRth field for Order objects
	// 37 = can receive clearingAccount and clearingIntent for Order objects
	
    private static final int CLIENT_VERSION = 37;
    private static final int SERVER_VERSION = 38;
    private static final byte[] EOL = {0};
    private static final String BAG_SEC_TYPE = "BAG";

    // FA msg data types
    public static final int GROUPS = 1;
    public static final int PROFILES = 2;
    public static final int ALIASES = 3;

    public static String faMsgTypeName(int faDataType) {
        switch (faDataType) {
            case GROUPS:
                return "GROUPS";
            case PROFILES:
                return "PROFILES";
            case ALIASES:
                return "ALIASES";
        }
        return null;
    }

    // outgoing msg id's
    private static final int REQ_MKT_DATA = 1;
    private static final int CANCEL_MKT_DATA = 2;
    private static final int PLACE_ORDER = 3;
    private static final int CANCEL_ORDER = 4;
    private static final int REQ_OPEN_ORDERS = 5;
    private static final int REQ_ACCOUNT_DATA = 6;
    private static final int REQ_EXECUTIONS = 7;
    private static final int REQ_IDS = 8;
    private static final int REQ_CONTRACT_DATA = 9;
    private static final int REQ_MKT_DEPTH = 10;
    private static final int CANCEL_MKT_DEPTH = 11;
    private static final int REQ_NEWS_BULLETINS = 12;
    private static final int CANCEL_NEWS_BULLETINS = 13;
    private static final int SET_SERVER_LOGLEVEL = 14;
    private static final int REQ_AUTO_OPEN_ORDERS = 15;
    private static final int REQ_ALL_OPEN_ORDERS = 16;
    private static final int REQ_MANAGED_ACCTS = 17;
    private static final int REQ_FA = 18;
    private static final int REPLACE_FA = 19;
    private static final int REQ_HISTORICAL_DATA = 20;
    private static final int EXERCISE_OPTIONS = 21;
    private static final int REQ_SCANNER_SUBSCRIPTION = 22;
    private static final int CANCEL_SCANNER_SUBSCRIPTION = 23;
    private static final int REQ_SCANNER_PARAMETERS = 24;
    private static final int CANCEL_HISTORICAL_DATA = 25;
    private static final int REQ_CURRENT_TIME = 49;
    private static final int REQ_REAL_TIME_BARS = 50;
    private static final int CANCEL_REAL_TIME_BARS = 51;
    
	private static final int MIN_SERVER_VER_REAL_TIME_BARS = 34;
	private static final int MIN_SERVER_VER_SCALE_ORDERS = 35;
	private static final int MIN_SERVER_VER_SNAPSHOT_MKT_DATA = 35;
	private static final int MIN_SERVER_VER_SSHORT_COMBO_LEGS = 35;
	private static final int MIN_SERVER_VER_WHAT_IF_ORDERS = 36;
	private static final int MIN_SERVER_VER_CONTRACT_CONID = 37;
	private static final int MIN_SERVER_VER_PTA_ORDERS = 39;

    private AnyWrapper 			m_anyWrapper;	// msg handler
    private Socket 			    m_socket;   // the socket
    private DataOutputStream 	m_dos;      // the socket output stream
    private boolean 			m_connected;// true if we are connected
    private EReader 			m_reader;   // thread which reads msgs from socket
    private int 			    m_serverVersion =1;
    private String              m_TwsTime;

    @Override
    public int serverVersion()          { return m_serverVersion;   }
    @Override
    public String TwsConnectionTime()   { return m_TwsTime; }
    @Override
    public AnyWrapper wrapper() 		{ return m_anyWrapper; }
    @Override
    public EReader reader()             { return m_reader; }


    public EClientSocket( AnyWrapper anyWrapper) {
        m_anyWrapper = anyWrapper;
    }
    
    @Override
    public boolean isConnected() {
        return m_connected;
    }
    
    @Override
    public synchronized void eConnect( String host, int port, int clientId) {
        // already connected?
        host = checkConnected(host);
        if(host == null){
            return;
        }
        try{
            Socket socket = new Socket( host, port);
            eConnect(socket, clientId);
        }
        catch( Exception e) {
            connectionError();
        }
    }
    
    protected void connectionError() {
        m_anyWrapper.error( EClientErrors.NO_VALID_ID, EClientErrors.CONNECT_FAIL.code(),
                EClientErrors.CONNECT_FAIL.msg());
        m_reader = null;
    }
    
    protected String checkConnected(String host) {
        if( m_connected) {
            m_anyWrapper.error(EClientErrors.NO_VALID_ID, EClientErrors.ALREADY_CONNECTED.code(),
                    EClientErrors.ALREADY_CONNECTED.msg());
            return null;
        }
        if( isNull( host) ) {
            host = "127.0.0.1";
        }
        return host;
    }

    @Override
    public EReader createReader(EClientSocket socket, DataInputStream dis) {
        return new EReader(socket, dis);
    }

    @Override
    public synchronized void eConnect(Socket socket, int clientId) throws IOException {
        m_socket = socket;

        // create io streams
        DataInputStream dis = new DataInputStream( m_socket.getInputStream() );
        m_dos = new DataOutputStream( m_socket.getOutputStream() );

        // set client version
        send( CLIENT_VERSION);

        // start reader thread
        m_reader = createReader(this, dis); 

        // check server version
        m_serverVersion = m_reader.readInt();
        System.out.println("Server Version:" + m_serverVersion);
        if ( m_serverVersion >= 20 ){
            m_TwsTime = m_reader.readStr();
            System.out.println("TWS Time at connection:" + m_TwsTime);
        }
        if( m_serverVersion < SERVER_VERSION) {
            m_anyWrapper.error( EClientErrors.NO_VALID_ID, EClientErrors.UPDATE_TWS.code(), EClientErrors.UPDATE_TWS.msg());
            return;
        }

        // Send the client id
        if ( m_serverVersion >= 3 ){
            send( clientId);
        }

        m_reader.start();

        // set connected flag
        m_connected = true;
    }

    @Override
    public synchronized void eDisconnect() {
    	System.out.println( "Disconnecting" );
    	Exception ex = new Exception();
    	ex.printStackTrace();
        // not connected?
        if( !m_connected) {
            return;
        }

        try {
            // stop reader thread
            if( m_reader != null) {
                m_reader.interrupt();
            }

            // close socket
            if( m_socket != null) {
                m_socket.close();
            }
        }
        catch( Exception e) {
        	e.printStackTrace();
        }

        m_connected = false;
        System.exit(1);
    }

    @Override
    public synchronized void cancelScannerSubscription( int tickerId) {
        // not connected?
        if( !m_connected) {
            error( EClientErrors.NO_VALID_ID, EClientErrors.NOT_CONNECTED, "");
            return;
        }

        if (m_serverVersion < 24) {
          error(EClientErrors.NO_VALID_ID, EClientErrors.UPDATE_TWS,
                "  It does not support API scanner subscription.");
          return;
        }

        final int VERSION = 1;

        // send cancel mkt data msg
        try {
            send( CANCEL_SCANNER_SUBSCRIPTION);
            send( VERSION);
            send( tickerId);
        }
        catch( Exception e) {
            error( tickerId, EClientErrors.FAIL_SEND_CANSCANNER, "" + e);
            e.printStackTrace();
           // close();
        }
    }

    @Override
    public synchronized void reqScannerParameters() {
        // not connected?
        if (!m_connected) {
            error(EClientErrors.NO_VALID_ID, EClientErrors.NOT_CONNECTED, "");
            return;
        }

        if (m_serverVersion < 24) {
          error(EClientErrors.NO_VALID_ID, EClientErrors.UPDATE_TWS,
                "  It does not support API scanner subscription.");
          return;
        }

        final int VERSION = 1;

        try {
            send(REQ_SCANNER_PARAMETERS);
            send(VERSION);
        }
        catch( Exception e) {
            error( EClientErrors.NO_VALID_ID,
                   EClientErrors.FAIL_SEND_REQSCANNERPARAMETERS, "" + e);
            e.printStackTrace();
            //close();
        }
    }

    @Override
    public synchronized void reqScannerSubscription( int tickerId,
        ScannerSubscription subscription) {
        // not connected?
        if (!m_connected) {
            error(EClientErrors.NO_VALID_ID, EClientErrors.NOT_CONNECTED, "");
            return;
        }

        if (m_serverVersion < 24) {
          error(EClientErrors.NO_VALID_ID, EClientErrors.UPDATE_TWS,
                "  It does not support API scanner subscription.");
          return;
        }

        final int VERSION = 3;

        try {
            send(REQ_SCANNER_SUBSCRIPTION);
            send(VERSION);
            send(tickerId);
            sendMax(subscription.numberOfRows());
            send(subscription.instrument());
            send(subscription.locationCode());
            send(subscription.scanCode());
            sendMax(subscription.abovePrice());
            sendMax(subscription.belowPrice());
            sendMax(subscription.aboveVolume());
            sendMax(subscription.marketCapAbove());
            sendMax(subscription.marketCapBelow());
            send(subscription.moodyRatingAbove());
            send(subscription.moodyRatingBelow());
            send(subscription.spRatingAbove());
            send(subscription.spRatingBelow());
            send(subscription.maturityDateAbove());
            send(subscription.maturityDateBelow());
            sendMax(subscription.couponRateAbove());
            sendMax(subscription.couponRateBelow());
            send(subscription.excludeConvertible());
            if (m_serverVersion >= 25) {
                send(subscription.averageOptionVolumeAbove());
                send(subscription.scannerSettingPairs());
            }
            if (m_serverVersion >= 27) {
                send(subscription.stockTypeFilter());
            }
        }
        catch( Exception e) {
            error( tickerId, EClientErrors.FAIL_SEND_REQSCANNER, "" + e);
            e.printStackTrace();
           // close();
        }
    }
   
    @Override
    public synchronized void reqMktData(int tickerId, Contract contract,
    		String genericTickList, boolean snapshot) {
        if (!m_connected) {
            error(EClientErrors.NO_VALID_ID, EClientErrors.NOT_CONNECTED, "");
            return;
        }
        
        if (m_serverVersion < MIN_SERVER_VER_SNAPSHOT_MKT_DATA && snapshot) {
        	error(tickerId, EClientErrors.UPDATE_TWS,
        			"  It does not support snapshot market data requests.");
        	return;
        }

        final int VERSION = 7;

        try {
            // send req mkt data msg
            send(REQ_MKT_DATA);
            send(VERSION);
            send(tickerId);

            // send contract fields
            send(contract.m_symbol);
            send(contract.m_secType);
            send(contract.m_expiry);
            send(contract.m_strike);
            send(contract.m_right);
            if (m_serverVersion >= 15) {
                send(contract.m_multiplier);
            }
            send(contract.m_exchange);
            if (m_serverVersion >= 14) {
                send(contract.m_primaryExch);
            }
            send(contract.m_currency);
            if(m_serverVersion >= 2) {
                send( contract.m_localSymbol);
            }
            if(m_serverVersion >= 8 && BAG_SEC_TYPE.equalsIgnoreCase(contract.m_secType)) {
                if ( contract.m_comboLegs == null ) {
                    send( 0);
                }
                else {
                    send( contract.m_comboLegs.size());

                    ComboLeg comboLeg;
                    for (int i=0; i < contract.m_comboLegs.size(); i ++) {
                        comboLeg = (ComboLeg)contract.m_comboLegs.get(i);
                        send( comboLeg.m_conId);
                        send( comboLeg.m_ratio);
                        send( comboLeg.m_action);
                        send( comboLeg.m_exchange);
                    }
                }
            }
            if (m_serverVersion >= 31) {
            	/*
            	 * Note: Even though SHORTABLE tick type supported only
            	 *       starting server version 33 it would be relatively
            	 *       expensive to expose this restriction here.
            	 *       
            	 *       Therefore we are relying on TWS doing validation.
            	 */
            	send( genericTickList);
            }
            if (m_serverVersion >= MIN_SERVER_VER_SNAPSHOT_MKT_DATA) {
            	send (snapshot);
            }
        }
        catch( Exception e) {
            error( tickerId, EClientErrors.FAIL_SEND_REQMKT, "" + e);
            e.printStackTrace();
         //   close();
        }
    }

    @Override
    public synchronized void cancelHistoricalData( int tickerId ) {
        // not connected?
        if( !m_connected) {
            error( EClientErrors.NO_VALID_ID, EClientErrors.NOT_CONNECTED, "");
            return;
        }

        if (m_serverVersion < 24) {
          error(EClientErrors.NO_VALID_ID, EClientErrors.UPDATE_TWS,
                "  It does not support historical data query cancellation.");
          return;
        }

        final int VERSION = 1;

        // send cancel mkt data msg
        try {
            send( CANCEL_HISTORICAL_DATA);
            send( VERSION);
            send( tickerId);
        }
        catch( Exception e) {
            error( tickerId, EClientErrors.FAIL_SEND_CANHISTDATA, "" + e);
            e.printStackTrace();
            //close();
        }
    }
    
    @Override
    public void cancelRealTimeBars(int tickerId) {
        // not connected?
        if( !m_connected) {
            error( EClientErrors.NO_VALID_ID, EClientErrors.NOT_CONNECTED, "");
            return;
        }
        
        if (m_serverVersion < MIN_SERVER_VER_REAL_TIME_BARS) {
            error(EClientErrors.NO_VALID_ID, EClientErrors.UPDATE_TWS,
                  "  It does not support realtime bar data query cancellation.");
            return;
        }
        
        final int VERSION = 1;

        // send cancel mkt data msg
        try {
            send( CANCEL_REAL_TIME_BARS);
            send( VERSION);
            send( tickerId);
        }
        catch( Exception e) {
            error( tickerId, EClientErrors.FAIL_SEND_CANRTBARS, "" + e);
            e.printStackTrace();
            //   close();
        }        
    }

    @Override
    public synchronized void reqHistoricalData( int tickerId, Contract contract,
                                                String endDateTime, String durationStr,
                                                String barSizeSetting, String whatToShow,
                                                int useRTH, int formatDate) {
        // not connected?
        if( !m_connected) {
            error( tickerId, EClientErrors.NOT_CONNECTED, "");
            return;
        }

        final int VERSION = 4;

        try {
          if (m_serverVersion < 16) {
            error(EClientErrors.NO_VALID_ID, EClientErrors.UPDATE_TWS,
                  "  It does not support historical data backfill.");
            return;
          }

          send(REQ_HISTORICAL_DATA);
          send(VERSION);
          send(tickerId);
          
          // send contract fields
          send(contract.m_symbol);
          send(contract.m_secType);
          send(contract.m_expiry);
          send(contract.m_strike);
          send(contract.m_right);
          send(contract.m_multiplier);
          send(contract.m_exchange);
          send(contract.m_primaryExch);
          send(contract.m_currency);
          send(contract.m_localSymbol);
          if (m_serverVersion >= 31) {
        	  send(contract.m_includeExpired ? 1 : 0);
          }
          if (m_serverVersion >= 20) {
              send(endDateTime);
              send(barSizeSetting);
          }
          send(durationStr);
          send(useRTH);
          send(whatToShow);
          if (m_serverVersion > 16) {
              send(formatDate);
          }
          if (BAG_SEC_TYPE.equalsIgnoreCase(contract.m_secType)) {
              if (contract.m_comboLegs == null) {
                  send(0);
              }
              else {
                  send(contract.m_comboLegs.size());

                  ComboLeg comboLeg;
                  for (int i = 0; i < contract.m_comboLegs.size(); i++) {
                      comboLeg = (ComboLeg) contract.m_comboLegs.get(i);
                      send(comboLeg.m_conId);
                      send(comboLeg.m_ratio);
                      send(comboLeg.m_action);
                      send(comboLeg.m_exchange);
                  }
              }
          }
        }
        catch (Exception e) {
          error(tickerId, EClientErrors.FAIL_SEND_REQHISTDATA, "" + e);
          e.printStackTrace();
          // close();
        }
    }
    
    @Override
    public synchronized void reqRealTimeBars(int tickerId, Contract contract, int barSize, String whatToShow, boolean useRTH) {
        // not connected?
        if (!m_connected ) {
            error(EClientErrors.NO_VALID_ID, EClientErrors.NOT_CONNECTED, "");
            return;
        }
        if (m_serverVersion < MIN_SERVER_VER_REAL_TIME_BARS) {
            error(EClientErrors.NO_VALID_ID, EClientErrors.UPDATE_TWS,
                  "  It does not support real time bars.");
            return;
        }

        final int VERSION = 1;

        try {
            // send req mkt data msg
            send(REQ_REAL_TIME_BARS);
            send(VERSION);
            send(tickerId);

            // send contract fields
            send(contract.m_symbol);
            send(contract.m_secType);
            send(contract.m_expiry);
            send(contract.m_strike);
            send(contract.m_right);
            send(contract.m_multiplier);
            send(contract.m_exchange);
            send(contract.m_primaryExch);
            send(contract.m_currency);
            send(contract.m_localSymbol);
            send(barSize);
            send(whatToShow);
            send(useRTH);
            
        }
        catch( Exception e) {
            error( tickerId, EClientErrors.FAIL_SEND_REQRTBARS, "" + e);
            e.printStackTrace();
            //close();
        }

    }

    @Override
    public synchronized void reqContractDetails(Contract contract)
    {
        // not connected?
        if( !m_connected) {
            error( EClientErrors.NO_VALID_ID, EClientErrors.NOT_CONNECTED, "");
            return;
        }

        // This feature is only available for versions of TWS >=4
        if( m_serverVersion < 4) {
            error(EClientErrors.NO_VALID_ID, EClientErrors.UPDATE_TWS.code(),
                            EClientErrors.UPDATE_TWS.msg());
            return;
        }

        final int VERSION = 4;

        try {
            // send req mkt data msg
            send( REQ_CONTRACT_DATA);
            send( VERSION);

            // send contract fields
            if (m_serverVersion >= MIN_SERVER_VER_CONTRACT_CONID) {
            	send(contract.m_conId);
            }
            send( contract.m_symbol);
            send( contract.m_secType);
            send( contract.m_expiry);
            send( contract.m_strike);
            send( contract.m_right);
            if (m_serverVersion >= 15) {
                send(contract.m_multiplier);
            }
            send( contract.m_exchange);
            send( contract.m_currency);
            send( contract.m_localSymbol);
            if (m_serverVersion >= 31) {
                send(contract.m_includeExpired);
            }
        }
        catch( Exception e) {
            error( EClientErrors.NO_VALID_ID, EClientErrors.FAIL_SEND_REQCONTRACT, "" + e);
            e.printStackTrace();
            //close();
        }
    }

    @Override
    public synchronized void reqMktDepth( int tickerId, Contract contract, int numRows)
    {
        // not connected?
        if( !m_connected) {
            error( EClientErrors.NO_VALID_ID, EClientErrors.NOT_CONNECTED, "");
            return;
        }

        // This feature is only available for versions of TWS >=6
        if( m_serverVersion < 6) {
            error(EClientErrors.NO_VALID_ID, EClientErrors.UPDATE_TWS.code(),
                    EClientErrors.UPDATE_TWS.msg());
            return;
        }

        final int VERSION = 3;

        try {
            // send req mkt data msg
            send( REQ_MKT_DEPTH);
            send( VERSION);
            send( tickerId);

            // send contract fields
            send( contract.m_symbol);
            send( contract.m_secType);
            send( contract.m_expiry);
            send( contract.m_strike);
            send( contract.m_right);
            if (m_serverVersion >= 15) {
              send(contract.m_multiplier);
            }
            send( contract.m_exchange);
            send( contract.m_currency);
            send( contract.m_localSymbol);
            if (m_serverVersion >= 19) {
                send( numRows);
            }
        }
        catch( Exception e) {
            error( tickerId, EClientErrors.FAIL_SEND_REQMKTDEPTH, "" + e);
            e.printStackTrace();
            
            // close();
        }
    }
   
    @Override
    public synchronized void cancelMktData( int tickerId) {
        // not connected?
        if( !m_connected) {
            error( EClientErrors.NO_VALID_ID, EClientErrors.NOT_CONNECTED, "");
            return;
        }

        final int VERSION = 1;

        // send cancel mkt data msg
        try {
            send( CANCEL_MKT_DATA);
            send( VERSION);
            send( tickerId);
        }
        catch( Exception e) {
            error( tickerId, EClientErrors.FAIL_SEND_CANMKT, "" + e);
            e.printStackTrace();
            //close();
        }
    }

    @Override
    public synchronized void cancelMktDepth( int tickerId) {
        // not connected?
        if( !m_connected) {
            error( EClientErrors.NO_VALID_ID, EClientErrors.NOT_CONNECTED, "");
            return;
        }

        // This feature is only available for versions of TWS >=6
        if( m_serverVersion < 6) {
            error(EClientErrors.NO_VALID_ID, EClientErrors.UPDATE_TWS.code(),
                    EClientErrors.UPDATE_TWS.msg());
            return;
        }

        final int VERSION = 1;

        // send cancel mkt data msg
        try {
            send( CANCEL_MKT_DEPTH);
            send( VERSION);
            send( tickerId);
        }
        catch( Exception e) {
            error( tickerId, EClientErrors.FAIL_SEND_CANMKTDEPTH, "" + e);
            e.printStackTrace();
            //close();
        }
    }

    @Override
    public synchronized void exerciseOptions( int tickerId, Contract contract,
                                              int exerciseAction, int exerciseQuantity,
                                              String account, int override) {
        // not connected?
        if( !m_connected) {
            error( tickerId, EClientErrors.NOT_CONNECTED, "");
            return;
        }

        final int VERSION = 1;

        try {
          if (m_serverVersion < 21) {
            error(EClientErrors.NO_VALID_ID, EClientErrors.UPDATE_TWS,
                  "  It does not support options exercise from the API.");
            return;
          }

          send(EXERCISE_OPTIONS);
          send(VERSION);
          send(tickerId);
          
          // send contract fields
          send(contract.m_symbol);
          send(contract.m_secType);
          send(contract.m_expiry);
          send(contract.m_strike);
          send(contract.m_right);
          send(contract.m_multiplier);
          send(contract.m_exchange);
          send(contract.m_currency);
          send(contract.m_localSymbol);
          send(exerciseAction);
          send(exerciseQuantity);
          send(account);
          send(override);
      }
      catch (Exception e) {
        error(tickerId, EClientErrors.FAIL_SEND_REQMKT, "" + e);
        e.printStackTrace();
        //close();
      }
    }

    @Override
    public synchronized void placeOrder( int id, Contract contract, Order order) {
        // not connected?
        if( !m_connected) {
            error( EClientErrors.NO_VALID_ID, EClientErrors.NOT_CONNECTED, "");
            return;
        }
        
        if (m_serverVersion < MIN_SERVER_VER_SCALE_ORDERS) {
        	if (order.m_scaleNumComponents != Integer.MAX_VALUE ||
        		order.m_scaleComponentSize != Integer.MAX_VALUE ||
        		order.m_scalePriceIncrement != Double.MAX_VALUE) {
        		error(id, EClientErrors.UPDATE_TWS,
            		"  It does not support Scale orders.");
        		return;
        	}
        }
        
        if (m_serverVersion < MIN_SERVER_VER_SSHORT_COMBO_LEGS) {
        	if (!contract.m_comboLegs.isEmpty()) {
                ComboLeg comboLeg;
                for (int i = 0; i < contract.m_comboLegs.size(); ++i) {
                    comboLeg = (ComboLeg)contract.m_comboLegs.get(i);
                    if (comboLeg.m_shortSaleSlot != 0 ||
                    	!IsEmpty(comboLeg.m_designatedLocation)) {
                		error(id, EClientErrors.UPDATE_TWS,
                			"  It does not support SSHORT flag for combo legs.");
                		return;
                    }
                }
        	}
        }
        
        if (m_serverVersion < MIN_SERVER_VER_WHAT_IF_ORDERS) {
        	if (order.m_whatIf) {
        		error(id, EClientErrors.UPDATE_TWS,
        			"  It does not support what-if orders.");
        		return;
        	}
        }

        final int VERSION = 25;

        // send place order msg
        try {
            send( PLACE_ORDER);
            send( VERSION);
            send( id);

            // send contract fields
            send( contract.m_symbol);
            send( contract.m_secType);
            send( contract.m_expiry);
            send( contract.m_strike);
            send( contract.m_right);
            if (m_serverVersion >= 15) {
                send(contract.m_multiplier);
            }
            send( contract.m_exchange);
            if( m_serverVersion >= 14) {
              send(contract.m_primaryExch);
            }
            send( contract.m_currency);
            if( m_serverVersion >= 2) {
                send (contract.m_localSymbol);
            }

            // send main order fields
            send( order.m_action);
            send( order.m_totalQuantity);
            send( order.m_orderType);
            send( order.m_lmtPrice);
            send( order.m_auxPrice);

            // send extended order fields
            send( order.m_tif);
            send( order.m_ocaGroup);
            send( order.m_account);
            send( order.m_openClose);
            send( order.m_origin);
            send( order.m_orderRef);
            send( order.m_transmit);
            if( m_serverVersion >= 4 ) {
                send (order.m_parentId);
            }

            if( m_serverVersion >= 5 ) {
                send (order.m_blockOrder);
                send (order.m_sweepToFill);
                send (order.m_displaySize);
                send (order.m_triggerMethod);
                if (m_serverVersion < 38) {
                	// will never happen
                	send(/* order.m_ignoreRth */ false);
                }
                else {
                	send (order.m_outsideRth);
                }
            }

            if(m_serverVersion >= 7 ) {
                send(order.m_hidden);
            }

            // Send combo legs for BAG requests
            if(m_serverVersion >= 8 && BAG_SEC_TYPE.equalsIgnoreCase(contract.m_secType)) {
                if ( contract.m_comboLegs == null ) {
                    send( 0);
                }
                else {
                    send( contract.m_comboLegs.size());

                    ComboLeg comboLeg;
                    for (int i=0; i < contract.m_comboLegs.size(); i ++) {
                        comboLeg = (ComboLeg)contract.m_comboLegs.get(i);
                        send( comboLeg.m_conId);
                        send( comboLeg.m_ratio);
                        send( comboLeg.m_action);
                        send( comboLeg.m_exchange);
                        send( comboLeg.m_openClose);
                        
                        if (m_serverVersion >= MIN_SERVER_VER_SSHORT_COMBO_LEGS) {
                        	send( comboLeg.m_shortSaleSlot);
                        	send( comboLeg.m_designatedLocation);
                        }
                    }
                }
            }

            if ( m_serverVersion >= 9 ) {
            	// send deprecated sharesAllocation field
                send( "");
            }

            if ( m_serverVersion >= 10 ) {
                send( order.m_discretionaryAmt);
            }

            if ( m_serverVersion >= 11 ) {
                send( order.m_goodAfterTime);
            }

            if ( m_serverVersion >= 12 ) {
                send( order.m_goodTillDate);
            }

            if ( m_serverVersion >= 13 ) {
               send( order.m_faGroup);
               send( order.m_faMethod);
               send( order.m_faPercentage);
               send( order.m_faProfile);
           }
           if (m_serverVersion >= 18) { // institutional short sale slot fields.
               send( order.m_shortSaleSlot);      // 0 only for retail, 1 or 2 only for institution.
               send( order.m_designatedLocation); // only populate when order.m_shortSaleSlot = 2.
           }
           if (m_serverVersion >= 19) {
               send( order.m_ocaType);
               if (m_serverVersion < 38) {
            	   // will never happen
            	   send( /* order.m_rthOnly */ false);
               }
               send( order.m_rule80A);
               send( order.m_settlingFirm);
               send( order.m_allOrNone);
               sendMax( order.m_minQty);
               sendMax( order.m_percentOffset);
               send( order.m_eTradeOnly);
               send( order.m_firmQuoteOnly);
               sendMax( order.m_nbboPriceCap);
               sendMax( order.m_auctionStrategy);
               sendMax( order.m_startingPrice);
               sendMax( order.m_stockRefPrice);
               sendMax( order.m_delta);
        	   // Volatility orders had specific watermark price attribs in server version 26
        	   double lower = (m_serverVersion == 26 && order.m_orderType.equals("VOL"))
        	   		? Double.MAX_VALUE
        	   		: order.m_stockRangeLower;
        	   double upper = (m_serverVersion == 26 && order.m_orderType.equals("VOL"))
   	   				? Double.MAX_VALUE
   	   				: order.m_stockRangeUpper;
               sendMax( lower);
               sendMax( upper);
           }

           if (m_serverVersion >= 22) {
               send( order.m_overridePercentageConstraints);
           }
           
           if (m_serverVersion >= 26) { // Volatility orders
               sendMax( order.m_volatility);
               sendMax( order.m_volatilityType);
               if (m_serverVersion < 28) {
            	   send( order.m_deltaNeutralOrderType.equalsIgnoreCase("MKT"));
               } else {
            	   send( order.m_deltaNeutralOrderType);
            	   sendMax( order.m_deltaNeutralAuxPrice);
               }
               send( order.m_continuousUpdate);
               if (m_serverVersion == 26) {
            	   // Volatility orders had specific watermark price attribs in server version 26
            	   double lower = order.m_orderType.equals("VOL") ? order.m_stockRangeLower : Double.MAX_VALUE;
            	   double upper = order.m_orderType.equals("VOL") ? order.m_stockRangeUpper : Double.MAX_VALUE;
                   sendMax( lower);
                   sendMax( upper);
               }
               sendMax( order.m_referencePriceType);
           }
           
           if (m_serverVersion >= 30) { // TRAIL_STOP_LIMIT stop price
               sendMax( order.m_trailStopPrice);
           }
           
           if (m_serverVersion >= MIN_SERVER_VER_SCALE_ORDERS) {
        	   sendMax (order.m_scaleNumComponents);
        	   sendMax (order.m_scaleComponentSize);
        	   sendMax (order.m_scalePriceIncrement);
           }
           
           if (m_serverVersion >= MIN_SERVER_VER_PTA_ORDERS) {
        	   send (order.m_clearingAccount);
        	   send (order.m_clearingIntent);
           }

           if (m_serverVersion >= MIN_SERVER_VER_WHAT_IF_ORDERS) {
        	   send (order.m_whatIf);
           }
        }
        catch( Exception e) {
            error( id, EClientErrors.FAIL_SEND_ORDER, "" + e);
            e.printStackTrace();
            //close();
        }
    }

    @Override
    public synchronized void reqAccountUpdates(boolean subscribe, String acctCode) {
        // not connected?
        if( !m_connected) {
            error( EClientErrors.NO_VALID_ID, EClientErrors.NOT_CONNECTED, "");
            return;
        }

        final int VERSION = 2;

        // send cancel order msg
        try {
            send( REQ_ACCOUNT_DATA );
            send( VERSION);
            send( subscribe);

            // Send the account code. This will only be used for FA clients
            if ( m_serverVersion >= 9 ) {
                send( acctCode);
            }
        }
        catch( Exception e) {
            error( EClientErrors.NO_VALID_ID, EClientErrors.FAIL_SEND_ACCT, "" + e);
            e.printStackTrace();
            // close();
        }
    }

    @Override
    public synchronized void reqExecutions(ExecutionFilter filter) {
        // not connected?
        if( !m_connected) {
            error( EClientErrors.NO_VALID_ID, EClientErrors.NOT_CONNECTED, "");
            return;
        }

        final int VERSION = 2;

        // send cancel order msg
        try {
            send( REQ_EXECUTIONS);
            send( VERSION);

            // Send the execution rpt filter data
            if ( m_serverVersion >= 9 ) {
                send( filter.m_clientId);
                send( filter.m_acctCode);

                // Note that the valid format for m_time is "yyyymmdd-hh:mm:ss"
                send( filter.m_time);
                send( filter.m_symbol);
                send( filter.m_secType);
                send( filter.m_exchange);
                send( filter.m_side);
            }
        }
        catch( Exception e) {
            error( EClientErrors.NO_VALID_ID, EClientErrors.FAIL_SEND_EXEC, "" + e);
            e.printStackTrace();
            // close();
        }
    }

    @Override
    public synchronized void cancelOrder( int id) {
        // not connected?
        if( !m_connected) {
            error( EClientErrors.NO_VALID_ID, EClientErrors.NOT_CONNECTED, "");
            return;
        }

        final int VERSION = 1;

        // send cancel order msg
        try {
            send( CANCEL_ORDER);
            send( VERSION);
            send( id);
        }
        catch( Exception e) {
            error( id, EClientErrors.FAIL_SEND_CORDER, "" + e);
            e.printStackTrace();
            //     close();
        }
    }

    @Override
    public synchronized void reqOpenOrders() {
        // not connected?
        if( !m_connected) {
            error( EClientErrors.NO_VALID_ID, EClientErrors.NOT_CONNECTED, "");
            return;
        }

        final int VERSION = 1;

        // send cancel order msg
        try {
            send( REQ_OPEN_ORDERS);
            send( VERSION);
        }
        catch( Exception e) {
            error(EClientErrors.NO_VALID_ID, EClientErrors.FAIL_SEND_OORDER, "" + e);
            e.printStackTrace();
            //close();
        }
    }

    @Override
    public synchronized void reqIds( int numIds) {
        // not connected?
        if( !m_connected) {
            error( EClientErrors.NO_VALID_ID, EClientErrors.NOT_CONNECTED, "");
            return;
        }

        final int VERSION = 1;

        try {
            send( REQ_IDS);
            send( VERSION);
            send( numIds);
        }
        catch( Exception e) {
            error( EClientErrors.NO_VALID_ID, EClientErrors.FAIL_SEND_CORDER, "" + e);
            e.printStackTrace();
            //close();
        }
    }

    @Override
    public synchronized void reqNewsBulletins( boolean allMsgs) {
        // not connected?
        if( !m_connected) {
            error( EClientErrors.NO_VALID_ID, EClientErrors.NOT_CONNECTED, "");
            return;
        }

        final int VERSION = 1;

        try {
            send( REQ_NEWS_BULLETINS);
            send( VERSION);
            send( allMsgs);
        }
        catch( Exception e) {
            error( EClientErrors.NO_VALID_ID, EClientErrors.FAIL_SEND_CORDER, "" + e);
            e.printStackTrace();
            // close();
        }
    }

    @Override
    public synchronized void cancelNewsBulletins() {
        // not connected?
        if( !m_connected) {
            error( EClientErrors.NO_VALID_ID, EClientErrors.NOT_CONNECTED, "");
            return;
        }

        final int VERSION = 1;

        // send cancel order msg
        try {
            send( CANCEL_NEWS_BULLETINS);
            send( VERSION);
        }
        catch( Exception e) {
            error( EClientErrors.NO_VALID_ID, EClientErrors.FAIL_SEND_CORDER, "" + e);
            e.printStackTrace();
            // close();
        }
    }

    @Override
    public synchronized void setServerLogLevel(int logLevel) {
        // not connected?
        if( !m_connected) {
            error( EClientErrors.NO_VALID_ID, EClientErrors.NOT_CONNECTED, "");
            return;
        }

        final int VERSION = 1;

                // send the set server logging level message
                try {
                        send( SET_SERVER_LOGLEVEL);
                        send( VERSION);
                        send( logLevel);
                }
        catch( Exception e) {
            error( EClientErrors.NO_VALID_ID, EClientErrors.FAIL_SEND_SERVER_LOG_LEVEL, "" + e);
            e.printStackTrace();
            // close();
        }
    }

    @Override
    public synchronized void reqAutoOpenOrders(boolean bAutoBind)
    {
        // not connected?
        if( !m_connected) {
            error( EClientErrors.NO_VALID_ID, EClientErrors.NOT_CONNECTED, "");
            return;
        }

        final int VERSION = 1;

        // send req open orders msg
        try {
            send( REQ_AUTO_OPEN_ORDERS);
            send( VERSION);
            send( bAutoBind);
        }
        catch( Exception e) {
            error(EClientErrors.NO_VALID_ID, EClientErrors.FAIL_SEND_OORDER, "" + e);
            e.printStackTrace();
            // close();
        }
    }

    @Override
    public synchronized void reqAllOpenOrders() {
        // not connected?
        if( !m_connected) {
            error( EClientErrors.NO_VALID_ID, EClientErrors.NOT_CONNECTED, "");
            return;
        }

        final int VERSION = 1;

        // send req all open orders msg
        try {
            send( REQ_ALL_OPEN_ORDERS);
            send( VERSION);
        }
        catch( Exception e) {
            error(EClientErrors.NO_VALID_ID, EClientErrors.FAIL_SEND_OORDER, "" + e);
            e.printStackTrace();
            // close();
        }
    }

    @Override
    public synchronized void reqManagedAccts() {
        // not connected?
        if( !m_connected) {
            error( EClientErrors.NO_VALID_ID, EClientErrors.NOT_CONNECTED, "");
            return;
        }

        final int VERSION = 1;

        // send req FA managed accounts msg
        try {
            send( REQ_MANAGED_ACCTS);
            send( VERSION);
        }
        catch( Exception e) {
            error(EClientErrors.NO_VALID_ID, EClientErrors.FAIL_SEND_OORDER, "" + e);
            e.printStackTrace();
            // close();
        }
    }

    @Override
    public synchronized void requestFA( int faDataType ) {
        // not connected?
        if( !m_connected) {
            error( EClientErrors.NO_VALID_ID, EClientErrors.NOT_CONNECTED, "");
            return;
        }

        // This feature is only available for versions of TWS >= 13
        if( m_serverVersion < 13) {
            error(EClientErrors.NO_VALID_ID, EClientErrors.UPDATE_TWS.code(),
                    EClientErrors.UPDATE_TWS.msg());
            return;
        }

        final int VERSION = 1;

        try {
            send( REQ_FA );
            send( VERSION);
            send( faDataType);
        }
        catch( Exception e) {
            error( faDataType, EClientErrors.FAIL_SEND_FA_REQUEST, "" + e);
            e.printStackTrace();
            // close();
        }
    }

    @Override
    public synchronized void replaceFA( int faDataType, String xml ) {
        // not connected?
        if( !m_connected) {
            error( EClientErrors.NO_VALID_ID, EClientErrors.NOT_CONNECTED, "");
            return;
        }

        // This feature is only available for versions of TWS >= 13
        if( m_serverVersion < 13) {
            error(EClientErrors.NO_VALID_ID, EClientErrors.UPDATE_TWS.code(),
                    EClientErrors.UPDATE_TWS.msg());
            return;
        }

        final int VERSION = 1;

        try {
            send( REPLACE_FA );
            send( VERSION);
            send( faDataType);
            send( xml);
        }
        catch( Exception e) {
            error( faDataType, EClientErrors.FAIL_SEND_FA_REPLACE, "" + e);
            e.printStackTrace();
            // close();
        }
    }
    
    @Override
    public synchronized void reqCurrentTime() {
        // not connected?
        if( !m_connected) {
            error( EClientErrors.NO_VALID_ID, EClientErrors.NOT_CONNECTED, "");
            return;
        }

        // This feature is only available for versions of TWS >= 33
        if( m_serverVersion < 33) {
            error(EClientErrors.NO_VALID_ID, EClientErrors.UPDATE_TWS,
                  "  It does not support current time requests.");
            return;
        }

        final int VERSION = 1;

        try {
            send( REQ_CURRENT_TIME );
            send( VERSION);
        }
        catch( Exception e) {
            error( EClientErrors.NO_VALID_ID, EClientErrors.FAIL_SEND_REQCURRTIME, "" + e);
            e.printStackTrace();
            // close();
        }
    }


    protected synchronized void error( String err) {
        m_anyWrapper.error( err);
    }

    protected synchronized void error( int id, int errorCode, String errorMsg) {
        m_anyWrapper.error( id, errorCode, errorMsg);
    }

    protected void close() {
        eDisconnect();
        m_anyWrapper.connectionClosed();
    }

    private static boolean is( String str) {
        // return true if the string is not empty
        return str != null && str.length() > 0;
    }

    private static boolean isNull( String str) {
        // return true if the string is null or empty
        return !is( str);
    }

    private void error(int id, EClientErrors.CodeMsgPair pair, String tail) {
        error(id, pair.code(), pair.msg() + tail);
    }

    protected void send( String str) throws IOException {
        // write string to data buffer; writer thread will
        // write it to socket
        if( !IsEmpty(str)) {
            m_dos.write( str.getBytes() );
        }
        sendEOL();
    }

    private void sendEOL() throws IOException {
        m_dos.write( EOL);
    }

    protected void send( int val) throws IOException {
        send( String.valueOf( val) );
    }

    protected void send( char val) throws IOException {
        m_dos.write( val);
        sendEOL();
    }

    protected void send( double val) throws IOException {
        send( String.valueOf( val) );
    }

    protected void send( long val) throws IOException {
        send( String.valueOf( val) );
    }

    private void sendMax( double val) throws IOException {
        if (val == Double.MAX_VALUE) {
            sendEOL();
        }
        else {
            send(String.valueOf(val));
        }
    }

    private void sendMax( int val) throws IOException {
        if (val == Integer.MAX_VALUE) {
            sendEOL();
        }
        else {
            send(String.valueOf(val));
        }
    }

    protected void send( boolean val) throws IOException {
        send( val ? 1 : 0);
    }
    
    private static boolean IsEmpty(String str) { 
    	return str == null || str.length() == 0;
    }

}
