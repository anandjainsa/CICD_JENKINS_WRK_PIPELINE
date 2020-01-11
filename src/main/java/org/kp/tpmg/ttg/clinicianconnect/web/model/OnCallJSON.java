package org.kp.tpmg.ttg.clinicianconnect.web.model;

import java.io.Serializable;

public class OnCallJSON implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private OnCallOutput service;

	public OnCallOutput getService() {
		return service;
	}

	public void setService(OnCallOutput service) {
		this.service = service;
	}

}
