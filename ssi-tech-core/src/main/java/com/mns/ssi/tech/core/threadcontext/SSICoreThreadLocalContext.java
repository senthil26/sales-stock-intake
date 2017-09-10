package com.mns.ssi.tech.core.threadcontext;

import java.util.Map;

public interface SSICoreThreadLocalContext {
	
	/**
	 * This return the UserID of the user login to the system
	 * 
	 * @return
	 */
	String getUserId();


	/**
	 * 
	 * @return
	 */
	Map<String, Object> getMapVariable();

	/**
	 * 
	 * @param mapVariable
	 */
	void setMapVariable(Map<String, Object> mapVariable);
	
	/**
	 * This return the userIP of the user login to the system
	 * 
	 * @return
	 */
	String getUserIp();
	
	/**
	 * store the Uuid by Execution Context
	 * 
	 * @return
	 */
	String getUuid();

}
