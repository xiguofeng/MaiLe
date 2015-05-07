package com.o2o.maile.ui.activity;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.o2o.maile.R;
import com.o2o.maile.entity.Goods;
import com.o2o.maile.network.config.MsgRequest;
import com.o2o.maile.network.logic.GoodsLogic;
import com.o2o.maile.ui.adapter.GoodsAdapter;
import com.o2o.maile.ui.adapter.MySimpleAdapter;
import com.o2o.maile.ui.view.AutoClearEditText;
import com.o2o.maile.ui.view.CustomProgressDialog;
import com.o2o.maile.util.AppManager;
import com.o2o.maile.util.CacheManager;

public class SearchActivity extends BaseActivity implements OnClickListener {

	private AutoClearEditText mSearchGoodsEt;

	private TextView mSearchTagTv;
	private TextView mSearchTv;
	private ListView mSellersLv;

	private ListView mSearchHistroyLv;
	private MySimpleAdapter mSimpleAdapter;

	// 弹出对话框中的控件

	private CustomProgressDialog mCustomProgressDialog;

	private String mSearchKey;

	private ArrayList<Goods> mGoodsList = new ArrayList<Goods>();

	private ArrayList<String> mSearchHistoryList = new ArrayList<String>();
	private GoodsAdapter mGoodsAdapter;

	private final Context mContext = SearchActivity.this;

	// private long mExitTime = 0;

	Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			int what = msg.what;
			switch (what) {
			case GoodsLogic.GOODS_LIST_BY_KEY_GET_SUC: {
				mGoodsList.clear();
				mGoodsList.addAll((ArrayList<Goods>) msg.obj);
				if (mGoodsList.size() > 0) {
					mGoodsAdapter.notifyDataSetChanged();
					mSearchHistroyLv.setVisibility(View.GONE);
					mSellersLv.setVisibility(View.VISIBLE);
					mSearchTagTv.setText(getString(R.string.find_goods));
				} else {
					
					Intent intent = new Intent(SearchActivity.this,
							SpecialGoodsActivity.class);
					startActivity(intent);
					overridePendingTransition(R.anim.push_left_in,
							R.anim.push_left_out);
					// Toast.makeText(mContext, "没有查询到相关商品", Toast.LENGTH_SHORT)
					// .show();
				}
				break;
			}
			case GoodsLogic.GOODS_LIST_BY_KEY_GET_FAIL: {

				break;
			}
			case GoodsLogic.GOODS_LIST_BY_KEY_GET_EXCEPTION: {
				break;
			}
			case GoodsLogic.NET_ERROR: {
				break;
			}
			default:
				break;
			}

			if (null != mCustomProgressDialog) {
				mCustomProgressDialog.dismiss();
			}
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.search);
		AppManager.getInstance().addActivity(SearchActivity.this);
		initView();
		initData();

	}

	@Override
	protected void onResume() {
		super.onResume();
		mSearchHistoryList.clear();
		mSearchHistoryList.addAll(CacheManager.sSearchHistroyList);
		Log.e("xxx_searchHistory", "" + mSearchHistoryList.size());
		mSimpleAdapter.notifyDataSetChanged();
	}

	private void initView() {
		// 初始化控件
		mSearchGoodsEt = (AutoClearEditText) findViewById(R.id.search_et);
		mSearchTv = (TextView) findViewById(R.id.search_tv);
		mSearchTagTv = (TextView) findViewById(R.id.search_tag_tv);
		mSellersLv = (ListView) findViewById(R.id.sellers_lv);
		mSearchHistroyLv = (ListView) findViewById(R.id.search_history_lv);
	}

	private void initData() {
		mSearchTv.setOnClickListener(this);
		mGoodsAdapter = new GoodsAdapter(mContext, mGoodsList);
		mSellersLv.setAdapter(mGoodsAdapter);
		mSellersLv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				Intent intent = new Intent(mContext, OrderFormActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable(OrderFormActivity.GOODS_KEY,
						mGoodsList.get(position));
				intent.putExtras(bundle);
				startActivity(intent);
				overridePendingTransition(R.anim.push_left_in,
						R.anim.push_left_out);

			}
		});

		mSimpleAdapter = new MySimpleAdapter(SearchActivity.this,
				mSearchHistoryList);
		mSearchHistroyLv.setAdapter(mSimpleAdapter);
		mSearchHistroyLv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				if (!TextUtils.isEmpty(mSearchHistoryList.get(position))) {
					mSearchKey = mSearchHistoryList.get(position);
					mSearchGoodsEt.setText(mSearchKey);
					getGoodsData(mSearchKey);
				}
			}
		});
		mSearchHistoryList.clear();
		mSearchHistoryList.addAll(CacheManager.sSearchHistroyList);
		mSimpleAdapter.notifyDataSetChanged();

		if (null != getIntent().getExtras()) {
			mSearchKey = getIntent().getExtras().getString("searchKey");
			if (!TextUtils.isEmpty(mSearchKey)) {
				mSearchGoodsEt.setText(mSearchKey);
				getGoodsData(mSearchKey);
			}
		}
	}

	private void getGoodsData(String keyword) {
		mCustomProgressDialog = new CustomProgressDialog(mContext);
		mCustomProgressDialog.show();
		CacheManager.addSearchHistroy(getApplicationContext(), mSearchKey);
		GoodsLogic.getGoodsByKey(mContext, mHandler, keyword,
				MsgRequest.GOODS_PAGE_SIZE);

		// mGoodsList.clear();
		// for (int i = 0; i < 10; i++) {
		// Goods goods = new Goods();
		// goods.setName("可乐" + i);
		// goods.setPrice("" + i);
		// goods.setBrief("可口可乐出品" + i);
		// mGoodsList.add(goods);
		// }
		// mGoodsAdapter.notifyDataSetChanged();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.search_tv: {
			mSearchKey = mSearchGoodsEt.getText().toString().trim();
			if ("".equals(mSearchKey)) {
				Toast.makeText(
						mContext,
						mContext.getResources()
								.getString(R.string.search_thing),
						Toast.LENGTH_SHORT).show();
			} else {
				getGoodsData(mSearchKey);
			}
			break;
		}
		default:
			break;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			// if ((System.currentTimeMillis() - mExitTime) > 2000) {
			// Toast.makeText(getApplicationContext(), R.string.exit,
			// Toast.LENGTH_SHORT).show();
			// mExitTime = System.currentTimeMillis();
			// } else {
			// finish();
			// }
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
