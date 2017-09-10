package com.mns.ssi.tech.core.dao.message;
/**
 * 
 * @author desha
 *
 */
public interface SSIDaoMessage {

	public final static String CAN_NOT_PERSIST_NULL_OBJECT = "CAN_NOT_PERSIST_NULL_OBJECT";
	public final static String CAN_NOT_FIND_BLANK_RECORD = "CAN_NOT_FIND_BLANK_RECORD";
	public final static String INVALID_CLASS_NAME = "INVALID_CLASS_NAME";
	public final static String INVALID_KEY = "INVALID_KEY";
	public final static String DUPLICATE_RECORD = "DUPLICATE_RECORD";
	public final static String BLANK_QUERY = "BLANK_QUERY";
	public final static String BLANK_PARAMETR = "BLANK_PARAMETR";
	public final static String BLANK_ENTITY = "BLANK_ENTITY";
	public static final String NOT_SUPPORTED_DATATYPE = " not Supported as file DataType";
	public static final String QUERY_EXECUTION_FAILED = "Native Query Execution Failed";
	public static final String ENTITY_CREATION_FAILED = "Entity Creation Failed";
	public static final String ENTITY_UPDATE_FAILED = "Entity Update Failed";
	public static final String ENTITY_DELETE_FAILED = "Entity Delete Failed";
	public static final String ENTITY_FIND_FAILED = "Entity Find Failed";
	public static final String ENTITY_COUNT_FAILED = "Entity Count Failed";
	public static final String PRE_SELECT_COUNT = " SELECT COUNT(1) ";
	public static final String FROM = " FROM ";
	public static final String BRACKET_LEFT = " ( ";
	public static final String BRACKET_RIGHT = " ) ";
	public static final String ORDER_BY = "ORDER BY";
	public static final String MAX_REGISTROS = "300";
	
}
