package com.o2o.maile.notify.notification;

import java.util.HashMap;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.o2o.maile.R;
import com.o2o.maile.entity.NotifyInfo;
import com.o2o.maile.notify.NotifyListActivity;

public class NotifyNotification {

	Context mContext;

	HashMap<String, NotificationItem> mNotifications;

	ManagerNotification mManagerNotification;

	public NotifyNotification(Context context,
			ManagerNotification managerNotification) {
		mContext = context;
		mManagerNotification = managerNotification;
		mNotifications = new HashMap<String, NotificationItem>();
	}

	static class NotificationItem {

		int mId;
		String mContent;
		String mTitles;

		void addItem(int itemId, String title, String content) {
			mTitles = title;
			mContent = content;
			mId = itemId;
		}
	}

	public void updateNotification(HashMap<String, NotifyInfo> map) {
		updateActiveNotification(map);
	}

	private void updateActiveNotification(HashMap<String, NotifyInfo> map) {
		mNotifications.clear();

		for (String key : map.keySet()) {
			NotifyInfo notifyInfo = map.get(key);
			NotificationItem item;
			if (mNotifications.containsKey(notifyInfo.getNotifyType())) {
				item = mNotifications.get(notifyInfo.getNotifyType());
				item.addItem(Integer.valueOf(key), notifyInfo.getTitle(),
						notifyInfo.getContent());
			} else {
				item = new NotificationItem();
				item.addItem(Integer.valueOf(key), notifyInfo.getTitle(),
						notifyInfo.getContent());
				mNotifications.put(key, item);
			}

		}

		for (NotificationItem item : mNotifications.values()) {
			Notification n = new Notification();

			n.icon = R.drawable.common_loading4_11;

			n.flags |= Notification.FLAG_ONGOING_EVENT;

			RemoteViews expandedView = new RemoteViews(
					mContext.getPackageName(), R.layout.notification);
			StringBuilder title = new StringBuilder(item.mTitles);

			expandedView.setTextViewText(R.id.nf_content, item.mContent);

			expandedView.setTextViewText(R.id.nf_content, title);

			expandedView.setImageViewResource(R.id.nf_icon,
					R.drawable.common_loading4_11);
			n.contentView = expandedView;

			Intent intent = new Intent(mContext, NotifyListActivity.class);
			n.contentIntent = PendingIntent.getActivity(mContext, item.mId,
					intent, PendingIntent.FLAG_UPDATE_CURRENT);

			PendingIntent pendIntent = PendingIntent.getActivity(mContext,
					item.mId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
			n.setLatestEventInfo(mContext, title, "", pendIntent);
			mManagerNotification.postNotification(item.mId, n);

		}
	}

}
