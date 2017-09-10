package com.mns.ssi.tech.core.logger.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
/**
 * 
 * @author desha
 *
 */
public class SSIBaseEntityDTO implements Serializable {

	private static final long serialVersionUID = 1210297822406590918L;

	private Long entityId;

	private String createBy;

	private String modBy;

	private Timestamp createDt;

	private Timestamp modDt;

	private List<ErrorMsgDTO> msgList;

	public Long getEntityId() {
		return entityId;
	}

	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getModBy() {
		return modBy;
	}

	public void setModBy(String modBy) {
		this.modBy = modBy;
	}

	public Timestamp getCreateDt() {
		return createDt;
	}

	public void setCreateDt(Timestamp createDt) {
		this.createDt = createDt;
	}

	public Timestamp getModDt() {
		return modDt;
	}

	public void setModDt(Timestamp modDt) {
		this.modDt = modDt;
	}

	public List<ErrorMsgDTO> getMsgList() {
		return msgList;
	}

	public void setMsgList(List<ErrorMsgDTO> msgList) {
		this.msgList = msgList;
	}
	
	

}
