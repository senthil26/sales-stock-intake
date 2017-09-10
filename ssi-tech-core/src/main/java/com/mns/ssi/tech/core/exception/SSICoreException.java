package com.mns.ssi.tech.core.exception;

import java.util.ArrayList;
import java.util.List;

import com.mns.ssi.tech.core.logger.dto.ErrorMsgDTO;
import com.mns.ssi.tech.core.logger.factory.LoggerFactory;
import com.mns.ssi.tech.core.logger.service.CoreLogger;

/**
 * 
 * @author desha
 *
 */
public class SSICoreException extends RuntimeException {

	private static final long serialVersionUID = 1293544776529298266L;

	protected CoreLogger loggerCore = LoggerFactory.getLogger(this.getClass());
	
	protected final static String KEY_UUID="uuid";

	private String uuid;

	/**
	 * for validation or error for error always store one dto
	 */
	private List<ErrorMsgDTO> lstErrorMsgDTO = new ArrayList<ErrorMsgDTO>();

	public SSICoreException() {
		super();
	}

	public SSICoreException(String message) {
		super(message);
		if (loggerCore.isDebugEnabled()) {
			loggerCore.error(message,this);
		}else{
			loggerCore.error(message);
		}
	}

	public SSICoreException(Exception exception) {
		super(exception);
		if (loggerCore.isDebugEnabled()) {
			loggerCore.error(exception.getMessage(),exception);
		}else{
			loggerCore.error(exception.getMessage());
		}
	}

	public SSICoreException(String message, Exception exception) {
		super(message, exception);
		if (loggerCore.isDebugEnabled()) {
			loggerCore.error(message,exception);
		}else{
			loggerCore.error(message);
		}
	}

	public SSICoreException(ErrorMsgDTO errorMsgDTO) {
		super(errorMsgDTO.getCode() + ":" + errorMsgDTO.getMsgText());
		lstErrorMsgDTO.add(errorMsgDTO);
		loggerCore.error(this.getMessage());
	}

	public SSICoreException(ErrorMsgDTO error, Exception e) {
		super(e);
		if (loggerCore.isDebugEnabled()) {
			loggerCore.error(this.getMessage(),e);
		}else{
			loggerCore.error(error.getCode()+" "+ error.getMsgText()+" Method "+error.getMethod());
		}
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public List<ErrorMsgDTO> getLstErrorMsgDTO() {
		return lstErrorMsgDTO;
	}

	public void setLstErrorMsgDTO(List<ErrorMsgDTO> lstErrorMsgDTO) {
		this.lstErrorMsgDTO = lstErrorMsgDTO;
	}

}
