package com.muhammed.iqbal.vivekbindra.adapter;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.api.services.youtube.YouTube;
import com.muhammed.iqbal.vivekbindra.R;
import com.muhammed.iqbal.vivekbindra.db.VideoViewModel;
import com.muhammed.iqbal.vivekbindra.model.Video;

import java.util.List;

public class FavoritelistCardAdapter extends RecyclerView.Adapter<FavoritelistCardAdapter.ViewHolder>  {

    private FragmentActivity mActivity;
    private VideoViewModel mVideoViewModel;
    private LiveData<List<Video>> mAllVideos;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final Context mContext;
        public final TextView mTitleText;

        public ViewHolder(View v) {
            super(v);
            mContext = v.getContext();
            mTitleText = (TextView) v.findViewById(R.id.video_title);
        }
    }

    public FavoritelistCardAdapter(FragmentActivity activity) { this.mActivity = activity; }

    // Create new views (invoked by the layout manager)
    @Override
    public FavoritelistCardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflate a card layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_card, parent, false);
        // populate the viewholder
        ViewHolder vh = new ViewHolder(v);

        mVideoViewModel = ViewModelProviders.of(mActivity).get(VideoViewModel.class);
        mAllVideos = mVideoViewModel.getAllVideos();

        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (mAllVideos.getValue().size() == 0) {
            return;
        }

        final Video video = mAllVideos.getValue().get(position);

        holder.mTitleText.setText(video.getTitle());
    }

    @Override
    public int getItemCount() {
        if(mAllVideos == null || mAllVideos.getValue() == null)
            return 0;
        return mAllVideos.getValue().size();
    }

}
