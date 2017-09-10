package com.mns.ssi.tech.core.rest.dto;

import java.io.Serializable;

public class RESTResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8716443384999266653L;
	private String status;
	private String errorMsg;
	private String result;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}
