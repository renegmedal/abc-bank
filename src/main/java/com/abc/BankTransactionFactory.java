package com.abc;

public class BankTransactionFactory {
	public static IBankTransaction create(TransactionType type) {
		switch (type) {
		case DEPOSIT: {
			return new DepositTransaction();
		}

		case WITHDRAW: {
			return new WithdrawTransaction();
		}

		default: {
			return null;
		}
		}
	}
}
