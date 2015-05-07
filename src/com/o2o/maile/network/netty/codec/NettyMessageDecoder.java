package com.o2o.maile.network.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.nio.charset.Charset;
import java.util.List;

import com.o2o.maile.network.netty.handler.NettyMessage;
import com.o2o.maile.network.netty.handler.NettyMessageImpl;

@Sharable
public class NettyMessageDecoder  extends MessageToMessageDecoder<ByteBuf> {

	Charset charset;
	
	
	public NettyMessageDecoder(Charset charset) {
		super();
		this.charset = charset;
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf msg,
			List<Object> out) throws Exception {
		Integer messageType = msg.readInt();
		String messageBody = msg.toString(charset);
		
		NettyMessage nettyMessage = new NettyMessageImpl(messageType, messageBody);
		out.add(nettyMessage);
	}

}
