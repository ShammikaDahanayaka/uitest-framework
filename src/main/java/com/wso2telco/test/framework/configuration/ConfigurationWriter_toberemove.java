package com.wso2telco.test.framework.configuration;

import java.io.FileOutputStream;
import java.util.Properties;

import com.wso2telco.test.framework.util.Default;
import com.wso2telco.test.framework.util.Timeout;
import com.wso2telco.test.framework.util.Wso2TestFrameworkException;

public class ConfigurationWriter_toberemove {

	private static Properties configDataFile;
    private String path = Default.CONFIG_PATH.getValue();
    
    ConfigurationWriter_toberemove() {}

    public ConfigurationWriter_toberemove(String path) {
        this.path = path;
    }
    
       public Properties writeConfig(String key,String value)  {
    	try {
           	FileOutputStream out = new FileOutputStream(getClass().getResource(path).getFile());
        	configDataFile.setProperty(key, value);
        	configDataFile.store(out, null);
			out.close();		
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
