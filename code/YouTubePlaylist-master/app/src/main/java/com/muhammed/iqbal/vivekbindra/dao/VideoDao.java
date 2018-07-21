package com.muhammed.iqbal.vivekbindra.dao;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.muhammed.iqbal.vivekbindra.model.Video;

import java.util.List;

@Dao
public interface VideoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Video video);

    @Query("DELETE FROM video_table")
    void deleteAll();

    @Query("SELECT * FROM video_table ORDER BY title ASC")
    LiveData<List<Video>> getAllVideos();

    @Query("SELECT * FROM video_table WHERE youtube_id = :youTubeId LIMIT 1")
    Video getVideoByyouTubeId(String youTubeId);

}
