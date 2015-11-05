package com.abc;

import java.util.Calendar;
import java.util.Date;

public class DateProvider {
	private static DateProvider instance = null;

	public static DateProvider getInstance() {
		synchronized (DateProvider.class) {
			if (instance == null)
				instance = new DateProvider();
		}
		return instance;
	}

	public Date now() {
		return Calendar.getInstance().getTime();
	}
	
	public Calendar nowCalendar() {
		return Calendar.getInstance();
	}
	public Calendar pastCalendarDays(int numOfDays) {
		Calendar pastDay = Calendar.getInstance();
		pastDay.add(Calendar.DATE, -numOfDays);
		return pastDay;		 
	}
	
	public Date pastDays(int numOfDays) {		 
		return pastCalendarDays(numOfDays).getTime();		 
	}
}
