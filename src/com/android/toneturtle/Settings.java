package com.android.toneturtle;

import java.io.Serializable;
import java.util.ArrayList;

import android.net.Uri;
import android.util.Log;

public class Settings implements Serializable{
	private static final long serialVersionUID = 1L;
	private ArrayList<Playlist> notification_playlists;
	private ArrayList<Playlist> ringtone_playlists;
	
	private ArrayList<String> current_notifications;
	private ArrayList<String> current_ringtones;

	private Int current_ringtone_playlist;
	private Int current_notification_playlist;
	private Boolean main_switch, notifications_switch, ringtones_switch;
	
	public Settings(){
		notification_playlists = new ArrayList<Playlist>();
		ringtone_playlists = new ArrayList<Playlist>();
		current_notifications = new ArrayList<String>();
		current_ringtones = new ArrayList<String>();
		current_notifications = new ArrayList<String>();
		current_ringtones = new ArrayList<String>();

		current_ringtone_playlist = new Int(-1);
		current_notification_playlist = new Int(-1);
		main_switch = true;
		notifications_switch = true;
		ringtones_switch = true;
	}
	
	// -----------------------------------------------SETTERS------------------------------------------//
	
	public void addPlaylistToNotifications(String name){
		notification_playlists.add(new Playlist(name));
		Log.d("playlist","added");
	}
	
	public void addPlaylistToRingtones(String name){
		ringtone_playlists.add(new Playlist(name));
	}
	
	public void addNotificationSound(int index, Uri newUri){
		notification_playlists.get(index).addSound(newUri);
		}
	
	public void addRingtoneSound(int index, Uri newUri){
		ringtone_playlists.get(index).addSound(newUri);
	}
	
	public void setCurrentNotificationPlaylist(int index){
		current_notification_playlist.Int = index;
	}
	
	public void setCurrentRingtonePlaylist(int index){
		current_ringtone_playlist.Int = index;
	}
	
	public void addCurrentNotification(Uri newUri){
		current_notifications.add(newUri+"");
	}
	
	public void addCurrentRingtone(Uri newUri){
		current_ringtones.add(newUri+"");
	}
	public void clearCurrentNotifications(){
		current_notifications.clear();
	}
	public void clearCurrentRingtones(){
		current_ringtones.clear();
	}
	public void setCurrentMode(){
		
	}
	public void setNotificationMode(int index, int mode){
		notification_playlists.get(index).setMode(mode);
	}
	public void setRingtoneMode(int index, int mode){
		ringtone_playlists.get(index).setMode(mode);
	}
	
	
	//------------------------------------------SWITCH SETTERS------------------------------------------//
	
	public void setRingtonesSwitch(Boolean ringtones_switch) {
		this.ringtones_switch = ringtones_switch;
	}
	
	public void setNotificationsSwitch(Boolean notifications_switch) {
		this.notifications_switch = notifications_switch;
	}
	
	public void setMainSwitch(Boolean main_switch) {
		this.main_switch = main_switch;
	}
	
	
	
	//----------------------------------------GETTERS----------------------------------------------//
	
	public ArrayList<Playlist> getNotifications(){
		return notification_playlists;
	}
	
	public ArrayList<Playlist> getRingtones(){
		return ringtone_playlists;
	}
	
	public ArrayList<String> getCurrentNotifications(){
		return current_notifications;
	}
	
	public ArrayList<String> getCurrentRingtones(){
		return current_ringtones;
	}
	
	public Playlist getNotificationPlaylist(int position){
		return notification_playlists.get(position);
	}
	
	public ArrayList<Uri> getNotificationPlaylist(){
		return notification_playlists.get(current_notification_playlist.Int).getSounds();
	}
	
	public Playlist getRingtonePlaylist(int position){
		return ringtone_playlists.get(position);
	}
	
	public ArrayList<Uri> getRingtonePlaylist(){
		return ringtone_playlists.get(current_ringtone_playlist.Int).getSounds();
	}
	
	public int getNotificationMode(int index){
		return ringtone_playlists.get(index).getMode();
	}
	
	public int getNotificationMode(){
		return notification_playlists.get(current_notification_playlist.Int).getMode();
	}
	
	public int getRingtoneMode(int index){
		return ringtone_playlists.get(index).getMode();
	}
	
	public int getRingtoneMode(){
		return ringtone_playlists.get(current_ringtone_playlist.Int).getMode();
	}
	
	//random
	
	public void printSounds(int index){
		notification_playlists.get(index).printSounds();
	}
	
	
	
	//------------------------------------------SWITCH GETTERS------------------------------------//
	
	public Boolean getMainSwitch() {
		return main_switch;
	}

	public Boolean getNotificationsSwitch() {
		return notifications_switch;
	}

	public Boolean getRingtonesSwitch() {
		return ringtones_switch;
	}
	
	
	
	//--------------------------RAVI TESTING METHODS-----------------------------------------------//
	public Int getCurrentNotificationPlaylist() {
		return current_notification_playlist;
	}

	public Int getCurrentRingtonePlaylist() {
		return current_ringtone_playlist;
	}
}