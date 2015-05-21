package com.o2o.maile.ui.activity;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.RatingBar;
import android.widget.TextView;

import com.o2o.maile.R;
import com.o2o.maile.service.PushService;
import com.o2o.maile.util.AppManager;
import com.o2o.maile.util.UserInfoManager;

public class ResultActivity extends BaseActivity implements OnClickListener {

	private TextView mMyOrderTv;

	private TextView mTimeTv;

	private TextView mPhoneTv;

	private TextView mBillNumTv;

	private TextView mSellerNameTv;

	private TextView mMsgTv;

	private RatingBar mSpeedRb;

	private RatingBar mQualityRb;

	private RatingBar mServiceRb;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.result);
		AppManager.getInstance().addActivity(ResultActivity.this);
		initView();
		initData();
	}

	private void initView() {
		mMyOrderTv = (TextView) findViewById(R.id.result_my_order_tv);
		mMyOrderTv.setOnClickListener(this);
		mTimeTv = (TextView) findViewById(R.id.result_time_tv);
		mPhoneTv = (TextView) findViewById(R.id.result_phone_tv);
		mSellerNameTv = (TextView) findViewById(R.id.result_seller_name_tv);
		mMsgTv = (TextView) findViewById(R.id.result_msg_tv);
		mBillNumTv = (TextView) findViewById(R.id.result_bill_count_tv);

		mSpeedRb = (RatingBar) findViewById(R.id.result_speed_rb);
		mQualityRb = (RatingBar) findViewById(R.id.result_quality_rb);
		mServiceRb = (RatingBar) findViewById(R.id.result_service_rb);
	}

	private void initData() {
		if (null != PushService.mPushOrder2Buyer) {
			mTimeTv.setText(""
					+ PushService.mPushOrder2Buyer.getProbablyWaitTime());
			// mTimeTv.setText(TimeUtils.TimeStamp2Date(
			// String.valueOf(System.currentTimeMillis()),
			// TimeUtils.FORMAT_PATTERN_DATE));
			mSellerNameTv.setText(PushService.mPushOrder2Buyer.getSellerName());
			try {
				mMsgTv.setText(URLDecoder.decode(
						PushService.mPushOrder2Buyer.getMessage(), "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			mPhoneTv.setText(PushService.mPushOrder2Buyer.getSellerPhone());
		} else {
			if (null != getIntent() || null != getIntent().getExtras()) {

				Bundle bundle = getIntent().getExtras();
				String orderID = bundle.getString("orderID");
				String orderState = bundle.getString("orderState");
				String sellerID = bundle.getString("sellerID");
				String sellerName = bundle.getString("sellerName");
				String sellerPhone = bundle.getString("sellerPhone");
				String probablyWaitTime = bundle.getString("probablyWaitTime");
				String message = bundle.getString("message");
				try {
					message = URLDecoder.decode(message, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}

				Date date = new Date(System.currentTimeMillis());
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
						"MM-dd HH:mm:ss");
				String timeStr = simpleDateFormat.format(date);
				mTimeTv.setText(timeStr);
				// mTimeTv.setText(TimeUtils.TimeStamp2Date(
				// String.valueOf(System.currentTimeMillis()),
				// TimeUtils.FORMAT_PATTERN_DATE));
				mSellerNameTv.setText(sellerName);
				mMsgTv.setText(message);
				mPhoneTv.setText(sellerPhone);
				// mBillNumTv.setText(message);
			}
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.result_my_order_tv: {
			if (!TextUtils.isEmpty(UserInfoManager.userInfo.getUsername())) {
				Intent intent = new Intent(ResultActivity.this,
						OrderListActivity.class);
				startActivity(intent);
				ResultActivity.this.finish();
				overridePendingTransition(R.anim.push_left_in,
						R.anim.push_left_out);
			} else {
				// TODO
				Intent intent = new Intent(ResultActivity.this,
						LoginActivity.class);
				startActivity(intent);
				ResultActivity.this.finish();
				overridePendingTransition(R.anim.push_left_in,
						R.anim.push_left_out);
			}

			break;
		}
		default:
			break;
		}

	}

}
