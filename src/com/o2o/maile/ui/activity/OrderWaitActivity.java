package com.o2o.maile.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.o2o.maile.R;
import com.o2o.maile.service.PushService;
import com.o2o.maile.ui.view.CustomProgressDialog;

public class OrderWaitActivity extends BaseActivity implements OnClickListener {

	private static final int TIME_UPDATE = 1;

	private TextView mTimingTv;

	private Context mContext;

	private CustomProgressDialog mCustomProgressDialog;

	private ProgressBar mPb;

	private int mTiming = 60;

	private Handler mTimeHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case TIME_UPDATE: {
				if (mTiming > 0) {
					mTiming--;
					mTimeHandler.sendEmptyMessageDelayed(TIME_UPDATE, 1000);
					if (mTiming < 4) {
						mTimingTv.setText(getString(R.string.no_find));
						mTimingTv.setTextColor(getResources().getColor(
								R.color.red));
					} else {
						mTimingTv.setText(getString(R.string.remainder_time)
								+ mTiming + "秒");
					}
					Log.e("xxx_mTiming", "" + mTiming);
				} else {
					OrderWaitActivity.this.finish();
					overridePendingTransition(R.anim.push_right_in,
							R.anim.push_right_out);
				}
				break;
			}
			default:
				break;
			}
		};

	};

	private BroadcastReceiver mNotifyBr = new BroadcastReceiver() {

		public void onReceive(Context paramContext, Intent paramIntent) {
			if (paramIntent.getAction().equals(
					PushService.NOTIFY_ORDER_CHALLENGED)) {
				// TODO
				challengedOrder(paramIntent);
			}
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_wait);
		mContext = OrderWaitActivity.this;
		initView();
		initData();
		initReceiver();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(mNotifyBr);
	}

	private void initView() {
		mTimingTv = (TextView) findViewById(R.id.order_wait_status_message);
		mPb = (ProgressBar) findViewById(R.id.order_wait_pb);
	}

	private void initData() {
		mTimeHandler.sendEmptyMessage(TIME_UPDATE);
	}

	private void initReceiver() {
		IntentFilter localIntentFilter = new IntentFilter();
		localIntentFilter.addAction(PushService.NOTIFY_ORDER_CHALLENGED);
		registerReceiver(mNotifyBr, localIntentFilter);
	}

	private void challengedOrder(Intent intent) {
		Bundle bundle = intent.getExtras();

		// String orderID = bundle.getString("orderID");
		// String orderState = bundle.getString("orderState");
		// String sellerID = bundle.getString("sellerID");
		// String sellerName = bundle.getString("sellerName");
		// String sellerPhone = bundle.getString("sellerPhone");
		// String probablyWaitTime = bundle.getString("probablyWaitTime");
		// String message = bundle.getString("message");

		Intent intent2 = new Intent(OrderWaitActivity.this,
				ResultActivity.class);
		intent2.putExtras(bundle);
		OrderWaitActivity.this.startActivity(intent2);
		OrderWaitActivity.this.finish();
		overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

		// PushOrder2BuyerRequest order = new PushOrder2BuyerRequest();
		// order.setOrderID(orderID);
		// order.setOrderState(orderState);
		// order.setSellerID(sellerID);
		// order.setSellerName(sellerName);
		// order.setSellerPhone(sellerPhone);
		// order.setProbablyWaitTime(Integer.valueOf(probablyWaitTime));
		// order.setMessage(message);
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
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
