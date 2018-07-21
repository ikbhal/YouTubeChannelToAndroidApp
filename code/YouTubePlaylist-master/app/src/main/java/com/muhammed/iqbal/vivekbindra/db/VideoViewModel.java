package com.muhammed.iqbal.vivekbindra.db;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.muhammed.iqbal.vivekbindra.model.Video;
import com.muhammed.iqbal.vivekbindra.model.Video;
import com.muhammed.iqbal.vivekbindra.repository.VideoRepository;
import com.muhammed.iqbal.vivekbindra.repository.VideoRepository;

import java.util.List;

public class VideoViewModel extends AndroidViewModel {
    private VideoRepository mRepository;
    private LiveData<List<Video>> mAllVideos;

    public VideoViewModel(Application appliation) {
        super(appliation);
        mRepository = new VideoRepository(appliation);
        mAllVideos = mRepository.getAllVideos();
    }

    public LiveData<List<Video>> getAllVideos() { return mAllVideos; }
    public void insert(Video Video) { mRepository.insert(Video); }
    public Video getVideoByYoutubeId(String youtubeId) { return mRepository.getVideoByYouTubeId(youtubeId);}
}
