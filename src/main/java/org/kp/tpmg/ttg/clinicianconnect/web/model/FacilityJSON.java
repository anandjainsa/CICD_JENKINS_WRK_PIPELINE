package org.kp.tpmg.ttg.clinicianconnect.web.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "FacilityJSON")
public class FacilityJSON implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private FacilityOutput service;

	public FacilityOutput getService() {
		return service;
	}

	public void setService(FacilityOutput service) {
		this.service = service;
	}

}
