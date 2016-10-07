/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sumzerotrading.ib;

import com.ib.client.Contract;
import com.sumzerotrading.data.CFDTicker;

/**
 *
 * @author RobTerpilowski
 *
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates and open the template
 * in the editor.
 */
/**
 *
 * @author RobTerpilowski
 */
public class CFDContractBuilder implements IContractBuilder<CFDTicker> {

    @Override
    public Contract buildContract(CFDTicker ticker) {
        Contract contract = new Contract();
        contract.m_currency = ticker.getCurrency();
        contract.m_exchange = ticker.getExchange().getExchangeName();
        contract.m_secType = IbUtils.getSecurityType(ticker.getInstrumentType());
        contract.m_symbol = ticker.getSymbol();
        if (ticker.getPrimaryExchange() != null) {
            contract.m_primaryExch = ticker.getPrimaryExchange().getExchangeName();
        }
        return contract;
    }

}
