package com.o2o.maile.notify;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.o2o.maile.R;
import com.o2o.maile.entity.NotifyInfo;
import com.o2o.maile.ui.activity.HistoryOrderActivity;

public class NotifyCommonView extends LinearLayout {

	private TextView mNotifyTitleTV;

	private TextView mNotifyTimeTV;

	private TextView mNotifyMsgTV;

	private TextView mNotifyMsgBTV;

	private TextView mNotifyMsgFTV;

	private TextView mNotifyOtherTV;

	private Button mBtn;

	public NotifyCommonView(Context context, NotifyInfo notifyInfo) {
		super(context);
		initView(context, notifyInfo);
		fillData(context, notifyInfo);
	}

	private void initView(final Context context, NotifyInfo notifyInfo) {

		LayoutInflater inflater = LayoutInflater.from(context);
		LinearLayout layout = (LinearLayout) inflater.inflate(
				R.layout.notify_common, null);
		mNotifyTitleTV = (TextView) layout
				.findViewById(R.id.notify_common_title_tv);
		mNotifyMsgFTV = (TextView) layout
				.findViewById(R.id.notify_common_msg_f_tv);
		mNotifyMsgTV = (TextView) layout
				.findViewById(R.id.notify_common_msg_tv);
		mNotifyMsgBTV = (TextView) layout
				.findViewById(R.id.notify_common_msg_b_tv);
		mNotifyOtherTV = (TextView) layout
				.findViewById(R.id.notify_queue_other_tv);
		mNotifyTimeTV = (TextView) layout
				.findViewById(R.id.notify_common_time_tv);
		mBtn = (Button) layout.findViewById(R.id.nf_que_submit_btn);
		if (notifyInfo.getNotifyType()
				.equals(NotifyFactory.NotifyType.NewOrder)) {
			// mBtn.setText(R.string.qrcode_desk);
			mBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {

					Intent intent = new Intent(context,
							HistoryOrderActivity.class);
					context.startActivity(intent);
					((Activity) context).overridePendingTransition(
							R.anim.push_left_in, R.anim.push_left_out);
				}
			});
		} else {
			mBtn.setVisibility(View.GONE);
		}
		this.addView(layout);
	}

	public void fillData(Context context, NotifyInfo notifyInfo) {
		if (notifyInfo.getNotifyType()
				.equals(NotifyFactory.NotifyType.NewOrder)) {
			mNotifyTitleTV.setText("标题");
			mNotifyMsgFTV.setText("");
			mNotifyMsgBTV.setText("");
			mNotifyMsgTV.setText("通知");
			mNotifyTimeTV.setText(notifyInfo.getDate());
		} else if (notifyInfo.getNotifyType().equals(
				NotifyFactory.NotifyType.NewOrder)) {
			mNotifyTitleTV.setText("标题");
			mNotifyMsgFTV.setText("");
			mNotifyMsgBTV.setText("");
			mNotifyMsgTV.setText("通知");
			mNotifyTimeTV.setText(notifyInfo.getDate());
		}
	}

}
