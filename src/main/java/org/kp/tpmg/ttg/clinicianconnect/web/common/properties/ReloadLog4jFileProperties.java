package org.kp.tpmg.ttg.clinicianconnect.web.common.properties;

import java.io.IOException;

import org.apache.log4j.PropertyConfigurator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.kp.tpmg.ttg.clinicianconnect.web.exception.OncallServiceException;

public enum ReloadLog4jFileProperties implements IReloadLog4jProperties {
	INSTANCE;
	ReloadLog4jFileProperties() {
	}

	private static Logger logger = LogManager.getLogger(ReloadLog4jFileProperties.class); 
	
	private Log4jFilePropertiesChangeWatcher propertyFileChangeWatcher = null;

	public void init(String filepath) throws OncallServiceException {
		try {
			logger.info("Loading Log4j configuration and Configuring logging levels !!" + filepath);
			PropertyConfigurator.configure(filepath);
		} catch (Exception e) {
			throw new OncallServiceException(e);
		}
	}

	@Override
	public void close() throws IOException {
		if (this.propertyFileChangeWatcher != null) {
			this.propertyFileChangeWatcher.close();
		}
	}


	@Override
	public void intializeLog4jSystem(String filePath) throws OncallServiceException {
		// TODO Auto-generated method stub
		try {
			propertyFileChangeWatcher = new Log4jFilePropertiesChangeWatcher(filePath);
		} catch (IOException e) {
			throw new OncallServiceException(e);
		}
	}

}
