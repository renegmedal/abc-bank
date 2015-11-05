package com.abc;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public abstract class AbstractAccount implements IAccount {

	ExecutorService executor = Executors.newFixedThreadPool(3);

	final int accountType;
	final List<Transaction> transactions;

	public AbstractAccount(int accountType) {
		this.accountType = accountType;
		this.transactions = new ArrayList<Transaction>();
	}

	public int getAccountType() {
		return accountType;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void deposit(final double amount) throws ExecutionException,
			InterruptedException {

		Future<IAccount> future = executor.submit(new Callable<IAccount>() {

			public IAccount call() throws Exception {
				IBankTransaction dt = BankTransactionFactory
						.create(TransactionType.DEPOSIT);
				return dt.execute(amount, AbstractAccount.this);
			}
		});

		future.get();

	}

	public void withdraw(final double amount) throws ExecutionException,
			InterruptedException {

		Future<IAccount> future = executor.submit(new Callable<IAccount>() {

			public IAccount call() throws Exception {
				IBankTransaction wt = BankTransactionFactory
						.create(TransactionType.WITHDRAW);
				return wt.execute(amount, AbstractAccount.this);
			}
		});

		future.get();

	}

	public void transfer(final double amount, final IAccount toAccount) throws ExecutionException,
			InterruptedException {

		Future<IAccount> future = executor.submit(new Callable<IAccount>() {

			public IAccount call() throws Exception {
				TransferTransaction transfer = new TransferTransaction(); 
				return transfer.execute(amount, AbstractAccount.this, toAccount);				 
			}
		});

		future.get();

	}

	public double sumTransactions() {
		return checkIfTransactionsExist(true);
	}

	private double checkIfTransactionsExist(boolean checkAll) {
		double amount = 0.0;
		for (Transaction t : transactions)
			amount += t.getAmount();
		return amount;
	}

	/**
	 * Determine if there is a withdrawal transaction for a given past to this account
	 * TODO: This is not an efficient way. Need to be changed
	 * 
	 * @param days Number of days passed since the current day
	 * @return true True if withdrawals were made for a given past days 
	 */
	public boolean hasWithdrawal(int numOfDays) {		
		Calendar pastDate = DateProvider.getInstance().pastCalendarDays(numOfDays+1);		
		Calendar tDate = null;
		for (Transaction t : transactions) {			 
			tDate = Calendar.getInstance();
			tDate.setTime(t.getTransactionDate());
			if (tDate.after(pastDate) && t.getAmount() < 0) return true;
		}		
		return false;
		 
	}
}
