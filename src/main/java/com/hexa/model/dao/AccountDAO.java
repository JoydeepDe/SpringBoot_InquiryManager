package com.hexa.model.dao;

import java.util.List;

import com.hexa.model.Account;

public interface AccountDAO {
	public List<Account> list();

	public boolean verifyAccount(Integer accountId);

	public boolean verifySSN(Integer accountId,Integer ssn);
	
	public Integer checkAccountBalance(Integer accountId);
	
	public void addBalance(Integer accountId,Integer amount);
	
	public void removeBalance(Integer accountId,Integer amount);

	public Account getAccount(Integer accountId);
	
}
