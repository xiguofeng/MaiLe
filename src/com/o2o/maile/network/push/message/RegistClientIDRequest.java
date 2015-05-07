package com.o2o.maile.network.push.message;

import com.o2o.maile.network.netty.NettyMessageType;
import com.o2o.maile.network.netty.codec.JsonUtil;
import com.o2o.maile.network.netty.codec.JsonUtil.JsonException;
import com.o2o.maile.network.netty.handler.NettyMessage;
import com.o2o.maile.network.netty.handler.NettyMessageImpl;

public class RegistClientIDRequest {

	public static NettyMessage encode(RegistClientIDRequest request)
	{
		String messageBody;
		try {
			messageBody = JsonUtil.beanToJson(request);
			NettyMessage nettyMessage = new NettyMessageImpl(NettyMessageType.RegistClientID.getType(), messageBody);
			return nettyMessage;
		} catch (JsonException e) {
			return null;
		}
		
	}
	String clientID;
	
	RegistClientIDRequest(){}

	public RegistClientIDRequest(String clientID) {
		super();
		this.clientID = clientID;
	}

	public String getClientID() {
		return clientID;
	}

	public void setClientID(String clientID) {
		this.clientID = clientID;
	}

	
}
