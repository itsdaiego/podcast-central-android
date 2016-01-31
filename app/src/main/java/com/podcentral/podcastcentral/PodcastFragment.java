package com.podcentral.podcastcentral;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.podcentral.podcastcentral.utils.ApiUtility;
import com.podcentral.podcastcentral.utils.CustomAdapter;
import com.podcentral.podcastcentral.utils.ImageUtility;
import com.podcentral.podcastcentral.utils.interfaces.AppConstants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dyego on 11/2/15.
 */
public class PodcastFragment extends Fragment {
    View rootView;
    ListView lv;
    ArrayList list;
    Activity currentActivity =  null;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_podcast, container, false);
         lv = (ListView)rootView.findViewById(R.id.list_podcasts);

        new PodcastApiUtility().execute();
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        currentActivity = activity;
        super.onAttach(activity);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private class PodcastApiUtility extends AsyncTask<String, String, JSONArray> {
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage(getString(R.string.podcast_loading_dialog));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected JSONArray doInBackground(String... params) {
            ApiUtility apiUtility = new ApiUtility();

            JSONArray json = apiUtility.getArrayJSON(AppConstants.PODCAST_JSON_URL);
            return json;
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            super.onPostExecute(jsonArray);
            pDialog.dismiss();
            try {

               list = new ArrayList();
                for(int i =0; i< jsonArray.length(); i++){
                    list.add(jsonArray.get(i));
                }
                CustomAdapter adapter = new CustomAdapter(getActivity(), R.layout.podcastlist, R.id.podcast_image, R.id.podcast_name, list);
                lv.setAdapter(adapter);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(currentActivity, PodcastDetailsActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("PODCAST_ID", position);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
