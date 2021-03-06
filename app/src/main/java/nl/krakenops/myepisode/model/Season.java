package nl.krakenops.myepisode.model;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Matthijs on 23/01/2016.
 */
public class Season implements Serializable {
    private long id;
    private int season;
    private int maxEpisodes;
    private LinkedHashMap<Integer, Episode> episodes;

    public Season(int season) {
        this.episodes = new LinkedHashMap<Integer, Episode>();
        this.season = season;
    }

    public long getId() {
        return id;
    }

    public int getSeason() {
        return season;
    }

    public int getMaxEpisodes() {
        return maxEpisodes;
    }

    public LinkedHashMap<Integer, Episode> getEpisodes() {
        return episodes;
    }

    public ArrayList<Episode> getEpisodesAsArrayList() {
        ArrayList<Episode> result = new ArrayList<Episode>();
        Iterator it = episodes.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            result.add((Episode)pair.getValue());
        }
        return result;
    }

    public Episode getEpisode(int episode) {
        Episode result = null;
        if (episodes.containsKey(episode)) {
            result = episodes.get(episode);
        }
        return result;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public void setMaxEpisodes(int maxEpisodes) {
        this.maxEpisodes = maxEpisodes;
    }

    public void addEpisode(Episode episode) {
        Log.d(this.getClass().getName(), "Addes Episode: " + episode.getEpisode() + " to Season " + getSeason());
        if (episodes.containsKey(episode.getEpisode())) {
            if (!episodes.get(episode.getEpisode()).equals(episode) && episodes.get(episode.getEpisode()).getDateWatched() != null) {
                episodes.put(episode.getEpisode(), episode);
            }
        } else {
            episodes.put(episode.getEpisode(), episode);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Season season1 = (Season) o;

        if (getId() != season1.getId()) return false;
        if (getSeason() != season1.getSeason()) return false;
        if (getMaxEpisodes() != season1.getMaxEpisodes()) return false;
        return !(getEpisodes() != null ? !getEpisodes().equals(season1.getEpisodes()) : season1.getEpisodes() != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + getSeason();
        result = 31 * result + getMaxEpisodes();
        result = 31 * result + (getEpisodes() != null ? getEpisodes().hashCode() : 0);
        return result;
    }
}
