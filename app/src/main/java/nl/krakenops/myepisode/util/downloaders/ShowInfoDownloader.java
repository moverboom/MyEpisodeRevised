package nl.krakenops.myepisode.util.downloaders;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.net.ssl.HttpsURLConnection;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TvResultsPage;
import info.movito.themoviedbapi.model.core.ResponseStatusException;
import info.movito.themoviedbapi.model.tv.TvEpisode;
import info.movito.themoviedbapi.model.tv.TvSeason;
import nl.krakenops.myepisode.model.Episode;
import nl.krakenops.myepisode.model.Season;
import nl.krakenops.myepisode.model.Show;
import nl.krakenops.myepisode.presenter.ShowPresenterImpl;

/**
 * This class extends AsyncTask and downloads all show information needed.
 * It runs in the background on a different thread and placeholders are used where needed.
 * Once this class finishes downloading everything, it updates the show.
 * Created by Matthijs on 24/01/2016.
 */
public class ShowInfoDownloader extends AsyncTask<Void, Void, Show> {
    private Context context;
    private Show show;
    private ShowPresenterImpl showPresenter;
    private static final String THUMBNAIL = "thumbnail";
    private static final String BACKDROP = "backdrop";

    public ShowInfoDownloader(Context context, Show show, ShowPresenterImpl showPresenter) {
        this.context = context;
        this.show = show;
        this.showPresenter = showPresenter;
    }

    @Override
    protected Show doInBackground(Void... params) {
        try {
            TmdbApi api = new TmdbApi("secret");
            TvResultsPage searchTv = api.getSearch().searchTv(show.getName(), "en", 0);
            if (searchTv.getResults().size() > 0) {
                String thumbnailPath = searchTv.getResults().get(0).getPosterPath();
                URL thumbnailUrl = new URL(api.getConfiguration().getSecureBaseUrl() + api.getConfiguration().getPosterSizes().get(3) + thumbnailPath);
                Log.d(this.getClass().getName(), "thumbnail URL " + thumbnailUrl);

                //Now that we have the URL, we can download the thumbnail and set it
                show.setThumbnailPath(downloadImage(thumbnailUrl, THUMBNAIL));
                //Backdrop follows
                String backdropPath = searchTv.getResults().get(0).getBackdropPath();
                URL backdropUrl = new URL(api.getConfiguration().getSecureBaseUrl() + api.getConfiguration().getBackdropSizes().get(0) + backdropPath);
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
                int id = searchTv.getResults().get(0).getId();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                try {
                    for (int i = 0; i < api.getTvSeries().getSeries(id, "en").getNumberOfSeasons(); i++) { //i iterates season list
                        Season tmpSeason = new Season(i+1);
                        Log.d(this.getClass().getName(), "Created new Season with number" + tmpSeason.getSeason());
                        List<TvEpisode> episodeList = api.getTvSeasons().getSeason(id, i, "en").getEpisodes();
                        for (int j = 0; j < episodeList.size(); j++) { //j iterates episode list
                            TvEpisode tvEpisode = episodeList.get(j);
                            Episode episode = new Episode(tvEpisode.getEpisodeNumber());
                            episode.setAirDate(formatter.parse((tvEpisode.getAirDate() != null) ? tvEpisode.getAirDate() : "1900/01/01"));
                            Log.d(this.getClass().getName(), "Created new Episode with number" + episode.getEpisode() + " and airdate " + formatter.format(episode.getAirDate()));
                            if (tvEpisode.getEpisodeNumber() == episodeNumber) { //If the episode equals the submitted episode, set the watched date to today >>> episode is watched
                                episode.setDateWatched(new Date());
                            }
                            tmpSeason.addEpisode(episode);
                        }
                        tmpSeason.setMaxEpisodes(episodeList.size());
                        show.addSeason(tmpSeason);
                    }
                } catch (ParseException pE) {
                    pE.printStackTrace();
                } catch (ResponseStatusException rE) {
                    Log.e(this.getClass().getName(), "Error status code"+ String.valueOf(rE.getResponseStatus().getStatusCode()));
                    //Error code seems to be 34 for every ResponseStatusException. This translates to a resource which could not be found
                    //In this case Episode information.
                    //A possible solution here would be to NOT download any further Episode information and just take user input.
                    //Maybe display a message for this????
                    rE.printStackTrace();
                    //API error, Do something with this
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
            HttpsURLConnection urlConnection = (HttpsURLConnection) thumbnailUrl.openConnection();
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
        Log.d(this.getClass().getName(), "onPostExecute() called with showID = " + result.getId());
        showPresenter.updateUI(result);
    }
}
