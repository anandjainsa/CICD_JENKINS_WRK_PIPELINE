package org.kp.tpmg.ttg.clinicianconnect.web.model;

import java.io.Serializable;

public class OnCallInput implements Serializable {

	private static final long serialVersionUID = 1L;

	private String facilityCode;
	private String specialty;
	private String nuid;
	private String token;
	private String kpEmployeeClass;
	private String kpRegionCode;
	private String appVersion;
	private String deviceType;
	private String iOSVersion;

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	public String getKpEmployeeClass() {
		return kpEmployeeClass;
	}

	public void setKpEmployeeClass(String kpEmployeeClass) {
		this.kpEmployeeClass = kpEmployeeClass;
	}

	public String getKpRegionCode() {
		return kpRegionCode;
	}

	public void setKpRegionCode(String kpRegionCode) {
		this.kpRegionCode = kpRegionCode;
	}

	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}

	public String getFacilityCode() {
		return facilityCode;
	}

	public void setFacilityCode(String facilityCode) {
		this.facilityCode = facilityCode;
	}

	public String getNuid() {
		return nuid;
	}

	public void setNuid(String nuid) {
		this.nuid = nuid;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getiOSVersion() {
		return iOSVersion;
	}

	public void setiOSVersion(String iOSVersion) {
		this.iOSVersion = iOSVersion;
	}

	@Override
	public String toString() {
		return "OnCallInput [facilityCode=" + facilityCode + ", specialty=" + specialty + ", nuid=" + nuid + ", token="
				+ token + ", kpEmployeeClass=" + kpEmployeeClass + ", kpRegionCode=" + kpRegionCode + ", appVersion=" + appVersion + ", deviceType="
				+ deviceType + ", iOSVersion=" + iOSVersion + "]";
	}

}
