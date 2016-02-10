package nl.krakenops.myepisode.presenter;

import android.content.Context;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.concurrent.ExecutionException;

import nl.krakenops.myepisode.datastorage.DAOFactory;
import nl.krakenops.myepisode.datastorage.SQLiteDAOFactory;
import nl.krakenops.myepisode.datastorage.ShowDAOInf;
import nl.krakenops.myepisode.model.Show;
import nl.krakenops.myepisode.util.downloaders.ShowInfoDownloader;
import nl.krakenops.myepisode.view.adapters.ViewPagerAdapter;

/**
 * Created by Matthijs on 26/01/2016.
 */
public class ShowPresenterImpl implements ShowPresenter {
    private LinkedHashMap<String, Show> showList;
    private long DAY_IN_MS = 1000 * 60 * 60 * 24;
    private DAOFactory daoFactory;
    private ShowDAOInf showDAO;
    //Android specific variables
    private Context context;
    private ViewPagerAdapter view = null;

    public ShowPresenterImpl(Context context) {
        this.context = context;
        showList = new LinkedHashMap<String, Show>();
        //Using the Factory Pattern to get a ShowDAO for the SQLiteDAO
        this.daoFactory = DAOFactory.getDAOFactory("nl.krakenops.myepisode.datastorage.SQLiteDAOFactory");
        showDAO = daoFactory.getShowDAO((context));
        try {
            showDAO.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setUIRef(Object view) {
        this.view = (ViewPagerAdapter)view;
    }

    /**
     * Inserts a Show
     * @param show Show to insert
     * @return true if success
     */
    @Override
    public void insertShow(Show show) {
        showDAO.insertShow(show);
        ShowInfoDownloader showInfoDownloader = new ShowInfoDownloader(context, show, this);
        showInfoDownloader.execute();
    }

    /**
     * Returns a List with all shows as Thumbnail.
     * @return List with Thumbnails
     */
    public ArrayList<Show> getAllShows() {
        /*IMPLEMENT DB CONNECTION*/
        ArrayList<Show> result = new ArrayList<Show>();
        if (showDAO.getAllShows() != null) {
            result = showDAO.getAllShows();
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
        if (showDAO.getRecentShows() != null) {
            result = showDAO.getRecentShows();
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
        if (showDAO.getFavShows() != null) {
            result = showDAO.getFavShows();
        }
        return result;
    }

    public void setShowFavorite(Show show) {
        if (showDAO.setShowFavorite(show)) {
            showList.put(show.getName(), show);
        }
        Log.d("ThumbnailPresenter", "Updating " + show.getName() + " to favorite = " + show.isFavorite());
    }

    /**
     * Updates the ViewPagerAdapter after the AsyncTask finishes downloading all the data.
     * @param show Show for which data was downloaded. Is returned by the AsyncTask.
     */
    public void updateUI(Show show) {
        showList.put(show.getName(), show);
        //view.notifyDataSetChanged();
    }
}
