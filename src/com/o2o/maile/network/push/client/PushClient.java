package com.o2o.maile.network.push.client;

import com.o2o.maile.network.netty.ConnectException;
import com.o2o.maile.network.netty.client.ClientConnectStateMonitor;
import com.o2o.maile.network.netty.handler.NettyMessageHandlerFactory;
import com.o2o.maile.network.push.message.RegistUserInfoRequest.UserType;

public interface PushClient {

	
	void stop();
	
	void addMessageHandlerFactory(NettyMessageHandlerFactory factory);

	void start() throws ConnectException;
	
	/**
	 * 推送客户端ID
	 * @return
	 */
	String clientID();
	
	/**
	 * 向推送客户端注册用户信息
	 * <p>买家选填，卖家必填</p>
	 * @param username
	 * @param password
	 */
	void registUserInfo(UserType userType, String username, String password);
	
	public void addClientConnectStateMonitor(ClientConnectStateMonitor monitor);
}
