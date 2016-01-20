package nl.krakenops.myepisode.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import nl.krakenops.myepisode.model.Episode;
import nl.krakenops.myepisode.model.Thumbnail;

/**
 * Created by Matthijs on 19/01/2016.
 */
public class ThumbnailController {
    LinkedHashMap<String, Thumbnail> thumbnailHashMap;

    public ThumbnailController() {
        thumbnailHashMap = new LinkedHashMap<String, Thumbnail>();
        createDataStub();
    }

    /**
     * Returns a List with all shows as Thumbnail.
     * @return List with Thumbnails
     */
    public List<Thumbnail> getAllShows() {
        /*IMPLEMENT DB CONNECTION*/
        List<Thumbnail> result = new ArrayList<Thumbnail>();
        Iterator it = thumbnailHashMap.entrySet().iterator();
        while ( it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            result.add((Thumbnail)pair.getValue());
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
        Iterator it = thumbnailHashMap.entrySet().iterator();
        while ( it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            result.add((Thumbnail)pair.getValue());
        }
        return result;
    }

    /**
     * Returns a List with all favorite shows as Thumbnail
     *
     * @return List with Thumbnails
     */
    public List<Thumbnail> getFavShows() {
        /*IMPLEMENT CHECK FOR FAVORITE*/
        List<Thumbnail> result = new ArrayList<Thumbnail>();
        Iterator it = thumbnailHashMap.entrySet().iterator();
        while ( it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            result.add((Thumbnail)pair.getValue());
        }
        return result;
    }

    private void createDataStub() {
        for (int i = 0; i < 20; i++) {
            if (i%2 == 0) {
                Thumbnail tmpThumbnail = new Thumbnail();
                tmpThumbnail.setName("Chuck" + i);
                tmpThumbnail.setFavorite(true);
                LinkedHashMap<String, Episode> episodeList = new LinkedHashMap<String, Episode>();
                for (int j = 0; j < 10; j++) {
                    Episode episode = new Episode(j, j);
                    episode.setDateWatched(new Date());
                    episodeList.put(String.valueOf(episode.getSeason()), episode);
                }
                tmpThumbnail.setWatchedEpisodes(episodeList);
                thumbnailHashMap.put(tmpThumbnail.getName(), tmpThumbnail);
            } else {
                Thumbnail tmpThumbnail = new Thumbnail();
                tmpThumbnail.setName("Chuck" + i);
                tmpThumbnail.setFavorite(false);
                LinkedHashMap<String, Episode> episodeList = new LinkedHashMap<String, Episode>();
                for (int j = 0; j < 10; j++) {
                    Episode episode = new Episode(j, j);
                    episode.setDateWatched(new Date());
                    episodeList.put(String.valueOf(episode.getSeason()), episode);
                }
                tmpThumbnail.setWatchedEpisodes(episodeList);
                thumbnailHashMap.put(tmpThumbnail.getName(), tmpThumbnail);
            }
        }
    }


}
