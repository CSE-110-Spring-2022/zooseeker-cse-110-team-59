package com.example.zooseeker_cse_110_team_59;

import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class MainActivity extends AppCompatActivity {

    private String[] filesToLoad = new String[]{"sample_zoo_graph.json", "sample_node_info.json", "sample_edge_info.json"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent listIntent = new Intent(this, ListActivity.class);
        listIntent.putExtra("Data Files", FilesToLoad.getFilesToLoad());
        // First is Graph File, Second is Vertex File, Third is Edge File
        startActivity(listIntent);
    }
}