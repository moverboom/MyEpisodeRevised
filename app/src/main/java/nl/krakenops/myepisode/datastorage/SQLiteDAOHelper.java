package nl.krakenops.myepisode.datastorage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.Serializable;

/**
 * Created by Matthijs on 24/01/2016.
 */
public class SQLiteDAOHelper extends SQLiteOpenHelper implements Serializable {
    private Context context;
    //Static constants which will be used throughout the class.
    //Database vars
    protected static final String DATABASE_NAME = "MyEpisodeShows";
    protected static final int DATABASE_VERSION = 1;

    //Tables
    protected static final String TABLE_SHOWS = "shows";
    protected static final String TABLE_EPISODE = "episode";
    protected static final String TABLE_SEASON = "season";
    protected static final String TABLE_THUMBNAIL = "thumbnail";
    protected static final String TABLE_BACKDROP = "backdrop";

    //Columns
    protected static final String COL_ID = "ID";

    protected static final String COL_THUMBNAILPATH = "thumbnailPath";
    protected static final String COL_NAME = "name";
    protected static final String COL_LASTWATCHED = "lastWatched";
    protected static final String COL_NEXTEPISODEAIRS = "nextEpisodeAirs";
    protected static final String COL_FKTHUMBNAILID = "FK_ThumbnailID";
    protected static final String COL_BACKDROPPATH = "backdropPath";
    protected static final String COL_SEASON = "season";
    protected static final String COL_MAXEPISODES = "maxEpisodes";
    protected static final String COL_FKBACKDROPID = "FK_BackdropID";
    protected static final String COL_FKSHOWID = "FK_ShowID";
    protected static final String COL_EPISODE = "episode";
    protected static final String COL_WATCHEDAT = "watchedAt";
    protected static final String COL_FKSEASONID = "FK_SeasonID";

    public SQLiteDAOHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //table which holds thumbnails with backdrop
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_THUMBNAIL +
                "("+COL_ID+" INTEGER primary key autoincrement, " + COL_THUMBNAILPATH + "VARCHAR NOT NULL);");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_BACKDROP +
                "("+COL_ID+" INTEGER primary key autoincrement, " +
                COL_BACKDROPPATH + "VARCHAR);");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_SHOWS +
                "("+COL_ID+" INTEGER primary key autoincrement, " +
                COL_NAME + "VARCHAR NOT NULL, " +
                COL_LASTWATCHED + "TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                COL_NEXTEPISODEAIRS + "TIMESTAMP, " +
                COL_FKTHUMBNAILID + "INTEGER," +
                COL_FKBACKDROPID + "INTEGER, " +
                "FOREIGN KEY("+COL_FKTHUMBNAILID+") REFERENCES "+TABLE_THUMBNAIL+"("+COL_ID+")" +
                "FOREIGN KEY("+COL_FKBACKDROPID+") REFERENCES "+TABLE_BACKDROP+"("+COL_ID+"));");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_SEASON +
                "(" + COL_ID + " INTEGER primary key autoincrement, " +
                COL_SEASON + "INTEGER NOT NULL, " +
                COL_MAXEPISODES + "INTEGER, " +
                COL_FKSHOWID + "INTEGER, " +
                "FOREIGN KEY(" + COL_FKSHOWID + ") REFERENCES shows(" + COL_ID + "));");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_EPISODE +
                "(" + COL_ID + " INTEGER primary key autoincrement, " +
                COL_EPISODE + "INTEGER NOT NULL, " +
                COL_WATCHEDAT + "TIMESTAMP" +
                COL_FKSEASONID + "INTEGER NOT NULL, " +
                "FOREIGN KEY(" + COL_FKSEASONID + ") REFERENCES " + TABLE_SEASON + "(" + COL_ID + "));");
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

    public Context getContext() {
        return context;
    }
}
