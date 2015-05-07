package com.o2o.maile.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * app信息管理
 */
public class AppInfoManager {

	public static final String APP_INFO_PREFERNCE_KEY = "appinfo";

	public static final String PUSH_URL_KEY = "push_url";

	public static final String CLIENT_ID_KEY = "client_id";

	/**
	 * 配置文件设置Push地址
	 */
	public static void setPushUrl(Context context, String url) {
		SharedPreferences.Editor userInfo = context.getSharedPreferences(
				APP_INFO_PREFERNCE_KEY, Context.MODE_PRIVATE).edit();

		userInfo.putString(PUSH_URL_KEY, url);
		userInfo.commit();
	}

	/**
	 * 从配置文件获取Push地址
	 */
	public static String getPushUrl(Context context) {
		SharedPreferences appInfo = context.getSharedPreferences(
				APP_INFO_PREFERNCE_KEY, Context.MODE_PRIVATE);

		return appInfo.getString(PUSH_URL_KEY, "");
	}

	/**
	 * 配置文件设置ClientID
	 */
	public static void setClinetID(Context context, String clientID) {
		SharedPreferences.Editor userInfo = context.getSharedPreferences(
				APP_INFO_PREFERNCE_KEY, Context.MODE_PRIVATE).edit();

		userInfo.putString(CLIENT_ID_KEY, clientID);
		userInfo.commit();
	}

	/**
	 * 从配置文件获取ClientID
	 */
	public static String getClinetID(Context context) {
		SharedPreferences appInfo = context.getSharedPreferences(
				APP_INFO_PREFERNCE_KEY, Context.MODE_PRIVATE);

		return appInfo.getString(CLIENT_ID_KEY, "");
	}

	public static void clearAppInfo(Context context) {
		SharedPreferences.Editor appInfo = context.getSharedPreferences(
				APP_INFO_PREFERNCE_KEY, Context.MODE_PRIVATE).edit();

		appInfo.putString(PUSH_URL_KEY, "");
		appInfo.putString(CLIENT_ID_KEY, "");
		appInfo.commit();
	}

}
