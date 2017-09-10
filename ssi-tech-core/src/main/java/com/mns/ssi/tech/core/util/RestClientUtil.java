package com.mns.ssi.tech.core.util;

import java.io.UnsupportedEncodingException;

import javax.ws.rs.core.HttpHeaders;

import com.mns.ssi.tech.core.exception.SSICoreException;
import com.mns.ssi.tech.core.rest.dto.RESTResponse;
import com.mns.ssi.tech.core.rest.dto.RESTStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.Base64;

/**
 * 
 * @author desha
 *
 */
public class RestClientUtil {

	private static final String CONTENT_TYPE_NAME = "content-type";
	private static final String CONTENT_TYPE_VALUE = "application/json";
	
	private static final Logger logger = LoggerFactory.getLogger(RestClientUtil.class);
	
	private static final int READ_TIMEOUT=600000;
	private static final int CONNECT_TIMEOUT=10000;

	private RestClientUtil() {
	}

	// The client Jersey should only create one on the application, recommended
	// on Jersey documentation
	// https://jersey.java.net/documentation/1.18/client-api.html#d4e607

	private static Client client = Client.create();
	static {
		client.setReadTimeout(READ_TIMEOUT); //time to processing
		client.setConnectTimeout(CONNECT_TIMEOUT);//
	}

	private static String getAutheticationHeaderValue(String userId,
			String password) {
		String authentication=null;
		try {
			authentication="Basic " + new String(Base64.encode(userId + ":" + password), "ASCII");
		} catch (UnsupportedEncodingException ex) {	
			logger.error(ex.getMessage(), ex);
		}
		return authentication;
	}
	
	public static RESTResponse callServiceGet(String pathRest, String userId, String password) {
		RESTResponse restResponse = new RESTResponse();
		
		try {
			logger.info("RestClientUtil.callServiceGet pathRest:"+pathRest);
			
			WebResource webResource = client.resource(pathRest);
			String authentication = getAutheticationHeaderValue(userId,
					password);
			
			//PERFORMANCE: The filter added to the Jersey Client was removed and send on the webResource method,
			//Remember that the client is a shared resource and should not be modified
			ClientResponse response = webResource.header(CONTENT_TYPE_NAME, CONTENT_TYPE_VALUE).
					header(HttpHeaders.AUTHORIZATION, authentication).get(
					ClientResponse.class);

			if (Status.OK.getStatusCode() == response.getStatus()) {
				restResponse.setStatus(RESTStatus.STATUS_OK.name());
				restResponse.setResult(response.getEntity(String.class));
			} else if (Status.BAD_REQUEST.getStatusCode() == response.getStatus()) {
				restResponse.setStatus(RESTStatus.STATUS_BADREQUEST.name());
				restResponse.setErrorMsg(response.getEntity(String.class));
			} else {
				restResponse.setStatus(RESTStatus.STATUS_UNKNOWN.name());
			}

		} catch (Exception ex) {
			throw new SSICoreException(ex);
		}

		return restResponse;

	}

	public static RESTResponse callServicePost(String pathRest, String jsonRequest, String userId, String password) {
		RESTResponse restResponse = new RESTResponse();
		try {
			String authentication = getAutheticationHeaderValue(userId,
					password);
			logger.info("RestClientUtil.callServicePost:"+pathRest+",jsonRequest:"+jsonRequest);
			
			WebResource webResource = client.resource(pathRest);

			//PERFORMANCE: The filter added to the Jersey Client was removed and send on the webResource method,
			//Remember that the client is a shared resource and should not be modified
			ClientResponse response = webResource.header(CONTENT_TYPE_NAME, CONTENT_TYPE_VALUE).
					header(HttpHeaders.AUTHORIZATION, authentication).post(
					ClientResponse.class, jsonRequest);
			logger.info("Status : " + response.getStatus());
			if (Status.OK.getStatusCode() == response.getStatus()) {
				restResponse.setStatus(RESTStatus.STATUS_OK.name());
				restResponse.setResult(response.getEntity(String.class));
			} else if (Status.BAD_REQUEST.getStatusCode() == response.getStatus()) {
				restResponse.setStatus(RESTStatus.STATUS_BADREQUEST.name());
				restResponse.setErrorMsg(response.getEntity(String.class));
			} else {
				restResponse.setStatus(RESTStatus.STATUS_UNKNOWN.name());
				restResponse.setErrorMsg("{\"error\":\"" +RESTStatus.STATUS_UNKNOWN.name() + "\"}");
			}

		} catch (Exception ex) {
			throw new SSICoreException(ex);
		}
		return restResponse;
	}

	public static <T> T callServicePost(String pathRest, String jsonRequest, Class<T> _class, String userId,
			String password) throws SSICoreException {

		RESTResponse restResponse = RestClientUtil.callServicePost(pathRest, jsonRequest, userId, password);
		if (RESTStatus.STATUS_OK.name().equals(restResponse.getStatus())) {
			return JSONUtil.jsonToJavaObject(restResponse.getResult(), _class);
		} else {
			throw new SSICoreException("Error in Service[URL=" + pathRest + "]: Status=" + restResponse.getStatus()
					+ "| ErrorMessage= " + restResponse.getErrorMsg());
		}
	}

	public static String callSimpleServicePost(String pathRest, String jsonRequest, String userId, String password) {

		RESTResponse restResponse = RestClientUtil.callServicePost(pathRest, jsonRequest, userId, password);
		if (RESTStatus.STATUS_OK.name().equals(restResponse.getStatus())) {
			return restResponse.getResult();
		} else {
			throw new SSICoreException("Error in Service[URL=" + pathRest + "]: Status=" + restResponse.getStatus()
					+ "| ErrorMessage= " + restResponse.getErrorMsg());
		}
	}

	public static RESTResponse callServicePost(String pathRest, String userId, String password) {
		RESTResponse restResponse = new RESTResponse();
		try {
			
			logger.info("RestClientUtil.callServicePost pathRest:"+pathRest);
			
			WebResource webResource = client.resource(pathRest);
			String authentication = getAutheticationHeaderValue(userId,
					password);

			//PERFORMANCE: The filter added to the Jersey Client was removed and send on the webResource method,
			//Remember that the client is a shared resource and should not be modified
			ClientResponse response = webResource.header(CONTENT_TYPE_NAME, CONTENT_TYPE_VALUE).header(HttpHeaders.AUTHORIZATION, authentication).post(
					ClientResponse.class);

			if (Status.OK.getStatusCode() == response.getStatus()) {
				restResponse.setStatus(RESTStatus.STATUS_OK.name());
				restResponse.setResult(response.getEntity(String.class));
			} else if (Status.BAD_REQUEST.getStatusCode() == response.getStatus()) {
				restResponse.setStatus(RESTStatus.STATUS_BADREQUEST.name());
				restResponse.setErrorMsg(response.getEntity(String.class));
			} else {
				restResponse.setStatus(RESTStatus.STATUS_UNKNOWN.name());
			}

		} catch (Exception ex) {
			throw new SSICoreException(ex);
		}
		return restResponse;
	}

}
