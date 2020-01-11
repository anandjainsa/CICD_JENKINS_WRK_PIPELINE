package org.kp.tpmg.ttg.clinicianconnect.web.model;

import java.io.Serializable;

public class CorTextDeptDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	private String departmentName;
	private String emailAddress;
	private String phoneNumber;

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

}
