package com.PocketCare.pocketCare.model;

import java.util.List;

public class ContactVbtInfo {
	
	private String vbtName;
	
	private int countTwo;
	
	private int countTen;
	
	private double avgDist;
	
	private String zone;
	
	private int offCampus;
	
	private List<OnCampus> onCampus;
	
	
	public ContactVbtInfo(String vbtName, int countTwo, int countTen, double avgDist, String zone, List<OnCampus> onCampus, int offCampus) {
		super();
		this.vbtName = vbtName;
		this.countTwo = countTwo;
		this.countTen = countTen;
		this.avgDist = avgDist;
		this.zone = zone;
		this.onCampus = onCampus;
		this.offCampus = offCampus;
	}
	

	public ContactVbtInfo() {
		super();
	}

	public int getOffCampus() {
		return offCampus;
	}


	public void setOffCampus(int offCampus) {
		this.offCampus = offCampus;
	}


	public List<OnCampus> getOnCampus() {
		return onCampus;
	}


	public void setOnCampus(List<OnCampus> onCampus) {
		this.onCampus = onCampus;
	}


	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public String getVbtName() {
		return vbtName;
	}

	public void setVbtName(String vbtName) {
		this.vbtName = vbtName;
	}

	public int getCountTwo() {
		return countTwo;
	}

	public void setCountTwo(int countTwo) {
		this.countTwo = countTwo;
	}

	public int getCountTen() {
		return countTen;
	}

	public void setCountTen(int countTen) {
		this.countTen = countTen;
	}

	public double getAvgDist() {
		return avgDist;
	}

	public void setAvgDist(double avgDist) {
		this.avgDist = avgDist;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((vbtName == null) ? 0 : vbtName.hashCode());
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
		ContactVbtInfo other = (ContactVbtInfo) obj;
		if (vbtName == null) {
			if (other.vbtName != null)
				return false;
		} else if (!vbtName.equals(other.vbtName))
			return false;
		return true;
	}

}
