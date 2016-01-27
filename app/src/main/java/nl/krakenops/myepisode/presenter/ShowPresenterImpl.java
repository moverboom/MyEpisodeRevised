package nl.krakenops.myepisode.presenter;

import android.content.Context;

import java.util.concurrent.ExecutionException;

import nl.krakenops.myepisode.model.Show;
import nl.krakenops.myepisode.util.downloaders.ShowInfoDownloader;
import nl.krakenops.myepisode.view.adapters.ViewPagerAdapter;

/**
 * Created by Matthijs on 19/01/2016.
 */
public class ShowPresenterImpl extends ShowPresenterAbstract {
    //Android specific variables
    private Context context;
    private ViewPagerAdapter view = null;

    /**
     * Constructor with Android specific implementation.
     * @param context
     */
    public ShowPresenterImpl(Context context) {
        super();
        this.context = context;
    }

    @Override
    public void setUIRef(Object view) {
        this.view = (ViewPagerAdapter)view;
    }

    /**
     * Inserts a Show
     * @param show Show to insert
     * @return true if success
     */
    @Override
    public void insertShow(Show show) {
        super.insertShow(show);
        ShowInfoDownloader showInfoDownloader = new ShowInfoDownloader(context, show, this);
        try {
            updateUI(showInfoDownloader.execute().get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates the ViewPagerAdapter after the AsyncTask finishes downloading all the data.
     * Also call super.updateShow to update 'cached' shows and data storage.
     * @param show Show for which data was downloaded. Is returned by the AsyncTask.
     */
    @Override
    public void updateUI(Show show) {
        super.updateUI(show);
        view.notifyDataSetChanged();
    }
}
