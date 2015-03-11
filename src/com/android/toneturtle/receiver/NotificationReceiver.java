package com.android.toneturtle.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.toneturtle.Playlist;
import com.android.toneturtle.service.LoopNotifications;
import com.android.toneturtle.service.LoopResetNotifications;
import com.android.toneturtle.service.ShuffleNotifications;

public class NotificationReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		SharedPreferences prefs = context.getSharedPreferences("com.android.toneturtle.ToneData", 0);
//		Log.d("NotificationReceiver","functional");

		if(prefs.getBoolean("NotificationsSwitch", false)){
			Log.d("NotificationReceiver","functional");
			int mode = prefs.getInt("NotificationMode", 2);
			Intent next;
			if (mode == Playlist.ShuffleMode){
				next = new Intent(context,ShuffleNotifications.class);
			}else if (mode == Playlist.LoopMode){
				next = new Intent(context,LoopNotifications.class);
			}else if (mode == Playlist.ShuffleMode){
				next = new Intent(context,LoopResetNotifications.class);
				Log.d("NotificationReceiver","mode correct");
			}else{
				next = new Intent(context,ShuffleNotifications.class);
			}
			next.setAction("ACTION_NEXT");
			context.startService(next);
		}
	}
}