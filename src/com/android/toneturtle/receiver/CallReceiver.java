package com.android.toneturtle.receiver;

import com.android.toneturtle.service.LoopResetRingtones;
import com.android.toneturtle.service.LoopRingtones;
import com.android.toneturtle.service.ShuffleRingtones;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class CallReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		SharedPreferences prefs = context.getSharedPreferences("com.android.toneturtle.ToneData", 0);
		if (prefs.getBoolean("RingtonesSwitch", false)){
			int mode = prefs.getInt("RingtoneMode", 0);
			Intent next;
			if (mode == 0){
				next = new Intent(context,ShuffleRingtones.class);
			}else if (mode == 1){
				next = new Intent(context,LoopRingtones.class);
			}else if (mode == 2){
				next = new Intent(context,LoopResetRingtones.class);
			}else{
				next = new Intent(context,ShuffleRingtones.class);
			}
			next.setAction("ACTION_NEXT");
			context.startService(next);
		}
	}
}