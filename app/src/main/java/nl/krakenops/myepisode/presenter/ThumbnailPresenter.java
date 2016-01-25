package nl.krakenops.myepisode.presenter;

import android.content.Context;
import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import nl.krakenops.myepisode.datastorage.DAOFactory;
import nl.krakenops.myepisode.model.Show;

/**
 * Created by Matthijs on 19/01/2016.
 */
public class ThumbnailPresenter implements Serializable {
    private LinkedHashMap<String, Show> showList;
    private long DAY_IN_MS = 1000 * 60 * 60 * 24;
    private DAOFactory daoFactory;

    public ThumbnailPresenter(Context context) {
        showList = new LinkedHashMap<String, Show>();
        //Using the Factory Pattern to get a ShowDAO for the SQLiteDAO
        this.daoFactory = DAOFactory.getDAOFactory("nl.krakenops.myepisode.datastorage.SQLiteDAOFactory");
        Log.v("ThumbnailPresenter", "Created new ThumbnailPresenter");
        //createDataStub();
    }

    /**
     * Inserts a Show
     * @param show Show to insert
     * @return true if success
     */
    public boolean insertShow(Show show) {
        return daoFactory.getShowDAO().insertShow(show);
    }

    /**
     * Returns a List with all shows as Thumbnail.
     * @return List with Thumbnails
     */
    public ArrayList<Show> getAllShows() {
        /*IMPLEMENT DB CONNECTION*/
        ArrayList<Show> result = new ArrayList<Show>();
        if (daoFactory.getShowDAO().getAllShows() != null) {
            result = daoFactory.getShowDAO().getAllShows();
        }
        return result;
    }

    /**
     * Returns a List with all recently watched shows as Thumbnail
     * Recently = 7 days ago or less
     * @return List with Thumbnails
     */
    public ArrayList<Show> getRecentShows() {
        /*IMPLEMENT DB CONNECTION + DATE CHECK*/
        ArrayList<Show> result = new ArrayList<Show>();
        if (daoFactory.getShowDAO().getRecentShows() != null) {
            result = daoFactory.getShowDAO().getRecentShows();
        }
        return result;
    }

    /**
     * Returns a List with all favorite shows as Thumbnail
     *
     * @return List with Thumbnails
     */
    public ArrayList<Show> getFavShows() {
        /*IMPLEMENT CHECK FOR FAVORITE*/
        ArrayList<Show> result = new ArrayList<Show>();
        if (daoFactory.getShowDAO().getFavShows() != null) {
            result = daoFactory.getShowDAO().getFavShows();
        }
        return result;
    }
    
    public void updateShow(Show show) {
        if (daoFactory.getShowDAO().updateShow(show)) {
            showList.put(show.getName(), show);
        }
        Log.d("ThumbnailPresenter", "Updating " + show.getName() + " to favorite = " + show.isFavorite());
    }
}
