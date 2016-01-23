package nl.krakenops.myepisode.datastorage;

import android.content.ContentValues;
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
    //Static constants which will be used throughout the class.
    //Database vars
    private static final String DATABASE_NAME = "MyEpisodeShows";
    private static final int DATABASE_VERSION = 1;

    //Tables
    private static final String TABLE_SHOWS = "shows";
    private static final String TABLE_EPISODE = "episode";
    private static final String TABLE_SEASON = "season";
    private static final String TABLE_THUMBNAIL = "thumbnail";
    private static final String TABLE_BACKDROP = "backdrop";

    //Columns
    private static final String COL_ID = "ID";

    private static final String COL_THUMBNAILPATH = "thumbnailPath";
    private static final String COL_NAME = "name";
    private static final String COL_LASTWATCHED = "lastWatched";
    private static final String COL_NEXTEPISODEAIRS = "nextEpisodeAirs";
    private static final String COL_FKTHUMBNAILID = "FK_ThumbnailID";
    private static final String COL_BACKDROPPATH = "backdropPath";
    private static final String COL_SEASON = "season";
    private static final String COL_MAXEPISODES = "maxEpisodes";
    private static final String COL_FKBACKDROPID = "FK_BackdropID";
    private static final String COL_FKSHOWID = "FK_ShowID";
    private static final String COL_EPISODE = "episode";
    private static final String COL_WATCHEDAT = "watchedAt";
    private static final String COL_FKSEASONID = "FK_SeasonID";

    public SQLiteShowDAO(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //table which holds thumbnails with backdrop
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_THUMBNAIL +
                "("+COL_ID+" INTEGER primary key autoincrement, " + COL_THUMBNAILPATH + "VARCHAR NOT NULL);");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_SHOWS +
                "("+COL_ID+" INTEGER primary key autoincrement, " +
                COL_NAME + "VARCHAR NOT NULL, " +
                COL_LASTWATCHED + "TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                COL_NEXTEPISODEAIRS + "TIMESTAMP, " +
                COL_FKTHUMBNAILID + "INTEGER," +
                "FOREIGN KEY("+COL_FKTHUMBNAILID+") REFERENCES "+TABLE_THUMBNAIL+"("+COL_ID+"));");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_BACKDROP +
                "("+COL_ID+" INTEGER primary key autoincrement, " +
                COL_BACKDROPPATH + "VARCHAR);");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_SEASON +
                "("+COL_ID+" INTEGER primary key autoincrement, " +
                COL_SEASON+ "INTEGER NOT NULL, " +
                COL_MAXEPISODES + "INTEGER, " +
                COL_FKBACKDROPID + "INTEGER, " +
                COL_FKSHOWID + "INTEGER, " +
                "FOREIGN KEY("+COL_FKBACKDROPID+") REFERENCES "+TABLE_BACKDROP+"("+COL_ID+")" +
                "FOREIGN KEY("+COL_FKSHOWID+") REFERENCES shows("+COL_ID+"));");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_EPISODE +
                "("+COL_ID+" INTEGER primary key autoincrement, " +
                COL_EPISODE + "INTEGER NOT NULL, " +
                COL_WATCHEDAT + "TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                COL_FKSEASONID + "INTEGER NOT NULL, " +
                "FOREIGN KEY("+COL_FKSEASONID+") REFERENCES "+TABLE_SEASON+"("+COL_ID+"));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query;
        query = "DROP TABLE IF EXISTS episode; " +
                "DROP TABLE IF EXISTS season; " +
                "DROP TABLE IF EXISTS backdrop; " +
                "DROP TABLE IF EXISTS shows " +
                "DROP TABLE IF EXISTS thumbnail;";
        db.execSQL(query);
        onCreate(db);
        db.close();
    }

    @Override
    public boolean insertShow(Thumbnail thumbnail) {
        SQLiteDatabase db = this.getWritableDatabase();

        //Creating ContentValue pairs
        ContentValues values = new ContentValues();
        values.put(COL_NAME, thumbnail.getName());

        // Inserting Row
        db.insert(TABLE_SHOWS, null, values);
        db.close();
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
