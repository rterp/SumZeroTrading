package com.limituptrading.marketdata;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;

public class MarketDepthBook implements IMarketDepthBook {

    public static enum Side { BID, ASK };
    protected static IMarketDepthBook testBook;
    protected ArrayList<MarketDepthLevel> levels = new ArrayList<MarketDepthLevel>();
    protected Side side;

    
    
    public static IMarketDepthBook newInstance() {
        if( testBook != null ) {
            return testBook;
        } else {
            return new MarketDepthBook();
        }
    }
    
    
    public static void setTestInstance( IMarketDepthBook book ) {
        testBook = book;
    }
    
    protected MarketDepthBook() {
        
    }
    
    /**
     * @return the side
     */
    @Override
    public Side getSide() {
        return side;
    }

    
    /**
     * @param side the side to set
     */
    @Override
    public void setSide(Side side) {
        this.side = side;
    }

    
    @Override
    public void clearLevels() {
        levels.clear();
    }

    
    @Override
    public MarketDepthLevel[] getLevels() {
        return levels.toArray(new MarketDepthLevel[]{});
    }

    
    @Override
    public int getLevelCount() {
        return levels.size();
    }

    @Override
    public MarketDepthLevel getLevelAt(int index) {
        return levels.get(index);
    }

    @Override
    public void addLevel(MarketDepthLevel level) {
        levels.add(level);
    }
    
    @Override
    public void insertLevel(int index, MarketDepthLevel level ) {
        if( levels.size() >= index ) {
            levels.add(index, level);
        } 
    }
    
    @Override
    public void updateLevel(int index, MarketDepthLevel level ) {
        if( levels.size() > index ) {
            levels.add(index, level);
            levels.remove(index+1);
        }
    }
    
    @Override
    public void deleteLevel( int index ) {
        if( levels.size() > index ) {
            levels.remove(index);
        }
    }

    @Override
    public void setLevels(MarketDepthLevel[] levelArray) {
        for (MarketDepthLevel level : levelArray) {
            levels.add(level);
        }
    }

    @Override
    public BigDecimal getTotalSize() {
        BigDecimal size = BigDecimal.ZERO;
        for (MarketDepthLevel level : levels) {
            size = size.add(level.getSize()).setScale(level.getSize().scale(), RoundingMode.HALF_UP);
        }
        return size;
    }

    @Override
    public BigDecimal getCumulativeSize(int level) {
        BigDecimal size = BigDecimal.ZERO;
        for (int i = 0; i <= level && i < levels.size(); i++) {
            size = size.add(levels.get(i).getSize()).setScale(levels.get(i).getSize().scale(), RoundingMode.HALF_UP);
        }
        return size;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((levels == null) ? 0 : levels.hashCode());
        result = prime * result + ((side == null) ? 0 : side.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        MarketDepthBook other = (MarketDepthBook) obj;
        if (levels == null) {
            if (other.levels != null) {
                return false;
            }
        } else if (!levels.equals(other.levels)) {
            return false;
        }
        if (side == null) {
            if (other.side != null) {
                return false;
            }
        } else if (!side.equals(other.side)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MarketDepthBook [levels=" + levels + ", side=" + side + "]";
    }

    @Override
    public void sort() {
        Collections.sort(levels);
    }
}
