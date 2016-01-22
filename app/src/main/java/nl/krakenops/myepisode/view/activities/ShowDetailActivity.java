package nl.krakenops.myepisode.view.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import nl.krakenops.myepisode.R;
import nl.krakenops.myepisode.model.Episode;
import nl.krakenops.myepisode.model.Thumbnail;
import nl.krakenops.myepisode.presenter.ThumbnailPresenter;
import nl.krakenops.myepisode.view.adapters.ExpandableListAdapter;
import nl.krakenops.myepisode.view.adapters.ThumbAdapter;

/**
 * This activity display detailed information about a watched tv show,
 * such as which episodes have been watch, what te latest episode was and when the next episode airs (FUTURE PLAN)
 * Thanks to theopentutorials.com from explaining how to use the ExpandableListView.
 */

public class ShowDetailActivity extends AppCompatActivity {
    List<String> groupList; //Seasons
    List<String> childList; //Episodes
    Map<String, List<String>> episodeCollection;
    ExpandableListView expListView;
    private Thumbnail thumbnail;
    private ThumbnailPresenter thumbnailPresenter;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        thumbnail = (Thumbnail)getIntent().getSerializableExtra("Thumbnail");
        //thumbnailPresenter = (ThumbnailPresenter)getIntent().getSerializableExtra("ThumbnailPresenter");
        this.thumbnailPresenter = new ThumbnailPresenter(getApplicationContext());
        createGroupList();

        createCollection();

        expListView = (ExpandableListView) findViewById(R.id.season_list);
        final ExpandableListAdapter expListAdapter = new ExpandableListAdapter(
                this, groupList, episodeCollection);
        expListView.setAdapter(expListAdapter);

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
        Iterator it = thumbnail.getWatchedEpisodes().entrySet().iterator();
        while ( it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            Episode tmpEpisode = (Episode) pair.getValue();
            groupList.add(String.valueOf(tmpEpisode.getSeason()));
        }
    }

    private void createCollection() {
        episodeCollection = new LinkedHashMap<String, List<String>>();

        Iterator it = thumbnail.getWatchedEpisodes().entrySet().iterator();
        while ( it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            if (episodeCollection.containsKey((String)pair.getKey())) {
                episodeCollection.get((String)pair.getKey()).add((String)pair.getValue());
            } else {
                List<String> episodeList = new ArrayList<String>();
                String episode = String.valueOf(((Episode)pair.getValue()).getEpisode());
                episodeList.add(episode);
                episodeCollection.put((String) pair.getKey(), episodeList);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        this.menu = menu;
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show_detail, menu);
        MenuItem menuItem = menu.findItem(R.id.action_fav_show);
        if (thumbnail.isFavorite()) {
            menuItem.setIcon(R.drawable.ic_fav_show_set);
        } else {
            menuItem.setIcon(R.drawable.ic_fav_show);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_fav_show) {
            MenuItem menuItem = menu.findItem(R.id.action_fav_show);
            if (!thumbnail.isFavorite()) {
                menuItem.setIcon(R.drawable.ic_fav_show_set);
                thumbnail.setFavorite(true);
                thumbnailPresenter.updateThumbnail(thumbnail);
                Log.d("ShowDetailActivity", "Updating " + thumbnail.getName() + " to favorite");

            } else {
                menuItem.setIcon(R.drawable.ic_fav_show);
                thumbnail.setFavorite(false);
                thumbnailPresenter.updateThumbnail(thumbnail);
                Log.d("ShowDetailActivity", "Updating " + thumbnail.getName() + " to non-favorite");
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
