package org.kp.tpmg.ttg.clinicianconnect.web.model;

import java.io.Serializable;
import java.util.List;

import org.kp.tpmg.clinicianconnect.ccutilityservices.model.AppVersion;
import org.kp.tpmg.clinicianconnect.ccutilityservices.model.OSVersion;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class SpecialtyEnvelope implements Serializable {

	private static final long serialVersionUID = 1L;
	private AppVersion appVersion;
	private Facility location;
	@JsonInclude(Include.NON_NULL)
	private List<LocationSpecialty> locationSpecialties;
	private List<Specialty> specialties;
	private String message;
	private OSVersion iOSVersion;
	
	@JsonIgnore
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Facility getLocation() {
		return location;
	}
	public void setLocation(Facility location) {
		this.location = location;
	}
	
	public List<LocationSpecialty> getLocationSpecialties() {
		return locationSpecialties;
	}
	public void setLocationSpecialties(List<LocationSpecialty> locationSpecialties) {
		this.locationSpecialties = locationSpecialties;
	}
	public List<Specialty> getSpecialties() {
		return specialties;
	}
	public void setSpecialties(List<Specialty> specialties) {
		this.specialties = specialties;
	}
	@JsonIgnore
	public AppVersion getAppVersion() {
		return appVersion;
	}
	public void setAppVersion(AppVersion appVersion) {
		this.appVersion = appVersion;
	}
	@JsonIgnore
	public OSVersion getiOSVersion() {
		return iOSVersion;
	}
	public void setiOSVersion(OSVersion iOSVersion) {
		this.iOSVersion = iOSVersion;
	}
	
}