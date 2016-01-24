/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.limituptrading.ib;

/**
 *
 * @author robbob
 */
public interface IBQuoteCallback {

    void tickPrice(int tickerId, int field, double price, int canAutoExecute);

    void tickSize(int tickerId, int field, int size);

    void tickGeneric(int tickerId, int tickType, double value);

    void tickString(int tickerId, int tickType, String value);

}
