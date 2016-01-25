package nl.krakenops.myepisode.datastorage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import nl.krakenops.myepisode.model.Episode;
import nl.krakenops.myepisode.model.Show;
import nl.krakenops.myepisode.util.downloaders.ShowInfoDownloader;

/**
 * Created by Matthijs on 22/01/2016.
 */
public class SQLiteShowDAO implements Serializable, ShowDAOInf {
    private SQLiteDatabase db;
    private SQLiteDAOHelper dbHelper;

    public SQLiteShowDAO(Context context) {
        dbHelper = new SQLiteDAOHelper(context);
    }

    /**
     * Opens a writable database
     * @throws SQLException
     */
    public void open() throws SQLException {
        db = dbHelper.getWritableDatabase();
    }

    /**
     * Opens a readable database
     * @throws SQLException
     */
    public void openReadable() throws SQLException {
        db = dbHelper.getReadableDatabase();
    }

    /**
     * Closes the current database connection
     * Must be called when operation is complete
     */
    public void close() {
        dbHelper.close();
    }

    @Override
    public boolean containsShow(String name) {
        boolean result = false;
        SQLiteStatement statement = db.compileStatement("SELECT COUNT(*) FROM "+dbHelper.TABLE_SHOWS+" WHERE "+dbHelper.COL_NAME+" = ?;");
        statement.bindString(1, name);
        long amount = statement.simpleQueryForLong();
        if (amount > 0) {
            result = true;
        }
        return result;
    }

    @Override
    public boolean insertShow(Show show) {
        boolean result = false;
        //First store user submitted data in database
        SQLiteStatement statement = db.compileStatement("INSERT INTO " + dbHelper.TABLE_SHOWS +
                "(" + dbHelper.COL_NAME + ", "
                + dbHelper.COL_LASTWATCHED + ") VALUES (?, ?);");
        statement.bindString(1, show.getName());
        statement.bindString(2, show.getLastWatched().toString());
        long insertID = statement.executeInsert();
        if (containsShow(show.getName())) {
//           String query = "SELECT "+dbHelper.COL_ID+" FROM "+dbHelper.TABLE_SHOWS+" WHERE "+dbHelper.COL_NAME+" = ?;";
//           Cursor c = db.rawQuery(query, new String[]{show.getName()});
//           if (c.moveToFirst()) {
//               show.setId(c.getInt(0));
//           }
            Log.i(this.getClass().getName(), String.valueOf(insertID));
            show.setId(insertID);
            //Retrieve additional information from TMDB API in AsyncTask
            //The AsyncTask updates the earlier submitted information
            //It calls methods from this class when it has new data
            ShowInfoDownloader showInfoDownloader = new ShowInfoDownloader(dbHelper.getContext(), show, this);
            showInfoDownloader.execute();
            result = true;
        }
        return result;
    }

    @Override
    public ArrayList<Show> getRecentShows() {
        db = dbHelper.getReadableDatabase();
        return null;
    }

    @Override
    public ArrayList<Show> getFavShows() {
        return null;
    }

    @Override
    public ArrayList<Show> getAllShows() {
        return null;
    }

    @Override
    public Show getShowByID(int id) {
        return null;
    }

    @Override
    public Show getShowByName(String name) {
        return null;
    }

    @Override
    public boolean setShowFavoriteById(int id, boolean favorite) {
        return false;
    }

    @Override
    public boolean setShowFavoriteByName(String name, boolean favorite) {
        return false;
    }

    @Override
    public boolean updateShowEpisodesById(int id, Episode episode) {
        return false;
    }

    @Override
    public boolean updateShowEpisodeByName(String name, Episode episode) {
        return false;
    }

    /**
     * Updates all information related to a show.
     * Should only be used when the show is inserted for the first time.
     * @param show the show to update as Show
     * @return true if success
     */
    @Override
    public boolean updateShow(Show show) {
        //First insert thumbnail and backdrop
        //Then set refs in Shows
        
        return false;
    }

    @Override
    public boolean removeEpisodeFromShowById(int id, Episode episode) {
        return false;
    }

    @Override
    public boolean removeEpisodeFromShowByName(String name, Episode episode) {
        return false;
    }

    @Override
    public boolean removeShowById(int id) {
        return false;
    }

    @Override
    public boolean removeShowByName(String name) {
        return false;
    }
}
