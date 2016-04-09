/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sumzerotrading.ib;

import com.ib.client.Contract;

/**
 *
 * @author RobTerpilowski
 */
public class ContractWrapper {
 
    protected Contract contract;
    
    public ContractWrapper(Contract contract) {
        this.contract = contract;
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("contractId: ")
        .append( contract.m_conId)
                .append(" currency: ")
                .append(contract.m_currency)
                .append(" expiry: ")
                .append(contract.m_expiry)
                .append(" include expiry: ")
                .append(contract.m_includeExpired)
                .append(" local symbol: ")
                .append(contract.m_localSymbol)
                .append(" multiplier: ")
                .append(contract.m_multiplier)
                .append(" primary exchange: ")
                .append(contract.m_primaryExch)
                .append(" right: ")
                .append(contract.m_right)
                .append(" sec id: ")
                .append(contract.m_secId)
                .append(" sec id type: ")
                .append(contract.m_secIdType)
                .append(" strike: ")
                .append(contract.m_strike)
                .append(" symbol: ")
                .append(contract.m_symbol)
                .append(" trading class: ")
                .append(contract.m_tradingClass)
                .append(" under comp: ")
                .append(contract.m_underComp);
        
        return sb.toString();
    }
}
