package com.example.zooseeker_cse_110_team_59;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public String searchBarInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onSearchSelectClick(View view) {
        AutoCompleteTextView searchBar = findViewById(R.id.search_bar);
        searchBarInput = searchBar.getText().toString();
        searchBar.setText("");
    }
}