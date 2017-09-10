package com.mns.ssi.tech.core.dao.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.mns.ssi.tech.core.constants.SSICoreConstant;
import com.mns.ssi.tech.core.entity.SSIBaseEntity;
import com.mns.ssi.tech.core.exception.SSICoreException;
import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mns.ssi.tech.core.dao.SSICoreBaseDao;
import com.mns.ssi.tech.core.dao.mapper.QueryResultsMapper;
import com.mns.ssi.tech.core.dao.message.SSIDaoMessage;
import com.mns.ssi.tech.core.ui.dto.SSIGridDetails;
import com.mns.ssi.tech.core.ui.dto.OrderByType;

/**
 * 
 * @author desha
 *
 */
@Repository("sSICoreBaseDao")
@Transactional
public class SSICoreBaseDaoImpl implements SSICoreBaseDao {
	private static final String RECORD_ID = "entityId";

	@PersistenceContext
	private EntityManager entityManager;


	/**
	 * 
	 * @param entity
	 * @return this will return the saved entity if persist is successful
	 */

	public <T extends SSIBaseEntity> T createEntity(T entity) {

		if (entity == null) {
			throw new SSICoreException(SSIDaoMessage.CAN_NOT_PERSIST_NULL_OBJECT);
		}
		try {
			entityManager.persist(entity);
			/* for default values in the database */
			entityManager.flush();
			entityManager.refresh(entity);
		} catch (Exception ex) {
			throw new SSICoreException(SSIDaoMessage.ENTITY_CREATION_FAILED, ex);
		}
		return entity;
	}

	/**
	 * 
	 * @param entity
	 * @return
	 */
	public <T extends SSIBaseEntity> T updateEntity(T entity) {
		T entityReturn = null;
		if (entity == null) {
			throw new SSICoreException(SSIDaoMessage.CAN_NOT_PERSIST_NULL_OBJECT);
		}
		try {
			entityReturn = entityManager.merge(entity);
		} catch (Exception ex) {
			throw new SSICoreException(SSIDaoMessage.ENTITY_UPDATE_FAILED, ex);
		}
		return entityReturn;
	}

	/**
	 * 
	 * @param entity
	 * @return
	 */
	public <T> T updateEntity(T entity) {
		T entityReturn = null;
		if (entity == null) {
			throw new SSICoreException(SSIDaoMessage.CAN_NOT_PERSIST_NULL_OBJECT);
		}
		try {
			entityReturn = entityManager.merge(entity);
		} catch (Exception ex) {
			throw new SSICoreException(SSIDaoMessage.ENTITY_UPDATE_FAILED, ex);
		}
		return entityReturn;
	}

	/**
	 * 
	 * @param queryString
	 * @param params
	 * @return
	 */
	public <T extends SSIBaseEntity> void updateByQuery(final String queryString, final Object... params) {
		// Validate the query and parameter
		if (queryString.isEmpty() && queryString.length() == 0) {
			throw new SSICoreException(SSIDaoMessage.BLANK_QUERY);
		}
		if (params == null || params.length == 0) {
			throw new SSICoreException(SSIDaoMessage.BLANK_PARAMETR);
		}

		try {
			Query query = addParamToQuery(queryString, params);
			query.executeUpdate();
		} catch (Exception ex) {
			throw new SSICoreException(SSIDaoMessage.ENTITY_UPDATE_FAILED, ex);
		}
	}

	/**
	 * 
	 * @param entityClass
	 * @param primaryKey
	 * @return
	 */
	public <T extends SSIBaseEntity> T deleteEntity(final Class<T> entityClass, final Object primaryKey) {

		T entity = entityManager.find(entityClass, primaryKey);

		if (entity == null) {
			throw new SSICoreException(SSIDaoMessage.CAN_NOT_PERSIST_NULL_OBJECT);
		}

		try {
			entityManager.remove(entity);
		} catch (Exception ex) {
			throw new SSICoreException(SSIDaoMessage.ENTITY_DELETE_FAILED, ex);
		}
		return entity;
	}

	/**
	 * 
	 * @param entityClass
	 * @param primaryKey
	 * @return
	 */

	public <T extends SSIBaseEntity> T findById(final Class<T> entityClass, final Object primaryKey) {

		if (primaryKey == null) {
			throw new SSICoreException(SSIDaoMessage.CAN_NOT_FIND_BLANK_RECORD);
		}

		try {
			T objectFound = entityManager.find(entityClass, primaryKey);
			if (objectFound != null) {
				entityManager.refresh(objectFound);
			}

			return objectFound;

		} catch (Exception ex) {
			throw new SSICoreException(SSIDaoMessage.ENTITY_FIND_FAILED, ex);
		}
	}

	/**
	 * 
	 * @param entityClass
	 * @param searchField
	 * @param recordIds
	 * @return
	 */

	public <T extends SSIBaseEntity> List<T> findByIds(final Class<T> entityClass, final List<Long> recordIds,
			boolean isNegative) {
		List<T> queryResult = null;
		try {
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
			Root<T> baseEntity = criteriaQuery.from(entityClass);
			criteriaQuery.select(baseEntity);
			Expression<Long> exp = baseEntity.get(RECORD_ID);
			Predicate predicate = exp.in(recordIds);
			if (isNegative) {
				criteriaQuery.where(predicate.not());
			} else {
				criteriaQuery.where(predicate);
			}
			criteriaQuery.orderBy(criteriaBuilder.asc(baseEntity.get(RECORD_ID)));

			queryResult = entityManager.createQuery(criteriaQuery).getResultList();
		} catch (Exception ex) {
			throw new SSICoreException(SSIDaoMessage.ENTITY_FIND_FAILED, ex);
		}

		return queryResult;

	}

	/**
	 * This method will search the field value by using in clause in SQL
	 * 
	 * @param entityClass
	 * @param searchField
	 * @param filedValues
	 * @return
	 */
	public <T extends SSIBaseEntity> List<T> findByFields(final Class<T> entityClass, final String searchField,
			final List<Object> filedValues, boolean isNegative) {
		List<T> queryResult = null;
		try {
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
			Root<T> baseEntity = criteriaQuery.from(entityClass);
			criteriaQuery.select(baseEntity);
			Expression<Object> exp = baseEntity.get(searchField);
			Predicate predicate = exp.in(filedValues);
			if (isNegative) {
				criteriaQuery.where(predicate.not());
			} else {
				criteriaQuery.where(predicate);
			}
			criteriaQuery.orderBy(criteriaBuilder.asc(baseEntity.get(RECORD_ID)));
			queryResult = entityManager.createQuery(criteriaQuery).getResultList();
		} catch (Exception ex) {
			throw new SSICoreException(SSIDaoMessage.ENTITY_FIND_FAILED, ex);
		}

		return queryResult;
	}

	/**
	 * 
	 * @param entityClass
	 * @param paramNames
	 * @param params
	 * @return Valid Entity if the search criteria matched else it will throw
	 *         exception
	 */
	public <T extends SSIBaseEntity> T findByBusinessKey(final Class<T> entityClass, final Map<String, Object> params) {

		T returnEntity = null;
		try {

			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
			Root<T> baseEntity = criteriaQuery.from(entityClass);
			List<Predicate> predicates = new ArrayList<Predicate>();
			// Add parameters to predicates
			addParameterToPredicate(params, criteriaBuilder, baseEntity, predicates);

			criteriaQuery.select(baseEntity).where(predicates.toArray(new Predicate[] {}));
			criteriaQuery.orderBy(criteriaBuilder.asc(baseEntity.get(RECORD_ID)));
			List<T> queryResult = entityManager.createQuery(criteriaQuery).getResultList();

			if (queryResult.isEmpty()) {
				return null;
			} else if (queryResult != null && queryResult.size() > 1) {
				throw new SSICoreException(SSIDaoMessage.DUPLICATE_RECORD);
			} else {
				returnEntity = queryResult.get(0);
			}
		} catch (Exception ex) {
			throw new SSICoreException(SSIDaoMessage.ENTITY_FIND_FAILED, ex);
		}
		return returnEntity;

	}

	private <T extends SSIBaseEntity> void addParameterToPredicate(final Map<String, Object> params,
			CriteriaBuilder criteriaBuilder, Root<T> baseEntity, List<Predicate> predicates) {
		for (Map.Entry<String, Object> param : params.entrySet()) {
			if (param.getKey() != null) {
				predicates.add(criteriaBuilder.equal(baseEntity.get(param.getKey()), param.getValue()));
			} else {
				throw new SSICoreException(SSIDaoMessage.INVALID_KEY);
			}
		}
	}

	/**
	 * 
	 * @param entityClass
	 * @param paramNames
	 * @param params
	 * @return Valid Entity if the search criteria matched else it will throw
	 *         exception
	 */
	public <T extends SSIBaseEntity> List<T> findAllWithParam(final Class<T> entityClass,
			final Map<String, Object> params) {
		List<T> queryResult = null;
		try {
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
			Root<T> baseEntity = criteriaQuery.from(entityClass);
			List<Predicate> predicates = new ArrayList<Predicate>();
			// Add parameters to predicates
			addParameterToPredicate(params, criteriaBuilder, baseEntity, predicates);
			criteriaQuery.select(baseEntity).where(predicates.toArray(new Predicate[] {}));
			criteriaQuery.orderBy(criteriaBuilder.asc(baseEntity.get(RECORD_ID)));
			queryResult = entityManager.createQuery(criteriaQuery).getResultList();

			if (queryResult.isEmpty()) {
				return null;
			}

		} catch (Exception ex) {
			throw new SSICoreException(SSIDaoMessage.ENTITY_FIND_FAILED, ex);
		}
		return queryResult;
	}

	/**
	 * 
	 * @param entityClass
	 * @param paramNames
	 * @param params
	 * @return Valid Entity if the search criteria matched else it will throw
	 *         exception
	 */
	public <T extends SSIBaseEntity> List<T> findAllWithParamOrderBy(final Class<T> entityClass,
			final Map<String, Object> params, List<String> orderByFields, OrderByType orderType) {
		List<T> queryResult = null;
		try {
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
			Root<T> baseEntity = criteriaQuery.from(entityClass);
			List<Predicate> predicates = new ArrayList<Predicate>();
			// Add parameters to predicates
			addParameterToPredicate(params, criteriaBuilder, baseEntity, predicates);

			criteriaQuery.select(baseEntity).where(predicates.toArray(new Predicate[] {}));
			// set Order by clause
			addOrderBy(orderByFields, orderType, criteriaBuilder, criteriaQuery, baseEntity);

			queryResult = entityManager.createQuery(criteriaQuery).getResultList();
			if (queryResult.isEmpty()) {
				return null;
			}
		} catch (Exception ex) {
			throw new SSICoreException(SSIDaoMessage.ENTITY_FIND_FAILED, ex);
		}
		return queryResult;

	}

	private <T extends SSIBaseEntity> void addOrderBy(List<String> orderByFields, OrderByType orderType,
			CriteriaBuilder criteriaBuilder, CriteriaQuery<T> criteriaQuery, Root<T> baseEntity) {
		if (!orderByFields.isEmpty()) {
			if (orderType.equals(OrderByType.DESC)) {
				for (String orderField : orderByFields) {
					criteriaQuery.orderBy(criteriaBuilder.desc(baseEntity.get(orderField)));
				}
			} else {
				for (String orderField : orderByFields) {
					criteriaQuery.orderBy(criteriaBuilder.asc(baseEntity.get(orderField)));
				}
			}
		} else {
			criteriaQuery.orderBy(criteriaBuilder.desc(baseEntity.get(RECORD_ID)));
		}
	}

	/**
	 * 
	 * @param queryName
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T extends SSIBaseEntity> List<T> findBaseEntityByNamedQuery(final String queryName, final Object... params) {
		final List<T> result;
		try {
			Query query;
			if (queryName != null) {
				query = entityManager.createNamedQuery(queryName);
			} else {
				throw new SSICoreException(SSIDaoMessage.BLANK_QUERY);
			}
			if (params == null || params.length == 0) {
				throw new SSICoreException(SSIDaoMessage.BLANK_PARAMETR);
			}
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i + 1, params[i]);
			}

			result = (List<T>) query.getResultList();
		} catch (Exception ex) {
			throw new SSICoreException(SSIDaoMessage.QUERY_EXECUTION_FAILED, ex);
		}
		return result;
	}

	/**
	 * 
	 * @param queryStr
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T extends SSIBaseEntity> List<T> findBaseEntityByQuery(final String queryStr, final Object... params) {

		final List<T> result;
		try {
			// Validate the query and parameter
			if (queryStr.isEmpty() && queryStr.length() == 0) {
				throw new SSICoreException(SSIDaoMessage.BLANK_QUERY);
			}
			if (params == null || params.length == 0) {
				throw new SSICoreException(SSIDaoMessage.BLANK_PARAMETR);
			}

			try {
				Query query = addParamToQuery(queryStr, params);
				result = (List<T>) query.getResultList();
			} catch (Exception e) {
				throw new SSICoreException(e);
			}
		} catch (Exception ex) {
			throw new SSICoreException(SSIDaoMessage.QUERY_EXECUTION_FAILED, ex);
		}
		return result;
	}

	private Query addParamToQuery(final String queryStr, final Object... params) {
		Query query = entityManager.createQuery(queryStr);

		for (int i = 0; i < params.length; i++) {
			if (params[i] != null) {
				query.setParameter(i + 1, params[i]);
			}
		}
		return query;
	}

	

	@SuppressWarnings("unchecked")
	public <T extends SSIBaseEntity> List<T> findBaseEntityPageByQuery(final Class<T> clazzDTO, final String queryString,
			final int start, final int limit, final Object... params) {
		final List<T> result;
		// Validate the query and parameter
		if (queryString.isEmpty() && queryString.length() == 0) {
			throw new SSICoreException(SSIDaoMessage.BLANK_QUERY);
		}

		try {
			Query query = addParamToQuery(queryString, params);
			if (start >= 0 && limit > 0) {
				query.setFirstResult(start);
				query.setMaxResults(limit);
			}
			result = (List<T>) query.getResultList();
		} catch (Exception ex) {
			throw new SSICoreException(SSIDaoMessage.QUERY_EXECUTION_FAILED, ex);
		}
		return result;
	}

	/**
	 * 
	 * @param queryName
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T extends SSIBaseEntity> List<T> findBaseEntityByNamedQueryWithNamedParams(final String queryName,
			final Map<String, Object> params) {
		final List<T> result;
		try {
			// Validate the query and parameter
			if (queryName.isEmpty() && queryName.length() == 0) {
				throw new SSICoreException(SSIDaoMessage.BLANK_QUERY);
			}
			if (params == null || params.isEmpty()) {
				throw new SSICoreException(SSIDaoMessage.BLANK_PARAMETR);
			}
			try {
				Query query = entityManager.createNamedQuery(queryName);
				for (final Map.Entry<String, ? extends Object> param : params.entrySet()) {
					query.setParameter(param.getKey(), param.getValue());

				}
				result = (List<T>) query.getResultList();
			} catch (Exception e) {
				throw new SSICoreException(e);
			}
		} catch (Exception ex) {
			throw new SSICoreException(SSIDaoMessage.QUERY_EXECUTION_FAILED, ex);
		}
		return result;
	}

	/**
	 * 
	 * @param queryName
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T extends SSIBaseEntity> List<T> findPageByQuery(final String queryString, final int start, final int limit,
			final Object... params) {
		final List<T> result;

		// Validate the query and parameter
		if (queryString.isEmpty() && queryString.length() == 0) {
			throw new SSICoreException(SSIDaoMessage.BLANK_QUERY);
		}

		try {
			Query query = entityManager.createQuery(queryString);
			if (params != null && params.length > 0) {
				for (int i = 0; i < params.length; i++) {
					query.setParameter(i + 1, params[i]);
				}
			}
			if (start >= 0 && limit > 0) {
				query.setFirstResult(start);
				query.setMaxResults(limit);
			}
			result = (List<T>) query.getResultList();
		} catch (Exception ex) {
			throw new SSICoreException(SSIDaoMessage.QUERY_EXECUTION_FAILED, ex);
		}
		return result;
	}

	/**
	 * 
	 * @param queryName
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T extends SSIBaseEntity> List<T> findPageByQueryWithoutLimit(final String queryString,
			final Object... params) {
		final List<T> result;

		// Validate the query and parameter
		if (queryString.isEmpty() && queryString.length() == 0) {
			throw new SSICoreException(SSIDaoMessage.BLANK_QUERY);
		}

		try {
			Query query = entityManager.createQuery(queryString);
			if (params != null && params.length > 0) {
				for (int i = 0; i < params.length; i++) {
					query.setParameter(i + 1, params[i]);
				}
			}
			result = (List<T>) query.getResultList();
		} catch (Exception ex) {
			throw new SSICoreException(SSIDaoMessage.QUERY_EXECUTION_FAILED, ex);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> findNonBaseEntityPageByQuery(final Class<T> clazzDTO, final String queryString, final int start,
			final int limit, final Object... params) {
		final List<T> result;
		// Validate the query and parameter
		if (queryString.isEmpty() && queryString.length() == 0) {
			throw new SSICoreException(SSIDaoMessage.BLANK_QUERY);
		}

		try {
			Query query = addParamToQuery(queryString, params);
			if (start >= 0 && limit > 0) {
				query.setFirstResult(start);
				query.setMaxResults(limit);
			}
			result = QueryResultsMapper.mapDTO(query.getResultList(), clazzDTO);
		} catch (Exception ex) {
			throw new SSICoreException(SSIDaoMessage.QUERY_EXECUTION_FAILED, ex);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> findNonBaseEntityPageByQueryWithoutLimit(final Class<T> clazzDTO, final String queryString,
			final Object... params) {
		final List<T> result;
		// Validate the query and parameter
		if (queryString.isEmpty() && queryString.length() == 0) {
			throw new SSICoreException(SSIDaoMessage.BLANK_QUERY);
		}

		try {
			Query query = addParamToQuery(queryString, params);
			result = QueryResultsMapper.mapDTO(query.getResultList(), clazzDTO);
		} catch (Exception ex) {
			throw new SSICoreException(SSIDaoMessage.QUERY_EXECUTION_FAILED, ex);
		}
		return result;
	}

	/**
	 * 
	 * @param queryName
	 * @param params
	 * @return
	 */

	@SuppressWarnings("unchecked")
	public <T extends SSIBaseEntity> List<T> findByQuery(final String queryString, final Object... params) {

		final List<T> result;
		// Validate the query and parameter
		if (queryString.isEmpty() && queryString.length() == 0) {
			throw new SSICoreException();
		}
		if (params == null || params.length == 0) {
			throw new SSICoreException();
		}

		try {
			Query query = addParamToQuery(entityManager, queryString, params);

			Object obj = query.getResultList();

			if (obj != null) {
				result = (List<T>) obj;
			} else {
				result = null;
			}

		} catch (Exception ex) {
			throw new SSICoreException(ex);
		}
		return result;
		}
	
	private Query addParamToQuery(final EntityManager entityManager, final String queryStr, final Object... params) {
		Query query = entityManager.createQuery(queryStr);

		for (int i = 0; i < params.length; i++) {
			if (params[i] != null) {
				query.setParameter(i + 1, params[i]);
			}
		}
		return query;
	}

	/**
	 * 
	 * @param queryStr
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T extends SSIBaseEntity> List<T> findBaseEntityByQueryWithNamedParams(final String queryStr,
			final Map<String, Object> params) {

		final List<T> result;
		// Validate the query and parameter
		if (queryStr.isEmpty() && queryStr.length() == 0) {
			throw new SSICoreException(SSIDaoMessage.BLANK_QUERY);
		}
		if (params == null || params.isEmpty()) {
			throw new SSICoreException(SSIDaoMessage.BLANK_PARAMETR);
		}

		try {
			Query query = entityManager.createQuery(queryStr);
			for (final Map.Entry<String, ? extends Object> param : params.entrySet()) {
				query.setParameter(param.getKey(), param.getValue());
			}
			result = (List<T>) query.getResultList();
		} catch (Exception ex) {
			throw new SSICoreException(SSIDaoMessage.QUERY_EXECUTION_FAILED, ex);
		}
		return result;
	}

	/**
	 * 
	 * @param clazz
	 * @return
	 */
	public <T extends SSIBaseEntity> List<T> findAll(final Class<T> clazz) {
		CriteriaQuery<T> criteriaQuery;
		try {
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			criteriaQuery = criteriaBuilder.createQuery(clazz);
			Root<T> baseEntity = criteriaQuery.from(clazz);
			criteriaQuery.orderBy(criteriaBuilder.asc(baseEntity.get(SSICoreConstant.PK_ID)));
 			return entityManager.createQuery(criteriaQuery).getResultList();
			
		} catch (Exception ex) {
			throw new SSICoreException(SSIDaoMessage.ENTITY_FIND_FAILED, ex);
		}

	}

	/**
	 * 
	 * @param clazz
	 * @return
	 */
	public <T extends SSIBaseEntity> Long countAll(final Class<T> clazz) {
		try {
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Long> cQuery = builder.createQuery(Long.class);
			Root<T> entity = cQuery.from(clazz);
			CriteriaQuery<Long> select = cQuery.select(builder.count(entity));
			return entityManager.createQuery(select).getResultList().get(0);
		} catch (Exception ex) {
			throw new SSICoreException(SSIDaoMessage.ENTITY_COUNT_FAILED, ex);
		}
	}

	/**
	 * 
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Long countByQuery(final String queryName, final Object... params) {
		final List<Long> result;

		// Validate the query and parameter
		if (queryName.isEmpty() && queryName.length() == 0) {
			throw new SSICoreException(SSIDaoMessage.BLANK_QUERY);
		}
		try {
			Query query = addParamToQuery(queryName, params);
			result = (List<Long>) query.getResultList();

		} catch (Exception ex) {
			throw new SSICoreException(SSIDaoMessage.QUERY_EXECUTION_FAILED, ex);
		}
		return Long.valueOf(result.get(0).toString());

	}

	/**
	 * 
	 * @param entityClass
	 * @param params
	 * @return boolean
	 */
	public <T extends SSIBaseEntity> boolean isDuplicate(final Class<T> entityClass, Map<String, Object> params) {

		try {
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
			Root<T> baseEntity = criteriaQuery.from(entityClass);
			List<Predicate> predicates = new ArrayList<Predicate>();
			for (Map.Entry<String, Object> param : params.entrySet()) {
				if (param.getKey() != null) {
					predicates.add(criteriaBuilder.equal(baseEntity.get(param.getKey()), param.getValue()));
				} else {
					throw new SSICoreException(SSIDaoMessage.INVALID_KEY);
				}
			}

			criteriaQuery.select(baseEntity).where(predicates.toArray(new Predicate[] {}));
			criteriaQuery.orderBy(criteriaBuilder.asc(baseEntity.get(RECORD_ID)));
			List<T> queryResult = entityManager.createQuery(criteriaQuery).getResultList();

			if (!queryResult.isEmpty() && queryResult.size() >= 1) {
				return true;

			} else {
				return false;
			}
		} catch (Exception ex) {
			throw new SSICoreException(SSIDaoMessage.ENTITY_FIND_FAILED, ex);
		}

	}

	@SuppressWarnings("unchecked")
	public <T extends SSIBaseEntity> void deleteEntity(String className, Object primaryKey) {

		Class<T> entityClass = null;
		try {
			entityClass = (Class<T>) Class.forName(className);
		} catch (Exception ex) {
			throw new SSICoreException(SSIDaoMessage.INVALID_CLASS_NAME + className + ex);
		}

		T entity = entityManager.find(entityClass, primaryKey);

		try {
			if (entity != null) {
				entityManager.remove(entity);
			} else {
				throw new SSICoreException(SSIDaoMessage.CAN_NOT_PERSIST_NULL_OBJECT);
			}

		} catch (Exception ex) {
			throw new SSICoreException(SSIDaoMessage.ENTITY_DELETE_FAILED, ex);
		}
	}

	public <T extends SSIBaseEntity> SSIGridDetails<T> findAllInGridWithParam(Class<T> clazz, Map<String, Object> params,
			int start, int limit, List<String> orderByFields, OrderByType orderType) {

		SSIGridDetails<T> pageList = new SSIGridDetails<T>();
		try {
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(clazz);
			Root<T> baseEntity = criteriaQuery.from(clazz);

			List<Predicate> predicates = new ArrayList<Predicate>();
			// add parameter to predicates
			addParameterToPredicate(params, criteriaBuilder, baseEntity, predicates);

			criteriaQuery.select(baseEntity).where(predicates.toArray(new Predicate[] {}));
			// set Order by clause
			addOrderBy(orderByFields, orderType, criteriaBuilder, criteriaQuery, baseEntity);
			// Order By setting finished

			List<T> queryResult = entityManager.createQuery(criteriaQuery).setFirstResult(start).setMaxResults(limit)
					.getResultList();

			List<T> queryResultTotal = entityManager.createQuery(criteriaQuery).getResultList();

			pageList.setRows(queryResult);
			pageList.setStart(start);
			pageList.setSize(queryResultTotal.size());
		} catch (Exception ex) {
			throw new SSICoreException(SSIDaoMessage.ENTITY_FIND_FAILED, ex);
		}
		return pageList;
	}

	public <T extends SSIBaseEntity> SSIGridDetails<T> findAllInGrid(Class<T> clazz, int start, int limit,
			List<String> orderByFields, OrderByType orderType) {

		SSIGridDetails<T> pageList = new SSIGridDetails<T>();
		try {
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(clazz);
			Root<T> baseEntity = criteriaQuery.from(clazz);
			criteriaQuery.select(baseEntity);
			// set Order by clause
			addOrderBy(orderByFields, orderType, criteriaBuilder, criteriaQuery, baseEntity);

			// Order By setting finished

			List<T> queryResult = entityManager.createQuery(criteriaQuery).setFirstResult(start).setMaxResults(limit)
					.getResultList();

			List<T> queryResultTotal = entityManager.createQuery(criteriaQuery).getResultList();

			pageList.setRows(queryResult);
			pageList.setStart(start);
			pageList.setSize(queryResultTotal.size());
		} catch (Exception ex) {
			throw new SSICoreException(SSIDaoMessage.ENTITY_FIND_FAILED, ex);
		}
		return pageList;
	}

	public <T extends SSIBaseEntity> SSIGridDetails<T> findLikeInFieldsInGrid(String className, String searchValue, int start,
			int limit, List<String> searchFields, List<String> orderByFields, OrderByType orderType) {

		SSIGridDetails<T> pageList = new SSIGridDetails<T>();
		List<Object> params = new ArrayList<Object>();

		try {

			params.add("%" + searchValue.toLowerCase() + "%");

			StringBuilder queryStr = new StringBuilder("SELECT x FROM ").append(className).append(" x ");

			if (searchValue != null && searchValue.trim().length() > 0) {

				queryStr.append(" WHERE ");
				// add Like to query
				addLikeToQuery(searchFields, queryStr);
				// add orderby To Query
				addOrderByToQuery(orderByFields, orderType, queryStr);

			}
			List<T> records = findPageByQuery(queryStr.toString(), start, limit, params.toArray());

			List<T> recordsSize = findPageByQueryWithoutLimit(queryStr.toString(), params.toArray());

			pageList.setRows(records);
			pageList.setStart(start);
			pageList.setSize(recordsSize.size());
		} catch (Exception ex) {
			throw new SSICoreException(SSIDaoMessage.ENTITY_FIND_FAILED, ex);
		}

		return pageList;

	}

	private void addOrderByToQuery(List<String> orderByFields, OrderByType orderType, StringBuilder queryStr) {
		if (!orderByFields.isEmpty()) {

			queryStr.append(" ORDER BY ");
			int countorderByField = 0;
			for (String orderField : orderByFields) {
				if (countorderByField > 1) {
					queryStr.append(",");
				}
				queryStr.append("x.").append(orderField);
				countorderByField++;
			}
			if (orderType.equals(OrderByType.DESC)) {
				queryStr.append(" DESC");
			} else {
				queryStr.append(" ASC");
			}

		} else {
			queryStr.append(" ORDER BY x.").append(RECORD_ID).append(" DESC");
		}
	}

	private void addLikeToQuery(List<String> searchFields, StringBuilder queryStr) {
		for (String fieldName : searchFields) {
			if (queryStr.indexOf("LIKE") > 0) {
				queryStr.append(" OR ");
			}
			queryStr.append("LOWER(x.").append(fieldName).append(") LIKE ?1 ");

		}
	}

	@SuppressWarnings("unchecked")
	public <T extends SSIBaseEntity> List<T> findByAttributes(Class<T> classz, Map<String, Object> map,
			String... orderBy) {
		try {
			return null;//BaseDAOHelper.getQuery(entityManager, classz, map, true, null, orderBy).getResultList();
		} catch (Exception ex) {
			throw new SSICoreException(SSIDaoMessage.ENTITY_FIND_FAILED, ex);
		}
	}

	public <T extends SSIBaseEntity> List<T> findOnly(final Class<T> clazz) {
		CriteriaQuery<T> criteriaQuery;
		try {
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			criteriaQuery = criteriaBuilder.createQuery(clazz);
			Root<T> base = criteriaQuery.from(clazz);
			criteriaQuery.orderBy(criteriaBuilder.asc(base.get("idEjecucion")));
			return entityManager.createQuery(criteriaQuery).getResultList();
		} catch (Exception ex) {
			throw new SSICoreException(SSIDaoMessage.ENTITY_FIND_FAILED, ex);
		}
	}

	@SuppressWarnings("unchecked")
	public <T extends SSIBaseEntity> T findObjectByQuery(final String queryString, final Object... params) {
		final T result;
		// Validate the query and parameter
		if (queryString.isEmpty() && queryString.length() == 0) {
			throw new SSICoreException(SSIDaoMessage.BLANK_QUERY);
		}
		if (params == null) {
			throw new SSICoreException(SSIDaoMessage.BLANK_PARAMETR);
		}
		try {
			Query query = addParamToQuery(queryString, params);
			result = (T) query.getSingleResult();
		} catch (Exception ex) {
			throw new SSICoreException(SSIDaoMessage.QUERY_EXECUTION_FAILED, ex);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public <T extends SSIBaseEntity> List<T> findByQueryWithoutParam(final String queryString) {
		final List<T> result;
		// Validate the query and parameter
		if (queryString.isEmpty() && queryString.length() == 0) {
			throw new SSICoreException(SSIDaoMessage.BLANK_QUERY);
		}

		try {
			Query query = entityManager.createQuery(queryString);
			result = (List<T>) query.getResultList();
		} catch (Exception ex) {
			throw new SSICoreException(SSIDaoMessage.QUERY_EXECUTION_FAILED, ex);
		}
		return result;
	}

	public <T extends SSIBaseEntity> void loadDataToStream(OutputStream os, Long recordID, Class<T> entityClass,
			String attribute) {
		try {
			T entity = findById(entityClass, recordID);

			Object obj = BeanUtils.getProperty(entity, attribute);
			InputStream inputStream;
			if (obj != null) {
				if (obj instanceof Blob) {
					inputStream = ((Blob) obj).getBinaryStream();
				} else if (obj instanceof Clob) {
					inputStream = ((Clob) obj).getAsciiStream();
				} else if (obj instanceof String) {
					inputStream = new ByteArrayInputStream(((String) obj).getBytes("UTF-8"));
				} else if (obj instanceof byte[]) {
					inputStream = new ByteArrayInputStream((byte[]) obj);
				} else {
					throw new SSICoreException(obj.getClass() + SSIDaoMessage.NOT_SUPPORTED_DATATYPE);
				}
				byte[] chunk = new byte[1024];
				int i = -1;
				while ((i = inputStream.read(chunk)) != -1) {
					os.write(chunk, 0, i);
				}
				inputStream.close();
			}
		} catch (Exception ex) {
			throw new SSICoreException(SSIDaoMessage.ENTITY_FIND_FAILED, ex);
		}

	}

	// //////////NonBaseEntity
	// Methods//////////////////////////////////////////////////////////////////////

	public <T> T findNonBaseEntityById(final Class<T> entityClass, final Object primaryKey) {

		if (primaryKey == null) {
			throw new SSICoreException(SSIDaoMessage.CAN_NOT_FIND_BLANK_RECORD);
		}

		try {
			T objectFound = entityManager.find(entityClass, primaryKey);
			return objectFound;

		} catch (Exception ex) {
			throw new SSICoreException(SSIDaoMessage.ENTITY_FIND_FAILED, ex);
		}
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> findNonBaseEntityByQuery(final Class<T> clazzDTO, final String queryString,
			final Object... params) {
		final List<T> result;
		// Validate the query and parameter
		if (queryString.isEmpty() && queryString.length() == 0) {
			throw new SSICoreException(SSIDaoMessage.BLANK_QUERY);
		}
		/*if (params == null || params.length == 0) {
			throw new SSICoreException(SSIDaoMessage.BLANK_PARAMETR);
		}*/
		try {
			Query query = entityManager.createQuery(queryString, clazzDTO);
			/*for (int i = 0; i < params.length; i++) {
				query.setParameter(i + 1, params[i]);
			}*/
			result = QueryResultsMapper.mapDTO(query.getResultList(), clazzDTO);
		} catch (Exception ex) {
			throw new SSICoreException(SSIDaoMessage.QUERY_EXECUTION_FAILED, ex);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> findNonBaseEntityByQuery(final String queryString, final Object... params) {
		final List<T> result;
		// Validate the query and parameter
		if (queryString.isEmpty() && queryString.length() == 0) {
			throw new SSICoreException(SSIDaoMessage.BLANK_QUERY);
		}
		if (params == null || params.length == 0) {
			throw new SSICoreException(SSIDaoMessage.BLANK_PARAMETR);
		}
		try {
			Query query = addParamToQuery(queryString, params);
			result = (List<T>) query.getResultList();
		} catch (Exception ex) {
			throw new SSICoreException(SSIDaoMessage.QUERY_EXECUTION_FAILED, ex);
		}
		return result;
	}

	public <T> SSIGridDetails<T> findNonBaseEntityByQueryInGrid(final Class<T> clazzDTO, final String queryString,
			final List<String> params, int start, int limit) {
		List<T> records = findNonBaseEntityPageByQuery(clazzDTO, queryString, start, limit, params);
		List<T> recordsSize = findNonBaseEntityPageByQueryWithoutLimit(clazzDTO, queryString, params);

		SSIGridDetails<T> pageList = new SSIGridDetails<T>();
		pageList.setRows(records);
		pageList.setStart(start);
		pageList.setSize(recordsSize.size());
		return pageList;
	}

	/*
	 * @param entityClass
	 * 
	 * @param primaryKey
	 * 
	 * @return
	 */
	public <T> T findByIdNonBaseEntity(final Class<T> entityClass, final Object primaryKey) {

		if (primaryKey == null) {
			throw new SSICoreException(SSIDaoMessage.CAN_NOT_FIND_BLANK_RECORD);
		}

		try {
			T objectFound = entityManager.find(entityClass, primaryKey);
			entityManager.refresh(objectFound);
			return objectFound;

		} catch (Exception ex) {
			throw new SSICoreException(SSIDaoMessage.ENTITY_FIND_FAILED, ex);
		}
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> findByNativeQuery(final Class<T> classDTO, final String queryString) {

		final List<T> listaAct;

		if (queryString.isEmpty() && queryString.length() == 0) {
			throw new SSICoreException(SSIDaoMessage.BLANK_QUERY);
		}

		try {
			final StringBuilder queryCriterios = new StringBuilder();
			queryCriterios.append(queryString);
			final Query query = entityManager.createNativeQuery(queryCriterios.toString(), classDTO);
			listaAct = (List<T>) query.getResultList();
		} catch (Exception ex) {
			throw new SSICoreException(SSIDaoMessage.QUERY_EXECUTION_FAILED, ex);
		}
		return listaAct;
	}

	@SuppressWarnings("unchecked")
	public <T> SSIGridDetails<T> findByNativeQueryInGrid(final Class<T> classDTO, final String queryString, int start,
			int limit, final Object... params) {

		final List<T> result;
		BigDecimal size = new BigDecimal(0);
		// Validate the query and parameter
		if (queryString.isEmpty() && queryString.length() == 0) {
			throw new SSICoreException(SSIDaoMessage.BLANK_QUERY);
		}

		try {
			Query query = entityManager.createNativeQuery(queryString, classDTO);
			if (params != null && params.length > 0) {
				for (int i = 0; i < params.length; i++) {
					query.setParameter(i + 1, params[i]);
				}
			}

			StringBuilder queryCountString = new StringBuilder();
			queryCountString.append(SSIDaoMessage.PRE_SELECT_COUNT);
			queryCountString.append(SSIDaoMessage.FROM);
			queryCountString.append(SSIDaoMessage.BRACKET_LEFT);
			queryCountString.append(queryString);
			queryCountString.append(SSIDaoMessage.BRACKET_RIGHT);

			Query queryCount = entityManager.createNativeQuery(queryCountString.toString());
						 
			if (params != null && params.length > 0) {
				for (int i = 0; i < params.length; i++) {
					queryCount.setParameter(i + 1, params[i]);
				}
			}
			size = (BigDecimal) queryCount.getSingleResult();

			if (start >= 0 && limit > 0) {
				query.setFirstResult(start);
				query.setMaxResults(limit);
			}
			result = (List<T>) query.getResultList();
		} catch (Exception ex) {
			throw new SSICoreException(SSIDaoMessage.QUERY_EXECUTION_FAILED, ex);
		}
		SSIGridDetails<T> pageList = new SSIGridDetails<T>();
		pageList.setRows(result);
		pageList.setStart(start);
		pageList.setSize(size.intValue());
		return pageList;
	}

	@SuppressWarnings("unchecked")
	public <T> T findByNativeQueryWithParam(final Class<T> classDTO, final String queryString, Object... params) {

		final T result;

		if (queryString.isEmpty() && queryString.length() == 0) {
			throw new SSICoreException(SSIDaoMessage.BLANK_QUERY);
		}

		try {

			Query query = entityManager.createNativeQuery(queryString, classDTO);
			if (params != null && params.length > 0) {
				for (int i = 0; i < params.length; i++) {
					query.setParameter(i + 1, params[i]);
				}
			}

			result = (T) query.getSingleResult();

		} catch (Exception ex) {
			System.out.println(ex);
			throw new SSICoreException(SSIDaoMessage.QUERY_EXECUTION_FAILED, ex);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> findByNativeQueryListWithParam(final Class<T> classDTO, final String queryString,
			Object... params) {
		return null;//methodBaseDAO.findByNativeQueryListWithParam(entityManager, classDTO, queryString, params);
	}

	

	
	public Blob getBlob(InputStream document, Long fileSize) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public <T extends SSIBaseEntity> SSIGridDetails<T> findBaseEntityByQueryInGrid(Class<T> clazzDTO, String queryString,
			List<String> params, int start, int limit) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> callProcedureWithNamedQuery(String namedQuery,Map<String,Object> params){
		Session session = entityManager.unwrap(Session.class);
		org.hibernate.Query querySession = session.getNamedQuery(namedQuery);
		
		if(params != null && params.size() > 0){
			for(Map.Entry<String, Object> param : params.entrySet()){
				querySession.setParameter(param.getKey(), param.getValue());
			}
		}
		
		return querySession.list();
		
	}
	
	public <T> int deleteAllEntities(Class<T> clazzDTO) {
	    String query = new StringBuilder("DELETE FROM ")
	                            .append(clazzDTO.getSimpleName())
	                            .append(" e")
	                            .toString();
	    return entityManager.createQuery(query).executeUpdate();
	}

	public <T> int truncateTable(Class<T> clazzDTO) {
	    String query = new StringBuilder("TRUNCATE TABLE ")
	                            .append(clazzDTO.getSimpleName())
	                            .toString();        
	    return entityManager.createNativeQuery(query).executeUpdate();
	}


}
