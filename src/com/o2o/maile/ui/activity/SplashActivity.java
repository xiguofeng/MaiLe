package com.o2o.maile.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.o2o.maile.R;

public class SplashActivity extends BaseActivity {

	private final int SPLISH_DISPLAY_LENGTH = 3000; // 延迟3秒启动登陆界面

	private Context mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.splash);
		mContext = SplashActivity.this;

		// 启动activity时不自动弹出软键盘
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
		startActivity(intent);
		SplashActivity.this.finish();
		overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
		// ConnectLogic.connect(mContext, mHandler);
		// // 启动三秒后进度到登陆界面
		// new Handler().postDelayed(new Runnable() {
		//
		// @Override
		// public void run() {
		// Intent startLoginIntent = new Intent(SplashActivity.this,
		// LoginActivity.class);
		// SplashActivity.this.startActivity(startLoginIntent);
		// SplashActivity.this.finish();
		// }
		// }, SPLISH_DISPLAY_LENGTH);

	}
}
