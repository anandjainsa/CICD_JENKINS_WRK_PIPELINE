package org.kp.tpmg.ttg.clinicianconnect.web.model;

import java.io.Serializable;

public class Specialty implements Serializable {

	private static final long serialVersionUID = 1L;
	private String code;
	private String name;
	private String isLive;
	public String getName() {	
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIsLive() {		
		return isLive;
	}
	public void setIsLive(String isLive) {

		if ("T".equalsIgnoreCase(isLive) || "Y".equalsIgnoreCase(isLive))
			this.isLive = "True";
		else {
			this.isLive = "False";
		}
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	@Override
	public String toString() {
		return "Specialty [code=" + code + ", name=" + name + ", isLive=" + isLive + "]";
	}
	
	

}

