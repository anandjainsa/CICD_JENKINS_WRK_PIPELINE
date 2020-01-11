package org.kp.tpmg.ttg.clinicianconnect.web.model;

import java.io.Serializable;
import java.util.List;

public class OnCallSchedule implements Serializable {

	private static final long serialVersionUID = 1L;

	private String callOrder;
	private String role;
	private String speciality;
	private String startDateTime;
	private String endDateTime;
	private String unavailableTill;
	private List<OnCallContact> contacts;
	private List<OnCallNotes> notes;

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

	public String getUnavailableTill() {
		return unavailableTill;
	}

	public void setUnavailableTill(String unavailableTill) {
		this.unavailableTill = unavailableTill;
	}

	public String getCallOrder() {
		return callOrder;
	}

	public void setCallOrder(String callOrder) {
		this.callOrder = callOrder;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getSpeciality() {
		return speciality;
	}

	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}

	public List<OnCallContact> getContacts() {
		return contacts;
	}

	public void setContacts(List<OnCallContact> contacts) {
		this.contacts = contacts;
	}

	public List<OnCallNotes> getNotes() {
		return notes;
	}

	public void setNotes(List<OnCallNotes> notes) {
		this.notes = notes;
	}

	@Override
	public String toString() {
		return "OnCallSchedule [callOrder=" + callOrder + ", role=" + role + ", speciality=" + speciality
				+ ", startDateTime=" + startDateTime + ", endDateTime=" + endDateTime + ", contacts=" + contacts
				+ ", notes=" + notes + "]";
	}

}
