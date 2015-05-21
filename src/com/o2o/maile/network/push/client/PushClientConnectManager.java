package com.o2o.maile.network.push.client;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;

import com.o2o.maile.network.netty.client.ClientConnectStateMonitor;
import com.o2o.maile.network.netty.client.NettyClientImpl;
import com.o2o.maile.network.netty.codec.JsonUtil;
import com.o2o.maile.network.netty.codec.JsonUtil.JsonException;
import com.o2o.maile.network.netty.handler.NettyMessage;
import com.o2o.maile.network.netty.handler.NettyMessageHandler;
import com.o2o.maile.network.push.message.CreateClientIDRequest;
import com.o2o.maile.network.push.message.CreateClientIDResponse;
import com.o2o.maile.network.push.message.RegistClientIDRequest;
import com.o2o.maile.network.push.message.RegistUserInfoRequest;
import com.o2o.maile.network.push.message.RegistUserInfoRequest.UserType;

public class PushClientConnectManager extends ClientConnectStateMonitor
		implements NettyMessageHandler {

	NettyClientImpl nettyClient;
	String username;
	String password;
	UserType userType;
	Channel channel = null;

	String clientID = null;

	PushClientConnectManager(NettyClientImpl nettyClient) {
		this.nettyClient = nettyClient;
	}

	@Override
	public void disconnect(Channel channel) {
		// 避免connectFailed导致的重复disconnect调用
		if (this.channel.isActive() && this.channel.isWritable()) {
			return;
		}
		closeQuietly(channel);
		closeQuietly(this.channel);
		this.channel = null;
		// 最大延迟间隔为20S
		long maxDelay = 20000;
		boolean result = false;
		long delay = 1000;
		
		// 循环建立连接， 直至连接建立成功
		while (!result) {
			delay = Math.min(delay + 1000, maxDelay);
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
			}
			result = nettyClient.reconnect();
		}
	}

	@Override
	public void connected(Channel channel) {
		this.channel = channel;
		initClientID(channel);
		registUserInfo2Server(channel, userType, username, password);
	}

	@Override
	public void connectFailed(Channel channel) {

		closeQuietly(channel);
	}

	public String getClientID() {
		return clientID;
	}

	public void registUserInfo(UserType userType, String username,
			String password) {
		this.username = username;
		this.password = password;
		this.userType = userType;
		registUserInfo2Server(channel, userType, username, password);
	}

	private void registUserInfo2Server(Channel channel, UserType userType,
			String username, String password) {
		System.out.println("registUserInfo2Server:" + (null != channel));
		if (null != channel) {
			if (null != username && null != password) {
				RegistUserInfoRequest loginRequest = new RegistUserInfoRequest(
						userType, username, password);
				NettyMessage nettyMessage = RegistUserInfoRequest
						.encode(loginRequest);
				ChannelFuture future = channel.writeAndFlush(nettyMessage);
				future.syncUninterruptibly();
				if (null != future.cause()) {
					System.out.println("registUserInfo2Server error");
					channel.close();
				} else {
					System.out.println("registUserInfo2Server success");
				}
			}
		}
	}

	void initClientID(Channel channel) {
		if (null == clientID) {
			NettyMessage nettyMessage = CreateClientIDRequest.encode();
			channel.writeAndFlush(nettyMessage);
		} else {
			RegistClientIDRequest request = new RegistClientIDRequest(clientID);
			NettyMessage nettyMessage = RegistClientIDRequest.encode(request);
			channel.writeAndFlush(nettyMessage);
		}
	}

	@Override
	public void handleNettyMessage(ChannelHandlerContext ctx,
			NettyMessage nettyMessage) {
		try {
			CreateClientIDResponse response = JsonUtil
					.jsonToBean(nettyMessage.getMessageBody(),
							CreateClientIDResponse.class);
			if (null != response) {
				this.clientID = response.getClientID();
			}
		} catch (JsonException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void closeQuietly(Channel channel) {
		if (channel != null) {
			try {
				channel.close();
			} catch (Throwable t) {
			}
		}
	}

}
