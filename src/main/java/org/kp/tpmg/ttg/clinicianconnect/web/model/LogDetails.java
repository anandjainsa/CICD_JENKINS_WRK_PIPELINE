package org.kp.tpmg.ttg.clinicianconnect.web.model;

import org.apache.commons.lang3.StringUtils;

public class LogDetails {

	private String moduleName;
	private String serviceName;
	private String severity;
	private String nuid;
	private String facilityName;
	private String specialtyName;
	private String param1;
	private String param2;
	private String reason;
	private String logMessage;
	private String firstName;
	private String lastName;

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getSeverity() {
		return severity;
	}

	public void setSeverity(String severity) {
		this.severity = severity;
	}

	public String getNuid() {
		return nuid;
	}

	public void setNuid(String nuid) {
		this.nuid = nuid;
	}

	public String getFacilityName() {
		return facilityName;
	}

	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}

	public String getSpecialtyName() {
		return specialtyName;
	}

	public void setSpecialtyName(String specialtyName) {
		this.specialtyName = specialtyName;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getLogMessage() {
		return logMessage;
	}

	public void setLogMessage(String logMessage) {
		this.logMessage = logMessage;
	}

	public String getParam2() {
		return param2;
	}

	public void setParam2(String param2) {
		this.param2 = param2;
	}

	public String getParam1() {
		return param1;
	}

	public void setParam1(String param1) {
		this.param1 = param1;
	}

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

	@Override
	public String toString() {
		return "LogDetails [moduleName=" + moduleName + ", serviceName=" + serviceName + ", severity=" + severity + ", nuid=" + nuid
				+ ", facilityName=" + facilityName + ", specialtyName=" + specialtyName + ", param1=" + param1 + ", param2=" + param2 + ", reason="
				+ reason + ", logMessage=" + logMessage + ", firstName=" + firstName + ", lastName=" + lastName + "]";
	}

	public void trimLogDetails() {

		if (StringUtils.isNotBlank(getModuleName()) && getModuleName().trim().length() > 100) {
			setModuleName(getModuleName().trim().substring(0, 99));
		}
		if (StringUtils.isNotBlank(getSeverity()) && getSeverity().trim().length() > 20) {
			setSeverity(getSeverity().trim().substring(0, 19));
		}
		if (StringUtils.isNotBlank(getServiceName()) && getServiceName().trim().length() > 100) {
			setServiceName(getServiceName().trim().substring(0, 99));
		}
		setFacilityAndSpecialtyName();
		setParams();
		setReasonAndMessage();
		setNames();
	}

	private void setReasonAndMessage() {
		if (StringUtils.isNotBlank(getLogMessage()) && getLogMessage().trim().length() > 5000) {
			setLogMessage(getLogMessage().trim().substring(0, 4999));
		}
		if (StringUtils.isNotBlank(getReason()) && getReason().trim().length() > 5000) {
			setReason(getReason().trim().substring(0, 4999));
		}
	}

	private void setNames() {
		if (StringUtils.isNotBlank(getFirstName()) && getFirstName().trim().length() > 200) {
			setParam1(getFirstName().trim().substring(0, 199));
		}
		if (StringUtils.isNotBlank(getLastName()) && getLastName().trim().length() > 200) {
			setParam1(getFirstName().trim().substring(0, 199));
		}
	}

	private void setParams() {
		if (StringUtils.isNotBlank(getParam1()) && getParam1().trim().length() > 100) {
			setParam1(getParam1().trim().substring(0, 99));
		}
		if (StringUtils.isNotBlank(getParam2()) && getParam2().trim().length() > 100) {
			setParam2(getParam2().trim().substring(0, 99));
		}
	}

	private void setFacilityAndSpecialtyName() {
		if (StringUtils.isNotBlank(getFacilityName()) && getFacilityName().trim().length() > 100) {
			setFacilityName(getFacilityName().trim().substring(0, 99));
		}
		if (StringUtils.isNotBlank(getSpecialtyName()) && getSpecialtyName().trim().length() > 100) {
			setSpecialtyName(getSpecialtyName().trim().substring(0, 99));
		}
	}
}
