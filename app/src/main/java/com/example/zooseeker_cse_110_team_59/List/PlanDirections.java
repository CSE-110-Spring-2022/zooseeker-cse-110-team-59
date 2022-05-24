package com.example.zooseeker_cse_110_team_59.List;

import android.app.Activity;
import android.widget.TextView;

import com.example.zooseeker_cse_110_team_59.DirectionsActivity;
import com.example.zooseeker_cse_110_team_59.R;
import com.example.zooseeker_cse_110_team_59.RoutePoint;

import java.util.ArrayList;

public class PlanDirections implements DirectionsSubject {
    ArrayList<DirectionsObserver> Observers = new ArrayList<DirectionsObserver>();
    private ArrayList<RoutePoint> myRoute;
    private int routeIndex;

    public PlanDirections(ArrayList<RoutePoint> route) {
        myRoute = route;
        routeIndex = -1;
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

    public void nextClicked() {
        routeIndex++;
        ArrayList<String> currData = new ArrayList<String>();
        ArrayList<String> nextData = new ArrayList<String>();
        // currexhibit text
        String currentExhibit = "Directions to " + myRoute.get(routeIndex).exhibitName;
        currData.add(currentExhibit);
        // directions text
        String directions = myRoute.get(routeIndex).directions;
        currData.add(directions);
        // are we on last exhibit
        String finished = "unfinished";
        if (routeIndex == (myRoute.size()-1)) {
            finished = "finished";
            nextData.add(finished);
            notifyDOS(currData, nextData);
            return;
        }
        nextData.add(finished);
        // next text
        String nextExhibit = "Next: " + myRoute.get(routeIndex + 1).exhibitName + ", " + myRoute.get(routeIndex + 1).distance + "ft";
        nextData.add(nextExhibit);
        notifyDOS(currData, nextData);
    }

    // FOR TESTING
    public int getRoutePointNum() {
        return routeIndex;
    }
}
