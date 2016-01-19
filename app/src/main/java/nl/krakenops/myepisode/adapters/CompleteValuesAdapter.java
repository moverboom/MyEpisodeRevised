package nl.krakenops.myepisode.adapters;

import android.app.Activity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;

import nl.krakenops.myepisode.util.parsers.AutoCompleteJsonParse;

/**
 * Created by Matthijs on 19/01/2016.
 */
public class CompleteValuesAdapter extends ArrayAdapter<String> {
    protected static final String TAG = "CompleteValuesAdapter";
    private List<String> completeValues;
    public CompleteValuesAdapter(Activity context, String nameFilter) {
        super(context, android.R.layout.simple_list_item_1);
        completeValues = new ArrayList<String>();
    }

    @Override
    public int getCount() {
        return completeValues.size();
    }

    @Override
    public String getItem(int index) {
        return completeValues.get(index);
    }

    @Override
    public Filter getFilter() {
        Filter myFilter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                AutoCompleteJsonParse jsonParse = new AutoCompleteJsonParse();
                if(constraint.length() > 2) {
                    //A class that queries a web api, parses the data and
                    //returns an ArrayList
                    List<AutoCompleteValues> new_values = jsonParse.getParseJsonWCF(constraint.toString());
                    completeValues.clear();
                    for (int i = 0; i < new_values.size(); i++) {
                        Log.i("new_value", "[" + new_values.get(i) + "]");
                        completeValues.add(new_values.get(i).getTitle());
                    }
                    //Now assign the values and count to the FilterResults object
                    filterResults.values = completeValues;
                    filterResults.count = completeValues.size();
                }
                return filterResults;
            }
            @Override
            protected  void publishResults(CharSequence constraint , FilterResults results) {
                if (results != null && results.count > 0) {
                    notifyDataSetChanged();
                }
                else {
                    notifyDataSetInvalidated();
                }
            }
        };
        return myFilter;
    }
}
