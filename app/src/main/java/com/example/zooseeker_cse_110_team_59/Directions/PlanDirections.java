package com.example.zooseeker_cse_110_team_59.Directions;

import com.example.zooseeker_cse_110_team_59.Directions.DirectionsObserver;
import com.example.zooseeker_cse_110_team_59.Directions.DirectionsSubject;
import com.example.zooseeker_cse_110_team_59.RouteGenerator;
import com.example.zooseeker_cse_110_team_59.RoutePoint;

import java.util.ArrayList;

public class PlanDirections implements DirectionsSubject {
    ArrayList<DirectionsObserver> Observers = new ArrayList<DirectionsObserver>();
    private ArrayList<RoutePoint> myRoute;
    private ArrayList<String> myIDs;
    private String currentLoc;
    private String destination;
    private int destinationIndex;

    public PlanDirections(ArrayList<RoutePoint> route, ArrayList<String> IDs) {
        myRoute = route;
        myIDs = IDs;
        destinationIndex = 0;
    }

    @Override
    public void registerDO(DirectionsObserver dObs) {
        Observers.add(dObs);
    }
    @Override
    public void notifyDOS(ArrayList<String> currStrings, ArrayList<String> nextStrings) {
        for (DirectionsObserver obs : Observers) {
            obs.update(currStrings, nextStrings);
        }
    }
    //
    public void nextClicked() {
        currentLoc = myIDs.get(destinationIndex);
        destinationIndex++;
        destination = myIDs.get(destinationIndex);
        ArrayList<String> currData = getCurrData();
        ArrayList<String> nextData = getNextData();
        // next text
        notifyDOS(currData, nextData);
    }

    public void previousClicked() {
        currentLoc = myIDs.get(destinationIndex);
        destinationIndex--;
        if (destinationIndex < 0) { destinationIndex = 0; }
        destination = myIDs.get(destinationIndex);
        ArrayList<String> currData = getCurrData();
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
        ArrayList<String> nextData = new ArrayList<String>();
        String finished = "unfinished";
        if (destinationIndex == (myRoute.size())) {
            finished = "finished";
        }
        nextData.add(finished);
        String nextExhibit = "";
        if (destinationIndex < (myRoute.size())) {
            nextExhibit = "Next: " + RouteGenerator.getNameFromId(myIDs.get(destinationIndex + 1)) + ", " +
                    RouteGenerator.getDistanceBetween(currentLoc, destination) + "ft";
        }
        nextData.add(nextExhibit);
        return nextData;
    }

    // FOR TESTING
    public int getRoutePointNum() {
        return destinationIndex;
    }
}
