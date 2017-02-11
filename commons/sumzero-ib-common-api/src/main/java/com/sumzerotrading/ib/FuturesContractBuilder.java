/**
 * MIT License

Copyright (c) 2015  Rob Terpilowski

Permission is hereby granted, free of charge, to any person obtaining a copy of this software 
and associated documentation files (the "Software"), to deal in the Software without restriction, 
including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, 
and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, 
subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING 
BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. 
IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, 
WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE 
OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */


package com.sumzerotrading.ib;

import com.ib.client.Contract;
import com.sumzerotrading.data.FuturesTicker;
import com.sumzerotrading.ib.symbol.ILocalSymbolBuilder;
import com.sumzerotrading.ib.symbol.LocalSymbolBuilderFactory;
import com.sumzerotrading.util.FuturesUtil;
import java.math.BigDecimal;

/**
 *
 * @author Rob Terpilowski
 */
public class FuturesContractBuilder implements IContractBuilder<FuturesTicker> {

    public Contract buildContract(FuturesTicker ticker) {
        Contract contract = new Contract();
        contract.m_currency = ticker.getCurrency();
        contract.m_exchange = ticker.getExchange().getExchangeName();
        contract.m_secType = IbUtils.getSecurityType(ticker.getInstrumentType());
        contract.m_symbol = IbUtils.translateToIbFuturesSymbol( ticker.getSymbol() );
        contract.m_expiry = IbUtils.getExpiryString(ticker.getExpiryMonth(), ticker.getExpiryYear());
        
        ILocalSymbolBuilder localSymbolBuilder = LocalSymbolBuilderFactory.getLocalSymbolBuilder(ticker.getExchange());
        
        contract.m_localSymbol = localSymbolBuilder.buildLocalSymbol(ticker.getSymbol(), ticker.getExpiryMonth(), ticker.getExpiryYear());
        
        BigDecimal multiplier = IbUtils.getIbMultiplier(ticker);
        if (multiplier != null) {
            contract.m_multiplier = multiplier.toString();
        }

        return contract;

    }
    
    
    


}
