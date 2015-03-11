package com.android.toneturtle.service;

import java.util.ArrayList;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.SystemClock;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

abstract public class LoopReset extends IntentService {

	public LoopReset(String name) {
		super(name);
	}
	
	@Override
	protected void onHandleIntent(Intent intent) {
		SharedPreferences prefs = getSharedPreferences("com.android.toneturtle.ToneData",0);
		Gson gson = new Gson();
		String action = intent.getAction();
		String name = getName();

		ArrayList<String> sounds = gson.fromJson(prefs.getString(name, null), new TypeToken<ArrayList<String>>(){}.getType());
		int current = prefs.getInt("Current"+name+"Index",-1);
		
		if(current+1 == sounds.size()){
			current = -1;
		}
		if(action == "ACTION_NEXT"){
			int time = prefs.getInt(name+"Time",20);
			AlarmManager am = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
			Intent alarmIntent = new Intent("com.android.toneturtle.action.RESET_"+name);
			PendingIntent pAlarmIntent = PendingIntent.getBroadcast(this,0,alarmIntent,0);
			am.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,SystemClock.elapsedRealtime()+time*1000, pAlarmIntent);
//			current++;
		}else{
			current = -1;
		}
		RingtoneManager.setActualDefaultRingtoneUri(this, getType(), Uri.parse(sounds.get(++current)));
		prefs.edit().putInt("Current"+name+"Index",current).apply();
	}
	abstract protected String getName();
	abstract protected int getType();
}