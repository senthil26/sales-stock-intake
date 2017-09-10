package com.mns.ssi.tech.core.logger.service;
/**
 * 
 * @author desha
 *
 */
public interface CoreLogger {
	
	 void error(Exception excepcion);
	 void error(String msgText);
	 void error(String msgText, Exception excepcion);
	 void debug(String msgText);
	 void warn(String obj);
	 void info(String msgText);
	 boolean isDebugEnabled();
	 boolean isInfoEnabled();
	 boolean isTraceEnabled() ;	
	 String getName();
	 String stackTraceToString(Throwable excepcion);	 
	 String getNameMethod(Throwable excepcion);	 
}