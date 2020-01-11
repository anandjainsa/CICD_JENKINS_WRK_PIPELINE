package org.kp.tpmg.ttg.clinicianconnect.web.model;

import java.io.Serializable;

public class OnCallSpecialty implements Serializable {

	private static final long serialVersionUID = 1L;
	private OnCallSchedule schedule;
	private OnCallProvider provider;
	private OnCallTelepresence telepresence;
	public OnCallSchedule getSchedule() {
		
		return schedule;
	}
	public void setSchedule(OnCallSchedule schedule) {
		this.schedule = schedule;
	}
	public OnCallProvider getProvider() {
		return provider;
	}
	public void setProvider(OnCallProvider provider) {
		this.provider = provider;
	}
	public OnCallTelepresence getTelepresence() {
		return telepresence;
	}
	public void setTelepresence(OnCallTelepresence telepresence) {
		this.telepresence = telepresence;
	}
	@Override
	public String toString() {
		return "OnCallSpecialty [schedule=" + schedule + ", provider="
				+ provider + ", telepresence=" + telepresence + "]";
	}
	
	
	
}
