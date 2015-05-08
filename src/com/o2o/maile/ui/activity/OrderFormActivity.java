package com.o2o.maile.ui.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.o2o.maile.R;
import com.o2o.maile.entity.Goods;
import com.o2o.maile.entity.OrderClient;
import com.o2o.maile.network.logic.OrderLogic;
import com.o2o.maile.ui.view.CustomProgressDialog;
import com.o2o.maile.util.AppManager;
import com.o2o.maile.util.LocationUtilsV5;
import com.o2o.maile.util.LocationUtilsV5.LocationCallback;
import com.o2o.maile.util.UserInfoManager;

public class OrderFormActivity extends BaseActivity implements OnClickListener {

	public static final String GOODS_KEY = "goods_key";

	private TextView mOrderNameTv;

	private EditText mOrderPriceEt;

	private EditText mOrderNumEt;

	private EditText mOrderAddressEt;

	private EditText mOrderPhoneEt;

	private Goods mGoods;

	private Button mSumbitBtn;

	private Button mGiveUpBtn;

	private BDLocation mLocData = null;

	private CustomProgressDialog mCustomProgressDialog;

	private Context mContext;

	private String mLatitude;

	private String mLongitude;

	Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			int what = msg.what;
			switch (what) {
			case OrderLogic.ORDER_CREATE_SUC: {
				alertInfo();
				// Intent intent = new Intent(OrderFormActivity.this,
				// OrderWaitActivity.class);
				// startActivity(intent);
				// OrderFormActivity.this.finish();
				// overridePendingTransition(R.anim.push_left_in,
				// R.anim.push_left_out);
				break;
			}
			case OrderLogic.ORDER_CREATE_FAIL: {

				break;
			}
			case OrderLogic.ORDER_CREATE_EXCEPTION: {
				break;
			}
			case OrderLogic.NET_ERROR: {
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
		setContentView(R.layout.order_form);
		mContext = OrderFormActivity.this;
		AppManager.getInstance().addActivity(OrderFormActivity.this);
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
		mGoods = (Goods) getIntent().getSerializableExtra(GOODS_KEY);
		if (null != mGoods) {
			mOrderNameTv.setText(mGoods.getName());
			mOrderPriceEt.setHint(getString(R.string.price_refer)
					+ mGoods.getPrice());
			mOrderPhoneEt.setHint(getString(R.string.phone_hint));
			mOrderNumEt.setHint(getString(R.string.num_hint));
			mOrderAddressEt.setHint(getString(R.string.loc_wait));
		}
		getLoc();
	}

	private void submitOrder() {

		String buyerNum = mOrderNumEt.getText().toString().trim();
		String buyerAddress = mOrderAddressEt.getText().toString().trim();
		String buyerPhone = mOrderPhoneEt.getText().toString().trim();
		String buyerPrice = mOrderPriceEt.getText().toString().trim();
		String buyerName;
		if (!TextUtils.isEmpty(UserInfoManager.userInfo.getUsername())) {
			buyerName = UserInfoManager.userInfo.getUsername();
		} else {
			buyerName = "游客";
		}
		String goodsName = mOrderNameTv.getText().toString().trim();
		String goodsBrief = mOrderNameTv.getText().toString().trim();
		String goodsID = mGoods.getId();

		if (!TextUtils.isEmpty(buyerNum) && !TextUtils.isEmpty(buyerAddress)
				&& !TextUtils.isEmpty(buyerPhone)
				&& !TextUtils.isEmpty(buyerPrice)
				&& !TextUtils.isEmpty(goodsName)
				&& !TextUtils.isEmpty(mLatitude)
				&& !TextUtils.isEmpty(mLongitude)) {

			mCustomProgressDialog = new CustomProgressDialog(mContext);
			mCustomProgressDialog.show();
			OrderClient order = new OrderClient();
			order.setBuyNum(buyerNum);
			order.setBuyAddress(buyerAddress);
			order.setBuyPhone(buyerPhone);
			order.setBuyPrice(buyerPrice);
			order.setBuyUserName(buyerName);
			order.setGoodsName(goodsName);
			order.setGoodsBrief(goodsBrief);
			order.setGoodsId(goodsID);
			order.setLatitude(mLatitude);
			order.setLongitude(mLongitude);

			if (!mGoods.getStandard().equals("true")) {
				order.setCategoryIDLevel1(mGoods.getCategoryIDLevel1());
				order.setCategoryIDLevel2(mGoods.getCategoryIDLevel2());
				order.setCategoryNameLevel1(mGoods.getCategoryNameLevel1());
				order.setCategoryNameLevel2(mGoods.getCategoryNameLevel2());
				OrderLogic.createOrder2(mContext, mHandler, order);
			} else {
				OrderLogic.createOrder(mContext, mHandler, order);

			}

		} else {
			Toast.makeText(mContext, R.string.order_write_please,
					Toast.LENGTH_SHORT).show();
		}

	}

	private void getLoc() {
		Log.e("xxx_loc", "1");
		LocationUtilsV5.getLocation(getApplicationContext(),
				new LocationCallback() {
					@Override
					public void onGetLocation(BDLocation location) {
						Log.e("xxx_latitude", "" + location.getLatitude());
						Log.e("xxx_longitude", "" + location.getLongitude());
						Log.e("xxx_longitude", "" + location.getAddrStr());
						mLatitude = String.valueOf(location.getLatitude());
						mLongitude = String.valueOf(location.getLongitude());
						String address = location.getAddrStr()
								+ mContext.getString(R.string.nearby);
						mOrderAddressEt.setText(address);
						// if (true) {
						//
						// // mLocData = location;
						// // mLocData.latitude = location.getLatitude();
						// // mLocData.longitude = location.getLongitude();
						// }
					}
				});
	}

	protected void alertInfo() {
		showAlertDialog("订单信息", "订单已经创建成功！", "继续购买",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						AppManager.getInstance().killActivity(
								SearchActivity.class);
						// Intent intent = new Intent(OrderFormActivity.this,
						// SearchActivity.class);
						// startActivity(intent);
						OrderFormActivity.this.finish();
						overridePendingTransition(R.anim.push_right_in,
								R.anim.push_right_out);
					}
				}, "返回主页", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						AppManager.getInstance().killAllActivity();
						Intent intent = new Intent(OrderFormActivity.this,
								MainActivity.class);
						startActivity(intent);
						OrderFormActivity.this.finish();
						overridePendingTransition(R.anim.push_right_in,
								R.anim.push_right_out);
					}
				});
	}

	protected void showAlertDialog(String title, String message,
			String positiveText,
			DialogInterface.OnClickListener onPositiveClickListener,
			String negativeText,
			DialogInterface.OnClickListener onNegativeClickListener) {
		new AlertDialog.Builder(this).setTitle(title).setMessage(message)
				.setPositiveButton(positiveText, onPositiveClickListener)
				.setNegativeButton(negativeText, onNegativeClickListener)
				.show();
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
