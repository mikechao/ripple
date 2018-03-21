package com.mike;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BalanceTest {
    
    private final double DELTA = 0.00001;

    @Test
    public void testGetBalance() {
        Balance balance = new Balance();
        Amount amount = balance.getBalance();
        assertEquals("The default amount should be 0.0 but was NOT", 
                Double.valueOf(0.0).doubleValue(), amount.getAmount().doubleValue(), DELTA);
    }
    
    @Test
    public void testAdd() {
        Amount amountToAdd = new Amount(10.0, "USD");
        Balance balance = new Balance();
        balance.add(amountToAdd);
        Amount addedAmounted = balance.getBalance();
        assertEquals("The amount added did NOT match", 
                amountToAdd.getAmount().doubleValue(), addedAmounted.getAmount().doubleValue(), DELTA);
    }
    
    @Test
    public void testSubstract() {
        Balance balance = new Balance();
        Amount amountToSub = new Amount(5.0, "USD");
        balance.subtract(amountToSub);
        Amount subtractedAmount = balance.getBalance();
        assertEquals("The amount subtracted did NOT match",
                -1 * amountToSub.getAmount().doubleValue(), subtractedAmount.getAmount().doubleValue(), DELTA);
    }
    
    @Test
    public void testToString() {
        Balance balance1 = new Balance();
        Balance balance2 = new Balance();
        assertEquals("Two newly create Balance objects should have the same toString", balance1.toString(), balance2.toString());
    }
}
