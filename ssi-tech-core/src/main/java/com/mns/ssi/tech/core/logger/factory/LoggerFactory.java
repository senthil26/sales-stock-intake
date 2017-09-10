package com.mns.ssi.tech.core.logger.factory;

import com.mns.ssi.tech.core.logger.service.CoreLogger;
import com.mns.ssi.tech.core.logger.service.impl.CoreLoggerImpl;

/**
 * 
 * @author desha
 *
 */
public class LoggerFactory {
	private LoggerFactory() {
	}

	public static CoreLogger getLogger(Class className) {
	    return new CoreLoggerImpl(className);
	}
	
	
}
