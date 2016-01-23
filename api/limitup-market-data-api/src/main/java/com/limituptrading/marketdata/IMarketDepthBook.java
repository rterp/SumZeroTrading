/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.limituptrading.marketdata;

import com.limituptrading.marketdata.MarketDepthBook.Side;
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
