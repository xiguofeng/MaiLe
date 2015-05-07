package com.o2o.maile.network.netty.handler;


import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
@Sharable
public class NettyMessageDispatchHandler extends SimpleChannelInboundHandler<NettyMessage>{

	NettyMessageHandlerFactoryRepository repository;
	
	

	public NettyMessageDispatchHandler(
			NettyMessageHandlerFactoryRepository repository) {
		super();
		this.repository = repository;
	}



	@Override
	protected void channelRead0(ChannelHandlerContext ctx, NettyMessage msg)
			throws Exception {
		Integer messageType = msg.getMessageType();
		NettyMessageHandlerFactory messageHandlerFactory = repository.getNettyMessageHandlerFactory(messageType);
		if(null != messageHandlerFactory)
		{
			NettyMessageHandler nettyMessageHandler = messageHandlerFactory.createNettyMessageHandler(messageType);
			nettyMessageHandler.handleNettyMessage(ctx, msg);
		}
		else
		{
			//logger.error("No messageHandlerFactory in repository, discard nettymessage, {}", msg);
		}
		
	}
	
	@Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
		//logger.error("Exception Caught in channel, {}", cause);
    }
}
