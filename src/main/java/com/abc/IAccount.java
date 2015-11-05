package com.abc;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface IAccount {
	
	public static final int CHECKING = 0;
	public static final int SAVINGS = 1;
	public static final int MAXI_SAVINGS = 2;
	public static final String CHECKING_TYPE_NAME = "Checking Account";
	public static final String SAVINGS_TYPE_NAME = "Savings Account";
	public static final String MAXI_SAVINGS_TYPE_NAME = "Maxi Savings Account";

	void deposit(double amount) throws ExecutionException, InterruptedException;

	void withdraw(double amount) throws ExecutionException,
			InterruptedException;

	double interestEarned();

	double sumTransactions();

	int getAccountType();

	String getAccountTypeName();

	List<Transaction> getTransactions();
}
