package com.mike;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Amount implements Serializable {
	private static final long serialVersionUID = -3512874511586732871L;
	private Double amount;
	private String currency;
	
	public Amount() {
		
	}
	
	public Amount(Double value, String currency) {
		this.amount = value;
		this.currency = currency;
	}
	
	public Double getAmount() {
		return amount;
	}
	
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	public String getCurrency() {
		return currency;
	}
	
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
				.append("amount", amount)
				.append("currency", currency)
				.toString();
				
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(this.amount)
				.append(this.currency)
				.toHashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Amount)) {
			return false;
		}
		Amount other = (Amount)obj;
		return new EqualsBuilder()
				.append(this.getAmount(), other.getAmount())
				.append(this.getCurrency(), other.getCurrency())
				.isEquals();
	}
}
