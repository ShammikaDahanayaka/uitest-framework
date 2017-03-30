package com.wso2telco.test.framework.configuration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.omg.CORBA.portable.OutputStream;

import com.wso2telco.test.framework.util.Default;
import com.wso2telco.test.framework.util.Timeout;
import com.wso2telco.test.framework.util.Wso2TestFrameworkException;

public class ConfigurationReader {
	
	private static Properties configDataFile;
    private String path = Default.CONFIG_PATH.getValue();
    
    ConfigurationReader() {}

    public ConfigurationReader(String path) {
        this.path = path;
    }
    
       public Properties readConfig() {
        InputStream istream = getClass().getResourceAsStream(path);
      
        if (null == istream) {
            try {
                istream = new FileInputStream(path);
            } catch (FileNotFoundException e) {}
            if (null == istream)
                throw new Wso2TestFrameworkException("The path " + path + "not exist");
        }
        configDataFile = new java.util.Properties(getDefaultProperties());
        try {
        	configDataFile.load(istream);
            istream.close();
        } catch (Exception ex) {
            throw new Wso2TestFrameworkException("Cannot load data" +path );
        }
      
        return configDataFile;
    }
    
    private Properties getDefaultProperties() {
        Properties def = new Properties();
        def.setProperty(ConfigurationKeys.BROWSER.getCongfigKey(), String.valueOf(Default.DRIVER_BROWSER));
        def.setProperty(ConfigurationKeys.IMPLICIT_WAIT.getCongfigKey(), String.valueOf(Timeout.IMPLICIT_WAIT));
        // TODO 
		return def;
       
       
    }
    
}
