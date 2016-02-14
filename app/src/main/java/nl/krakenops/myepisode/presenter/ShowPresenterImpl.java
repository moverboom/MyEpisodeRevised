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
import nl.krakenops.myepisode.model.Episode;
import nl.krakenops.myepisode.model.Season;
import nl.krakenops.myepisode.model.Show;
import nl.krakenops.myepisode.model.ViewModelHolder;
import nl.krakenops.myepisode.util.downloaders.ShowInfoDownloader;
import nl.krakenops.myepisode.view.adapters.ViewPagerAdapter;

/**
 * Created by Matthijs on 26/01/2016.
 */
public class ShowPresenterImpl implements ShowPresenter {
    private LinkedHashMap<String, Show> showList; //Key is show name
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
     * @param viewModelHolder ViewModelHolder from which to get data
     * @return true if success
     */
    @Override
    public void addEpisode(ViewModelHolder viewModelHolder) {
        Episode tmpEpisode = new Episode(viewModelHolder.getEpisode());
        tmpEpisode.setDateWatched(viewModelHolder.getWatchedAt());
        Season tmpSeason = new Season(viewModelHolder.getSeason());
        tmpSeason.addEpisode(tmpEpisode);
        Show tmpShow = new Show();
        tmpShow.setName(viewModelHolder.getShowName());
        tmpShow.setLastWatchedAt(viewModelHolder.getWatchedAt());
        tmpShow.addSeason(tmpSeason);
        if (showList.containsKey(viewModelHolder.getShowName())) {
            showList.put(tmpShow.getName(), tmpShow);
            showDAO.updateShowEpisodes(tmpShow);
        } else {
            if (showDAO.containsShow(viewModelHolder.getShowName())) {
                showDAO.updateShowEpisodes(tmpShow);
                tmpShow = showDAO.getShowByName(tmpShow.getName());
                showList.put(tmpShow.getName(), tmpShow);
            } else {
                if (showDAO.insertShow(tmpShow) != null) {
                    ShowInfoDownloader showInfoDownloader = new ShowInfoDownloader(context, tmpShow, this);
                    showInfoDownloader.execute();
                    //the onPostExecute method handles the result
                }
            }
        }
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
        /*DATE CHECK IS IMPLEMENTED IN DAO*/
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
        Log.d(this.getClass().getName(), "updateUI() called with showID = " + show.getId());
        showDAO.updateInsertShow(show);
        //view.notifyDataSetChanged();
    }
}
