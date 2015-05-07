package com.o2o.maile.ui.fragment;

import java.util.ArrayList;
import java.util.Collection;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.o2o.maile.R;
import com.o2o.maile.entity.OrderClient;
import com.o2o.maile.entity.OrderInfo;
import com.o2o.maile.network.config.MsgRequest;
import com.o2o.maile.network.logic.OrderLogic;
import com.o2o.maile.ui.activity.OrderDetailActivity;
import com.o2o.maile.ui.adapter.OrderInfoAdapter;
import com.o2o.maile.ui.view.CustomProgressDialog;
import com.o2o.maile.util.UserInfoManager;

public class OrderWaitReceivingFragment extends Fragment {

	private Context mContext;

	private CustomProgressDialog mWaitDialog;

	private ListView mListView;

	private OrderInfoAdapter mOrderAdapter;

	private ArrayList<OrderInfo> mHistoryOrderList = new ArrayList<OrderInfo>();

	private int mPageNum = 0;

	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			int what = msg.what;
			switch (what) {
			case OrderLogic.ORDERLIST_GET_SUC: {
				if(((Collection<? extends OrderInfo>) msg.obj).size()>0){
					mPageNum++;
					mHistoryOrderList.clear();
					mHistoryOrderList
					.addAll((Collection<? extends OrderInfo>) msg.obj);
					mOrderAdapter.notifyDataSetChanged();
				}else{
					Toast.makeText(mContext, R.string.no_order, Toast.LENGTH_SHORT)
					.show();
				}
				break;
			}
			case OrderLogic.ORDERLIST_GET_FAIL: {
				Toast.makeText(mContext, R.string.get_fail, Toast.LENGTH_SHORT)
						.show();
				break;
			}
			case OrderLogic.ORDERLIST_GET_EXCEPTION: {
				Toast.makeText(mContext, R.string.get_fail, Toast.LENGTH_SHORT)
						.show();
				break;
			}

			default:
				break;
			}
			if (null != mWaitDialog) {
				mWaitDialog.dismiss();
			}
		}

	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.history_order_fragment, null);
		initView(view);
		return view;
	}

	private void initView(View view) {
		mWaitDialog = new CustomProgressDialog(getActivity());
		mWaitDialog.show();

		mListView = (ListView) view.findViewById(R.id.history_order_lv);
		mHistoryOrderList.clear();
		mOrderAdapter = new OrderInfoAdapter(getActivity(), mHistoryOrderList);
		mListView.setAdapter(mOrderAdapter);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(mContext, OrderDetailActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable(OrderDetailActivity.ORDER_KEY,
						mHistoryOrderList.get(position));
				intent.putExtras(bundle);

				startActivity(intent);
				getActivity().overridePendingTransition(R.anim.push_left_in,
						R.anim.push_left_out);

			}
		});
		loadOrderList();
	}

	private void loadOrderList() {

		ArrayList<String> states = new ArrayList<String>();
		states.add(OrderClient.OrderState.challenged.toString());

		OrderLogic.getMyOrderList(mContext, mHandler,
				UserInfoManager.userInfo.getUsername(), mPageNum
						* MsgRequest.PAGE_SIZE, MsgRequest.PAGE_SIZE, states);
	}
}
