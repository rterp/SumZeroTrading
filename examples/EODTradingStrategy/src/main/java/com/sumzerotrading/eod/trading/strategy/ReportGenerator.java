/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sumzerotrading.eod.trading.strategy;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sumzerotrading.broker.order.OrderEvent;
import com.sumzerotrading.broker.order.OrderEventListener;
import com.sumzerotrading.broker.order.OrderStatus.Status;
import com.sumzerotrading.broker.order.TradeOrder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author RobTerpilowski
 */
public class ReportGenerator implements OrderEventListener {

    protected Logger logger = LoggerFactory.getLogger(ReportGenerator.class);
    protected Map<String, RoundTrip> roundTripMap = new HashMap<>();
    protected String outputFile;
    protected String outputDir;
    protected String partialDir;
    protected ObjectMapper mapper = new ObjectMapper();
    

    //For unit tests
    protected ReportGenerator() {
        configureMapper();
    }

    public ReportGenerator(String dir) {
        configureMapper();
        StringBuilder sb = new StringBuilder();
        sb.append(dir);
        if( ! dir.endsWith("/") ) {
            sb.append("/");
        }
        
        this.outputDir = sb.toString();
        partialDir = sb.toString() + "partial/";
        
        try {
            Files.createDirectories(Paths.get(outputDir));
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        }
        
        try {
            Files.createDirectories(Paths.get(partialDir));
        } catch( IOException ex ) {
            throw new IllegalStateException(ex);
        }
        
        sb.append("report.csv");
        outputFile = sb.toString();
        
        logger.info( "Created new Report Generator with output dir: " + outputDir );
        logger.info( "...and partialDir: " + partialDir);
        
    }
    
    
    
    public void loadPartialRoundTrips() throws IOException {
        File[] files = new File(partialDir).listFiles();
        for( File file : files ) {
            ObjectInputStream input = new ObjectInputStream(new FileInputStream(file));
            RoundTrip trip;
            try {
                trip = (RoundTrip) input.readObject();
                roundTripMap.put(trip.getCorrelationId(), trip);
            } catch (ClassNotFoundException ex) {
                throw new IllegalStateException(ex);
            }
            input.close();
            
        }
    }
    
    public void savePartial(String correlationId, RoundTrip trip ) throws IOException {
        String filename = partialDir + correlationId + ".ser";
        ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(filename));
        output.writeObject(trip);
        output.close();
    }
    
    public void deletePartial(String correlationId) throws IOException {
        String filename = partialDir + correlationId + ".ser";
        Files.deleteIfExists(Paths.get(filename));
    }

    
    @Override
    public void orderEvent(OrderEvent event) {
        TradeOrder order = event.getOrder();
        if (order.getCurrentStatus() == Status.FILLED) {
            TradeReferenceLine line = getTradeReferenceLine(order.getReference());
            RoundTrip roundTrip = roundTripMap.get(line.getCorrelationId());
            if (roundTrip == null) {
                roundTrip = new RoundTrip();
                roundTripMap.put(line.getCorrelationId(), roundTrip);
            }
            roundTrip.addTradeReference(order, line);
            if (roundTrip.isComplete()) {
                writeRoundTripToFile(roundTrip);
                roundTripMap.remove(line.getCorrelationId());
            } else {
                throw new IllegalStateException("Write this transaction to a temp file");
            }
        }
    }
    
    protected final void configureMapper() {
        mapper.setVisibilityChecker(mapper.getSerializationConfig().getDefaultVisibilityChecker()
                .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withCreatorVisibility(JsonAutoDetect.Visibility.NONE));
    }

    protected synchronized void writeRoundTripToFile(RoundTrip roundTrip) {
        String resultString = roundTrip.getResults() + "\n";
        try {
            Files.write(Paths.get(outputFile), resultString.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

    }

    protected TradeReferenceLine getTradeReferenceLine(String string) {
        TradeReferenceLine line = new TradeReferenceLine();
        line.parse(string);
        return line;
    }

}
