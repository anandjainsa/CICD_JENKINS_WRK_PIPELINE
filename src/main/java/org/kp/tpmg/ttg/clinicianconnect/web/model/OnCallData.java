package org.kp.tpmg.ttg.clinicianconnect.web.model;

import java.io.Serializable;

public class OnCallData implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String rundate;
	private String facilityCode;
	private String role;
	private String specialty;
	private String callOrder;
	private String startDateTime;
	private String endDateTime;
	private String mobileNumber;
	private String pagerNumber;
	private String lineNumber;
	private String notes;
	private String resourceName;
	private String nuid;
	private String nickName;
	private String humanType;
	private String unavailableTill;
	
	public String getUnavailableTill() {
		return unavailableTill;
	}
	
	public void setUnavailableTill(String unavailableTill) {
		this.unavailableTill = unavailableTill;
	}
	public String getRundate() {
		return rundate;
	}
	public void setRundate(String rundate) {
		this.rundate = rundate;
	}
	public String getFacilityCode() {
		return facilityCode;
	}
	public void setFacilityCode(String facilityCode) {
		this.facilityCode = facilityCode;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getSpecialty() {
		return specialty;
	}
	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}
	public String getCallOrder() {
		return callOrder;
	}
	public void setCallOrder(String callOrder) {
		this.callOrder = callOrder;
	}
	public String getStartDateTime() {
		return startDateTime;
	}
	public void setStartDateTime(String startDateTime) {
		this.startDateTime = startDateTime;
	}
	public String getEndDateTime() {
		return endDateTime;
	}
	public void setEndDateTime(String endDateTime) {
		this.endDateTime = endDateTime;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getPagerNumber() {
		return pagerNumber;
	}
	public void setPagerNumber(String pagerNumber) {
		this.pagerNumber = pagerNumber;
	}
	public String getLineNumber() {
		return lineNumber;
	}
	public void setLineNumber(String lineNumber) {
		this.lineNumber = lineNumber;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getResourceName() {
		return resourceName;
	}
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	public String getNuid() {
		return nuid;
	}
	public void setNuid(String nuid) {
		this.nuid = nuid;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getHumanType() {
		return humanType;
	}
	public void setHumanType(String humanType) {
		this.humanType = humanType;
	}
	
	@Override
	public String toString() {
		return "OnCallResultSetOutput [rundate=" + rundate + ", facilityCode=" + facilityCode + ", role=" + role + ", specialty=" + specialty
				+ ", callOrder=" + callOrder + ", startDateTime=" + startDateTime + ", endDateTime=" + endDateTime + ", mobileNumber=" + mobileNumber
				+ ", pagerNumber=" + pagerNumber + ", lineNumber=" + lineNumber + ", notes=" + notes + ", resourceName=" + resourceName + ", nuid="
				+ nuid + ", nickName=" + nickName + ", humanType=" + humanType + "]";
	}
}
