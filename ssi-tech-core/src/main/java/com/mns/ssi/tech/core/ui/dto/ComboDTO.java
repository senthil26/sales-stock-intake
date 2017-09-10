package com.mns.ssi.tech.core.ui.dto;

import java.io.Serializable;

public class ComboDTO implements Serializable{
 
	private static final long serialVersionUID = 3456992041867836558L;
	
	private String code;
	private String value;
	private Boolean status;
	
	public ComboDTO() {
	}

	public ComboDTO(String code, String value) {
		super();
		this.code = code;
		this.value = value;
	}

	public ComboDTO(String code, String value, Boolean status) {
		super();
		this.code = code;
		this.value = value;
		this.status = status;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}
}
