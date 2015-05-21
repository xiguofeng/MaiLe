package com.o2o.maile.network.netty.client;

import io.netty.channel.Channel;


public abstract class ClientConnectStateMonitor {

	public enum ConnectState
	{
		/**
		 * 连接断开
		 */
		disconnect,
		/**
		 * 连接建立
		 */
		connected,
		/**
		 * 连接失败
		 */
		connectFailed;
	}
	
	void onStateChange(ConnectState connectState, Channel channel)
	{
		switch(connectState)
		{
		case disconnect:
			disconnect(channel);
			break;
		case connected:
			connected(channel);
			break;
		case connectFailed:
		connectFailed(channel);
		}
	}
	
	/**
	 * 链接被中断时调用
	 */
	protected abstract void disconnect(Channel channel);
	/**
	 * 连接被建立
	 */
	protected abstract void connected(Channel channel);
	/**
	 * 连接(重连接)失败时调用
	 */
	protected abstract void connectFailed(Channel channel);
	
	
}
