package com.ideal.tools;

import com.ideal.smscut.R;
import com.ideal.smscut.SmsCut;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Parcel;

public class MyNotification{
	private Context context;
	public MyNotification(){
		
	}
	public MyNotification(Context context) {
		this.context=  context;
	}
	
	/**
     * ��״̬����ʾ֪ͨ
     */
    private void showNotification(){
        // ����һ��NotificationManager������  
    	NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        // ����Notification�ĸ�������  
        Notification notification =new Notification(R.drawable.icon,  
                "����ϵͳ", System.currentTimeMillis());
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
        // ����֪ͨ���¼���Ϣ  
        CharSequence contentTitle ="����ϵͳ����"; // ֪ͨ������  
        CharSequence contentText ="����ϵͳ����"; // ֪ͨ������  
        Intent notificationIntent =new Intent(context, SmsCut.class); // �����֪ͨ��Ҫ��ת��Activity  
        PendingIntent contentItent = PendingIntent.getActivity(context, 0, notificationIntent, 0);  
        notification.setLatestEventInfo(context, contentTitle, contentText, contentItent);  
          
        // ��Notification���ݸ�NotificationManager  
        notificationManager.notify(0, notification);  
    }
    //ɾ��֪ͨ   
    private void clearNotification(){
        // ������ɾ��֮ǰ���Ƕ����֪ͨ  
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);  
        notificationManager.cancel(0); 
  
    }

		
}
