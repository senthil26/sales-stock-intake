package com.mns.ssi.tech.core.service;

import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.util.List;
import java.util.Map;

import com.mns.ssi.tech.core.entity.SSIBaseEntity;
import com.mns.ssi.tech.core.ui.dto.SSIGridDetails;
import com.mns.ssi.tech.core.ui.dto.OrderByType;

public interface SSIRelationalCoreBaseService {

	<T extends SSIBaseEntity> T create(T o);

	<T extends SSIBaseEntity> T update(T o);

	<T extends SSIBaseEntity> T remove(final Class<T> entityClass, final Object recordId);

	<T extends SSIBaseEntity> T find(T o);

	<T extends SSIBaseEntity> List<T> list(T o);

	<T extends SSIBaseEntity> List<T> findAll(Class<T> clazz);

	<T extends SSIBaseEntity> boolean isDuplicate(final Class<T> entityClass, Map<String, Object> params);

	<T extends SSIBaseEntity> List<T> findByRecordIds(final Class<T> entityClass, final List<Object> recordIds,
                                                      boolean isNegative);

	<T extends SSIBaseEntity> T findById(final Class<T> entityClass, final Long recordId);

	<T extends SSIBaseEntity> List<T> findFieldIds(final Class<T> entityClass, final String searchField,
                                                   final List<Object> filedValues, boolean isNegative);

	void loadFile(OutputStream os, Long recordID);

	void remove(String className, Long recordId);

	Blob getBlob(InputStream document, Long fileSize);
	
	<T extends SSIBaseEntity> SSIGridDetails<T> findAllInGridWithParam(Class<T> clazz, Map<String, Object> params, int start, int limit, List<String> orderByFields, OrderByType orderType);
	
	<T extends SSIBaseEntity> List<T> findAllWithParam(final Class<T> entityClass, final Map<String, Object> params);
	



	/**
	 * 
	 * @param rows
	 * @param start
	 * @param limit
	 * @param size
	 * @return
	 */
	<T> SSIGridDetails<T> convertToGridPage(List<T> rows, int start, int limit);

	<T> T findByNativeQueryWithParam(final Class<T> classDTO, final String queryString, Object... params);
	
	/**
	 * Return List of entities according to params
	 * 
	 * @param queryString
	 *            : query string
	 * @param params
	 *            : params Obs: Use this method in complex query
	 * @return
	 */
	<T extends SSIBaseEntity> List<T> findByQuery(String queryString, Object... params);
	
}
