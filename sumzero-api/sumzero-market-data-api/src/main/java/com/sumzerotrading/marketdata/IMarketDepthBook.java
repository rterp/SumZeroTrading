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

package com.sumzerotrading.marketdata;

import com.sumzerotrading.marketdata.MarketDepthBook.Side;
import java.math.BigDecimal;

/**
 *
 * @author robbob
 */
public interface IMarketDepthBook {

    void addLevel(MarketDepthLevel level);

    void clearLevels();

    void deleteLevel(int index);

    BigDecimal getCumulativeSize(int level);

    MarketDepthLevel getLevelAt(int index);

    int getLevelCount();

    MarketDepthLevel[] getLevels();

    /**
     * @return the side
     */
    Side getSide();

    BigDecimal getTotalSize();

    void insertLevel(int index, MarketDepthLevel level);

    void setLevels(MarketDepthLevel[] levelArray);

    /**
     * @param side the side to set
     */
    void setSide(Side side);

    void sort();

    void updateLevel(int index, MarketDepthLevel level);
    
}
