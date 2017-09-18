package com.mns.ssi.tech.core.exception;
/**
 * 
 * @author desha
 *
 */
public enum ErrorType {
	ERROR("ERROR"),
	INFORMATION("INFORMATION"),
	VALIDATION("VALIDATION");
	
	String value;
	
	ErrorType(String value){
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
