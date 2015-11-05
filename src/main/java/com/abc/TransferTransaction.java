package com.abc;

public class TransferTransaction  {

	public IAccount execute(final double amount, IAccount fromAccount, IAccount toAccount) {
		if (amount <= 0) {
			throw new IllegalArgumentException(
					"transfer amount must be greater than zero");
		} else {
			fromAccount.getTransactions().add(new Transaction(-amount));
			toAccount.getTransactions().add(new Transaction(amount));
		}
		
		return fromAccount;
	}

}
