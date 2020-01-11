package org.kp.tpmg.ttg.clinicianconnect.web.model;

import java.io.Serializable;
import java.util.List;


public class LocationSpecialty implements Serializable {

	private static final long serialVersionUID = 1L;
	private String name;
	private List<Specialty> specialties;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Specialty> getSpecialties() {
		return specialties;
	}
	public void setSpecialties(List<Specialty> specialties) {
		this.specialties = specialties;
	}
}
