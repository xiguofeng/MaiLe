package com.o2o.maile.network.netty.client;

import com.o2o.maile.network.netty.ConnectException;
import com.o2o.maile.network.netty.handler.NettyMessage;
import com.o2o.maile.network.netty.handler.NettyMessageHandlerFactory;

public interface NettyClient {
	
	void start(ClientConnectStateMonitor connectStateMonitor) throws ConnectException;
	
	void stop();
	
	void registNettyMessageHandlerFactory(NettyMessageHandlerFactory factory);
	
	void sendAsync(NettyMessage message) throws ConnectException;
}
