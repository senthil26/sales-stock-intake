package com.mns.ssi.tech.core.service.impl;

import com.mns.ssi.tech.core.exception.SSICoreException;
import com.mns.ssi.tech.core.logger.factory.LoggerFactory;
import com.mns.ssi.tech.core.logger.service.CoreLogger;
import com.mns.ssi.tech.core.service.SSIDocumentCoreBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class SSIDocumentCoreBaseServiceImpl implements SSIDocumentCoreBaseService {
    private static final CoreLogger LOG = LoggerFactory.getLogger(SSIDocumentCoreBaseServiceImpl.class);

    private final MongoOperations mongo;

    @Autowired
    public SSIDocumentCoreBaseServiceImpl(MongoOperations mongo) {
        this.mongo = mongo;
    }

    @Override
    public <T> void create(Iterable<T> entities) {
        mongo.save(entities);
    }

    @Override
    public <T> void create(T entity) {
        mongo.save(entity);
    }

    @Override
    public <T> List<T> findByFieldsMatchingAny(Class<T> documentClass, Map<String, ?> fieldsToFind) {
        if(fieldsToFind == null ||
                fieldsToFind.entrySet().stream().anyMatch(e -> StringUtils.isEmpty(e.getKey()))) {
            throw new SSICoreException("One or more field values is invalid");
        }

        Query query = Query.query(queryCriteria(fieldsToFind));
        LOG.debug(query.toString());
        return mongo.find(Query.query(queryCriteria(fieldsToFind)), documentClass);
    }

    @Override
    public <T> List<T> findByFieldsMatchingIn(Class<T> documentClass, Map<String, List<?>> fieldsToFind) {
        if(fieldsToFind == null ||
                fieldsToFind.entrySet().stream().anyMatch(e -> StringUtils.isEmpty(e.getKey()))) {
            throw new SSICoreException("One or more field values is invalid");
        }

        Query query = Query.query(queryInCriteria(fieldsToFind));
        LOG.debug(query.toString());
        return mongo.find(Query.query(queryInCriteria(fieldsToFind)), documentClass);
    }

    @Override
    public <T, I> List<T> find(Class<T> documentClass, Function<I, Query> toQuery, I fromItem) {
        Query query = toQuery.apply(fromItem);
        LOG.debug(query.toString());
        return mongo.find(query, documentClass);
    }

    private Criteria queryCriteria(Map<String, ?> fieldsToFind) {
        List<Criteria> conditions = fieldsToFind.entrySet().stream()
                .map(e ->  Criteria.where(e.getKey()).is(e.getValue()))
                .collect(Collectors.toList());

        Criteria queryCriteria = new Criteria();
        queryCriteria.orOperator(conditions.toArray(new Criteria[conditions.size()]));
        return queryCriteria;
    }

    @Override
    public <T> void update(Class<T> entityClass, Map<String, ?> fieldsToFilter,
                           Map<String, ?> fieldsToUpdate) {
        Query query = Query.query(queryCriteria(fieldsToFilter));
        Update update = filterCriteria(fieldsToUpdate);

        mongo.updateMulti(query, update, entityClass);
    }

    @Override
    public <T> void upsert(Class<T> entityClass, Map<String, ?> fieldsToFilter,
                           Map<String, ?> fieldsToUpdate) {
        Query query = Query.query(queryCriteria(fieldsToFilter));
        Update update = filterCriteria(fieldsToUpdate);

        mongo.upsert(query, update, entityClass);
    }

    @Override
    public <T> void delete(Class<T> entityClass, Map<String, ?> fieldsToFilter) {
        Query query = Query.query(queryCriteria(fieldsToFilter));
        mongo.findAllAndRemove(query, entityClass);
    }

    private Criteria queryInCriteria(Map<String, List<?>> fieldsToFind) {
        List<Criteria> conditions = fieldsToFind.entrySet().stream()
                .map(e ->  Criteria.where(e.getKey()).in(e.getValue()))
                .collect(Collectors.toList());

        Criteria queryCriteria = new Criteria();
        queryCriteria.orOperator(conditions.toArray(new Criteria[conditions.size()]));
        return queryCriteria;
    }

    private Update filterCriteria(Map<String, ?> fieldsToFind) {
        Update update = new Update();
        fieldsToFind.entrySet().stream()
                .filter(e -> e != null && e.getKey() != null && e.getValue() != null)
                .forEach(e -> update.set(e.getKey(), e.getValue()));

        return update;
    }

}
