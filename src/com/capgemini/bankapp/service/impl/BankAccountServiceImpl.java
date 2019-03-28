package com.capgemini.bankapp.service.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.capgemini.bankapp.dao.BankAccountDao;
import com.capgemini.bankapp.dao.impl.BankAccountDaoImpl;
import com.capgemini.bankapp.exception.BankAccountNotFoundException;
import com.capgemini.bankapp.exception.LowBalanceException;
import com.capgemini.bankapp.model.BankAccount;
import com.capgemini.bankapp.service.BankAccountService;
import com.capgemini.bankapp.util.DbUtil;

public class BankAccountServiceImpl implements BankAccountService {
	static final Logger logger = Logger.getLogger(DbUtil.class);
	private BankAccountDao bankAccountDao;

	// To call the methods of BankAccountDao for implementation
	// We will write it in constructor
	public BankAccountServiceImpl() {
		bankAccountDao = new BankAccountDaoImpl();
	}

	// To check the balance
	@Override
	public double checkBalance(long accountId) throws BankAccountNotFoundException {
		double balance = bankAccountDao.getBalance(accountId);
		if (balance >= 0)
			return balance;
		throw new BankAccountNotFoundException("BankAccount Does Not Exist");
	}

	//
	@Override
	public double withdraw(long accountId, double amount) throws LowBalanceException, BankAccountNotFoundException {
		// Get the current balance
		double balance = bankAccountDao.getBalance(accountId);
		if (balance < 0)
			throw new BankAccountNotFoundException("BankAccount Does Not Exist");
		else if (balance - amount > 0) {
			balance = balance - amount;
			// Updating the balance after withdrawing from account
			bankAccountDao.updateBalance(accountId, balance);
			DbUtil.commit();
			return balance;
		} else
			throw new LowBalanceException("Insufficient Balance");
	}
	
	public double withdrawFundTransfer(long accountId, double amount) throws LowBalanceException, BankAccountNotFoundException {
		// Get the current balance
		double balance = bankAccountDao.getBalance(accountId);
		if (balance < 0)
			throw new BankAccountNotFoundException("BankAccount Does Not Exist");
		else if (balance - amount > 0) {
			balance = balance - amount;
			// Updating the balance after withdrawing from account
			bankAccountDao.updateBalance(accountId, balance);
			DbUtil.commit();
			return balance;
		} else
			throw new LowBalanceException("Insufficient Balance");
	}

	@Override
	public double deposit(long accountId, double amount) throws BankAccountNotFoundException {
		// Get the current balance
		double balance = bankAccountDao.getBalance(accountId);
		if (balance < 0)
			throw new BankAccountNotFoundException("BankAccount Does Not Exist");
		balance = balance + amount;
		// Updating the balance after depositing
		bankAccountDao.updateBalance(accountId, balance);
		DbUtil.commit();
		return balance;
	}

	@Override
	public boolean deleteBankAccount(long accountId) throws BankAccountNotFoundException {
		// Directly calling the Delete method of bankAccount of BankAccountDao
		boolean result = bankAccountDao.deleteBankAccount(accountId);
		if(result) {
			DbUtil.commit();
			return result;
		}
		throw new BankAccountNotFoundException("BankAccount does Not Exist ");
	}

	@Override
	public double fundTransfer(long fromAccount, long toAccount, double amount)
			throws LowBalanceException, BankAccountNotFoundException {
		try {
			double newBalance = withdrawFundTransfer(fromAccount, amount);
			deposit(toAccount, amount);
			DbUtil.commit();
			return newBalance;
		} catch (LowBalanceException | BankAccountNotFoundException e) {
			logger.error("Exception ", e);
			DbUtil.rollback();
			throw e;
		}

	}

	@Override
	public boolean addNewAccount(BankAccount account) {
		boolean result = bankAccountDao.addNewBankAccount(account);
		if(result)
			DbUtil.commit();
		return result;
	}

	@Override
	public List<BankAccount> findAllBankAccounts() {
		return bankAccountDao.findAllBankAccounts();
	}

	@Override
	public BankAccount searchAccount(long accountId) throws BankAccountNotFoundException {
		BankAccount account = bankAccountDao.searchAccount(accountId);
		if(account == null)
			throw new BankAccountNotFoundException("BankAccount does not exist");
		return account;

	}

	@Override
	public boolean updateAccount(long accountId, String accountName, String accountType) {

		boolean result = bankAccountDao.updateAccount(accountId, accountName, accountType);
		if(result)
			DbUtil.commit();
		return result;
	}

}
