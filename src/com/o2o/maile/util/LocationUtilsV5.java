package com.o2o.maile.util;

import android.content.Context;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.GeofenceClient;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;

public class LocationUtilsV5 {

	public static void getLocation(final Context context,
			final LocationCallback callback) {

		Log.e("xxx_loc", "2");
		LocationClient locationClient = new LocationClient(context);
		MyLocationListener mMyLocationListener = new MyLocationListener(
				callback);
		locationClient.registerLocationListener(mMyLocationListener);

		// 设置定位参数
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);// 设置定位模式
		option.setCoorType("gcj02");// 返回的定位结果是百度经纬度，默认值gcj02
		int span = 1000;
		try {
			// span = Integer.valueOf(frequence.getText().toString());
		} catch (Exception e) {
			// TODO: handle exception
		}
		option.setScanSpan(span);// 设置发起定位请求的间隔时间为1000ms
		option.setIsNeedAddress(true);

		locationClient.setLocOption(option);
		Log.e("xxx_loc", "3");

		locationClient.start();
		// locationClient.requestLocation();

		GeofenceClient mGeofenceClient = new GeofenceClient(context);
	}

	public static interface LocationCallback {

		void onGetLocation(BDLocation location);
	}

	protected static class MyLocationListener implements BDLocationListener {

		protected LocationCallback callback;

		public MyLocationListener(LocationCallback callback) {
			this.callback = callback;
		}

		@Override
		public void onReceiveLocation(BDLocation location) {

			Log.e("xxx_loc", "4");
			// Receive Location
			StringBuffer sb = new StringBuffer(256);
			sb.append("time : ");
			sb.append(location.getTime());
			sb.append("\nerror code : ");
			sb.append(location.getLocType());
			sb.append("\nlatitude : ");
			sb.append(location.getLatitude());
			sb.append("\nlontitude : ");
			sb.append(location.getLongitude());
			sb.append("\nradius : ");
			sb.append(location.getRadius());
			sb.append(location.getAddrStr());
			Log.e("xxx_loc", "5" + location.getAddrStr());
			if (location.getLocType() == BDLocation.TypeGpsLocation) {
				sb.append("\nspeed : ");
				sb.append(location.getSpeed());
				sb.append("\nsatellite : ");
				sb.append(location.getSatelliteNumber());
				sb.append("\ndirection : ");
				sb.append(location.getDirection());
			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());
				sb.append("\noperationers : ");
				sb.append(location.getOperators());
			}
			if (null != callback) {
				callback.onGetLocation(location);
			}
		}
	}

}
