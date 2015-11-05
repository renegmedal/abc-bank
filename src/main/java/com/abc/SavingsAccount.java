package com.abc;

public class SavingsAccount extends AbstractAccount {

	public SavingsAccount() {
		super(IAccount.SAVINGS);			
	}
	
	public String getAccountTypeName() {
		return IAccount.SAVINGS_TYPE_NAME;
	}

	public double interestEarned() {
		final double amount = sumTransactions();
		if (amount <= 1000)
			return amount * 0.001;
		else
			return 1 + (amount - 1000) * 0.002;
	 
	}	
}
