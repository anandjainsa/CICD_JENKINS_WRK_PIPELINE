package org.kp.tpmg.ttg.clinicianconnect.web.model;

import java.io.Serializable;

public class SearchDetailInput implements Serializable {

	private static final long serialVersionUID = 1L;
	private String nuid;
	private String resourceId;
	private String token;
	private String kpEmployeeClass;
	private String kpRegionCode;
	private String appVersion;
	private String deviceType;
	private String iOSVersion;

	public SearchDetailInput() {
		super();
	}

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	public String getNuid() {
		return nuid;
	}

	public void setNuid(String nuid) {
		this.nuid = nuid;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
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
		return "SearchDetailInput [nuid=" + nuid + ", resourceId=" + resourceId + ", token=" + token
				+ ", kpEmployeeClass=" + kpEmployeeClass + ", kpRegionCode=" + kpRegionCode + ", appVersion=" + appVersion + ", deviceType="
				+ deviceType + ", iOSVersion=" + iOSVersion + "]";
	}

}
