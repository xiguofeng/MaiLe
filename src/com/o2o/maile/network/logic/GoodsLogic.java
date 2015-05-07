package com.o2o.maile.network.logic;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.o2o.maile.BaseApplication;
import com.o2o.maile.entity.Category;
import com.o2o.maile.entity.Goods;
import com.o2o.maile.network.config.MsgResult;
import com.o2o.maile.network.config.RequestUrl;
import com.o2o.maile.network.http.HttpUtils;
import com.o2o.maile.network.volley.Request.Method;
import com.o2o.maile.network.volley.Response.Listener;
import com.o2o.maile.network.volley.toolbox.JsonObjectRequest;

public class GoodsLogic {

	public static final int NET_ERROR = 0;

	public static final int CATEGROY_LIST_GET_SUC = NET_ERROR + 1;

	public static final int CATEGROY_LIST_GET_FAIL = CATEGROY_LIST_GET_SUC + 1;

	public static final int CATEGROY_LIST_GET_EXCEPTION = CATEGROY_LIST_GET_FAIL + 1;

	public static final int GOODS_LIST_GET_SUC = CATEGROY_LIST_GET_EXCEPTION + 1;

	public static final int GOODS_LIST_GET_FAIL = GOODS_LIST_GET_SUC + 1;

	public static final int GOODS_LIST_GET_EXCEPTION = GOODS_LIST_GET_FAIL + 1;

	public static final int GOODS_LIST_BY_KEY_GET_SUC = GOODS_LIST_GET_EXCEPTION + 1;

	public static final int GOODS_LIST_BY_KEY_GET_FAIL = GOODS_LIST_BY_KEY_GET_SUC + 1;

	public static final int GOODS_LIST_BY_KEY_GET_EXCEPTION = GOODS_LIST_BY_KEY_GET_FAIL + 1;

	public static void getCategroyList(final Context context,
			final Handler handler, final String categoryID) {

		String url = RequestUrl.HOST_URL + RequestUrl.goods.queryGoodsCategory;
		Map<String, String> params = new HashMap<String, String>();
		params.put("categoryID", categoryID);

		BaseApplication.getInstanceRequestQueue().add(
				new JsonObjectRequest(Method.POST, url, new JSONObject(params),
						new Listener<JSONObject>() {
							@Override
							public void onResponse(JSONObject response) {
								if (null != response) {
									Log.e("xxx_categroy", response.toString());
									parseCategroyListData(response, handler);
								}
							}
						}, null));
		BaseApplication.getInstanceRequestQueue().start();

	}

	private static void parseCategroyListData(JSONObject response,
			Handler handler) {
		try {
			ArrayList<Category> mTempGoodsCategoryList = new ArrayList<Category>();
			JSONArray goodsCategoryListArray = response
					.getJSONArray("goodsCategorys");

			int size = goodsCategoryListArray.length();
			JSONObject ggnewsListJsonObject = new JSONObject();
			for (int i = 0; i < size; i++) {
				ggnewsListJsonObject = goodsCategoryListArray.getJSONObject(i);

				Category category = new Category();
				String categoryID = ggnewsListJsonObject
						.getString("categoryID").trim();
				String categoryName = ggnewsListJsonObject.getString(
						"categoryName").trim();

				category.setCategoryID(categoryID);
				category.setCategoryName(categoryName);
				mTempGoodsCategoryList.add(category);
			}

			Message message = new Message();
			message.what = CATEGROY_LIST_GET_SUC;
			message.obj = mTempGoodsCategoryList;
			handler.sendMessage(message);

		} catch (JSONException e) {
			handler.sendEmptyMessage(CATEGROY_LIST_GET_EXCEPTION);
		}
	}

	public static void getGoodsByCategroyId(final Context context,
			final Handler handler, final String id) {
		if (HttpUtils.checkNetWorkInfo(context)) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					String url = RequestUrl.HOST_URL
							+ RequestUrl.goods.queryGoodsByCategory;

					ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
					params.add(new BasicNameValuePair("articleid", id));
					url = HttpUtils.getUrl(url, params);

					BaseApplication.getInstanceRequestQueue().add(
							new JsonObjectRequest(Method.GET, url, null,
									new Listener<JSONObject>() {
										@Override
										public void onResponse(
												JSONObject response) {
											if (null != response) {
												parseGoodsListData(response,
														handler);
											} else {
												handler.sendEmptyMessage(GOODS_LIST_GET_SUC);
											}

										}
									}, null));
					BaseApplication.getInstanceRequestQueue().start();
				}

			}).start();
		} else {
			handler.sendEmptyMessage(NET_ERROR);
		}
	}

	private static void parseGoodsListData(JSONObject response, Handler handler) {
		try {
			JSONArray goodsListArray = response
					.getJSONArray(MsgResult.RESULT_DATA_TAG);
			JSONObject goodsJsonObject = new JSONObject();
			int size = goodsListArray.length();
			Goods goods = new Goods();
			for (int i = 0; i < size; i++) {
				goodsJsonObject = goodsListArray.getJSONObject(i);

				String sGoodsId = goodsJsonObject.getString("goodsId").trim();
				String sGoodsName = goodsJsonObject.getString("goodsName")
						.trim();

				goods.setId(sGoodsId);
				goods.setName(sGoodsName);
			}

			Message message = new Message();
			message.what = GOODS_LIST_GET_SUC;
			message.obj = goods;
			handler.sendMessage(message);

		} catch (JSONException e) {
			handler.sendEmptyMessage(GOODS_LIST_GET_EXCEPTION);
		}
	}

	public static void getGoodsByKey(final Context context,
			final Handler handler, final String keyword, final int limit) {

		String url = RequestUrl.HOST_URL + RequestUrl.goods.queryGoodsByKey;

		JSONObject requestJson = new JSONObject();
		try {
			requestJson.put("keyword", URLEncoder.encode(keyword, "UTF-8"));
			requestJson.put("limit", limit);

			BaseApplication.getInstanceRequestQueue().add(
					new JsonObjectRequest(Method.POST, url, requestJson,
							new Listener<JSONObject>() {
								@Override
								public void onResponse(JSONObject response) {

									if (null != response) {
										Log.e("xxx_goods_bykey_3",
												response.toString());
										parseGoodsListByKeyData(response,
												handler);
									} else {
										handler.sendEmptyMessage(GOODS_LIST_BY_KEY_GET_FAIL);
									}
								}
							}, null));
			BaseApplication.getInstanceRequestQueue().start();
		} catch (UnsupportedEncodingException  e) {
			handler.sendEmptyMessage(GOODS_LIST_BY_KEY_GET_EXCEPTION);
		} catch (JSONException e) {
			handler.sendEmptyMessage(GOODS_LIST_BY_KEY_GET_EXCEPTION);
		}

	}

	private static void parseGoodsListByKeyData(JSONObject response,
			Handler handler) {
		try {
			ArrayList<Goods> mTempGoodsList = new ArrayList<Goods>();
			JSONArray goodsListArray = response.getJSONArray("goodsList");

			int size = goodsListArray.length();
			JSONObject goodsJsonObject = new JSONObject();
			for (int i = 0; i < size; i++) {
				goodsJsonObject = goodsListArray.getJSONObject(i);

				Goods goods = new Goods();
				String goodsID = goodsJsonObject.getString("goodsID").trim();
				String goodsName = goodsJsonObject.getString("goodsName")
						.trim();
				String goodsBrief = goodsJsonObject.getString("goodsBrief")
						.trim();
				Double goodsPrice = goodsJsonObject.getDouble("price");

				goods.setId(goodsID);
				goods.setName(goodsName);
				goods.setBrief(goodsBrief);
				goods.setPrice(String.valueOf(goodsPrice));
				mTempGoodsList.add(goods);
			}

			Message message = new Message();
			message.what = GOODS_LIST_BY_KEY_GET_SUC;
			message.obj = mTempGoodsList;
			handler.sendMessage(message);

		} catch (JSONException e) {
			handler.sendEmptyMessage(GOODS_LIST_BY_KEY_GET_EXCEPTION);
		}
	}

}
