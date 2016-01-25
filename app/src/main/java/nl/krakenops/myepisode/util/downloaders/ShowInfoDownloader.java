package nl.krakenops.myepisode.util.downloaders;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TvResultsPage;
import info.movito.themoviedbapi.model.tv.TvEpisode;
import info.movito.themoviedbapi.model.tv.TvSeason;
import nl.krakenops.myepisode.datastorage.SQLiteShowDAO;
import nl.krakenops.myepisode.model.Episode;
import nl.krakenops.myepisode.model.Season;
import nl.krakenops.myepisode.model.Show;
import nl.krakenops.myepisode.presenter.ShowPresenter;

/**
 * This class extends AsyncTask and downloads all show information needed.
 * It runs in the background on a different thread and placeholders are used where needed.
 * Once this class finishes downloading everything, it updates the show.
 * Created by Matthijs on 24/01/2016.
 */
public class ShowInfoDownloader extends AsyncTask<Void, Void, Show> {
    private Context context;
    private Show show;
    private ShowPresenter showPresenter;
    private static final String THUMBNAIL = "thumbnail";
    private static final String BACKDROP = "backdrop";

    public ShowInfoDownloader(Context context, Show show, ShowPresenter showPresenter) {
        this.context = context;
        this.show = show;
        this.showPresenter = showPresenter;
    }

    @Override
    protected Show doInBackground(Void... params) {
        Show result = null;
        try {
            TmdbApi api = new TmdbApi("secret");
            TvResultsPage searchTv = api.getSearch().searchTv(show.getName(), "en", 0);
            if (searchTv.getResults().size() > 0) {
                String thumbnailPath = searchTv.getResults().get(0).getPosterPath();
                URL thumbnailUrl = new URL(api.getConfiguration().getBaseUrl() + api.getConfiguration().getPosterSizes().get(3) + thumbnailPath);
                Log.d(this.getClass().getName(), "thumbnail URL " + thumbnailUrl);

                //Now that we have the URL, we can download the thumbnail and set it
                show.setThumbnailPath(downloadImage(thumbnailUrl, THUMBNAIL));
                //Backdrop follows
                String backdropPath = searchTv.getResults().get(0).getBackdropPath();
                URL backdropUrl = new URL(api.getConfiguration().getBaseUrl() + api.getConfiguration().getBackdropSizes().get(0) + backdropPath);
                show.setBackdropPath(downloadImage(backdropUrl, BACKDROP));

                //Store the submitted episode number in a variable.
                //At this point, only one episode is submitted.
                int episodeNumber = 0;
                for (Season s : show.getSeasonsAsArrayList()) {
                    for (Episode e : s.getEpisodesAsArrayList()) {
                        episodeNumber = e.getEpisode();
                    }
                }

                /*
                * Retrieve all seasons. Each season has a list with episodes.
                * This backdrop is displayed in the ShowDetailActivity.
                * */
                List<TvSeason> seasonList = searchTv.getResults().get(0).getSeasons();
                for (int i = 0; i < seasonList.size(); i++) { //i iterates season list
                    Season tmpSeason = new Season(seasonList.get(i).getSeasonNumber());
                    for (int j = 0; j < seasonList.get(i).getEpisodes().size(); j++) { //j iterates episode list
                        TvEpisode tvEpisode = seasonList.get(i).getEpisodes().get(j);
                        Episode episode = new Episode(tvEpisode.getEpisodeNumber());
                        if (tvEpisode.getEpisodeNumber() == episodeNumber) { //If the episode equals the submitted episode, set the watched date to today >>> episode is watched
                            episode.setDateWatched(new Date());
                        }
                        tmpSeason.addEpisode(episode);
                    }
                    tmpSeason.setMaxEpisodes(seasonList.get(i).getEpisodes().size());
                    tmpSeason.setLastWatched(new Date());
                    show.addSeason(tmpSeason);
                }
            }
        } catch (IOException iE) {
            iE.printStackTrace();
        }
        return show;
    }

    /**
     * Downloads an image using a given URL and concats the imagekind to the filename.
     * @param thumbnailUrl URL to download from
     * @param imageKind Thumbnail or Backdrop. This will be concatenated to the filename
     * @return filepath as String
     * @throws IOException
     */
    private String downloadImage(URL thumbnailUrl, String imageKind) throws IOException{
        String result = null;
        try {
            //Create connection
            HttpURLConnection urlConnection = (HttpURLConnection) thumbnailUrl.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoOutput(true);
            urlConnection.connect();

            //Set file
            File tmpFile;
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                tmpFile = context.getExternalCacheDir(); //Create file on SD
            } else {
                tmpFile = context.getCacheDir(); //Create file on phone
            }
            File file = new File(tmpFile, show.getName() + imageKind);
            file.createNewFile();

            //Create file
            FileOutputStream fos = new FileOutputStream(file);
            InputStream input = urlConnection.getInputStream();
            int totalSize = urlConnection.getContentLength();
            int downloadedSize = 0;
            byte[] buffer = new byte[1024];
            int bufferLength = 0;
            while ((bufferLength = input.read(buffer)) > 0) {
                fos.write(buffer, 0, bufferLength);
                downloadedSize += bufferLength;
                Log.i("Progress:", "downloadedSize:" + downloadedSize + "totalSize:" + totalSize);
            }
            fos.close();
            if (downloadedSize == totalSize) {
                result = file.getPath();
            }
        } catch (IOException iE) {
            throw new IOException(iE.getMessage());
        }
        return result;
    }

    @Override
    protected void onPostExecute(Show result) {
        super.onPostExecute(result);
        showPresenter.updateUI(result);
    }
}
