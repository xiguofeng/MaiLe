package com.o2o.maile.entity;

import java.io.Serializable;
import java.util.List;

public class OrderInfo implements Serializable {
	// {"state":"challenged","goods":["HX0001"],"buyer":"12369","createTime":1429685379927,"buyerComment":"","seller":"12369","sellerComment":""}

	private static final long serialVersionUID = -7290060899951515026L;

	public String getBuyer() {
		return buyer;
	}

	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}

	public String getSeller() {
		return seller;
	}

	public void setSeller(String seller) {
		this.seller = seller;
	}

	public List<String> getGoods() {
		return goods;
	}

	public void setGoods(List<String> goods) {
		this.goods = goods;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getBuyerComment() {
		return buyerComment;
	}

	public void setBuyerComment(String buyerComment) {
		this.buyerComment = buyerComment;
	}

	public String getSellerComment() {
		return sellerComment;
	}

	public void setSellerComment(String sellerComment) {
		this.sellerComment = sellerComment;
	}

	/**
	 * 订单卖家
	 */
	String buyer;
	/**
	 * 订单买家
	 */
	String seller;

	/**
	 * 订单交易的商品信息
	 */
	List<String> goods;

	/**
	 * 订单创建时间
	 */
	long createTime;

	String state;

	/**
	 * 买家对订单的评价信息
	 */
	String buyerComment;

	/**
	 * 卖家对订单的评价
	 */
	String sellerComment;

}
