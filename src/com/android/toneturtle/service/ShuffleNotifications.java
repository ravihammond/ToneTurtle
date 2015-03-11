package com.android.toneturtle.service;

import android.media.RingtoneManager;

public class ShuffleNotifications extends Shuffle {
	
	public ShuffleNotifications(){
		super("ShuffleRingtones");
	}
	protected String getName(){
		return "Notifications";
	}
	protected int getType(){
		return RingtoneManager.TYPE_NOTIFICATION;
	}
}
