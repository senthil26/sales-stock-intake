package com.mns.ssi.tech.core.threadcontext.impl;

import java.util.Map;

import com.mns.ssi.tech.core.threadcontext.SSICoreThreadLocalContext;

public class SSICoreThreadLocalContextImpl implements SSICoreThreadLocalContext{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8863027585978402051L;


	private String userId;
	
	private String userIp;/** ip of client browser**/

	/**
	 * Variable to store source REST method or navigation page
	 */
	private String source;

	private Map<String, Object> mapVariable;

	static ThreadLocal<SSICoreThreadLocalContext> localContext = new ThreadLocal<SSICoreThreadLocalContext>();
	
	private String uuid;

	public SSICoreThreadLocalContextImpl() {
	}

	public SSICoreThreadLocalContextImpl(String userId) {
		this.userId = userId;
	}

	public static SSICoreThreadLocalContext getInstance() {
		SSICoreThreadLocalContext ctx = localContext.get();
		if (ctx == null) {
			ctx = new SSICoreThreadLocalContextImpl();
			setInstance(ctx);
		}
		return localContext.get();
	}

	public static void setInstance(SSICoreThreadLocalContext ctx) {
		SSICoreThreadLocalContextImpl.localContext.set(ctx);
	}

	@Override
	public String getUserId() {
		return userId;
	}

	public void setMatricula(String matricula) {
		this.userId = matricula;
	}


	@Override
	public Map<String, Object> getMapVariable() {
		return mapVariable;
	}

	@Override
	public void setMapVariable(Map<String, Object> mapVariable) {
		this.mapVariable = mapVariable;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
	@Override
	public String getUserIp() {
		return userIp;
	}

	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

}
