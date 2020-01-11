package org.kp.tpmg.ttg.clinicianconnect.web.model;

import java.io.Serializable;

public class UpdateClinicianUnavailabilityOutput implements Serializable {

	private static final long serialVersionUID = 1L;
	private String name;
	private UpdateClinicianUnavailabilityEnvelope envelope;
	private Status status;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public UpdateClinicianUnavailabilityEnvelope getEnvelope() {
		return envelope;
	}

	public void setEnvelope(UpdateClinicianUnavailabilityEnvelope envelope) {
		this.envelope = envelope;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

}
