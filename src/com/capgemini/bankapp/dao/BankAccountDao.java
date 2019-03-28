package com.capgemini.bankapp.dao;

import java.util.List;

import com.capgemini.bankapp.exception.BankAccountNotFoundException;
import com.capgemini.bankapp.model.BankAccount;

public interface BankAccountDao {
	
	public double getBalance(long accountId) throws BankAccountNotFoundException;
	public void updateBalance(long accountId, double newBalance) throws BankAccountNotFoundException;
	public boolean deleteBankAccount(long accountId) ;
	public boolean addNewBankAccount(BankAccount account) ;
	public List<BankAccount> findAllBankAccounts() ;
	public BankAccount searchAccount(long accountId) throws BankAccountNotFoundException;
	public boolean updateAccount(long accountId, String updateName,String updateType) ;
//	public long findAccount(long accountId);
}
