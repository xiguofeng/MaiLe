package com.o2o.maile.network.netty.handler;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class NettyMessageHandlerFactoryRepository {

	Map<Integer, NettyMessageHandlerFactory> repository = new ConcurrentHashMap<Integer, NettyMessageHandlerFactory>();
	
	public void registNettyMessageHandlerFactory(NettyMessageHandlerFactory factory)
	{
		List<Integer> messageTypes = factory.getNettyMessageTypes();
		if(null != messageTypes)
		{
			for(Integer messageType: messageTypes)
			{
				if(repository.containsKey(messageType))
				{
					throw new IllegalArgumentException("MessageHanlderFactory has been registered. messageType=" + messageType);
				}
				else
				{
					repository.put(messageType, factory);
				}
			}
		}
	}
	
	public NettyMessageHandlerFactory getNettyMessageHandlerFactory(Integer messageType)
	{
		return repository.get(messageType);
	}
}
