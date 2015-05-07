package com.o2o.maile.ui.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.o2o.maile.R;
import com.o2o.maile.entity.Goods;

public class GoodsAdapter extends BaseAdapter {

	private Context mContext;

	private ArrayList<Goods> mDatas;

	private LayoutInflater mInflater;

	public GoodsAdapter(Context context, ArrayList<Goods> datas) {
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
			convertView = mInflater.inflate(R.layout.goods_item, null);

			holder = new ViewHolder();
			holder.mName = (TextView) convertView
					.findViewById(R.id.item_name_tv);
			holder.mPrice = (TextView) convertView
					.findViewById(R.id.item_price_tv);
			holder.mBrief = (TextView) convertView
					.findViewById(R.id.item_brief_tv);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.mName.setText(mDatas.get(position).getName());
		holder.mPrice.setText("￥"+mDatas.get(position).getPrice());
		if (!"null".equals(mDatas.get(position).getBrief())) {
			holder.mBrief.setText(mDatas.get(position).getBrief());
		}else{
			holder.mBrief.setText(mContext.getString(R.string.no_goods_brief));
		}
		return convertView;
	}

	static class ViewHolder {

		public TextView mName;

		public TextView mPrice;

		public TextView mBrief;
	}
}
