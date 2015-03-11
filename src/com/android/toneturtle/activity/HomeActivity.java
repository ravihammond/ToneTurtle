package com.android.toneturtle.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;

import com.android.toneturtle.R;
import com.devahead.extendingandroidapplication.ToneApplication;

public class HomeActivity extends Activity{
	ToneApplication app;
	private final int NOTIFICATION_REQUEST_CODE = 234532;
	private final int RINGTONE_REQUEST_CODE = 403943;
	public Boolean on;
	
	private Button notification_button;
	private Button ringtone_button;
	private Switch main_switch;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) { 
		super.onCreate(savedInstanceState);
		app = (ToneApplication)getApplication();
		setContentView(R.layout.activity_home);
		setListeners();
	}
	
	private void setListeners(){
		main_switch = (Switch)findViewById(R.id.main_switch);
		main_switch.setChecked(app.getMainSwitch());
		main_switch.setOnCheckedChangeListener(new OnCheckedChangeListener() {
		    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		    	if(isChecked){
		    		app.mainOn();
		    	}else{
		    		app.mainOff();
		    	}
		    }
		});
		notification_button = (Button)findViewById(R.id.notification_home_button);
		notification_button.setOnClickListener(new View.OnClickListener(){
		    public void onClick(View view) {
		    	startNotificationsActivity();
			}
		});
		ringtone_button = (Button)findViewById(R.id.ringtones_home_button);
		ringtone_button.setOnClickListener(new View.OnClickListener(){
		    public void onClick(View view) {
		    	startRingtonesActivity();
			}
		});
	}
	
	protected void onResume(){
		super.onResume();
		main_switch.setChecked(app.getMainSwitch());
	}
	
	private void startNotificationsActivity(){
		Intent intent = new Intent(getBaseContext(),PlaylistNotificationActivity.class);
		startActivityForResult(intent, NOTIFICATION_REQUEST_CODE);		
		this.overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);		
	}
	
	private void startRingtonesActivity(){
		Intent intent = new Intent(getBaseContext(),PlaylistRingtoneActivity.class);
		startActivityForResult(intent, RINGTONE_REQUEST_CODE);
		this.overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == NOTIFICATION_REQUEST_CODE && resultCode == RESULT_OK){
			startRingtonesActivity();
		}
		if(requestCode == RINGTONE_REQUEST_CODE && resultCode == RESULT_OK){
			startNotificationsActivity();
		}
    }
}