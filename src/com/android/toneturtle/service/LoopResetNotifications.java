package com.android.toneturtle.service;

import android.media.RingtoneManager;
import android.util.Log;

public class LoopResetNotifications extends LoopReset {
	
	public LoopResetNotifications(){
		super("LoopResetNotifications");
	}
	protected String getName(){
		Log.d("service","activated");
		return "Notifications";
	}
	protected int getType(){
		return RingtoneManager.TYPE_NOTIFICATION;
	}
}