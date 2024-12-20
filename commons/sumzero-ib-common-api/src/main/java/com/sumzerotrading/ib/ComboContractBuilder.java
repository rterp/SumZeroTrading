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

import com.ib.client.ComboLeg;
import com.ib.client.Contract;
import com.sumzerotrading.data.ComboTicker;
import java.util.Vector;

/**
 *
 * @author Rob Terpilowski
 */
public class ComboContractBuilder implements IContractBuilder<ComboTicker> {

    public Contract buildContract(ComboTicker ticker) {
       Contract contract1 = ContractBuilderFactory.getContractBuilder(ticker.getFirstTicker()).buildContract(ticker.getFirstTicker());
        Contract contract2 = ContractBuilderFactory.getContractBuilder(ticker.getSecondTicker()).buildContract(ticker.getSecondTicker());
//        

        if (ticker.getContractId1() == 0) {
            throw new IllegalStateException("Cotract for ticker: " + ticker.getFirstTicker() + " doesn't have contract ID set for combo ticker");
        }

        if (ticker.getContractId2() == 0) {
            throw new IllegalStateException("Cotract for ticker: " + ticker.getSecondTicker() + " doesn't have contract ID set for combo ticker");
        }

        ComboLeg leg1 = new ComboLeg();
        leg1.conid(ticker.getContractId1()); 
        leg1.ratio(ticker.getFirstTickerRatio());
        leg1.action("BUY");
        leg1.exchange(ticker.getFirstTicker().getExchange().getExchangeName());
        
        
        
        ComboLeg leg2 = new ComboLeg();
        leg2.conid(ticker.getContractId2());
        leg2.ratio(ticker.getSecondTickerRatio());
        leg2.action("SELL");
        leg2.exchange(ticker.getSecondTicker().getExchange().getExchangeName());

        Contract contract = new Contract();
        Vector comboLegs = new Vector();
        comboLegs.add(leg1);
        comboLegs.add(leg2);

        contract.symbol( "USD");
        contract.secType("BAG");
        contract.currency("USD");
        contract.comboLegs(comboLegs);
        contract.exchange(contract1.exchange());

        return contract;

    }
}
