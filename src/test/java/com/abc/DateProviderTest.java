package com.abc;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

public class DateProviderTest {
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
	@Test
    public void pastDaysTest() {
    	 
		Calendar past10Day = Calendar.getInstance();
		past10Day.add(Calendar.DATE, -10);
		
		
        Date computedDate = DateProvider.getInstance().pastDays(10);                  
        
        String sPast10Days = sdf.format(past10Day.getTime());
        String sComputedDate = sdf.format(computedDate);
        
        Assert.assertTrue(sComputedDate.equals(sPast10Days));
         
    }
	
	@Test
    public void pastDays_not_equal_over() {
    	 
		Calendar past10Day = Calendar.getInstance();
		past10Day.add(Calendar.DATE, -10);
		
		
        Date computedDate = DateProvider.getInstance().pastDays(11);                  
        
        String sPast10Days = sdf.format(past10Day.getTime());
        String sComputedDate = sdf.format(computedDate);
        
        Assert.assertFalse(sComputedDate.equals(sPast10Days));
         
    }
	@Test
    public void pastDays_not_equal_under() {
    	 
		Calendar past10Day = Calendar.getInstance();
		past10Day.add(Calendar.DATE, -10);
		
		
        Date computedDate = DateProvider.getInstance().pastDays(9);                  
        
        String sPast10Days = sdf.format(past10Day.getTime());
        String sComputedDate = sdf.format(computedDate);
        
        Assert.assertFalse(sComputedDate.equals(sPast10Days));
         
    }
}
