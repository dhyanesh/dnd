package com.android.dnd;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Date;

public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private final int MY_PERMISSIONS_REQUEST_READ_SMS_STATE = 1;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerViewAdapter;
    private ArrayList<SmsDetails> smsList = new ArrayList<SmsDetails>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        recyclerView = (RecyclerView) findViewById(R.id.sms_recycler_view);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerViewAdapter = new SmsRecyclerViewAdapter(smsList);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
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

        if (id == R.id.nav_camera) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS)
                    != PackageManager.PERMISSION_GRANTED) {
                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_SMS)) {
                    // TextView textView = (TextView) findViewById(R.id.main_text_view);
                    // textView.setText("Please allow reading SMS permissions.");
                } else {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.READ_SMS},
                            MY_PERMISSIONS_REQUEST_READ_SMS_STATE);
                }
            } else {
                displaySMS();
            }
        } else if (id == R.id.nav_gallery) {
            // TextView textView = (TextView) findViewById(R.id.main_text_view);
            // textView.setText("Gallery.");
        } else if (id == R.id.nav_slideshow) {
            // TextView textView = (TextView) findViewById(R.id.main_text_view);
            // textView.setText("Slideshow.");
        } else if (id == R.id.nav_manage) {
            // TextView textView = (TextView) findViewById(R.id.main_text_view);
            // textView.setText("Manage.");
        } else if (id == R.id.nav_share) {
            // TextView textView = (TextView) findViewById(R.id.main_text_view);
            // textView.setText("Share.");
        } else if (id == R.id.nav_send) {
            // TextView textView = (TextView) findViewById(R.id.main_text_view);
            // textView.setText("Send.");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_SMS_STATE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    displaySMS();
                } else {
                    // TextView textView = (TextView) findViewById(R.id.main_text_view);
                    // textView.setText("Unable to read SMS.");
                }
                return;
            }
        }
    }

    public void displaySMS() {
        // Create Inbox box URI
        Uri inboxURI = Uri.parse("content://sms/inbox");

        // List required columns
        String[] reqCols = new String[]{"date", "address", "body"};

        // Get Content Resolver object, which will deal with Content Provider
        ContentResolver contentResolver = getContentResolver();

        // Fetch Inbox SMS Message from Built-in Content Provider
        Cursor cursor = contentResolver.query(inboxURI, reqCols, null, null, null);
        int addressIndex = cursor.getColumnIndex("address");
        int bodyIndex = cursor.getColumnIndex("body");
        int dateIndex = cursor.getColumnIndex("date");
        if (cursor.moveToFirst()) { // must check the result to prevent exception
            int limit = 10;
            do {
                SmsDetails sms = new SmsDetails();
                sms.setAddress(cursor.getString(addressIndex));
                sms.setBody(cursor.getString(bodyIndex));
                String dateStr = cursor.getString(dateIndex);
                Date smsDate = new Date(Long.parseLong(dateStr));
                sms.setDate(smsDate);
                smsList.add(sms);
                --limit;
            } while (cursor.moveToNext() && limit > 0);
            recyclerViewAdapter.notifyDataSetChanged();
            cursor.close();
        } else {
        }
    }
}
