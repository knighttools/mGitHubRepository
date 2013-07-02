package com.ideal.smscut;

import com.ideal.smscut.R;
import com.ideal.tools.MyNotification;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SmsCut extends Activity {
	private EditText sms_num_edit;
	private Button yes_btn;
	private Button no_btn;
	private Context context;
	private SmsRecevier recevier=null;
	private boolean isregiset = false;

	private static final String ACTION = "android.provider.Telephony.SMS_RECEIVED";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		context = this.getApplicationContext();
		sms_num_edit = (EditText) findViewById(R.id.sms_number_edit);
		yes_btn = (Button) findViewById(R.id.yes_btn);
		no_btn = (Button) findViewById(R.id.no_btn);
		yes_btn.setEnabled(true);
		no_btn.setEnabled(false);
		yes_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				regiset();
				new MyNotification(context);
				yes_btn.setEnabled(false);
				no_btn.setEnabled(true);
			}
		});
		no_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				unregiset();
				SmsCut.this.finish();
			}
		});
	}
	
	public void regiset() {
		IntentFilter filter = new IntentFilter(ACTION);
		filter.setPriority(1000);// �������ȼ����
		recevier = new SmsRecevier(sms_num_edit.getText().toString().trim());
		registerReceiver(recevier, filter);
		isregiset = true;
		Toast.makeText(this, "��ʼ����", 0).show();
	}
	
	public void unregiset() {
		if (recevier != null && isregiset) {
			unregisterReceiver(recevier);
			isregiset = false;
			Toast.makeText(this, "ֹͣ����,�رճ���", 0).show();
		} else
			Toast.makeText(this, "��δ���ã��رճ���", 0).show();
	}
	protected void onDestroy() {
		super.onDestroy();
		if (recevier != null && isregiset) {
			unregisterReceiver(recevier);
			isregiset = false;
			Toast.makeText(this, "ֹͣ����,�رճ���", 0).show();
		}
	}
}
