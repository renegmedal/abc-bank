package com.abc;

public class CheckingAccount extends AbstractAccount {

	public CheckingAccount() {
		super(IAccount.CHECKING);		
	}
	
	public String getAccountTypeName() {
		return IAccount.CHECKING_TYPE_NAME;
	}
	
	public double interestEarned() {		
		return sumTransactions() * 0.001;
	}

}
