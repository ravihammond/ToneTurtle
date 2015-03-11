package com.android.toneturtle.activity;

import com.android.toneturtle.R;
import com.android.toneturtle.R.id;
import com.android.toneturtle.R.layout;
import com.android.toneturtle.adapter.AdapterSound;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class SoundRingtoneActivity extends SoundActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sound_notification);
	    setData();
	    populateLists();
	    setListeners();
	    setOwnListeners();
	}

	@Override
	protected void setData() {
		action_bar = getActionBar();
		action_bar.setDisplayHomeAsUpEnabled(true);
		action_bar.setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
		
		intent = getIntent();
		position = (int) intent.getIntExtra("position",-1);
		playlist = app.getRingtonePlaylist(position);
	    action_bar.setTitle(playlist.getName());

		list_view = (ListView) findViewById(R.id.n_sound_list_view);
		sound_adapter = new AdapterSound(this, playlist);
	}
	
	@Override
	protected void resetCurrentPlaylist() {
		if(position == app.getCurrentRingtonePlaylist().Int){
			app.removeCurrentRingtones();
			app.setCurrentRingtonePlaylist(position);
		}
	}

	@Override
	protected void setOwnListeners() {
		mode_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View view, int spinner_position, long id) {
            	app.setRingtoneMode(position, spinner_position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            	Log.d("spinner test","spinner nothing selected");
            }
        });
	}
}
