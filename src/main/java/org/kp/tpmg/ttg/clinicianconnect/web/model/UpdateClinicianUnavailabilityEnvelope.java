package org.kp.tpmg.ttg.clinicianconnect.web.model;

import java.io.Serializable;

public class UpdateClinicianUnavailabilityEnvelope implements Serializable {

	private static final long serialVersionUID = 1L;

	private String result;
	private boolean success;
	private Integer errorIdentifier;
	private String errorMessage;

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Integer getErrorIdentifier() {
		return errorIdentifier;
	}

	public void setErrorIdentifier(Integer errorIdentifier) {
		this.errorIdentifier = errorIdentifier;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
