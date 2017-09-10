package com.mns.ssi.tech.core.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.mns.ssi.tech.core.constants.SSICoreConstant;
import com.mns.ssi.tech.core.exception.SSICoreException;

/**
 * 
 * @author desha
 *
 */
public final class JSONUtil {

	private static final String BLANCK_CHARACTER = "";
	private static final String UNICODE_CHARACTER_EXPRESSION = "\\r|\\t|\\n";

	private JSONUtil() {
	}

	//Improve Performance only create one instance of ObjectMapper with not fail on unknown properties
	private static ObjectMapper objectMapperFailOnUnknowFalse= new ObjectMapper();
	static{
		objectMapperFailOnUnknowFalse.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}
	
	//Improve Performance only create one instance of ObjectMapper indent
	private static ObjectMapper objectMapperIndentOutput= new ObjectMapper();
	static{
		objectMapperIndentOutput.enable(SerializationFeature.INDENT_OUTPUT);
	}
	
	//Improve Performance only create one instance of ObjectMapper
	private static ObjectMapper objectMapperWithoutConfig= new ObjectMapper();

	/**
	 * 
	 * @param json
	 *            string in JSON format
	 * @return returns a Map
	 */
	public static Map<String, Object> jsonToMap(String json) throws SSICoreException {
		try {
			return jsonToJavaObject(json, Map.class);
		} catch (Exception ex) {
			throw new SSICoreException(ex);
		}
	}

	/**
	 * 
	 * @param json
	 *            string in JSON format
	 * @return returns a List of object of a specified class
	 */
	public static List<Object> jsonToList(String json) throws SSICoreException {
		try {
			return jsonToJavaObject(json, List.class);
		} catch (Exception ex) {
			throw new SSICoreException(ex);
		}
	}

	/**
	 * 
	 * @param json
	 *            string in JSON format
	 * @return returns a List
	 */
	public static <T> List<T> jsonToListObject(String json, Class<?> clazz) throws SSICoreException {
		List<T> list = new ArrayList<T>();
		try {
			list = objectMapperFailOnUnknowFalse.readValue(json.replaceAll(UNICODE_CHARACTER_EXPRESSION, BLANCK_CHARACTER), objectMapperFailOnUnknowFalse
					.getTypeFactory().constructCollectionType(ArrayList.class, clazz));
		} catch (Exception e) {
			throw new SSICoreException(SSICoreConstant.JSON_TO_LIST_OBJECT_FAILED, e);
		}
		return list;
	}

	/**
	 * @param json
	 *            string in JSON format
	 * @param clazz
	 *            java class
	 * @return return an object of a specified class
	 */
	@SuppressWarnings("unchecked")
	public static <T> T jsonToJavaObject(String json, Class<?> clazz) throws SSICoreException {
		T output = null;
		try {
			output = (T) objectMapperFailOnUnknowFalse.readValue(json.replaceAll(UNICODE_CHARACTER_EXPRESSION, BLANCK_CHARACTER), clazz);
		} catch (Exception e) {
			throw new SSICoreException(SSICoreConstant.JSON_TO_JAVA_OBJECT_FAILED, e);
		}
		return output;
	}

	@SuppressWarnings({ "unchecked" })
	public static <T> T jsonToGenericJavaObject(String json, Class<?> input, Class<?>... generic)
			throws SSICoreException {
		T output = null;
		try {
			output = (T) objectMapperFailOnUnknowFalse.readValue(json.replaceAll(UNICODE_CHARACTER_EXPRESSION, BLANCK_CHARACTER), objectMapperFailOnUnknowFalse
					.getTypeFactory().constructParametricType(input, generic));
		} catch (Exception e) {
			throw new SSICoreException(SSICoreConstant.JSON_TO_GENERIC_JAVA_OBJECT_FAILED, e);
		}
		return output;
	}

	/**
	 * @param json
	 *            string in JSON format
	 * @param key
	 *            java class
	 * @param input
	 *            java class
	 * @param generic
	 *            array java class
	 * @return return an generic object
	 */
	@SuppressWarnings({ "unchecked" })
	public static <T> T jsonToGenericMap(String json, Class<?> key, Class<?> input, Class<?>... generic)
			throws SSICoreException {
		T output = null;
		try {
			JavaType keyType = objectMapperFailOnUnknowFalse.getTypeFactory().constructType(key);
			JavaType valueType = objectMapperFailOnUnknowFalse.getTypeFactory().constructParametricType(input, generic);
			output = (T) objectMapperFailOnUnknowFalse.readValue(json.replaceAll(UNICODE_CHARACTER_EXPRESSION, BLANCK_CHARACTER), objectMapperFailOnUnknowFalse
					.getTypeFactory().constructMapType(Map.class, keyType, valueType));
		} catch (Exception ex) {
			throw new SSICoreException(SSICoreConstant.JSON_TO_GENERIC_JAVA_OBJECT_FAILED, ex);
		}
		return output;
	}

	/**
	 * @param obj
	 *            java object
	 * @return return a string in JSON format
	 */
	public static String javaObjectToJson(Object obj) throws  SSICoreException{
		if (obj == null) {
			return null;
		}
		String output = null;
		try {
			output = objectMapperWithoutConfig.writeValueAsString(obj);
		} catch (Exception ex) {
			throw new SSICoreException(SSICoreConstant.JAVA_TO_JSON_OBJECT_FAILED, ex);
		}
		return output;
	}

	/**
	 * @param obj
	 *            java object
	 * @return return a string in JSON format
	 */
	public static String javaObjectToPrettyJson(Object obj) throws SSICoreException {
		if (obj == null) {
			return null;
		}
		String output = null;
		try {
			output = objectMapperIndentOutput.writeValueAsString(obj);
		} catch (Exception ex) {
			throw new SSICoreException(SSICoreConstant.JAVA_TO_PRETTY_JSON_OBJECT_FAILED, ex);
		}
		return output;
	}
}
