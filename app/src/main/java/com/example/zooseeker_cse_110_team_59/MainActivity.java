package com.example.zooseeker_cse_110_team_59;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.zooseeker_cse_110_team_59.Data.ZooData;
import com.example.zooseeker_cse_110_team_59.Directions.DirectionsActivity;
import com.example.zooseeker_cse_110_team_59.List.ListActivity;
import com.example.zooseeker_cse_110_team_59.Overflow.ActivityOverflow;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class:            MainActivity
 * Description:      sets up the onCreate method and  is the entry point of the application
 *
 * Public functions: onCreate          - creates the application for the MainActivity
 */
public class MainActivity extends ActivityOverflow {

    /**
     * The onCreate method creates the MainActivity for the application
     * super is used to call the parent class constructor
     * setContentView is used to set the xml
     *
     * @return None
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ZooData.setZooData();

        if (TestSettings.isTestClearing()) clearSharedPreferences();

        Intent startIntent = decideStartIntent();

        finish();
        startActivity(startIntent);
    }

    private Intent decideStartIntent() {
        Intent startIntent;

        SharedPreferences preferences = getSharedPreferences("shared_preferences", MODE_PRIVATE);
        String storedActivity = preferences.getString("storedActivity", "ListActivity");

        if (storedActivity.equals("DirectionsActivity")) {
            startIntent = new Intent(this, DirectionsActivity.class);
            setDirectionExtras(startIntent, preferences);
        } else {
            startIntent = new Intent(this, ListActivity.class);
            setListExtras(startIntent, preferences);
        }

        return startIntent;
    }

    //region Extra Setters
    // Link: https://www.geeksforgeeks.org/how-to-save-arraylist-to-sharedpreferences-in-android/
    // Title: How to Save ArrayList to SharedPreferences in Android?
    // Date Captured: May 24th 2022
    // Usage: For how to store/retrieve an ArrayList of Strings on/from SharedPreferences. Doing it as an ArrayList
    // is important so that order is maintained. By doing it this way, (alongside some smart helper method creation),
    // we can entirely avoid using databases!

    private void setListExtras(Intent listIntent, SharedPreferences preferences) {
        ArrayList<String> enteredExhibits = new ArrayList<String>();

        listIntent.putStringArrayListExtra("Entered Exhibits", enteredExhibits);

        String enteredExhibitsJson = preferences.getString("storedEnteredExhibits", null);
        if (enteredExhibitsJson == null) return;

        Gson gson = new Gson();

        Type type = new TypeToken<ArrayList<String>>() {}.getType();

        enteredExhibits = gson.fromJson(enteredExhibitsJson, type);

        listIntent.putStringArrayListExtra("Entered Exhibits", enteredExhibits);
    }

    private void setDirectionExtras(Intent dirIntent, SharedPreferences preferences) {
        ArrayList<String> IDs = new ArrayList<String>(Arrays.asList("entrance_exit_gate", "entrance_exit_gate"));
        int startIndex = 1;

        dirIntent.putStringArrayListExtra("IDs in Order", IDs);
        dirIntent.putExtra("Start Index", startIndex);

        String routeIDsJson = preferences.getString("storedRouteIDs", null);
        if (routeIDsJson == null) return;

        Gson gson = new Gson();

        Type type = new TypeToken<ArrayList<String>>() {}.getType();

        IDs = gson.fromJson(routeIDsJson, type);
        startIndex = preferences.getInt("storedStartIndex", 1);

        dirIntent.putStringArrayListExtra("IDs in Order", IDs);
        dirIntent.putExtra("Start Index", startIndex);
    }
    //endregion

    //region ActivityOverflow Abstract Methods
    @Override
    protected void startMainActivity() {
        finish();
        startActivity(new Intent(this, MainActivity.class));
    }
    //endregion
}