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
 * Defines functionality for a Level 2 order book.
 * 
 * @author Rob Terpilowski
 */
public interface IMarketDepthBook {

    /**
     * Adds a level to the order book.
     * 
     * @param level The level to add to the book.
     */
    public void addLevel(MarketDepthLevel level);

    /**
     * Clears all levels from the book
     * 
     */
    public void clearLevels();

    /**
     * Deletes a level at the specified index from the book.
     * 
     * @param index The index of the level to delete.
     */
    public void deleteLevel(int index);
    

    /**
     * Gets the cumulative size of the book
     * 
     * @param level The level to obtain the cumulative size up to.
     * @return The cumulative size of the book up to the specified level
     */
    public BigDecimal getCumulativeSize(int level);

    /**
     * Gets the level at the specified index
     * 
     * @param index The index to obtain the level for
     * @return The level for the specified index
     */
    public MarketDepthLevel getLevelAt(int index);

    /**
     * Gets the total number of levels in the book.
     * 
     * @return The total number of levels in the order book
     */
    public int getLevelCount();

    
    /**
     * Gets all the levels of the book as an array.
     * 
     * @return All levels of this book.
     */
    public MarketDepthLevel[] getLevels();

    /**
     * Returns the side of this book Bid or Ask
     * 
     * @return the side of this order book.
     */
    public Side getSide();

    /**
     * Get the total size of this order book.
     * 
     * @return The total size of the order book
     */
    public BigDecimal getTotalSize();

    
    /**
     * Inserts the specified level at the specified index
     * 
     * @param index The index to insert the level at
     * @param level The level to insert
     */
    public void insertLevel(int index, MarketDepthLevel level);

    /**
     * Set all levels of this order book
     * @param levelArray The array of levels to set
     */
    public void setLevels(MarketDepthLevel[] levelArray);

    /**
     * Set the side of this order book, Bid or Ask
     
     * @param side the side to set
     */
    public void setSide(Side side);

    /**
     * Sort the book
     */
    public void sort();

    /**
     * Updates the specified level at the specified index
     * @param index The index to update
     * @param level The level to place at the specified index.
     */
    public void updateLevel(int index, MarketDepthLevel level);
    
}
