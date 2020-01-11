package org.kp.tpmg.ttg.clinicianconnect.web.model;

import java.io.Serializable;
import java.util.List;

import org.kp.tpmg.ttg.clinicianconnect.web.model.Facility;

public class Location implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;
	private List<Facility> facilities;
	
	
	public List<Facility> getFacilities() {
			
		return facilities;
	}
	public void setFacilities(List<Facility> facilities) {
		this.facilities = facilities;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
