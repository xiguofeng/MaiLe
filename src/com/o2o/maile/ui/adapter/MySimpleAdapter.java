package com.o2o.maile.ui.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.o2o.maile.R;

public class MySimpleAdapter extends BaseAdapter {

	private Context mContext;

	private ArrayList<String> mDatas;

	private LayoutInflater mInflater;

	public MySimpleAdapter(Context context, ArrayList<String> datas) {
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
			convertView = mInflater.inflate(R.layout.simple_item, null);
			holder = new ViewHolder();
			holder.mName = (TextView) convertView
					.findViewById(R.id.item_name_tv);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.mName.setText(mDatas.get(position));
		return convertView;
	}

	static class ViewHolder {

		public TextView mName;

	}
}
