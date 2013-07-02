package com.ideal.smscut;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.ideal.tools.MyNotification;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

public class SmsRecevier extends BroadcastReceiver {
	private String num;

	public SmsRecevier(String number) {
		Log.v("TAG", "SmsRecevier create");
		num=number;
	}

	// 接受短信
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.v("TAG", "SmsRecevier onReceive");
		Object[] pdus = (Object[]) intent.getExtras().get("pdus");
		if (pdus != null && pdus.length > 0) {
			SmsMessage[] messages = new SmsMessage[pdus.length];
			for (int i = 0; i < pdus.length; i++) {
				byte[] pdu = (byte[]) pdus[i];
				messages[i] = SmsMessage.createFromPdu(pdu);
			}
			for (SmsMessage message : messages) {
				String content = message.getMessageBody();// 得到短信内容
				String sender = message.getOriginatingAddress();// 得到发信息的号码
				if (sender.equals(num)) {
					Log.e("TAG", "拦截的内容为："+content);
					abortBroadcast();// 中止发送
					Log.e("TAG", "此号码为黑名单号码，已拦截!");
					
					//System.out.println(context);
					// 创建一个NotificationManager的引用  
			    	NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
			        // 定义Notification的各种属性  
			    	
			    	Log.e("TAG", "notificationManage create");
			        Notification notification =new Notification(R.drawable.icon,  
			                "短信客服", System.currentTimeMillis());
			        Log.e("TAG", "notification create");
			        //FLAG_AUTO_CANCEL   该通知能被状态栏的清除按钮给清除掉
			        //FLAG_NO_CLEAR      该通知不能被状态栏的清除按钮给清除掉
			        //FLAG_ONGOING_EVENT 通知放置在正在运行
			        //FLAG_INSISTENT     是否一直进行，比如音乐一直播放，知道用户响应
			        notification.flags = Notification.FLAG_ONGOING_EVENT; // 将此通知放到通知栏的"Ongoing"即"正在运行"组中  
			        notification.flags = Notification.FLAG_NO_CLEAR; // 表明在点击了通知栏中的"清除通知"后，此通知不清除，经常与FLAG_ONGOING_EVENT一起使用  
			        notification.flags = Notification.FLAG_SHOW_LIGHTS;  
			        //DEFAULT_ALL     使用所有默认值，比如声音，震动，闪屏等等
			        //DEFAULT_LIGHTS  使用默认闪光提示
			        //DEFAULT_SOUNDS  使用默认提示声音
			        //DEFAULT_VIBRATE 使用默认手机震动，需加上<uses-permission android:name="android.permission.VIBRATE" />权限
			        notification.defaults = Notification.DEFAULT_LIGHTS;
			        //叠加效果常量
			        //notification.defaults=Notification.DEFAULT_LIGHTS|Notification.DEFAULT_SOUND;
			        notification.ledARGB = Color.BLUE;  
			        notification.ledOnMS =5000; //闪光时间，毫秒
			        
			        Log.e("TAG", "set properties");
			        // 设置通知的事件消息  
			        CharSequence contentTitle ="拦截短信内容"; // 通知栏标题  
			        CharSequence contentText =content; // 通知栏内容
			        Log.e("TAG", "set text");
			        Intent notificationIntent =new Intent(context, SmsCut.class); // 点击该通知后要跳转的Activity  
			        PendingIntent contentItent = PendingIntent.getActivity(context, 0, notificationIntent, 0);  
			        notification.setLatestEventInfo(context, contentTitle, contentText, contentItent);  
			        Log.e("TAG", "set links");
			        // 把Notification传递给NotificationManager  
			        notificationManager.notify(0, notification);  
			        Log.e("TAG", "start notify");
				}
				/*
				 * 
				 * 回复信息需要用到
				Date date = new Date(message.getTimestampMillis());
				SimpleDateFormat format = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				String sendContent = format.format(date) + ":" + sender + "--"
						+ content;
				SmsManager smsManager = SmsManager.getDefault();// 发信息时需要的
				smsManager.sendTextMessage("", null, sendContent, null, null);// 转发给
				Log.v("TAG", sendContent);
				*/
			}
		}
	}
}
