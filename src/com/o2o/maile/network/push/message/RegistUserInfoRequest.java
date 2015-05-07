package com.o2o.maile.network.push.message;

import com.o2o.maile.network.netty.NettyMessageType;
import com.o2o.maile.network.netty.codec.JsonUtil;
import com.o2o.maile.network.netty.codec.JsonUtil.JsonException;
import com.o2o.maile.network.netty.handler.NettyMessage;
import com.o2o.maile.network.netty.handler.NettyMessageImpl;

public class RegistUserInfoRequest {
	
	public static enum UserType
	{
		buyer,
		seller;
	}
	public static NettyMessage encode(RegistUserInfoRequest loginRequest)
	{
		String messageBody;
		try {
			messageBody = JsonUtil.beanToJson(loginRequest);
			NettyMessage nettyMessage = new NettyMessageImpl(NettyMessageType.RegistUserInfo.getType(), messageBody );
			return nettyMessage;
		} catch (JsonException e) {
			return null;
		}
		
	}

	String username;
	
	String password;
	
	UserType userType;
	
	RegistUserInfoRequest(){}

	public RegistUserInfoRequest(UserType userType, String username, String password) {
		super();
		this.username = username;
		this.password = password;
		this.userType = userType;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}
	
	
}
