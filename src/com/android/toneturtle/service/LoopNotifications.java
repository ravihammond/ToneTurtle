package com.android.toneturtle.service;

import android.media.RingtoneManager;

public class LoopNotifications extends Loop {
	
	public LoopNotifications(){
		super("LoopNotifications");
	}
	protected String getName(){
		return "Notifications";
	}
	protected int getType(){
		return RingtoneManager.TYPE_NOTIFICATION;
	}
}
