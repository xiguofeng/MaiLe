package com.o2o.maile.notify;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.o2o.maile.network.push.message.PushOrder2BuyerRequest;
import com.o2o.maile.service.PushService;

public class ChallengedOrderNotify implements Notify {

	// {"orderID":"26373082314953477079756380883","probablyWaitTime":123,"orderState":"1",
	// "sellerID":"26352823805463796607871884824","sellerName":"12369","sellerPhone":"8",
	// "message":"%E6%B1%9F%E8%8B%8F%E7%9C%81%E5%8D%97%E4%BA%AC%E5%B8%82%E9%9B%A8%E8%8A%B1%E5%8F%B0%E5%8C%BA%E5%B0%8F%E8%A1%8C%E8%B7%AF130%E5%8F%B7%E9%99%84%E8%BF%91"}
	@Override
	public void sendMsg(Context context, Object object) {
		PushOrder2BuyerRequest order = (PushOrder2BuyerRequest) object;

		Bundle localBundle = new Bundle();
		localBundle.putString("orderID", order.getOrderID());
		localBundle.putString("orderState", order.getOrderState());
		localBundle.putString("sellerID", order.getSellerID());
		localBundle.putString("sellerName", order.getSellerName());
		localBundle.putString("sellerPhone", order.getSellerPhone());
		localBundle.putString("probablyWaitTime",
				String.valueOf(order.getProbablyWaitTime()));
		localBundle.putString("message", order.getMessage());
		Intent localIntent = new Intent(PushService.NOTIFY_ORDER_CHALLENGED);
		localIntent.putExtras(localBundle);
		context.sendBroadcast(localIntent);
	}

}
