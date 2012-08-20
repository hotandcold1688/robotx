package com.macyou.admin;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.macyou.robot.common.Knowledge;

public class KnowledgeAdminServiceImpl implements KnowledgeAdminService {
	private static final String ROBOT_KNOWLEDGE_NAMESPACE = "ROBOT_KNOWLEDGE";
	SqlMapClientTemplate sqlMapClientTemplate;

	@Override
	public void insertKnowledge(Knowledge k) {
		sqlMapClientTemplate.insert(ROBOT_KNOWLEDGE_NAMESPACE +".INSERT", k);
	}

	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}
}
