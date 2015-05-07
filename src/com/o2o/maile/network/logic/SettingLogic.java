package com.o2o.maile.network.logic;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.o2o.maile.BaseApplication;
import com.o2o.maile.network.config.MsgResult;
import com.o2o.maile.network.config.RequestUrl;
import com.o2o.maile.network.volley.Request.Method;
import com.o2o.maile.network.volley.Response.Listener;
import com.o2o.maile.network.volley.toolbox.JsonObjectRequest;

public class SettingLogic {

	public static final int NET_ERROR = 0;

	public static final int LOCAL_SET_SUC = NET_ERROR + 1;

	public static final int LOCAL_SET_FAIL = LOCAL_SET_SUC + 1;

	public static final int LOCAL_SET_EXCEPTION = LOCAL_SET_FAIL + 1;

	public static final int LOCALRANGE_SET_SUC = LOCAL_SET_EXCEPTION + 1;

	public static final int LOCALRANGE_SET_FAIL = LOCALRANGE_SET_SUC + 1;

	public static final int LOCALRANGE_SET_EXCEPTION = LOCALRANGE_SET_FAIL + 1;

	public static final int CATEGROY_SET_SUC = LOCALRANGE_SET_EXCEPTION + 1;

	public static final int CATEGROY_SET_FAIL = CATEGROY_SET_SUC + 1;

	public static final int CATEGROY_SET_EXCEPTION = CATEGROY_SET_FAIL + 1;

	public static void setLocal(final Context context, final Handler handler,
			final double longitude, final double latitude, final String username) {

		String url = RequestUrl.HOST_URL + RequestUrl.storeSet.setloc;

		JSONObject requestJson = new JSONObject();
		try {
			requestJson.put("username", URLEncoder.encode(username, "UTF-8"));
			requestJson.put("longitude", longitude);
			requestJson.put("latitude", latitude);

			BaseApplication.getInstanceRequestQueue().add(
					new JsonObjectRequest(Method.POST, url, requestJson,
							new Listener<JSONObject>() {
								@Override
								public void onResponse(JSONObject response) {
									if (null != response) {
										parseSetLocalData(response, handler);
									}

								}
							}, null));
			BaseApplication.getInstanceRequestQueue().start();

		} catch (UnsupportedEncodingException e) {
			handler.sendEmptyMessage(LOCAL_SET_EXCEPTION);
		} catch (JSONException e) {
			handler.sendEmptyMessage(LOCAL_SET_EXCEPTION);
		}

	}

	private static void parseSetLocalData(JSONObject response, Handler handler) {
		try {
			String sucResult = response.getString(MsgResult.RESULT_TAG).trim();

			if (!TextUtils.isEmpty(sucResult) && MsgResult.RESULT_FAIL.equals(sucResult)) {
				Message message = new Message();
				message.what = LOCAL_SET_FAIL;
				message.obj = response.getString(MsgResult.RESULT_MSG_TAG).trim();
				handler.sendMessage(message);
			} else {
				handler.sendEmptyMessage(LOCAL_SET_SUC);
			}

		} catch (JSONException e) {
			handler.sendEmptyMessage(LOCAL_SET_EXCEPTION);
		}
	}

	public static void setLocalRange(final Context context,
			final Handler handler, final double serviceRange,
			final String username) {

		String url = RequestUrl.HOST_URL + RequestUrl.storeSet.setServiceRange;

		JSONObject requestJson = new JSONObject();
		try {
			requestJson.put("username", URLEncoder.encode(username, "UTF-8"));
			requestJson.put("serviceRange", serviceRange);

			BaseApplication.getInstanceRequestQueue().add(
					new JsonObjectRequest(Method.POST, url, requestJson,
							new Listener<JSONObject>() {
								@Override
								public void onResponse(JSONObject response) {
									if (null != response) {
										parseSetLocalRangeData(response,
												handler);
									}

								}
							}, null));
			BaseApplication.getInstanceRequestQueue().start();

		} catch (UnsupportedEncodingException e) {
			handler.sendEmptyMessage(LOCALRANGE_SET_EXCEPTION);
		} catch (JSONException e) {
			handler.sendEmptyMessage(LOCALRANGE_SET_EXCEPTION);
		}

	}

	private static void parseSetLocalRangeData(JSONObject response,
			Handler handler) {
		try {
			String sucResult = response.getString(MsgResult.RESULT_TAG).trim();

			if (!TextUtils.isEmpty(sucResult) && MsgResult.RESULT_FAIL.equals(sucResult)) {
				Message message = new Message();
				message.what = LOCALRANGE_SET_FAIL;
				message.obj = response.getString(MsgResult.RESULT_MSG_TAG).trim();
				handler.sendMessage(message);
			} else {
				handler.sendEmptyMessage(LOCALRANGE_SET_SUC);
			}

		} catch (JSONException e) {
			handler.sendEmptyMessage(LOCALRANGE_SET_EXCEPTION);
		}
	}

	public static void setCategroy(final Context context,
			final Handler handler, final ArrayList<String> categroyIDs,
			final String username) {
		String url = RequestUrl.HOST_URL + RequestUrl.storeSet.setGoodsCategory;
		JSONArray jsonArray = new JSONArray(categroyIDs);
		JSONObject requestJson = new JSONObject();
		try {
			requestJson.put("username", URLEncoder.encode(username, "UTF-8"));
			requestJson.put("categoryIDs", jsonArray);
			Log.e("xxx", requestJson.toString());

			BaseApplication.getInstanceRequestQueue().add(
					new JsonObjectRequest(Method.POST, url, requestJson,
							new Listener<JSONObject>() {
								@Override
								public void onResponse(JSONObject response) {
									if (null != response) {
										parseSetCategroyData(response, handler);
									}

								}
							}, null));
			BaseApplication.getInstanceRequestQueue().start();

		} catch (JSONException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

	}

	private static void parseSetCategroyData(JSONObject response,
			Handler handler) {
		try {
			String sucResult = response.getString(MsgResult.RESULT_TAG).trim();

			if (!TextUtils.isEmpty(sucResult) && MsgResult.RESULT_FAIL.equals(sucResult)) {
				Message message = new Message();
				message.what = CATEGROY_SET_FAIL;
				message.obj = response.getString(MsgResult.RESULT_FAIL).trim();
				handler.sendMessage(message);
			} else {
				handler.sendEmptyMessage(CATEGROY_SET_SUC);
			}

		} catch (JSONException e) {
			handler.sendEmptyMessage(CATEGROY_SET_EXCEPTION);
		}
	}

}
