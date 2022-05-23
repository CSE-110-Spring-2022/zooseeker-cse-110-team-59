package com.example.zooseeker_cse_110_team_59.List;

import java.util.ArrayList;

public interface DirectionsSubject {
    public void registerDO(DirectionsObserver dObs);

    public void notifyDOS(ArrayList<String> currStrings, ArrayList<String> nextStrings);
}
