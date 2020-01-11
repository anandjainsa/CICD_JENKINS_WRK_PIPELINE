package org.kp.tpmg.ttg.clinicianconnect.web.model;

import java.io.Serializable;

public class UpdateClinicianUnavailabilityJson implements Serializable {

	private static final long serialVersionUID = 1L;
	private UpdateClinicianUnavailabilityOutput service;

	public UpdateClinicianUnavailabilityOutput getService() {
		return service;
	}

	public void setService(UpdateClinicianUnavailabilityOutput service) {
		this.service = service;
	}

}
