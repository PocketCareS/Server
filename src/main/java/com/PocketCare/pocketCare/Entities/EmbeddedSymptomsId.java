package com.PocketCare.pocketCare.Entities;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
public class EmbeddedSymptomsId implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2459500852308999599L;
	@NotNull
	private String deviceId;
	@NotNull
	private long date;
	
	
	public EmbeddedSymptomsId(@NotNull String deviceId, @NotNull long date) {
		super();
		this.deviceId = deviceId;
		this.date = date;
	}
	
	public String getDeviceId() {
		return deviceId;
	}


	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}


	public long getDate() {
		return date;
	}


	public void setDate(long date) {
		this.date = date;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((deviceId == null) ? 0 : deviceId.hashCode());
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
		EmbeddedSymptomsId other = (EmbeddedSymptomsId) obj;
		if (date != other.date)
			return false;
		if (deviceId == null) {
			if (other.deviceId != null)
				return false;
		} else if (!deviceId.equals(other.deviceId))
			return false;
		return true;
	}
	
	

}
