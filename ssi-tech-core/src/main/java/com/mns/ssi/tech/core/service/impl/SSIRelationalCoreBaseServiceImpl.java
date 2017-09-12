package com.mns.ssi.tech.core.service.impl;

import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.util.List;
import java.util.Map;

import com.mns.ssi.tech.core.entity.SSIBaseEntity;
import com.mns.ssi.tech.core.exception.SSICoreException;
import org.springframework.beans.factory.annotation.Autowired;

import com.mns.ssi.tech.core.dao.SSICoreBaseDao;
import com.mns.ssi.tech.core.service.SSIRelationalCoreBaseService;
import com.mns.ssi.tech.core.ui.dto.OrderByType;
import com.mns.ssi.tech.core.ui.dto.SSIGridDetails;
import com.mns.ssi.tech.core.ui.dto.SSIGridParameter;
import org.springframework.stereotype.Service;

@Service
public class SSIRelationalCoreBaseServiceImpl implements SSIRelationalCoreBaseService {

    private SSICoreBaseDao sSICoreBaseDao;

    @Autowired
    public SSIRelationalCoreBaseServiceImpl(SSICoreBaseDao sSICoreBaseDao) {
        this.sSICoreBaseDao = sSICoreBaseDao;
    }

    public SSICoreBaseDao getsSICoreBaseDao() {
        return sSICoreBaseDao;
    }

    public void setsSICoreBaseDao(SSICoreBaseDao sSICoreBaseDao) {
        this.sSICoreBaseDao = sSICoreBaseDao;
    }

    public <T extends SSIBaseEntity> T create(T o) {
        return sSICoreBaseDao.createEntity(o);
    }

    public <T extends SSIBaseEntity> T update(T o) {

        return sSICoreBaseDao.updateEntity(o);
    }

    public <T extends SSIBaseEntity> T remove(Class<T> entityClass, Object recordId) {
        return sSICoreBaseDao.deleteEntity(entityClass, recordId);
    }

    public <T extends SSIBaseEntity> T find(T o) {
        // TODO
        return null;
    }

    public <T extends SSIBaseEntity> List<T> findAll(Class<T> clazz) {
        return sSICoreBaseDao.findAll(clazz);
    }

    public <T extends SSIBaseEntity> boolean isDuplicate(Class<T> entityClass, Map<String, Object> params) {
        return sSICoreBaseDao.isDuplicate(entityClass, params);
    }

    public <T extends SSIBaseEntity> T findById(Class<T> entityClass, Long recordId) {
        return sSICoreBaseDao.findById(entityClass, recordId);
    }

    public <T extends SSIBaseEntity, I> List<T> findFieldIds(Class<T> entityClass, String searchField,
                                                             List<I> filedValues, boolean isNegative) {
        return sSICoreBaseDao.findByFields(entityClass, searchField, filedValues, isNegative);
    }

    public void remove(String className, Long recordId) {
        sSICoreBaseDao.deleteEntity(className, recordId);

    }

    public Blob getBlob(InputStream document, Long fileSize) {
        return sSICoreBaseDao.getBlob(document, fileSize);
    }

    public <T extends SSIBaseEntity> List<T> list(T o) {
        // TODO Auto-generated method stub
        return null;
    }

    public <T extends SSIBaseEntity> List<T> findByRecordIds(Class<T> entityClass, List<Object> recordIds,
                                                             boolean isNegative) {
        // TODO Auto-generated method stub
        return null;
    }

    public void loadFile(OutputStream os, Long recordID) {
        // TODO Auto-generated method stub

    }

    public <T> List<T> findByNativeQuery(Class<T> classDTO, String queryString) {
        return sSICoreBaseDao.findByNativeQuery(classDTO, queryString);
    }

    public <T extends SSIBaseEntity> List<T> findByQueryWithoutParam(String queryString) {
        return sSICoreBaseDao.findByQueryWithoutParam(queryString);
    }

    public <T> SSIGridDetails<T> findByNativeQueryInGrid(Class<T> classDTO, String queryString, int start, int limit,
                                                         Object... params) {
        return sSICoreBaseDao.findByNativeQueryInGrid(classDTO, queryString, start, limit, params);
    }

    @Override
    public <T> T findByNativeQueryWithParam(Class<T> classDTO, String queryString, Object... params) {
        return sSICoreBaseDao.findByNativeQueryWithParam(classDTO, queryString, params);
    }

    public <T extends SSIBaseEntity> SSIGridDetails<T> findAllInGridWithParam(Class<T> clazz, Map<String, Object> params,
                                                                              int start, int limit, List<String> orderByFields, OrderByType orderType) {
        return sSICoreBaseDao.findAllInGridWithParam(clazz, params, start, limit, orderByFields, orderType);
    }

    @Override
    public <T> SSIGridDetails<T> convertToGridPage(List<T> rows, int start, int limit) {

        SSIGridDetails<T> pageList = new SSIGridDetails<T>();
        pageList.setRows(rows);
        pageList.setStart(start);
        pageList.setLimit(limit);
        pageList.setSize(rows.size());
        return pageList;

    }

    public <T extends SSIBaseEntity> SSIGridDetails<T> findAllInGrid(SSIGridParameter gridParam) {
        String searchValue = gridParam.getBasicSearch();
        int start = gridParam.getStart();
        int limit = gridParam.getLimit();
        String className = gridParam.getClassName();
        List<String> searchFields = gridParam.getBasicSearchAttribues();
        List<String> orderByFields = gridParam.getOrderByFields();
        OrderByType orderType = gridParam.getOrderBy();
        Map<String, Object> advancedSearchParams = gridParam.getAdvancedSearch();

        if (advancedSearchParams != null && advancedSearchParams.size() > 0) {
            @SuppressWarnings("unchecked")
            Class<T> pagedClass = (Class<T>) gridParam.getPagedClass();
            return sSICoreBaseDao.findAllInGridWithParam(pagedClass, advancedSearchParams, start, limit, orderByFields,
                    orderType);
        } else if (searchValue != null && searchValue.trim().length() > 0) {
            return sSICoreBaseDao.findLikeInFieldsInGrid(className, searchValue, start, limit, searchFields,
                    orderByFields, orderType);
        } else {
            @SuppressWarnings("unchecked")
            Class<T> pagedClass = (Class<T>) gridParam.getPagedClass();
            return sSICoreBaseDao.findAllInGrid(pagedClass, start, limit, orderByFields, orderType);
        }

    }

    public <T extends SSIBaseEntity> List<T> findAllWithParam(Class<T> entityClass, Map<String, Object> params) {
        if (params != null && !params.isEmpty()) {
            return (List<T>) sSICoreBaseDao.findAllWithParam(entityClass, params);

        } else {
            throw new SSICoreException();
        }
    }

    public <T extends SSIBaseEntity> List<T> findByQuery(String queryString, Object... params) {
        return sSICoreBaseDao.findByQuery(queryString, params);
    }

    public <T> List<T> findNonBaseEntityByQuery(final Class<T> entityClass, final String queryString, final Object... params) {
        return sSICoreBaseDao.findNonBaseEntityByQuery(entityClass, queryString, params);
    }
}
