package com.o2o.maile.ui.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.o2o.maile.R;
import com.o2o.maile.entity.Goods;
import com.o2o.maile.ui.adapter.GoodsAdapter;

public class HistoryOrderActivity extends Activity {

	private Context mContext = HistoryOrderActivity.this;

	private ListView mHistoryOrderLv;

	private GoodsAdapter mCommodityAdapter;

	private ArrayList<Goods> mCommodityList = new ArrayList<Goods>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.history_order);
		initView();
		initData();
	}

	private void initView() {
		// 初始化控件
		mHistoryOrderLv = (ListView) findViewById(R.id.history_order_lv);
	}

	private void initData() {
		for (int i = 0; i < 10; i++) {
		
			Goods commodity = new Goods();
			commodity.setName("订单编号" + i);
			commodity.setPrice("" + i);
			commodity.setBrief("订单已完成");
			if(i==1){
				commodity.setBrief("订单未完成：正在送货");
			}
			mCommodityList.add(commodity);
		}
		
		mCommodityAdapter = new GoodsAdapter(mContext, mCommodityList);
		mHistoryOrderLv.setAdapter(mCommodityAdapter);
		mHistoryOrderLv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				Intent intent = new Intent(mContext, HistoryOrderFormActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.push_left_in,
						R.anim.push_left_out);
			}
		});

	}

}
