package com.mns.ssi.tech.core.rest.dto;

public enum RESTStatus {

	STATUS_OK("OK"), STATUS_BADREQUEST("BADREQUEST"), STATUS_UNKNOWN("UNKNOWN"), STATUS_ERROR("ERROR");

	private String status;

	private RESTStatus(String restStatus) {
		status = restStatus;
	}

	public String getRestStatus() {
		return status;
	}

}
