package com.abc;

import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

public class TransactionTest {

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	@Test
	public void transaction() {
		Transaction t = new Transaction(5);
		assertTrue(t instanceof Transaction);
	}

	@Test
	public void transaction_with_date() {
		try {
			Date transactionDate = sdf.parse("2015-11-01");

			Transaction t = new Transaction(5.0, transactionDate);

			Assert.assertTrue(t.getAmount() == 5.0);

			Date tDate = t.getTransactionDate();

			Assert.assertEquals("2015-11-01", sdf.format(tDate));

		} catch (ParseException e) {
			Assert.fail();
		}
	}

	@Test
	public void transaction_negative_amount() {
		Transaction t = new Transaction(-1000.0);
		Assert.assertTrue(t.getAmount() == -1000.0);

	}
}
