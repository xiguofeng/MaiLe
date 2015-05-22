package com.o2o.maile.service;

import io.netty.channel.ChannelHandlerContext;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.o2o.maile.R;
import com.o2o.maile.entity.NotifyInfo;
import com.o2o.maile.network.config.MsgResult;
import com.o2o.maile.network.logic.ConnectLogic;
import com.o2o.maile.network.netty.ConnectException;
import com.o2o.maile.network.netty.NettyMessageType;
import com.o2o.maile.network.netty.handler.AbstractNettyMessageHandler;
import com.o2o.maile.network.netty.handler.NettyMessageHandler;
import com.o2o.maile.network.netty.handler.NettyMessageHandlerFactory;
import com.o2o.maile.network.push.client.PushClient;
import com.o2o.maile.network.push.client.PushClientImpl;
import com.o2o.maile.network.push.message.PushOrder2BuyerRequest;
import com.o2o.maile.notify.Notify;
import com.o2o.maile.notify.NotifyFactory;
import com.o2o.maile.ui.activity.ResultActivity;
import com.o2o.maile.util.AppInfoManager;

/**
 * 
 * Push 服务.
 * 
 */
public class PushService extends Service {

	public static final String NOTIFY_ORDER_CHALLENGED = "action.notify.data.challenged";;

	private Context mContext;

	private Notification mNotification;
	private NotificationManager mNotificationManager;
	private NotificationCompat.Builder mBuilder;
	private PendingIntent mResultIntent;
	public static HashMap<Integer, NotifyInfo> sNotifyHistory = new HashMap<Integer, NotifyInfo>();
	HashMap<Integer, Notification> mNotifications = new HashMap<Integer, Notification>();
	private static int sNotifyId = 0;

	public static PushOrder2BuyerRequest mPushOrder2Buyer;
	public static String sDownUrl;

	/**
	 * 0 不需要升級 1 新更新 2 强制升级
	 */
	public static int isUngradeFlag = -1;
	public Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			int what = msg.what;
			switch (what) {
			case ConnectLogic.CONNECT_SUC: {
				HashMap<String, String> message = (HashMap<String, String>) msg.obj;
				if ("0".equals(message.get(MsgResult.RESULT_TYPE_TAG))) {
					isUngradeFlag = 0;
					AppInfoManager.setPushUrl(mContext,
							message.get(MsgResult.RESULT_PUSH_ADDRESS_TAG));
					pushConnection();
				} else if ("1".equals(message.get(MsgResult.RESULT_TYPE_TAG))) {
					isUngradeFlag = 1;
					sDownUrl = message
							.get(MsgResult.RESULT_SOFTDOWNLOADADDRESS_TAG);
					AppInfoManager.setPushUrl(mContext,
							message.get(MsgResult.RESULT_PUSH_ADDRESS_TAG));
					pushConnection();
				} else {
					isUngradeFlag = 2;
					sDownUrl = message
							.get(MsgResult.RESULT_SOFTDOWNLOADADDRESS_TAG);
					Toast.makeText(mContext, R.string.force_upgrade,
							Toast.LENGTH_SHORT).show();
					Intent intent = new Intent();
					intent.setAction(Intent.ACTION_VIEW);
					intent.setData(Uri.parse(sDownUrl));
					startActivity(intent);
				}
				break;
			}
			case ConnectLogic.CONNECT_FAIL: {
				Toast.makeText(mContext, R.string.reg_fail, Toast.LENGTH_SHORT)
						.show();
				break;
			}
			case ConnectLogic.CONNECT_EXCEPTION: {
				break;
			}
			case ConnectLogic.NET_ERROR: {
				break;
			}

			default:
				break;
			}

		}

	};

	@Override
	public void onCreate() {
		super.onCreate();
		mContext = this;
		initNotify();
		String version = getVersion();
		if (!TextUtils.isEmpty(version)) {
			ConnectLogic.connect(mContext, mHandler, Integer.parseInt(version));
		}
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		flags = START_STICKY;
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Intent sevice = new Intent(this, PushService.class);  
	    this.startService(sevice);  
	}

	private void pushConnection() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				String pushUrl = AppInfoManager.getPushUrl(mContext);
				if (TextUtils.isEmpty(pushUrl)) {
					return;
				}
				// int divisionIndex = pushUrl.indexOf(";");
				String[] divisionUrl = pushUrl.split(":");
				if (divisionUrl.length < 2) {
					return;
				}
				String host = divisionUrl[0];
				int port = Integer.valueOf(divisionUrl[1]);

				Log.e("xxx_pushurl", host + port);
				// [1] 启动客户端
				PushClient cli = new PushClientImpl(host, port);
				try {
					cli.start();
					// [2] 注册用户信息
					// cli.registUserInfo(UserType.buyer,
					// UserInfoManager.userInfo.getUsername(),
					// UserInfoManager.userInfo.getPassword());

					// [3] 定义推送消息处理类
					NettyMessageHandlerFactory factory = new NettyMessageHandlerFactory() {

						@Override
						public List<Integer> getNettyMessageTypes() {

							return Arrays
									.asList(NettyMessageType.PushBuyerOrderInfo
											.getType());
						}

						@Override
						public NettyMessageHandler createNettyMessageHandler(
								Integer messageType) {

							NettyMessageHandler h = new AbstractNettyMessageHandler<PushOrder2BuyerRequest>() {

								@Override
								protected void handle(
										ChannelHandlerContext ctx,
										PushOrder2BuyerRequest request) {
									Log.e("xxx_push_end", request.toString());
									sendMsg(request);
									System.out.println(request.toString());
								}

								@Override
								protected Class<PushOrder2BuyerRequest> getRequestType() {
									return PushOrder2BuyerRequest.class;
								}

							};
							return h;
						}
					};
					cli.addMessageHandlerFactory(factory);

					try {
						String clientID = "";
						while (TextUtils.isEmpty(clientID)) {
							Thread.sleep(2000);
							clientID = cli.clientID();
							Log.e("xxx_clientID", clientID);
							AppInfoManager.setClinetID(getApplicationContext(),
									clientID);
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				} catch (ConnectException e) {
					e.printStackTrace();
				}

			}
		}).start();

	}

	private void sendMsg(PushOrder2BuyerRequest info) {

		if (null != info) {
			mPushOrder2Buyer = info;
			Notify newOrderNotify = NotifyFactory
					.create(NotifyFactory.NotifyType.NewOrder);
			newOrderNotify.sendMsg(mContext, info);

			NotifyInfo notifyInfo = new NotifyInfo();
			notifyInfo.setNotifyType(NotifyFactory.NotifyType.NewOrder);
			notifyInfo.setSellerName(info.getSellerName());
			notifyInfo.setSellerPhone(info.getSellerPhone());
			notifyInfo.setTitle(getResources().getString(R.string.order_suc));
			notifyInfo.setContent(info.getOrderID());
			showIntentActivityNotify(notifyInfo);
		}

	}

	private void showNotification(NotifyInfo notifyInfo) {

		Notification notification = new Notification();
		notification.icon = R.drawable.ic_launcher;
		// notification.flags |= Notification.FLAG_ONGOING_EVENT;// 表示正处于活动中
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		notification.defaults |= Notification.DEFAULT_SOUND;

		Intent intent = new Intent(this, ResultActivity.class);
		// 这里要加入此Flags，作用：当你通过点击通知栏来唤起Activity时，对应的Activity启动模式要为android:launchMode="singleTop"
		// 于此Flag一起使用，可以保证你要启动的Activity不会重新启动，在一个堆栈只有一个对应的实例对象
		// 使用这个标志，如果正在启动的Activity的Task已经在运行的话，那么，新的Activity将不会启动；代替的，当前Task会简单的移入前台。
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		PendingIntent pendingIntent = PendingIntent.getActivity(this,
				sNotifyId + 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		notification.contentIntent = pendingIntent;

		RemoteViews contentView = new RemoteViews(getPackageName(),
				R.layout.notification);
		contentView.setImageViewResource(R.id.nf_icon, R.drawable.ic_launcher);
		notification.contentView = contentView;

		long time = SystemClock.elapsedRealtime();

		// notification.contentView.setTextViewText(R.id.nf_content,
		// notifyInfo.getTitle());
		notification.contentView.setTextViewText(R.id.nf_content,
				notifyInfo.getTitle() + "：订单ID：" + notifyInfo.getContent());

		// 使用服务来启动通知栏，这样的好处时，
		// 1.当应用程序在后台运行时，startForeground可以使本应用优先级提高，不至于被系统杀掉
		// 2.当应用被异常挂掉时，可以保证通知栏对应的图标被系统移除
		// mNotificationManager.notify(sNotifyId + 1, notification);
		startForeground(sNotifyId + 1, notification);

		sNotifyHistory.put(sNotifyId, notifyInfo);
		mNotifications.put(sNotifyId, notification);
		sNotifyId++;

	}

	/** 初始化通知栏 */
	private void initNotify() {
		mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		mBuilder = new NotificationCompat.Builder(this);
	}

	/** 显示通知栏点击跳转到指定Activity */
	private void showIntentActivityNotify(NotifyInfo notifyInfo) {
		// Notification.FLAG_ONGOING_EVENT --设置常驻
		// Flag;Notification.FLAG_AUTO_CANCEL 通知栏上点击此通知后自动清除此通知
		// notification.flags = Notification.FLAG_AUTO_CANCEL;
		// //在通知栏上点击此通知后自动清除此通知
		mBuilder.setAutoCancel(true)
				// 点击后让通知将消失
				.setContentTitle(notifyInfo.getTitle())
				.setContentText("：订单ID：" + notifyInfo.getContent())
				.setTicker("点我").setSmallIcon(R.drawable.ic_launcher)
				.setDefaults(Notification.DEFAULT_VIBRATE);
		// 点击的意图ACTION是跳转到Intent
		Intent resultIntent = new Intent(this, ResultActivity.class);
		resultIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
				resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		mBuilder.setContentIntent(pendingIntent);
		Log.e("xxx_mNotificationManager", "mNotificationManager");
		mNotificationManager.notify(100, mBuilder.build());
	}

	/**
	 * 获取版本号
	 * 
	 * @return 当前应用的版本号
	 */
	private String getVersion() {
		try {
			PackageManager manager = this.getPackageManager();
			PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
			String version = info.versionName;
			return version;
		} catch (Exception e) {
			return null;
		}
	}

}
