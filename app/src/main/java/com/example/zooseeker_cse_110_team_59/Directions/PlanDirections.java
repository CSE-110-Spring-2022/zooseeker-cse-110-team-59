package com.example.zooseeker_cse_110_team_59.Directions;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.VisibleForTesting;

import com.example.zooseeker_cse_110_team_59.Route.RouteGenerator;
import com.example.zooseeker_cse_110_team_59.Retention.SharedPreferencesSaver;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

public class PlanDirections implements DirectionsSubject, SharedPreferencesSaver {
    ArrayList<DirectionsObserver> Observers = new ArrayList<DirectionsObserver>();
    private ArrayList<String> myIDs;
    private Activity directionsActivity;
    private String currentLoc;
    private String destination;
    private int destinationIndex;

    public PlanDirections(Activity activity, ArrayList<String> IDs) {
        directionsActivity = activity;

        myIDs = IDs;
        destinationIndex = 0;
        destination = myIDs.get(destinationIndex);
    }

    //region Button Responders
    public void nextClicked() {
        currentLoc = destination;
        destinationIndex++;
        destination = myIDs.get(destinationIndex);
        updateData();
    }

    public void previousClicked() {
        currentLoc = destination;
        destinationIndex--;
        destination = myIDs.get(destinationIndex);
        updateData();
    }
    //endregion

    //region Data Getters
    public void updateData() {
        saveSharedPreferences();
        ArrayList<String> prevData = getPrevData();
        ArrayList<String> currData = getCurrData();
        ArrayList<String> nextData = getNextData();

        notifyDOS(prevData, currData, nextData);
    }

    public ArrayList<String> getPrevData() {
        if (destinationIndex == 0) return new ArrayList<String>(Arrays.asList("hide"));

        ArrayList<String> prevData = new ArrayList<String>();
        prevData.add("show");

        String nextExhibit = "Previous: " + RouteGenerator.getNameFromId(myIDs.get(destinationIndex - 1)) + ", " +
                RouteGenerator.getDistanceBetween(currentLoc, myIDs.get(destinationIndex - 1)) + "ft";
        prevData.add(nextExhibit);

        return prevData;
    }

    public ArrayList<String> getCurrData() {
        ArrayList<String> currData = new ArrayList<String>();
        String currentExhibit = "Directions to " + RouteGenerator.getNameFromId(destination);
        currData.add(currentExhibit);

        String directions;
        if (RouteGenerator.getDistanceBetween(currentLoc, destination) == 0.0) {
            directions = "You have arrived at " + RouteGenerator.getNameFromId(destination) + ".\n";
        } else {
            directions = RouteGenerator.getDirectionsBetween(currentLoc, destination);
        }
        currData.add(directions);
        return currData;
    }

    public ArrayList<String> getNextData() {
        if (destinationIndex == (myIDs.size() - 1)) return new ArrayList<String>(Arrays.asList("hide"));

        ArrayList<String> nextData = new ArrayList<String>();
        nextData.add("show");

        String nextExhibit = "Next: " + RouteGenerator.getNameFromId(myIDs.get(destinationIndex + 1)) + ", " +
                    RouteGenerator.getDistanceBetween(currentLoc, myIDs.get(destinationIndex + 1)) + "ft";
        nextData.add(nextExhibit);

        return nextData;
    }
    //endregion

    //region DirectionsSubject Interface Methods
    @Override
    public void registerDO(DirectionsObserver dObs) {
        Observers.add(dObs);
    }
    @Override
    public void notifyDOS(ArrayList<String> prevStrings, ArrayList<String> currStrings, ArrayList<String> nextStrings) {
        for (DirectionsObserver obs : Observers) {
            obs.update(prevStrings, currStrings, nextStrings);
        }
    }
    //endregion

    //region SharedPreferencesSaver Interface Methods
    @Override
    public void saveSharedPreferences() {
        SharedPreferences preferences = directionsActivity.getSharedPreferences("shared_preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        if (preferences.contains("storedStartIndex")) editor.remove("storedStartIndex");
        if (preferences.contains("storedRouteIDs")) editor.remove("storedRouteIDs");
        editor.commit();

        Gson gson = new Gson();

        String routeIDsJson = gson.toJson(myIDs);

        editor.putString("storedRouteIDs", routeIDsJson);
        editor.putInt("storedStartIndex", destinationIndex);
        editor.commit();
    }
    //endregion

    //region Getters for Testing
    @VisibleForTesting
    public int getDestinationIndex() {
        return destinationIndex;
    }
    //endregion
}
