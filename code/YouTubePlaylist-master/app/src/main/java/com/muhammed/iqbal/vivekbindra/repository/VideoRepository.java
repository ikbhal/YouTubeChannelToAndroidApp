package com.muhammed.iqbal.vivekbindra.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.muhammed.iqbal.vivekbindra.dao.VideoDao;
import com.muhammed.iqbal.vivekbindra.db.VideoRoomDatabase;
import com.muhammed.iqbal.vivekbindra.model.Video;

import java.util.List;

public class VideoRepository {
    private VideoDao mVideoDao;
    private LiveData<List<Video>> mAllVideos;

    public VideoRepository(Application application) {
        VideoRoomDatabase db = VideoRoomDatabase.getDatabase(application);
        mVideoDao = db.videoDao();
        mAllVideos = mVideoDao.getAllVideos();
    }

    public Video getVideoByYouTubeId(String youTubeId) {
        //new getAsyncTask(mVideoDao).execute(youTubeId);
        return mVideoDao.getVideoByyouTubeId(youTubeId);
    }

    private static class getAsyncTask extends AsyncTask<String, String, Video> {
        private VideoDao mAsyncTaskDao;

        getAsyncTask(VideoDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected  Video doInBackground(final String... params) {
            mAsyncTaskDao.getVideoByyouTubeId(params[0]);
            return null;
        }
    }

    public LiveData<List<Video>> getAllVideos() {
        return mAllVideos;
    }

    public void insert(Video word) {
        new insertAsyncTask(mVideoDao).execute(word);
    }

    private static class insertAsyncTask extends AsyncTask<Video, Void, Void> {
        private VideoDao mAsyncTaskDao;

        insertAsyncTask(VideoDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected  Void doInBackground(final Video... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

}
