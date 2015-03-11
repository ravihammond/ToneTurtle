package com.android.toneturtle.activity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Switch;

import com.android.toneturtle.R;
import com.android.toneturtle.adapter.AdapterPlaylist;

public class PlaylistRingtoneActivity extends PlaylistActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ringtones);
		actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
		setData();
		setSwitchListener();
		setListeners();
		populateListView();
		switchVisibility();
	}
	
	@Override
	protected void setData(){
		selected = app.getCurrentRingtonePlaylist();
		new_playlist_button =(ImageButton) findViewById(R.id.new_button);
		playlists = app.getRingtones();
		list_view = (ListView) findViewById(R.id.list_view);
		adapter = new AdapterPlaylist(this, playlists, selected,1);
		playlist_switch = (Switch)findViewById(R.id.playlist_switch);
	}

	@Override
	protected void addPlaylist(String name) {
		app.addPlaylistToRingtones(name);
	}

	@Override
	protected void passSoundActivity(int position){
		Intent intent = new Intent(getBaseContext(),SoundRingtoneActivity.class);
		intent.putExtra("position", position);
		startSoundActivity(intent);
	}

	@Override
	protected void setSwitchListener(){
		playlist_switch.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view_clicked) {
		    	Log.d("test","im working?...");
		    	if(playlist_switch.isChecked()){
		    		Log.d("test","on works");
		    		app.ringtonesOn();
		    		app.mainOn();
		    	}else{
		    		app.ringtonesOff();
		    	}
			}
		});
	}

	@Override
	protected void switchVisibility() {
		if(app.getMainSwitch()){
			playlist_switch.setChecked(app.getRingtonesSwitch());
		}
	}
}
