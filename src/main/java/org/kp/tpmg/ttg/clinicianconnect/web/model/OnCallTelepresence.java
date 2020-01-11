package org.kp.tpmg.ttg.clinicianconnect.web.model;

import java.io.Serializable;

public class OnCallTelepresence implements Serializable {

	private static final long serialVersionUID = 1L;

	
	private String video ;
	private String  audio;
	private String text ="false";
	
	public String getVideo() {

		return video;
	}
	public void setVideo(String video) {
		this.video = video;
	}
	public String getAudio() {

		return audio;
	}
	public void setAudio(String audio) {
		this.audio = audio;
	}
	public String getText() {
			
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	@Override
	public String toString() {
		return "OnCallTelepresence [video=" + video + ", audio=" + audio
				+ ", text=" + text + "]";
	}

}
