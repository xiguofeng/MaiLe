package com.o2o.maile.ui.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.o2o.maile.R;
import com.o2o.maile.service.PushService;

public class SplashActivity extends BaseActivity {

	private final int SPLISH_DISPLAY_LENGTH = 2000; // 延迟3秒启动登陆界面

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

		// 启动三秒后进度到登陆界面
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				handleMsg();
			}
		}, SPLISH_DISPLAY_LENGTH);

	}

	private void handleMsg() {
		while (-1 == PushService.isUngradeFlag) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if (1 == PushService.isUngradeFlag) {
			alertUpgradeInfo();
		} else {
			jump();
		}
	}

	private void jump() {
		Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
		startActivity(intent);
		SplashActivity.this.finish();
		overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	}

	protected void alertUpgradeInfo() {
		showAlertDialog("版本升级", "已有新版本", "升级",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent();
						intent.setAction(Intent.ACTION_VIEW);
						intent.setData(Uri.parse(PushService.sDownUrl));
						startActivity(intent);
						// Intent intent = new Intent(Intent.ACTION_VIEW, Uri
						// .parse(mDownUrl));
					}
				}, "取消", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						jump();

					}
				});
	}

	protected void showAlertDialog(String title, String message,
			String positiveText,
			DialogInterface.OnClickListener onPositiveClickListener,
			String negativeText,
			DialogInterface.OnClickListener onNegativeClickListener) {
		new AlertDialog.Builder(this).setTitle(title).setMessage(message)
				.setPositiveButton(positiveText, onPositiveClickListener)
				.setNegativeButton(negativeText, onNegativeClickListener)
				.show();
	}
}
