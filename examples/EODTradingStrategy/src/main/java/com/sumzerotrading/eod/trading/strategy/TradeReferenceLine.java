/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sumzerotrading.eod.trading.strategy;

import lombok.Getter;


/**  
 *
 * @author RobTerpilowski
 */
public class TradeReferenceLine {
    //longExitOrder.setReferenceString("EOD-Pair-Strategy:" + correlationId + ":Exit:LongSide*");
    
    public enum Direction {LONG, SHORT};
    public enum Side {ENTRY, EXIT};
    
    @Getter
    protected String strategy;
    
    @Getter
    protected String correlationId;
    
    @Getter
    protected Direction direction;
    
    @Getter
    protected Side side;

    
    public void parse( String referenceLine ) {
        String[] mainTokens = referenceLine.split("\\*");
        String[] tokens = mainTokens[0].split(":");
        strategy = tokens[0];
        correlationId = tokens[1];
        side = parseSide(tokens[2]);
        direction = parseDirection(tokens[3]);
        
    }
    
    
    protected Side parseSide( String side ) {
        if( "Exit".equalsIgnoreCase(side)) {
            return Side.EXIT;
        } else if( "Entry".equalsIgnoreCase(side)) {
            return Side.ENTRY;
        } else {
            throw new IllegalStateException( "Unknown Side: " + side );
        }
    }
    
    protected Direction parseDirection( String dir ) {
        if( "Long".equalsIgnoreCase(dir) ) {
            return Direction.LONG;
        } else if( "Short".equalsIgnoreCase(dir) ) {
            return Direction.SHORT;
        } else {
            throw new IllegalStateException( "Unknown Direction: " + dir );
        }
    }
    
    
}
