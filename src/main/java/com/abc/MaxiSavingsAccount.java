package com.abc;

public class MaxiSavingsAccount extends AbstractAccount {

	public MaxiSavingsAccount() {
		super(IAccount.MAXI_SAVINGS);		
	}
		
	public String getAccountTypeName() {
		return IAccount.MAXI_SAVINGS_TYPE_NAME;
	}
	public double interestEarned() {		
		final double amount = sumTransactions();
		if (amount <= 1000)
			return amount * 0.02;
		if (amount <= 2000)
			return 20 + (amount - 1000) * 0.05;
		return 70 + (amount - 2000) * 0.1;
	}
}
