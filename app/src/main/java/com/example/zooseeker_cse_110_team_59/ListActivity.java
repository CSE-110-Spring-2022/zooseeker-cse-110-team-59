package com.example.zooseeker_cse_110_team_59;

import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class ListActivity extends AppCompatActivity {

    private String[] filesToLoadFrom;

    private Map<String, ZooData.VertexInfo> vInfo;
    private Map<String, String> userEntryToID;
    private List<String> enteredExhibits;
    private List<String> autocompleteSuggestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        Bundle bundle = getIntent().getExtras();
        filesToLoadFrom = bundle.getStringArray("Data Files"); // Graph, Vertex, Edge

        vInfo = ZooData.loadVertexInfoJSON(filesToLoadFrom[1]);

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

    public void addToLists(String searchBarInput) {
        enteredExhibits.add(userEntryToID.get(searchBarInput));

        TextView animalsListTextView = findViewById(R.id.animals_list_text_view);
        String animalsListText = animalsListTextView.getText().toString();
        animalsListTextView.setText(animalsListText + searchBarInput + "\n");
    }

    public void increaseListsCount() {
        TextView listCount = findViewById(R.id.list_count_text_view);
        listCount.setText(enteredExhibits.size() + "");
    }

    @VisibleForTesting
    public List<String> getEnteredExhibits() {
        return enteredExhibits;
    }

    @VisibleForTesting
    public List<String> getAutocompleteSuggestions() {
        return autocompleteSuggestions;
    }
}