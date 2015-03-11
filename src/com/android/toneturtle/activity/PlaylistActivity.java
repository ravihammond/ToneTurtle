package com.android.toneturtle.activity;


import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Switch;

import com.android.toneturtle.Int;
import com.android.toneturtle.Playlist;
import com.android.toneturtle.R;
import com.android.toneturtle.adapter.AdapterPlaylist;
import com.devahead.extendingandroidapplication.ToneApplication;

abstract public class PlaylistActivity extends Activity {
	protected ToneApplication app;
	protected final int NAME_REQUEST_CODE = 273643;
	
	protected ArrayList<Playlist> playlists;
	protected ListView list_view;
	protected AdapterPlaylist adapter;
	protected Int selected;
	
	protected ActionBar actionBar;
	protected ImageButton new_playlist_button;
	protected View footer_view;
	protected Switch playlist_switch;
	
	abstract protected void setData();
	abstract protected void addPlaylist(String name);
	abstract protected void passSoundActivity(int position);
	abstract protected void setSwitchListener();
	abstract protected void switchVisibility();
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		app = (ToneApplication)getApplication();
		app.savePlaylistActivity(this);
	}
	
	protected void onResume(){
		super.onResume();
		app.savePlaylistActivity(this);
		if(playlists.size()>0)
			list_view.removeFooterView(footer_view);
		adapter.notifyDataSetChanged();
		switchVisibility();
	}
	
	public void onBackPressed(){
	    super.onBackPressed();
	    app.savePlaylistActivity(null);
	    finish();
	    overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) { 
	    switch (item.getItemId()) {
	        case android.R.id.home:
	        	app.savePlaylistActivity(null);
	        	finish();
	            this.overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
	            return true;
	        default:
	            return super.onOptionsItemSelected(item); 
	    }
	}
	
	protected void setListeners(){
		new_playlist_button.setOnClickListener(new View.OnClickListener(){
		    public void onClick(View view) {
		    		startNameChooserActivity();
				}
		});
		list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view_clicked, int position, long id) {
				Log.d("test","activated");
				int view_id = view_clicked.getId();
				if(view_id==R.id.playlist_item){
					passSoundActivity(position);
				}else{
					startNameChooserActivity();
				}
				
			}
		});
	}
	
	protected void startSoundActivity(Intent intent){
		startActivity(intent);
		this.overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
	}
	
	protected void startNameChooserActivity(){
		Intent intent = new Intent(getBaseContext(),NameChooserActivity.class);
		startActivityForResult(intent, NAME_REQUEST_CODE);
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == NAME_REQUEST_CODE && resultCode == RESULT_OK){
        	this.overridePendingTransition(R.anim.no_animation,R.anim.push_up_out);
    		String returnedresult = data.getData().toString();
    		addPlaylist(returnedresult);
    		list_view.removeFooterView(footer_view);
    		adapter.notifyDataSetChanged();
        }
	}
	
	protected void populateListView(){
		list_view.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		footer_view = ((LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.playlist_footer, null, true);
		list_view.addFooterView(footer_view);
		list_view.setAdapter(adapter);
		adapter.notifyDataSetChanged(); 
	}
	
	public void listUpdate(){
		if(playlists.size()==0){
			list_view.addFooterView(footer_view);
			adapter.notifyDataSetChanged(); 
		}
	}
}
