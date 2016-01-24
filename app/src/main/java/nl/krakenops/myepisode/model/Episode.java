package nl.krakenops.myepisode.model;

import java.io.Serializable;
import java.util.Date;

/**
 * This class represents a watched episode.
 * Created by Matthijs on 20/01/2016.
 */
public class Episode implements Serializable {
    private int id;
    private int episode;
    private Date dateWatched;

    public Episode(int episode) {
        this.episode = episode;
    }

    public int getID() {
        return id;
    }

    public int getEpisode() {
        return episode;
    }

    public Date getDateWatched() {
        return dateWatched;
    }

    public void setID(int id) {
        this.id = id;
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

        if (getEpisode() != episode1.getEpisode()) return false;
        return !(getDateWatched() != null ? !getDateWatched().equals(episode1.getDateWatched()) : episode1.getDateWatched() != null);

    }

    @Override
    public int hashCode() {
        int result = getEpisode();
        result = 31 * result + (getDateWatched() != null ? getDateWatched().hashCode() : 0);
        return result;
    }
}
