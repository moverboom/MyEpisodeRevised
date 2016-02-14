package nl.krakenops.myepisode.util.parsers;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.model.tv.TvSeries;
import nl.krakenops.myepisode.view.adapters.AutoCompleteValues;

/**
 * Created by Matthijs on 19/01/2016.
 */
public class AutoCompleteJsonParse {
    private TmdbApi api = new TmdbApi("secret");

    public List<AutoCompleteValues> getParseJsonWCF(String sName)
    {
        List<AutoCompleteValues> values = new ArrayList<AutoCompleteValues>();
        try {
            List<TvSeries> result = api.getSearch().searchTv(sName, "en", 0).getResults();
            for(int i = 0; i < result.size(); i++){
                values.add(new AutoCompleteValues(result.get(i).getName()));
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return values;
    }
}
