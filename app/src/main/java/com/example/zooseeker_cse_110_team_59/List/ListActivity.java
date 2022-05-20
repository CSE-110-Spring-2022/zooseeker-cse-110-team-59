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
public class ListActivity extends AppCompatActivity {

    private ExhibitList exhibitList;
    private List<String> autocompleteSuggestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        exhibitList = new ExhibitList();

        autocompleteSuggestions = new ArrayList<>();
        ZooData.vertexData.forEach((id, datum) -> {
            if (datum.kind.equals(ZooData.VertexInfo.Kind.EXHIBIT)) autocompleteSuggestions.add(datum.name);
        });

        AutoCompleteTextView searchBar = findViewById(R.id.search_bar);
        ArrayAdapter<String> searchBarAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, autocompleteSuggestions);
        searchBar.setAdapter(searchBarAdapter);
    }

    public void onSearchSelectClick(View view) {
        AutoCompleteTextView searchBar = findViewById(R.id.search_bar);
        String searchBarInput = searchBar.getText().toString();
        searchBar.setText("");

        exhibitList.checkSearchBar(searchBarInput);
    }

    public List<String> addToLists(String searchBarInput) {
        exhibitList.getEnteredExhibits().add(exhibitList.userEntryToID.get(searchBarInput));

        TextView animalsListTextView = findViewById(R.id.animals_list_text_view);
        String animalsListText = animalsListTextView.getText().toString();
        animalsListTextView.setText(animalsListText + searchBarInput + "\n");

        return exhibitList.getEnteredExhibits();
    }

    public int increaseListsCount() {
        TextView listCount = findViewById(R.id.list_count_text_view);
        listCount.setText(exhibitList.getEnteredExhibits().size() + "");

        return exhibitList.getEnteredExhibits().size();
    }

    public boolean isExhibitValidSize()
    {
        if (exhibitList.getEnteredExhibits().size() == 0) {
            Utilities.showAlert(this, "Empty List", "No exhibits have been added to your list.");
            return false;
        }

        return true;
    }
    public void onGeneratePlanClick(View view) {

        if (isExhibitValidSize()) {
            Intent loadingIntent = new Intent(this, LoadingActivity.class);
            loadingIntent.putStringArrayListExtra("enteredExhibits", exhibitList.getEnteredExhibits());
            finish();
            startActivity(loadingIntent);
        }
    }

    //getter methods to see values when testing
    @VisibleForTesting
    public List<String> getEnteredExhibits() {
        return exhibitList.getEnteredExhibits();
    }

    @VisibleForTesting
    public List<String> getAutocompleteSuggestions() {
        return autocompleteSuggestions;
    }
}