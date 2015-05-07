package com.o2o.maile.network.push.client;

import com.o2o.maile.network.netty.ConnectException;
import com.o2o.maile.network.netty.client.ClientConnectStateMonitor;
import com.o2o.maile.network.netty.client.NettyClientImpl;
import com.o2o.maile.network.netty.handler.NettyMessageHandlerFactory;
import com.o2o.maile.network.push.message.RegistUserInfoRequest.UserType;

public class PushClientImpl implements PushClient{

	String host;
	int port;
	NettyClientImpl nettyClient;
	
	String username;
	String password;
	PushClientConnectManager connectManager;
	
	public PushClientImpl(String host, int port) {
		super();
		this.host = host;
		this.port = port;
		
		nettyClient = new NettyClientImpl(host, port);
		connectManager = new PushClientConnectManager(nettyClient);
	}

	@Override
	public void start() throws ConnectException {
		
		nettyClient.addClientConnectStateMonitor(connectManager);
		nettyClient.registNettyMessageHandlerFactory(new PushClientMessageHandlerFactory(connectManager));
		nettyClient.start();
	}

	@Override
	public void stop() {
		nettyClient.stop();
	}

	@Override
	public void addMessageHandlerFactory(NettyMessageHandlerFactory factory) {
		nettyClient.registNettyMessageHandlerFactory(factory);
	}

	@Override
	public void registUserInfo(UserType userType, String username, String password) {
		this.username = username;
		this.password = password;
		connectManager.registUserInfo(userType, username, password);
	}

	@Override
	public String clientID() {
		return connectManager.getClientID();
	}
	
	@Override
	public void addClientConnectStateMonitor(ClientConnectStateMonitor monitor) {
		nettyClient.addClientConnectStateMonitor(monitor);
	}
	
}
