package com.o2o.maile;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;

import com.o2o.maile.network.volley.RequestQueue;
import com.o2o.maile.network.volley.toolbox.Volley;
import com.o2o.maile.service.PushService;

public class BaseApplication extends Application {

	private static Context context;

	private static RequestQueue sQueue;

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public void onCreate() {
		super.onCreate();
		context = getApplicationContext();
		sQueue = Volley.newRequestQueue(getApplicationContext());
		startPushService();
		// ImageLoaderConfig.initImageLoader(this, Constants.BASE_IMAGE_CACHE);
		// PrefUtils.putBoolean(PrefUtils.IS_REFRESHING, false); // init
	}

	private void startPushService() {
		Intent intent = new Intent(getApplicationContext(), PushService.class);
		// intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		getApplicationContext().startService(intent);
		Log.e("xxx_push", "startPushService");
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
	}

	public static RequestQueue getInstanceRequestQueue() {
		if (null == sQueue) {
			sQueue = Volley.newRequestQueue(getContext());
		}
		return sQueue;
	}

	public static Context getContext() {
		return context;
	}

}
