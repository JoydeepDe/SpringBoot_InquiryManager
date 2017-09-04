/**
 * 
 */
package com.hexa.model.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.hexa.model.Transaction;
import com.hexa.model.dao.TransactionDAO;

/**
 * @author INDRADEO CHAURASIA
 *
 */
public class TransactionDAOImpl implements TransactionDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public TransactionDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hexa.model.dao.TransactionDAO#list()
	 */
	@Override
	@Transactional
	public List<Transaction> list() {
		@SuppressWarnings("unchecked")
		List<Transaction> transactionList = sessionFactory.getCurrentSession().createQuery("from Transaction").list();
		return transactionList;
	}

	@Override
	@Transactional
	public void save(Transaction data) {
		sessionFactory.getCurrentSession().save(data);
		
	}

}
