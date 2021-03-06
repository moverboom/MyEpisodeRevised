package nl.krakenops.myepisode.view.activities;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import nl.krakenops.myepisode.R;
import nl.krakenops.myepisode.model.Episode;
import nl.krakenops.myepisode.model.Season;
import nl.krakenops.myepisode.model.Show;
import nl.krakenops.myepisode.model.ViewModelHolder;
import nl.krakenops.myepisode.presenter.ShowPresenter;
import nl.krakenops.myepisode.presenter.ShowPresenterImpl;
import nl.krakenops.myepisode.view.adapters.AutoCompleteValues;
import nl.krakenops.myepisode.view.adapters.DelayAutoCompleteTextView;
import nl.krakenops.myepisode.view.adapters.ShowsCompleteAdapter;

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

        final DelayAutoCompleteTextView autcomplete = (DelayAutoCompleteTextView) findViewById(R.id.showTitleInput); //Setting edittext
        autcomplete.setAdapter(new ShowsCompleteAdapter(this)); //Setting AutoComplete Adapter to field
        autcomplete.setLoadingIndicator((android.widget.ProgressBar) findViewById(R.id.loading_indicator));
        autcomplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AutoCompleteValues value = (AutoCompleteValues) parent.getItemAtPosition(position);
                autcomplete.setText(value.getTitle());
            }
        });
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
            EditText showNameEditText = (EditText) findViewById(R.id.showTitleInput); //Find fields
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
                ViewModelHolder viewModelHolder = new ViewModelHolder(
                        showNameEditText.getText().toString(),
                        Integer.parseInt(seasonEditText.getText().toString()),
                        Integer.parseInt(episodeEditText.getText().toString()),
                        new Date());
                Log.d(this.getClass().getName(), "Created a new ViewModelHolder with showName: " + showNameEditText.getText().toString());

                /*Hiding Soft Keyboard on button press*/
                InputMethodManager iim = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                iim.hideSoftInputFromWindow(showNameEditText.getWindowToken(), 0);
                /*Save data and set confirm layout*/
                showPresenter.addEpisode(viewModelHolder);
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
