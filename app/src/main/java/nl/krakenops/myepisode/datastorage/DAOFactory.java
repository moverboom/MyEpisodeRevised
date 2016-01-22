package nl.krakenops.myepisode.datastorage;

import android.util.Log;

import java.io.Serializable;

/**
 * Created by Matthijs on 22/01/2016.
 */
public abstract class DAOFactory implements Serializable {

    /**
     * This method creates an isntance of the specified factory class
     * and returns the instance to the caller. The class of the instance creatd has
     * to extends DAOFactory, which forces the implementation of abstract methods to get domain
     * objects from the factory
     *
     * @param factoryClassName the string indicating the complete package name of the
     *                         factory class that must be loaded
     * @return An object instance of the requested factory class.
     */
    public static DAOFactory getDAOFactory(String factoryClassName) {
        DAOFactory factoryInstance = null;
        try {
            Log.v("DAOFactory", "Loading class " + factoryClassName);
            Class<?> factoryClass = Class.forName(factoryClassName);
            factoryInstance = (DAOFactory) factoryClass.newInstance();
        } catch (ClassNotFoundException cE) {
            Log.e("DAOFactory", "Class not found");
        } catch (InstantiationException iE) {
            Log.e("DAOFactory", "Unable to instantiate class" + iE.getMessage());
        } catch (IllegalAccessException ilE) {
            Log.e("DAOFactory", "Not allowed to access class");
        }
        return factoryInstance;
    }

    /**
     * Create and return a DAO for the specified datasource implementation
     * @return ShowDAOInf the DAO instance which implements this interface
     */
    public abstract ShowDAOInf getShowDAO();
}
