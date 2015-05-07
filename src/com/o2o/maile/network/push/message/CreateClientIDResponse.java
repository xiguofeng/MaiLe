package com.o2o.maile.network.push.message;

import com.o2o.maile.network.netty.NettyMessageType;
import com.o2o.maile.network.netty.codec.JsonUtil;
import com.o2o.maile.network.netty.codec.JsonUtil.JsonException;
import com.o2o.maile.network.netty.handler.NettyMessage;
import com.o2o.maile.network.netty.handler.NettyMessageImpl;

public class CreateClientIDResponse {

	public static NettyMessage encode(CreateClientIDResponse response)
	{
		String messageBody;
		try {
			messageBody = JsonUtil.beanToJson(response);
			NettyMessage nettyMessage = new NettyMessageImpl(NettyMessageType.CreateClientID.getType(), messageBody );
			return nettyMessage;
		} catch (JsonException e) {
			return null;
		}
		
	}
	
	String clientID;
	
	CreateClientIDResponse(){}

	public CreateClientIDResponse(String clientID) {
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
