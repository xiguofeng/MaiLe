package com.o2o.maile.network.netty.handler;

public interface NettyMessage {

	Integer getMessageType();
	
	String getMessageBody();
}
