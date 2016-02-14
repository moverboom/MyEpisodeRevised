package nl.krakenops.myepisode.model;

import java.util.Date;

/**
 * Class which holds data submitted by the user.
 * Created final to ensure consistency
 * Created by Matthijs on 14/02/2016.
 */
public final class ViewModelHolder {
    private final String showName;
    private final int season;
    private final int episode;
    private final Date watchedAt;

    public ViewModelHolder(String showName, int season, int episode, Date watchedAt) {
        this.showName = showName;
        this.season = season;
        this.episode = episode;
        this.watchedAt = watchedAt;
    }

    public String getShowName() {
        return showName;
    }

    public int getSeason() {
        return season;
    }

    public int getEpisode() {
        return episode;
    }

    public Date getWatchedAt() {
        return watchedAt;
    }
}
