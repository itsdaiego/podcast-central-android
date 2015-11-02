package com.podcentral.podcastcentral;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import com.podcentral.podcastcentral.utils.*;
import com.podcentral.podcastcentral.utils.interfaces.AppConstants;

public class MainActivity extends AppCompatActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private Toolbar toolbar;
    JSONObject user;
    private TextView username, email;
    private ImageView userImage;

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
    @Override
    public void onNavigationDrawerItemSelected(int position) {
        Intent intent;
        switch (position){
            case 0:
                Log.i("Activity called: ", "user");

                break;
            case 1:
                Log.i("Status: ", "Podcast");
                break;
            case 2:
                //TODO: Create Community activity
                Log.i("Status: ", "Community");
                break;
        }
        /*FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.podcast, PodcastFragment.newInstance(position + 1))
                .commit();*/
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
            ApiUtility apiUtility = new ApiUtility();

            JSONObject json = apiUtility.getUserJSON(AppConstants.JSON_URL, AppConstants.USER_ID, 1);
            return json;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            pDialog.dismiss();
            try {
                ImageUtility imageUtility = new ImageUtility();

                String name = jsonObject.getString("name");
                String userEmail = jsonObject.getString("email");
                String image = jsonObject.getString("image64");

                userImage.setImageBitmap(imageUtility.getDecodedBase64Image(image));
                username.setText("Username: " + name);
                email.setText("Email: " + userEmail);



            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void initialize(){
        username = (TextView) findViewById(R.id.username);
        email = (TextView) findViewById(R.id.email);
        userImage = (ImageView) findViewById(R.id.user_image);
        user  = null;
    }
}
