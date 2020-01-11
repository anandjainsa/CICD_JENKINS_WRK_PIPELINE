package org.kp.tpmg.ttg.clinicianconnect.web.common.util;

public class ClinicianPhoneObject {

	private String resourceId;
	private String facilityCode;
	private String clinicianCellPhoneNumber;
	private String clinicianPageNumber;
	private String resourceCellPhoneNumber;
	private String resourceNurseInternalPhoneNumber;
	private String resourceNurseExternalPhoneNumber;
	private String resourcePrivateInternalPhoneNumber;
	private String resourcePrivateExternalPhoneNumber;
	private String resourcePagerPhoneNumber;
	private String nuid;
	private String clinicianName;
	private String lastName;
	private String areaSpecialtyName;
	private String emailAddress;

	public ClinicianPhoneObject(){
		 resourceId =null;
		 facilityCode =null;
		 clinicianCellPhoneNumber =null;
		 clinicianPageNumber =null;
		 resourceCellPhoneNumber =null;
		 resourceNurseInternalPhoneNumber =null;
		 resourceNurseExternalPhoneNumber =null;
		 resourcePrivateInternalPhoneNumber =null;
		 resourcePrivateExternalPhoneNumber=null;
		 resourcePagerPhoneNumber=null;
		 nuid=null;
		 clinicianName=null;
		 lastName=null;
		 areaSpecialtyName = null;
	}
	
	public String getResourceId() {
		return resourceId;
	}
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	public String getFacilityCode() {
		return facilityCode;
	}
	public void setFacilityCode(String facilityCode) {
		this.facilityCode = facilityCode;
	}
	public String getClinicianCellPhoneNumber() {

		return clinicianCellPhoneNumber;
	}
	public void setClinicianCellPhoneNumber(String clinicianCellPhoneNumber) {
		this.clinicianCellPhoneNumber = clinicianCellPhoneNumber;
	}
	public String getClinicianPageNumber() {
	
		return clinicianPageNumber;
	}
	public void setClinicianPageNumber(String clinicianPageNumber) {
		this.clinicianPageNumber = clinicianPageNumber;
	}
	public String getResourceCellPhoneNumber() {
		return resourceCellPhoneNumber;
	}
	public void setResourceCellPhoneNumber(String resourceCellPhoneNumber) {
		this.resourceCellPhoneNumber = resourceCellPhoneNumber;
	}
	public String getResourceNurseInternalPhoneNumber() {
		return resourceNurseInternalPhoneNumber;
	}
	public void setResourceNurseInternalPhoneNumber(
			String resourceNurseInternalPhoneNumber) {
		this.resourceNurseInternalPhoneNumber = resourceNurseInternalPhoneNumber;
	}
	public String getResourceNurseExternalPhoneNumber() {
		return resourceNurseExternalPhoneNumber;
	}
	public void setResourceNurseExternalPhoneNumber(
			String resourceNurseExternalPhoneNumber) {
		this.resourceNurseExternalPhoneNumber = resourceNurseExternalPhoneNumber;
	}
	public String getResourcePrivateInternalPhoneNumber() {
		return resourcePrivateInternalPhoneNumber;
	}
	public void setResourcePrivateInternalPhoneNumber(
			String resourcePrivateInternalPhoneNumber) {
		this.resourcePrivateInternalPhoneNumber = resourcePrivateInternalPhoneNumber;
	}
	public String getResourcePrivateExternalPhoneNumber() {
		return resourcePrivateExternalPhoneNumber;
	}
	public void setResourcePrivateExternalPhoneNumber(
			String resourcePrivateExternalPhoneNumber) {
		this.resourcePrivateExternalPhoneNumber = resourcePrivateExternalPhoneNumber;
	}
	public String getResourcePagerPhoneNumber() {
		return resourcePagerPhoneNumber;
	}
	public void setResourcePagerPhoneNumber(String resourcePagerPhoneNumber) {
		this.resourcePagerPhoneNumber = resourcePagerPhoneNumber;
	}

	public String getNuid() {
		return nuid;
	}


	public void setNuid(String nuid) {
		this.nuid = nuid;
	}
	public String getClinicianName() {
		return clinicianName;
	}


	public void setClinicianName(String clinicianName) {
		this.clinicianName = clinicianName;
	}

	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
    
	public String getAreaSpecialtyName() {
		return areaSpecialtyName;
	}
	
	
	public void setAreaSpecialtyName(String areaSpecialtyName) {
		this.areaSpecialtyName = areaSpecialtyName;
	}

	@Override
	public String toString() {
		return "ClinicianPhoneObject [resourceId=" + resourceId
				+ ", facilityCode=" + facilityCode
				+ ", clinicianCellPhoneNumber=" + clinicianCellPhoneNumber
				+ ", clinicianPageNumber=" + clinicianPageNumber
				+ ", resourceCellPhoneNumber=" + resourceCellPhoneNumber
				+ ", resourceNurseInternalPhoneNumber="
				+ resourceNurseInternalPhoneNumber
				+ ", resourceNurseExternalPhoneNumber="
				+ resourceNurseExternalPhoneNumber
				+ ", resourcePrivateInternalPhoneNumber="
				+ resourcePrivateInternalPhoneNumber
				+ ", resourcePrivateExternalPhoneNumber="
				+ resourcePrivateExternalPhoneNumber
				+ ", resourcePagerPhoneNumber=" + resourcePagerPhoneNumber
				+ ", nuid=" + nuid + ", clinicianName=" + clinicianName
				+ ", lastName=" + lastName + ", areaSpecialtyName="
				+ areaSpecialtyName + "]";
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
}
