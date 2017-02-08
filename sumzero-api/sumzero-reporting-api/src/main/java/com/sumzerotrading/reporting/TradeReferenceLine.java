/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sumzerotrading.reporting;

import java.util.Objects;


/**  
 *
 * @author RobTerpilowski
 */
public class TradeReferenceLine {
    //longExitOrder.setReferenceString("EOD-Pair-Strategy:" + correlationId + ":Exit:LongSide*");
    
    public static final long serialVersionUID = 1L;
    
    public enum Direction {LONG, SHORT};
    public enum Side {ENTRY, EXIT};
    
    protected String strategy;
    
    protected String correlationId;
    
    protected Direction direction;
    
    protected Side side;

    
    public static TradeReferenceLine create( String strategy, Direction direction, Side side ) {
        TradeReferenceLine line = new TradeReferenceLine();
        line.setStrategy(strategy).setDirection(direction).setSide(side);
        
        
        return line;
    }
    
    
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

    public String getStrategy() {
        return strategy;
    }

    public TradeReferenceLine setStrategy(String strategy) {
        this.strategy = strategy;
        return this;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public TradeReferenceLine setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
        return this;
    }

    public Direction getDirection() {
        return direction;
    }

    public TradeReferenceLine setDirection(Direction direction) {
        this.direction = direction;
        return this;
    }

    public Side getSide() {
        return side;
    }

    public TradeReferenceLine setSide(Side side) {
        this.side = side;
        return this;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + Objects.hashCode(this.strategy);
        hash = 17 * hash + Objects.hashCode(this.correlationId);
        hash = 17 * hash + Objects.hashCode(this.direction);
        hash = 17 * hash + Objects.hashCode(this.side);
        return hash;
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
        final TradeReferenceLine other = (TradeReferenceLine) obj;
        if (!Objects.equals(this.strategy, other.strategy)) {
            return false;
        }
        if (!Objects.equals(this.correlationId, other.correlationId)) {
            return false;
        }
        if (this.direction != other.direction) {
            return false;
        }
        if (this.side != other.side) {
            return false;
        }
        return true;
    }

    //longExitOrder.setReferenceString("EOD-Pair-Strategy:" + correlationId + ":Exit:LongSide*");
    @Override
    public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append(strategy).append(":").append(correlationId).append(":").append(side).append(":").append(direction).append("*");
      return sb.toString();
    }
    
    
    
    
    
    
}
