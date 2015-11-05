package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class BankTest {
	private static final double DOUBLE_DELTA = 1e-15;

	@Test
	public void customerSummary() {
		Bank bank = new Bank();
		Customer john = new Customer("John");
		john.openAccount(new CheckingAccount());
		bank.addCustomer(john);

		assertEquals("Customer Summary\n - John (1 account)",
				bank.customerSummary());
	}

	@Test
	public void checkingAccount() {
		Bank bank = new Bank();
		IAccount checkingAccount = new CheckingAccount();
		Customer bill = new Customer("Bill").openAccount(checkingAccount);
		bank.addCustomer(bill);
		try {
			checkingAccount.deposit(100.0);
		} catch (Exception e) {
			fail();
		}

		assertEquals(0.1, bank.totalInterestPaid(), DOUBLE_DELTA);
	}

	@Test
	public void checkingAccountNegativeDeposit() {

		Bank bank = new Bank();
		IAccount checkingAccount = new CheckingAccount();
		Customer bill = new Customer("Bill").openAccount(checkingAccount);
		bank.addCustomer(bill);

		try {
			checkingAccount.deposit(-100.0);
			fail();
		} catch (Exception e) {
			assertEquals(e.getMessage(),
					"java.lang.IllegalArgumentException: amount must be greater than zero");
		}
	}

	@Test
	public void checkingAccountNegativeWithdraw() {

		Bank bank = new Bank();
		IAccount checkingAccount = new CheckingAccount();
		Customer bill = new Customer("Bill").openAccount(checkingAccount);
		bank.addCustomer(bill);

		try {
			checkingAccount.withdraw(-100.0);
			fail();
		} catch (Exception e) {
			assertEquals(e.getMessage(),
					"java.lang.IllegalArgumentException: amount must be greater than zero");
		}
	}

	@Test
	public void savings_account() {
		Bank bank = new Bank();
		IAccount savingsAccount = new SavingsAccount();
		bank.addCustomer(new Customer("Bill").openAccount(savingsAccount));
		try {
			savingsAccount.deposit(1500.0);
		} catch (Exception e) {
			fail();
		}
		assertEquals(2.0, bank.totalInterestPaid(), DOUBLE_DELTA);
	}

	@Test
	public void savingsAccountNegativeDeposit() {
		Bank bank = new Bank();
		IAccount maxiSavingsAccount = new MaxiSavingsAccount();
		bank.addCustomer(new Customer("Bill").openAccount(maxiSavingsAccount));
		try {
			maxiSavingsAccount.deposit(-3000.0);
			fail();
		} catch (Exception e) {
			assertEquals(e.getMessage(),
					"java.lang.IllegalArgumentException: amount must be greater than zero");
		}
	}

	@Test
	public void savingsAccountNegativeWithdraw() {
		Bank bank = new Bank();
		IAccount checkingAccount = new MaxiSavingsAccount();
		bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));
		try {
			checkingAccount.withdraw(-3000.0);
			fail();
		} catch (Exception e) {
			assertEquals(e.getMessage(),
					"java.lang.IllegalArgumentException: amount must be greater than zero");
		}
	}

	@Test
	public void maxi_savings_account() {
		Bank bank = new Bank();
		IAccount maxiSavingsAccount = new MaxiSavingsAccount();
		bank.addCustomer(new Customer("Bill").openAccount(maxiSavingsAccount));
		try {
			maxiSavingsAccount.deposit(3000.0);
		} catch (Exception e) {
			fail();
		}
		assertEquals(170.0, bank.totalInterestPaid(), DOUBLE_DELTA);
	}
	
	@Test
	public void MaxiSavingsAccountNegativeDeposit() {
		Bank bank = new Bank();
		IAccount checkingAccount = new MaxiSavingsAccount();
		bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));
		try {
			checkingAccount.deposit(-3000.0);
			fail();
		} catch (Exception e) {
			assertEquals(e.getMessage(),"java.lang.IllegalArgumentException: amount must be greater than zero");
		}
	}
	
	@Test
	public void MaxiSavingsAccountNegativeWithdraw() {
		Bank bank = new Bank();
		IAccount checkingAccount = new MaxiSavingsAccount();
		bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));
		try {
			checkingAccount.withdraw(-3000.0);
			fail();
		} catch (Exception e) {
			assertEquals(e.getMessage(),"java.lang.IllegalArgumentException: amount must be greater than zero");
		}
	}
}
