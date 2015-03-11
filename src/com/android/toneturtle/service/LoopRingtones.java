package com.android.toneturtle.service;

import android.media.RingtoneManager;

public class LoopRingtones extends Loop{
	
	public LoopRingtones(){
		super("LoopRingtones");
	}
	protected String getName(){
		return "Ringotnes";
	}
	protected int getType(){
		return RingtoneManager.TYPE_RINGTONE;
	}
}
