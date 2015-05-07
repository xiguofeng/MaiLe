
package com.o2o.maile.notify.notification;

import java.util.HashMap;
import java.util.Map;

import android.app.Notification;
import android.app.NotificationManager;

public class ManagerNotification {

    NotificationManager mNotificationManager;

    Map<String, String> msgMap = new HashMap<String, String>();

    public ManagerNotification(NotificationManager notificationManager) {
        mNotificationManager = notificationManager;
    }

    public void postNotification(long id, Notification notification) {
        mNotificationManager.notify((int) id, notification);
    }

}
