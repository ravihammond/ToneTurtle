package com.android.toneturtle.adapter;

import java.util.ArrayList;

import com.android.toneturtle.Int;
import com.android.toneturtle.Playlist;
import com.android.toneturtle.R;
import com.android.toneturtle.R.id;
import com.android.toneturtle.R.layout;
import com.devahead.extendingandroidapplication.ToneApplication;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class AdapterPlaylist extends BaseAdapter{
	private ArrayList<Playlist> playlists;
	private Context context;
	private LayoutInflater inflater;
	
	private int type;
	private Int selected;
	private ToneApplication app;
	
	public AdapterPlaylist(Context context, ArrayList<Playlist> playlists, Int selected, int type){
		app = (ToneApplication)context.getApplicationContext();
		this.context = context;
		this.playlists = playlists;
		this.selected = selected;
		this.type = type;
		inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public int getCount() {
		// return the number of records in cursor
	    return playlists.size();
	}

	@Override
	public Object getItem(int position) {
		return playlists.get(position);
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
             view = inflater.inflate(R.layout.playlist_items, null);  
             holder = new CompleteListViewHolder(view);  
             view.setTag(holder);  
        } else {  
             holder = (CompleteListViewHolder) view.getTag();  
        }  
        holder.name.setText(playlists.get(position).getName());
        if (selected.Int == position){
			holder.checkbox.setChecked(true); 
		}else {
			holder.checkbox.setChecked(false);
		}
        holder.checkbox.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(holder.checkbox.isChecked()){
					if(type==1){
						app.setCurrentRingtonePlaylist(position);
					}else if (type==2){
						app.setCurrentNotificationPlaylist(position);
					}
					notifyDataSetChanged();
				}else{
					holder.checkbox.setChecked(true);
				}
			}	
		}); 
        holder.delete.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				playlists.remove(position);
				app.playlistActivityDelete();
				if(position<selected.Int || (position==playlists.size() && position==selected.Int)){
					selected.Int--;
				}
				notifyDataSetChanged();
			}	
		}); 
        return view; 
	}	
	class CompleteListViewHolder {  
		public TextView name;  
		public CheckBox checkbox;
		public Button delete;
	    public CompleteListViewHolder(View base) {  
	    	name = (TextView) base.findViewById(R.id.text_view);  
	    	checkbox = (CheckBox) base.findViewById(R.id.checkbox);
	    	delete = (Button) base.findViewById(R.id.delete);
	    }  
	}  
}
		
