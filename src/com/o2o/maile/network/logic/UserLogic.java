package com.o2o.maile.network.logic;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.o2o.maile.BaseApplication;
import com.o2o.maile.R;
import com.o2o.maile.entity.User;
import com.o2o.maile.network.config.MsgResult;
import com.o2o.maile.network.config.RequestUrl;
import com.o2o.maile.network.http.HttpUtils;
import com.o2o.maile.network.volley.Request.Method;
import com.o2o.maile.network.volley.Response.Listener;
import com.o2o.maile.network.volley.toolbox.JsonObjectRequest;

public class UserLogic {

	public static final int NET_ERROR = 0;

	public static final int REGIS_SUC = NET_ERROR + 1;

	public static final int REGIS_FAIL = REGIS_SUC + 1;

	public static final int REGIS_EXCEPTION = REGIS_FAIL + 1;

	public static final int LOGIN_SUC = REGIS_EXCEPTION + 1;

	public static final int LOGIN_FAIL = LOGIN_SUC + 1;

	public static final int LOGIN_EXCEPTION = LOGIN_FAIL + 1;

	public static final int MODIFY_PWD_SUC = LOGIN_EXCEPTION + 1;

	public static final int MODIFY_PWD_FAIL = MODIFY_PWD_SUC + 1;

	public static final int MODIFY_PWD_EXCEPTION = MODIFY_PWD_FAIL + 1;

	public static void register(final Context context, final Handler handler,
			final User user) {

		String url = RequestUrl.HOST_URL + RequestUrl.account.register;
		try {
			Map<String, String> params = new HashMap<String, String>();
			params.put("username",
					URLEncoder.encode(user.getUsername(), "UTF-8"));
			params.put("password",
					URLEncoder.encode(user.getPassword(), "UTF-8"));
			params.put("email", URLEncoder.encode(user.getEmail(), "UTF-8"));
			params.put("telephone",
					URLEncoder.encode(user.getTelephone(), "UTF-8"));

			BaseApplication.getInstanceRequestQueue().add(
					new JsonObjectRequest(Method.POST, url, new JSONObject(
							params), new Listener<JSONObject>() {
						@Override
						public void onResponse(JSONObject response) {
							if (null != response) {
								parseRegisterData(response, handler);
							} else {
								Message msg = new Message();
								msg.what = REGIS_FAIL;
								msg.obj = context.getResources().getString(R.string.get_data_fail);
								handler.sendMessage(msg);
							}
						}
					}, null));
			BaseApplication.getInstanceRequestQueue().start();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void parseRegisterData(JSONObject response, Handler handler) {
		try {
			String sucResult = response.getString(MsgResult.RESULT_TAG).trim();
			if (sucResult.equals(MsgResult.RESULT_FAIL)) {
				Message msg = new Message();
				msg.what = REGIS_FAIL;
				msg.obj = response.getString(MsgResult.RESULT_MSG_TAG).trim();
				handler.sendMessage(msg);
			} else {
				handler.sendEmptyMessage(REGIS_SUC);
			}
		} catch (JSONException e) {
			e.printStackTrace();
			handler.sendEmptyMessage(REGIS_EXCEPTION);
		}
	}

	public static void login(final Context context, final Handler handler,
			final User user) {

		String url = RequestUrl.HOST_URL + RequestUrl.account.login;

		try {
			Map<String, String> params = new HashMap<String, String>();
			params.put("username",
					URLEncoder.encode(user.getUsername(), "UTF-8"));
			params.put("password",
					URLEncoder.encode(user.getPassword(), "UTF-8"));
			params.put("userType", URLEncoder.encode("buyer", "UTF-8"));

			BaseApplication.getInstanceRequestQueue().add(
					new JsonObjectRequest(Method.POST, url, new JSONObject(
							params), new Listener<JSONObject>() {
						@Override
						public void onResponse(JSONObject response) {
							if (null != response) {
								parseLoginData(response, handler);
							} else {
								handler.sendEmptyMessage(LOGIN_FAIL);
							}
						}
					}, null));
			BaseApplication.getInstanceRequestQueue().start();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

	}

	private static void parseLoginData(JSONObject response, Handler handler) {
		try {
			String sucResult = response.getString(MsgResult.RESULT_TAG).trim();
			if (sucResult.equals(MsgResult.RESULT_FAIL)) {
				handler.sendEmptyMessage(LOGIN_FAIL);
			} else {
				handler.sendEmptyMessage(LOGIN_SUC);
			}
		} catch (JSONException e) {
			handler.sendEmptyMessage(LOGIN_EXCEPTION);
		}
	}

	public static void modifyPwd(final Context context, final Handler handler,
			final String userName, final String oldPwd, final String newPwd) {

		String url = RequestUrl.HOST_URL + RequestUrl.account.modifypwd;

		try {
			Map<String, String> params = new HashMap<String, String>();
			params.put("userName", URLEncoder.encode(userName, "UTF-8"));
			params.put("passWord", URLEncoder.encode(oldPwd, "UTF-8"));
			params.put("newpassword", URLEncoder.encode(newPwd, "UTF-8"));

			BaseApplication.getInstanceRequestQueue().add(
					new JsonObjectRequest(Method.POST, url, new JSONObject(
							params), new Listener<JSONObject>() {
						@Override
						public void onResponse(JSONObject response) {
							if (null != response) {
								handler.sendEmptyMessage(MODIFY_PWD_SUC);
								parseModifyPwdData(response, handler);
							} else {
								handler.sendEmptyMessage(MODIFY_PWD_FAIL);
							}
						}
					}, null));
			BaseApplication.getInstanceRequestQueue().start();

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	private static void parseModifyPwdData(JSONObject response, Handler handler) {
		try {
			String sucResult = response.getString("success").trim();
			if (sucResult.equals("false")) {
				handler.sendEmptyMessage(MODIFY_PWD_FAIL);
			} else {
				handler.sendEmptyMessage(MODIFY_PWD_SUC);
			}
		} catch (JSONException e) {
			e.printStackTrace();
			handler.sendEmptyMessage(MODIFY_PWD_EXCEPTION);
		}
	}

	public static void forgetPwd(final Context context, final Handler handler,
			final String userName) {

		String url = RequestUrl.HOST_URL + RequestUrl.account.modifypwd;

		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("susername", userName));
		// params.add(new BasicNameValuePair("password", oldPwd));
		// params.add(new BasicNameValuePair("password", newPwd));

		url = HttpUtils.getUrl(url, params);

		BaseApplication.getInstanceRequestQueue().add(
				new JsonObjectRequest(Method.GET, url, null,
						new Listener<JSONObject>() {
							@Override
							public void onResponse(JSONObject response) {
								if (null != response) {
									Log.e("xxx_modify", response.toString());
								} else {
									handler.sendEmptyMessage(MODIFY_PWD_FAIL);
								}
							}
						}, null));
		BaseApplication.getInstanceRequestQueue().start();
	}

}
