package com.podcentral.podcastcentral.utils;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.podcentral.podcastcentral.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CustomAdapter extends ArrayAdapter {
    JSONArray jsonList = new JSONArray();
    public CustomAdapter(Context context, ArrayList items) {
        super(context, 0, items);
        for(int i=0; i < items.size(); i++){
            jsonList.put(items.get(i));
        }

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        // Get the data item for this position
        //User user = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.podcastlist, parent, false);
        }
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.podcast_name);
        // Populate the data into the template view using the data object
        try{
            JSONObject jsonObject = jsonList.getJSONObject(position);
            tvName.setText(jsonObject.getString("name"));

        }catch (JSONException e){
            e.printStackTrace();
        }

        // Return the completed view to render on screen
        return convertView;
    }
}