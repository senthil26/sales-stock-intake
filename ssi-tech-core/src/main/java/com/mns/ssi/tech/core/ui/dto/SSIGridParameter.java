package com.mns.ssi.tech.core.ui.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.mns.ssi.tech.core.exception.SSICoreException;

public class SSIGridParameter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9194926979304300431L;

	private int start = 0;
	private int limit = 0;
	private String className;
	private String basicSearch;
	private List<String> basicSearchAttribues = new ArrayList<String>();
	private String parentPath;
	private List<String> orderByFields;
	private OrderByType orderBy;
	private Map<String, Object> advancedSearch;
	private String parentId;

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public Map<String, Object> getAdvancedSearch() {
		return advancedSearch;
	}

	public void setAdvancedSearch(Map<String, Object> advancedSearch) {
		this.advancedSearch = advancedSearch;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Class<?> getPagedClass() {
		if (className == null) {
			return null;
		} else {
			try {
				return Class.forName(className);
			} catch (Exception ex) {
				throw new SSICoreException(ex);
			}
		}
	}

	public String getBasicSearch() {
		return basicSearch;
	}

	public void setBasicSearch(String basicSearch) {
		this.basicSearch = basicSearch;
	}

	public List<String> getBasicSearchAttribues() {
		return basicSearchAttribues;
	}

	public void setBasicSearchAttribues(List<String> basicSearchAttribues) {
		this.basicSearchAttribues = basicSearchAttribues;
	}

	public String getParentPath() {
		return parentPath;
	}

	public void setParentPath(String parentPath) {
		this.parentPath = parentPath;
	}

	public OrderByType getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(OrderByType orderBy) {
		this.orderBy = orderBy;
	}

	public List<String> getOrderByFields() {
		return orderByFields;
	}

	public void setOrderByFields(List<String> orderByFields) {
		this.orderByFields = orderByFields;
	}

}
