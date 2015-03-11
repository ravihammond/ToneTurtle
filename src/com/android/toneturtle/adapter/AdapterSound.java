package com.android.toneturtle.adapter;

import java.util.ArrayList;

import com.android.toneturtle.Playlist;
import com.android.toneturtle.R;
import com.android.toneturtle.R.id;
import com.android.toneturtle.R.layout;
import com.devahead.extendingandroidapplication.ToneApplication;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class AdapterSound extends BaseAdapter{
	private Playlist playlist;
	private Context context;
	private LayoutInflater inflater;
	private ToneApplication app;
	
	public AdapterSound(Context context, Playlist playlist){
		app = (ToneApplication)context.getApplicationContext();
		this.context = context;
		this.playlist = playlist;
		inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public int getCount() {
		// return the number of records in cursor
	    return playlist.getSounds().size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View view = convertView;  
        final CompleteListViewHolder holder;  
        if (convertView == null){  
             LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);  
             view = inflater.inflate(R.layout.sound_items, null);  
             holder = new CompleteListViewHolder(view);  
             view.setTag(holder);  
        } else {  
             holder = (CompleteListViewHolder) view.getTag();  
        }  
        holder.name.setText(playlist.getSoundName(position));
        holder.delete.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				playlist.getSounds().remove(position);
				app.soundActivityDelete();
				notifyDataSetChanged();
			}	
		});
        return view; 
	}	
	class CompleteListViewHolder {  
		public TextView name;  
		public Button delete;
	    public CompleteListViewHolder(View base) {  
	    	name = (TextView) base.findViewById(R.id.text_view);  
	    	delete = (Button) base.findViewById(R.id.delete);
	    }  
	}  
}
		

