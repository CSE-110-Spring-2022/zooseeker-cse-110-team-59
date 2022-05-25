package com.example.zooseeker_cse_110_team_59.List;

import androidx.annotation.VisibleForTesting;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.example.zooseeker_cse_110_team_59.ActivityOverflow;
import com.example.zooseeker_cse_110_team_59.LoadingActivity;
import com.example.zooseeker_cse_110_team_59.MainActivity;
import com.example.zooseeker_cse_110_team_59.R;
import com.example.zooseeker_cse_110_team_59.RouteGenerator;
import com.example.zooseeker_cse_110_team_59.SharedPreferencesSaver;
import com.example.zooseeker_cse_110_team_59.Utilities;
import com.example.zooseeker_cse_110_team_59.ZooData;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class:           ListActivity
 * Description:     The activity started when the user adds exhibits to the list
 *
 * Public functions:
 *
 * checkSearchBar   - called when the user presses the search button
 * isValid    - checks if user input is valid by comparing it to a hashmap
 *              of exhibits
 * isNew    - checks if user input is a newly entered exhibit by comparing it a
 *              arraylist of previously entered exhibits
 * addToLists   - adds the user input to a arraylist of exhibits
 * increaseListsCount   - increments the entered exhibits arraylist by 1
 * isExhibitValidSize   - checks if the enteredexhibit arraylist has a valid size
 * onGeneratePlanClick  - generates the plan by instantiating a new intent
 */
public class ListActivity extends ActivityOverflow implements ExhibitObserver,SharedPreferencesSaver {

    private ExhibitList exhibitList;
    private ArrayList<String> startEnteredExhibits;
    private TextView listCount;
    private TextView enteredExhibitsTextView;
    private AutoCompleteTextView searchBarTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        Bundle bundle = getIntent().getExtras();
        startEnteredExhibits = bundle.getStringArrayList("Entered Exhibits");

        searchBarTextView = findViewById(R.id.search_bar);
        listCount = findViewById(R.id.list_count_text_view);
        enteredExhibitsTextView = findViewById(R.id.animals_list_text_view);

        ArrayList<ArrayList<String>> exhibitTagList = new ArrayList<ArrayList<String>>();
        ZooData.vertexData.forEach((id, datum) -> {
            if (datum.isExhibit()) {
                ArrayList<String> row = new ArrayList<String>(Arrays.asList(datum.name));
                row.addAll(datum.tags);
                exhibitTagList.add(row);
            }
        });

        searchBarTextView.setAdapter(new AutoCompleteAdapter(this, android.R.layout.simple_list_item_1, exhibitTagList));

        exhibitList = new ExhibitList(this);
        exhibitList.registerEO(this);

        startEnteredExhibits.forEach(enteredExhibit -> exhibitList.checkInput(RouteGenerator.getNameFromId(enteredExhibit)));

        saveSharedPreferences();
    }

    //region Search Bar Handlers/Helpers
    public void onSearchSelectClick(View view) {
        checkSearchBar();
    }

    public void checkSearchBar() {
        exhibitList.checkInput(getSearchBar());
    }

    public void clearSearchBar()
    {
        searchBarTextView.setText("");
    }

    public String getSearchBar () {
        String searchBarInput = searchBarTextView.getText().toString();
        clearSearchBar();

        return searchBarInput;
    }
    //endregion

    //region ExhibitObserver Interface Methods
    public void update(String input, int count) {
        addToList(input);
        increaseListCount(count);
    }
    //endregion

    //region View Updaters
    public void addToList(String searchBarInput) {
        String enteredExhibitsText = enteredExhibitsTextView.getText().toString();
        enteredExhibitsTextView.setText(enteredExhibitsText + searchBarInput + "\n");
    }

    public void increaseListCount(int count) {
        listCount.setText(count + "");
    }
    //endregion

    //region Generate Plan Button Handler/Helper
    public boolean isExhibitValidSize()
    {
        if (Integer.parseInt(listCount.getText().toString()) == 0) {
            Utilities.showAlert(this, "Empty List", "No exhibits have been added to your list.");
            return false;
        }

        return true;
    }

    public void onGeneratePlanClick(View view) {

        if (isExhibitValidSize()) {
            Intent loadingIntent = new Intent(this, LoadingActivity.class);
            loadingIntent.putStringArrayListExtra("enteredExhibits", (ArrayList<String>) exhibitList.getEnteredExhibits());
            finish();
            startActivity(loadingIntent);
        }
    }
    //endregion

    //region ActivityOverflow Abstract Methods
    @Override
    protected void startMainActivity() {
        finish();
        startActivity(new Intent(this, MainActivity.class));
    }
    //endregion

    //region SharedPreferencesSaver Interface Methods
    @Override
    public void saveSharedPreferences() {
        SharedPreferences preferences = getSharedPreferences("shared_preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        if (preferences.contains("storedActivity")) editor.remove("storedActivity");
        editor.commit();
        editor.putString("storedActivity", "ListActivity");
        editor.commit();
    }
    //endregion

    //region Getters for Testing
    @VisibleForTesting
    public ExhibitList getExhibitList() {
        return exhibitList;
    }

    @VisibleForTesting
    public TextView getListCount() {
        return listCount;
    }

    @VisibleForTesting
    public TextView getEnteredExhibitsTextView() {
        return enteredExhibitsTextView;
    }

    @VisibleForTesting
    public AutoCompleteTextView getSearchBarTextView() {
        return searchBarTextView;
    }
    //endregion
}