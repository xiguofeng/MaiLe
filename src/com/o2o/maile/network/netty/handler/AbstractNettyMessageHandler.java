package com.o2o.maile.network.netty.handler;

import io.netty.channel.ChannelHandlerContext;
import android.util.Log;

import com.o2o.maile.network.netty.codec.JsonUtil;

public abstract class AbstractNettyMessageHandler<T> implements
		NettyMessageHandler {

	public void handleNettyMessage(ChannelHandlerContext ctx,
			NettyMessage nettyMessage) {
		Log.e("xxx_nettyMessage.getMessageBody()",
				nettyMessage.getMessageBody());
		Class<T> clazz = getRequestType();
		try {
			if (clazz.equals(String.class)) {

				handle(ctx, (T) nettyMessage.getMessageBody());
			} else if (null == nettyMessage.getMessageBody()
					|| "".equals(nettyMessage.getMessageBody())) {
				handle(ctx, null);
			} else {
				T request = JsonUtil.jsonToBean(nettyMessage.getMessageBody(),
						clazz);
				handle(ctx, request);
			}

		} catch (Exception e) {
		}
	}

	protected abstract void handle(ChannelHandlerContext ctx, T request);

	protected abstract Class<T> getRequestType();
}
