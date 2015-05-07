package com.o2o.maile.util;

import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

public class CacheManager {

	public static final String SEARCH_HISTORY_KEY = "search_history";

	public static ArrayList<String> sSearchHistroyList = new ArrayList<String>();

	public static void addSearchHistroy(Context context,String key) {
		if (sSearchHistroyList.contains(key)) {
			sSearchHistroyList.remove(key);
		}
		sSearchHistroyList.add(0, key);
		saveSearchHistroy(context);
	}

	public static void setSearchHistroy(Context context) {
		SharedPreferences processInfoPreferences = context
				.getSharedPreferences(SEARCH_HISTORY_KEY, Context.MODE_PRIVATE);
		String searchHistroy = processInfoPreferences.getString(
				SEARCH_HISTORY_KEY, "");
		if (!TextUtils.isEmpty(searchHistroy)) {
			sSearchHistroyList.clear();
			if (!searchHistroy.contains(",")) {
				sSearchHistroyList.add(searchHistroy);
			} else {
				String[] strings = searchHistroy.split(",");
				for (String s : strings) {
					sSearchHistroyList.add(s);
				}
			}
		}
	}

	public static void saveSearchHistroy(Context context) {
		StringBuffer info = new StringBuffer();
		for (int i = 0; i < sSearchHistroyList.size(); i++) {
			if (i != sSearchHistroyList.size() - 1) {
				info.append(sSearchHistroyList.get(i)).append(",");
			} else {
				info.append(String.valueOf(sSearchHistroyList.get(i)));
			}
		}
		SharedPreferences.Editor processInfo = context.getSharedPreferences(
				SEARCH_HISTORY_KEY, Context.MODE_PRIVATE).edit();
		processInfo.putString(SEARCH_HISTORY_KEY, info.toString());
		processInfo.commit();

	}

	public static void clearSearchHistoryInfo(Context context) {
		SharedPreferences.Editor processInfo = context.getSharedPreferences(
				SEARCH_HISTORY_KEY, Context.MODE_PRIVATE).edit();
		processInfo.putString(SEARCH_HISTORY_KEY, "");
		processInfo.commit();
		sSearchHistroyList.clear();
	}

}
