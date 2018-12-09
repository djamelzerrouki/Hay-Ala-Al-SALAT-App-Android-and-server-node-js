package com.example.djamel.salate;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class AlarmNotificationReceiver extends BroadcastReceiver {
	// Notification ID to allow for future updates
	private static final int MY_NOTIFICATION_ID = 1;
	private static final String TAG = "AlarmNotificationReceiver";

	// Notification Text Elements
	private final CharSequence tickerText = "Are You Playing Angry Birds Again!";
	private final CharSequence contentTitle = "حان وقت الآذان : ";
	private final CharSequence contentText = "صلاة المغرب  تقبل اللّه صلاتك ومزيد من الاجر و المغفرة و الثواب ,إنشاء اللّه  ";

	// Notification Action Elements
	private Intent mNotificationIntent;
	private PendingIntent mContentIntent;

	// Notification Sound and Vibration on Arrival
	private Uri soundURI = Uri
			.parse("android.resource://com.example.djamel.salate/"
					+ R.raw.adhan);
	private long[] mVibratePattern = { 0, 200, 200, 300 };

	@SuppressLint("LongLogTag")
	@Override
	public void onReceive(Context context, Intent intent) {

		mNotificationIntent = new Intent(context, Home.class);
  mContentIntent = PendingIntent.getActivity(context, 0,
				mNotificationIntent,  PendingIntent.FLAG_UPDATE_CURRENT);

		//NotificationSalat notificationSalat=new NotificationSalat();

	//	notificationSalat.notify( context, new Date()+" ",1234);
		//  messageNotification.notify(context ,"rabeh cv ",134);


		 Notification.Builder notificationBuilder = new Notification.Builder(
				context)
				.setTicker(tickerText)
				.setSmallIcon(android.R.drawable.stat_sys_warning)
				.setAutoCancel(true)
				.setContentTitle(contentTitle)
				.setContentText(contentText)
				.setContentIntent(mContentIntent)
				.setSound(soundURI)
				.setVibrate(mVibratePattern);
		// Pass the Notification to the NotificationManager:
		NotificationManager mNotificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		mNotificationManager.notify(MY_NOTIFICATION_ID,
				notificationBuilder.build());
		
	 Log.i(TAG,"Sending notification at:" + DateFormat.getDateTimeInstance().format(new Date()));

	}
}
