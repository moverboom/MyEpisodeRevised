package nl.krakenops.myepisode.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Matthijs on 20/01/2016.
 */
public class Episode implements Serializable {
    private int season;
    private int episode;
    private Date dateWatched;

    public Episode(int season, int episode) {
        this.season = season;
        this.episode = episode;
    }

    public int getSeason() {
        return season;
    }

    public int getEpisode() {
        return episode;
    }

    public Date getDateWatched() {
        return dateWatched;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public void setEpisode(int episode) {
        this.episode = episode;
    }

    public void setDateWatched(Date dateWatched) {
        this.dateWatched = dateWatched;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Episode episode1 = (Episode) o;

        if (getSeason() != episode1.getSeason()) return false;
        return getEpisode() == episode1.getEpisode();

    }

    @Override
    public int hashCode() {
        int result = getSeason();
        result = 31 * result + getEpisode();
        return result;
    }
}
