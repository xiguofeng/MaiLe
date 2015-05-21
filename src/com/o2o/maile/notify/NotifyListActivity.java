package com.o2o.maile.notify;

import java.util.ArrayList;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.o2o.maile.R;
import com.o2o.maile.entity.NotifyInfo;
import com.o2o.maile.service.NotifyService;

public class NotifyListActivity extends Activity implements OnClickListener {

	private Context mContext;

	private ImageView mBackIv;

	private ImageView mClearNotifyIv;

	private LinearLayout mNotifyViewLl;

	private ArrayList<NotifyInfo> mNotifyInfoList = new ArrayList<NotifyInfo>();

	private ArrayList<NotifyInfo> mNotifyQueueList = new ArrayList<NotifyInfo>();

	private BroadcastReceiver mNotifyBr = new BroadcastReceiver() {

		public void onReceive(Context paramContext, Intent paramIntent) {

			if (paramIntent.getAction()
					.equals(NotifyService.NOTIFY_DATA_UPDATE)) {
				initData();
			}
		}

	};

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);

		mContext = NotifyListActivity.this;
		setContentView(R.layout.notify);
		initView();
		initData();
		IntentFilter localIntentFilter = new IntentFilter();
		localIntentFilter.addAction(NotifyService.NOTIFY_DATA_UPDATE);
		registerReceiver(mNotifyBr, localIntentFilter);
	}

	@Override
	protected void onResume() {
		super.onResume();
		initData();
	}

	private void initView() {
		// mBackIv = (ImageView) findViewById(R.id.notify_back_iv);
		// mBackIv.setOnClickListener(this);
		// mClearNotifyIv = (ImageView) findViewById(R.id.notify_clear_iv);
		// mClearNotifyIv.setOnClickListener(this);
		mNotifyViewLl = (LinearLayout) findViewById(R.id.notify_ll);
	}

	private void initData() {
		mNotifyViewLl.removeAllViews();
		mNotifyInfoList.clear();
		boolean isHasQueueType = false;
		for (Integer key : NotifyService.sNotifyHistory.keySet()) {
			NotifyInfo notifyInfo = NotifyService.sNotifyHistory.get(key);
			// if (NotifyType.Quene.equals(notifyInfo.getNotifyType())) {
			// if (!isHasQueueType) {
			// mNotifyInfoList.add(notifyInfo);
			// isHasQueueType = true;
			// } else {
			// for (int i = 0; i < mNotifyInfoList.size(); i++) {
			// if
			// (NotifyType.Quene.equals(mNotifyInfoList.get(i).getNotifyType()))
			// {
			// mNotifyInfoList.set(i, notifyInfo);
			// }
			// }
			// }
			//
			// } else {
			//
			// }
			mNotifyInfoList.add(notifyInfo);
		}

		for (NotifyInfo notifyInfo : mNotifyInfoList) {
			NotifyCommonView itemView = new NotifyCommonView(mContext,
					notifyInfo);
			mNotifyViewLl.addView(itemView);
		}
		// for (int i = 0; i < 4; i++) {
		// NotifyInfo notifyInfo = new NotifyInfo();
		// notifyInfo.setContent("4");
		// if (i == 0) {
		// notifyInfo.setNotifyType(NotifyType.AddFood);
		// } else if (i == 1) {
		// notifyInfo.setNotifyType(NotifyType.Comment);
		// } else if (i == 2) {
		// notifyInfo.setNotifyType(NotifyType.Desk);
		// } else {
		// notifyInfo.setNotifyType(NotifyType.Quene);
		// }
		// notifyInfo.setTitle("title");
		// NotifyCommonView itemView = new NotifyCommonView(mContext,
		// notifyInfo);
		// // itemView.setId(i);
		// // itemView.setPadding(0, 10, 0, 0);
		// // itemView.setOnClickListener(new OnClickListener() {
		// //
		// // @Override
		// // public void onClick(View v) {
		// // Intent intent = new Intent(NotifyListActivity.this,
		// // PublishCommentActivity.class);
		// // startActivity(intent);
		// // overridePendingTransition(R.anim.push_left_in,
		// // R.anim.push_left_out);
		// // }
		// // });
		// mNotifyViewLl.addView(itemView);
		// }
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(mNotifyBr);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		default:
			break;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
			overridePendingTransition(R.anim.push_right_in,
					R.anim.push_right_out);
			return true;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

}
