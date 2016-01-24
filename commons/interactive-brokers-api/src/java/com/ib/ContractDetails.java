/*
 * ContractDetails.java
 *
 */
package com.ib.client;

public class ContractDetails {
    public Contract	m_summary;
    public String 	m_marketName;
    public String 	m_tradingClass;
    public double 	m_minTick;
    public int      m_priceMagnifier;
    public String 	m_orderTypes;
    public String 	m_validExchanges;

    // BOND values
    public String 	m_cusip;
    public String 	m_ratings;
    public String 	m_descAppend;
    public String 	m_bondType;
    public String 	m_couponType;
    public boolean 	m_callable			= false;
    public boolean 	m_putable			= false;
    public double 	m_coupon			= 0;
    public boolean 	m_convertible		= false;
    public String 	m_maturity;
    public String 	m_issueDate;
    public String 	m_nextOptionDate;
    public String 	m_nextOptionType;
    public boolean 	m_nextOptionPartial = false;
    public String 	m_notes;

    public ContractDetails() {
        m_summary = new Contract();
        m_minTick = 0;
    }

    public ContractDetails( Contract p_summary, String p_marketName, String p_tradingClass,
    		double p_minTick, String p_orderTypes, String p_validExchanges) {
        m_summary = p_summary;
    	m_marketName = p_marketName;
    	m_tradingClass = p_tradingClass;
    	m_minTick = p_minTick;
    	m_orderTypes = p_orderTypes;
    	m_validExchanges = p_validExchanges;
    }
}