package com.example.zooseeker_cse_110_team_59;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> enteredAnimals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        enteredAnimals = new ArrayList<>();
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
        if (searchBarInput.equals("") == false) {
            return true;
        } else {
            Utilities.showAlert(this, "Empty Search Bar!", "Please enter some text into the search bar.");
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

    public ArrayList<String> getEnteredAnimals() {
        return enteredAnimals;
    }

}