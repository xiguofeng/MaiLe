package com.o2o.maile.network.netty.handler;

public class NettyMessageImpl implements NettyMessage {

	Integer messageType;
	
	String messageBody;
	
	public NettyMessageImpl(){}
	
	public NettyMessageImpl(Integer messageType, String messageBody) {
		super();
		this.messageType = messageType;
		this.messageBody = messageBody;
	}

	@Override
	public Integer getMessageType() {
		return messageType;
	}

	@Override
	public String getMessageBody() {
		return messageBody;
	}

	@Override
	public String toString() {
		return "NettyMessageImpl [messageType=" + messageType
				+ ", messageBody=" + messageBody + "]";
	}

	
}
