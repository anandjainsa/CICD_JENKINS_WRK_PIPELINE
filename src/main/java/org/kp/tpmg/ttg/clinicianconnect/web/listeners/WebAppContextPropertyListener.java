package org.kp.tpmg.ttg.clinicianconnect.web.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.kp.tpmg.ttg.clinicianconnect.web.common.util.OncallServiceConstants;

@WebListener
public class WebAppContextPropertyListener implements ServletContextListener{
	

public void contextInitialized(ServletContextEvent sce) {
	try {
		String confProtiesPath = OncallServiceConstants.DB_CONFIG_LOCATION;		
	} catch (Exception e) {
		
	}
	
}
public void contextDestroyed(ServletContextEvent sce) {
		
	}
}
