/*
 * Order.java
 *
 */
package com.ib.client;

public class Order {
    final public static int 	CUSTOMER = 0;
    final public static int 	FIRM = 1;
    final public static char    OPT_UNKNOWN='?';
    final public static char    OPT_BROKER_DEALER='b';
    final public static char    OPT_CUSTOMER ='c';
    final public static char    OPT_FIRM='f';
    final public static char    OPT_ISEMM='m';
    final public static char    OPT_FARMM='n';
    final public static char    OPT_SPECIALIST='y';
    final public static int 	AUCTION_MATCH = 1;
    final public static int 	AUCTION_IMPROVEMENT = 2;
    final public static int 	AUCTION_TRANSPARENT = 3;
    final public static String  EMPTY_STR = "";

    // main order fields
    public int 		m_orderId;
    public int 		m_clientId;
    public int  	m_permId;
    public String 	m_action;
    public int 		m_totalQuantity;
    public String 	m_orderType;
    public double 	m_lmtPrice;
    public double 	m_auxPrice;

    // extended order fields
    public String 	m_tif;  // "Time in Force" - DAY, GTC, etc.
    public String 	m_ocaGroup; // one cancels all group name
    public int      m_ocaType;  // 1 = CANCEL_WITH_BLOCK, 2 = REDUCE_WITH_BLOCK, 3 = REDUCE_NON_BLOCK
    public String 	m_orderRef;
    public boolean 	m_transmit;	// if false, order will be created but not transmited
    public int 		m_parentId;	// Parent order Id, to associate Auto STP or TRAIL orders with the original order.
    public boolean 	m_blockOrder;
    public boolean	m_sweepToFill;
    public int 		m_displaySize;
    public int 		m_triggerMethod; // 0=Default, 1=Double_Bid_Ask, 2=Last, 3=Double_Last, 4=Bid_Ask, 7=Last_or_Bid_Ask, 8=Mid-point
    public boolean 	m_outsideRth;
    public boolean  m_hidden;
    public String   m_goodAfterTime; // FORMAT: 20060505 08:00:00 {time zone}
    public String   m_goodTillDate;  // FORMAT: 20060505 08:00:00 {time zone}
    public boolean  m_overridePercentageConstraints;
    public String   m_rule80A;  // Individual = 'I', Agency = 'A', AgentOtherMember = 'W', IndividualPTIA = 'J', AgencyPTIA = 'U', AgentOtherMemberPTIA = 'M', IndividualPT = 'K', AgencyPT = 'Y', AgentOtherMemberPT = 'N'
    public boolean  m_allOrNone;
    public int      m_minQty;
    public double   m_percentOffset;    // REL orders only
    public double   m_trailStopPrice;   // for TRAILLIMIT orders only

    // Financial advisors only 
    public String   m_faGroup;
    public String   m_faProfile;
    public String   m_faMethod;
    public String   m_faPercentage;

    // Institutional orders only
    public String 	m_openClose;          // O=Open, C=Close
    public int 		m_origin;             // 0=Customer, 1=Firm
    public int      m_shortSaleSlot;      // 1 if you hold the shares, 2 if they will be delivered from elsewhere.  Only for Action="SSHORT
    public String   m_designatedLocation; // set when slot=2 only.

    // SMART routing only
    public double   m_discretionaryAmt;
    public boolean  m_eTradeOnly;
    public boolean  m_firmQuoteOnly;
    public double   m_nbboPriceCap;

    // BOX or VOL ORDERS ONLY
    public int      m_auctionStrategy; // 1=AUCTION_MATCH, 2=AUCTION_IMPROVEMENT, 3=AUCTION_TRANSPARENT

    // BOX ORDERS ONLY
    public double   m_startingPrice;
    public double   m_stockRefPrice;
    public double   m_delta;

    // pegged to stock or VOL orders
    public double   m_stockRangeLower;
    public double   m_stockRangeUpper;

    // VOLATILITY ORDERS ONLY
    public double   m_volatility;
    public int      m_volatilityType;     // 1=daily, 2=annual
    public int      m_continuousUpdate;
    public int      m_referencePriceType; // 1=Average, 2 = BidOrAsk
    public String   m_deltaNeutralOrderType;
    public double   m_deltaNeutralAuxPrice;

    // COMBO ORDERS ONLY
    public double   m_basisPoints;      // EFP orders only
    public int      m_basisPointsType;  // EFP orders only
    
    // SCALE ORDERS ONLY
    public int      m_scaleNumComponents;
    public int      m_scaleComponentSize;
    public double   m_scalePriceIncrement;

    // Clearing info
    public String 	m_account; // IB account
    public String   m_settlingFirm;
    public String   m_clearingAccount; // True beneficiary of the order
    public String   m_clearingIntent; // "" (Default), "IB", "Away", "PTA" (PostTrade)

    // What-if
    public boolean  m_whatIf;

    public Order() {
    	m_outsideRth = false;
        m_openClose	= "O";
        m_origin = CUSTOMER;
        m_transmit = true;
        m_designatedLocation = EMPTY_STR;
        m_minQty = Integer.MAX_VALUE;
        m_percentOffset = Double.MAX_VALUE;
        m_nbboPriceCap = Double.MAX_VALUE;
        m_startingPrice = Double.MAX_VALUE;
        m_stockRefPrice = Double.MAX_VALUE;
        m_delta = Double.MAX_VALUE;
        m_stockRangeLower = Double.MAX_VALUE;
        m_stockRangeUpper = Double.MAX_VALUE;
        m_volatility = Double.MAX_VALUE;
        m_volatilityType = Integer.MAX_VALUE;
        m_deltaNeutralOrderType = EMPTY_STR;
        m_deltaNeutralAuxPrice = Double.MAX_VALUE;
        m_referencePriceType = Integer.MAX_VALUE;
        m_trailStopPrice = Double.MAX_VALUE;
        m_basisPoints = Double.MAX_VALUE;
        m_basisPointsType = Integer.MAX_VALUE;
        m_scaleNumComponents = Integer.MAX_VALUE;
        m_scaleComponentSize = Integer.MAX_VALUE;
        m_scalePriceIncrement = Double.MAX_VALUE;
        m_whatIf = false;
    }

    public boolean equals(Object p_other) {

        if ( this == p_other )
            return true;

        if ( p_other == null )
            return false;

        Order l_theOther = (Order)p_other;

        if ( m_permId == l_theOther.m_permId ) {
            return true;
        }

        if (m_orderId != l_theOther.m_orderId ||
        	m_clientId != l_theOther.m_clientId ||
        	m_totalQuantity != l_theOther.m_totalQuantity ||
        	m_lmtPrice != l_theOther.m_lmtPrice ||
        	m_auxPrice != l_theOther.m_auxPrice ||
        	m_ocaType != l_theOther.m_ocaType ||
        	m_transmit != l_theOther.m_transmit ||
        	m_parentId != l_theOther.m_parentId ||
        	m_blockOrder != l_theOther.m_blockOrder ||
        	m_sweepToFill != l_theOther.m_sweepToFill ||
        	m_displaySize != l_theOther.m_displaySize ||
        	m_triggerMethod != l_theOther.m_triggerMethod ||
        	m_outsideRth != l_theOther.m_outsideRth ||
        	m_hidden != l_theOther.m_hidden ||
        	m_overridePercentageConstraints != l_theOther.m_overridePercentageConstraints ||
        	m_allOrNone != l_theOther.m_allOrNone ||
        	m_minQty != l_theOther.m_minQty ||
        	m_percentOffset != l_theOther.m_percentOffset ||
        	m_trailStopPrice != l_theOther.m_trailStopPrice ||
        	m_origin != l_theOther.m_origin ||
        	m_shortSaleSlot != l_theOther.m_shortSaleSlot ||
        	m_discretionaryAmt != l_theOther.m_discretionaryAmt ||
        	m_eTradeOnly != l_theOther.m_eTradeOnly ||
        	m_firmQuoteOnly != l_theOther.m_firmQuoteOnly ||
        	m_nbboPriceCap != l_theOther.m_nbboPriceCap ||
        	m_auctionStrategy != l_theOther.m_auctionStrategy ||
        	m_startingPrice != l_theOther.m_startingPrice ||
        	m_stockRefPrice != l_theOther.m_stockRefPrice ||
        	m_delta != l_theOther.m_delta ||
        	m_stockRangeLower != l_theOther.m_stockRangeLower ||
        	m_stockRangeUpper != l_theOther.m_stockRangeUpper ||
        	m_volatility != l_theOther.m_volatility ||
        	m_volatilityType != l_theOther.m_volatilityType ||
        	m_continuousUpdate != l_theOther.m_continuousUpdate ||
        	m_referencePriceType != l_theOther.m_referencePriceType ||
        	m_deltaNeutralAuxPrice != l_theOther.m_deltaNeutralAuxPrice ||
        	m_basisPoints != l_theOther.m_basisPoints ||
        	m_basisPointsType != l_theOther.m_basisPointsType ||
        	m_scaleNumComponents != l_theOther.m_scaleNumComponents ||
        	m_scaleComponentSize != l_theOther.m_scaleComponentSize ||
        	m_scalePriceIncrement != l_theOther.m_scalePriceIncrement ||
        	m_whatIf != l_theOther.m_whatIf) {
        	return false;
        }
        
        if (Util.StringCompare(m_action, l_theOther.m_action) != 0 ||
        	Util.StringCompare(m_orderType, l_theOther.m_orderType) != 0 ||
        	Util.StringCompare(m_tif, l_theOther.m_tif) != 0 ||
        	Util.StringCompare(m_ocaGroup, l_theOther.m_ocaGroup) != 0 ||
        	Util.StringCompare(m_orderRef,l_theOther.m_orderRef) != 0 ||
        	Util.StringCompare(m_goodAfterTime, l_theOther.m_goodAfterTime) != 0 ||
        	Util.StringCompare(m_goodTillDate, l_theOther.m_goodTillDate) != 0 ||
        	Util.StringCompare(m_rule80A, l_theOther.m_rule80A) != 0 ||
        	Util.StringCompare(m_faGroup, l_theOther.m_faGroup) != 0 ||
        	Util.StringCompare(m_faProfile, l_theOther.m_faProfile) != 0 ||
        	Util.StringCompare(m_faMethod, l_theOther.m_faMethod) != 0 ||
        	Util.StringCompare(m_faPercentage, l_theOther.m_faPercentage) != 0 ||
        	Util.StringCompare(m_openClose, l_theOther.m_openClose) != 0 ||
        	Util.StringCompare(m_designatedLocation, l_theOther.m_designatedLocation) != 0 ||
        	Util.StringCompare(m_deltaNeutralOrderType, l_theOther.m_deltaNeutralOrderType) != 0 ||
        	Util.StringCompare(m_account, l_theOther.m_account) != 0 ||
        	Util.StringCompare(m_settlingFirm, l_theOther.m_settlingFirm) != 0 ||
        	Util.StringCompare(m_clearingAccount, l_theOther.m_clearingAccount) != 0 ||
        	Util.StringCompare(m_clearingIntent, l_theOther.m_clearingIntent) != 0) {
        	return false;
        }

        return true;
    }
}