package com.o2o.maile.ui.activity;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.o2o.maile.R;
import com.o2o.maile.ui.adapter.MySimpleAdapter;
import com.o2o.maile.util.AppManager;
import com.o2o.maile.util.CacheManager;
import com.o2o.maile.util.UserInfoManager;

@SuppressLint("NewApi")
public class MainActivity extends BaseActivity implements OnClickListener {

	private LinearLayout firstBg;

	private RelativeLayout mSearchRl;

	private TextView mMyOrderTv;

	private ListView mSearchHistroyLv;

	private float y;

	private long mExitTime = 0;

	private ArrayList<String> mSearchHistoryList = new ArrayList<String>();

	private MySimpleAdapter mSimpleAdapter;

	private String mSearchKey;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		setUpView();
		initData();
		AppManager.getInstance().addActivity(MainActivity.this);
		// startPushService();
	}

	@Override
	protected void onResume() {
		super.onResume();
		mSearchHistoryList.clear();
		mSearchHistoryList.addAll(CacheManager.sSearchHistroyList);
		Log.e("xxx_searchHistory", "" + mSearchHistoryList.size());
		mSimpleAdapter.notifyDataSetChanged();
	}

	private void setUpView() {
		mSearchHistroyLv = (ListView) findViewById(R.id.main_search_history_lv);
		mMyOrderTv = (TextView) findViewById(R.id.main_my_order_tv);
		mMyOrderTv.setOnClickListener(this);
		firstBg = (LinearLayout) findViewById(R.id.activitmain_bg);
		mSearchRl = (RelativeLayout) findViewById(R.id.main_search_rl);
		mSearchRl.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				y = mSearchRl.getY();
				TranslateAnimation animation = new TranslateAnimation(0, 0, 0,
						-y);
				animation.setDuration(500);
				animation.setFillAfter(true);
				animation.setAnimationListener(new AnimationListener() {
					@Override
					public void onAnimationRepeat(Animation animation) {
					}

					@Override
					public void onAnimationStart(Animation animation) {
					}

					@Override
					public void onAnimationEnd(Animation animation) {
						Intent intent = new Intent(MainActivity.this,
								SearchActivity.class);
						intent.putExtra("searchKey", mSearchKey);
						startActivityForResult(intent, 500);
						overridePendingTransition(R.anim.animationb,
								R.anim.animationa);
					}
				});
				firstBg.startAnimation(animation);
			}
		});
	}

	private void initData() {
		mSimpleAdapter = new MySimpleAdapter(MainActivity.this,
				mSearchHistoryList);
		mSearchHistroyLv.setAdapter(mSimpleAdapter);

		mSearchHistroyLv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				if (!TextUtils.isEmpty(mSearchHistoryList.get(position))) {
					mSearchKey = mSearchHistoryList.get(position);
					Intent intent = new Intent(MainActivity.this,
							SearchActivity.class);
					intent.putExtra("searchKey", mSearchKey);
					startActivityForResult(intent, 500);
					overridePendingTransition(R.anim.animationb,
							R.anim.animationa);
				}
			}
		});

		CacheManager.setSearchHistroy(getApplicationContext());
		mSearchHistoryList.addAll(CacheManager.sSearchHistroyList);
		mSimpleAdapter.notifyDataSetChanged();

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		TranslateAnimation animation = new TranslateAnimation(0, 0, -y, 0);
		animation.setDuration(500);
		animation.setFillAfter(true);
		firstBg.startAnimation(animation);
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.main_my_order_tv: {
			if (!TextUtils.isEmpty(UserInfoManager.userInfo.getUsername())) {
				Intent intent = new Intent(MainActivity.this,
						OrderListActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.push_left_in,
						R.anim.push_left_out);
			} else {
				// AppManager.getInstance().addActivity(MainActivity.this);
				Intent intent = new Intent(MainActivity.this,
						LoginActivity.class);
				intent.setAction(LoginActivity.ORIGIN_FROM_ORDER_KEY);
				startActivity(intent);
				MainActivity.this.finish();
				overridePendingTransition(R.anim.push_left_in,
						R.anim.push_left_out);
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
			if ((System.currentTimeMillis() - mExitTime) > 2000) {
				Toast.makeText(getApplicationContext(), R.string.exit,
						Toast.LENGTH_SHORT).show();
				mExitTime = System.currentTimeMillis();
			} else {
				if (!UserInfoManager.getRememberPwd(getApplicationContext())) {
					UserInfoManager.clearUserInfo(getApplicationContext());
				}
				CacheManager.saveSearchHistroy(getApplicationContext());
				finish();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
