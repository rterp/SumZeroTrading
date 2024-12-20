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
        .append( contract.conid())
                .append(" currency: ")
                .append(contract.currency())
                .append(" expiry: ")
                .append(contract.lastTradeDateOrContractMonth())
                .append(" include expiry: ")
                .append(contract.includeExpired())
                .append(" local symbol: ")
                .append(contract.localSymbol())
                .append(" multiplier: ")
                .append(contract.multiplier())
                .append(" primary exchange: ")
                .append(contract.primaryExch())
                .append(" right: ")
                .append(contract.right())
                .append(" sec id: ")
                .append(contract.secId())
                .append(" sec id type: ")
                .append(contract.secIdType())
                .append(" strike: ")
                .append(contract.strike())
                .append(" symbol: ")
                .append(contract.symbol())
                .append(" trading class: ")
                .append(contract.tradingClass());
        
        return sb.toString();
    }
}
