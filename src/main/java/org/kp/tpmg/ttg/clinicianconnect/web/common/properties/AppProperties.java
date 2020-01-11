package org.kp.tpmg.ttg.clinicianconnect.web.common.properties;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.kp.tpmg.ttg.clinicianconnect.web.common.util.OncallServiceConstants;
import org.kp.tpmg.ttg.common.property.ApplicationProperty;
import org.kp.tpmg.ttg.common.property.ApplicationPropertyException;
import org.kp.tpmg.ttg.common.property.IApplicationProperties;

public class AppProperties {
	
	private static Logger logger = Logger.getLogger(AppProperties.class);
	
	private static AppProperties instance;
	private static IApplicationProperties properties = null;

	public static final String PROPERTY_FILE_PATH = OncallServiceConstants.RESOURCES_LOCATION+OncallServiceConstants.ONCALL_SERVICE_PROPERTIES_FILE;	

	public static AppProperties getInstance() {
		synchronized (AppProperties.class) {
			if (instance == null) {
				instance = new AppProperties();
			}
		}
		return instance;
	}

	public static String getExtPropertiesValueByKey(String strKey) {
		String keyValue = "";
		try {
			keyValue = getInstance().getApplicationProperty().getProperty(strKey);
		} catch (Exception ex) {
			logger.error("Error in getting external property value by key: " + strKey, ex);
		}
		return keyValue;
	}

	public IApplicationProperties getApplicationProperty() {
		return AppPropertiesInitializer.getApplicationProperties();
	}

	public static class AppPropertiesInitializer {
		
		private AppPropertiesInitializer() {
		}

		public static IApplicationProperties getApplicationProperties() {
			if (properties == null) {
				try {
					final List<String> propertyFiles = new ArrayList<String>();
					propertyFiles.add(PROPERTY_FILE_PATH);
					properties = ApplicationProperty.getFilePropertyInstance(propertyFiles);
					System.out.println("properties "+properties);
				} catch (ApplicationPropertyException ex) {
					logger.error("Error loading property files" + ex.getMessage(), ex);
				}
			}
			return properties;
		}
	}
	
	public static String getProperty(String propertyName) {
		String propertyValue = null;
		try {
			propertyValue = getInstance().getApplicationProperty().getProperty(propertyName);
		} catch (Exception e) {
			logger.error("ERROR while getting property values for " + propertyName + " in ClinicianConnectProperties is: ",e);
		}
		return propertyValue;
	}
}
