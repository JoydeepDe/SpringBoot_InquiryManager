/**
 * 
 */
package com.hexa.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author INDRADEO CHAURASIA
 *
 */
@Entity
@Table(name = "Account")
public class Account {

	@Id
	@GeneratedValue
	@Column(name = "AccountId")
	private Integer accountID;
	
	@Column(name = "AccountType")
	private String accountType;
	
	@Column(name = "AccountHolderName")
	private String accountHolderName;
	
	@Column(name = "SSN")
	private Integer ssn;
	
	@Column(name = "Balance")
	private Integer balance;

	public Integer getAccountID() {
		return accountID;
	}

	public void setAccountID(Integer accountID) {
		this.accountID = accountID;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getAccountHolderName() {
		return accountHolderName;
	}

	public void setAccountHolderName(String accountHolderName) {
		this.accountHolderName = accountHolderName;
	}

	public Integer getSsn() {
		return ssn;
	}

	public void setSsn(Integer ssn) {
		this.ssn = ssn;
	}

	public Integer getBalance() {
		return balance;
	}

	public void setBalance(Integer balance) {
		this.balance = balance;
	}

}
