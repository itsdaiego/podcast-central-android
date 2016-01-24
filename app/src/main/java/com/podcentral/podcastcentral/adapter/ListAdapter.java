package com.podcentral.podcastcentral.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.podcentral.podcastcentral.bean.Podcast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dyego on 12/24/15.
 */
public class ListAdapter extends Activity {
    public ListAdapter(Context context, int layout, List list){

    }
    private List<Podcast> listPodcasts = new ArrayList<Podcast>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
