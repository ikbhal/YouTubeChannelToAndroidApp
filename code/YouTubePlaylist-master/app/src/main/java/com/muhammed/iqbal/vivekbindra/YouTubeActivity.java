package com.muhammed.iqbal.vivekbindra;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.youtube.YouTube;
import com.muhammed.iqbal.vivekbindra.model.PlaylistVideos;

import java.util.HashMap;
import java.util.Map;
//import com.google.android.gms.ads.MobileAds;
//import com.google.android.gms.ads.AdRequest;
//import com.google.android.gms.ads.AdView;

/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class YouTubeActivity extends AppCompatActivity {
    private static final String[] YOUTUBE_PLAYLISTS = {
            // vivek bindra one play  list
            "PLOxBmXq4mdMPoQucD1Nqn7ap5KGqqAJ0M", //motivational videos
            "PLOxBmXq4mdMMPhAp3yFbTHpmt_YtqSgoV", //leadership videos
            "PLOxBmXq4mdMO5gaoK8vUpL3d2PDSIbFPX", // double your sales
            "PLOxBmXq4mdMOK31-vLM6tOWFJyjSrdVXE", // motivational 2 videos
            "PLOxBmXq4mdMMiJ9iwSCkdii-qzH-OAflA", // team building
            "PLOxBmXq4mdMPyTWsnc1MXuE_JJsJAQ87H", // inspirational videos
            "PLOxBmXq4mdMPdPa-8-yLOYkbyDOH2NlPr", //sales training
            "PLOxBmXq4mdMPTXYv6vfbdMGS2V6dHRYMf", // managerial effectiveness
            "PLOxBmXq4mdMNbRWUxO32Ge188bdQSMlNR", // Management principles
            "PLOxBmXq4mdMPtPgeO0MxzznHzM9juoB1x" // Time Management Videos
    };
    int[] NAV_ITEM_IDS = {R.id.motivational_videos, R.id.leadership_videos, R.id.double_your_sales,
            R.id.motivational_2_videos, R.id.team_building, R.id.inspirational_videos,
            R.id.sales_training, R.id.managerial_effectiveness, R.id.management_principles,
            R.id.time_management_videos};
    Map<Integer,String> navItemToPlayListIdMap = new HashMap<Integer, String>();

    private YouTube mYoutubeDataApi;
    private final GsonFactory mJsonFactory = new GsonFactory();
    private final HttpTransport mTransport = AndroidHttp.newCompatibleTransport();
    //private AdView mAdView;

    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    private YouTubeRecyclerViewFragment youTubeRecyclerViewFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.youtube_activity);

        // map navigation item to play list id
        for(int i =0; i < NAV_ITEM_IDS.length; i++) {
            navItemToPlayListIdMap.put(NAV_ITEM_IDS[i], YOUTUBE_PLAYLISTS[i]);
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);

        dl = findViewById(R.id.youtube_activity);
        nv = findViewById(R.id.nv);
        nv.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        // reload the UI with the playlist video list of the selected playlist
                        String playListId  = navItemToPlayListIdMap.get(item.getItemId());
                        if(playListId != null) {
                            youTubeRecyclerViewFragment.mPlaylistVideos = new PlaylistVideos(playListId);
                            youTubeRecyclerViewFragment.reloadUi(youTubeRecyclerViewFragment.mPlaylistVideos, true);
                        }else {
                            Toast.makeText(YouTubeActivity.this, "Wrong navigation item clicked or need to implement yet", Toast.LENGTH_LONG).show();
                        }
                        dl.closeDrawers();
                        return true;
                    }
                }
        );
        if(!isConnected()){
            Toast.makeText(YouTubeActivity.this,"No Internet Connection Detected",Toast.LENGTH_LONG).show();
        }
        
        if (ApiKey.YOUTUBE_API_KEY.startsWith("YOUR_API_KEY")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setMessage("Edit ApiKey.java and replace \"YOUR_API_KEY\" with your Applications Browser API Key")
                        .setTitle("Missing API Key")
                        .setNeutralButton("Ok, I got it!", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        });

            AlertDialog dialog = builder.create();
            dialog.show();

        } else if (savedInstanceState == null) {
            mYoutubeDataApi = new YouTube.Builder(mTransport, mJsonFactory, null)
                    .setApplicationName(getResources().getString(R.string.app_name))
                    .build();
            youTubeRecyclerViewFragment = YouTubeRecyclerViewFragment.newInstance(mYoutubeDataApi, YOUTUBE_PLAYLISTS);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, youTubeRecyclerViewFragment)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(com.muhammed.iqbal.vivekbindra.R.menu.main_menu, menu);
        return true;
    }
    
    public boolean isConnected() {
        ConnectivityManager cm =
            (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch(item.getItemId()) {
            case  R.id.action_recyclerview :
                youTubeRecyclerViewFragment = YouTubeRecyclerViewFragment.newInstance(mYoutubeDataApi, YOUTUBE_PLAYLISTS);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, youTubeRecyclerViewFragment)
                        .commit();
                return true;
            case android.R.id.home:
                dl.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
