package com.o2o.maile.network.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

import java.nio.charset.Charset;
import java.util.List;

import com.o2o.maile.network.netty.handler.NettyMessage;
import com.o2o.maile.network.netty.handler.NettyMessageImpl;

@Sharable
public class NettyMessageDecoder  extends MessageToMessageDecoder<ByteBuf> {

	class Cache{
		private int byteLen;
		private ByteBuf buf;
		
		Cache(ByteBuf msg)
		{
			byteLen = msg.readInt();
			buf = Unpooled.buffer(byteLen);
			buf.writeBytes(msg);
		}
		
		public void write(ByteBuf msg)
		{
			int writeLen = Math.min(buf.writableBytes(), msg.readableBytes());
			buf.writeBytes(msg, writeLen);
		}
		
		public boolean isComplate()
		{
			return buf.writableBytes() <= 0;
		}
	}
	
	static AttributeKey<Cache> key = AttributeKey.valueOf("decode_cache");
	
	Charset charset;
	
	
	public NettyMessageDecoder(Charset charset) {
		super();
		this.charset = charset;
	}


	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf msg,
			List<Object> out) throws Exception {
		Attribute<Cache> attr = ctx.attr(key);
		Cache cache = attr.get();
		if(null == cache)
		{
			cache = new Cache(msg);
			attr.set(cache);
		}
		else
		{
			cache.write(msg);
		}
		
		if(cache.isComplate())
		{
			attr.remove();
			Integer messageType = cache.buf.readInt();
			String messageBody = cache.buf.toString(charset);
			NettyMessage nettyMessage = new NettyMessageImpl(messageType, messageBody);
			out.add(nettyMessage);
		}
		
	}

	@Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		cleatResource(ctx);
		ctx.fireChannelReadComplete();
    }
	@Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
		cleatResource(ctx);
        ctx.fireExceptionCaught(cause);
    }
	
	private void cleatResource(ChannelHandlerContext ctx)
	{
		Attribute<Cache> attr = ctx.attr(key);
		attr.remove();
	}
}
