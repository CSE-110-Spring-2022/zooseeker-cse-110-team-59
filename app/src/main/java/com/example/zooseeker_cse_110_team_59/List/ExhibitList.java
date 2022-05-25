package com.example.zooseeker_cse_110_team_59.List;
import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.VisibleForTesting;

import com.example.zooseeker_cse_110_team_59.RouteGenerator;
import com.example.zooseeker_cse_110_team_59.SharedPreferencesSaver;
import com.example.zooseeker_cse_110_team_59.Utilities;
import com.example.zooseeker_cse_110_team_59.ZooData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExhibitList implements ExhibitSubject, SharedPreferencesSaver {
    ArrayList<ExhibitObserver> observers = new ArrayList<ExhibitObserver>();
    private Map<String, String> userEntryToID;
    private ArrayList<String> enteredExhibits;
    Activity listActivity;

    public ExhibitList(Activity activity) {
        listActivity = activity;

        userEntryToID = new HashMap<>();
        ZooData.vertexData.forEach((id, datum) -> {
            if (datum.kind.equals(ZooData.VertexInfo.Kind.EXHIBIT)) userEntryToID.put(datum.name, id);
        });

        enteredExhibits = new ArrayList<String>();
    }

    //region Word Verification Methods
    public String checkInput(String input) {
        if (isValid(input) && isNew(input))
        {
            enteredExhibits.add(userEntryToID.get(input));
            saveSharedPreferences();
            notifyEOS(input, enteredExhibits.size());
        }

        return input;
    }

    public boolean isValid(String searchBarInput) {
        if (userEntryToID.containsKey(searchBarInput)) {
            return true;
        } else {
            Utilities.showAlert(listActivity , "Invalid Entry", searchBarInput + " is not an exhibit at the San Diego Zoo.");
            return false;
        }
    }

    public boolean isNew(String searchBarInput) {
        return !enteredExhibits.contains(userEntryToID.get(searchBarInput));
    }

    //endregion

    //region ExhibitSubject Interface Methods
    @Override
    public void registerEO (ExhibitObserver eo)
    {
        observers.add(eo);
    }

    @Override
    public void notifyEOS (String name, int count)
    {
        for (ExhibitObserver observer: observers)
        {
            observer.update(name, count);
        }
    }

    //endregion

    //region SharedPreferencesSaver Interface Methods
    // Link: https://www.geeksforgeeks.org/how-to-save-arraylist-to-sharedpreferences-in-android/
    // Title: How to Save ArrayList to SharedPreferences in Android?
    // Date Captured: May 24th 2022
    // Usage: For how to store/retrieve an ArrayList of Strings on/from SharedPreferences. Doing it as an ArrayList
    // is important so that order is maintained. By doing it this way, (alongside some smart helper method creation),
    // we can entirely avoid using databases!
    @Override
    public void saveSharedPreferences() {
        SharedPreferences preferences = listActivity.getSharedPreferences("shared_preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        if (preferences.contains("storedEnteredExhibits")) editor.remove("storedEnteredExhibits");
        editor.commit();

        Gson gson = new Gson();

        String enteredExhibitsJson = gson.toJson(enteredExhibits);

        editor.putString("storedEnteredExhibits", enteredExhibitsJson);
        editor.commit();
    }
    //endregion

    //region Getters for Testing
    @VisibleForTesting
    public List<String> getEnteredExhibits() {
        return enteredExhibits;
    }
    //endregion
}