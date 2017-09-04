package com.hexa.model.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hexa.model.Account;
import com.hexa.model.dao.AccountDAO;

@Service
@Component
@Repository
public class AccountDAOImpl implements AccountDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public AccountDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	@Transactional
	public List<Account> list() {
		@SuppressWarnings("unchecked")
		List<Account> accountList = sessionFactory.getCurrentSession().createQuery("from Account").list();
		return accountList;
	}

	@Override
	@Transactional
	public boolean verifyAccount(Integer accountId) {
		Account account = (Account) sessionFactory.getCurrentSession().get(Account.class, accountId);
		if (account != null) {
			return true;
		}
		return false;
	}

	@Override
	@Transactional
	public boolean verifySSN(Integer accountId, Integer ssn) {
		Query query = sessionFactory.getCurrentSession()
				.createQuery("SELECT 1 FROM com.hexa.model.Account a WHERE a.accountID = :accountID and a.ssn = :ssn");
		query.setInteger("accountID", accountId);
		query.setInteger("ssn", ssn);
		if (query.uniqueResult() != null) {
			return true;
		} else {
			return false;
		}

	}

	@Override
	@Transactional
	public Integer checkAccountBalance(Integer accountId) {
		// TODO Auto-generated method stub
		Account account = (Account) sessionFactory.getCurrentSession().get(Account.class, accountId);
		return account.getBalance();
	}

	@Override
	@Transactional
	public void addBalance(Integer accountId, Integer amount) {
		// TODO Auto-generated method stub
		Account account = (Account) sessionFactory.getCurrentSession().get(Account.class, accountId);
		Integer balance = account.getBalance();
		account.setBalance(balance + amount);
		sessionFactory.getCurrentSession().save(account);
	}

	@Override
	@Transactional
	public void removeBalance(Integer accountId, Integer amount) {
		// TODO Auto-generated method stub
		Account account = (Account) sessionFactory.getCurrentSession().get(Account.class, accountId);
		Integer balance = account.getBalance();
		account.setBalance(balance - amount);
		sessionFactory.getCurrentSession().save(account);
	}

	@Override
	@Transactional
	public Account getAccount(Integer accountId) {
		Account account = (Account) sessionFactory.getCurrentSession().get(Account.class, accountId);
		return account;
	}

}
