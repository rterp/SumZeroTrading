/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sumzerotrading.intraday.trading.strategy;

import java.util.Objects;
import lombok.EqualsAndHashCode;
import lombok.Getter;


/**  
 *
 * @author RobTerpilowski
 */
public class TradeReferenceLine {
    
    public static long serialVersionUID = 1L;
    
    public enum Direction {LONG, SHORT};
    public enum Side {ENTRY, EXIT};
    
    protected String strategy;
    
    protected String correlationId;
    
    protected Direction direction;
    
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

    public String getStrategy() {
        return strategy;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public Direction getDirection() {
        return direction;
    }

    public Side getSide() {
        return side;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 11 * hash + Objects.hashCode(this.strategy);
        hash = 11 * hash + Objects.hashCode(this.correlationId);
        hash = 11 * hash + Objects.hashCode(this.direction);
        hash = 11 * hash + Objects.hashCode(this.side);
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

    @Override
    public String toString() {
        return "TradeReferenceLine{" + "strategy=" + strategy + ", correlationId=" + correlationId + ", direction=" + direction + ", side=" + side + '}';
    }
    
    
    


    
    
}
