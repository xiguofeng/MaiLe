package com.o2o.maile.entity;

import java.io.Serializable;

public class OrderClient implements Serializable {

	private static final long serialVersionUID = 8912365559481657349L;

	public enum OrderState {
		/**
		 * 订单已创建(对应买家下单)
		 */
		created("创建"),

		/**
		 * 卖家已抢单(对应卖家抢单)
		 */
		challenged("已成交"),

		/**
		 * 货物已投递(对应卖家订单确认)
		 */
		delivered("已送达"),

		/**
		 * 交易完成，对应买家订单确认
		 */
		success("已确认"),

		/**
		 * 订单超时，未交易成功，对应30分钟内没有卖家抢单， 交易失败
		 */
		timeout("超时");

		String state;

		OrderState(String state) {
			this.state = state;
		}

		public String getState() {
			return state;
		}
	}

	private String id;

	private String goodsName;

	private String goodsId;

	private String goodsBrief;

	private String goodsPrice;

	private String goodsImgUrl;

	private String buyNum;

	private String buyUserName;

	private String buyAddress;

	private String buyPhone;

	private String buyOrderSucNum;

	private String buyPrice;

	private String latitude;

	private String longitude;

	private String totalPrice;

	private int probablyWaitTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public String getGoodsBrief() {
		return goodsBrief;
	}

	public void setGoodsBrief(String goodsBrief) {
		this.goodsBrief = goodsBrief;
	}

	public String getGoodsPrice() {
		return goodsPrice;
	}

	public void setGoodsPrice(String goodsPrice) {
		this.goodsPrice = goodsPrice;
	}

	public String getGoodsImgUrl() {
		return goodsImgUrl;
	}

	public void setGoodsImgUrl(String goodsImgUrl) {
		this.goodsImgUrl = goodsImgUrl;
	}

	public String getBuyUserName() {
		return buyUserName;
	}

	public void setBuyUserName(String buyUserName) {
		this.buyUserName = buyUserName;
	}

	public String getBuyNum() {
		return buyNum;
	}

	public void setBuyNum(String buyNum) {
		this.buyNum = buyNum;
	}

	public String getBuyAddress() {
		return buyAddress;
	}

	public void setBuyAddress(String buyAddress) {
		this.buyAddress = buyAddress;
	}

	public String getBuyPhone() {
		return buyPhone;
	}

	public void setBuyPhone(String buyPhone) {
		this.buyPhone = buyPhone;
	}

	public String getBuyOrderSucNum() {
		return buyOrderSucNum;
	}

	public void setBuyOrderSucNum(String buyOrderSucNum) {
		this.buyOrderSucNum = buyOrderSucNum;
	}

	public String getBuyPrice() {
		return buyPrice;
	}

	public void setBuyPrice(String buyPrice) {
		this.buyPrice = buyPrice;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}

	public int getProbablyWaitTime() {
		return probablyWaitTime;
	}

	public void setProbablyWaitTime(int probablyWaitTime) {
		this.probablyWaitTime = probablyWaitTime;
	}

}
