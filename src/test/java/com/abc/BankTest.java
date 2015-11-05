package com.abc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.junit.Test;

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
					"java.lang.IllegalArgumentException: deposit amount must be greater than zero");
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
					"java.lang.IllegalArgumentException: withdrawal amount must be greater than zero");
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
					"java.lang.IllegalArgumentException: deposit amount must be greater than zero");
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
					"java.lang.IllegalArgumentException: withdrawal amount must be greater than zero");
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
		//assertEquals(170.0, bank.totalInterestPaid(), DOUBLE_DELTA);
		//System.out.println("Bank interest paid: " + bank.totalInterestPaid());
		
		assertEquals(150.0, bank.totalInterestPaid(), DOUBLE_DELTA);
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
			assertEquals(e.getMessage(),"java.lang.IllegalArgumentException: deposit amount must be greater than zero");
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
			assertEquals(e.getMessage(),"java.lang.IllegalArgumentException: withdrawal amount must be greater than zero");
		}
	}
	
	
	@Test
	public void TransferBetweenAccounts() {
	
		IAccount savingsAccount = new SavingsAccount();
		IAccount checkingAccount = new CheckingAccount();
		
		Customer customer = new Customer("Bill");
		customer.openAccount(savingsAccount);
		customer.openAccount(checkingAccount);
				 
		assertEquals(2, customer.getNumberOfAccounts(), DOUBLE_DELTA);
	
		try {
			savingsAccount.deposit(1000.0);
			checkingAccount.deposit(2000.0);
			
			assertTrue("Before transfer, the savings account balance should be 1000.0",savingsAccount.sumTransactions() == 1000.0);
			assertTrue("Before transfer, the checking account balance should be 2000.0",checkingAccount.sumTransactions() == 2000.0);
			
			checkingAccount.transfer(500.0, savingsAccount);
			
			assertFalse(savingsAccount.sumTransactions() == 1000.0);
			assertFalse(checkingAccount.sumTransactions() == 2000.0);
			
			assertTrue("After transfer, the new savings account balance should be 1500.0",savingsAccount.sumTransactions() == 1500.0);
			assertTrue("After transfer, the new checking account balance should be 1500.0",checkingAccount.sumTransactions() == 1500.0);			
			
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}		
	}
	
	
	@Test
	public void maxi_savings_account_without_withdrawal_within_10days() {		
		
		Bank bank = new Bank();
		IAccount maxiSavingsAccount = new MaxiSavingsAccount();
		bank.addCustomer(new Customer("Bill").openAccount(maxiSavingsAccount));
		try {
		    Transaction transaction1 = new Transaction(1000, DateProvider.getInstance().pastDays(20));	
		    Transaction transaction2 = new Transaction(2000, DateProvider.getInstance().pastDays(10));		  
		    Transaction transaction3 = new Transaction(-2000, DateProvider.getInstance().pastDays(11)); // withdrawal outside 10 days
		    Transaction transaction4 = new Transaction(2000, DateProvider.getInstance().pastDays(3));
		    
		    maxiSavingsAccount.getTransactions().add(transaction1);
		    maxiSavingsAccount.getTransactions().add(transaction2);
		    maxiSavingsAccount.getTransactions().add(transaction3);
		    maxiSavingsAccount.getTransactions().add(transaction4);		    
		    assertFalse(maxiSavingsAccount.hasWithdrawal(10));
		    assertTrue(maxiSavingsAccount.hasWithdrawal(11));
		    assertTrue(maxiSavingsAccount.hasWithdrawal(30));
		   
		    
		} catch (Exception e) {
			fail();
		}
		// 3000 * 0.05 = 150.0
		assertEquals(150.0, bank.totalInterestPaid(), DOUBLE_DELTA);
	}
	
	
	@Test
	public void maxi_savings_account_withdrawal_within_10days() {		
		
		Bank bank = new Bank();
		IAccount maxiSavingsAccount = new MaxiSavingsAccount();
		bank.addCustomer(new Customer("Bill").openAccount(maxiSavingsAccount));
		try {
			
		    Transaction transaction1 = new Transaction(1000, DateProvider.getInstance().pastDays(20));  
		    Transaction transaction2 = new Transaction(2000, DateProvider.getInstance().pastDays(10));  
		    Transaction transaction3 = new Transaction(2000, DateProvider.getInstance().pastDays(5));
		    Transaction transaction4 = new Transaction(-2000, DateProvider.getInstance().pastDays(10)); // withdrawal within past 10 days
		    
		    maxiSavingsAccount.getTransactions().add(transaction1);
		    maxiSavingsAccount.getTransactions().add(transaction2);
		    maxiSavingsAccount.getTransactions().add(transaction3);
		    maxiSavingsAccount.getTransactions().add(transaction4);
		    
		    assertTrue(maxiSavingsAccount.hasWithdrawal(10));		     		   
		    
		} catch (Exception e) {
			fail();
		}
		// 3000 * 0.01 = 50.0
		assertEquals(30.0, bank.totalInterestPaid(), DOUBLE_DELTA);
	}
}
