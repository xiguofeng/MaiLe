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
import com.o2o.maile.entity.OrderClient;
import com.o2o.maile.entity.OrderClient.OrderState;
import com.o2o.maile.entity.OrderInfo;
import com.o2o.maile.network.config.RequestUrl;
import com.o2o.maile.network.http.HttpUtils;
import com.o2o.maile.network.netty.codec.JsonUtil;
import com.o2o.maile.network.netty.codec.JsonUtil.JsonException;
import com.o2o.maile.network.volley.Request.Method;
import com.o2o.maile.network.volley.Response.Listener;
import com.o2o.maile.network.volley.toolbox.JsonObjectRequest;
import com.o2o.maile.util.AppInfoManager;

public class OrderLogic {

	public static final int NET_ERROR = 0;

	public static final int ORDER_CREATE_SUC = NET_ERROR + 1;

	public static final int ORDER_CREATE_FAIL = ORDER_CREATE_SUC + 1;

	public static final int ORDER_CREATE_EXCEPTION = ORDER_CREATE_FAIL + 1;

	public static final int ORDERLIST_GET_SUC = ORDER_CREATE_EXCEPTION + 1;

	public static final int ORDERLIST_GET_FAIL = ORDERLIST_GET_SUC + 1;

	public static final int ORDERLIST_GET_EXCEPTION = ORDERLIST_GET_FAIL + 1;

	public static void createOrder(final Context context,
			final Handler handler, final OrderClient order) {

		String url = RequestUrl.HOST_URL + RequestUrl.order.buyCreateOrder;
		JSONObject requestJson = new JSONObject();
		try {
			requestJson.put("clientID", URLEncoder.encode(
					AppInfoManager.getClinetID(context), "UTF-8"));
			requestJson.put("buyNum", Integer.parseInt(order.getBuyNum()));
			requestJson.put("buyerAddress",
					URLEncoder.encode(order.getBuyAddress(), "UTF-8"));
			requestJson.put("buyerPhone",
					URLEncoder.encode(order.getBuyPhone(), "UTF-8"));
			requestJson.put("buyerName",
					URLEncoder.encode(order.getBuyUserName(), "UTF-8"));
			requestJson.put("totalPrice", Integer.parseInt(order.getBuyPrice())
					* Integer.parseInt(order.getBuyNum()));
			requestJson.put("goodsName",
					URLEncoder.encode(order.getGoodsName(), "UTF-8"));
			requestJson.put("goodsBrief",
					URLEncoder.encode(order.getGoodsBrief(), "UTF-8"));
			requestJson.put("goodsID",
					URLEncoder.encode(order.getGoodsId(), "UTF-8"));
			requestJson
					.put("latitude", Double.parseDouble(order.getLatitude()));
			requestJson.put("longitude",
					Double.parseDouble(order.getLongitude()));

			BaseApplication.getInstanceRequestQueue().add(
					new JsonObjectRequest(Method.POST, url, requestJson,
							new Listener<JSONObject>() {
								@Override
								public void onResponse(JSONObject response) {
									if (null != response) {
										Log.e("xxx_createOrder",
												response.toString());
										parseCreateOrderData(response, handler);
									}

								}
							}, null));
			BaseApplication.getInstanceRequestQueue().start();
		} catch (UnsupportedEncodingException e) {
			handler.sendEmptyMessage(ORDER_CREATE_EXCEPTION);
		} catch (JSONException e) {
			handler.sendEmptyMessage(ORDER_CREATE_EXCEPTION);
		}

	}

	private static void parseCreateOrderData(JSONObject response,
			Handler handler) {
		try {
			String orderID = response.getString("orderID").trim();

			if (!TextUtils.isEmpty(orderID)) {
				Message message = new Message();
				message.what = ORDER_CREATE_SUC;
				message.obj = orderID;
				handler.sendMessage(message);
			} else {
				handler.sendEmptyMessage(ORDER_CREATE_FAIL);
			}

		} catch (JSONException e) {
			handler.sendEmptyMessage(ORDER_CREATE_EXCEPTION);
		}
	}

	// TODO
	public static void createOrder2(final Context context,
			final Handler handler, final OrderClient order) {

		String url = RequestUrl.HOST_URL + RequestUrl.order.buyCreateOrder;
		JSONObject requestJson = new JSONObject();
		try {
			requestJson.put("categoryIDLevel2", URLEncoder.encode(
					AppInfoManager.getClinetID(context), "UTF-8"));
			requestJson.put("categoryIDLevel1", URLEncoder.encode(
					AppInfoManager.getClinetID(context), "UTF-8"));
			requestJson.put("categoryNameLevel1", URLEncoder.encode(
					AppInfoManager.getClinetID(context), "UTF-8"));
			requestJson.put("categoryNameLevel2", URLEncoder.encode(
					AppInfoManager.getClinetID(context), "UTF-8"));
			requestJson.put("goodsPrice", URLEncoder.encode(
					AppInfoManager.getClinetID(context), "UTF-8"));

			requestJson.put("clientID", URLEncoder.encode(
					AppInfoManager.getClinetID(context), "UTF-8"));
			requestJson.put("buyNum", Integer.parseInt(order.getBuyNum()));
			requestJson.put("buyerAddress",
					URLEncoder.encode(order.getBuyAddress(), "UTF-8"));
			requestJson.put("buyerPhone",
					URLEncoder.encode(order.getBuyPhone(), "UTF-8"));
			requestJson.put("buyerName",
					URLEncoder.encode(order.getBuyUserName(), "UTF-8"));
			requestJson.put("totalPrice", Integer.parseInt(order.getBuyPrice())
					* Integer.parseInt(order.getBuyNum()));
			requestJson.put("goodsName",
					URLEncoder.encode(order.getGoodsName(), "UTF-8"));
			requestJson.put("goodsBrief",
					URLEncoder.encode(order.getGoodsBrief(), "UTF-8"));
			requestJson
					.put("latitude", Double.parseDouble(order.getLatitude()));
			requestJson.put("longitude",
					Double.parseDouble(order.getLongitude()));

			BaseApplication.getInstanceRequestQueue().add(
					new JsonObjectRequest(Method.POST, url, requestJson,
							new Listener<JSONObject>() {
								@Override
								public void onResponse(JSONObject response) {
									if (null != response) {
										Log.e("xxx_createOrder",
												response.toString());
										parseCreateOrder2Data(response, handler);
									}

								}
							}, null));
			BaseApplication.getInstanceRequestQueue().start();
		} catch (UnsupportedEncodingException e) {
			handler.sendEmptyMessage(ORDER_CREATE_EXCEPTION);
		} catch (JSONException e) {
			handler.sendEmptyMessage(ORDER_CREATE_EXCEPTION);
		}

	}

	private static void parseCreateOrder2Data(JSONObject response,
			Handler handler) {
		try {
			String orderID = response.getString("orderID").trim();

			if (!TextUtils.isEmpty(orderID)) {
				Message message = new Message();
				message.what = ORDER_CREATE_SUC;
				message.obj = orderID;
				handler.sendMessage(message);
			} else {
				handler.sendEmptyMessage(ORDER_CREATE_FAIL);
			}

		} catch (JSONException e) {
			handler.sendEmptyMessage(ORDER_CREATE_EXCEPTION);
		}
	}

	public static void getMyOrderList(final Context context,
			final Handler handler, final String userName, final int skip,
			final int limit, ArrayList<String> statesList) {
		if (HttpUtils.checkNetWorkInfo(context)) {
			String url = RequestUrl.HOST_URL + RequestUrl.order.queryBuyerOrder;

			JSONObject requestJson = new JSONObject();
			JSONArray statesJsonArray = new JSONArray(statesList);
			try {
				requestJson.put("buyerName",
						URLEncoder.encode(userName, "UTF-8"));
				requestJson.put("skip", skip);
				requestJson.put("limit", limit);
				requestJson.put("states", statesJsonArray);

				Log.e("xxx_OrderLogic", requestJson.toString());
				Log.e("xxx_OrderLogic_url", url);

				BaseApplication.getInstanceRequestQueue().add(
						new JsonObjectRequest(Method.POST, url, requestJson,
								new Listener<JSONObject>() {
									@Override
									public void onResponse(JSONObject response) {
										if (null != response) {
											Log.e("xxx_orderlist",
													response.toString());
											parseMyOrderListData(response,
													handler);
										}

									}
								}, null));
				BaseApplication.getInstanceRequestQueue().start();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} else {
			handler.sendEmptyMessage(NET_ERROR);
		}
	}

	public static void getMyOrderList(final Context context,
			final Handler handler, final String userName, final int skip,
			final int limit, OrderState state) {
		if (HttpUtils.checkNetWorkInfo(context)) {

			String url = RequestUrl.HOST_URL + RequestUrl.order.queryBuyerOrder;

			JSONObject requestJson = new JSONObject();
			try {
				requestJson.put("buyerName",
						URLEncoder.encode(userName, "UTF-8"));
				requestJson.put("skip", skip);
				requestJson.put("limit", limit);
				requestJson.put("states", state);

				BaseApplication.getInstanceRequestQueue().add(
						new JsonObjectRequest(Method.POST, url, requestJson,
								new Listener<JSONObject>() {
									@Override
									public void onResponse(JSONObject response) {
										if (null != response) {
											parseMyOrderListData(response,
													handler);
										}

									}
								}, null));
				BaseApplication.getInstanceRequestQueue().start();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} else {
			handler.sendEmptyMessage(NET_ERROR);
		}
	}

	// List<OrderInfo> orderInfos = new ArrayList<>();

	private static void parseMyOrderListData(JSONObject response,
			Handler handler) {
		try {
			Log.e("xxx_parselist", "parseMyOrderListData1");
			JSONArray orderListArray = response.getJSONArray("orderInfos");
			int size = orderListArray.length();

			ArrayList<OrderInfo> orderInfos = new ArrayList<OrderInfo>();
			JSONObject orderJsonObject = new JSONObject();
			for (int i = 0; i < size; i++) {
				OrderInfo order = new OrderInfo();
				orderJsonObject = orderListArray.getJSONObject(i);
				order = JsonUtil.jsonToBean(orderJsonObject.toString(),
						OrderInfo.class);
				orderInfos.add(order);
			}
			Log.e("xxx_parselist", "ORDERLIST_GET_SUC");
			Message message = new Message();
			message.what = ORDERLIST_GET_SUC;
			message.obj = orderInfos;
			handler.sendMessage(message);

			// String sGoodsName =
			// orderJsonObject.getString("goods").trim();

			// JSONArray goodsNameListArray = orderJsonObject
			// .getJSONArray("goods");
			// int size2 = goodsNameListArray.length();
			// String sGoodsName;
			// for (int j = 0; j < size2; j++) {
			// sGoodsName =
			// }
			// String buyer = orderJsonObject.getString("buyer").trim();
			// String seller = orderJsonObject.getString("seller").trim();
			// String sellerComment = orderJsonObject.getString(
			// "sellerComment").trim();
			// String buyerComment =
			// orderJsonObject.getString("buyerComment")
			// .trim();
		} catch (JSONException e) {
			Log.e("xxx_parselist", "JSONException");
			handler.sendEmptyMessage(ORDERLIST_GET_EXCEPTION);
		} catch (JsonException e) {
			Log.e("xxx_parselist", "JsonException");
			handler.sendEmptyMessage(ORDERLIST_GET_EXCEPTION);
		}
	}
}
