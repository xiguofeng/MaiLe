package com.o2o.maile.network.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.nio.charset.Charset;
import java.util.List;

import android.annotation.SuppressLint;

import com.o2o.maile.network.netty.ConnectException;
import com.o2o.maile.network.netty.handler.NettyMessage;
@Sharable
public class NettyMessageEncoder   extends MessageToMessageEncoder<NettyMessage>{

	Charset charset;
	
	
	public NettyMessageEncoder(Charset charset) {
		super();
		this.charset = charset;
	}


	@SuppressLint("NewApi")
	@Override
	protected void encode(ChannelHandlerContext ctx, NettyMessage msg,
			List<Object> out) throws Exception {
		if(null == msg.getMessageType())
		{
			throw new ConnectException("Message type cannot be null, encode failed.");
		}
		
		byte[] body = null;
		String messageBody = msg.getMessageBody();
		if(null != messageBody)
		{
			body = messageBody.getBytes(charset);
		}
		else
		{
			body = new byte[0];
		}
		
		int byteLen = body.length + 4;
		ByteBuf outBuf = Unpooled.buffer(byteLen);
		outBuf.writeInt(msg.getMessageType().intValue());
		outBuf.writeBytes(body);
		out.add(outBuf);
	}

}
