package com.example.app01;

import android.content.Intent;
import android.os.Bundle;

import com.example.app01.appFragments.AlarmManagerFragment;
import com.example.app01.appFragments.BooksAsyncTaskLoaderFragment;
import com.example.app01.appFragments.BroadcastRecieverFragment;
import com.example.app01.appFragments.JobSchedulerFragment;
import com.example.app01.appFragments.NotificationsFragment;
import com.example.app01.appFragments.ScoreCounterFragment;
import com.example.app01.appFragments.SharedPreferencesFragment;
import com.example.app01.appFragments.SimpleAsyncTaskFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.Menu;

public class NavigationDrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static Fragment fragment = null;
    private FragmentManager fm;
    private FragmentTransaction ft;

    private static final String NOTIFICATION_FRAGMENT_INTENT_ID = "notificationFragmentIntentId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //NotificationApp: //11
        Intent in = getIntent();
        int intentInt = in.getIntExtra(NOTIFICATION_FRAGMENT_INTENT_ID, 0);
        if (intentInt == 3) {
            fragment = new NotificationsFragment();
        }//11 finished
        else if (intentInt == 1) {//AlarmManagerApp: //14
            fragment = new AlarmManagerFragment();//AlarmManagerApp: //14 ends.
        } else if (intentInt == 2) {//JobSchedulerApp: //05
            fragment = new JobSchedulerFragment();
        }//JobSchedulerApp: //05 ends go back to JobSchedulerFragment for //06

        if (fragment == null) {
                fragment = new ScoreCounterFragment();
        }

        //SharedPreferencesFragment: point //09
        // If turn the screen orientation then the savedInstanceState is not null.
        // In this condition, do not need to add new fragment again.
        if (savedInstanceState == null) {//SharedPreferencesFragment: point //09 ends go back.
            fm = getSupportFragmentManager();
            ft = fm.beginTransaction();
            ft.replace(R.id.id_navigation_drawer_frameLayout, fragment);
            ft.commit();
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_drawer, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.scoreCounterApp) {
            getSupportActionBar().setTitle(ScoreCounterFragment.class.getSimpleName());
            fragment = new ScoreCounterFragment();
        } else if (id == R.id.asyncTaskApp) {
            getSupportActionBar().setTitle(SimpleAsyncTaskFragment.class.getSimpleName());
            fragment = new SimpleAsyncTaskFragment();
        } else if (id == R.id.asyncTaskLoaderApp) {
            getSupportActionBar().setTitle(BooksAsyncTaskLoaderFragment.class.getSimpleName());
            fragment = new BooksAsyncTaskLoaderFragment();
        } else if (id == R.id.broadcastRecieverApp) {
            getSupportActionBar().setTitle(BroadcastRecieverFragment.class.getSimpleName());
            fragment = new BroadcastRecieverFragment();
        } else if (id == R.id.notificationsApp) {
            getSupportActionBar().setTitle(NotificationsFragment.class.getSimpleName());
            fragment = new NotificationsFragment();
        } else if (id == R.id.alarmManagerApp) {
            getSupportActionBar().setTitle(AlarmManagerFragment.class.getSimpleName());
            fragment = new AlarmManagerFragment();
        } else if (id == R.id.jobSchedulerApp) {
            getSupportActionBar().setTitle(JobSchedulerFragment.class.getSimpleName());
            fragment = new JobSchedulerFragment();
        } else if (id == R.id.sharedPreferenceApp) {//SharedPreferencesApp: point //02
            getSupportActionBar().setTitle(SharedPreferencesFragment.class.getSimpleName());
            fragment = new SharedPreferencesFragment();
            //SharedPreferencesApp: point //02 ends. go back to SharedPreferencesFragment
        }

        if(fragment != null){
            fm = getSupportFragmentManager();
            ft = fm.beginTransaction();
            ft.replace(R.id.id_navigation_drawer_frameLayout, fragment);
            ft.commit();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
