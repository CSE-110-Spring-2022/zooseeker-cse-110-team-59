package com.example.zooseeker_cse_110_team_59.Directions;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Pair;

import androidx.annotation.VisibleForTesting;

import com.example.zooseeker_cse_110_team_59.Route.RouteGenerator;
import com.example.zooseeker_cse_110_team_59.Data.SharedPreferencesSaver;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

public class PlanDirections implements DirectionsSubject, SharedPreferencesSaver {
    ArrayList<DirectionsObserver> Observers = new ArrayList<DirectionsObserver>();
    private ArrayList<String> routeIDs;
    private Activity directionsActivity;
    private String currentLoc;
    private String destination;
    private int destinationIndex;
    private boolean deniedReplan;
    private Pair<Double, Double> lastKnownCoords;

    public PlanDirections(Activity activity, ArrayList<String> IDs, int startIndex) {
        directionsActivity = activity;

        routeIDs = IDs;
        destinationIndex = startIndex;
        destination = routeIDs.get(destinationIndex);
        currentLoc = destination;
        deniedReplan = false;

        lastKnownCoords = null;
    }

    public void skipClicked() {
        routeIDs.remove(destinationIndex);
        destination = routeIDs.get(destinationIndex);
        updateData();
    }

    private boolean skipVisible() {
        if (destinationIndex == 0 ||(destinationIndex == (routeIDs.size() - 1))) {
            return false;
        }
        return true;
    }

    //region Button Responders
    public void nextClicked() {
        destinationIndex++;
        updateData();
    }

    public void previousClicked() {
        destinationIndex--;
        updateData();
    }
    //endregion

    //region Location Methods
    public void setLastKnownCoords(Pair<Double, Double> coords) {
        lastKnownCoords = coords;
        respondToChangedLocation();
    }

    private void respondToChangedLocation() {
        currentLoc = RouteGenerator.findClosestIdToCoords(lastKnownCoords);

        if (destinationIndex == 0 || destinationIndex == routeIDs.size() - 1) {
            updateData();
            return;
        }

        String closestUnvisited = RouteGenerator.findClosestIdToId(currentLoc, new ArrayList<String>(routeIDs.subList(destinationIndex, routeIDs.size() - 1)));
        if(!destination.equals(closestUnvisited)
                && !RouteGenerator.getParentIdFromId(destination).equals(RouteGenerator.getParentIdFromId(closestUnvisited))
                && deniedReplan == false){
            suggestReplan(closestUnvisited);
        } else {
            updateData();
        }
    }

    public void suggestReplan(String newClosest) {
        deniedReplan = true; // We set it to true here so that it wont try to suggest a replan while it's already suggesting one
        var builder = new AlertDialog.Builder(directionsActivity)
                .setTitle("Replan Suggestion")
                .setMessage("You are currently closer to " +
                        RouteGenerator.getNameFromId(newClosest) + " than to " +
                        RouteGenerator.getNameFromId(destination) + ". Would you like to replan?")
                .setPositiveButton("YES", (dialog, which) -> {
                     deniedReplan = false;
                     replanRoute();
                })
                .setNegativeButton("NO", (dialog, which) -> {
                    dialog.cancel();
                    updateData();
                });
        builder.show();
    }

    public void replanRoute() {
        ArrayList<String> newRouteIDs = new ArrayList<String>(routeIDs.subList(0, destinationIndex));
        newRouteIDs.addAll(RouteGenerator.getIdsFromRoute(RouteGenerator.generateRoute(currentLoc, new ArrayList<String>(routeIDs.subList(destinationIndex, routeIDs.size() - 1)), "entrance_exit_gate")));
        // This works correctly because the generated route does NOT include the start id, so this new route will not contain the currentLoc as a new entry
        routeIDs = newRouteIDs;
        updateData();
    }
    //endregion

    //region Data Getters
    public void updateData() {
        destination = routeIDs.get(destinationIndex);

        saveSharedPreferences();

        ArrayList<String> prevData = getPrevData();
        ArrayList<String> currData = getCurrData();
        ArrayList<String> nextData = getNextData();

        notifyDOS(prevData, currData, nextData, skipVisible());
    }

    public ArrayList<String> getPrevData() {
        if (destinationIndex == 0) return new ArrayList<String>(Arrays.asList("hide"));

        ArrayList<String> prevData = new ArrayList<String>();
        prevData.add("show");

        String nextExhibit = "Previous: " + RouteGenerator.getNameFromId(routeIDs.get(destinationIndex - 1)) + ", " +
                RouteGenerator.getDistanceBetween(currentLoc, routeIDs.get(destinationIndex - 1)) + "ft";
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
            deniedReplan = false;
        } else {
            directions = RouteGenerator.getDirectionsBetween(currentLoc, destination);
        }
        currData.add(directions);
        return currData;
    }

    public ArrayList<String> getNextData() {
        if (destinationIndex == (routeIDs.size() - 1)) return new ArrayList<String>(Arrays.asList("hide"));

        ArrayList<String> nextData = new ArrayList<String>();
        nextData.add("show");

        String nextExhibit = "Next: " + RouteGenerator.getNameFromId(routeIDs.get(destinationIndex + 1)) + ", " +
                    RouteGenerator.getDistanceBetween(currentLoc, routeIDs.get(destinationIndex + 1)) + "ft";
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
    public void notifyDOS(ArrayList<String> prevStrings, ArrayList<String> currStrings, ArrayList<String> nextStrings, boolean skipVisibility) {
        for (DirectionsObserver obs : Observers) {
            obs.update(prevStrings, currStrings, nextStrings, skipVisibility);
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

        String routeIDsJson = gson.toJson(routeIDs);

        editor.putString("storedRouteIDs", routeIDsJson);
        editor.putInt("storedStartIndex", destinationIndex);
        editor.commit();
    }
    //endregion

    //region Getters/Setters for Testing
    @VisibleForTesting
    public int getDestinationIndex() {
        return destinationIndex;
    }

    @VisibleForTesting
    public ArrayList<String> getRouteIDs() {
        return routeIDs;
    }

    @VisibleForTesting
    public void setDeniedReplan (boolean bool) {
        deniedReplan = bool;
    }
    //endregion
}
