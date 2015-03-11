package com.android.toneturtle.activity;

import java.io.File;
import java.util.ArrayList;

import paul.arian.fileselector.FileSelectionActivity;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;

import com.android.toneturtle.Playlist;
import com.android.toneturtle.R;
import com.android.toneturtle.adapter.AdapterSound;
import com.devahead.extendingandroidapplication.ToneApplication;

abstract public class SoundActivity extends Activity{
	protected ToneApplication app;
	protected final int FILE_REQUEST_CODE = 382764;
	
	protected ArrayList<String> mode_list;
	protected Spinner mode_spinner;
	//protected AdapterModeSpinner mode_adapter;
	
	protected Playlist playlist;
	protected ListView list_view;
	protected AdapterSound sound_adapter;
	protected int position;
	
	protected Intent intent;
	protected ActionBar action_bar;
	protected Button shuffle_button;
	protected Button loop_button;
	protected Button loop_reset_button;
	protected ImageButton new_sound_button;
	protected View footer_view;
	
	abstract protected void setData();
	abstract protected void resetCurrentPlaylist();
	abstract protected void setOwnListeners();
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		app = (ToneApplication)getApplication();
		app.saveSoundActivity(this);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) { 
	    switch (item.getItemId()) {
	        case android.R.id.home:
	        	goBack();	            
	            return true;
	        default:
	            return super.onOptionsItemSelected(item); 
	    }
	}

	protected void goBack(){
        app.savePlaylistActivity(null);
	    finish();
        this.overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
	}
	
	protected void onResume(){
		super.onResume();
		app.saveSoundActivity(this);
		if(playlist.getSounds().size()>0)
			list_view.removeFooterView(footer_view);
		sound_adapter.notifyDataSetChanged();
	}
	
	public void onBackPressed(){
	    super.onBackPressed();
	    app.savePlaylistActivity(null);
	    finish();
	    overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
	}
	
	protected void setListeners() {
		new_sound_button =(ImageButton) findViewById(R.id.new_sound_button);
		new_sound_button.setOnClickListener(new View.OnClickListener(){
		    public void onClick(View view){
		    	startFileChooserActivity();
		    }
		});
		list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view_clicked, int position, long id) {
				int view_id = view_clicked.getId();
				if(view_id==R.id.sound_item){
				}else{
					startFileChooserActivity();
				}
			}
		});
	}
	
	public void startFileChooserActivity(){
		Intent intent = new Intent(getBaseContext(),FileSelectionActivity.class);
		startActivityForResult(intent, FILE_REQUEST_CODE);
		this.overridePendingTransition(R.anim.scale_from_corner,R.anim.no_animation);
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Uri uri;
		if(requestCode == FILE_REQUEST_CODE && resultCode == RESULT_OK){
			Log.d("working","working");
            @SuppressWarnings("unchecked")
			ArrayList<File> Files = (ArrayList<File>) data.getSerializableExtra("upload");
            for(File file : Files){
                String path = file.getAbsolutePath();
                uri = Uri.parse("file://"+path);
                playlist.addSound(uri);
                sound_adapter.notifyDataSetChanged(); 
            }
            resetCurrentPlaylist();
        }
    }
	
	protected void populateLists(){
		mode_list = new ArrayList<String>();
		mode_list.add("Shuffle"); mode_list.add("Loop"); mode_list.add("Reset"); 
		mode_spinner = (Spinner) findViewById(R.id.mode_spinner);
		ArrayAdapter<String> spinner_adapter = new ArrayAdapter<String>(this,
				R.layout.spinner_mode_items, mode_list);
		spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mode_spinner.setAdapter(spinner_adapter);
		
		footer_view = ((LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.n_sound_footer, null, true);
		list_view.addFooterView(footer_view);
		list_view.setAdapter(sound_adapter);
		sound_adapter.notifyDataSetChanged(); 
	}
	
	public void listUpdate(){
		if(playlist.getSounds().size()==0){
			list_view.addFooterView(footer_view);
			sound_adapter.notifyDataSetChanged();
		}
	}
}
