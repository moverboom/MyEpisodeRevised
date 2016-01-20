package nl.krakenops.myepisode.view.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import nl.krakenops.myepisode.R;
import nl.krakenops.myepisode.model.Thumbnail;
import nl.krakenops.myepisode.view.activities.ShowDetailActivity;

/**
 * Created by Matthijs on 19/01/2016.
 */
public class ThumbAdapter extends RecyclerView.Adapter<ThumbAdapter.ThumbHolder> {
    private List<Thumbnail> thumbnailList;
    private Activity activity;

    public ThumbAdapter (List<Thumbnail> thumbnailList, Activity activity) {
        this.thumbnailList = thumbnailList;
        this.activity = activity;
    }

    @Override
    public ThumbHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View thumbnailView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.cardview_thumbnails, parent, false);
        return new ThumbHolder(thumbnailView, activity);
    }

    @Override
    public void onBindViewHolder(ThumbHolder holder, int position) {
        Thumbnail thumbnail = thumbnailList.get(position);
        holder.vThumb.setImageResource(thumbnail.getThumbnailID()); //Get resource from thumbnail
    }

    @Override
    public int getItemCount() {
        return thumbnailList.size();
    }

    public static class ThumbHolder extends RecyclerView.ViewHolder {
        protected ImageView vThumb;
        public ThumbHolder(View itemView , final Activity activity) {
            super(itemView);
            vThumb = (ImageView) itemView.findViewById(R.id.thumbnail);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.startActivity(new Intent(activity.getApplicationContext(), ShowDetailActivity.class));
                }
            });
        }
    }
}
