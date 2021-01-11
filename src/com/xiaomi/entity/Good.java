package com.xiaomi.entity;

public class Good {
    private Integer goodId;

    private String goodName;

    private Float goodPrice;

    private String goodType;

    private String goodColor;

    private Integer goodCount;

    private String goodImg;

    private String goodDesc;
    
    

    public Good() {
    	
	}
    
	public Good(String goodName, String goodType, String goodColor) {
		super();
		this.goodName = goodName;
		this.goodType = goodType;
		this.goodColor = goodColor;
	}

	public Integer getGoodId() {
        return goodId;
    }

    public void setGoodId(Integer goodId) {
        this.goodId = goodId;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName == null ? null : goodName.trim();
    }

    public Float getGoodPrice() {
        return goodPrice;
    }

    public void setGoodPrice(Float goodPrice) {
        this.goodPrice = goodPrice;
    }

    public String getGoodType() {
        return goodType;
    }

    public void setGoodType(String goodType) {
        this.goodType = goodType == null ? null : goodType.trim();
    }

    public String getGoodColor() {
        return goodColor;
    }

    public void setGoodColor(String goodColor) {
        this.goodColor = goodColor == null ? null : goodColor.trim();
    }

    public Integer getGoodCount() {
        return goodCount;
    }

    public void setGoodCount(Integer goodCount) {
        this.goodCount = goodCount;
    }

    public String getGoodImg() {
        return goodImg;
    }

    public void setGoodImg(String goodImg) {
        this.goodImg = goodImg == null ? null : goodImg.trim();
    }

    public String getGoodDesc() {
        return goodDesc;
    }

    public void setGoodDesc(String goodDesc) {
        this.goodDesc = goodDesc == null ? null : goodDesc.trim();
    }

	@Override
	public String toString() {
		return "Good [goodId=" + goodId + ", goodName=" + goodName + ", goodPrice=" + goodPrice + ", goodType="
				+ goodType + ", goodColor=" + goodColor + ", goodCount=" + goodCount + ", goodImg=" + goodImg
				+ ", goodDesc=" + goodDesc + "]";
	}
    
    
}