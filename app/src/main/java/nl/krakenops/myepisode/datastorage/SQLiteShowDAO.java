package nl.krakenops.myepisode.datastorage;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import nl.krakenops.myepisode.model.Episode;
import nl.krakenops.myepisode.model.Thumbnail;

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
    public boolean insertShow(Thumbnail thumbnail) {
        boolean result = false;
        //Creating ContentValue pairs
//        ContentValues values = new ContentValues();
//        values.put(dbHelper.COL_NAME, thumbnail.getName());
//
//        // Inserting Row
//        db.insert(dbHelper.TABLE_SHOWS, null, values);
//        db.close();


        return result;
    }

    @Override
    public ArrayList<Thumbnail> getRecentShows() {
        return null;
    }

    @Override
    public ArrayList<Thumbnail> getFavShows() {
        return null;
    }

    @Override
    public ArrayList<Thumbnail> getAllShows() {
        return null;
    }

    @Override
    public Thumbnail getShowByID(int id) {
        return null;
    }

    @Override
    public Thumbnail getShowByName(String name) {
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
