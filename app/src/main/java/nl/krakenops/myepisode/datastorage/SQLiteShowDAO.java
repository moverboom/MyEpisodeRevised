package nl.krakenops.myepisode.datastorage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;

import nl.krakenops.myepisode.model.Episode;
import nl.krakenops.myepisode.model.Show;

/**
 * Created by Matthijs on 22/01/2016.
 */
public class SQLiteShowDAO implements Serializable, ShowDAOInf {
    private SQLiteDatabase db;
    private SQLiteDAOHelper dbHelper;

    public SQLiteShowDAO(Context context) {
        dbHelper = new SQLiteDAOHelper(context);
    }

    public void open() throws SQLException {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    @Override
    public boolean insertShow(Show show) {
        boolean result = false;
        //Creating ContentValue pairs
//        ContentValues values = new ContentValues();
//        values.put(dbHelper.COL_NAME, thumbnail.getName());
//
//        // Inserting Row
//        db.insert(dbHelper.TABLE_SHOWS, null, values);
//        db.close();

        //First store user submitted data in database

        //Retrieve additional information from TMDB API in AsyncTask
        //The AsyncTask updates the earlier submitted information

        return result;
    }

    @Override
    public ArrayList<Show> getRecentShows() {
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
