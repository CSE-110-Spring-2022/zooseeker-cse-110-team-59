package com.example.zooseeker_cse_110_team_59.Directions;

import androidx.annotation.VisibleForTesting;

import com.example.zooseeker_cse_110_team_59.RouteGenerator;

import java.util.ArrayList;
import java.util.Arrays;

public class PlanDirections implements DirectionsSubject {
    ArrayList<DirectionsObserver> Observers = new ArrayList<DirectionsObserver>();
    private ArrayList<String> myIDs;
    private String currentLoc;
    private String destination;
    private int destinationIndex;

    public PlanDirections(ArrayList<String> IDs) {
        myIDs = IDs;
        destinationIndex = 0;
        destination = myIDs.get(destinationIndex);
    }

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
    //
    public void nextClicked() {
        currentLoc = destination;
        destinationIndex++;
        destination = myIDs.get(destinationIndex);
        ArrayList<String> currData = getCurrData();
        ArrayList<String> nextData = getNextData();
        ArrayList<String> prevData = getPrevData();
        // next text
        notifyDOS(prevData, currData, nextData);
    }

    public void previousClicked() {
        currentLoc = destination;
        destinationIndex--;
        destination = myIDs.get(destinationIndex);
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
        // directions text
        String directions = RouteGenerator.getDirectionsBetween(currentLoc, destination);
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

    @VisibleForTesting
    public int getDestinationIndex() {
        return destinationIndex;
    }
}
