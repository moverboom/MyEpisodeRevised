package nl.krakenops.myepisode.util.parsers;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbSearch;
import info.movito.themoviedbapi.TvResultsPage;
import info.movito.themoviedbapi.model.tv.TvSeries;
import nl.krakenops.myepisode.view.adapters.AutoCompleteValues;

/**
 * Created by Matthijs on 19/01/2016.
 */
public class AutoCompleteJsonParse {
    private TmdbApi api = new TmdbApi("361e460bdf4209b48c8db9b6ad0ea321");

    public List<AutoCompleteValues> getParseJsonWCF(String sName)
    {
        List<AutoCompleteValues> values = new ArrayList<AutoCompleteValues>();
        try {
            TvResultsPage search = api.getSearch().searchTv(sName, "en", 1);
            List<TvSeries> listResult = search.getResults();
            for(int i = 0; i < search.getResults().size(); i++){
                values.add(new AutoCompleteValues(search.getResults().get(i).getOriginalName()));
            }
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        return values;
    }
}
