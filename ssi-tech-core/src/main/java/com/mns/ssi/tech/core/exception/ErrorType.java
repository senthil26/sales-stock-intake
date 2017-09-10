package com.mns.ssi.tech.core.exception;
/**
 * 
 * @author desha
 *
 */
public enum ErrorType {
	ERROR("ERROR"),
	INFORMATIVO("INFORMATIVO"),
	VALIDACION("VALIDACION");
	
	String value;
	
	ErrorType(String value){
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
