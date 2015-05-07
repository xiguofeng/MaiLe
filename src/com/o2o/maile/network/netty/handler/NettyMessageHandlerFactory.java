package com.o2o.maile.network.netty.handler;

import java.util.List;

public interface NettyMessageHandlerFactory {

	/**
	 * 该工厂负责创建Handler的消息类型
	 * @return
	 */
	List<Integer> getNettyMessageTypes();
	
	/**
	 * 根据消息类型创建消息的处理器
	 * @param messageType
	 * @return
	 */
	NettyMessageHandler createNettyMessageHandler(Integer messageType);
}
