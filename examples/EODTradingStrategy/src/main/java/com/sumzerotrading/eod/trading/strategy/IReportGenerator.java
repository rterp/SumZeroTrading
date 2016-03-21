/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sumzerotrading.eod.trading.strategy;

import com.sumzerotrading.broker.order.OrderEventListener;
import java.io.IOException;

/**
 *
 * @author RobTerpilowski
 */
public interface IReportGenerator extends OrderEventListener {

    void deletePartial(String correlationId) throws IOException;

    void loadPartialRoundTrips() throws IOException;

    void savePartial(String correlationId, RoundTrip trip) throws IOException;
    
}
