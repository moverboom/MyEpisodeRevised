package nl.krakenops.myepisode.datastorage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import nl.krakenops.myepisode.model.Episode;
import nl.krakenops.myepisode.model.Thumbnail;

/**
 * Created by Matthijs on 22/01/2016.
 */
public class SQLiteShowDAO extends SQLiteOpenHelper implements Serializable, ShowDAOInf {

    public SQLiteShowDAO(Context context) {
        super(context, "MyEpisode.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //table which holds thumbnails with backdrop
        db.execSQL("CREATE TABLE IF NOT EXISTS thumbnail " +
                "(ID INTEGER primary key autoincrement, " +
                "thumbnailPath VARCHAR NOT NULL);");

        db.execSQL("CREATE TABLE IF NOT EXISTS shows " +
                "(ID INTEGER primary key autoincrement, " +
                "lastWatched TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                "nextEpisodeAirs TIMESTAMP, " +
                "FK_ThumbnailID INTEGER," +
                "FOREIGN KEY(FK_ThumbnailID) REFERENCES thumbnail(ID));");

        db.execSQL("CREATE TABLE IF NOT EXISTS backdrop " +
                "(ID INTEGER primary key autoincrement, " +
                "backdropPath VARCHAR);");

        db.execSQL("CREATE TABLE IF NOT EXISTS season " +
                "(ID INTEGER primary key autoincrement, " +
                "season INTEGER NOT NULL, " +
                "maxEpisodes INTEGER, " +
                "FK_BackdropID INTEGER," +
                "FOREIGN KEU(FK_BackdropID) REFERENCES backdrop(ID));");

        db.execSQL("CREATE TABLE IF NOT EXISTS episode" +
                "(ID INTEGER primary key autoincrement, " +
                "episode INTEGER NOT NULL, " +
                "watchedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                "FK_SeasonID INTEGER NOT NULL, " +
                "FK_ShowID INTEGER NOT NULL" +
                "FOREIGN KEY(FK_SeasonID) REFERENCES season(ID), " +
                "FOREIGN KEY(FK_ShowID) REFERENCES shows(ID));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query;
        query = "DROP TABLE IF EXISTS episode; " +
                "DROP TABLE IF EXISTS shows; " +
                "DROP TABLE IF EXISTS season; " +
                "DROP TABLE IF EXISTS thumbnail " +
                "DROP TABLE IF EXISTS backdrop;";
        db.execSQL(query);
        onCreate(db);
        db.close();
    }

    @Override
    public boolean insertShow(Thumbnail thumbnail) {
        return false;
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
