package nl.krakenops.myepisode.view.adapters;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.File;
import java.util.List;

import nl.krakenops.myepisode.R;
import nl.krakenops.myepisode.model.Show;
import nl.krakenops.myepisode.view.activities.ShowDetailActivity;

/**
 * Created by Matthijs on 19/01/2016.
 */
public class ThumbAdapter extends RecyclerView.Adapter<ThumbAdapter.ThumbHolder> {
    private Activity activity;
    protected List<Show> showList;

    public ThumbAdapter (Activity activity, List<Show> showList) {
        this.activity = activity;
        this.showList = showList;
        Log.d("ThumbAdapter", "Created new ThumbAdapter");
    }

    @Override
    public ThumbHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View thumbnailView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.cardview_thumbnails, parent, false);
        return new ThumbHolder(thumbnailView, activity);
    }

    @Override
    public void onBindViewHolder(ThumbHolder holder, int position) {
        Show show = showList.get(position);
        if (show.getThumbnailPath() == null) {
            holder.vThumb.setImageResource(R.drawable.placeholder);
        } else {
            File tmpFile = new File(show.getThumbnailPath());
            if (tmpFile.exists()) {
                Bitmap bitmap = BitmapFactory.decodeFile(tmpFile.getAbsolutePath());
                holder.vThumb.setImageBitmap(bitmap);
            } else {
                holder.vThumb.setImageResource(R.drawable.placeholder);
            }
        }
        holder.vThumb.setTag(show);
        holder.vThumb.setScaleType(ImageView.ScaleType.FIT_XY);
    }

    @Override
    public int getItemCount() {
        return showList.size();
    }

    public static class ThumbHolder extends RecyclerView.ViewHolder {
        protected ImageView vThumb;
        public ThumbHolder(View itemView , final Activity activity) {
            super(itemView);
            vThumb = (ImageView) itemView.findViewById(R.id.show);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("Thumbnail", (Show) vThumb.getTag());
                    //bundle.putSerializable("ThumbnailPresenter", presenter);
                    activity.startActivity(new Intent(activity.getApplicationContext(), ShowDetailActivity.class).putExtras(bundle));
                }
            });
        }
    }
}
