package com.mns.ssi.tech.core.entity;

import java.io.Serializable;

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

	/**
	 * The persistent column for the ID_ENTIDAD database table column
	 */
	@Id
	@JsonInclude
	@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
	@Column(name="id")
	protected Long entityId;
	

	/**
	 * 
	 */

	/*@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "MODIFIED_BY")
	private String modifiedBy;

	@Column(name = "CREATED_DT", insertable = false)
	private Timestamp createdDt;

	@Column(name = "MODIFIED_DT")
	private Timestamp modifiedDt;*/

	/**
	 * Transient object to manage old state of entities for audit
	 *//*
	@Transient
	private HashMap<String, Object> _old;

	*//**
	 * 
	 * 
	 * /* (non-Javadoc)
	 * 
	 * @see Object#hashCode()
	 *//*
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((entityId == null) ? 0 : entityId.hashCode());
		return result;
	}

	
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SSIBaseEntity other = (SSIBaseEntity) obj;
		if (entityId == null) {
			if (other.entityId != null)
				return false;
		} else if (!entityId.equals(other.entityId))
			return false;
		return true;
	}

	
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 
	@Override
	public String toString() {
		StringBuilder buff = new StringBuilder();
		buff.append(super.getClass().getSimpleName());
		Long dummy = this.entityId;
		if (dummy == null) {
			buff.append(" not yet persisted");
		} else {
			buff.append(dummy);
		}
		buff.append("]");
		return buff.toString();
	}*/

	/**
	 * Gets the model id.
	 * 
	 * @return the model id
	 */
	public Long getEntityId() {
		return entityId;
	}

	/**
	 * Sets the model id.
	 * 
	 * @param entityId
	 *            the new model id
	 */
	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}

	/**
	 * Gets the _old.
	 * 
	 * @return the old state, for audit propose
	 *//*
	public HashMap<String, Object> get_old() {
		return _old;
	}

	*//**
	 * Set_old.
	 * 
	 * @param _old
	 *            the _old
	 *//*
	public void set_old(HashMap<String, Object> _old) {
		this._old = _old;
	}
*/
	/*public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Timestamp getCreatedDt() {
		return createdDt;
	}

	public void setCreatedDt(Timestamp createdDt) {
		this.createdDt = createdDt;
	}*/

	/*public Timestamp getModifiedDt() {
		return modifiedDt;
	}

	public void setModifiedDt(Timestamp modifiedDt) {
		this.modifiedDt = modifiedDt;
	}*/

	
}
