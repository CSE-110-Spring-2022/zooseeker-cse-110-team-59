package com.example.zooseeker_cse_110_team_59.MS2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.AutoCompleteTextView;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.zooseeker_cse_110_team_59.Data.FilesToLoad;
import com.example.zooseeker_cse_110_team_59.Data.ZooData;
import com.example.zooseeker_cse_110_team_59.Directions.DirectionsActivity;
import com.example.zooseeker_cse_110_team_59.List.ListActivity;
import com.example.zooseeker_cse_110_team_59.MainActivity;
import com.example.zooseeker_cse_110_team_59.TestSettings;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;

@RunWith(AndroidJUnit4.class)
public class UnitTestsUserStoryMS1_13 {
    private Context context = ApplicationProvider.getApplicationContext();

    @Rule
    public ActivityScenarioRule<MainActivity> scenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    //region INCLUDE THIS IN EVERY UNIT TEST. Change file names to desired test files.
    @BeforeClass
    public static void setTestData() {
        FilesToLoad.injectNewFiles(new String[]{"test_zoo_graph_ms2.json", "test_node_info_ms2.json", "test_edge_info_ms2.json"});
        TestSettings.setTestClearing(true);
        TestSettings.setTestPositioning(true);
        ZooData.setZooData();
    }
    //endregion

    @Test
    public void testListScreenRetention() {
        ArrayList<String> enteredExhibits = new ArrayList<String>();
        Intent listIntent = new Intent(context, ListActivity.class).putExtra("Entered Exhibits", enteredExhibits);

        ActivityScenario<ListActivity> listActivity = ActivityScenario.launch(listIntent);
        listActivity.moveToState(Lifecycle.State.CREATED);

        final String[] enteredExhibitsJson = {""};

        listActivity.onActivity(activity -> {
            AutoCompleteTextView searchBar = activity.getSearchBarTextView();
            SharedPreferences preferences = activity.getSharedPreferences("shared_preferences", Context.MODE_PRIVATE);

            assertEquals("ListActivity", preferences.getString("storedActivity", null));

            searchBar.setText("Koi Fish");
            activity.checkSearchBar();

            searchBar.setText("Gorillas");
            activity.checkSearchBar();

            searchBar.setText("Table");
            activity.checkSearchBar();

            enteredExhibitsJson[0] = preferences.getString("storedEnteredExhibits", null);

            assertNotNull(enteredExhibitsJson[0]);
        });

        Gson gson = new Gson();

        Type type = new TypeToken<ArrayList<String>>() {}.getType();

        enteredExhibits = gson.fromJson(enteredExhibitsJson[0], type);
        listIntent.putStringArrayListExtra("Entered Exhibits", enteredExhibits);

        listActivity.recreate();

        ArrayList<String> finalEnteredExhibits = enteredExhibits;
        listActivity.onActivity(activity -> {
            assertEquals(finalEnteredExhibits, activity.getExhibitList().getEnteredExhibits());
            assertEquals("Koi Fish\n" + "Gorillas\n", activity.getEnteredExhibitsTextView().getText().toString());
        });
    }

    @Test
    public void testDirectionsScreenRetention() {
        ArrayList<String> routeIDs = new ArrayList<String>(Arrays.asList("entrance_exit_gate", "koi", "entrance_exit_gate"));
        int startIndex = 1;
        Intent directionsIntent = new Intent(context, DirectionsActivity.class)
                .putExtra("IDs in Order", routeIDs)
                .putExtra("Start Index", startIndex);

        ActivityScenario<DirectionsActivity> dirActivity = ActivityScenario.launch(directionsIntent);
        dirActivity.moveToState(Lifecycle.State.CREATED);

        final String[] routeInformation = {"", ""};

        dirActivity.onActivity(activity -> {
            SharedPreferences preferences = activity.getSharedPreferences("shared_preferences", Context.MODE_PRIVATE);

            assertEquals("DirectionsActivity", preferences.getString("storedActivity", null));

            activity.getPlanDirections().nextClicked();

            routeInformation[0] = preferences.getString("storedRouteIDs", null);
            routeInformation[1] = preferences.getInt("storedStartIndex", 1) + "";

            assertNotNull(routeInformation[0]);
            assertNotEquals("1", routeInformation[1]);
        });

        Gson gson = new Gson();

        Type type = new TypeToken<ArrayList<String>>() {}.getType();

        routeIDs = gson.fromJson(routeInformation[0], type);
        directionsIntent.putStringArrayListExtra("IDs in Order", routeIDs);
        startIndex = Integer.valueOf(routeInformation[1]);
        directionsIntent.putExtra("Start Index", startIndex);

        dirActivity.recreate();

        ArrayList<String> finalRouteIDs = routeIDs;
        int finalStartIndex = startIndex;
        dirActivity.onActivity(activity -> {
            assertEquals(finalRouteIDs, activity.getPlanDirections().getRouteIDs());
            assertEquals(finalStartIndex, activity.getPlanDirections().getDestinationIndex());
            assertEquals("1. Proceed on Terrace Lagoon Loop 2200.0 ft towards Front Street.\n" +
                    "2. Proceed on Front Street 3200.0 ft towards Gate Path.\n" +
                    "3. Proceed on Gate Path 1100.0 ft towards Entrance and Exit Gate.\n",
                    activity.getDirectionsTV().getText().toString());
        });
    }
}
