package nl.krakenops.myepisode.datastorage;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

/**
 * Created by Matthijs on 22/01/2016.
 */
public class SQLiteDBController extends SQLiteOpenHelper implements Serializable {
    private String TVShow;
    private Context context;
    private SQLiteDatabase db;
    //Fixed SQL statements
    private final String selectAll = "SELECT Name, thumbnail, Episode, Season, DateWatched FROM shows s INNER JOIN watchedfrom w ON s.ID = w.ID INNER JOIN thumbnails t ON s.ID = t.ID";
    private final String selectRecent = "SELECT Name, thumbnail, Episode, Season, DateWatched FROM shows s INNER JOIN watchedfrom w ON s.ID = w.ID INNER JOIN thumbnails t ON s.ID = t.ID LIMIT 10";
    private final String selectShows = "SELECT Name, thumbnail, COUNT(Episode), COUNT(DISTINCT Season), DateWatched FROM shows s INNER JOIN watchedfrom w ON s.ID = w.ID INNER JOIN thumbnails t ON s.ID = t.ID GROUP BY Name";

    public SQLiteDBController(Context context) {
        super(context, "MyEpisode.db", null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //table which holds a season
        String query;
        query = "CREATE TABLE IF NOT EXISTS season " +
                "(ID INTEGER primary key autoincrement, " +
                "Season INTEGER NOT NULL," +
                "MaxEpisodes INTEGER);";
        db.execSQL(query);

        //table which holds a watched episode with reference to the season
        String episodeQuery;
        episodeQuery = "CREATE TABLE IF NOT EXISTS episode " +
                "(ID INTEGER primary key autoincrement, " +
                "Episode INTEGER NOT NULL, " +
                "WatchedAt DATETIME, " +
                "FK_Season INTEGER NOT NULL" +
                "FOREIGN KEY(FK_Season) REFERENCES season(ID));";
        db.execSQL(episodeQuery);

        //table which holds thumbnails with backdrop
        String thumbQuery;
        thumbQuery = "CREATE TABLE IF NOT EXISTS thumbnail " +
                "(ID INTEGER primary key autoincrement, " +
                "ThumbnailPath VARCHAR NOT NULL, " +
                "BackdropPath VARHCHAR);";
        db.execSQL(thumbQuery);

        //table which holds shows with reference to episodes and a thumbnail
        String showQuery;
        showQuery = "CREATE TABLE IF NOT EXISTS shows " +
                "(ID INTEGER primary key autoincrement, " +
                "Name VARCHAR NOT NULL, " +
                "isFavorite INTEGER DEFAULT 0, " +
                "lastWatched DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                "nextEpisodeAirs DATETIME, " +
                "FK_Episode INTEGER NOT NULL, " +
                "FK_Thumbnail INTEGER, " +
                "FOREIGN KEY(FK_Episode) REFERENCES episode(ID)," +
                "FOREIGN KEY(FK_Thumbnail) REFERENCES thumbnail(ID));";
        db.execSQL(showQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query;
        query = "DROP TABLE IF EXISTS shows; " +
                "DROP TABLE IF EXISTS thumbnail " +
                "DROP TABLE IF EXISTS episode " +
                "DROP TABLE IF EXISTS season;";
        db.execSQL(query);
        onCreate(db);
        db.close();
    }


    /**
     * Insert values into SQLite db
     *
     * @param queryValues
     */
    public void insertValues(HashMap<String, String> queryValues) {
        /*create new Date and format it to local. Then esacpe it*/
        Date date = new Date();
        DateFormat dateLocal = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault());
        String formattedDate = DatabaseUtils.sqlEscapeString(dateLocal.format(date));

        TVShow = DatabaseUtils.sqlEscapeString(queryValues.get("TVShow"));
        Log.i("Get Record", "[" + TVShow + "]");
//        StringBuilder strEscape = new StringBuilder();
        db = this.getWritableDatabase();

        //check if there already is a show with this name. If not, call thumbnaildownloader and insert showname
        Cursor a = db.rawQuery(("SELECT ID FROM shows WHERE Name = "+TVShow+";"), null);
        if(!a.moveToFirst()) {
            //no show found
            //Insert new show and get ID from new show record
            String insertShow = "INSERT INTO shows(Name) VALUES("+TVShow+");";
            db.execSQL(insertShow);
            String selectID = "SELECT ID FROM shows WHERE Name = "+TVShow+";";
            Cursor id = db.rawQuery(selectID, null);
            if(id.moveToFirst()) {
                int showID = id.getInt(id.getColumnIndex("ID")); //Assign new ID to showID

                /*Insert into record into thumbnails with placeholder and foreign key to ensure valid data*/
                String insertPlaceholder = "INSERT INTO thumbnails(ID, thumbnail) VALUES('"+showID+"', 'placeholder');";
                db.execSQL(insertPlaceholder);

                /*Call thumbnaildownloader and pass showID as foreign key*/
//                ThumbnailDownloader thumbnailDownloader = new ThumbnailDownloader(db, context, TVShow, showID);
//                thumbnailDownloader.execute();

                /*insert new values into watchedfrom with showID as foreign key*/
                String insertValues = "INSERT INTO watchedfrom(ID, Episode, Season, DateWatched) VALUES " +
                        "('" + showID + "', '" + queryValues.get("Episode") + "', '" + queryValues.get("Season") + "', " + formattedDate + ");";
                db.execSQL(insertValues);
            }

        } else {
            //show found, so insert episode, season and date to watchedfrom with reference id
            String insertValues = "INSERT INTO watchedfrom(ID, Episode, Season, DateWatched) VALUES " +
                    "("+a.getInt(a.getColumnIndex("ID"))+", "+queryValues.get("Episode")+", "+queryValues.get("Season")+", "+ formattedDate+");";
            Log.i("Fetched ID", "[" + a.getInt(a.getColumnIndex("ID")) + "]");
            db.execSQL(insertValues);
            db.close();
        }
    }

    /**
     * Get values from SQLite DB as ArrayList
     *
     * @return
     */
//    public ArrayList<HashMap<String, String>> getRecent () {
//        ArrayList<HashMap<String, String>> episodeList;
//        db = this.getWritableDatabase();
//        SQLiteDbSelect sqLiteDbSelect = new SQLiteDbSelect(context, db, selectRecent);
//        episodeList = sqLiteDbSelect.getEpisodes();
//        db.close();
//        return episodeList;
//    }
//
//    public ArrayList<HashMap<String, String>> getShows() {
//        ArrayList<HashMap<String, String>> episodeList;
//        db = this.getWritableDatabase();
//        SQLiteDbSelect sqLiteDbSelect = new SQLiteDbSelect(context, db, selectShows);
//        episodeList = sqLiteDbSelect.getEpisodes();
//        db.close();
//        return episodeList;
//    }

    public void removeWatched(String rEpisode, String rSeason, String rTVShow, String rDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("watched", "Episode = '"+rEpisode+"' AND Season = '"+rSeason+"' AND TVShow = '"+rTVShow+"' AND DateWatched = '"+rDate+"';", null);
        db.close();
//        Log.i("response", "[" + get() + "]");
    }

}
