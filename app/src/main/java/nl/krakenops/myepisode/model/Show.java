package nl.krakenops.myepisode.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import nl.krakenops.myepisode.R;

/**
 * This class represents a TV Show.
 *
 * Created by Matthijs on 19/01/2016.
 */
public class Show implements Serializable {
    private String name;
    private int thumbnail = R.drawable.chuck;
    private boolean isFavorite = false;
    private Date lastWatched = new Date(); //Instantiate new Date
    private LinkedHashMap<Integer, Season> seasons; //Key = SeasonNumber    Value = Season object

    public Show() {
        seasons = new LinkedHashMap<Integer, Season>();
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

    public LinkedHashMap<Integer, Season> getSeasons() {
        return seasons;
    }

    public ArrayList<Season> getSeasonsAsArrayList() {
        ArrayList<Season> result = new ArrayList<Season>();
        Iterator it = seasons.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            result.add((Season)pair.getValue());
        }
        return result;
    }

    public void setFavorite(boolean favorite) {
        this.isFavorite = favorite;
    }

    public void addEpisode(Episode episode, int season) {
        if (seasons.containsKey(season)) {
            seasons.get(season).addEpisode(episode);
        } else {
            Season tmpSeason = new Season(season);
            tmpSeason.addEpisode(episode);
            seasons.put(tmpSeason.getSeason(), tmpSeason);
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
