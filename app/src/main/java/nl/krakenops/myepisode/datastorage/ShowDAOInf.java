package nl.krakenops.myepisode.datastorage;

import java.util.ArrayList;
import java.util.List;

import nl.krakenops.myepisode.model.Episode;
import nl.krakenops.myepisode.model.Thumbnail;

/**
 * Interface which defines methodes to be implemented by every DAO
 * Created by Matthijs on 22/01/2016.
 */
public interface ShowDAOInf {

    /**
     * Inserts a new show into the database using a Thumbnail.
     * A Thumbnail holds all information needed.
     * If a show is already present, this method will update the existing show.
     * @param thumbnail Thumbnail to use for show data
     * @return true if success
     */
    boolean insertShow(Thumbnail thumbnail);

    /**
     * Returns a List with all recently watched shows as as Thumbnail
     * Recently = 7 days ago or less
     * @return List with Thumbnails
     */
    ArrayList<Thumbnail> getRecentShows();

    /**
     * Returns a List with all favorite shows as as Thumbnail
     * @return List with Thumbnails
     */
    ArrayList<Thumbnail> getFavShows();

    /**
     * Returns a List with all watched shows as as Thumbnail
     * @return List with Thumbnails
     */
    ArrayList<Thumbnail> getAllShows();

    /**
     * Returns a show as Thumbnail for a given id
     * @param id ID of the show to find
     * @return show as Thumbnail
     */
    Thumbnail getShowByID(int id);

    /**
     * Return a show as a Thumbnail for a given name
     * @param  name name of the show to find
     * @return show as Thumbnail
     */
    Thumbnail getShowByName(String name);

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
     * Updated the episodes watched for a show using a given name
     * @param name name of the show to set
     * @param episode Episode to set as Episode
     * @return true is success
     */
    boolean updateShowEpisodeByName(String name, Episode episode);

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
