package com.example.zooseeker_cse_110_team_59.Directions;

import java.util.ArrayList;

public interface DirectionsObserver {
    public void update(ArrayList<String> prevStrings, ArrayList<String> currStrings, ArrayList<String> nextStrings, boolean skipVisibility);
}
