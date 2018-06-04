/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sumzerotrading.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The current.date property can be used in log4j.properties file to create a log file name 
 * containing the current date.
 * 
 * @author RobTerpilowski
 */
public class LoggingFilenameProperties {

    static {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss");
        System.setProperty("current.date", dateFormat.format(new Date()));
    }
}
