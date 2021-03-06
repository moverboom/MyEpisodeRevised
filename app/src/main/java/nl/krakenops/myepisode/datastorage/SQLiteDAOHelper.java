package nl.krakenops.myepisode.datastorage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.Serializable;

/**
 * Created by Matthijs on 24/01/2016.
 */
public class SQLiteDAOHelper extends SQLiteOpenHelper {
    //Static constants which will be used throughout the class.
    //Database vars
    protected static final String DATABASE_NAME = "MyEpisodeShows";
    protected static final int DATABASE_VERSION = 4;

    //Tables
    protected static final String TABLE_SHOWS = "shows";
    protected static final String TABLE_EPISODE = "episodes";
    protected static final String TABLE_SEASON = "seasons";
    protected static final String TABLE_THUMBNAIL = "thumbnails";
    protected static final String TABLE_BACKDROP = "backdrops";

    //Columns
    protected static final String COL_ID = "ID";

    protected static final String COL_THUMBNAILPATH = "thumbnailPath";
    protected static final String COL_NAME = "name";
    protected static final String COL_ISFAVORITE = "isFavorite";
    protected static final String COL_LASTWATCHEDAT = "lastWatchedAt";
    protected static final String COL_WATCHEDAT = "watchedAt";
    protected static final String COL_AIRDATE = "airDate";
    protected static final String COL_FKTHUMBNAILID = "FK_ThumbnailID";
    protected static final String COL_BACKDROPPATH = "backdropPath";
    protected static final String COL_SEASON = "season";
    protected static final String COL_FKBACKDROPID = "FK_BackdropID";
    protected static final String COL_FKSHOWID = "FK_ShowID";
    protected static final String COL_EPISODE = "episode";
    protected static final String COL_FKSEASONID = "FK_SeasonID";

    public SQLiteDAOHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //table which holds thumbnails with backdrop
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_THUMBNAIL +
                "("+COL_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_THUMBNAILPATH + " VARCHAR NOT NULL);");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_BACKDROP +
                "("+COL_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_BACKDROPPATH + " VARCHAR);");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_SHOWS +
                "("+COL_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NAME + " VARCHAR NOT NULL, " +
                COL_ISFAVORITE + " INTEGER DEFAULT 0, " +
                COL_LASTWATCHEDAT + " VARCHAR, " +
                COL_FKTHUMBNAILID + " INTEGER, " +
                COL_FKBACKDROPID + " INTEGER, " +
                "FOREIGN KEY("+COL_FKTHUMBNAILID+") REFERENCES "+TABLE_THUMBNAIL+"("+COL_ID+")" +
                "FOREIGN KEY("+COL_FKBACKDROPID+") REFERENCES "+TABLE_BACKDROP+"("+COL_ID+"));");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_SEASON +
                "(" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_SEASON + " INTEGER NOT NULL, " +
                COL_FKSHOWID + " INTEGER, " +
                "FOREIGN KEY(" + COL_FKSHOWID + ") REFERENCES shows(" + COL_ID + "));");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_EPISODE +
                "(" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_EPISODE + " INTEGER NOT NULL, " +
                COL_WATCHEDAT + " VARCHAR, " +
                COL_AIRDATE + " VARCHAR, " +
                COL_FKSEASONID + " INTEGER NOT NULL, " +
                "FOREIGN KEY(" + COL_FKSEASONID + ") REFERENCES " + TABLE_SEASON + "(" + COL_ID + "));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query;
        query = "DROP TABLE IF EXISTS episodes; " +
                "DROP TABLE IF EXISTS seasons; " +
                "DROP TABLE IF EXISTS backdrops; " +
                "DROP TABLE IF EXISTS thumbnails; " +
                "DROP TABEL IF EXISTS shows;";
        db.execSQL(query);
        onCreate(db);
        db.close();
    }
}
