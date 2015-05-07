package com.o2o.maile.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.o2o.maile.entity.User;

/**
 * 用户信息管理类
 */
public class UserInfoManager {

	public static final String USER_INFO_PREFERNCE_KEY = "userinfo";

	public static final String USER_FIRST_USE_KEY = "ISFIRSTUSE";

	public static final String USER_ID_KEY = "user_id";

	public static final String USER_NAME_KEY = "user_name";

	public static final String USER_PWD_KEY = "password";

	public static final String USER_REAL_NAME_KEY = "realname";

	public static final String USER_COMPANY_NAME_KEY = "companyname";

	public static final String USER_PROVINCE_KEY = "province";

	public static final String USER_CITY_KEY = "city";

	public static final String USER_BIRTHDAY_KEY = "birthday";

	public static final String USER_SEX_KEY = "sex";

	public static final String USER_SIGNATURE_KEY = "signature";

	public static final String USER_LAST_LOGINTIME_KEY = "last_login_time";

	public static final String USER_LAST_LOGINIP_KEY = "last_login_ip";

	public static final String USER_REMEMBER_PSW = "is_remember_pwd";

	public static final String USER_LOGIN_IN = "is_login_in";

	public static final String USER_LOGIN_IN_IS_AUTO = "is_login_in_auto";

	public static final String USER_GESTURUE_PWD = "gesturue_pwd";

	public static final String USER_GESTURUE_PWD_IS_HAS = "is_has_gesturue_pwd";

	public static final String USER_GESTURUE_PWD_IS_OPEN = "is_open_gesturue_pwd";

	public static User userInfo = new User();

	/**
	 * 保存用户登录返回信息
	 * 
	 * @param context
	 */
	public static void setUserInfo(Context context) {
		SharedPreferences userInfoPreferences = context.getSharedPreferences(
				USER_INFO_PREFERNCE_KEY, Context.MODE_PRIVATE);

		UserInfoManager.userInfo.setId(userInfoPreferences.getString(
				USER_ID_KEY, ""));

		UserInfoManager.userInfo.setUsername(userInfoPreferences.getString(
				USER_NAME_KEY, ""));
		UserInfoManager.userInfo.setPassword(userInfoPreferences.getString(
				USER_PWD_KEY, ""));
		UserInfoManager.userInfo.setRealName(userInfoPreferences.getString(
				USER_REAL_NAME_KEY, ""));
		UserInfoManager.userInfo.setCompanyname(userInfoPreferences.getString(
				USER_COMPANY_NAME_KEY, ""));
		UserInfoManager.userInfo.setProvince(userInfoPreferences.getString(
				USER_PROVINCE_KEY, ""));
		UserInfoManager.userInfo.setCity(userInfoPreferences.getString(
				USER_CITY_KEY, ""));

		UserInfoManager.userInfo.setSex(userInfoPreferences.getString(
				USER_SEX_KEY, ""));
		UserInfoManager.userInfo.setSignature(userInfoPreferences.getString(
				USER_SIGNATURE_KEY, ""));
		UserInfoManager.userInfo.setBirthday(userInfoPreferences.getString(
				USER_BIRTHDAY_KEY, ""));

		UserInfoManager.userInfo.setLastLoginTime(userInfoPreferences
				.getString(USER_LAST_LOGINTIME_KEY, ""));
	}

	/**
	 * 保存用户登录返回信息到配置文件
	 * 
	 * @param context
	 * @param userName
	 * @param userPass
	 * @param user
	 *            登录成功后，服务器返回的用户信息
	 */
	public static void saveUserInfo(Context context, String userName,
			String userPass, User user) {
		if (null != user) {
			SharedPreferences.Editor userInfoSp = context.getSharedPreferences(
					USER_INFO_PREFERNCE_KEY, Context.MODE_PRIVATE).edit();

			userInfoSp.putString(USER_ID_KEY,
					null == user.getId() ? "" : user.getId());

			userInfoSp.putString(USER_NAME_KEY, userName);
			userInfoSp.putString(USER_PWD_KEY, userPass);
			userInfoSp.putString(USER_REAL_NAME_KEY, user.getRealName());
			userInfoSp.putString(USER_COMPANY_NAME_KEY, user.getCompanyname());
			userInfoSp.putString(USER_PROVINCE_KEY,
					null == user.getProvince() ? "" : user.getProvince());
			userInfoSp.putString(USER_CITY_KEY, null == user.getCity() ? ""
					: user.getCity());

			userInfoSp.putString(USER_SEX_KEY,
					null == user.getSex() ? "" : user.getSex());
			userInfoSp.putString(USER_SIGNATURE_KEY,
					null == user.getSignature() ? "" : user.getSignature());
			userInfoSp.putString(USER_BIRTHDAY_KEY,
					null == user.getBirthday() ? "" : user.getBirthday());
			userInfoSp.putString(USER_LAST_LOGINTIME_KEY, null == user
					.getLastLoginTime() ? "" : user.getLastLoginTime());
			userInfoSp.commit();
		}

	}

	public static void clearUserInfo(Context context) {
		SharedPreferences.Editor userInfoSp = context.getSharedPreferences(
				USER_INFO_PREFERNCE_KEY, Context.MODE_PRIVATE).edit();

		userInfoSp.putString(USER_ID_KEY, "");

		userInfoSp.putString(USER_NAME_KEY, "");
		userInfoSp.putString(USER_PWD_KEY, "");
		userInfoSp.putString(USER_REAL_NAME_KEY, "");
		userInfoSp.putString(USER_COMPANY_NAME_KEY, "");
		userInfoSp.putString(USER_PROVINCE_KEY, "");
		userInfoSp.putString(USER_CITY_KEY, "");

		userInfoSp.putString(USER_BIRTHDAY_KEY, "");
		userInfoSp.putString(USER_SEX_KEY, "");
		userInfoSp.putString(USER_SIGNATURE_KEY, "");
		userInfoSp.putString(USER_LAST_LOGINTIME_KEY, "");

		userInfoSp.putBoolean(USER_LOGIN_IN_IS_AUTO, false);
		userInfoSp.putBoolean(USER_REMEMBER_PSW, false);
		userInfoSp.commit();

		UserInfoManager.userInfo.setId("");

		UserInfoManager.userInfo.setUsername("");
		UserInfoManager.userInfo.setPassword("");
		UserInfoManager.userInfo.setRealName("");
		UserInfoManager.userInfo.setCompanyname("");
		UserInfoManager.userInfo.setProvince("");
		UserInfoManager.userInfo.setCity("");

		UserInfoManager.userInfo.setBirthday("");
		UserInfoManager.userInfo.setSex("");
		UserInfoManager.userInfo.setSignature("");
		UserInfoManager.userInfo.setLastLoginTime("");
	}

	/**
	 * 保存是否记住密码到配置文件
	 * 
	 * @param context
	 * @param isRemember
	 *            是否记住密码
	 */
	public static void setRememberPwd(Context context, Boolean isRemember) {

		SharedPreferences.Editor userInfoSp = context.getSharedPreferences(
				USER_INFO_PREFERNCE_KEY, Context.MODE_PRIVATE).edit();

		userInfoSp.putBoolean(USER_REMEMBER_PSW, isRemember);
		userInfoSp.commit();

	}

	/**
	 * 从配置文件获取是否记住密码
	 * 
	 * @param context
	 * @param USER_REMEMBER_PWD
	 *            是否记住密码
	 */
	public static boolean getRememberPwd(Context context) {
		SharedPreferences userInfo = context.getSharedPreferences(
				USER_INFO_PREFERNCE_KEY, Context.MODE_PRIVATE);

		return userInfo.getBoolean(USER_REMEMBER_PSW, false);

	}

	/**
	 * 保存是否自动登录
	 * 
	 * @param context
	 * @param isAutoLoginIn
	 */
	public static void setLoginInAuto(Context context, Boolean isAutoLoginIn) {
		SharedPreferences.Editor userInfo = context.getSharedPreferences(
				USER_INFO_PREFERNCE_KEY, Context.MODE_PRIVATE).edit();

		userInfo.putBoolean(USER_LOGIN_IN_IS_AUTO, isAutoLoginIn);
		userInfo.commit();
	}

	public static boolean getLoginInAuto(Context context) {
		SharedPreferences userInfo = context.getSharedPreferences(
				USER_INFO_PREFERNCE_KEY, Context.MODE_PRIVATE);

		return userInfo.getBoolean(USER_LOGIN_IN_IS_AUTO, false);
	}

	/**
	 * 保存登录状态到配置文件
	 * 
	 * @param context
	 * @param isLoginIn
	 *            是否登录
	 */

	public static void setLoginIn(Context context, Boolean isLoginIn) {

		SharedPreferences.Editor userInfo = context.getSharedPreferences(
				USER_INFO_PREFERNCE_KEY, Context.MODE_PRIVATE).edit();

		userInfo.putBoolean(USER_LOGIN_IN, isLoginIn);
		userInfo.commit();
	}

	/**
	 * 从配置文件获取登录状态
	 * 
	 * @param context
	 * @param isLoginIn
	 *            是否登录
	 */
	public static boolean getLoginIn(Context context) {
		SharedPreferences userInfo = context.getSharedPreferences(
				USER_INFO_PREFERNCE_KEY, Context.MODE_PRIVATE);

		return userInfo.getBoolean(USER_LOGIN_IN, false);

	}

	/**
	 * 保存手势密码
	 * 
	 * @param context
	 * @param pwdStr
	 *            手势密码
	 */
	public static void setGesturuePwd(Context context, String pwdStr) {

		SharedPreferences.Editor userInfo = context.getSharedPreferences(
				USER_INFO_PREFERNCE_KEY, Context.MODE_PRIVATE).edit();

		if (!TextUtils.isEmpty(pwdStr)) {
			userInfo.putBoolean(USER_GESTURUE_PWD_IS_HAS, true);
			userInfo.putString(USER_GESTURUE_PWD, pwdStr);
		} else {
			userInfo.putBoolean(USER_GESTURUE_PWD_IS_HAS, false);
			userInfo.putString(USER_GESTURUE_PWD, "");
		}
		userInfo.commit();

	}

	/**
	 * 加入手势密码关闭功能
	 * 
	 * @param context
	 * @param isClose
	 */
	public static void setOpenOrCloseGesturuePwd(Context context,
			boolean isClose) {

		SharedPreferences.Editor userInfo = context.getSharedPreferences(
				USER_INFO_PREFERNCE_KEY, Context.MODE_PRIVATE).edit();

		userInfo.putBoolean(USER_GESTURUE_PWD_IS_OPEN, isClose);

		userInfo.commit();
	}

	public static boolean getIsOpenGesturuePwd(Context context) {
		SharedPreferences userInfo = context.getSharedPreferences(
				USER_INFO_PREFERNCE_KEY, Context.MODE_PRIVATE);

		return userInfo.getBoolean(USER_GESTURUE_PWD_IS_OPEN, true);
	}

	/**
	 * 从配置文件获取是有手势密码
	 * 
	 * @param context
	 * @param USER_GESTURUE_PSW_IS_HAS
	 *            是否有手势密码
	 */
	public static boolean getIsHasGesturuePwd(Context context) {
		SharedPreferences userInfo = context.getSharedPreferences(
				USER_INFO_PREFERNCE_KEY, Context.MODE_PRIVATE);

		return userInfo.getBoolean(USER_GESTURUE_PWD_IS_HAS, false);

	}

	/**
	 * 从配置文件获取是有手势密码
	 * 
	 * @param context
	 * @param USER_GESTURUE_PSW_IS_HAS
	 *            是否有手势密码
	 */
	public static String getGesturuePwd(Context context) {
		SharedPreferences userInfo = context.getSharedPreferences(
				USER_INFO_PREFERNCE_KEY, Context.MODE_PRIVATE);

		return userInfo.getString(USER_GESTURUE_PWD, "");

	}

}
