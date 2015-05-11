package com.o2o.maile.ui.activity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

import com.o2o.maile.R;
import com.o2o.maile.entity.Category;
import com.o2o.maile.entity.Goods;
import com.o2o.maile.network.logic.GoodsLogic;
import com.o2o.maile.ui.view.CustomProgressDialog;
import com.o2o.maile.ui.view.treemenu.ExpandTabView;
import com.o2o.maile.ui.view.treemenu.ViewLeft;
import com.o2o.maile.ui.view.treemenu.ViewMiddle;
import com.o2o.maile.ui.view.treemenu.ViewRight;

public class SpecialGoodsActivity extends Activity {

	private ExpandTabView expandTabView;
	private TextView mGoodsNameTv;
	private ArrayList<View> mViewArray = new ArrayList<View>();
	private ViewLeft viewLeft;
	private ViewMiddle viewMiddle;
	private ViewRight viewRight;
	private ArrayList<Category> groups = new ArrayList<Category>();
	// private LinkedList<Category> childrenItem = new LinkedList<Category>();
	private SparseArray<LinkedList<Category>> children = new SparseArray<LinkedList<Category>>();
	private ArrayList<Category> mCategroyList = new ArrayList<Category>();
	private ArrayList<Category> childrenItem = new ArrayList<Category>();

	private ArrayList<Category> mTempCategroyList = new ArrayList<Category>();

	private boolean isAlreadyGetOne = false;
	private Category mNowSelectCategoryOne;
	private Category mNowSelectCategoryTwo;
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
						if (!isAlreadyGetOne) {
							mCategroyList.clear();
							mCategroyList.addAll(mTempCategroyList);
							groups.addAll(mCategroyList);
							isAlreadyGetOne = true;
							GoodsLogic.getCategroyList(
									SpecialGoodsActivity.this, mHandler, groups
											.get(0).getCategoryID());
						} else {
							childrenItem.clear();
							mCategroyList.clear();
							mCategroyList.addAll(mTempCategroyList);
							childrenItem.addAll(mCategroyList);
						}
						viewMiddle.refreshData(groups, childrenItem);
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
		initData();
		initVaule();
		initListener();

	}

	private void initView() {

		expandTabView = (ExpandTabView) findViewById(R.id.expandtab_view);
		mGoodsNameTv = (TextView) findViewById(R.id.sg_title_tv);
		viewLeft = new ViewLeft(this);
		viewMiddle = new ViewMiddle(this);
		viewRight = new ViewRight(this);

	}

	private void initData() {
		String name = getIntent().getExtras().getString("goodsName");
		mGoodsNameTv.setText(name);
		mCustomProgressDialog = new CustomProgressDialog(
				SpecialGoodsActivity.this);
		mCustomProgressDialog.show();
		GoodsLogic.getCategroyList(SpecialGoodsActivity.this, mHandler, "");
	}

	private void initVaule() {

		// mViewArray.add(viewLeft);
		mViewArray.add(viewMiddle);
		// mViewArray.add(viewRight);
		ArrayList<String> mTextArray = new ArrayList<String>();
		// mTextArray.add("距离");
		mTextArray.add("请选择物品类别");
		// mTextArray.add("距离");
		expandTabView.setValue(mTextArray, mViewArray);
		// expandTabView.setTitle(viewLeft.getShowText(), 0);
		// expandTabView.setTitle(viewMiddle.getShowText(), 1);
		// expandTabView.setTitle(viewRight.getShowText(), 2);
		viewMiddle.refreshData(groups, childrenItem);

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
			public void getValue(int position) {
				mNowSelectCategoryTwo = childrenItem.get(position);
				onRefresh(viewMiddle, "position");

			}
		});
		viewMiddle.setOnSelectOneListener(new ViewMiddle.OnSelectOneListener() {

			@Override
			public void getValue(int position) {
				GoodsLogic.getCategroyList(SpecialGoodsActivity.this, mHandler,
						groups.get(position).getCategoryID());
				mNowSelectCategoryOne = groups.get(position);
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
		// if (position >= 0 &&
		// !expandTabView.getTitle(position).equals(showText)) {
		// expandTabView.setTitle("您选择的物品种类是：" + showText, position);
		// }
		if (null == mNowSelectCategoryOne) {
			mNowSelectCategoryOne = groups.get(0);
		}
		Goods goods = new Goods();
		goods.setName(mGoodsNameTv.getText().toString().trim());
		goods.setCategoryIDLevel1(mNowSelectCategoryOne.getCategoryID());
		goods.setCategoryNameLevel1(mNowSelectCategoryOne.getCategoryName());
		goods.setCategoryIDLevel2(mNowSelectCategoryTwo.getCategoryID());
		goods.setCategoryNameLevel2(mNowSelectCategoryTwo.getCategoryName());
		goods.setStandard("false");

		Intent intent = new Intent(SpecialGoodsActivity.this,
				OrderFormActivity.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable(OrderFormActivity.GOODS_KEY, goods);
		intent.putExtras(bundle);
		startActivity(intent);
		SpecialGoodsActivity.this.finish();
		overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
		// Toast.makeText(SpecialGoodsActivity.this, showText,
		// Toast.LENGTH_SHORT)
		// .show();

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
