package org.kp.tpmg.ttg.clinicianconnect;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication(scanBasePackages = { 
		"org.kp.tpmg.ttg.clinicianconnect",
		"org.kp.tpmg.clinicianconnect.ccutilityservices.exception",
		"org.kp.tpmg.clinicianconnect.ccutilityservices.model",
		"org.kp.tpmg.clinicianconnect.ccutilityservices.util",
		"org.kp.tpmg.clinicianconnect.ccutilityservices.dao",
		"org.kp.tpmg.clinicianconnect.ccutilityservices.dao.impl" })
public class OncallservicesApplication {
	public static void main(String[] args) {
		new SpringApplicationBuilder(OncallservicesApplication.class).build().run(args);
	}
}