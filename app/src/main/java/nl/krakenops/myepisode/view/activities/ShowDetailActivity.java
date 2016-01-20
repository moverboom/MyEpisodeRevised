package nl.krakenops.myepisode.view.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import nl.krakenops.myepisode.R;
import nl.krakenops.myepisode.view.adapters.ExpandableListAdapter;

public class ShowDetailActivity extends AppCompatActivity {
    List<String> groupList;
    List<String> childList;
    Map<String, List<String>> episodeCollection;
    ExpandableListView expListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        createGroupList();

        createCollection();

        expListView = (ExpandableListView) findViewById(R.id.season_list);
        final ExpandableListAdapter expListAdapter = new ExpandableListAdapter(
                this, groupList, episodeCollection);
        expListView.setAdapter(expListAdapter);

        //setGroupIndicatorToRight();

        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                final String selected = (String) expListAdapter.getChild(
                        groupPosition, childPosition);
                Toast.makeText(getBaseContext(), selected, Toast.LENGTH_LONG)
                        .show();

                return true;
            }
        });
    }

    private void createGroupList() {
        groupList = new ArrayList<String>();
        groupList.add("1");
        groupList.add("2");
        groupList.add("3");
        groupList.add("4");
        groupList.add("5");
    }

    private void createCollection() {
        // preparing laptops collection(child)
        String[] season1 = { "1", "2", "3" };
        String[] season2 = { "1", "2", "3", "4" };
        String[] season3 = { "1", "2", "3"  };
        String[] season4 = { "1", "2", "3"  };
        String[] season5 = { "1", "2", "3"  };

        episodeCollection = new LinkedHashMap<String, List<String>>();

        for (String laptop : groupList) {
            if (laptop.equals("1")) {
                loadChild(season1);
            } else if (laptop.equals("2"))
                loadChild(season2);
            else if (laptop.equals("3"))
                loadChild(season3);
            else if (laptop.equals("4"))
                loadChild(season4);
            else
                loadChild(season5);

            episodeCollection.put(laptop, childList);
        }
    }

    private void loadChild(String[] episodes) {
        childList = new ArrayList<String>();
        for (String episode : episodes)
            childList.add(episode);
    }

    private void setGroupIndicatorToRight() {
        /* Get the screen width */
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;

        expListView.setIndicatorBounds(width - getDipsFromPixel(35), width
                - getDipsFromPixel(5));
    }

    // Convert pixel to dip
    public int getDipsFromPixel(float pixels) {
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (pixels * scale + 0.5f);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show_detail, menu);
        return true;
    }
}
