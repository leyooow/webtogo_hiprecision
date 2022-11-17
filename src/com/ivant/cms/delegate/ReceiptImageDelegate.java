package com.ivant.cms.delegate;

import java.util.List;

import com.ivant.cms.db.ReceiptImageDAO;
import com.ivant.cms.entity.Company;
import com.ivant.cms.entity.Member;
import com.ivant.cms.entity.Receipts;

public class ReceiptImageDelegate extends AbstractBaseDelegate<Receipts, ReceiptImageDAO>{
	private static ReceiptImageDelegate instance = new ReceiptImageDelegate();
	public static ReceiptImageDelegate getInstance() {
		return ReceiptImageDelegate.instance;
	}
	public ReceiptImageDelegate() {
		super(new ReceiptImageDAO());
	}
	
	
	public List<Receipts> findAll(Company company, Member member) {
		return dao.findAll(company, member).getList();
	}

}
