package com.capgemini.bankapp.service.impl;

import java.util.List;

import com.capgemini.bankapp.dao.BankAccountDao;
import com.capgemini.bankapp.dao.impl.BankAccountDaoImpl;
import com.capgemini.bankapp.exception.LowBalanceException;
import com.capgemini.bankapp.model.BankAccount;
import com.capgemini.bankapp.service.BankAccountService;

public class BankAccountServiceImpl implements BankAccountService {

	private BankAccountDao bankAccountDao;

	// To call the methods of BankAccountDao for implementation
	// We will write it in constructor
	public BankAccountServiceImpl() {
		bankAccountDao = new BankAccountDaoImpl();
	}

	// To check the balance
	@Override
	public double checkBalance(long accountId) {
		return bankAccountDao.getBalance(accountId);
	}

	//
	@Override
	public double withdraw(long accountId, double amount) throws LowBalanceException {
		// Get the current balance
		double balance = bankAccountDao.getBalance(accountId);
		if (balance - amount > 0) {
			balance = balance - amount;
			// Updating the balance after withdrawing from account
			bankAccountDao.updateBalance(accountId, balance);
			return balance;
		} else
			throw new LowBalanceException("Insufficient Balance");
	}

	@Override
	public double deposit(long accountId, double amount) {
		// Get the current balance
		double balance = bankAccountDao.getBalance(accountId);
		balance = balance + amount;
		// Updating the balance after depositing
		bankAccountDao.updateBalance(accountId, balance);
		return balance;
	}

	@Override
	public boolean deleteBankAccount(long accountId) {
		// Directly calling the Delete method of bankAccount of BankAccountDao
		return bankAccountDao.deleteBankAccount(accountId);
	}

	@Override
	public double fundTranfer(long fromAccount, long toAccount, double amount) throws LowBalanceException {
		// Calling the withdraw method of the same class to withdraw the amount
		double newBalance = withdraw(fromAccount, amount);
		// Same class Deposit method
		deposit(toAccount, amount);
		return newBalance;
	}

	@Override
	public boolean addNewAccount(BankAccount account) {
		return bankAccountDao.addNewBankAccount(account);
	}

	@Override
	public List<BankAccount> findAllBankAccounts() {
		return bankAccountDao.findAllBankAccounts();
	}

	@Override
	public BankAccount searchAccount(long accountId) {
	return bankAccountDao.searchAccount(accountId);
		
	}

}
