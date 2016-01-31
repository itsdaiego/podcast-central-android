package com.podcentral.podcastcentral;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.TextView;

import com.podcentral.podcastcentral.utils.ApiUtility;
import com.podcentral.podcastcentral.utils.CustomAdapter;
import com.podcentral.podcastcentral.utils.interfaces.AppConstants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by dyego on 1/31/16.
 */
public class PodcastDetailsActivity extends AppCompatActivity implements  NavigationDrawerFragment.NavigationDrawerCallbacks {
    int podcast_id = 0;
    Toolbar toolbar = null;
    TextView podcast_name = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.podcast_details_activity);
        initialize();

        //Setting up the toolbar
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.statusBar));
        window.setTitle("Podcast Supimpa");
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        //Setting up the navigation drawer
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);

        drawerFragment.setUp((DrawerLayout) findViewById(R.id.drawer_layout), toolbar);

        Bundle bundle = getIntent().getExtras();

        podcast_id = bundle.getInt("PODCAST_ID");
        new PodcastApiUtility().execute();
    }
    @Override
    public void onNavigationDrawerItemSelected(int position) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        PodcastFragment podcastFragment;
        switch (position){
            case 0:
                getSupportFragmentManager().beginTransaction();
                fragmentManager.beginTransaction()
                        .remove(getSupportFragmentManager().findFragmentByTag("PodcastFragment")).commit();
                break;
            case 1:
                podcastFragment = new PodcastFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.main_layout, podcastFragment, "PodcastFragment")
                        .commit();
                break;
            case 2:
                break;
        }

    }
    private class PodcastApiUtility extends AsyncTask<String, String, JSONObject> {
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(PodcastDetailsActivity.this);
            pDialog.setMessage(getString(R.string.podcast_loading_dialog));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... params) {
            ApiUtility apiUtility = new ApiUtility();

            JSONObject json = apiUtility.getContentJSON(AppConstants.PODCAST_JSON_URL, AppConstants.PODCAST_ID, podcast_id+1);
            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            super.onPostExecute(json);
            pDialog.dismiss();
            try{
                podcast_name.setText(json.getString("name"));
            }catch(JSONException e){
                e.printStackTrace();
            }
        }

    }
    public void initialize(){
        podcast_name = (TextView) findViewById(R.id.podcast_name);
    }
}
