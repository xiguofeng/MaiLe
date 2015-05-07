package com.o2o.maile.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.o2o.maile.R;
import com.o2o.maile.entity.OrderInfo;
import com.o2o.maile.ui.view.CustomProgressDialog;
import com.o2o.maile.util.AppManager;

public class OrderDetailActivity extends BaseActivity implements
		OnClickListener {

	public static final String ORDER_KEY = "order_key";

	private TextView mGoodsNameTv;

	private TextView mBuyPriceTv;

	private TextView mBuyNumTv;

	private TextView mBuyAddressTv;

	private TextView mBuyPhoneTv;

	private Context mContext;

	private OrderInfo mOrder;

	private CustomProgressDialog mCustomProgressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_detail);
		mContext = OrderDetailActivity.this;
		AppManager.getInstance().addActivity(OrderDetailActivity.this);
		initView();
		initData();
	}

	private void initView() {

		mGoodsNameTv = (TextView) findViewById(R.id.order_name_tv);
		mBuyPriceTv = (TextView) findViewById(R.id.order_price_tv);
		mBuyNumTv = (TextView) findViewById(R.id.order_num_tv);
		mBuyAddressTv = (TextView) findViewById(R.id.order_address_tv);
		mBuyPhoneTv = (TextView) findViewById(R.id.order_phone_tv);
	}

	// TODO
	// 订单信息缺失
	private void initData() {
		mOrder = (OrderInfo) getIntent().getSerializableExtra(ORDER_KEY);

		mGoodsNameTv.setText(mOrder.getBuyer());
		mBuyPriceTv.setText(mOrder.getBuyerComment());
		mBuyNumTv.setText("" + mOrder.getCreateTime());
		mBuyAddressTv.setText("" + mOrder.getBuyer());
		mBuyPhoneTv.setText(mOrder.getBuyerComment());
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		default:
			break;
		}
	}

}
