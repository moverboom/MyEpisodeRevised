package nl.krakenops.myepisode.view.activities;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import nl.krakenops.myepisode.R;
import nl.krakenops.myepisode.model.Episode;
import nl.krakenops.myepisode.model.Season;
import nl.krakenops.myepisode.model.Show;
import nl.krakenops.myepisode.presenter.ShowPresenter;
import nl.krakenops.myepisode.presenter.ShowPresenterImpl;
import nl.krakenops.myepisode.view.adapters.CompleteValuesAdapter;

/**
 * Created by Matthijs on 19/01/2016.
 */
public class AddWatchedAc extends AppCompatActivity{
    private ShowPresenter showPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_watched);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        AutoCompleteTextView actv = (AutoCompleteTextView) findViewById(R.id.editTextAddedShow); //Setting AutoComplete field
        actv.setAdapter(new CompleteValuesAdapter(this, actv.getText().toString())); //Setting AutoComplete Adapter to field
        showPresenter = new ShowPresenterImpl(getApplicationContext());
        findButtonAdded();
    }

    //Find 'Save' Button
    private void findButtonAdded() {
        Button buttonAdded = (Button) findViewById(R.id.buttonAdded);
        buttonAdded.setOnClickListener(buttonAddedListener);
    }

    //Binding actionListener to 'Save' Button
    private Button.OnClickListener buttonAddedListener = new Button.OnClickListener() {
        public void onClick(View v) {
            List<EditText> fields = new ArrayList<>(); //List to save fields for check
            int notFilled = 0; //Range 0-3. +1 when field isn't filled properly
            EditText showNameEditText = (EditText) findViewById(R.id.editTextAddedShow); //Find fields
            EditText seasonEditText = (EditText) findViewById(R.id.editTextAddedSeason);
            EditText episodeEditText = (EditText) findViewById(R.id.editTextAddedEpisode);
            fields.add(showNameEditText); //Adding fields to List
            fields.add(seasonEditText);
            fields.add(episodeEditText);

            for (EditText e : fields) { //Loop through List
                if (e.getText().toString().equals("") || e.getText().toString().equals(" ") || e.getText().toString().trim().length() <= 0) { //Conditions for incorrect filled fields
                    e.getBackground().setColorFilter(Color.alpha(-65536), PorterDuff.Mode.SRC_ATOP);
                    notFilled++; //Field filled incorrect
                }
                else { //Field filled correct
                    e.getBackground().setColorFilter(Color.alpha(16843601), PorterDuff.Mode.SRC_ATOP);
                }
            }
            if(notFilled == 0) { //if all fields are filled correct
                HashMap queryValues = new HashMap<String, String>(); //HashMap for input data
                queryValues.put("Show", showNameEditText.getText().toString()); //Adding data to HashMap and escaping
                queryValues.put("Season", seasonEditText.getText().toString());
                queryValues.put("Episode", episodeEditText.getText().toString());

                Show show = new Show();
                show.setName(showNameEditText.getText().toString());
                Season season = new Season(Integer.parseInt(seasonEditText.getText().toString()));
                Episode episode = new Episode(Integer.parseInt(episodeEditText.getText().toString()));
                season.addEpisode(episode);
                show.addSeason(season);

                /*Hiding Soft Keyboard on button press*/
                InputMethodManager iim = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                iim.hideSoftInputFromWindow(showNameEditText.getWindowToken(), 0);
                /*Save data and set confirm layout*/
                showPresenter.insertShow(show);
                //setContentView(R.layout.add_watched_confirm);
            }
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        //noinspection SimplifiableIfStatement
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
