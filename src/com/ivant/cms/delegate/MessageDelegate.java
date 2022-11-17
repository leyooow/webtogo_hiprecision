package com.ivant.cms.delegate;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.ivant.cms.db.MessageDAO;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Message;

/**
 * 
 * @author Angel
 * 
 *         Manages database transactions with the Message Entity
 */
public class MessageDelegate extends AbstractBaseDelegate<Message, MessageDAO> {

	private static MessageDelegate instance;

	protected MessageDelegate() {
		super(new MessageDAO());
	}

	/**
	 * 
	 * @return MessageDelegate Instance
	 */
	public static MessageDelegate getInstance() {
		if (instance == null) {
			return new MessageDelegate();
		}
		return instance;
	}

	/**
	 * 
	 * @return List of Messages ordered by date, most recent first
	 */
	public List<Message> listAllMessages(Company company, int resultsPerPage, int pageNumber) {
		return dao.findAllByCriterion(pageNumber, resultsPerPage, null, null, null,
				new Order[] { Order.desc("createdOn") },
				Restrictions.eq("company", company),
				Restrictions.eq("isValid", Boolean.TRUE)).getList();
	}

	/**
	 * 
	 * @param messageID
	 * @return valid Message based on given primary key, ID
	 */
	public Message getMessage(Long messageID, Company company) {
		return dao.findAllByCriterion(null, null, null, null,
				Restrictions.eq("isValid", Boolean.TRUE),
				Restrictions.eq("company", company),
				Restrictions.eq("id", messageID)).uniqueResult();
	}

	public BigInteger getMessageCount(Company currentCompany) {
		return dao.getMessageCount(currentCompany);
	}

}
