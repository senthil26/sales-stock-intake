package com.mns.ssi.tech.core.util;

/**
 * Example How to use RestClient to call your external Services
 * @author desha
 *
 */
public class ProductRestCleint {

	/*public static <T extends BaseRuleResponseDTO> T resolveRule(String ruleId, BaseRuleRequestDTO ruleRequest,
			Class<T> ruleResponseClass, AppContext appContext, String url, String usr,
			String pwd) {

		T rule = null;
		try {
			List<T> list = resolveRuleToList(ruleId, ruleRequest, ruleResponseClass, appContext, url, usr, pwd);

			if (CoreValidationUtil.isNotEmpty(list)) {
				rule = list.get(0);
			}
		} catch (Exception ex) {
			throw new CommonCoreException(ex);
		}
		return rule;

	}

	@SuppressWarnings("unchecked")
	public static <T extends BaseRuleResponseDTO> List<T> resolveRuleToList(String ruleId,
			BaseRuleRequestDTO ruleRequest, Class<T> ruleResponseClass, AppContext appContext, String url, String usr,
			String pwd) {

		List<T> listRuleResponse = null;

		try {

			String key = ruleId + ruleRequest.getRuleKey();

			if (CoreValidationUtil.isNotEmpty(appContext.getMapRuleResult().get(key))) {
				listRuleResponse = (List<T>) appContext.getMapRuleResult().get(key);
				System.out.println("CACHE.RULE:key: " +  key +" RESPONSE:" + JSONUtil.javaObjectToJson(listRuleResponse));
				return listRuleResponse;
			}

			Map<String, Object> mapRequest = new HashMap<String, Object>();

			RuleRequestDTO<BaseRuleRequestDTO> request = new RuleRequestDTO<BaseRuleRequestDTO>();
			request.setRuleRequest(ruleRequest);
			request.setRuleId(ruleId);

			mapRequest.put("1", request);

			String jsonRequest = JSONUtil.javaObjectToJson(mapRequest);
			
			System.out.println("BRMS.RULE.REQUEST:" + jsonRequest);

			RESTResponse restResponse = RestClientUtil.callServicePost(appContext.getRuleRestURL() + url, jsonRequest,
					usr, pwd);
			
			System.out.println("BRMS.RULE.RESPONSE:" + JSONUtil.javaObjectToJson(restResponse));
			
			if (RESTStatus.STATUS_OK.name().equals(restResponse.getStatus())) {
				Map<String, RuleResponseDTO<T>> brmsRuleResponseDTO = JSONUtil.jsonToGenericMap(
						restResponse.getResult(), String.class, RuleResponseDTO.class, ruleResponseClass);
				listRuleResponse = brmsRuleResponseDTO.get("1").getRuleResponse();

				appContext.getMapRuleResult().put(key, listRuleResponse);

				return listRuleResponse;
			} else {
				BRMSErrorDTO brmsErrorDTO = JSONUtil.jsonToJavaObject(restResponse.getErrorMsg(), BRMSErrorDTO.class);
				ErrorMsgDTO errorMsgDTO = new ErrorMsgDTO();
				errorMsgDTO.setCodigo(CoreErrorCode.ERROR_BRMS0001.getCoreErrorCode());
				errorMsgDTO.setTipoMensaje(ErrorType.ERROR.getValue());
				errorMsgDTO.setTextoMensaje(brmsErrorDTO.getTextoMensaje());
				errorMsgDTO.setErrorInterfaceCode(brmsErrorDTO.getCodigo());
				errorMsgDTO.setInterfaceCode(CoreErrorCode.DROOLS.getCoreErrorCode());
				throw new CommonCoreException(errorMsgDTO);
			}
		} catch (Exception ex) {
			throw new CommonCoreException(ex);
		}

	}

	public static <T extends BaseRuleResponseDTO> Map<String, List<T>> resolveGroupRuleToMapList(String ruleId,
			Map<String, ? extends BaseRuleRequestDTO> mapRuleRequest, Class<T> ruleResponseClass,
			AppContext appContext, String url, String usr, String pwd) {
		try {
			Map<String, Object> mapRequest = new HashMap<String, Object>();
			RuleRequestDTO<BaseRuleRequestDTO> request = null;
			for (Map.Entry<String, ? extends BaseRuleRequestDTO> entry : mapRuleRequest.entrySet()) {
				request = new RuleRequestDTO<BaseRuleRequestDTO>();
				request.setRuleRequest(entry.getValue());
				request.setRuleId(ruleId);
				mapRequest.put(entry.getKey(), request);

			}
			String jsonRequest = JSONUtil.javaObjectToJson(mapRequest);

			RESTResponse restResponse = RestClientUtil.callServicePost(appContext.getRuleRestURL() + url, jsonRequest,
					usr, pwd);

			if (RESTStatus.STATUS_OK.name().equals(restResponse.getStatus())) {
				Map<String, RuleResponseDTO<T>> brmsRuleResponseDTO = JSONUtil.jsonToGenericMap(
						restResponse.getResult(), String.class, RuleResponseDTO.class, ruleResponseClass);

				Map<String, List<T>> mapRuleResponse = new HashMap<String, List<T>>();
				for (Map.Entry<String, RuleResponseDTO<T>> entry : brmsRuleResponseDTO.entrySet()) {
					mapRuleResponse.put(entry.getKey(), entry.getValue().getRuleResponse());
				}
				return mapRuleResponse;
			} else {
				BRMSErrorDTO brmsErrorDTO = JSONUtil.jsonToJavaObject(restResponse.getErrorMsg(), BRMSErrorDTO.class);
				ErrorMsgDTO errorMsgDTO = new ErrorMsgDTO();
				errorMsgDTO.setCodigo(CoreErrorCode.ERROR_BRMS0001.getCoreErrorCode());
				errorMsgDTO.setTipoMensaje(ErrorType.ERROR.getValue());
				errorMsgDTO.setTextoMensaje(brmsErrorDTO.getTextoMensaje());
				errorMsgDTO.setErrorInterfaceCode(brmsErrorDTO.getCodigo());
				errorMsgDTO.setInterfaceCode(CoreErrorCode.DROOLS.getCoreErrorCode());

				throw new CommonCoreException(errorMsgDTO);
			}
		} catch (Exception ex) {
			throw new CommonCoreException(ex);
		}

	}
*/
}
