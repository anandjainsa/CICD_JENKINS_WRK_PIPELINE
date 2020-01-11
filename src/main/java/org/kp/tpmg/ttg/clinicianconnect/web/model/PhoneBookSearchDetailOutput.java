package org.kp.tpmg.ttg.clinicianconnect.web.model;

import java.io.Serializable;

import org.kp.tpmg.clinicianconnect.ccutilityservices.model.AppVersion;
import org.kp.tpmg.clinicianconnect.ccutilityservices.model.OSVersion;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class PhoneBookSearchDetailOutput implements Serializable {

	private static final long serialVersionUID = 1L;
	private String name;
	private Status status;
	private AppVersion appVersion;
	private PhoneBookSearchDetailEnvelope envelope;
	@JsonSerialize(include = JsonSerialize.Inclusion.NON_EMPTY)
	private OSVersion iOSVersion;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public PhoneBookSearchDetailEnvelope getEnvelope() {
		return envelope;
	}

	public void setEnvelope(PhoneBookSearchDetailEnvelope envelope) {
		this.envelope = envelope;
	}

	public OSVersion getiOSVersion() {
		return iOSVersion;
	}

	public void setiOSVersion(OSVersion iOSVersion) {
		this.iOSVersion = iOSVersion;
	}

}
