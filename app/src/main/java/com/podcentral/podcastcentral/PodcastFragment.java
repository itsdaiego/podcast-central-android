package com.podcentral.podcastcentral;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.podcentral.podcastcentral.utils.ApiUtility;
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
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_podcast, container, false);

        new PodcastApiUtility().execute();
        return rootView;
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
                ImageUtility imageUtility = new ImageUtility();

                ArrayList list = new ArrayList();
                for(int i =0; i< jsonArray.length(); i++){
                    list.add(jsonArray.get(i));
                }
                ArrayAdapter<List> adapter = new ArrayAdapter<List>(getActivity(), R.layout.podcastlist, R.id.podcast_name, list);
                ListView lv = (ListView) rootView.findViewById(android.R.id.list);
                lv.setAdapter(adapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
