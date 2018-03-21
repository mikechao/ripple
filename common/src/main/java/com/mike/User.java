package com.mike;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class User implements Serializable {
	private static final long serialVersionUID = 6626667340172472467L;
	
	private String name;
	
	public User() {
		
	}
	
	public User(String name) {
		this.name = name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
				.append("name", name)
				.toString();
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(name)
				.toHashCode();
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof User)) {
			return false;
		}
		User other = (User)o;
		return new EqualsBuilder()
				.append(this.getName(), other.getName())
				.isEquals();
	}
}
