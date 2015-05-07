package com.o2o.maile.network.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import com.o2o.maile.network.netty.ConnectException;
import com.o2o.maile.network.netty.client.ClientConnectStateMonitor.ConnectState;
import com.o2o.maile.network.netty.codec.NettyMessageDecoder;
import com.o2o.maile.network.netty.codec.NettyMessageEncoder;
import com.o2o.maile.network.netty.handler.NettyMessage;
import com.o2o.maile.network.netty.handler.NettyMessageDispatchHandler;
import com.o2o.maile.network.netty.handler.NettyMessageHandlerFactory;
import com.o2o.maile.network.netty.handler.NettyMessageHandlerFactoryRepository;

public class NettyClientImpl {

	public static final int READ_TIMEOUT = 12;
	public static final int WRITE_TIMEOUT = 5;

	String host;
	int port;
	List<ClientConnectStateMonitor> connectStateMonitors = new ArrayList<ClientConnectStateMonitor>();

	NettyMessageHandlerFactoryRepository repository;
	ClientConnectMonitorHandler clientConnectMonitorHandler;

	EventLoopGroup eventLoopGroup;
	Bootstrap bootstrap;
	Channel clientChannel;
	ChannelFuture clientChannelFuture;
	boolean isStoped = true;

	/**
	 * 保护clientChannel资源
	 */
	static Object locker = new Object();

	public NettyClientImpl(String host, int port) {
		super();
		this.host = host;
		this.port = port;
		repository = new NettyMessageHandlerFactoryRepository();
	}

	public void start() throws ConnectException {
		if (!isStoped) {
			throw new ConnectException("Netty client is already started.");
		}
		isStoped = false;
		clientConnectMonitorHandler = new ClientConnectMonitorHandler();
		bootstrap = configureBootstrap();
		synchronized (locker) {
			clientChannel = connect();
		}

		if (null == clientChannel) {
			throw new ConnectException("Failed to connect remote server.");
		}

	}

	public void stop() {
		synchronized (locker) {
			if (!isStoped) {
				isStoped = true;
				clientChannel.close();
				eventLoopGroup.shutdownGracefully();
			}
		}

	}

	public void registNettyMessageHandlerFactory(
			NettyMessageHandlerFactory factory) {
		repository.registNettyMessageHandlerFactory(factory);
	}

	public void sendAsync(NettyMessage message) throws ConnectException {
		if (!isStoped) {
			if (clientChannel.isWritable()) {
				clientChannel.writeAndFlush(message);
			} else {
				clientChannel.close();
				if (reconnect()) {
					clientChannel.writeAndFlush(message);
				}
				throw new ConnectException("Cannot connect to server.");
			}

		} else {
			throw new ConnectException("Client not started.");
		}
	}

	public void addClientConnectStateMonitor(
			ClientConnectStateMonitor connectStateMonitor) {
		connectStateMonitors.add(connectStateMonitor);
	}

	Channel connect() {
		synchronized (locker) {
			if (!isStoped) {
				clientChannelFuture = bootstrap.connect();
				clientChannelFuture.addListener(new ChannelFutureListener() {
					@Override
					public void operationComplete(ChannelFuture future)
							throws Exception {
						if (future.cause() != null) {
							notifyConnectStateMonitor(
									ConnectState.connectFailed,
									future.channel());
						} else {
							notifyConnectStateMonitor(ConnectState.connected,
									future.channel());
						}
					}
				});
				clientChannelFuture.syncUninterruptibly();
				Channel clientChannel = clientChannelFuture.channel();
				clientChannel.closeFuture().addListener(
						new ChannelFutureListener() {
							@Override
							public void operationComplete(ChannelFuture future)
									throws Exception {

								notifyConnectStateMonitor(
										ConnectState.disconnect,
										future.channel());
							}
						});
				return clientChannel;
			}
			return null;
		}

	}

	private void notifyConnectStateMonitor(ConnectState connectState,
			Channel channel) {
		for (ClientConnectStateMonitor connectStateMonitor : connectStateMonitors) {
			connectStateMonitor.onStateChange(connectState, channel);
		}
	}

	public boolean reconnect() {
		synchronized (locker) {
			if (!clientChannel.isActive()) {
				clientChannel = connect();
				if (null == clientChannel) {
					return false;
				}
			}
			return true;
		}

	}

	Bootstrap configureBootstrap() {

		Bootstrap b = new Bootstrap();
		eventLoopGroup = new NioEventLoopGroup();

		b.group(eventLoopGroup).channel(NioSocketChannel.class)
				.remoteAddress(host, port)
				.handler(new ChannelInitializer<SocketChannel>() {
					@Override
					public void initChannel(SocketChannel ch) throws Exception {
						ChannelPipeline channelPipeline = ch.pipeline();
						channelPipeline.addLast(
								"NettyMessageDecoder",
								new NettyMessageDecoder(Charset
										.forName("UTF-8")));
						channelPipeline.addLast(
								"NettyMessageEncoder",
								new NettyMessageEncoder(Charset
										.forName("UTF-8")));
						channelPipeline.addLast("IdleStateHandler",
								new IdleStateHandler(READ_TIMEOUT,
										WRITE_TIMEOUT, 0));
						channelPipeline.addLast("ClientConnectMonitorHandler",
								clientConnectMonitorHandler);
						channelPipeline.addLast("NettyMessageDispatchHandler",
								new NettyMessageDispatchHandler(repository));
					}
				});

		return b;
	}
}
