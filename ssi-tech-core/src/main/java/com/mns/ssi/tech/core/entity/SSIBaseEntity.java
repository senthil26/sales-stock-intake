package com.mns.ssi.tech.core.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 *
 * @author desha
 *
 *         The base persistent class for database tables
 */
@MappedSuperclass
public abstract class SSIBaseEntity implements Serializable {

	private static final long serialVersionUID = -5479188943101445954L;

	@Id
	@JsonInclude
	@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
	@Column(name="ENTITY_ID")
	private Long entityId;

	@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "MODIFIED_BY")
	private String modifiedBy;

	@Column(name = "CREATED_DATE", insertable = false)
	private Timestamp createdDate;

	@Column(name = "MODIFIED_DATE")
	private Timestamp modifiedDate;

	public Long getEntityId() {
		return entityId;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public Timestamp getModifiedDate() {
		return modifiedDate;
	}
}