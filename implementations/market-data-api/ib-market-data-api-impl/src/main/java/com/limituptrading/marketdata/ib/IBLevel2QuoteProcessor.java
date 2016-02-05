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


package com.sumzerotrading.marketdata.ib;

import com.sumzerotrading.data.Ticker;
import com.sumzerotrading.marketdata.*;
import com.sumzerotrading.util.QuoteUtil;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.BlockingQueue;

/**
 *
 * @author Rob Terpilowski
 */
public class IBLevel2QuoteProcessor extends IBQuoteProcessor<Level2QuoteData> {

    protected HashMap<Ticker, IMarketDepthBook> bidBookMap = new HashMap<Ticker, IMarketDepthBook>();
    protected HashMap<Ticker, IMarketDepthBook> askBookMap = new HashMap<Ticker, IMarketDepthBook>();
    protected int contractId = 0;

    protected int OP_INSERT = 0;
    protected int OP_UPDATE = 1;
    protected int OP_DELETE = 2;
    protected int SIDE_BID = 1;
    protected int SIDE_ASK = 0;

    public IBLevel2QuoteProcessor(BlockingQueue<Level2QuoteData> queue, IQuoteEngine quoteEngine) {
        super(queue, quoteEngine);
    }

    @Override
    protected void processData(Level2QuoteData data) {
        double price = data.getPrice();
        Ticker ticker = data.getTicker();
        int operation = data.getOperation();
        int side = data.getSide();
        int position = data.getPosition();
        int size = data.getSize();
        IMarketDepthBook book;
        MarketDepthBook.Side depthSide;
        

        if (side == SIDE_BID) {
            book = bidBookMap.get(data.getTicker());
            depthSide = MarketDepthBook.Side.BID;
            if (book == null) {
                book = MarketDepthBook.newInstance();
                book.setSide(MarketDepthBook.Side.BID);
                bidBookMap.put(ticker, book);
            }
        } else {
            book = askBookMap.get(data.getTicker());
            depthSide = MarketDepthBook.Side.ASK;
            if (book == null) {
                book = MarketDepthBook.newInstance();
                book.setSide(MarketDepthBook.Side.ASK);
                askBookMap.put(ticker, book);
            }
        }


         BigDecimal bdPrice = QuoteUtil.getBigDecimalValue(ticker, price);

        MarketDepthLevel level = new MarketDepthLevel(depthSide, bdPrice, size);
        if( operation == OP_INSERT ) {
            book.insertLevel(position, level);
        } else if ( operation == OP_UPDATE ) {
            book.updateLevel( position, level );
        } else if( operation == OP_DELETE ) {
            book.deleteLevel(position);
        } else {
            throw new IllegalStateException( "Unknown operation: " + operation );
        }
        buildAndFireEvent(ticker, book);
        
    }
    
    
    public void removeTicker( Ticker ticker ) {
        bidBookMap.remove(ticker);
        askBookMap.remove(ticker);
    }
    
    
    protected void buildAndFireEvent(Ticker ticker, IMarketDepthBook book) {
        QuoteType type;
        if( book.getSide() == MarketDepthBook.Side.ASK ) {
            type = QuoteType.MARKET_DEPTH_ASK;
        } else {
            type = QuoteType.MARKET_DEPTH_BID;
        }
        quoteEngine.fireMarketDepthQuote(new Level2Quote(ticker, type, getDate(), book));
    }    
    
    
    /**
     * Overridden by unit tests.
     * @return 
     */
    protected Date getDate() {
        return new Date();
    }
//
//    protected void processDataOld(Level2QuoteData data) {
//        double price = data.getPrice();
//        Ticker ticker = data.getTicker();
//        int operation = data.getOperation();
//        int side = data.getSide();
//        int position = data.getPosition();
//        int size = data.getSize();
//
//
//        BigDecimal bdPrice = new BigDecimal(price).setScale(5, RoundingMode.HALF_UP);
//        BigDecimal bdSize = new BigDecimal(size).setScale(0, RoundingMode.HALF_UP);
//
//        //logger.info( "Market Depth: position: " +  position + " operation: " + operation + " side: " + side + " price: " + price + " size:" + size );
//        MarketDepthBook.Side depthSide;
//        if (side == 0) {
//            depthSide = MarketDepthBook.Side.ASK;
//        } else {
//            depthSide = MarketDepthBook.Side.BID;
//        }
//        MarketDepthLevel level = new MarketDepthLevel(depthSide, bdPrice, size);
//        Integer lastSide = lastSideMap.get(ticker);
//        Integer lastOperation = lastOperationMap.get(ticker);
//        MarketDepthLevel lastLevel = lastLevelMap.get(ticker);
//
//
//        if (lastSide != null && lastOperation != null && lastLevel != null) {
//            if (side == lastSide.intValue() && (level.equals(lastLevel)) && operation == lastOperation.intValue()) {
//                lastLevelMap.put(ticker, level);
//                lastSideMap.put(ticker, side);
//                lastOperationMap.put(ticker, operation);
//                return;
//            }
//        }
//
//        ArrayList<MarketDepthLevel> askList = askListMap.get(ticker);
//        if (askList == null) {
//            askList = new ArrayList<MarketDepthLevel>();
//            askListMap.put(ticker, askList);
//        }
//
//        ArrayList<MarketDepthLevel> bidList = bidListMap.get(ticker);
//        if (bidList == null) {
//            bidList = new ArrayList<MarketDepthLevel>();
//            bidListMap.put(ticker, bidList);
//        }
//
//        //logger.info( "Market Depth: position: " +  position + " operation: " + operation + " side: " + side + " price: " + price + " size:" + size );
//        if (side == 0) {
//            if (operation == 0) {
//                while (askList.size() < position) {
//                    askList.add(new MarketDepthLevel(MarketDepthBook.Side.ASK, BigDecimal.ZERO, 0));
//                }
//                askList.add(position, new MarketDepthLevel(MarketDepthBook.Side.ASK, bdPrice, size));
//            } else if (operation == 1) {
//                if (askList.size() > position) {
//                    askList.get(position).setPrice(bdPrice);
//                    askList.get(position).setSize(bdSize);
//                }
//            } else if (operation == 2) {
//                if (askList.size() > position) {
//                    askList.remove(position);
//                }
//            }
//            MarketDepthBook book = new MarketDepthBook();
//            book.setLevels(askList.toArray(new MarketDepthLevel[0]));
//            book.setSide(MarketDepthBook.Side.ASK);
//            buildAndFireEvent(ticker, book, QuoteType.MARKET_DEPTH_ASK);
//            //writeAskDepth( askList, tickerId );
//        } else {
//
//            if (operation == 0) {
//                while (bidList.size() < position) {
//                    bidList.add(new MarketDepthLevel(MarketDepthBook.Side.BID, BigDecimal.ZERO, 0));
//                }
//                bidList.add(position, new MarketDepthLevel(MarketDepthBook.Side.BID, bdPrice, size));
//            } else if (operation == 1 && bidList.size() > position) {
//                bidList.get(position).setPrice(bdPrice);
//                bidList.get(position).setSize(bdSize);
//            } else if (operation == 2 && bidList.size() > position) {
//                bidList.remove(position);
//            }
//            MarketDepthBook book = new MarketDepthBook();
//            book.setLevels(bidList.toArray(new MarketDepthLevel[0]));
//            book.setSide(MarketDepthBook.Side.BID);
//
//            buildAndFireEvent(ticker, book, QuoteType.MARKET_DEPTH_BID);
//
//        }
//        lastLevelMap.put(ticker, level);
//        lastSideMap.put(ticker, side);
//        lastOperationMap.put(ticker, operation);
//
//
//    }


}
