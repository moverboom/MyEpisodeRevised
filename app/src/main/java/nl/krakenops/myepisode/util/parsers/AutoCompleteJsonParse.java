package nl.krakenops.myepisode.util.parsers;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbSearch;
import info.movito.themoviedbapi.TvResultsPage;
import info.movito.themoviedbapi.model.tv.TvSeries;
import nl.krakenops.myepisode.adapters.AutoCompleteValues;

/**
 * Created by Matthijs on 19/01/2016.
 */
public class AutoCompleteJsonParse {
    public AutoCompleteJsonParse(){}
    public TmdbApi api = new TmdbApi("ecf8a3e4ef91346008bf400cbb9a6d00");
    public List<AutoCompleteValues> getParseJsonWCF(String sName)
    {
        List<AutoCompleteValues> ListData = new ArrayList<AutoCompleteValues>();
        try {
            TvResultsPage search = api.getSearch().searchTv(sName, "eng", 1);
            Log.i("TMDB response", "[" + search + "]");
            List<TmdbSearch> results = new ArrayList<TmdbSearch>();
            List<TvSeries> listResult = search.getResults();
            for(int i = 0; i < listResult.size(); i++){
                String showName = listResult.get(i).getOriginalName();
                ListData.add(new AutoCompleteValues(showName));
            }
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        return ListData;
    }
}
