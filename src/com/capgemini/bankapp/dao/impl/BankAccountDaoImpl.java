package com.capgemini.bankapp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.capgemini.bankapp.dao.BankAccountDao;
import com.capgemini.bankapp.model.BankAccount;
import com.capgemini.bankapp.util.DbUtil;

public class BankAccountDaoImpl implements BankAccountDao {

	@Override
	public double getBalance(long accountId) {
		String query = "select account_balance from bankaccounts where account_id= " + accountId;
		double balance = -1;
		Connection connection = DbUtil.getConnection();
		try (PreparedStatement statement = connection.prepareStatement(query);
				ResultSet result = statement.executeQuery()) {
			if(result.next())
				balance = result.getDouble(1);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return balance;
	}

	@Override
	public void updateBalance(long accountId, double newBalance) {
		String query = "update bankaccounts set account_balance = ? where account_id = ? ";
		Connection connection = DbUtil.getConnection();
		try (PreparedStatement statement = connection.prepareStatement(query)) {

			statement.setDouble(1, newBalance);
			statement.setDouble(2, accountId);

			int result = statement.executeUpdate();
		//	System.out.println("No of rows updated :" + result);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public boolean deleteBankAccount(long accountId) {
		String query = "delete from bankaccounts where account_Id = " + accountId;
		int result;
		Connection connection = DbUtil.getConnection();
		try (PreparedStatement statement = connection.prepareStatement(query)) {

			result = statement.executeUpdate();
			if (result == 1)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean addNewBankAccount(BankAccount account) {
		// Id is AutoIncrement so don't need to declare for inserting
		String query = "insert into bankaccounts (customer_name , account_type , account_balance) value (?,?,?)";
		// Steps of JDBC
		Connection connection = DbUtil.getConnection();
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			// To set values of placeHolder(?,?,?)
			statement.setString(1, account.getAccountHolderName());
			statement.setString(2, account.getAccountType());
			statement.setDouble(3, account.getAccountBalance());
			// DML so we will use executeUpdate method
			int result = statement.executeUpdate();
			if (result == 1)
				return true;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<BankAccount> findAllBankAccounts() {
		String query = "select * from bankaccounts";
		// Output is in table format so we will use list to show the table
		List<BankAccount> accounts = new ArrayList<>();
		// Steps of JDBC
		Connection connection = DbUtil.getConnection();
		try (PreparedStatement statement = connection.prepareStatement(query);
				ResultSet result = statement.executeQuery()) {
			// .next() is in ResultSet Interface
			// while loop will add the account in BankAccount
			while (result.next()) {
				long accountId = result.getLong(1);
				String accountHolderName = result.getString(2);
				String accountType = result.getString(3);
				double accountBalance = result.getDouble(4);
				BankAccount account = new BankAccount(accountId, accountHolderName, accountType, accountBalance);
				accounts.add(account);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return accounts;
	}

	@Override
	public BankAccount searchAccount(long accountId) {
		String query = "select * from bankaccounts where account_Id =  " + accountId;
		BankAccount bankAccountDetails = null;
		Connection connection = DbUtil.getConnection();
		try (PreparedStatement statement = connection.prepareStatement(query);
				ResultSet result = statement.executeQuery()) {
			if (result.next()) {
				bankAccountDetails = new BankAccount(result.getLong(1), result.getString(2), result.getString(3),
						result.getDouble(4));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return bankAccountDetails;
	}

	@Override
	public boolean updateAccount(long accountId, String updateName, String updateType) {
		String query = " update bankaccounts set customer_name = ?,account_type = ? where account_id =? ";
		Connection connection = DbUtil.getConnection();
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, updateName);
			statement.setString(2, updateType);
			statement.setLong(3, accountId);
			int result = statement.executeUpdate();
			// System.out.println("No of rows affected : " + result);
			if (result == 1)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

}
