/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.limituptrading.time;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author RobTerpilowski
 */
public class TimeProvider implements ITimeProvider {

    protected List<TimeUpdatedListener> listeners = new ArrayList<>();
    protected Timer timer = new Timer("Time Provider timer", true);
    
    
    
    @Override
    public void start() {
       // timer.schedule(this, 0, 1000);
    }

    @Override
    public void stop() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    
    
    @Override
    public void addTimeUpdatedListener(TimeUpdatedListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeTimeUpdatedListener(TimeUpdatedListener listener) {
        listeners.remove(listener);
    }
 
    
    protected void fireTimerEvent() {
        for( TimeUpdatedListener listener : listeners ) {
            listener.timeUpdated(LocalDateTime.now() );
        }
    }
    
    
}
