package com.o2o.maile.network.push.client;

import java.util.Arrays;
import java.util.List;

import com.o2o.maile.network.netty.NettyMessageType;
import com.o2o.maile.network.netty.handler.NettyMessageHandler;
import com.o2o.maile.network.netty.handler.NettyMessageHandlerFactory;

public class PushClientMessageHandlerFactory implements
		NettyMessageHandlerFactory {

	PushClientConnectManager pushClientConnectManager;
	PushClientMessageHandlerFactory(PushClientConnectManager pushClientConnectManager)
	{
		this.pushClientConnectManager = pushClientConnectManager;
	}
	
	@Override
	public List<Integer> getNettyMessageTypes() {
		
		return Arrays.asList(NettyMessageType.CreateClientID.getType());
	}

	@Override
	public NettyMessageHandler createNettyMessageHandler(Integer messageType) {
		if(messageType.intValue() == NettyMessageType.CreateClientID.getType())
		{
			return pushClientConnectManager;
		}
		return null;
	}

}
