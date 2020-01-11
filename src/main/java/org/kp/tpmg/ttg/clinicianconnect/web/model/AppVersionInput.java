package org.kp.tpmg.ttg.clinicianconnect.web.model;

import java.io.Serializable;

public class AppVersionInput implements Serializable {

	private static final long serialVersionUID = 1L;

	private String nuid;
	private String token;
	private String appVersion;
	private String iOSVersion;
	private String deviceType;

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

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	public String getiOSVersion() {
		return iOSVersion;
	}

	public void setiOSVersion(String iOSVersion) {
		this.iOSVersion = iOSVersion;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	@Override
	public String toString() {
		return "AppVersionInput [nuid=" + nuid + ", token=" + token + ", appVersion=" + appVersion + ", iOSVersion="
				+ iOSVersion + ", deviceType=" + deviceType + "]";
	}

}
