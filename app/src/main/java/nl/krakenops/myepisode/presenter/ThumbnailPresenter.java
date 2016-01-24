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
    private LinkedHashMap<String, Show> thumbnailHashMap;
    private long DAY_IN_MS = 1000 * 60 * 60 * 24;
    private DAOFactory daoFactory;

    public ThumbnailPresenter(Context context) {
        thumbnailHashMap = new LinkedHashMap<String, Show>();
        //Using the Factory Pattern to get a ShowDAO for the SQLiteDAO
        this.daoFactory = DAOFactory.getDAOFactory("nl.krakenops.myepisode.datastorage.SQLiteDAOFactory");
        Log.v("ThumbnailPresenter", "Created new ThumbnailPresenter");
        //createDataStub();
    }

    /**
     * Returns a List with all shows as Thumbnail.
     * @return List with Thumbnails
     */
    public ArrayList<Show> getAllShows() {
        /*IMPLEMENT DB CONNECTION*/
        ArrayList<Show> result = new ArrayList<Show>();
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
    public ArrayList<Show> getRecentShows() {
        /*IMPLEMENT DB CONNECTION + DATE CHECK*/
        ArrayList<Show> result = new ArrayList<Show>();
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
    public ArrayList<Show> getFavShows() {
        /*IMPLEMENT CHECK FOR FAVORITE*/
        ArrayList<Show> result = new ArrayList<Show>();
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

//    private void createDataStub() {
//        for (int i = 0; i < 20; i++) {
//            if (i%2 == 0) {
//                Thumbnail tmpThumbnail = new Thumbnail();
//                tmpThumbnail.setName("Chuck" + i);
//                tmpThumbnail.setFavorite(true);
//                tmpThumbnail.setLastWatched(new Date(System.currentTimeMillis() - (i * DAY_IN_MS)));
//                LinkedHashMap<String, Episode> episodeList = new LinkedHashMap<String, Episode>();
//                for (int j = 0; j < 10; j++) {
//                    Episode episode = new Episode(j, j);
//                    episode.setDateWatched(new Date(System.currentTimeMillis() - (i * DAY_IN_MS)));
//                    episodeList.put(String.valueOf(episode.getSeason()), episode);
//                }
//                tmpThumbnail.setWatchedEpisodes(episodeList);
//                thumbnailHashMap.put(tmpThumbnail.getName(), tmpThumbnail);
//            } else {
//                Thumbnail tmpThumbnail = new Thumbnail();
//                tmpThumbnail.setName("Chuck" + i);
//                tmpThumbnail.setFavorite(false);
//                tmpThumbnail.setLastWatched(new Date(System.currentTimeMillis() - (i * DAY_IN_MS)));
//                LinkedHashMap<String, Episode> episodeList = new LinkedHashMap<String, Episode>();
//                for (int j = 0; j < 10; j++) {
//                    Episode episode = new Episode(j, j);
//                    episode.setDateWatched(new Date(System.currentTimeMillis() - (i * DAY_IN_MS)));
//                    episodeList.put(String.valueOf(episode.getSeason()), episode);
//                }
//                tmpThumbnail.setWatchedEpisodes(episodeList);
//                thumbnailHashMap.put(tmpThumbnail.getName(), tmpThumbnail);
//            }
//        }
//    }

    public void setFavourite(Show show) {
        if (thumbnailHashMap.containsKey(show.getName())) {
            thumbnailHashMap.get(show.getName()).setFavorite(true);
        } else {
            thumbnailHashMap.put(show.getName(), show);
        }
    }

    public void updateThumbnail(Show show) {
        thumbnailHashMap.put(show.getName(), show);
        Log.d("ThumbnailPresenter", "Updating " + show.getName() + " to favorite = " + show.isFavorite());
    }


}
