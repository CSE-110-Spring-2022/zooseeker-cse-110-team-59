package com.example.zooseeker_cse_110_team_59;

import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class ListActivity extends AppCompatActivity {

    private Map<String, ZooData.VertexInfo> vInfo;
    private Map<String, String> userEntryToID;
    private ArrayList<String> enteredExhibits;
    private List<String> autocompleteSuggestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        vInfo = ZooData.loadVertexInfoJSON(FilesToLoad.getVertexFile());

        userEntryToID = new HashMap<>();
        vInfo.forEach((id, datum) -> {
            if (datum.kind.equals(ZooData.VertexInfo.Kind.EXHIBIT)) userEntryToID.put(datum.name, id);
        });

        enteredExhibits = new ArrayList<>();

        autocompleteSuggestions = new ArrayList<>();
        userEntryToID.forEach((name, id) -> autocompleteSuggestions.add(name));

        AutoCompleteTextView searchBar = findViewById(R.id.search_bar);
        ArrayAdapter<String> searchBarAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, autocompleteSuggestions);
        searchBar.setAdapter(searchBarAdapter);
    }

    public void onSearchSelectClick(View view) {
        checkSearchBar();
    }

    public String checkSearchBar() {
        AutoCompleteTextView searchBar = findViewById(R.id.search_bar);
        String searchBarInput = searchBar.getText().toString();
        searchBar.setText("");

        if (isValid(searchBarInput) && isNew(searchBarInput)) {
            addToLists(searchBarInput);
            increaseListsCount();
        }

        return searchBarInput;
    }

    public boolean isValid(String searchBarInput) {
        if (userEntryToID.containsKey(searchBarInput)){
            return true;
        } else {
            Utilities.showAlert(this, "Invalid Entry", searchBarInput + " is not an exhibit at the San Diego Zoo.");
            return false;
        }
    }

    public boolean isNew(String searchBarInput) {
        return !enteredExhibits.contains(userEntryToID.get(searchBarInput));
    }

    public List<String> addToLists(String searchBarInput) {
        enteredExhibits.add(userEntryToID.get(searchBarInput));

        TextView animalsListTextView = findViewById(R.id.animals_list_text_view);
        String animalsListText = animalsListTextView.getText().toString();
        animalsListTextView.setText(animalsListText + searchBarInput + "\n");

        return enteredExhibits;
    }

    public int increaseListsCount() {
        TextView listCount = findViewById(R.id.list_count_text_view);
        listCount.setText(enteredExhibits.size() + "");

        return enteredExhibits.size();
    }

    public boolean isExhibitValidSize()
    {
        if (enteredExhibits.size() == 0) {
            Utilities.showAlert(this, "Empty List", "No exhibits have been added to your list.");
            return false;
        }

        return true;
    }
    public void onGeneratePlanClick(View view) {

        if (isExhibitValidSize()) {
            Intent loadingIntent = new Intent(this, LoadingActivity.class);
            loadingIntent.putStringArrayListExtra("enteredExhibits", enteredExhibits);
            finish();
            startActivity(loadingIntent);
        }
    }

    //getter methods to see values when testing
    @VisibleForTesting
    public List<String> getEnteredExhibits() {
        return enteredExhibits;
    }

    @VisibleForTesting
    public List<String> getAutocompleteSuggestions() {
        return autocompleteSuggestions;
    }
}