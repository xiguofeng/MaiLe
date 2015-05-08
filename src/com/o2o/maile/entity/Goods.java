package com.o2o.maile.entity;

import java.io.Serializable;

public class Goods implements Serializable {

	private static final long serialVersionUID = 8912365559481657349L;

	private String id;

	private String name;

	private String brief;

	private String price;

	private String iconUrl;

	private String standard;

	private String categoryIDLevel2;

	private String categoryNameLevel2;

	private String categoryIDLevel1;

	private String categoryNameLevel1;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBrief() {
		return brief;
	}

	public void setBrief(String brief) {
		this.brief = brief;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public String getStandard() {
		return standard;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}

	public String getCategoryIDLevel2() {
		return categoryIDLevel2;
	}

	public void setCategoryIDLevel2(String categoryIDLevel2) {
		this.categoryIDLevel2 = categoryIDLevel2;
	}

	public String getCategoryNameLevel2() {
		return categoryNameLevel2;
	}

	public void setCategoryNameLevel2(String categoryNameLevel2) {
		this.categoryNameLevel2 = categoryNameLevel2;
	}

	public String getCategoryIDLevel1() {
		return categoryIDLevel1;
	}

	public void setCategoryIDLevel1(String categoryIDLevel1) {
		this.categoryIDLevel1 = categoryIDLevel1;
	}

	public String getCategoryNameLevel1() {
		return categoryNameLevel1;
	}

	public void setCategoryNameLevel1(String categoryNameLevel1) {
		this.categoryNameLevel1 = categoryNameLevel1;
	}

}
