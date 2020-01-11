package org.kp.tpmg.ttg.clinicianconnect.web.exception;

public class OncallServiceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public OncallServiceException() {
		super();
	}

	public OncallServiceException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public OncallServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public OncallServiceException(String message) {
		super(message);
	}

	public OncallServiceException(Throwable cause) {
		super(cause);
	}
	
}
