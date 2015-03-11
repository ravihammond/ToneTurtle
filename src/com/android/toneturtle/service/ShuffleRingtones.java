package com.android.toneturtle.service;

import android.media.RingtoneManager;

public class ShuffleRingtones extends Shuffle {
	
	public ShuffleRingtones(){
		super("ShuffleRingtones");
	}
	protected String getName(){
		return "Ringtones";
	}
	protected int getType(){
		return RingtoneManager.TYPE_RINGTONE;
	}
}