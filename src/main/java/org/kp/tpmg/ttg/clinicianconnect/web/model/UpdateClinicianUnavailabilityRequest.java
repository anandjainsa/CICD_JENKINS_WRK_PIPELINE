package org.kp.tpmg.ttg.clinicianconnect.web.model;

import java.io.Serializable;

public class UpdateClinicianUnavailabilityRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	private String statusExpiresAtDateTime;
	private String nuid;

	public String getStatusExpiresAtDateTime() {
		return statusExpiresAtDateTime;
	}

	public void setStatusExpiresAtDateTime(String statusExpiresAtDateTime) {
		this.statusExpiresAtDateTime = statusExpiresAtDateTime;
	}

	public String getNuid() {
		return nuid;
	}

	public void setNuid(String nuid) {
		this.nuid = nuid;
	}

}
