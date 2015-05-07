package com.o2o.maile.ui.activity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.SparseArray;
import android.view.View;
import android.widget.Toast;

import com.o2o.maile.R;
import com.o2o.maile.entity.Category;
import com.o2o.maile.network.logic.GoodsLogic;
import com.o2o.maile.ui.view.CustomProgressDialog;
import com.o2o.maile.ui.view.treemenu.ExpandTabView;
import com.o2o.maile.ui.view.treemenu.ViewLeft;
import com.o2o.maile.ui.view.treemenu.ViewMiddle;
import com.o2o.maile.ui.view.treemenu.ViewRight;

public class SpecialGoodsActivity extends Activity {

	private ExpandTabView expandTabView;
	private ArrayList<View> mViewArray = new ArrayList<View>();
	private ViewLeft viewLeft;
	private ViewMiddle viewMiddle;
	private ViewRight viewRight;
	private ArrayList<Category> groups = new ArrayList<Category>();
	private LinkedList<Category> childrenItem = new LinkedList<Category>();
	private SparseArray<LinkedList<Category>> children = new SparseArray<LinkedList<Category>>();
	private ArrayList<Category> mCategroyList = new ArrayList<Category>();

	private ArrayList<Category> mTempCategroyList = new ArrayList<Category>();

	private CustomProgressDialog mCustomProgressDialog;

	Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			int what = msg.what;
			switch (what) {
			case GoodsLogic.CATEGROY_LIST_GET_SUC: {
				if (null != msg.obj) {
					mTempCategroyList.clear();
					mTempCategroyList
							.addAll((Collection<? extends Category>) msg.obj);
					if (mTempCategroyList.size() > 0) {
						ArrayList<Category> categroyList = new ArrayList<Category>();
						categroyList.addAll(mTempCategroyList);
						mCategroyList.clear();
						mCategroyList.addAll(mTempCategroyList);
					}
				}
				break;
			}
			case GoodsLogic.CATEGROY_LIST_GET_FAIL: {
				break;
			}
			case GoodsLogic.CATEGROY_LIST_GET_EXCEPTION: {
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
		setContentView(R.layout.special_goods);
		initView();
		initVaule();
		initListener();

	}

	private void initView() {

		expandTabView = (ExpandTabView) findViewById(R.id.expandtab_view);
		viewLeft = new ViewLeft(this);
		viewMiddle = new ViewMiddle(this);
		viewRight = new ViewRight(this);

	}

	private void initVaule() {

		// mViewArray.add(viewLeft);
		mViewArray.add(viewMiddle);
		// mViewArray.add(viewRight);
		ArrayList<String> mTextArray = new ArrayList<String>();
		// mTextArray.add("距离");
		mTextArray.add("区域");
		// mTextArray.add("距离");
		expandTabView.setValue(mTextArray, mViewArray);
		// expandTabView.setTitle(viewLeft.getShowText(), 0);
		// expandTabView.setTitle(viewMiddle.getShowText(), 1);
		// expandTabView.setTitle(viewRight.getShowText(), 2);
		viewMiddle.refreshData(groups, children);

	}

	private void initListener() {

		viewLeft.setOnSelectListener(new ViewLeft.OnSelectListener() {

			@Override
			public void getValue(String distance, String showText) {
				onRefresh(viewLeft, showText);
			}
		});

		viewMiddle.setOnSelectListener(new ViewMiddle.OnSelectListener() {

			@Override
			public void getValue(String showText) {

				onRefresh(viewMiddle, showText);

			}
		});

		viewRight.setOnSelectListener(new ViewRight.OnSelectListener() {

			@Override
			public void getValue(String distance, String showText) {
				onRefresh(viewRight, showText);
			}
		});

	}

	private void onRefresh(View view, String showText) {

		expandTabView.onPressBack();
		int position = getPositon(view);
		if (position >= 0 && !expandTabView.getTitle(position).equals(showText)) {
			expandTabView.setTitle(showText, position);
		}
		Toast.makeText(SpecialGoodsActivity.this, showText, Toast.LENGTH_SHORT)
				.show();

	}

	private int getPositon(View tView) {
		for (int i = 0; i < mViewArray.size(); i++) {
			if (mViewArray.get(i) == tView) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public void onBackPressed() {

		if (!expandTabView.onPressBack()) {
			finish();
		}
	}

}
