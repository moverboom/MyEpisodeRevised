package nl.krakenops.myepisode.datastorage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Observable;

import nl.krakenops.myepisode.model.Episode;
import nl.krakenops.myepisode.model.Show;

/**
 * Interface which defines methodes to be implemented by every DAO
 * Created by Matthijs on 22/01/2016.
 */
public interface ShowDAOInf {

    void open() throws SQLException;

    void close();

    /**
     * Checks if the datastorage contains the show for the given name
     * @param name name of the show to check
     * @return true is show exists
     */
    boolean containsShow(String name);

    /**
     * Inserts a new show into the database using a Thumbnail.
     * A Thumbnail holds all information needed.
     * If a show is already present, this method will update the existing show.
     * @param show Thumbnail to use for show data
     * @return true if success
     */
    Show insertShow(Show show);

    /**
     * Used to update a show which was inserted for the first time.
     * @param show Show to update
     * @return true if success
     */
    boolean updateInsertShow(Show show);

    /**
     * Returns a List with all recently watched shows as as Thumbnail
     * Recently = 7 days ago or less
     * @return List with Thumbnails
     */
    ArrayList<Show> getRecentShows();

    /**
     * Returns a List with all favorite shows as as Thumbnail
     * @return List with Thumbnails
     */
    ArrayList<Show> getFavShows();

    /**
     * Returns a List with all watched shows as as Thumbnail
     * @return List with Thumbnails
     */
    ArrayList<Show> getAllShows();

    /**
     * Returns a show as Thumbnail for a given id
     * @param id ID of the show to find
     * @return show as Thumbnail
     */
    Show getShowByID(int id);

    /**
     * Return a show as a Thumbnail for a given name
     * @param  name name of the show to find
     * @return show as Thumbnail
     */
    Show getShowByName(String name);

    /**
     * Sets whether a show is a favorite or not using the given show object
     * @param show Show to set
     * @return true if success
     */
    boolean setShowFavorite(Show show);

    /**
     * Updates the episode watched.
     * Uses the available Season and Episode models to get the correct information.
     * @param show Show to update
     * @return true is success
     */
    boolean updateShowEpisodes(Show show);

    /**
     * Updates the thumbnailPath set for a show.
     * Retrieves the new path from the show object
     * @param show Show to update
     * @return true if success
     */
    boolean updateShowThumbnail(Show show);

    /**
     * Updates the backdropPath set for a show.
     * Retrieves the new path from the show object
     * @param show Show to update
     * @return true if success
     */
    boolean updateShowBackdrop(Show show);

    /**
     * Removes a show
     * @param show Show to remove
     * @return true if success
     */
    boolean removeShow(Show show);
}
