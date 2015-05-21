package com.o2o.maile.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.o2o.maile.R;
import com.o2o.maile.entity.Goods;
import com.o2o.maile.util.LocationUtilsV5;
import com.o2o.maile.util.LocationUtilsV5.LocationCallback;

public class HistoryOrderFormActivity extends Activity implements OnClickListener {

	public static final String COMMODITY_KEY = "Commodity_key";

	private TextView mOrderNameTv;

	private EditText mOrderPriceEt;

	private EditText mOrderNumEt;

	private EditText mOrderAddressEt;

	private EditText mOrderPhoneEt;

	private Goods mCommodity;

	private Button mSumbitBtn;

	private Button mGiveUpBtn;

	private BDLocation mLocData = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.order_form);
		initView();
		initData();
	}

	private void initView() {
		// 初始化控件
		mOrderNameTv = (TextView) findViewById(R.id.order_name_tv);
		mOrderPriceEt = (EditText) findViewById(R.id.order_price_et);
		mOrderNumEt = (EditText) findViewById(R.id.order_num_et);
		mOrderAddressEt = (EditText) findViewById(R.id.order_address_et);
		mOrderPhoneEt = (EditText) findViewById(R.id.order_phone_et);
		mSumbitBtn = (Button) findViewById(R.id.order_submit_btn);
		mGiveUpBtn = (Button) findViewById(R.id.order_giveup_btn);

		mSumbitBtn.setOnClickListener(this);
		mGiveUpBtn.setOnClickListener(this);
	}

	private void initData() {
		mCommodity = (Goods) getIntent()
				.getSerializableExtra(COMMODITY_KEY);
		if (null != mCommodity) {
			mOrderNameTv.setText(mCommodity.getName());
			mOrderPriceEt.setText(mCommodity.getName());
			mOrderPhoneEt.setText("15195879956");
			mOrderNumEt.setText("3");
			mOrderAddressEt.setText("南京市江宁区18号");
		}
		getLoc();
	}

	private void submitOrder() {
		Intent intent = new Intent(HistoryOrderFormActivity.this, ResultActivity.class);
		startActivity(intent);
	}

	private void getLoc() {
		LocationUtilsV5.getLocation(getApplicationContext(),
				new LocationCallback() {
					@Override
					public void onGetLocation(BDLocation location) {
						Log.e("xxx_latitude", "" + location.getLatitude());
						Log.e("xxx_longitude", "" + location.getLongitude());

						if (true) {

							// mLocData = location;
							// mLocData.latitude = location.getLatitude();
							// mLocData.longitude = location.getLongitude();
						}
					}
				});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.order_submit_btn: {
			submitOrder();
			break;
		}
		case R.id.order_giveup_btn: {
			finish();
			break;
		}
		default:
			break;
		}
	}
}
