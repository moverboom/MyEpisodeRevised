package nl.krakenops.myepisode.presenter;

import android.content.Context;
import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import nl.krakenops.myepisode.datastorage.DAOFactory;
import nl.krakenops.myepisode.datastorage.SQLiteShowDAO;
import nl.krakenops.myepisode.datastorage.ShowDAOInf;
import nl.krakenops.myepisode.model.Episode;
import nl.krakenops.myepisode.model.Thumbnail;

/**
 * Created by Matthijs on 19/01/2016.
 */
public class ThumbnailPresenter implements Serializable {
    private LinkedHashMap<String, Thumbnail> thumbnailHashMap;
    private long DAY_IN_MS = 1000 * 60 * 60 * 24;
    private DAOFactory daoFactory;

    public ThumbnailPresenter(Context context) {
        thumbnailHashMap = new LinkedHashMap<String, Thumbnail>();
        //Using the Factory Pattern to get a ShowDAO for the SQLiteDAO
        this.daoFactory = DAOFactory.getDAOFactory("nl.krakenops.myepisode.datastorage.SQLiteDAOFactory");
        Log.v("ThumbnailPresenter", "Created new ThumbnailPresenter");
        createDataStub();
    }

    /**
     * Returns a List with all shows as Thumbnail.
     * @return List with Thumbnails
     */
    public ArrayList<Thumbnail> getAllShows() {
        /*IMPLEMENT DB CONNECTION*/
        ArrayList<Thumbnail> result = new ArrayList<Thumbnail>();
//        Iterator it = thumbnailHashMap.entrySet().iterator();
//        while ( it.hasNext()) {
//            Map.Entry pair = (Map.Entry) it.next();
//            result.add((Thumbnail)pair.getValue());
//        }
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
    public ArrayList<Thumbnail> getRecentShows() {
        /*IMPLEMENT DB CONNECTION + DATE CHECK*/
        ArrayList<Thumbnail> result = new ArrayList<Thumbnail>();
//        Iterator it = thumbnailHashMap.entrySet().iterator();
//        Iterator it = thumbnailHashMap.entrySet().iterator();
//        while ( it.hasNext()) {
//            Map.Entry pair = (Map.Entry) it.next();
//            if (((Thumbnail)pair.getValue()).getLastWatched().after(new Date(System.currentTimeMillis() - (7 * DAY_IN_MS)))) {
//                result.add((Thumbnail)pair.getValue());
//            }
//        }
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
    public ArrayList<Thumbnail> getFavShows() {
        /*IMPLEMENT CHECK FOR FAVORITE*/
        ArrayList<Thumbnail> result = new ArrayList<Thumbnail>();
//        Iterator it = thumbnailHashMap.entrySet().iterator();
//        while ( it.hasNext()) {
//            Map.Entry pair = (Map.Entry) it.next();
//            if (((Thumbnail)pair.getValue()).isFavorite()) {
//                result.add((Thumbnail) pair.getValue());
//            }
//        }
        if (daoFactory.getShowDAO().getFavShows() != null) {
            result = daoFactory.getShowDAO().getFavShows();
        }
        return result;
    }

    private void createDataStub() {
        for (int i = 0; i < 20; i++) {
            if (i%2 == 0) {
                Thumbnail tmpThumbnail = new Thumbnail();
                tmpThumbnail.setName("Chuck" + i);
                tmpThumbnail.setFavorite(true);
                tmpThumbnail.setLastWatched(new Date(System.currentTimeMillis() - (i * DAY_IN_MS)));
                LinkedHashMap<String, Episode> episodeList = new LinkedHashMap<String, Episode>();
                for (int j = 0; j < 10; j++) {
                    Episode episode = new Episode(j, j);
                    episode.setDateWatched(new Date(System.currentTimeMillis() - (i * DAY_IN_MS)));
                    episodeList.put(String.valueOf(episode.getSeason()), episode);
                }
                tmpThumbnail.setWatchedEpisodes(episodeList);
                thumbnailHashMap.put(tmpThumbnail.getName(), tmpThumbnail);
            } else {
                Thumbnail tmpThumbnail = new Thumbnail();
                tmpThumbnail.setName("Chuck" + i);
                tmpThumbnail.setFavorite(false);
                tmpThumbnail.setLastWatched(new Date(System.currentTimeMillis() - (i * DAY_IN_MS)));
                LinkedHashMap<String, Episode> episodeList = new LinkedHashMap<String, Episode>();
                for (int j = 0; j < 10; j++) {
                    Episode episode = new Episode(j, j);
                    episode.setDateWatched(new Date(System.currentTimeMillis() - (i * DAY_IN_MS)));
                    episodeList.put(String.valueOf(episode.getSeason()), episode);
                }
                tmpThumbnail.setWatchedEpisodes(episodeList);
                thumbnailHashMap.put(tmpThumbnail.getName(), tmpThumbnail);
            }
        }
    }

    public void setFavourite(Thumbnail thumbnail) {
        if (thumbnailHashMap.containsKey(thumbnail.getName())) {
            thumbnailHashMap.get(thumbnail.getName()).setFavorite(true);
        } else {
            thumbnailHashMap.put(thumbnail.getName(), thumbnail);
        }
    }

    public void updateThumbnail(Thumbnail thumbnail) {
        thumbnailHashMap.put(thumbnail.getName(), thumbnail);
        Log.d("ThumbnailPresenter", "Updating " + thumbnail.getName() + " to favorite = " + thumbnail.isFavorite());
    }


}
