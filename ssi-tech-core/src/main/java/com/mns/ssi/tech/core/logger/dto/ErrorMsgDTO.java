package com.mns.ssi.tech.core.logger.dto;

import java.io.Serializable;

/**
 * 
 * @author desha
 *
 */
public class ErrorMsgDTO extends SSIBaseEntityDTO implements Serializable {
	
	
	private static final long serialVersionUID = 4602521382011928096L;
		
	private String code;
	private String msgText;
	private String msgTechnical;	
	private String msgType;
    private String method;
    private String action;
    private String hostname;
    private String uuid;

	public ErrorMsgDTO() {
	}
	

	
	public String toString(){
		
		StringBuilder sb=new StringBuilder();
		sb.append(", entityId:").append(this.getEntityId());
		sb.append(", code:").append(code);
		sb.append(", msgText:").append(msgText);
		sb.append(", msgTechnical:").append(msgTechnical);
		sb.append(", msgType:").append(msgType);
		sb.append(" ,method:").append(method);
		sb.append(", action:").append(action);
		sb.append(" ,hostname:").append(hostname);
		sb.append(" ,uuid:").append(uuid);
		return sb.toString();		
	}



	public String getCode() {
		return code;
	}



	public void setCode(String code) {
		this.code = code;
	}



	public String getMsgText() {
		return msgText;
	}



	public void setMsgText(String msgText) {
		this.msgText = msgText;
	}



	public String getMsgTechnical() {
		return msgTechnical;
	}



	public void setMsgTechnical(String msgTechnical) {
		this.msgTechnical = msgTechnical;
	}



	public String getMsgType() {
		return msgType;
	}



	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}



	public String getMethod() {
		return method;
	}



	public void setMethod(String method) {
		this.method = method;
	}



	public String getAction() {
		return action;
	}



	public void setAction(String action) {
		this.action = action;
	}



	public String getHostname() {
		return hostname;
	}



	public void setHostname(String hostname) {
		this.hostname = hostname;
	}



	public String getUuid() {
		return uuid;
	}



	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	

	
}
