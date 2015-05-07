package com.o2o.maile.network.netty;

public enum NettyMessageType {

	Ping, RegistClientID, CreateClientID, RegistUserInfo,
	/**
	 * 向卖家推送订单信息
	 */
	PushSellOrderInfo,
	/**
	 * 向买家推送订单状态
	 */
	PushBuyerOrderInfo;

	public static NettyMessageType toNettyMessageType(Integer type) {

		NettyMessageType[] messageTypes = NettyMessageType.values();
		if (messageTypes.length > type) {
			return messageTypes[type];
		}
		return null;
	}

	public int getType() {
		return this.ordinal();
	}
}
