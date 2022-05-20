package com.example.zooseeker_cse_110_team_59.List;

import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.example.zooseeker_cse_110_team_59.LoadingActivity;
import com.example.zooseeker_cse_110_team_59.R;
import com.example.zooseeker_cse_110_team_59.Utilities;
import com.example.zooseeker_cse_110_team_59.ZooData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
public class ListActivity extends AppCompatActivity implements ExhibitObserver {

    private ExhibitList exhibitList;
    private List<String> autocompleteSuggestions;
    private TextView listCount;
    private TextView enteredExhibitsTextView;
    private AutoCompleteTextView searchBarTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        searchBarTextView = findViewById(R.id.search_bar);
        listCount = findViewById(R.id.list_count_text_view);
        enteredExhibitsTextView = findViewById(R.id.animals_list_text_view);

        exhibitList = new ExhibitList(this);
        exhibitList.registerEO(this);

        autocompleteSuggestions = new ArrayList<>();
        ZooData.vertexData.forEach((id, datum) -> {
            if (datum.kind.equals(ZooData.VertexInfo.Kind.EXHIBIT)) autocompleteSuggestions.add(datum.name);
        });

        ArrayAdapter<String> searchBarAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, autocompleteSuggestions);
        searchBarTextView.setAdapter(searchBarAdapter);
    }

    //region UI functionality methods:
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

    public void update(String input, int count) {
        addToList(input);
        increaseListCount(count);
    }

    public void addToList(String searchBarInput) {
        String enteredExhibitsText = enteredExhibitsTextView.getText().toString();
        enteredExhibitsTextView.setText(enteredExhibitsText + searchBarInput + "\n");
    }

    public void increaseListCount(int count) {
        listCount.setText(count + "");
    }

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

    //<editor-fold desc="Getters for Testing">
    @VisibleForTesting
    public ExhibitList getExhibitList() {
        return exhibitList;
    }

    @VisibleForTesting
    public List<String> getAutocompleteSuggestions() {
        return autocompleteSuggestions;
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
    //</editor-fold>
}