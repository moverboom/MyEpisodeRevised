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

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TvResultsPage;
import nl.krakenops.myepisode.model.Show;

/**
 * Created by Matthijs on 24/01/2016.
 */
public class ShowInfoDownloader extends AsyncTask<Void, Void, Boolean> {
    private Context context;
    private Show show;
    private SQLiteDatabase db;

    public ShowInfoDownloader(Context context, Show show, SQLiteDatabase db) {
        this.context = context;
        this.show = show;
        this.db = db;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        boolean result = false;
        try {
            TmdbApi api = new TmdbApi("361e460bdf4209b48c8db9b6ad0ea321");
            TvResultsPage searchTv = api.getSearch().searchTv(show.getName(), "en", 0);
            if (searchTv.getResults().size() > 0) {
                String thumbnailPath = searchTv.getResults().get(0).getPosterPath();
                URL thumbnailUrl = new URL(api.getConfiguration().getBaseUrl() + api.getConfiguration().getPosterSizes().get(3) + thumbnailPath);
                Log.d(this.getClass().getName(), "thumbnail URL " + thumbnailUrl);

                //Now that we have the URL, we can download it
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
                File file = new File(tmpFile, show.getName());
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
                    Log.i("Progress:","downloadedSize:"+downloadedSize+"totalSize:"+ totalSize) ;
                }
                fos.close();
                if (downloadedSize == totalSize) {
                    String filePath = file.getPath();
                    show.setThumbnailPath(filePath);
                    result = true;
                }

            }
        } catch (MalformedURLException mE) {
            mE.printStackTrace();
        } catch (IOException iE) {
            iE.printStackTrace();
        }
        return result;
    }
}
