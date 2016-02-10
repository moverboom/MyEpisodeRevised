package nl.krakenops.myepisode.presenter;

import android.util.Log;

import java.util.ArrayList;

import nl.krakenops.myepisode.model.Show;

/**
 * Created by Matthijs on 27/01/2016.
 */
public interface ShowPresenter {

    abstract void setUIRef(Object view);

    /**
     * Inserts a Show
     * @param show Show to insert
     * @return true if success
     */
    void insertShow(Show show);

    /**
     * Returns a List with all shows as Thumbnail.
     * @return List with Thumbnails
     */
    ArrayList<Show> getAllShows();

    /**
     * Returns a List with all recently watched shows as Thumbnail
     * Recently = 7 days ago or less
     * @return List with Thumbnails
     */
    ArrayList<Show> getRecentShows();

    /**
     * Returns a List with all favorite shows as Thumbnail
     *
     * @return List with Thumbnails
     */
    ArrayList<Show> getFavShows();

    void setShowFavorite(Show show);

    /**
     * Updates the ViewPagerAdapter after the AsyncTask finishes downloading all the data.
     * @param show Show for which data was downloaded. Is returned by the AsyncTask.
     */
    abstract void updateUI(Show show);
}
