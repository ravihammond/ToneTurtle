package com.devahead.extendingandroidapplication;

import java.io.File;
import java.util.ArrayList;

import com.android.toneturtle.Playlist;
import com.android.toneturtle.Settings;
import com.android.toneturtle.Int;
import com.android.toneturtle.activity.PlaylistActivity;
import com.android.toneturtle.activity.SoundActivity;
import com.google.gson.Gson;

import android.app.Application;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;


public class ToneApplication extends Application{
	private Gson gson = new Gson();
	private Settings settings = new Settings();
	private SharedPreferences prefs;
	private PlaylistActivity playlist_activity;
	private SoundActivity sound_activity;
	
	public void onCreate(){
		String jsonstring = gson.toJson(settings);
	    prefs = getSharedPreferences("com.android.toneturtle.ToneData", 0);
	    jsonstring = prefs.getString("settings", jsonstring); 
	    saveSettings(jsonstring);
	    String path2 = "/storage/sdcard0/TestSounds/arrow_x.wma";
	    String path1 = "/storage/sdcard0/TestSounds/bicycle_bell.wma";
	    String path3 = "/storage/sdcard0/TestSounds/bad_disk_x.wma";
	    addPlaylistToNotifications("test playlist");
	    addNotificationSound(0,Uri.parse("file://"+path1));
	    addNotificationSound(0,Uri.parse("file://"+path2));
		addNotificationSound(0,Uri.parse("file://"+path3));
		setCurrentNotificationPlaylist(0);
		notificationsOn();
		playlist_activity = null;
	}
	
	public void saveSettings(String jsonstring){
//		 settings = gson.fromJson(jsonstring, Settings.class);
	}
	
	// Playlists

	public void setCurrentNotificationPlaylist(int index){
		settings.setCurrentNotificationPlaylist(index);
		if (settings.getMainSwitch() && settings.getNotificationsSwitch()){
			if (settings.getNotificationPlaylist().size()!=0){
				removeCurrentNotifications();
				addCurrentNotifications();
				ArrayList<String> sounds = settings.getCurrentNotifications();
				String json = gson.toJson(sounds);
				prefs.edit().putInt("NotificationMode", settings.getNotificationMode());
				prefs.edit().putString("Notifications", json).apply();
			}else{
    			Toast.makeText(this, "Selected playlist is empty", Toast.LENGTH_LONG).show();
			}
		}
	}
	// possibly not needed
	public void update(){
		setCurrentNotificationPlaylist(settings.getCurrentNotificationPlaylist().Int);
		setCurrentRingtonePlaylist(settings.getCurrentRingtonePlaylist().Int);
	}
	
	public void setCurrentRingtonePlaylist(int index){
		settings.setCurrentRingtonePlaylist(index);
		if (settings.getMainSwitch() && settings.getRingtonesSwitch()){
			if (settings.getNotificationPlaylist().size()!=0){
				removeCurrentRingtones();
				addCurrentRingtones();
				ArrayList<String> sounds = settings.getCurrentNotifications();
				String json = gson.toJson(sounds);
				prefs.edit().putInt("NotificationMode", settings.getNotificationMode());
				prefs.edit().putString("Ringtones", json).apply();
			}else{
    			Toast.makeText(this, "Selected playlist is empty", Toast.LENGTH_LONG).show();
			}
		}
	}
	
	// switches
	public void notificationsOn(){
		settings.setNotificationsSwitch(true);
		removeCurrentNotifications();
		addCurrentNotifications();
		prefs.edit().putBoolean("NotificationsSwitch", true).apply();
	}
	public void notificationsOff(){
		settings.setNotificationsSwitch(false);
		removeCurrentNotifications();
		prefs.edit().putBoolean("NotificationsSwitch",false).apply();
	}
	public void ringtonesOn(){
		settings.setRingtonesSwitch(true);
		removeCurrentRingtones();
		addCurrentRingtones();
		prefs.edit().putBoolean("RingtonesSwitch", true).apply();
	}
	public void ringtonesOff(){
		settings.setRingtonesSwitch(false);
		removeCurrentRingtones();
		prefs.edit().putBoolean("RingtonesSwitch", false).apply();
	}
	public void mainOn(){
		settings.setMainSwitch(true);
	}
	public void mainOff(){
		settings.setMainSwitch(false);
		removeCurrentNotifications();
		removeCurrentRingtones();
		prefs.edit().putBoolean("NotificationsSwitch", false).apply();
		prefs.edit().putBoolean("RingtonesSwitch", false).apply();
	}
	public Boolean getNotificationsSwitch(){
		return settings.getNotificationsSwitch();
	}
	public Boolean getRingtonesSwitch(){
		return settings.getRingtonesSwitch();
	}
	public Boolean getMainSwitch(){
		return settings.getMainSwitch();
	}
	
	// service methods
	
	public void addCurrentNotifications(){
		File file;
		String path, name, extension;
		Uri mediaUri, newUri;
		ContentValues values = new ContentValues();
		for(Uri uri : settings.getNotificationPlaylist()){
			file = new File(uri.getPath());
			path = file.getAbsolutePath();
			name = file.getName().substring(0,file.getName().lastIndexOf('.'));
			extension = file.getName().substring(file.getName().lastIndexOf('.')+1);
			values.put(MediaStore.MediaColumns.DATA, path);
			values.put(MediaStore.MediaColumns.TITLE, name);
			values.put(MediaStore.MediaColumns.MIME_TYPE,"audio/"+extension);
			values.put(MediaStore.Audio.Media.IS_RINGTONE, false);
			values.put(MediaStore.Audio.Media.IS_NOTIFICATION, true);
			values.put(MediaStore.Audio.Media.IS_ALARM, false);
			values.put(MediaStore.Audio.Media.IS_MUSIC,false);
			
			mediaUri = MediaStore.Audio.Media.getContentUriForPath(path);
			getContentResolver().delete(mediaUri,MediaStore.MediaColumns.DATA + "=\"" + path + "\"", null);
			newUri = getContentResolver().insert(mediaUri, values);
			settings.addCurrentNotification(newUri);
			Log.d("Added to current",newUri+"");
		}
		prefs.edit().putInt("android.provider.Telephony.SMS_RECEIVED", -1).apply();
		RingtoneManager.setActualDefaultRingtoneUri(this, RingtoneManager.TYPE_NOTIFICATION, Uri.parse(settings.getCurrentNotifications().get(0)));
	}
	
	public void removeCurrentNotifications(){
		for(String path : settings.getCurrentNotifications()){
			getContentResolver().delete(Uri.parse(path), MediaStore.MediaColumns.DATA + "=\"" + path + "\"", null);
		}
		settings.clearCurrentNotifications();
	}
	
	public void addCurrentRingtones(){
		File file;
		String name, extension;
		Uri mediaUri, newUri;
		ContentValues values = new ContentValues();
		for(String path : settings.getCurrentRingtones()){
			file = new File(path);
			path = file.getAbsolutePath();
			name = file.getName().substring(0,file.getName().lastIndexOf('.'));
			extension = file.getName().substring(file.getName().lastIndexOf('.')+1);
			values.put(MediaStore.MediaColumns.DATA, path);
			values.put(MediaStore.MediaColumns.TITLE, name);
			values.put(MediaStore.MediaColumns.MIME_TYPE,"audio/"+extension);
			values.put(MediaStore.Audio.Media.IS_RINGTONE, true);
			values.put(MediaStore.Audio.Media.IS_NOTIFICATION, false);
			values.put(MediaStore.Audio.Media.IS_ALARM, false);
			values.put(MediaStore.Audio.Media.IS_MUSIC,false);
			
			mediaUri = MediaStore.Audio.Media.getContentUriForPath(path);
			getContentResolver().delete(Uri.parse(path),MediaStore.MediaColumns.DATA + "=\"" + path + "\"", null);
			newUri = getContentResolver().insert(mediaUri, values);
			settings.addCurrentRingtone(newUri);
		}
		prefs.edit().putInt("android.provider.Telephony.PHONE_STATE", -1);
		RingtoneManager.setActualDefaultRingtoneUri(this, RingtoneManager.TYPE_RINGTONE, Uri.parse(settings.getCurrentRingtones().get(0)));
	}
	
	public void removeCurrentRingtones(){
		for(String path : settings.getCurrentRingtones()){
			getContentResolver().delete(Uri.parse(path), MediaStore.MediaColumns.DATA + "=\"" + path + "\"", null);
		}
		settings.clearCurrentRingtones();
	}
	
	// setters
	
	public void addNotificationSound(int index, Uri newUri){
		settings.addNotificationSound(index, newUri);
	}
	
	public void addRingtoneSound(int index, Uri newUri){
		settings.addRingtoneSound(index, newUri);
	}
	
	public void addPlaylistToNotifications(String name){

		settings.addPlaylistToNotifications(name);
	}
	
	public void addPlaylistToRingtones(String name){
		settings.addPlaylistToRingtones(name);
	}
	
	public void setNotificationMode(int index, int mode){
		if (settings.getCurrentNotificationPlaylist().Int == index){
			prefs.edit().putInt("NotificationMode", mode);
		}
		settings.getNotificationPlaylist(index).setMode(mode);
	}
	public void setRingtoneMode(int index, int mode){
		if (settings.getCurrentRingtonePlaylist().Int == index){
			prefs.edit().putInt("RingtoneMode", mode);
		}
		settings.getRingtonePlaylist(index).setMode(mode);
	}
	
	// getters
	
	public ArrayList<Playlist> getNotifications(){
		return settings.getNotifications();
	}
	
	public ArrayList<Playlist> getRingtones(){
		return settings.getRingtones();
	}
	
	public Playlist getNotificationPlaylist(int position){
		return settings.getNotificationPlaylist(position);
	}
	
	public Playlist getRingtonePlaylist(int position){
		return settings.getRingtonePlaylist(position);
	}
	
	public Int getCurrentNotificationPlaylist(){
		return settings.getCurrentNotificationPlaylist();
	}
	
	public Int getCurrentRingtonePlaylist(){
		return settings.getCurrentRingtonePlaylist();
	}
	
	// extras / testing

	public void appendUri(Uri uri) {
		//settings.addOther(uri);
	}
	
	public void printOther(){
		//settings.printOther();
	}
	
	public ArrayList<Uri> getOther(){
		
		//return settings.getOther();
		return new ArrayList<Uri>();
	}
	
	public void printSounds(int index){
		settings.printSounds(index);
	}
	
	public void savePlaylistActivity(PlaylistActivity playlist_activity){
		this.playlist_activity = playlist_activity;
	}
	
	public void saveSoundActivity(SoundActivity sound_activity){
		this.sound_activity = sound_activity;
	}
	
	public void playlistActivityDelete(){
		if(playlist_activity != null){
			playlist_activity.listUpdate();
		}
	}
	
	public void soundActivityDelete(){
		if(sound_activity != null){
			sound_activity.listUpdate();
		}
	}
}
