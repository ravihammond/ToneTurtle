package com.android.toneturtle.service;

import android.media.RingtoneManager;

public class LoopResetRingtones extends LoopReset {
	
	public LoopResetRingtones(){
		super("LoopResetRingtones");
	}
	protected String getName(){
		return "Ringtones";
	}
	protected int getType(){
		return RingtoneManager.TYPE_RINGTONE;
	}
}