package com.example.zooseeker_cse_110_team_59.Directions;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Pair;

import androidx.annotation.VisibleForTesting;

import com.example.zooseeker_cse_110_team_59.Route.RouteGenerator;
import com.example.zooseeker_cse_110_team_59.Data.SharedPreferencesSaver;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

public class PlanDirections implements DirectionsSubject, SharedPreferencesSaver {
    ArrayList<DirectionsObserver> Observers = new ArrayList<DirectionsObserver>();
    private ArrayList<String> routeIDs;
    private Activity directionsActivity;
    private String currentLoc;
    private String destination;
    private String detailLevel;
    private int destinationIndex;
    private Pair<Double, Double> lastKnownCoords;

    public PlanDirections(Activity activity, ArrayList<String> startRouteIDs, int startIndex, String startDetailLevel) {
        directionsActivity = activity;

        routeIDs = startRouteIDs;
        destinationIndex = startIndex;
        destination = routeIDs.get(destinationIndex);
        currentLoc = destination;
        detailLevel = startDetailLevel;

        lastKnownCoords = null;
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

    public void detailLevelBriefClicked() {
        detailLevel = "BRIEF";
        updateData();
    }

    public void detailLevelDetailedClicked() {
        detailLevel = "DETAILED";
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
        updateData();

        /**
         * HERE GOES THE CODE FOR DOING ANYTHING WITH THE NEW LOCATION. FOR BETTER SRP/OCP,
         * SPLIT THE BELOW INTO MULTIPLE METHODS INSTEAD OF PUTTING IT ALL HERE.
         *
         *
         * Replan: Instead of calling updateData in the above right away, of the routeIDs
         * from destinationIndex to the end (WITHOUT the last one in there, which is always
         * entrance_exit_gate), find the closest one to this new currentLoc. If destination !=
         * this closest unvisited exhibit, they're not at the same place (so different parent_ids)
         * and deniedReplan == false (more on this later), then suggest a replan (to see a way suggesting
         * a replan might work, look at the AlertDialog.builder usage in DirectionsActivity.onMockButtonClicked,
         * there isn't a need to include fields in this one here, but you will need to run specific code
         * based on their response, like in that usage over there). Otherwise, if not all of the three
         * above statements are true, readjust.
         *
         *      IF THEY SAY YES TO THE REPLAN, take the portion of routeIDs from 0 to destinationIndex - 1
         * and save them into a new ArrayList, in their original order (you can do this with
         * for (int i = 0; i < destinationIndex - 1; i++) to avoid OutOfBounds). Then, with start_id =
         * currentLoc, replan_portion = routeIDs from destinationIndex to the end (WITHOUT the last one
         * in there, which is always entrance_exit_gate), and end_id = entrance_exit_gate, run
         * RouteGenerator.generateRoute, and get the IDs of the resulting route (this works because
         * the generated route DOES NOT INCLUDE the start_id, so our current location won't get
         * added to the routeIDs). Save those IDs (again, in order) to that new ArrayList we made
         * earlier to save the first half. THEN, set routeIDs = this new ArrayList (which is the route
         * in replanned order), and call updateData.
         *
         *      IF THEY SAY NO TO THE REPLAN, set deniedReplan = true, and readjust. deniedReplan should
         * only go back to being false when either (a) they skip (we'll get to that eventually) or (b)
         * they reach their destination (you can see a special clause for this in getCurrData(),
         * where if the currentLoc and destination have 0.0 distance between them, a different set
         * of directions are set).
         */
    }
    //endregion

    //region Data Getters
    public void updateData() {
        destination = routeIDs.get(destinationIndex);

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
        } else {
            directions = RouteGenerator.getDirectionsBetween(currentLoc, destination, detailLevel);
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
        if (preferences.contains("storedDetailLevel")) editor.remove("storedDetailLevel");
        editor.commit();

        Gson gson = new Gson();

        String routeIDsJson = gson.toJson(routeIDs);

        editor.putString("storedRouteIDs", routeIDsJson);
        editor.putInt("storedStartIndex", destinationIndex);
        editor.putString("storedDetailLevel", detailLevel);
        editor.commit();
    }
    //endregion

    //region Getters for Testing
    @VisibleForTesting
    public int getDestinationIndex() {
        return destinationIndex;
    }

    @VisibleForTesting
    public ArrayList<String> getRouteIDs() {
        return routeIDs;
    }
    //endregion
}
