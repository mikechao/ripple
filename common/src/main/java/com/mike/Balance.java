package com.mike;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Balance {

	private Amount balance = new Amount(Double.valueOf(0), "USD");
	
	public Balance() {
		
	}
	
	public void add(Amount add) {
		Double balanceValue = balance.getAmount();
		balanceValue = add.getAmount() + balanceValue;
		balance.setAmount(balanceValue);
	}
	
	public void subtract(Amount sub) {
		Double balanceValue = balance.getAmount();
		balanceValue = balanceValue - sub.getAmount();
		balance.setAmount(balanceValue);
	}
	
	public Amount getBalance() {
		return balance;
	}
	
	@Override
	public String toString() {
	    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
	            .append("balance", balance)
	            .toString();
	}
}
