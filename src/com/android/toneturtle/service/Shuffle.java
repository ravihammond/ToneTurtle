package com.android.toneturtle.service;

import java.util.ArrayList;
import java.util.Random;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

abstract public class Shuffle extends IntentService {
	
	public Shuffle(String name){
		super(name);
	}
	
	@Override
	public void onHandleIntent(Intent intent){
		SharedPreferences prefs = getSharedPreferences("com.android.toneturtle.ToneData", 0);
		Gson gson = new Gson();
		String name = getName();
		
		ArrayList<String> sounds = gson.fromJson(prefs.getString(name,null), new TypeToken<ArrayList<String>>(){}.getType());
		
		Random rand = new Random();
		RingtoneManager.setActualDefaultRingtoneUri(this, getType(), Uri.parse(sounds.get(rand.nextInt(sounds.size()))));
	}
	
	abstract protected String getName();
	abstract protected int getType();
}
