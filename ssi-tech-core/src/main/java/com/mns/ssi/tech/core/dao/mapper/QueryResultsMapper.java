package com.mns.ssi.tech.core.dao.mapper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.mns.ssi.tech.core.exception.SSICoreException;
import org.apache.commons.beanutils.BeanUtils;

/**
 * 
 * @author desha
 *
 */
public class QueryResultsMapper {

	public static <T> List<T> mapDTO(List<Object[]> objectArrayList, Class<T> genericType) {
		List<T> ret = new ArrayList<T>();
		List<Field> mappingFields = getQueryResultColumnAnnotatedFields(genericType);
		try {
			for (Object[] objectArr : objectArrayList) {
				T t = genericType.newInstance();
				for (int i = 0; i < objectArr.length; i++) {
					BeanUtils.setProperty(t, mappingFields.get(i).getName(), objectArr[i]);
				}
				ret.add(t);
			}
		} catch (Exception ex) {
			throw new SSICoreException(ex);
		}
		return ret;
	}

	// Get ordered list of fields
	private static <T> List<Field> getQueryResultColumnAnnotatedFields(Class<T> genericType) {
		Field[] fields = genericType.getDeclaredFields();
		List<Field> orderedFields = Arrays.asList(new Field[fields.length]);
		for (int i = 0; i < fields.length; i++) {
			if (fields[i].isAnnotationPresent(QueryResultColumn.class)) {
				QueryResultColumn nqrc = fields[i].getAnnotation(QueryResultColumn.class);
				orderedFields.set(nqrc.index(), fields[i]);
			}
		}
		return orderedFields;
	}

}
