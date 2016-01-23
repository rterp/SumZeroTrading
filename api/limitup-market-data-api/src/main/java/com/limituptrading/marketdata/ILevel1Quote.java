/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.limituptrading.marketdata;

import java.math.BigDecimal;

/**
 *
 * @author robbob
 */
public interface ILevel1Quote extends IQuote {
    
    
    public BigDecimal getValue();
}
