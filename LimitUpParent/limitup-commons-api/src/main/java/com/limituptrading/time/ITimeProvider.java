/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.limituptrading.time;

/**
 *
 * @author RobTerpilowski
 */
public interface ITimeProvider {

    
    /**
     * Starts the specified time provider
     */
    public void start();
    
    /**
     * Stops the specified time provider
     */
    public void stop();
    
    /**
     * Adds the specified time update listener
     *
     * @param listener The listener to add.
     */
    public void addTimeUpdatedListener( TimeUpdatedListener listener );
    
    /**
     * Removes the specified time update listener 
     *
     * @param listener The listener to remove.
     */
    public void removeTimeUpdatedListener( TimeUpdatedListener listener );
}
