package com.capgemini.bankapp.service;

import java.util.List;

import com.capgemini.bankapp.exception.LowBalanceException;
import com.capgemini.bankapp.model.BankAccount;

public interface BankAccountService {
	
	public double checkBalance(long accountId);
	public double withdraw(long accountId,double amount) throws LowBalanceException;
	public double deposit(long accountId,double amount);
	public boolean deleteBankAccount(long accountId);
	public double fundTranfer(long fromAccount , long toAccount , double amount) throws LowBalanceException ;
	public boolean addNewAccount(BankAccount account);
	public List<BankAccount> findAllBankAccounts();
	public BankAccount searchAccount(long accountId);
}
