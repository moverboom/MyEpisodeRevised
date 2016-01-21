package nl.krakenops.myepisode.view.adapters;

import android.app.Activity;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import nl.krakenops.myepisode.R;
import nl.krakenops.myepisode.model.Thumbnail;
import nl.krakenops.myepisode.presenter.ThumbnailPresenter;
import nl.krakenops.myepisode.view.activities.ShowDetailActivity;

/**
 * Created by Matthijs on 19/01/2016.
 */
public class ThumbAdapter extends RecyclerView.Adapter<ThumbAdapter.ThumbHolder> implements Serializable {
    private Activity activity;
    protected ThumbnailPresenter thumbnailPresenter;

    public ThumbAdapter (Activity activity, ThumbnailPresenter thumbnailPresenter) {
        this.activity = activity;
        this.thumbnailPresenter = thumbnailPresenter;
        Log.d("ThumbAdapter", "Created new ThumbAdapter");
    }

    @Override
    public ThumbHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View thumbnailView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.cardview_thumbnails, parent, false);
        return new ThumbHolder(thumbnailView, activity, thumbnailPresenter);
    }

    @Override
    public void onBindViewHolder(ThumbHolder holder, int position) {
        Thumbnail thumbnail = thumbnailPresenter.getRecentShows().get(position);
        holder.vThumb.setImageResource(thumbnail.getThumbnailID()); //Get resource from thumbnail
        holder.vThumb.setTag(thumbnail);
        holder.vThumb.setScaleType(ImageView.ScaleType.FIT_XY);
    }

    @Override
    public int getItemCount() {
        return thumbnailPresenter.getRecentShows().size();
    }

    public static class ThumbHolder extends RecyclerView.ViewHolder {
        protected ImageView vThumb;
        public ThumbHolder(View itemView , final Activity activity, final ThumbnailPresenter presenter) {
            super(itemView);
            vThumb = (ImageView) itemView.findViewById(R.id.thumbnail);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("Thumbnail", (Thumbnail) vThumb.getTag());
                    bundle.putSerializable("ThumbnailPresenter", presenter);
                    activity.startActivity(new Intent(activity.getApplicationContext(), ShowDetailActivity.class).putExtras(bundle));
                }
            });
        }
    }


    public void updateThumbnail(Thumbnail thumbnail) {
        thumbnailPresenter.updateThumbnail(thumbnail);
    }


}
