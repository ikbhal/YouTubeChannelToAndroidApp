package com.muhammed.iqbal.vivekbindra.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.muhammed.iqbal.vivekbindra.dao.VideoDao;
import com.muhammed.iqbal.vivekbindra.model.Video;

@Database(entities = {Video.class}, version = 1)
public abstract class VideoRoomDatabase extends RoomDatabase {
    public abstract VideoDao videoDao();
    private static VideoRoomDatabase INSTANCE;

    public static VideoRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (VideoRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                VideoRoomDatabase.class, "video_database")
                                .build();
                }
            }
        }
        return INSTANCE;
    }
}
