package com.android.toneturtle.activity;

import com.android.toneturtle.R;
import com.android.toneturtle.R.anim;
import com.android.toneturtle.R.id;
import com.android.toneturtle.R.layout;
import com.android.toneturtle.R.menu;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NameChooserActivity extends Activity {
	Button cancelbutton;
	Button createbutton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_playlist_name_chooser);
		this.overridePendingTransition(R.anim.push_up_in,0);
		setListeners();
	}

	private void setListeners() {
		cancelbutton =(Button) findViewById(R.id.button_cancel);
		cancelbutton.setOnClickListener(new View.OnClickListener(){
		    public void onClick(View view) {
		    		callFinish();
				}
		});
		createbutton =(Button) findViewById(R.id.button_create);
		createbutton.setOnClickListener(new View.OnClickListener(){
		    public void onClick(View view) {
		    		EditText textinput  = (EditText)findViewById(R.id.edit_playlist_name);
		    		String text = textinput.getText().toString();
		    		if (text.matches("")) {
		    			Toast toast= Toast.makeText(NameChooserActivity.this, "please enter a playlist name", Toast.LENGTH_SHORT);
		    			toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 100);
		    			toast.show();
		    		    return;
		    		}else{
		    			exitWithResult(text);
		    		}
				}
		});
	}
	private void exitWithResult(String text){
		Intent data = new Intent();
		data.setData(Uri.parse(text));
		setResult(RESULT_OK, data);
		callFinish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.playlist_name_chooser, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void callFinish(){
		finish();
		this.overridePendingTransition(R.anim.no_animation,R.anim.push_up_out);
	}
}
