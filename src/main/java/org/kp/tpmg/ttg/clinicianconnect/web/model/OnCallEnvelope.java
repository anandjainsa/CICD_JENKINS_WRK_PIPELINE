package org.kp.tpmg.ttg.clinicianconnect.web.model;

import java.io.Serializable;
import java.util.List;

import org.kp.tpmg.clinicianconnect.ccutilityservices.model.AppVersion;
import org.kp.tpmg.clinicianconnect.ccutilityservices.model.OSVersion;
import org.kp.tpmg.clinicianconnect.ccutilityservices.model.OnCallSpecialtySchedule;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class OnCallEnvelope implements Serializable {

	private static final long serialVersionUID = 1L;
	private AppVersion appVersion;
	private Facility location;
	private Specialty specialty;
	private List<OnCallSpecialtySchedule> specialtySchedule;
	private List<OnCallSpecialty> specialists;
	private Long asOfDate;
	private String message;
	private OSVersion iOSVersion;
	
	@JsonIgnore
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Long getAsOfDate() {
		return asOfDate;
	}

	public void setAsOfDate(Long asOfDate) {
		this.asOfDate = asOfDate;
	}

	public Facility getLocation() {
		return location;
	}

	public void setLocation(Facility location) {
		this.location = location;
	}

	public Specialty getSpecialty() {
		return specialty;
	}

	public void setSpecialty(Specialty specialty) {
		this.specialty = specialty;
	}

	public List<OnCallSpecialtySchedule> getSpecialtySchedule() {
		return specialtySchedule;
	}

	public void setSpecialtySchedule(List<OnCallSpecialtySchedule> specialtySchedule) {
		this.specialtySchedule = specialtySchedule;
	}

	public List<OnCallSpecialty> getSpecialists() {
		return specialists;
	}

	public void setSpecialists(List<OnCallSpecialty> specialists) {
		this.specialists = specialists;
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

	@Override
	public String toString() {
		return "OnCallEnvelope [appVersion=" + appVersion + ", location=" + location + ", specialty=" + specialty
				+ ", specialtySchedule=" + specialtySchedule + ", specialists=" + specialists + ", asOfDate=" + asOfDate
				+ ", message=" + message + ", iOSVersion=" + iOSVersion + "]";
	}

}
