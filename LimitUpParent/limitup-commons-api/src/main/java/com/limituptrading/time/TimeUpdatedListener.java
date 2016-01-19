/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.limituptrading.time;

import java.time.LocalDateTime;

/**
 *
 * @author RobTerpilowski
 */
public interface TimeUpdatedListener {
    
    public void timeUpdated( LocalDateTime localDateTime );
    
}
