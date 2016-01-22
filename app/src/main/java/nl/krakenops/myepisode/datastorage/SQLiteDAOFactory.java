package nl.krakenops.myepisode.datastorage;

import android.content.Context;

/**
 * This class represents the factory for SQLiteDAO's.
 * Created by Matthijs on 22/01/2016.
 */
public class SQLiteDAOFactory extends DAOFactory {
    private Context context;

    /**
     * Used to set the Context attribute. SQLiteOpenHelper requires Context
     * @param context
     */
    public void setContext(Context context) {
        this.context = context;
    }

    /**
     * Returns a ShowDAOInf
     * @return ShowDAOInf
     */
    @Override
    public ShowDAOInf getShowDAO() {
        return new SQLiteShowDAO(context);
    }
}
