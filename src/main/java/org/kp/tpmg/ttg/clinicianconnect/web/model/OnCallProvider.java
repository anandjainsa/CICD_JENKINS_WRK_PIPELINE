package org.kp.tpmg.ttg.clinicianconnect.web.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class OnCallProvider implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;
	private String firstName;
	private String lastName;
	private String middleName;
	private String profTitle;
	private String nickName;
	private String homepageUrl;
	private String facilityName;
	private String facilityCode;
	private String specialtyName;
	private String specialtyCode;
	private String nuid;
	private String photoUrl;
	private String secureTextUrl;
	private String email;

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
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHomepageUrl() {
		return homepageUrl;
	}

	public void setHomepageUrl(String homepageUrl) {
		this.homepageUrl = homepageUrl;
	}

	public String getFacilityName() {
		return facilityName;
	}

	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
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

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public String getSecureTextUrl() {
		return secureTextUrl;
	}

	public void setSecureTextUrl(String secureTextUrl) {
		this.secureTextUrl = secureTextUrl;
	}
	public String getSpecialtyName() {
		return specialtyName;
	}

	public void setSpecialtyName(String specialtyName) {
		this.specialtyName = specialtyName;
	}

	public String getSpecialtyCode() {
		return specialtyCode;
	}

	public void setSpecialtyCode(String specialtyCode) {
		this.specialtyCode = specialtyCode;
	}


	@Override
	public String toString() {
		return "OnCallProvider [name=" + name + ", homepageUrl=" + homepageUrl + ", facilityName=" + facilityName + ", facilityCode="
				+ facilityCode + ", nuid=" + nuid + ", photoUrl=" + photoUrl + ", secureTextUrl=" + secureTextUrl + ", email=" + email
				+ "]";
	}

	
	
	
	
}
