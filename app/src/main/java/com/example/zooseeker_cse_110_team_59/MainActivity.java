package com.example.zooseeker_cse_110_team_59;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    private List<WaypointItem> waypointItems;
    private List<String> exhibitNames;

    private List<String> enteredAnimals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        enteredAnimals = new ArrayList<>();
        waypointItems = WaypointItem.loadJSON(this, "sample_exhibits.json");

        // Link: https://stackoverflow.com/questions/122105/how-to-filter-a-java-collection-based-on-predicate
        // Title: How to filter a Java Collection (based on predicate)?
        // Date Captured: April 27th, 2022
        // Used: Knowledge of how to format .filter() in java (specifically the .collect(Collectors.toList()) part). I already had some knowledge of filter/map from CSE 130, but in different languages.
        List<WaypointItem> exhibitItems = waypointItems.stream().filter(item -> item.itemType.equals("exhibit")).collect(Collectors.toList());
        exhibitNames = exhibitItems.stream().map(exhibit -> exhibit.id).collect(Collectors.toList());
        exhibitNames = StringConverter.dashedToNormalList(exhibitNames);

        // AutoCompleteTextView animalBoxTextview = findViewById(R.id.search_bar);
        // ArrayAdapter<String> animalBoxAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, exhibitNames);
        // animalBoxTextview.setAdapter(animalBoxAdapter);
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
        if (exhibitNames.contains(searchBarInput)) {
            return true;
        } else {
            Utilities.showAlert(this, "Invalid Entry", searchBarInput + " is not an exhibit at the San Diego Zoo.");
            return false;
        }
    }

    public boolean isNew(String searchBarInput) {
        return !enteredAnimals.contains(searchBarInput);
    }

    public void addToLists(String searchBarInput) {
        enteredAnimals.add(searchBarInput);

        TextView animalsListTextView = findViewById(R.id.animals_list_text_view);
        String animalsListText = animalsListTextView.getText().toString();
        animalsListTextView.setText(animalsListText + searchBarInput + "\n");
    }

    public void increaseListsCount() {
        TextView listCount = findViewById(R.id.list_count_text_view);
        listCount.setText(enteredAnimals.size() + "");
    }

    public List<String> getEnteredAnimals() {
        return enteredAnimals;
    }

}