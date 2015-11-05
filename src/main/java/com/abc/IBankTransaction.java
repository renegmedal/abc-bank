package com.abc;

public interface IBankTransaction {

	IAccount execute(double amount, IAccount account);
	
}
