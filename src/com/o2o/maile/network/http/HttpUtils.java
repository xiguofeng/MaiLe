
package com.o2o.maile.network.http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class HttpUtils {
    public static final String TAG = "HttpUtils";
    
    public static boolean checkNetWorkInfo(Context context) {
        ConnectivityManager cManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cManager.getActiveNetworkInfo();
        if (info != null && info.isAvailable()) {
            return true;
        } else {
            return false;
        }
    }


    public static String sendHttpRequestByHttpClientGet(String url, ArrayList<NameValuePair> params) {
        return CustomeHttpClient.get(url, params);
    }

    public static String sendHttpRequestByHttpClientPost(String url, JSONObject jsonObject) {
        return CustomeHttpClient.post(url, jsonObject);
    }

  
    public static String getUrl(Context context, String url, ArrayList<NameValuePair> params)
            throws IOException {
        String strResult = "";
        HttpPost request = new HttpPost(url);
        HttpParams httpParameters = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParameters, 5000);
        HttpConnectionParams.setSoTimeout(httpParameters, 5000);
        request.setParams(httpParameters);
        try {
            if (params != null)
                request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        HttpResponse response = new DefaultHttpClient().execute(request);
        if (response.getStatusLine().getStatusCode() == 200) {
            // try {
            strResult = java.net.URLDecoder.decode(EntityUtils.toString(response.getEntity()),
                    "UTF-8");
            Log.d(TAG, url + "  ------> \n" + strResult);
            // } catch (Exception e) {
            // //do nothing
            // }
        }
        return strResult;
    }

    public static String getUrl(String url, ArrayList<NameValuePair> params) {

        if (null != params && params.size() != 0) {
            String paramStr = "";
            for (int i = 0; i < params.size(); i++) {
                String name = params.get(i).getName();
                String value = params.get(i).getValue();
                paramStr += paramStr = "&" + name + "=" + value;
            }

            if (!paramStr.equals("")) {
                paramStr = paramStr.replaceFirst("&", "?");
                url += paramStr;
            }
        }
        return url;
    }
}
