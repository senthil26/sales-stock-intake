package com.mns.ssi.tech.core.dao.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * Example to use DAOHelper
 * @author desha
 *
 */
public class BaseDAOHelper {
	static Logger logger = LoggerFactory.getLogger(BaseDAOHelper.class);

	private BaseDAOHelper(){
	}

	public static String getCalenderDetails() {
		StringBuilder sb = new StringBuilder();
		sb.append("select A.id, A.memberName as Season, D.memberName as Week from CalenderData A,");
		sb.append(" CalenderData B, CalenderData C, CalenderData D where A.entityId = B.parentMemberId ");
		sb.append(" and A.calenderId =1 and A.parentMemberId =0 and B.entityId  = C.parentMemberId");
		sb.append(" and C.entityId  = D.parentMemberId");
		sb.append(" Order by D.sortOrder");
		return sb.toString();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BaseDAOHelper []";
	}

	public static String getAuditReport() {
		StringBuilder sb = new StringBuilder();

		sb.append(" SELECT a FROM Audit a WHERE a.groupCode =?1    ");
		sb.append(" AND a.reportCode = ?2 ");
		sb.append(" and a.fechaUltimaModificacion >= ?3     ");
		sb.append(" and a.fechaUltimaModificacion <= ?4 ");
		return sb.toString();
	}

	public static String getLogByApplication() {
		StringBuilder sb = new StringBuilder();

		sb.append(" select a from LogApplication a where a.application = ?1 ");
		sb.append(" and a.fechaCreacion >= ?2 ");
		sb.append(" and a.fechaCreacion <= ?3 ");

		return sb.toString();
	}

	public static String getLog() {
		StringBuilder sb = new StringBuilder();

		sb.append(" select a from DsErrorControl a where a.ejecutionDate >= ?1 ");
		sb.append(" and a.ejecutionDate <= ?2 ");
		sb.append(" and a.descrEjecution <= ?3 ");

		return sb.toString();
	}

	public static String getRuleEnabled() {
		StringBuilder sb = new StringBuilder();

		sb.append(" select a from Rule a where a.indRule = ?1 ");
		sb.append(" Order By a.ruleCode ");

		return sb.toString();
	}

	public static String getRuleColumn() {
		StringBuilder sb = new StringBuilder();

		sb.append(" select a from RuleParameter a where a.ruleId = ?1 ");
		sb.append(" and a.indParameter = ?2 ");
		sb.append(" and a.indRuleParameter = 1 order by a.parameterPosition ");

		return sb.toString();
	}

	public static String getRuleColumnByName() {
		StringBuilder sb = new StringBuilder();

		sb.append(" select a from RuleParameter a where a.headerName = ?2 ");
		sb.append(" and a.indRuleParameter = 1 ");
		sb.append(" and a.ruleId = (select b.entityId from Rule b where b.ruleCode = ?1) ");

		return sb.toString();
	}

}
