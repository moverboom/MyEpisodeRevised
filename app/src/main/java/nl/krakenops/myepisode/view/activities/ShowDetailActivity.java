package nl.krakenops.myepisode.view.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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
import nl.krakenops.myepisode.model.Season;
import nl.krakenops.myepisode.model.Show;
import nl.krakenops.myepisode.presenter.ShowPresenter;
import nl.krakenops.myepisode.presenter.ShowPresenterImpl;
import nl.krakenops.myepisode.view.adapters.ExpandableListAdapter;

/**
 * This activity display detailed information about a watched tv show,
 * such as which episodes have been watch, what te latest episode was and when the next episode airs (FUTURE PLAN)
 * Thanks to theopentutorials.com from explaining how to use the ExpandableListView.
 */

public class ShowDetailActivity extends AppCompatActivity {
    List<String> groupList; //Seasons
    Map<String, List<String>> episodeCollection;
    ExpandableListView expListView;
    private Show show;
    private ShowPresenter showPresenter;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        show = (Show)getIntent().getSerializableExtra("Thumbnail");
        //thumbnailPresenter = (ThumbnailPresenter)getIntent().getSerializableExtra("ThumbnailPresenter");
        this.showPresenter = new ShowPresenterImpl(getApplicationContext());
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

    //Creates an ArrayList of all seasons
    private void createGroupList() {
        groupList = new ArrayList<String>();
        Iterator it = show.getSeasons().entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            Season tmpSeason = (Season) pair.getValue();
            groupList.add(String.valueOf(tmpSeason.getSeason()));
        }
    }

    //Maps seasons to episodes
    private void createCollection() {
        episodeCollection = new LinkedHashMap<String, List<String>>();

        Iterator it = show.getSeasons().entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            List<String> episodes = new ArrayList<String>();
            Season tmpSeason = (Season)pair.getValue();
            episodeCollection.put(String.valueOf(tmpSeason.getSeason()), episodes);
            for (Episode episode : tmpSeason.getEpisodesAsArrayList()) {
                episodeCollection.get(String.valueOf(tmpSeason.getSeason())).add(String.valueOf(episode.getEpisode()));
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
        if (show.isFavorite()) {
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
            if (!show.isFavorite()) {
                menuItem.setIcon(R.drawable.ic_fav_show_set);
                show.setFavorite(true);
                showPresenter.setShowFavorite(show);
                Log.d("ShowDetailActivity", "Updating " + show.getName() + " to favorite");

            } else {
                menuItem.setIcon(R.drawable.ic_fav_show);
                show.setFavorite(false);
                showPresenter.setShowFavorite(show);
                Log.d("ShowDetailActivity", "Updating " + show.getName() + " to non-favorite");
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
