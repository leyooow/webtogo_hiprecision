package com.ivant.cms.db;

import java.math.BigInteger;

import org.hibernate.SQLQuery;

import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Message;

public class MessageDAO extends AbstractBaseDAO<Message> {
	public MessageDAO() {
		super();
	}

	public BigInteger getMessageCount(Company currentCompany) {
		StringBuilder query = new StringBuilder();
		query.append("SELECT COUNT(*) FROM message WHERE company_id = :currentCompanyID and valid is true");
		SQLQuery q = getSession().createSQLQuery(query.toString());
		q.setLong("currentCompanyID", currentCompany.getId());
		return (BigInteger) q.uniqueResult();
	}
}
