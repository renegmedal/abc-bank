package com.abc;

public class WithdrawTransaction implements IBankTransaction {

	public IAccount execute(final double amount, final IAccount account) {
		if (amount <= 0) {
			throw new IllegalArgumentException(
					"amount must be greater than zero");
		} else {
			account.getTransactions().add(new Transaction(-amount));
		}
		return account;
	}

}
