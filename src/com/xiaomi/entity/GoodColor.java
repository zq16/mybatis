package com.xiaomi.entity;

public class GoodColor {

	private String goodColor;

	public GoodColor() {
	}

	public GoodColor(String goodColor) {
		this.goodColor = goodColor;
	}

	public String getGoodColor() {
		return goodColor;
	}

	public void setGoodColor(String goodColor) {
		this.goodColor = goodColor;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((goodColor == null) ? 0 : goodColor.hashCode());
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
		GoodColor other = (GoodColor) obj;
		if (goodColor == null) {
			if (other.goodColor != null)
				return false;
		} else if (!goodColor.equals(other.goodColor))
			return false;
		return true;
	}

}
