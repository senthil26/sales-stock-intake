package com.mns.ssi.tech.core.ui.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SSIGridDetails<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6602678680138591397L;

	private long limit;
	private long start;
	private List<T> rows = new ArrayList<T>();
	private long size;

	public long getLimit() {
		return limit;
	}

	public void setLimit(long limit) {
		this.limit = limit;
	}

	public long getStart() {
		return start;
	}

	public void setStart(long start) {
		this.start = start;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	@SuppressWarnings("hiding")
	public <T> SSIGridDetails<T> getPagedList(List<T> rows, long start, long limit, long size) {
		SSIGridDetails<T> p = new SSIGridDetails<T>();
		p.setRows(rows);
		p.setLimit(limit);
		p.setSize(size);
		p.setStart(start);
		return p;
	}
}
