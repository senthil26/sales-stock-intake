package com.mns.ssi.tech.core.service;


import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

public interface SSIDocumentCoreBaseService {
    <T> void create(Iterable<T> entities);

    <T> void create(T entity);

    <T> List<T> findByFieldsMatchingAny(final Class<T> documentClass, Map<String, ?> fields);

    <T> List<T> findByFieldsMatchingIn(final Class<T> documentClass, Map<String, List<?>> fields);

    <T, I> List<T> find(Class<T> documentClass, Function<I, Query> toQuery, I fromItem);

    <T> void update(Class<T> documentClass, Map<String, ?> fieldsToFilter, Map<String, ?> fieldsToUpdate);

    <T> void upsert(Class<T> documentClass, Map<String, ?> fieldsToFilter, Map<String, ?> fieldsToUpdate);

    <T> void delete(Class<T> documentClass, Map<String, ?> fieldsToFilter);
}
