
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author RobTerpilowski
 */
public class EODSystemProperties {
    

    protected LocalTime startTime;
    protected String twsHost;
    protected int twsPort;
    protected int twsClientId;
    protected int tradeSizeDollars;
    protected Map<String,String> longShortTickerMap = new HashMap<>();
    
    
    public EODSystemProperties(String fileName) throws IOException {
        this( new FileInputStream(fileName) );
        
    }
    
    public EODSystemProperties(InputStream inputStream) throws IOException {
        Properties props = new Properties();
        props.load(inputStream);
        parseProps(props);
    }


    protected void parseProps( Properties props ) {
        twsHost = props.getProperty("tws.host");
        twsPort = Integer.parseInt(props.getProperty("tws.port"));
        twsClientId = Integer.parseInt(props.getProperty("tws.client.id"));
        tradeSizeDollars = Integer.parseInt(props.getProperty("trade.size.dollars"));
        startTime = LocalTime.parse(props.getProperty("start.time"));
        longShortTickerMap = getLongShortTickers(props);
    }

    
    protected Map<String,String> getLongShortTickers( Properties props ) {
        Map<String,String> map = new HashMap<>();
        for( Object key : props.keySet()) {
            String keyString = (String) key;
            if( keyString.startsWith("pair") ) {
                String[] tickers = props.getProperty(keyString).split(":");
                map.put(tickers[0], tickers[1]);
            }
        }
        
        return map;
    }
   
    
    
    
}
