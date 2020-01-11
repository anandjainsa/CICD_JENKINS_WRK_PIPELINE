package org.kp.tpmg.ttg.clinicianconnect.web.model;

import java.io.Serializable;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.kp.tpmg.clinicianconnect.ccutilityservices.model.AppVersion;
import org.kp.tpmg.clinicianconnect.ccutilityservices.model.OSVersion;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class SpecialtyOutput implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String name;
	private SpecialtyEnvelope envelope;
	private AppVersion appVersion;
	@JsonSerialize(include = JsonSerialize.Inclusion.NON_EMPTY)
	private OSVersion iOSVersion;
	private Status status;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public SpecialtyEnvelope getEnvelope() {
		return envelope;
	}
	public void setEnvelope(SpecialtyEnvelope envelope) {
		this.envelope = envelope;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	
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
