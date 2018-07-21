package com.muhammed.iqbal.vivekbindra;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.muhammed.iqbal.vivekbindra.adapter.FavoritelistCardAdapter;
import com.muhammed.iqbal.vivekbindra.db.VideoViewModel;
import com.muhammed.iqbal.vivekbindra.model.Video;

import java.util.List;

public class FavoriteActivity extends AppCompatActivity {
    private VideoViewModel mVideoViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        mVideoViewModel = ViewModelProviders.of(this).get(VideoViewModel.class);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final FavoritelistCardAdapter adapter = new FavoritelistCardAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
