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
    private long id;
    private String name;
    private Date lastWatchedAt;
    private int thumbnail = R.drawable.chuck;
    private String thumbnailPath = null; //A thumbnail is shown in the fragments
    private String backdropPath = null; //A backdrop is shown in the ShowDetailActivity
    private boolean isFavorite = false;
    private LinkedHashMap<Integer, Season> seasons; //Key = SeasonNumber    Value = Season object

    public Show() {
        seasons = new LinkedHashMap<Integer, Season>();
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getLastWatchedAt() {
        return lastWatchedAt;
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

    public Season getSeason(int season) {
        Season result = null;
        if (seasons.containsKey(season)) {
            result = seasons.get(season);
        }
        return result;
    }

    public Season getLastSeason() {
        Season result = null;
        int seasonNumber = 0;
        Iterator it = seasons.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            if ((int)pair.getKey() > seasonNumber) {
                seasonNumber = (int)pair.getKey();
            }
            result = getSeason(seasonNumber);
        }
        return result;
    }

    public String getThumbnailPath() {
        return thumbnailPath;
    }

    public String getBackdropPath() {
        return backdropPath;
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
            seasons.put(season, tmpSeason);
        }
    }

    public void setId(long id) {
        this.id = id;
    }

    public void addSeason(Season season) {
        if (!seasons.containsKey(season.getSeason())) {
            seasons.put(season.getSeason(), season);
        } else {
            if (!seasons.get(season.getSeason()).equals(season)) {
                seasons.put(season.getSeason(), season);
            }
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastWatchedAt(Date lastWatchedAt) {
        this.lastWatchedAt = lastWatchedAt;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Show show = (Show) o;

        if (getId() != show.getId()) return false;
        if (thumbnail != show.thumbnail) return false;
        if (isFavorite() != show.isFavorite()) return false;
        if (getName() != null ? !getName().equals(show.getName()) : show.getName() != null)
            return false;
        if (getThumbnailPath() != null ? !getThumbnailPath().equals(show.getThumbnailPath()) : show.getThumbnailPath() != null)
            return false;
        if (getBackdropPath() != null ? !getBackdropPath().equals(show.getBackdropPath()) : show.getBackdropPath() != null)
            return false;
        return !(getSeasons() != null ? !getSeasons().equals(show.getSeasons()) : show.getSeasons() != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + thumbnail;
        result = 31 * result + (getThumbnailPath() != null ? getThumbnailPath().hashCode() : 0);
        result = 31 * result + (getBackdropPath() != null ? getBackdropPath().hashCode() : 0);
        result = 31 * result + (isFavorite() ? 1 : 0);
        result = 31 * result + (getSeasons() != null ? getSeasons().hashCode() : 0);
        return result;
    }
}
