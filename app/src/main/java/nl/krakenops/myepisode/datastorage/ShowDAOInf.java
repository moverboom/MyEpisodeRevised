package nl.krakenops.myepisode.datastorage;

import java.util.ArrayList;

import nl.krakenops.myepisode.model.Episode;
import nl.krakenops.myepisode.model.Show;

/**
 * Interface which defines methodes to be implemented by every DAO
 * Created by Matthijs on 22/01/2016.
 */
public interface ShowDAOInf {

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
    boolean insertShow(Show show);

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
     * Sets whether a show is a favorite or not using a given id
     * @param id id of the show to set
     * @param favorite boolean if show is favorite
     * @return true if success
     */
    boolean setShowFavoriteById(int id, boolean favorite);

    /**
     * Sets whether a show is a favorite or not using a given name
     * @param name name of the show to set
     * @param favorite boolean if show if favorite
     * @return true if success
     */
    boolean setShowFavoriteByName(String name, boolean favorite);

    /**
     * Updates the episodes watched for a show using a given id
     * @param id id of the show to update
     * @param episode Episode to set as Episode
     * @return true is success
     */
    boolean updateShowEpisodesById(int id, Episode episode);

    /**
     * Updates the episodes watched for a show using a given name
     * @param name name of the show to set
     * @param episode Episode to set as Episode
     * @return true is success
     */
    boolean updateShowEpisodeByName(String name, Episode episode);

    /**
     * Updates all information about a show.
     * @param show the show to update as Show
     * @return true if success
     */
    boolean updateShow(Show show);

    /**
     * Removes an episode from a show using the given show id
     * @param id id of the show from which to remove the episode
     * @param episode Episode to remove as Episode
     * @return true if success
     */
    boolean removeEpisodeFromShowById(int id, Episode episode);

    /**
     * Removes an episode from a show using a given show name
     * @param name name of the show from which to remove the episode
     * @param episode Episode to remove as Episode
     * @return true is success
     */
    boolean removeEpisodeFromShowByName(String name, Episode episode);

    /**
     * Removes a show using the given id
     * @param id id of the show to remove
     * @return true if success
     */
    boolean removeShowById(int id);

    /**
     * Removes a show using the given name
     * @param name name of the show to remove
     * @return true if success
     */
    boolean removeShowByName(String name);
}
