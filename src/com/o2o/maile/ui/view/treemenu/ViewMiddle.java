package com.o2o.maile.ui.view.treemenu;

import java.util.ArrayList;
import java.util.LinkedList;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.o2o.maile.R;
import com.o2o.maile.entity.Category;
import com.o2o.maile.ui.adapter.CategoryAdapter;

public class ViewMiddle extends LinearLayout implements ViewBaseAction {

	private ListView regionListView;
	private ListView plateListView;
	private ArrayList<Category> groups = new ArrayList<Category>();
	// private LinkedList<Category> childrenItem = new LinkedList<Category>();
	private ArrayList<Category> childrenItem = new ArrayList<Category>();
	private SparseArray<LinkedList<Category>> children = new SparseArray<LinkedList<Category>>();
	private CategoryAdapter earaListViewAdapter;
	private CategoryAdapter plateListViewAdapter;
	private OnSelectOneListener mOnSelectOneListener;
	private OnSelectListener mOnSelectListener;
	private int tEaraPosition = 0;
	private int tBlockPosition = 0;
	private String showString = "不限";

	public ViewMiddle(Context context) {
		super(context);
		init(context);
	}

	public ViewMiddle(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public void updateShowText(String showArea, String showBlock) {
		if (showArea == null || showBlock == null) {
			return;
		}
		for (int i = 0; i < groups.size(); i++) {
			if (groups.get(i).equals(showArea)) {
				earaListViewAdapter.setSelectedPosition(i);
				childrenItem.clear();
				if (i < children.size()) {
					childrenItem.addAll(children.get(i));
				}
				tEaraPosition = i;
				break;
			}
		}
		for (int j = 0; j < childrenItem.size(); j++) {
			if (childrenItem.get(j).getCategoryName().replace("不限", "")
					.equals(showBlock.trim())) {
				plateListViewAdapter.setSelectedPosition(j);
				tBlockPosition = j;
				break;
			}
		}
		setDefaultSelect();
	}

	private void init(Context context) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.middle_item, this, true);
		regionListView = (ListView) findViewById(R.id.listView);
		plateListView = (ListView) findViewById(R.id.listView2);
		// setBackgroundDrawable(getResources().getDrawable(
		// R.drawable.choosearea_bg_left));
		// for (int i = 0; i < 10; i++) {
		// groups.add(i + "行");
		// LinkedList<String> tItem = new LinkedList<String>();
		// for (int j = 0; j < 15; j++) {
		//
		// tItem.add(i + "行" + j + "列");
		//
		// }
		// children.put(i, tItem);
		// }

		earaListViewAdapter = new CategoryAdapter(context, groups,
				R.drawable.choose_item_selected,
				R.drawable.choose_eara_item_selector);
		earaListViewAdapter.setTextSize(17);
		earaListViewAdapter.setSelectedPositionNoNotify(tEaraPosition);
		regionListView.setAdapter(earaListViewAdapter);
		earaListViewAdapter
				.setOnItemClickListener(new CategoryAdapter.OnItemClickListener() {

					@Override
					public void onItemClick(View view, int position) {
						groups.clear();
						childrenItem.clear();
						mOnSelectOneListener.getValue(position);
						// if (position < children.size()) {
						// childrenItem.clear();
						// childrenItem.addAll(children.get(position));
						// plateListViewAdapter.notifyDataSetChanged();
						// }
					}
				});
		// if (tEaraPosition < children.size())
		// childrenItem.addAll(children.get(tEaraPosition));
		plateListViewAdapter = new CategoryAdapter(context, childrenItem,
				R.drawable.choose_item_right,
				R.drawable.choose_plate_item_selector);
		plateListViewAdapter.setTextSize(15);
		plateListViewAdapter.setSelectedPositionNoNotify(tBlockPosition);
		plateListView.setAdapter(plateListViewAdapter);
		plateListViewAdapter
				.setOnItemClickListener(new CategoryAdapter.OnItemClickListener() {

					@Override
					public void onItemClick(View view, final int position) {

						showString = childrenItem.get(position)
								.getCategoryName();
						if (mOnSelectListener != null) {
							mOnSelectListener.getValue(position);
						}

					}
				});
		if (tBlockPosition < childrenItem.size())
			showString = childrenItem.get(tBlockPosition).getCategoryName();
		if (showString.contains("不限")) {
			showString = showString.replace("不限", "");
		}
		setDefaultSelect();

	}

	public void refreshData(ArrayList<Category> categoryOne,
			ArrayList<Category> categoryTwo) {
		groups.clear();
		groups.addAll(categoryOne);
		childrenItem.clear();
		childrenItem.addAll(categoryTwo);
		Log.e("xxx_1", "" + childrenItem.size());
		childrenItem.size();
		earaListViewAdapter.notifyDataSetChanged();
		plateListViewAdapter.notifyDataSetChanged();
	}

	public void setDefaultSelect() {
		regionListView.setSelection(tEaraPosition);
		plateListView.setSelection(tBlockPosition);
	}

	public String getShowText() {
		return showString;
	}

	public void setOnSelectListener(OnSelectListener onSelectListener) {
		mOnSelectListener = onSelectListener;
	}

	public interface OnSelectListener {
		public void getValue(int position);
	}

	public void setOnSelectOneListener(OnSelectOneListener onSelectoneListener) {
		mOnSelectOneListener = onSelectoneListener;
	}

	public interface OnSelectOneListener {
		public void getValue(int position);
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}
}
