package com.android.toneturtle.service;

import java.util.ArrayList;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

abstract public class Loop extends IntentService {

	public Loop(String name){
		super(name);
	}
	
	@Override
	public void onHandleIntent(Intent intent){
		SharedPreferences prefs = getSharedPreferences("com.android.toneturtle.ToneData",0);
		Gson gson = new Gson();
		String name = getName();
		
		ArrayList<String> sounds = gson.fromJson(prefs.getString(name,null), new TypeToken<ArrayList<String>>(){}.getType());
		int current = prefs.getInt("Current"+name+"Index",-1);
		
		if (current+1 == sounds.size()){
			current = -1;
		}
		RingtoneManager.setActualDefaultRingtoneUri(this, getType(), Uri.parse(sounds.get(++current)));
		prefs.edit().putInt("Current"+name+"Index", current).apply();
	}
	abstract protected String getName();
	abstract protected int getType();
}
