package com.mike;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Transaction implements Serializable {
	private static final long serialVersionUID = 4912292366685727200L;
	
	private User fromUser;
	private User toUser;
	private Amount amount;
	
	public Transaction() {
		
	}

	public Transaction(User fromUser, User toUser, Amount amount) {
		this.fromUser = fromUser;
		this.toUser = toUser;
		this.amount = amount;
	}
	
	public User getFromUser() {
		return fromUser;
	}
	
	public void setFromUser(User fromUser) {
		this.fromUser = fromUser;
	}
	
	public User getToUser() {
		return toUser;
	}
	
	public void setToUser(User toUser) {
		this.toUser = toUser;
	}
	
	public Amount getAmount() {
		return amount;
	}
	
	public void setAmount(Amount amount) {
		this.amount = amount;
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
				.append("fromUser", fromUser)
				.append("toUser", toUser)
				.append("amount", amount)
				.toString();
	}
}
