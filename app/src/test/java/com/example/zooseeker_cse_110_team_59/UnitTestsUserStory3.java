package com.example.zooseeker_cse_110_team_59;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListAdapter;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class UnitTestsUserStory3 {

    private Context context = ApplicationProvider.getApplicationContext();

    private Intent listIntent = new Intent(context, ListActivity.class)
            .putExtra("Data Files", new String[]{"test_zoo_graph.json", "test_node_info.json", "test_edge_info.json"});

    @Rule
    public ActivityScenarioRule<ListActivity> scenarioRule = new ActivityScenarioRule<>(listIntent);

    @Test
    public void testSetAdapterCorrectly() {
        ActivityScenario<ListActivity> scenario = scenarioRule.getScenario();

        scenario.moveToState(Lifecycle.State.CREATED);

        scenario.onActivity(activity -> {
            AutoCompleteTextView searchBar = activity.findViewById(R.id.search_bar);

            ArrayAdapter<String> testAdapter = new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, new ArrayList<String>());
            testAdapter.addAll(activity.getAutocompleteSuggestions());

            ArrayAdapter searchBarAdapter = (ArrayAdapter) searchBar.getAdapter();
            assertEquals(testAdapter.getAutofillOptions(), searchBarAdapter.getAutofillOptions());
        });
    }
}
