package com.xiaomi.entity;

public class GoodType {
	private String goodType;
	private Float goodPrice;

	public GoodType() {
	}

	public GoodType(String goodType, Float goodPrice) {
		this.goodType = goodType;
		this.goodPrice = goodPrice;
	}

	@Override
	public String toString() {
		return "GoodType [goodType=" + goodType + ", goodPrice=" + goodPrice + "]";
	}

	public String getGoodType() {
		return goodType;
	}

	public void setGoodType(String goodType) {
		this.goodType = goodType;
	}

	public Float getgoodPrice() {
		return goodPrice;
	}

	public void setgoodPrice(Float goodPrice) {
		this.goodPrice = goodPrice;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((goodType == null) ? 0 : goodType.hashCode());
		result = prime * result + ((goodPrice == null) ? 0 : goodPrice.hashCode());
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
		GoodType other = (GoodType) obj;
		if (goodType == null) {
			if (other.goodType != null)
				return false;
		} else if (!goodType.equals(other.goodType))
			return false;
		if (goodPrice == null) {
			if (other.goodPrice != null)
				return false;
		} else if (!goodPrice.equals(other.goodPrice))
			return false;
		return true;
	}

	

}
