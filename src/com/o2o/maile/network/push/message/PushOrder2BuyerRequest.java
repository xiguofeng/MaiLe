package com.o2o.maile.network.push.message;

import com.o2o.maile.network.netty.NettyMessageType;
import com.o2o.maile.network.netty.codec.JsonUtil;
import com.o2o.maile.network.netty.codec.JsonUtil.JsonException;
import com.o2o.maile.network.netty.handler.NettyMessage;
import com.o2o.maile.network.netty.handler.NettyMessageImpl;

public class PushOrder2BuyerRequest {
	
	public static NettyMessage encode(PushOrder2BuyerRequest request)
	{
		String jsonStr;
		try {
			jsonStr = JsonUtil.beanToJson(request);
			NettyMessage nettyRequest = new NettyMessageImpl(NettyMessageType.PushBuyerOrderInfo.getType(), jsonStr);
			return nettyRequest;
		} catch (JsonException e) {
			return null;
		}
	}
	
	public static PushOrder2BuyerRequest decode(String str)
	{
		try {
			return JsonUtil.jsonToBean(str, PushOrder2BuyerRequest.class);
		} catch (JsonException e) {
			return null;
		}
	}
	/**
	 * 订单ID
	 */
	String orderID;
	/**
	 * 订单状态
	 * 1：已被抢单
	 */
	String orderState;
	
	String sellerID;
	
	String sellerName;
	
	String sellerPhone;
	
	/**
	 * 预计送货时间 
	 * 单位： 分钟
	 */
	int probablyWaitTime;
//	String sellerTotalOrders;
	/**
	 * 卖家的留言信息
	 */
	String message;
	
	public String getOrderID() {
		return orderID;
	}
	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}
	public String getOrderState() {
		return orderState;
	}
	public void setOrderState(String orderState) {
		this.orderState = orderState;
	}
	public String getSellerID() {
		return sellerID;
	}
	public void setSellerID(String sellerID) {
		this.sellerID = sellerID;
	}
	public String getSellerName() {
		return sellerName;
	}
	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}
	public String getSellerPhone() {
		return sellerPhone;
	}
	public void setSellerPhone(String sellerPhone) {
		this.sellerPhone = sellerPhone;
	}
	public int getProbablyWaitTime() {
		return probablyWaitTime;
	}
	public void setProbablyWaitTime(int probablyWaitTime) {
		this.probablyWaitTime = probablyWaitTime;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
