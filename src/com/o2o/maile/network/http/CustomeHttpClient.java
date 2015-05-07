package com.o2o.maile.network.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import android.util.Log;

public class CustomeHttpClient {

	private static final String CHARSET = HTTP.UTF_8;

	private static HttpClient customHttpClient;

	private CustomeHttpClient() {
	}

	public static synchronized HttpClient getHttpClient() {
		if (null == customHttpClient) {
			HttpParams params = new BasicHttpParams();
			HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setContentCharset(params, CHARSET);
			HttpProtocolParams.setUseExpectContinue(params, true);
			HttpProtocolParams
					.setUserAgent(
							params,
							"Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) "
									+ "AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");
			// 从连接池中取连接的超时时间
			ConnManagerParams.setTimeout(params, 5000);
			// 连接超时
			HttpConnectionParams.setConnectionTimeout(params, 7000);
			// 请求超时
			HttpConnectionParams.setSoTimeout(params, 5000);

			SchemeRegistry schReg = new SchemeRegistry();
			schReg.register(new Scheme("http", PlainSocketFactory
					.getSocketFactory(), 80));
			schReg.register(new Scheme("https", SSLSocketFactory
					.getSocketFactory(), 443));

			// 设置HttpClient支持HTTP和HTTPS两种模式
			ClientConnectionManager conMgr = new ThreadSafeClientConnManager(
					params, schReg);

			customHttpClient = new DefaultHttpClient(conMgr, params);
		}

		return customHttpClient;
	}

	public static String post(String url, JSONObject json) {
		String result = "";
		// 创建POST请求
		try {
			StringEntity JsonEntity = new StringEntity(json.toString(), CHARSET);
			HttpPost request = new HttpPost(url);
			request.setEntity(JsonEntity);
			HttpClient client = getHttpClient();
			HttpResponse response = client.execute(request);
			HttpEntity httpEntity = null;
			Log.e("xxxx", ""+response.getStatusLine().getStatusCode());
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				httpEntity = response.getEntity();

				BufferedReader br = new BufferedReader(new InputStreamReader(
						httpEntity.getContent()));

				StringBuffer resultStr = new StringBuffer();
				String readLine = null;
				while ((readLine = br.readLine()) != null) {
					resultStr.append(readLine);
				}
				result = resultStr.toString();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}

	public static String post(String url, ArrayList<NameValuePair> params) {
		String result = null;
		try {
			HttpPost request = new HttpPost(url);
			request.setEntity(new UrlEncodedFormEntity(params, CHARSET));
			HttpClient client = getHttpClient();
			HttpResponse response = client.execute(request);
			HttpEntity httpEntity = null;
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				httpEntity = response.getEntity();

				BufferedReader br = new BufferedReader(new InputStreamReader(
						httpEntity.getContent()));

				StringBuffer resultStr = new StringBuffer();
				String readLine = null;
				while ((readLine = br.readLine()) != null) {
					resultStr.append(readLine);
				}
				result = resultStr.toString();
			}

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String get(String url, ArrayList<NameValuePair> params) {
		String result = "";
		url = HttpUtils.getUrl(url, params);
		try {

			HttpGet request = new HttpGet(url);
			HttpClient client = getHttpClient();
			HttpResponse response = client.execute(request);
			HttpEntity httpEntity = null;

			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				httpEntity = response.getEntity();

				BufferedReader br = new BufferedReader(new InputStreamReader(
						httpEntity.getContent()));

				StringBuffer resultStr = new StringBuffer();
				String readLine = null;
				while ((readLine = br.readLine()) != null) {
					resultStr.append(readLine);
				}
				result = resultStr.toString();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;

	}
}
