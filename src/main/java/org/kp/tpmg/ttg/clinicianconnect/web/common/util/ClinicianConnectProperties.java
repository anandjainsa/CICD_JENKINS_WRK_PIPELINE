package org.kp.tpmg.ttg.clinicianconnect.web.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



public class ClinicianConnectProperties {

	private static Logger log = LogManager.getLogger(ClinicianConnectProperties.class);
	
	private ClinicianConnectProperties(){
	}
	
	public static FileInputStream readFile(String resourcesPath) {

		File file = new File(resourcesPath);
		FileInputStream fileInput = null;
		try {
			fileInput = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			log.error("ERROR in readFile in ClinicianConnectProperties is: " + e.getMessage(), e);
		}
		return fileInput;
	}

//	public static String getProperty(String propertyName) {
//		String propertyValue = null;
//		try {
//			propertyValue = AppProperties.getInstance().getApplicationProperty().getProperty(propertyName);
//			System.out.println("propertyValue---"+propertyValue);
//		} catch (Exception e) {
//			log.error("ERROR in getProperty in ClinicianConnectProperties is: " + e.getMessage(), e);
//		}
//		return propertyValue;
//	}

//	public static String getEndPointURL(String propertyName) throws Exception {
//		String ttg_provider_service_endPointUrl = null;
//		try {
//			ttg_provider_service_endPointUrl = AppProperties.getInstance().getApplicationProperty().getProperty(propertyName);
//			System.out.println("propertyValue---"+ttg_provider_service_endPointUrl);
//
//		} catch (Exception e) {
//			log.error("ERROR in getEndPointURL in ClinicianConnectProperties is: " + e.getMessage());
//			throw new Exception(e);
//		}
//		return ttg_provider_service_endPointUrl;
//	}
	
	public static Properties getDataSourceConfig() {
		Properties props=new Properties();
		
		try {
			
			String filePath="C://Users/bchandan/Desktop/springboot/conf.properties";
			//String filePath=OncallServiceConstants.DB_CONFIG_LOCATION;
			InputStream input=new FileInputStream(filePath);
			props.load(input);
		} catch (IOException e) {
			log.error("error in reading file"+e.getMessage(), e);
		}
		return props;
	}

	
}
