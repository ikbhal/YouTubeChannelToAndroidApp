package com.muhammed.iqbal.vivekbindra;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoContentDetails;
import com.google.api.services.youtube.model.VideoSnippet;
import com.google.api.services.youtube.model.VideoStatistics;
import com.muhammed.iqbal.vivekbindra.db.VideoViewModel;
import com.muhammed.iqbal.vivekbindra.utils.Utils;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class VideoActivity extends AppCompatActivity {
    private static final DecimalFormat sFormatter = new DecimalFormat("#,###,###");
    private Video video;
    private TextView mTitleText;
    private TextView mDescriptionText;
    private ImageView mThumbnailImage;
    private ImageView mShareIcon;
    private TextView mShareText;
    private TextView mDurationText;
    private TextView mViewCountText;
    private  TextView mLikeCountText;
    private  TextView mDislikeCountText;

    private VideoViewModel mVideoViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        setContentView(R.layout.activity_video);

        mVideoViewModel = ViewModelProviders.of(this).get(VideoViewModel.class);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(android.support.v7.appcompat.R.drawable.abc_ic_ab_back_material);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(VideoActivity.this, "back clicked in vidoe activity", Toast.LENGTH_LONG).show();
                finish();
            }
        });
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(VideoActivity.this, "fab is clicked", Toast.LENGTH_LONG).show();

                com.muhammed.iqbal.vivekbindra.model.Video video = new com.muhammed.iqbal.vivekbindra.model.Video();

                Intent intent = getIntent();
                video.setYouTubeId(intent.getStringExtra("id"));
                video.setTitle(intent.getStringExtra("title"));
                video.setDescription(intent.getStringExtra("description"));
                video.setUrl(intent.getStringExtra("url"));

                mVideoViewModel.insert(video);
                Toast.makeText(VideoActivity.this, "video adding to favorites", Toast.LENGTH_LONG).show();
            }
        });

        mTitleText = (TextView) findViewById(R.id.video_title);
        mDescriptionText = (TextView) findViewById(R.id.video_description);
        mThumbnailImage = (ImageView) findViewById(R.id.video_thumbnail);
        mShareIcon = (ImageView) findViewById(R.id.video_share);
        mShareText = (TextView) findViewById(R.id.video_share_text);
        mDurationText = (TextView) findViewById(R.id.video_dutation_text);
        mViewCountText= (TextView) findViewById(R.id.video_view_count);
        mLikeCountText = (TextView) findViewById(R.id.video_like_count);
        mDislikeCountText = (TextView) findViewById(R.id.video_dislike_count);

        mTitleText.setText(intent.getStringExtra("title"));

        mDescriptionText.setText(intent.getStringExtra("description"));

        //et load the video thumbnail image
        Picasso.with(this)
                .load(intent.getStringExtra("url"))
                .placeholder(R.drawable.video_placeholder)
                .into(mThumbnailImage);

        // set the click listener to play the video
        mThumbnailImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VideoActivity.this.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=" + getIntent().getStringExtra("id"))));
            }
        });

        // create and set the click listener for both the share icon and share text
        View.OnClickListener shareClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Watch \"" + getIntent().getStringExtra("title") + "\" on YouTube");
                sendIntent.putExtra(Intent.EXTRA_TEXT, "https://www.youtube.com/watch?v=" + getIntent().getStringExtra("id"));
                sendIntent.setType("text/plain");
                //getContext().startActivity(sendIntent);
            }
        };
        mShareIcon.setOnClickListener(shareClickListener);
        mShareText.setOnClickListener(shareClickListener);

        // set the video duration text
        //mDurationText.setText(Utils.parseDuration(intent.getStringExtra("duration")));
        // set the video statistics
        //mViewCountText.setText(sFormatter.format(intent.getStringExtra("view")));
        //mLikeCountText.setText(sFormatter.format(intent.getStringExtra("like")));
        //mDislikeCountText.setText(sFormatter.format(intent.getStringExtra("dislike")));


    }
}
