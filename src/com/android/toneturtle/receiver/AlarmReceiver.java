package com.android.toneturtle.receiver;

import com.android.toneturtle.service.LoopReset;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {
	
	static final String ACTION_RESET_NOTIFICATION = "com.android.toneturtle.action.RESET_NOTIFICATION";
	static final String ACTION_RESET_RINGTONE = "com.android.toneturtle.action.RESET_RINGTONE";
	
	SharedPreferences prefs;

	@Override
	public void onReceive(Context context, Intent intent) {
		prefs = context.getSharedPreferences("com.android.toneturtle.ToneData", 0);
		String action = intent.getAction();
		Boolean bool;
		if (action == ACTION_RESET_NOTIFICATION){
			bool = prefs.getBoolean("NotificationsSwitch", false);
		}else{
			bool = prefs.getBoolean("RingtonesSwitch", false);
		}
		if (bool){
			Intent reset = new Intent(context, LoopReset.class);
			reset.setAction(action);
			context.startService(reset);
		}
	}
}