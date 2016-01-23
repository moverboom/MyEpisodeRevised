package nl.krakenops.myepisode.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Matthijs on 23/01/2016.
 */
public class Season {
    private int season;
    private Date lastWatched;
    private int maxEpisodes;
    private String backdropPath;
    private LinkedHashMap<Integer, Episode> episodes;

    public Season(int season) {
        this.episodes = new LinkedHashMap<Integer, Episode>();
        this.season = season;
    }

    public int getSeason() {
        return season;
    }

    public Date getLastWatched() {
        return lastWatched;
    }

    public int getMaxEpisodes() {
        return maxEpisodes;
    }

    public String getBackdropPath() {
        return backdropPath;
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

    public void setSeason(int season) {
        this.season = season;
    }

    public void setLastWatched(Date lastWatched) {
        this.lastWatched = lastWatched;
    }

    public void setMaxEpisodes(int maxEpisodes) {
        this.maxEpisodes = maxEpisodes;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public void addEpisode(Episode episode) {
        if (episodes.containsKey(episode.getEpisode())) {
            if (!episodes.get(episode.getEpisode()).equals(episode)) {
                episodes.put(episode.getEpisode(), episode);
            }
        } else {
            episodes.put(episode.getEpisode(), episode);
        }
    }
}
