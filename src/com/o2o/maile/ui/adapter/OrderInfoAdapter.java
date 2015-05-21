package com.o2o.maile.ui.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.o2o.maile.R;
import com.o2o.maile.entity.OrderInfo;
import com.o2o.maile.util.TimeUtils;

public class OrderInfoAdapter extends BaseAdapter {

	private Context mContext;

	private ArrayList<OrderInfo> mDatas;

	private LayoutInflater mInflater;

	public OrderInfoAdapter(Context context, ArrayList<OrderInfo> datas) {
		this.mContext = context;
		this.mDatas = datas;
		mInflater = LayoutInflater.from(mContext);

	}

	@Override
	public int getCount() {
		if (mDatas != null) {
			return mDatas.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.order_item, null);

			holder = new ViewHolder();
			holder.mName = (TextView) convertView
					.findViewById(R.id.item_name_tv);
			holder.mPrice = (TextView) convertView
					.findViewById(R.id.item_price_tv);
			holder.mLocal = (TextView) convertView
					.findViewById(R.id.item_local_tv);
			holder.mNum = (TextView) convertView.findViewById(R.id.item_num_tv);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.mName.setText(mDatas.get(position).getBuyer());
		holder.mLocal.setText(mDatas.get(position).getState());
		holder.mPrice.setText(mDatas.get(position).getGoods().get(0));
		holder.mNum.setText(TimeUtils.TimeStamp2Date(
				String.valueOf(mDatas.get(position).getCreateTime()),
				TimeUtils.FORMAT_PATTERN_DATE));
		return convertView;
	}

	static class ViewHolder {

		public TextView mName;

		public TextView mPrice;

		public TextView mLocal;

		public TextView mNum;
	}
}
