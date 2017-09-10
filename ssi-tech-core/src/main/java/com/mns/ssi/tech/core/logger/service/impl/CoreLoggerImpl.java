package com.mns.ssi.tech.core.logger.service.impl;

import java.net.InetAddress;

import com.mns.ssi.tech.core.constants.SSICoreConstant;
import com.mns.ssi.tech.core.exception.SSICoreException;
import com.mns.ssi.tech.core.threadcontext.SSICoreThreadLocalContext;
import com.mns.ssi.tech.core.threadcontext.impl.SSICoreThreadLocalContextImpl;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import com.mns.ssi.tech.core.logger.service.CoreLogger;
import com.mns.ssi.tech.core.util.SSICoreValidationUtil;

/**
 * 
 * @author desha
 * This class acts as a common logging class and provides methods to log errors and records.
 *
 * @param <T>
 */
public class CoreLoggerImpl<T> implements CoreLogger {

	private static final String SYSTEM_USER = "SYSTEM";
	private Boolean traceSupported;
	private org.slf4j.Logger wrappedLogger;

	protected Class<T> className;

	/**
	 * @param className
	 */
	public CoreLoggerImpl(Class<T> className) {
		wrappedLogger = LoggerFactory.getLogger(className);
		this.className = className;
	}

	
	@Override
	public void warn(String mensaje) {
		wrappedLogger.warn(mensaje);
	}

	
	@Override
	public void debug(String mensaje) {
		setMDC();
		wrappedLogger.debug(mensaje);
		MDC.clear();
	}

	
	@Override
	public void info(String message) {
		setMDC();
		wrappedLogger.info(message);
		MDC.clear();
	}


	@Override
	public String getName() {
		return wrappedLogger.getName();
	}


	@Override
	public String getNameMethod(Throwable exception) {
		return exception.getStackTrace()[0].getMethodName();
	}

	public void error(String mensaje) {
		setMDC();
		wrappedLogger.error(mensaje);
		MDC.clear();
	}

	
	@Override
	public void error(Exception exception) {
		setMDC();
		wrappedLogger.error(exception.getMessage(), exception);
		MDC.clear();
	}


	@Override
	public void error(String mensaje, Exception exception) {
		setMDC();
		wrappedLogger.error(exception.getMessage(), exception);
		MDC.clear();
	}

	
	@Override
	public String stackTraceToString(Throwable exception) {
		StringBuilder sb = new StringBuilder();
		for (StackTraceElement element : exception.getStackTrace()) {
			sb.append(element.toString());
			sb.append("\n");
		}
		return sb.toString();
	}
	
	private static void setMDC() {

		String userId = SYSTEM_USER;
		String uuid = "";
		SSICoreThreadLocalContext coreExecution = SSICoreThreadLocalContextImpl.getInstance();
		if (coreExecution != null) {
			userId = coreExecution.getUserId();


			if (userId == null) {
				userId = SYSTEM_USER;
			}
		}
		try {

			MDC.put("hostname", InetAddress.getLocalHost().getHostName());
		} catch (Exception ex) {
			throw new SSICoreException(ex);
		}
		MDC.put("thread", String.valueOf(Thread.currentThread().getId()));
		if(SSICoreValidationUtil.isEmpty(uuid)){
			uuid= SSICoreConstant.EMPTY;
		}
		MDC.put("uuid", uuid);
		MDC.put("matricula", userId);

	}

	
	@Override
	public boolean isDebugEnabled() {
		return wrappedLogger.isDebugEnabled();
	}

	
	@Override
	public boolean isInfoEnabled() {
		return wrappedLogger.isInfoEnabled();
	}

	@Override
	public boolean isTraceEnabled() {
		return wrappedLogger.isTraceEnabled();
	}

	/**
	 * @return
	 */
	protected boolean isTraceSupported() {
		if (traceSupported == null) {
			isTraceEnabled();
			traceSupported = Boolean.TRUE;
		}
		return traceSupported.booleanValue();
	}
}
