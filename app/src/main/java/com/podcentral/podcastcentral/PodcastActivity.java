package com.podcentral.podcastcentral;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by dyego on 11/2/15.
 */
public class PodcastActivity extends AppCompatActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_podcast);

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
    }
    @Override
    public void onNavigationDrawerItemSelected(int position) {
        Intent intent;
        switch (position){
            case 0:
                Log.i("Activity called: ", "user");
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case 1:
                Log.i("Status: ", "Podcast");
                intent = new Intent(this, PodcastActivity.class);
                startActivity(intent);
                break;
            case 2:
                //TODO: Create Community activity
                Log.i("Status: ", "Community");
                break;
        }
    }
}
