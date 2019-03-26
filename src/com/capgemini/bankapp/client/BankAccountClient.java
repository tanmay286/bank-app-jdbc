package com.capgemini.bankapp.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.capgemini.bankapp.exception.LowBalanceException;
import com.capgemini.bankapp.model.BankAccount;
import com.capgemini.bankapp.service.BankAccountService;
import com.capgemini.bankapp.service.impl.BankAccountServiceImpl;

public class BankAccountClient {

	public static void main(String arg[]) {
		int choice;
		String accountHolderName;
		String accountType;
		double accountBalance;
		long accountId;
		double amount;
		double balance;
		BankAccountService bankService = new BankAccountServiceImpl();

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {

			while (true) {
				System.out.println("1. Add New BankAccount\n2. Withdraw\n3. Deposit\n4. Fund Transfer");
				System.out.println("5. Delete BankAccount\n6. Display All BankAccount Details");
				System.out.println("7. Search BankAccount\n8. Check Balance\n9. Exit\n");

				System.out.println("Enter your Choice : ");
				choice = Integer.parseInt(reader.readLine());

				switch (choice) {
				case 1:
					System.out.println("Enter the account holder name");
					accountHolderName = reader.readLine();
					System.out.println("Enter the account Type ");
					accountType = reader.readLine();
					System.out.println("Enter the balance");
					accountBalance = Double.parseDouble(reader.readLine());

					BankAccount account = new BankAccount(accountHolderName, accountType, accountBalance);
					if (bankService.addNewAccount(account))
						System.out.println("Account Succesfully Created");
					else
						System.out.println("Failed to create account");
					break;

				case 2:
					System.out.println("Enter the account number : ");
					accountId = Long.parseLong(reader.readLine());
					System.out.println("Enter the withdraw amount :");
					amount = Double.parseDouble(reader.readLine());
					balance = bankService.withdraw(accountId, amount);
					System.out.println("Balance After Withdraw is = " + balance);
					break;
					
				case 3:
					System.out.println("Enter the account number : ");
					accountId= Long.parseLong(reader.readLine());
					System.out.println("Enter the amount to deposit : ");
					amount = Double.parseDouble(reader.readLine());
					balance = bankService.deposit(accountId, amount);
					System.out.println("Balance after depositing amount is = " + balance);
					break;
				
				case 4:
					System.out.println("Enter the account number : ");
					long fromAccount=Long.parseLong(reader.readLine());
					System.out.println("Enter the account number of recieve : ");
					long toAccount=Long.parseLong(reader.readLine());
					System.out.println("Enter the amount to transfer : ");
					amount = Double.parseDouble(reader.readLine());
					balance= bankService.fundTranfer(fromAccount, toAccount, amount);
					System.out.println("Sender Balance is = " + bankService.checkBalance(fromAccount));
					System.out.println("Reciever balance is = " + bankService.checkBalance(toAccount));
					break;
				
				case 5:
					System.out.println("Enter the account you want to remove ");
					accountId=Long.parseLong(reader.readLine());
					
					if(bankService.deleteBankAccount(accountId))
						System.out.println("Account is deleted successfully ");
					else
						System.out.println("Sorry account is not deleted ");
					break;
				case 6:
					List<BankAccount> accounts=new ArrayList<>();
					accounts = bankService.findAllBankAccounts();
					for(BankAccount bankAccount : accounts) {
						System.out.println(bankAccount);
					}
					break;
				case 7:
					System.out.println("Enter the account you want to search ");
					accountId=Long.parseLong(reader.readLine());
					BankAccount bankAccounts=bankService.searchAccount(accountId);
					System.out.println("Detils is :" + bankAccounts );
					break;
				case 8:
					System.out.println("Enter the account number u want to check balance :");
					accountId=Long.parseLong(reader.readLine());
					System.out.println("Balance = " + bankService.checkBalance(accountId));
					break;
				case 9:
					System.out.println("Thank you using Bank ..");
					System.exit(0);

				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (LowBalanceException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
	}

}
