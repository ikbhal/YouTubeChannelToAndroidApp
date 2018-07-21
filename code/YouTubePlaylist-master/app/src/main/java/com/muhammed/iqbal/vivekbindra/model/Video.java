package com.muhammed.iqbal.vivekbindra.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "video_table")
public class Video {
    @PrimaryKey(autoGenerate =  true)
    private int id;

    @ColumnInfo(name = "youtube_id")
    private String youTubeId;

    @NonNull
    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "description")
    private String description;

    @NonNull
    @ColumnInfo(name = "url")
    private String url;

    public Video(){}

    public Video(String title) {
        super();
        this.title = title;
    }
    public Video(String youTubeId, String title, String description, String url){
        super();
        this.youTubeId = youTubeId;
        this.title = title;
        this.description = description;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getYouTubeId() {
        return youTubeId;
    }

    public void setYouTubeId(@NonNull String youTubeId) {
        this.youTubeId = youTubeId;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NonNull
    public String getUrl() {
        return url;
    }

    public void setUrl(@NonNull String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Video{" +
                "id=" + id +
                ", youTubeId='" + youTubeId + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
