package com.mns.ssi.tech.core.dao;

import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.util.List;
import java.util.Map;

import com.mns.ssi.tech.core.entity.SSIBaseEntity;

import com.mns.ssi.tech.core.ui.dto.OrderByType;
import com.mns.ssi.tech.core.ui.dto.SSIGridDetails;

/**
 * 
 * @author desha
 *
 */
public interface SSICoreBaseDao {
	
	/**
	 * 
	 * @param entity
	 * @return
	 */
	<T extends SSIBaseEntity> T createEntity(T entity);

	/**
	 * 
	 * @param entity
	 * @return
	 */
	<T extends SSIBaseEntity> T updateEntity(T entity);

	/**
	 * 
	 * @param entity
	 * @return
	 */
	<T> T updateEntity(T entity);

	/**
	 * 
	 * @param queryString
	 * @param params
	 * @return
	 */
	<T extends SSIBaseEntity> void updateByQuery(final String queryString, final Object... params);

	/**
	 * 
	 * @param entityClass
	 * @param primaryKey
	 * @return
	 */
	<T extends SSIBaseEntity> T deleteEntity(final Class<T> entityClass, final Object primaryKey);

	/**
	 * 
	 * @param entityClass
	 * @param primaryKey
	 * @return
	 */

	<T extends SSIBaseEntity> T findById(final Class<T> entityClass, final Object primaryKey);

	/**
	 * 
	 * @param entityClass
	 * @param searchField
	 * @param recordIds
	 * @return
	 */

	<T extends SSIBaseEntity> List<T> findByIds(final Class<T> entityClass, final List<Long> recordIds, boolean isNegative);

	/**
	 * This method will search the field value by using in clause in SQL
	 * 
	 * @param entityClass
	 * @param searchField
	 * @param filedValues
	 * @return
	 */
	<T extends SSIBaseEntity, I> List<T> findByFields(final Class<T> entityClass, final String searchField,
                                                   final List<I> filedValues, boolean isNegative);

	/**
	 * 
	 * @param entityClass
	 * @param paramNames
	 * @param params
	 * @return Valid Entity if the search criteria matched else it will throw exception
	 */
	<T extends SSIBaseEntity> T findByBusinessKey(final Class<T> entityClass, final Map<String, Object> params);

	/**
	 * 
	 * @param entityClass
	 * @param paramNames
	 * @param params
	 * @return Valid Entity if the search criteria matched else it will throw exception
	 */
	<T extends SSIBaseEntity> List<T> findAllWithParam(final Class<T> entityClass, final Map<String, Object> params);

	/**
	 * 
	 * @param entityClass
	 * @param paramNames
	 * @param params
	 * @return Valid Entity if the search criteria matched else it will throw exception
	 */
	<T extends SSIBaseEntity> List<T> findAllWithParamOrderBy(final Class<T> entityClass,
                                                              final Map<String, Object> params, List<String> orderByFields, OrderByType orderType);

	/**
	 * 
	 * @param queryName
	 * @param params
	 * @return
	 */
	<T extends SSIBaseEntity> List<T> findBaseEntityByNamedQuery(final String queryName, final Object... params);

	/**
	 * 
	 * @param queryStr
	 * @param params
	 * @return
	 */
	<T extends SSIBaseEntity> List<T> findBaseEntityByQuery(final String queryStr, final Object... params);

	/**
	 * 
	 * @param clazzDTO
	 * @param queryString
	 * @param params
	 * @param start
	 * @param limit
	 * @return
	 */
	<T extends SSIBaseEntity> SSIGridDetails<T> findBaseEntityByQueryInGrid(final Class<T> clazzDTO, final String queryString,
                                                                            final List<String> params, int start, int limit);

	/**
	 * 
	 * @param clazzDTO
	 * @param queryString
	 * @param start
	 * @param limit
	 * @param params
	 * @return
	 */
	<T extends SSIBaseEntity> List<T> findBaseEntityPageByQuery(final Class<T> clazzDTO, final String queryString,
                                                                final int start, final int limit, final Object... params);

	/**
	 * 
	 * @param queryName
	 * @param params
	 * @return
	 */
	<T extends SSIBaseEntity> List<T> findBaseEntityByNamedQueryWithNamedParams(final String queryName,
                                                                                final Map<String, Object> params);

	/**
	 * 
	 * @param queryName
	 * @param params
	 * @return
	 */
	<T extends SSIBaseEntity> List<T> findPageByQuery(final String queryString, final int start, final int limit,
                                                      final Object... params);

	/**
	 * 
	 * @param entityClassDTO
	 * @param queryString
	 * @param start
	 * @param limit
	 * @param params
	 * @return
	 */
	<T> List<T> findNonBaseEntityPageByQuery(final Class<T> entityClassDTO, final String queryString, final int start,
                                             final int limit, final Object... params);

	/**
	 * 
	 * @param queryName
	 * @param params
	 * @return
	 */
	<T extends SSIBaseEntity> List<T> findByQuery(final String queryString, final Object... params);

	/**
	 * 
	 * @param queryStr
	 * @param params
	 * @return
	 */
	<T extends SSIBaseEntity> List<T> findBaseEntityByQueryWithNamedParams(final String queryStr,
                                                                           final Map<String, Object> params);

	/**
	 * 
	 * @param clazz
	 * @return
	 */
	<T extends SSIBaseEntity> List<T> findAll(final Class<T> clazz);

	/**
	 * 
	 * @param clazz
	 * @return
	 */
	<T extends SSIBaseEntity> Long countAll(final Class<T> clazz);

	/**
	 * 
	 * @param clazz
	 * @return
	 */
	Long countByQuery(final String queryName, final Object... params);

	/**
	 * 
	 * @param entityClass
	 * @param params
	 * @return boolean
	 */
	<T extends SSIBaseEntity> boolean isDuplicate(final Class<T> entityClass, Map<String, Object> params);

	/**
	 * 
	 * @param className
	 * @param primaryKey
	 */
	<T extends SSIBaseEntity> void deleteEntity(String className, Object primaryKey);

	/**
	 * 
	 * @param clazz
	 * @param params
	 * @param start
	 * @param limit
	 * @param orderByFields
	 * @param orderType
	 * @return
	 */
	<T extends SSIBaseEntity> SSIGridDetails<T> findAllInGridWithParam(Class<T> clazz, Map<String, Object> params, int start,
                                                                       int limit, List<String> orderByFields, OrderByType orderType);

	/**
	 * 
	 * @param clazz
	 * @param start
	 * @param limit
	 * @param orderByFields
	 * @param orderType
	 * @return
	 */
	<T extends SSIBaseEntity> SSIGridDetails<T> findAllInGrid(Class<T> clazz, int start, int limit,
                                                              List<String> orderByFields, OrderByType orderType);

	/**
	 * 
	 * @param className
	 * @param searchValue
	 * @param start
	 * @param limit
	 * @param searchFields
	 * @param orderByFields
	 * @param orderType
	 * @return
	 */
	<T extends SSIBaseEntity> SSIGridDetails<T> findLikeInFieldsInGrid(String className, String searchValue, int start,
                                                                       int limit, List<String> searchFields, List<String> orderByFields, OrderByType orderType);

	/**
	 * 
	 * @param classz
	 * @param map
	 * @param orderBy
	 * @return
	 */
	<T extends SSIBaseEntity> List<T> findByAttributes(Class<T> classz, Map<String, Object> map, String... orderBy);

	/**
	 * 
	 * @param clazz
	 * @return
	 */
	<T extends SSIBaseEntity> List<T> findOnly(final Class<T> clazz);

	/**
	 * 
	 * @param queryString
	 * @param params
	 * @return
	 */
	<T extends SSIBaseEntity> T findObjectByQuery(final String queryString, final Object... params);

	/**
	 * 
	 * @param queryString
	 * @return
	 */
	<T extends SSIBaseEntity> List<T> findByQueryWithoutParam(final String queryString);

	/**
	 * 
	 * @param os
	 * @param recordID
	 * @param entityClass
	 * @param attribute
	 */
	<T extends SSIBaseEntity> void loadDataToStream(OutputStream os, Long recordID, Class<T> entityClass, String attribute);

	// ////////NonBaseEntity Methods////////////////////////////////////////////////////////////////
	/**
	 * 
	 * @param entityClass
	 * @param primaryKey
	 * @return
	 */
	<T> T findNonBaseEntityById(final Class<T> entityClass, final Object primaryKey);

	/**
	 * 
	 * @param entityClass
	 * @param queryString
	 * @param params
	 * @return
	 */
	<T> List<T> findNonBaseEntityByQuery(final Class<T> entityClass, final String queryString, final Object... params);

	/**
	 * 
	 * @param queryString
	 * @param params
	 * @return
	 */
	<T> List<T> findNonBaseEntityByQuery(final String queryString, final Object... params);

	/**
	 * 
	 * @param queryString
	 * @param params
	 * @param start
	 * @param limit
	 * @return
	 */
	<T> SSIGridDetails<T> findNonBaseEntityByQueryInGrid(final Class<T> classzDTO, final String queryString,
                                                         List<String> params, int start, int limit);

	/**
	 * 
	 * @param entityClass
	 * @param primaryKey
	 * @return
	 */
	<T> T findByIdNonBaseEntity(final Class<T> entityClass, final Object primaryKey);

	/**
	 * 
	 * @param classDTO
	 * @param queryString
	 * @return
	 */
	<T> List<T> findByNativeQuery(final Class<T> classDTO, final String queryString);

	/**
	 * 
	 * @param classDTO
	 * @param queryString
	 * @param start
	 * @param limit
	 * @param params
	 * @return
	 */
	<T> SSIGridDetails<T> findByNativeQueryInGrid(final Class<T> classDTO, final String queryString, int start, int limit,
                                                  final Object... params);

	/**
	 * 
	 * @param classDTO
	 * @param queryString
	 * @param params
	 * @return
	 */
	<T> T findByNativeQueryWithParam(final Class<T> classDTO, final String queryString, Object... params);

	/**
	 * Method is created for allow past a query with paremeters and set a list(improve performance with cache
	 * preparedstatement)
	 * 
	 * @param classDTO
	 * @param queryString
	 * @param params
	 * @return
	 */
	<T> List<T> findByNativeQueryListWithParam(Class<T> classDTO, String queryString, Object... params);

	Blob getBlob(InputStream document, Long fileSize);


	<T> List<T> callProcedureWithNamedQuery(String namedQuery, Map<String, Object> params);
	
	<T> int deleteAllEntities(Class<T> classDTO);

	<T> int truncateTable(Class<T> classDTO);

}
