package com.podcentral.podcastcentral;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import com.podcentral.podcastcentral.utils.*;
import com.podcentral.podcastcentral.utils.interfaces.AppConstants;

public class MainActivity extends AppCompatActivity implements AppConstants {

    private Toolbar toolbar;
    JSONObject user;
    private TextView username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initializing variables
        initialize();


        //Setting up the toolbar
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.statusBar));
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);


        //Setting up the navigation drawer
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);

        drawerFragment.setUp((DrawerLayout)findViewById(R.id.drawer_layout), toolbar);


        //Getting API data using JSON format
        new JsonUitlity().execute();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class JsonUitlity extends AsyncTask<String, String, JSONObject> {
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage(getString(R.string.loading_dialog));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected JSONObject doInBackground(String... params) {
            JsonUtility jsonUtility = new JsonUtility();

            JSONObject json = jsonUtility.getJSONFromUrl(AppConstants.JSON_URL, AppConstants.USER_ID, 5);
            return json;
    }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            pDialog.dismiss();
            try {
                String name = jsonObject.getString("name");

                username.setText("Welcome: " + name);


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    public void initialize(){
        username = (TextView) findViewById(R.id.username);
        user  = null;
    }
}
