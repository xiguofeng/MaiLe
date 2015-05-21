package com.o2o.maile.network.netty.client;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoop;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

import java.util.concurrent.TimeUnit;

import com.o2o.maile.network.netty.NettyMessageType;
import com.o2o.maile.network.netty.handler.NettyMessage;
import com.o2o.maile.network.netty.handler.NettyMessageImpl;

@Sharable
public class ClientConnectMonitorHandler extends SimpleChannelInboundHandler<NettyMessage>{
	
	
	
	/**
	 * 连接通道是否被关闭
	 */
	boolean channelClosed = true;
	
	
	
	public ClientConnectMonitorHandler() {
		
	}

	@Override
    public void channelActive(ChannelHandlerContext ctx) {
		channelClosed = false;
		
		println("channelActive: " + ctx.channel().remoteAddress());
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, NettyMessage msg) throws Exception {
        if(msg.getMessageType().intValue() == NettyMessageType.Ping.getType())
        {
        	//Discard
        	println("Ping message received.");
        }
        else
        {
        	ctx.fireChannelRead(msg);
        }
    }

    @Override
    public void userEventTriggered(final ChannelHandlerContext ctx, Object evt) {
        if (!(evt instanceof IdleStateEvent)) {
            return;
        }

        IdleStateEvent e = (IdleStateEvent) evt;
        if (e.state() == IdleState.READER_IDLE) {
            
            println("Not receive msg for long time, close channel.");
            closeChannel(ctx);
        }
        else if(e.state() == IdleState.WRITER_IDLE)
        {
        	final EventLoop loop = ctx.channel().eventLoop();
            loop.schedule(new Runnable() {
                @Override
                public void run() {
                    println("Write ping to: ");
                    NettyMessage nettyMessage = new NettyMessageImpl(NettyMessageType.Ping.getType(), "ping");
                	ctx.writeAndFlush(nettyMessage);
                }
            }, 0, TimeUnit.SECONDS);
        	
        }
    }

    @Override
    public void channelInactive(final ChannelHandlerContext ctx) {
        println("Disconnected from: " + ctx.channel().remoteAddress());
        closeChannel(ctx);
    }

    @Override
    public void channelUnregistered(final ChannelHandlerContext ctx) throws Exception {
    	closeChannel(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        closeChannel(ctx);
    }

    void println(String msg) {
    	System.err.format("[UPTIME: %5ds] %s%n", (System.currentTimeMillis()) / 1000, msg);
    }
    
    
    void closeChannel(ChannelHandlerContext ctx)
    {
    	if(!channelClosed)
    	{
    		ctx.close();
    		channelClosed = true; 
    	}
    	
    }
}
