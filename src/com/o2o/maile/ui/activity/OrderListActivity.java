package com.o2o.maile.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.o2o.maile.R;
import com.o2o.maile.ui.fragment.OrderAgreedFragment;
import com.o2o.maile.ui.fragment.OrderAllFragment;
import com.o2o.maile.ui.fragment.OrderHasCreateFragment;
import com.o2o.maile.ui.fragment.OrderNotAgreedFragment;
import com.o2o.maile.ui.fragment.OrderWaitReceivingFragment;
import com.o2o.maile.ui.view.pageindicator.TabPageIndicator;

public class OrderListActivity extends FragmentActivity implements
		OnClickListener {

	private static final String[] ORDERLISTTYPETITLE = new String[] { "1", "2",
			"3", "4", "5" };

	private ImageView mBackIv;

	private ViewPager mPager;// 页卡内容

	private TabPageIndicator mIndicator;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_list);
		findViewById();
		initView();
	}

	private void findViewById() {
		mPager = (ViewPager) findViewById(R.id.orderlist_vPager);
		// mBackIv = (ImageView) findViewById(R.id.trans_query_back_iv);
		mIndicator = (TabPageIndicator) findViewById(R.id.orderlist_indicator);
	}

	private void initView() {
		InitViewPager();
	}

	/**
	 * 初始化ViewPager
	 */
	private void InitViewPager() {
		FragmentPagerAdapter adapter = new TabAdapter(
				getSupportFragmentManager());
		mPager.setAdapter(adapter);
		mIndicator.setViewPager(mPager);
	}

	class TabAdapter extends FragmentPagerAdapter {
		public TabAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {

			Fragment nowFragment = new OrderAllFragment();
			switch (position) {
			case 0: {
				nowFragment = new OrderAllFragment();
				break;
			}
			case 1: {
				nowFragment = new OrderHasCreateFragment();
				break;
			}
			case 2: {
				nowFragment = new OrderWaitReceivingFragment();
				break;
			}
			case 3: {
				nowFragment = new OrderAgreedFragment();
				break;
			}
			case 4: {
				nowFragment = new OrderNotAgreedFragment();
				break;
			}
			default:
				break;
			}

			return nowFragment;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			String title = getResources().getString(R.string.unfinished_order);
			switch (position % ORDERLISTTYPETITLE.length) {
			case 0: {
				title = getResources().getString(R.string.order_all);
				break;
			}
			case 1: {
				title = getResources().getString(R.string.order_create);
				break;
			}
			case 2: {
				title = getResources().getString(R.string.order_wait_receiving);
				break;
			}
			case 3: {
				title = getResources().getString(R.string.order_agreed);
				break;
			}
			case 4: {
				title = getResources().getString(R.string.order_not_agreed);
				break;
			}
			}
			return title;
		}

		@Override
		public int getCount() {
			return ORDERLISTTYPETITLE.length;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// case R.id.trans_query_back_iv: {
		// finish();
		// overridePendingTransition(R.anim.push_right_in,
		// R.anim.push_right_out);
		// break;
		// }
		default:
			break;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {

			finish();
			overridePendingTransition(R.anim.push_right_in,
					R.anim.push_right_out);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
