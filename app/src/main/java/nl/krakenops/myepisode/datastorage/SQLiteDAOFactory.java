package nl.krakenops.myepisode.datastorage;

import android.content.Context;

/**
 * This class represents the factory for SQLiteDAO's.
 * Created by Matthijs on 22/01/2016.
 */
public class SQLiteDAOFactory extends DAOFactory {
    /**
     * Returns a ShowDAOInf
     * @return ShowDAOInf
     */
    @Override
    public ShowDAOInf getShowDAO(Context context) {
        return new SQLiteShowDAO(context);
    }
}
