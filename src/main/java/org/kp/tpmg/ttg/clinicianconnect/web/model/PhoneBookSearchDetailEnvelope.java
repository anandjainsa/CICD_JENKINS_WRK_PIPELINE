package org.kp.tpmg.ttg.clinicianconnect.web.model;

import java.io.Serializable;
import java.util.List;

import org.kp.tpmg.clinicianconnect.cclib.model.mdoprovider.PhoneBookClinicianLocation;
import org.kp.tpmg.clinicianconnect.cclib.model.mdoprovider.PhoneBookDepartment;
import org.kp.tpmg.clinicianconnect.cclib.model.mdoprovider.PhoneBookFacility;
import org.kp.tpmg.clinicianconnect.cclib.model.mdoprovider.PhoneBookSpecialty;
import org.kp.tpmg.clinicianconnect.ccutilityservices.model.AppVersion;
import org.kp.tpmg.clinicianconnect.ccutilityservices.model.OSVersion;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class PhoneBookSearchDetailEnvelope implements Serializable {

	private static final long serialVersionUID = 1L;
	private AppVersion appVersion;
	private String nuid;
	private String firstName;
	private String lastName;
	private String middleName;
	private String profTitle;
	private String nickName;
	private String resourceId;
	private String name;
	private String providerType;
	private PhoneBookFacility facility;
	private PhoneBookSpecialty specialty;
	private PhoneBookDepartment department;
	private String photoUrlString;
	private String homePageUrl;
	private String secureTextUrl;
	private String favorite;
	private OnCallTelepresence telepresence;
	private List<PhoneBookClinicianLocation> clinicianLocations;
	private OSVersion iOSVersion;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getProfTitle() {
		return profTitle;
	}

	public void setProfTitle(String profTitle) {
		this.profTitle = profTitle;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	@JsonIgnore
	public AppVersion getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(AppVersion appVersion) {
		this.appVersion = appVersion;
	}

	public String getNuid() {
		return nuid;
	}

	public void setNuid(String nuid) {
		this.nuid = nuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public PhoneBookFacility getFacility() {
		return facility;
	}

	public void setFacility(PhoneBookFacility facility) {
		this.facility = facility;
	}

	public PhoneBookSpecialty getSpecialty() {
		return specialty;
	}

	public void setSpecialty(PhoneBookSpecialty specialty) {
		this.specialty = specialty;
	}

	public PhoneBookDepartment getDepartment() {
		return department;
	}

	public void setDepartment(PhoneBookDepartment department) {
		this.department = department;
	}

	public String getPhotoUrlString() {
		return photoUrlString;
	}

	public void setPhotoUrlString(String photoUrlString) {
		this.photoUrlString = photoUrlString;
	}

	public String getFavorite() {
		return favorite;
	}

	public void setFavorite(String favorite) {
		this.favorite = favorite;
	}

	public OnCallTelepresence getTelepresence() {
		return telepresence;
	}

	public void setTelepresence(OnCallTelepresence telepresence) {
		this.telepresence = telepresence;
	}

	public List<PhoneBookClinicianLocation> getClinicianLocations() {
		return clinicianLocations;
	}

	public void setClinicianLocations(List<PhoneBookClinicianLocation> clinicianLocations) {
		this.clinicianLocations = clinicianLocations;
	}

	public String getSecureTextUrl() {
		return secureTextUrl;
	}

	public void setSecureTextUrl(String secureTextUrl) {
		this.secureTextUrl = secureTextUrl;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public String getHomePageUrl() {
		return homePageUrl;
	}

	public void setHomePageURL(String homePageUrl) {
		this.homePageUrl = homePageUrl;
	}

	public String getProviderType() {
		return providerType;
	}

	public void setProviderType(String providerType) {
		this.providerType = providerType;
	}

	@JsonIgnore
	public OSVersion getiOSVersion() {
		return iOSVersion;
	}

	public void setiOSVersion(OSVersion osVersion) {
		this.iOSVersion = osVersion;
	}

	@Override
	public String toString() {
		return "PhoneBookSearchDetailEnvelope [appVersion=" + appVersion + ", nuid=" + nuid + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", middleName=" + middleName + ", profTitle=" + profTitle + ", nickName=" + nickName + ", resourceId=" + resourceId + ", name="
				+ name + ", providerType=" + providerType + ", facility=" + facility + ", specialty=" + specialty + ", department=" + department
				+ ", photoUrlString=" + photoUrlString + ", homePageUrl=" + homePageUrl + ", secureTextUrl=" + secureTextUrl + ", favorite="
				+ favorite + ", telepresence=" + telepresence + ", clinicianLocations=" + clinicianLocations + ", iOSVersion=" + iOSVersion + "]";
	}

}
