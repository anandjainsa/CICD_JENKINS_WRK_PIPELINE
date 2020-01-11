package org.kp.tpmg.ttg.clinicianconnect.web.model;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.kp.tpmg.clinicianconnect.ccutilityservices.model.AppVersion;
import org.kp.tpmg.clinicianconnect.ccutilityservices.model.OSVersion;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@XmlRootElement(name = "FacilityEnvelope")
public class FacilityEnvelope implements Serializable {

	private static final long serialVersionUID = 1L;

	private AppVersion appVersion;
	@JsonInclude(Include.NON_NULL)
	private List<Location> locations;
	private List<Facility> facilities;
	private OSVersion iOSVersion;

	public List<Location> getLocations() {
		return locations;
	}

	public void setLocations(List<Location> locations) {
		this.locations = locations;
	}

	public List<Facility> getFacilities() {
		return facilities;
	}

	public void setFacilities(List<Facility> facilities) {
		this.facilities = facilities;
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
		return "FacilityEnvelope [appVersion=" + appVersion + ", locations=" + locations + "]";
	}

}
