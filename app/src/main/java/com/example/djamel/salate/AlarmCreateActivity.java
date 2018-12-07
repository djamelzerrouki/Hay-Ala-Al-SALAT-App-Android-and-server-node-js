package com.example.djamel.salate;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class AlarmCreateActivity extends Activity {

	private AlarmManager mAlarmManager;
	private Intent mNotificationReceiverIntent ;
	private PendingIntent mNotificationReceiverPendingIntent ;
	private static final long INITIAL_ALARM_DELAY =  10 * 1000L;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);

		// Get the AlarmManager Service
		mAlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

		// Create PendingIntent to start the AlarmNotificationReceiver
		mNotificationReceiverIntent = new Intent(AlarmCreateActivity.this,
				AlarmNotificationReceiver.class);
		mNotificationReceiverPendingIntent = PendingIntent.getBroadcast(
				AlarmCreateActivity.this, 0, mNotificationReceiverIntent, 0);


		// Single Alarm Button
		final Button singleAlarmButton = (Button) findViewById(R.id.single_alarm_button);
		singleAlarmButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mAlarmManager.set(AlarmManager.RTC_WAKEUP,
						System.currentTimeMillis() + INITIAL_ALARM_DELAY,
						mNotificationReceiverPendingIntent);


				Toast.makeText(getApplicationContext(), "Single Alarm Set",
						Toast.LENGTH_LONG).show();
			}
		});




	}
}