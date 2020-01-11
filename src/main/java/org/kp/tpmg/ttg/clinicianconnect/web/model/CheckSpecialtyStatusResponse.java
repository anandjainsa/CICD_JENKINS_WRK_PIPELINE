package org.kp.tpmg.ttg.clinicianconnect.web.model;

import java.io.Serializable;

public class CheckSpecialtyStatusResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String isSpecailtyTurnedOff;
	private String isSpecialtyUpdatedTobad;
	
	public String getIsSpecailtyTurnedOff() {
		return isSpecailtyTurnedOff;
	}
	public void setIsSpecailtyTurnedOff(String isSpecailtyTurnedOff) {
		this.isSpecailtyTurnedOff = isSpecailtyTurnedOff;
	}
	public String getIsSpecialtyUpdatedTobad() {
		return isSpecialtyUpdatedTobad;
	}
	public void setIsSpecialtyUpdatedTobad(String isSpecialtyUpdatedTobad) {
		this.isSpecialtyUpdatedTobad = isSpecialtyUpdatedTobad;
	}
	@Override
	public String toString() {
		return "CheckSpecialtyStatusResponse [isSpecailtyTurnedOff=" + isSpecailtyTurnedOff + ", isSpecialtyUpdatedTobad=" + isSpecialtyUpdatedTobad+"]";
	}
}
