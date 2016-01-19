package nl.krakenops.myepisode.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import nl.krakenops.myepisode.R;

/**
 * Created by Matthijs on 19/01/2016.
 */
public class ThumbAdapter extends RecyclerView.Adapter<ThumbAdapter.ThumbHolder> {
    private List<Thumbnail> thumbnailList;

    public ThumbAdapter (List<Thumbnail> thumbnailList) {
        this.thumbnailList = thumbnailList;
    }

    @Override
    public ThumbHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View thumbnailView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.cardview_thumbnails, parent, false);
        return new ThumbHolder(thumbnailView);
    }

    @Override
    public void onBindViewHolder(ThumbHolder holder, int position) {
        Thumbnail thumbnail = thumbnailList.get(position);
        holder.vThumb.setImageResource(thumbnail.thumbnail); //Get resource from thumbnail
    }

    @Override
    public int getItemCount() {
        return thumbnailList.size();
    }

    public static class ThumbHolder extends RecyclerView.ViewHolder {
        protected ImageView vThumb;
        public ThumbHolder(View itemView) {
            super(itemView);
            vThumb = (ImageView) itemView.findViewById(R.id.thumbnail);
        }
    }
}
