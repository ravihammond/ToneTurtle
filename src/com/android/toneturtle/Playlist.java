package com.android.toneturtle;

import java.io.File;
import java.util.ArrayList;

import android.net.Uri;
import android.util.Log;

public class Playlist{
	
	
	private String name;
	private ArrayList<Uri> sounds;
	private int mode;
	private boolean selected;
	private int reset_time; // seconds
	
	
	public static final int ShuffleMode = 0;
	public static final int LoopMode = 1;
	public static final int LoopResetMode = 2;
	
	public Playlist(String name){
		this.name = name;
		sounds = new ArrayList<Uri>();
//		testing
		mode = LoopResetMode;
	}
	
	public boolean getSelected(){
		return selected;
	}
	
	public String getName(){
		return name;
	}
	
	public void addSound(Uri uri){
		sounds.add(uri);
	}
	
	public String getSoundName(int index){
		File f = new File("" + sounds.get(index));
		return f.getName().substring(0,f.getName().lastIndexOf('.'));
	}
	
	public int getMode(){
		return mode;
	}
	
	public void moveSound(int from, int to){
		char up; 
		if (from<to)
			up = 1;
		else
			up = 0;
		
		Uri tempUri = sounds.get(from);
		sounds.remove(from);
		sounds.add(to-up,tempUri);
	}
	
	public void removeSound(int index){
		sounds.remove(index);
	}
	
	public ArrayList<Uri> getSounds(){
		return sounds;
	}
	
	// TESTING METHOD //
	public void printSounds(){
		String name;
		File f;
		Log.d("test","printing sounds:");
		for(int i = 0; i < sounds.size() ; i++){
			f = new File("" + sounds.get(i));
			name = f.getName().substring(0,f.getName().lastIndexOf('.'));
			Log.d(""+i,name);
		}
	}
	public void setMode(int newMode){
		mode = newMode;
	}
}

/*
 method with sorting! (probably works?)
	public void addSound(Uri newUri){
		File f = new File("" + newUri);
		String name, newName = f.getName().substring(0,f.getName().lastIndexOf('.'));
		for(Uri uri : sounds){
			if(newUri.equals(uri)){
				return;
			}
		for(Uri uri2 : sounds){
			f = new File("" + uri2);
			name = f.getName().substring(0,f.getName().lastIndexOf('.'));
			if(name.compareToIgnoreCase(newName)<0){
				newName = f.getName().substring(0,f.getName().lastIndexOf('.'));
				sounds.add(sounds.indexOf(uri2),newUri);
				return;
			}
		}
		sounds.add(newUri);
		}
	}
	
	*/
