package com.PocketCare.pocketCare.Entities;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
public class EmbeddedContactsId implements Serializable {

	private static final long serialVersionUID = -581739425700128261L;
	@NotNull
	private String zipCode;
	@NotNull
	private long date;
	@NotNull
	private long hour;
	
	public EmbeddedContactsId(@NotNull String zipCode, @NotNull long date, @NotNull long hour) {
		super();
		this.zipCode = zipCode;
		this.date = date;
		this.hour = hour;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}

	public long getHour() {
		return hour;
	}

	public void setHour(long hour) {
		this.hour = hour;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((zipCode == null) ? 0 : zipCode.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EmbeddedContactsId other = (EmbeddedContactsId) obj;
		if (date != other.date)
			return false;
		if (hour != other.hour)
			return false;
		if (zipCode == null) {
			if (other.zipCode != null)
				return false;
		} else if (!zipCode.equals(other.zipCode))
			return false;
		return true;
	}
	
	
	
	

}
