package nl.krakenops.myepisode.model;

import java.io.Serializable;
import java.util.Date;

import nl.krakenops.myepisode.datastorage.DAOFactory;

/**
 * This class represents a watched episode.
 * Created by Matthijs on 20/01/2016.
 */
public class Episode implements Serializable {
    private long id;
    private int episode;
    private Date dateWatched = null;
    private Date airDate = null;

    public Episode(int episode) {
        this.episode = episode;
    }

    public long getID() {
        return id;
    }

    public int getEpisode() {
        return episode;
    }

    public Date getDateWatched() {
        return dateWatched;
    }

    public Date getAirDate() {
        return airDate;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setEpisode(int episode) {
        this.episode = episode;
    }

    public void setDateWatched(Date dateWatched) {
        this.dateWatched = dateWatched;
    }

    public void setAirDate(Date airDate) {
        this.airDate = airDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Episode episode1 = (Episode) o;

        if (id != episode1.id) return false;
        if (getEpisode() != episode1.getEpisode()) return false;
        if (getDateWatched() != null ? !getDateWatched().equals(episode1.getDateWatched()) : episode1.getDateWatched() != null)
            return false;
        return !(getAirDate() != null ? !getAirDate().equals(episode1.getAirDate()) : episode1.getAirDate() != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + getEpisode();
        result = 31 * result + (getDateWatched() != null ? getDateWatched().hashCode() : 0);
        result = 31 * result + (getAirDate() != null ? getAirDate().hashCode() : 0);
        return result;
    }
}
