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


package com.limituptrading.ib;

import com.limituptrading.data.*;

/**
 *
 * @author Rob Terpilowski
 */
public class ContractBuilderFactory {
    
    
    
    public static IContractBuilder getContractBuilder( Ticker ticker ) {
        if( ticker instanceof StockTicker ) {
            return new StockContractBuilder();
        } else if( ticker instanceof CurrencyTicker ) {
            return new CurrencyContractBuilder();
        } else if( ticker instanceof FuturesTicker ) {
            return new FuturesContractBuilder();
        } else if( ticker instanceof ComboTicker ) {
            return new ComboContractBuilder();
        } else if( ticker instanceof IndexTicker ) {
            return new IndexContractBuilder();
        } else {
            throw new IllegalStateException( "Unsupported ticker type: " + ticker.getClass() );
        }
    }
}
