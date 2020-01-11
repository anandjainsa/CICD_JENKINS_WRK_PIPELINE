package org.kp.tpmg.ttg.clinicianconnect.web.model;

import java.io.Serializable;

import org.kp.tpmg.clinicianconnect.ccutilityservices.common.StringUtil;

public class OnCallContact implements Serializable {

	private static final long serialVersionUID = 1L;

	private String type ;
	private String  number;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getNumber() {
	
		return number;
	}
	public void setNumber(String number) {
		this.number = StringUtil.getNumberFormat(number);
	}

}
