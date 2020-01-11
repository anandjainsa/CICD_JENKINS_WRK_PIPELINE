package org.kp.tpmg.ttg.clinicianconnect.web.model;

import java.io.Serializable;

public class PhoneBookSearchDetailJSON implements Serializable {

	private static final long serialVersionUID = 1L;
	private PhoneBookSearchDetailOutput service;

	public PhoneBookSearchDetailOutput getService() {
		return service;
	}

	public void setService(PhoneBookSearchDetailOutput service) {
		this.service = service;
	}
}
