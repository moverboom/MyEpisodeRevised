package nl.krakenops.myepisode.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import nl.krakenops.myepisode.R;

/**
 * Created by Matthijs on 19/01/2016.
 */
public class Thumbnail implements Serializable {
    private String name;
    private int thumbnail = R.drawable.chuck;
    private boolean isFavorite = false;
    private Date lastWatched = new Date(); //Instantiate new Date
    private LinkedHashMap<String, Episode> watchedEpisodes; //Key = SeasonNumber    Value = Episode object

    public Thumbnail() {
        watchedEpisodes = new LinkedHashMap<String, Episode>();
    }

    public int getThumbnailID() {
        return thumbnail;
    }

    public String getName() {
        return name;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public LinkedHashMap<String, Episode> getWatchedEpisodes() {
        return watchedEpisodes;
    }

    public void setFavorite(boolean favorite) {
        this.isFavorite = favorite;
    }

    public void setWatchedEpisodes(LinkedHashMap<String, Episode> watched) {
        Iterator it = watched.entrySet().iterator();
        while ( it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            if (!watchedEpisodes.containsValue(pair.getValue())) {
                watchedEpisodes.put((String)pair.getKey(), (Episode)pair.getValue());
            }
        }
    }

    public Date getLastWatched() {
        return lastWatched;
    }

    public void setLastWatched(Date date) {
        this.lastWatched = date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }
}
