package org.kp.tpmg.ttg.clinicianconnect.web.common.properties;

import org.kp.tpmg.ttg.clinicianconnect.web.exception.OncallServiceException;

public class Log4jApplicationProperties {
	
	private Log4jApplicationProperties() {
		super();
	}
	public static IReloadLog4jProperties getLog4jApplicationPropertiesInstance() throws OncallServiceException {
		return ReloadLog4jFileProperties.INSTANCE;
	}
	public static void initializeLog4jSystem(String filePath) throws OncallServiceException {
		//Initialize log4jSystem
		ReloadLog4jFileProperties.INSTANCE.init(filePath);
		//Start the daemon thread - watch Service for log4j properties file
		ReloadLog4jFileProperties.INSTANCE.intializeLog4jSystem(filePath);
	}
}
