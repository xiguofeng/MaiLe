package com.o2o.maile.network.push.message;

import com.o2o.maile.network.netty.NettyMessageType;
import com.o2o.maile.network.netty.handler.NettyMessage;
import com.o2o.maile.network.netty.handler.NettyMessageImpl;

public class CreateClientIDRequest {
	
	public static NettyMessage encode()
	{
		NettyMessage nettyMessage = new NettyMessageImpl(NettyMessageType.CreateClientID.getType(), "");
		return nettyMessage;
	}

	
	
}
