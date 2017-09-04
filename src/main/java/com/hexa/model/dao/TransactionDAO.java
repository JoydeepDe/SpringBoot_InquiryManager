package com.hexa.model.dao;

import java.util.List;

import com.hexa.model.Transaction;

public interface TransactionDAO {

	public List<Transaction> list();

	public void save(Transaction data);
	
}
