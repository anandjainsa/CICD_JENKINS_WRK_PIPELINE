package org.kp.tpmg.ttg.clinicianconnect.web.common.properties;

import java.io.Closeable;

import org.kp.tpmg.ttg.clinicianconnect.web.exception.OncallServiceException;


public interface IReloadLog4jProperties extends Closeable {
	public void intializeLog4jSystem(String filePath) throws OncallServiceException;
}
