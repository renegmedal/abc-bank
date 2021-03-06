package com.abc;

public class DepositTransaction implements IBankTransaction {

	public IAccount execute(final double amount, final IAccount account) {
		if (amount <= 0) {
			throw new IllegalArgumentException(
					"deposit amount must be greater than zero");
		} else {
			account.getTransactions().add(new Transaction(amount));
		}
		return account;
	}
}
