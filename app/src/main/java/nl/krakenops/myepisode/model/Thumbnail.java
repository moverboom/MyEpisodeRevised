package nl.krakenops.myepisode.model;

import nl.krakenops.myepisode.R;

/**
 * Created by Matthijs on 19/01/2016.
 */
public class Thumbnail {
    private String name;
    private int thumbnail = R.drawable.chuck;

    public int getThumbnailID() {
        return thumbnail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }
}
