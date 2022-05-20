package com.example.zooseeker_cse_110_team_59.List;

import androidx.annotation.VisibleForTesting;

import com.example.zooseeker_cse_110_team_59.Utilities;
import com.example.zooseeker_cse_110_team_59.ZooData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExhibitList {
    private Map<String, String> userEntryToID;
    private ArrayList<String> enteredExhibits;

    public ExhibitList() {
        userEntryToID = new HashMap<>();
        ZooData.vertexData.forEach((id, datum) -> {
            if (datum.kind.equals(ZooData.VertexInfo.Kind.EXHIBIT)) userEntryToID.put(datum.name, id);
        });

        enteredExhibits = new ArrayList<>();
    }

    public String checkSearchBar(String input) {
        if (isValid(input) && isNew(input)) {

        }

        return input;
    }

    public boolean isValid(String searchBarInput) {
        if (userEntryToID.containsKey(searchBarInput)) {
            return true;
        } else {
            Utilities.showAlert(null, "Invalid Entry", searchBarInput + " is not an exhibit at the San Diego Zoo.");
            return false;
        }
    }

    public boolean isNew(String searchBarInput) {
        return !enteredExhibits.contains(userEntryToID.get(searchBarInput));
    }//getter methods to see values when testing

    @VisibleForTesting
    public List<String> getEnteredExhibits() {
        return enteredExhibits;
    }
}