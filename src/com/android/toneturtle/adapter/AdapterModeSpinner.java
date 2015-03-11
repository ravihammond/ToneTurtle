package com.android.toneturtle.adapter;

import java.util.ArrayList;

import com.android.toneturtle.R;
import com.android.toneturtle.R.id;
import com.android.toneturtle.R.layout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AdapterModeSpinner extends BaseAdapter{
	private ArrayList<String> string_list;
	private Context context;
	private LayoutInflater inflater;
	
	public AdapterModeSpinner(Context context, ArrayList<String> string_list){
		this.context = context;
		this.string_list = string_list;
		inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public int getCount() {
		// return the number of records in cursor
	    return string_list.size();
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
             view = inflater.inflate(R.layout.spinner_mode_items, null);  
             holder = new CompleteListViewHolder(view);  
             view.setTag(holder);  
        } else {  
             holder = (CompleteListViewHolder) view.getTag();
        }  
        holder.text.setText(string_list.get(position));
        return view; 
	}	
	class CompleteListViewHolder {
		public TextView text;
	    public CompleteListViewHolder(View base) {
	    	text = (TextView) base.findViewById(R.id.mode);
	    }
	}
}
		

