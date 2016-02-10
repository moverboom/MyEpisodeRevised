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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import nl.krakenops.myepisode.model.Episode;
import nl.krakenops.myepisode.model.Season;
import nl.krakenops.myepisode.model.Show;
import nl.krakenops.myepisode.util.downloaders.ShowInfoDownloader;

/**
 * Created by Matthijs on 22/01/2016.
 */
public class SQLiteShowDAO implements ShowDAOInf {
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

    /**
     * This method updates a Show using the information from the given Show object as argument.
     * It returns the same show object, but with the ID set.
     * @param show Thumbnail to use for show data
     * @return
     */
    @Override
    public Show insertShow(Show show) {
        //First store user submitted data in database
        //Inset show
        SQLiteStatement statement = db.compileStatement("INSERT INTO " + dbHelper.TABLE_SHOWS +
                "(" + dbHelper.COL_NAME + ", " + dbHelper.COL_ISFAVORITE + ") VALUES (?, ?);");
        statement.bindString(1, show.getName());
        //Ternary operator. If the show is a favorite, assign 1
        long isFavorite = (show.isFavorite()) ? 1:0;
        statement.bindLong(2, isFavorite);
        show.setId(statement.executeInsert()); //Method return the ID which we can set


        //Insert season and episode
        SQLiteStatement seasonStmt = db.compileStatement("INSERT INTO " + dbHelper.TABLE_SEASON +
                "(" + dbHelper.COL_SEASON + ", " + dbHelper.COL_FKSHOWID + ") VALUES (?, ?);");
        SQLiteStatement episodeStmt = db.compileStatement("INSERT INTO " + dbHelper.TABLE_EPISODE +
                "(" + dbHelper.COL_EPISODE + ", " + dbHelper.COL_FKSEASONID + ", " + dbHelper.COL_WATCHEDAT + ") VALUES (?, ?, ?);");
        for (Season s : show.getSeasonsAsArrayList()) {
            seasonStmt.bindString(1, String.valueOf(s.getSeason()));
            seasonStmt.bindLong(2, show.getId());
            show.getSeason(s.getSeason()).setId(seasonStmt.executeInsert());
            for (Episode e : s.getEpisodesAsArrayList()) {
                episodeStmt.bindString(1, String.valueOf(e.getEpisode()));
                episodeStmt.bindLong(2, show.getSeason(s.getSeason()).getId());
                show.getSeason(s.getSeason()).getEpisode(e.getEpisode()).setId(episodeStmt.executeInsert());
            }
        }
        return show;
    }

    /**
     * Used to update a show which was inserted for the first time.
     * @param show Show to update
     * @return true if success
     */
    @Override
    public boolean updateInsertShow(Show show) {
        boolean result = false;

        //Insert thumbnailPath and store ID
        SQLiteStatement thumbStmt = db.compileStatement("INSERT INTO " + dbHelper.TABLE_THUMBNAIL +
                "(" + dbHelper.COL_THUMBNAILPATH + ") VALUES (?);");
        thumbStmt.bindString(1, show.getThumbnailPath());
        long thumbID = thumbStmt.executeInsert();

        //Insert backdropPath and store ID
        SQLiteStatement backdropStmt = db.compileStatement("INSERT INTO " + dbHelper.TABLE_BACKDROP +
                "(" + dbHelper.COL_BACKDROPPATH + ") VALUES (?);");
        backdropStmt.bindString(1, show.getBackdropPath());
        long backdropID = backdropStmt.executeInsert();

        //Update show thumbnail and backdrop paths
        SQLiteStatement updateStmt = db.compileStatement("UPDATE " + dbHelper.TABLE_SHOWS +
                "SET " + dbHelper.COL_FKTHUMBNAILID + " = ?, " + dbHelper.COL_FKBACKDROPID + " = ? " +
                "WHERE " + dbHelper.COL_ID + " = " + show.getId() + ";");
        updateStmt.bindLong(1, thumbID);
        updateStmt.bindLong(2, backdropID);
        updateStmt.executeUpdateDelete();

        //Update episodes and seasons
        //First we compile all needed statements, which will later be used in a for loop
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
        SQLiteStatement seasonStmt = db.compileStatement("INSERT INTO " + dbHelper.TABLE_SEASON +
                "(" + dbHelper.COL_SEASON + ", " +
                dbHelper.COL_FKSHOWID + ") VALUES (?, ?, ?);");
        SQLiteStatement episodeStmt = db.compileStatement("INSERT INTO " + dbHelper.TABLE_EPISODE +
                "(" + dbHelper.COL_EPISODE + ", " +
                dbHelper.COL_AIRDATE + ", " +
                dbHelper.COL_FKSEASONID + ") VALUES (?, ?, ?);");
        //Separate statement for the episode which has already been watched
        SQLiteStatement watchedEpisodeStmt = db.compileStatement("UPDATE " + dbHelper.TABLE_EPISODE +
                "SET " + dbHelper.COL_AIRDATE + " = ? " +
                "WHERE " + dbHelper.COL_ID + " = ?;");



        return result;
    }

    /**
     * Returns a List with all recently watched shows as as Thumbnail
     * Recently = 7 days ago or less
     *
     * @return List with Thumbnails
     */
    @Override
    public ArrayList<Show> getRecentShows() {
        return null;
    }

    /**
     * Returns a List with all favorite shows as as Thumbnail
     *
     * @return List with Thumbnails
     */
    @Override
    public ArrayList<Show> getFavShows() {
        return null;
    }

    /**
     * Returns a List with all watched shows as as Thumbnail
     *
     * @return List with Thumbnails
     */
    @Override
    public ArrayList<Show> getAllShows() {
        return null;
    }

    /**
     * Returns a show as Thumbnail for a given id
     *
     * @param id ID of the show to find
     * @return show as Thumbnail
     */
    @Override
    public Show getShowByID(int id) {
        return null;
    }

    /**
     * Return a show as a Thumbnail for a given name
     *
     * @param name name of the show to find
     * @return show as Thumbnail
     */
    @Override
    public Show getShowByName(String name) {
        return null;
    }

    /**
     * Sets whether a show is a favorite or not using the given show object
     *
     * @param show     Show to set
     * @return true if success
     */
    @Override
    public boolean setShowFavorite(Show show) {
        return false;
    }

    /**
     * Updates the episode watched.
     * Uses the available Season and Episode models to get the correct information.
     *
     * @param show Show to update
     * @return true is success
     */
    @Override
    public boolean updateShowEpisodes(Show show) {
        return false;
    }

    public boolean updateShowThumbnail(Show show) {
        return false;
    }

    /**
     * Updates the backdropPath set for a show.
     * Retrieves the new path from the show object
     *
     * @param show Show to update
     * @return true if success
     */
    @Override
    public boolean updateShowBackdrop(Show show) {
        return false;
    }

    /**
     * Removes a show
     *
     * @param show Show to remove
     * @return true if success
     */
    @Override
    public boolean removeShow(Show show) {
        return false;
    }

}
