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

	// ���ܶ���
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
				String content = message.getMessageBody();// �õ���������
				String sender = message.getOriginatingAddress();// �õ�����Ϣ�ĺ���
				if (sender.equals(num)) {
					Log.e("TAG", "���ص�����Ϊ��"+content);
					abortBroadcast();// ��ֹ����
					Log.e("TAG", "�˺���Ϊ���������룬������!");
					
					//System.out.println(context);
					// ����һ��NotificationManager������  
			    	NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
			        // ����Notification�ĸ�������  
			    	
			    	Log.e("TAG", "notificationManage create");
			        Notification notification =new Notification(R.drawable.icon,  
			                "���ſͷ�", System.currentTimeMillis());
			        Log.e("TAG", "notification create");
			        //FLAG_AUTO_CANCEL   ��֪ͨ�ܱ�״̬���������ť�������
			        //FLAG_NO_CLEAR      ��֪ͨ���ܱ�״̬���������ť�������
			        //FLAG_ONGOING_EVENT ֪ͨ��������������
			        //FLAG_INSISTENT     �Ƿ�һֱ���У���������һֱ���ţ�֪���û���Ӧ
			        notification.flags = Notification.FLAG_ONGOING_EVENT; // ����֪ͨ�ŵ�֪ͨ����"Ongoing"��"��������"����  
			        notification.flags = Notification.FLAG_NO_CLEAR; // �����ڵ����֪ͨ���е�"���֪ͨ"�󣬴�֪ͨ�������������FLAG_ONGOING_EVENTһ��ʹ��  
			        notification.flags = Notification.FLAG_SHOW_LIGHTS;  
			        //DEFAULT_ALL     ʹ������Ĭ��ֵ�������������𶯣������ȵ�
			        //DEFAULT_LIGHTS  ʹ��Ĭ��������ʾ
			        //DEFAULT_SOUNDS  ʹ��Ĭ����ʾ����
			        //DEFAULT_VIBRATE ʹ��Ĭ���ֻ��𶯣������<uses-permission android:name="android.permission.VIBRATE" />Ȩ��
			        notification.defaults = Notification.DEFAULT_LIGHTS;
			        //����Ч������
			        //notification.defaults=Notification.DEFAULT_LIGHTS|Notification.DEFAULT_SOUND;
			        notification.ledARGB = Color.BLUE;  
			        notification.ledOnMS =5000; //����ʱ�䣬����
			        
			        Log.e("TAG", "set properties");
			        // ����֪ͨ���¼���Ϣ  
			        CharSequence contentTitle ="���ض�������"; // ֪ͨ������  
			        CharSequence contentText =content; // ֪ͨ������
			        Log.e("TAG", "set text");
			        Intent notificationIntent =new Intent(context, SmsCut.class); // �����֪ͨ��Ҫ��ת��Activity  
			        PendingIntent contentItent = PendingIntent.getActivity(context, 0, notificationIntent, 0);  
			        notification.setLatestEventInfo(context, contentTitle, contentText, contentItent);  
			        Log.e("TAG", "set links");
			        // ��Notification���ݸ�NotificationManager  
			        notificationManager.notify(0, notification);  
			        Log.e("TAG", "start notify");
				}
				/*
				 * 
				 * �ظ���Ϣ��Ҫ�õ�
				Date date = new Date(message.getTimestampMillis());
				SimpleDateFormat format = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				String sendContent = format.format(date) + ":" + sender + "--"
						+ content;
				SmsManager smsManager = SmsManager.getDefault();// ����Ϣʱ��Ҫ��
				smsManager.sendTextMessage("", null, sendContent, null, null);// ת����
				Log.v("TAG", sendContent);
				*/
			}
		}
	}
}
