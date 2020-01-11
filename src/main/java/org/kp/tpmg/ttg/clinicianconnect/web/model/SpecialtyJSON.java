package org.kp.tpmg.ttg.clinicianconnect.web.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "SpecialtyJSON")
public class SpecialtyJSON implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private SpecialtyOutput service;

	public SpecialtyOutput getService() {
		return service;
	}

	public void setService(SpecialtyOutput service) {
		this.service = service;
	}

}
