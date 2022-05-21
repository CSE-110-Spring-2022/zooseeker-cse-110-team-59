package com.example.zooseeker_cse_110_team_59.List;
import android.app.Activity;
import androidx.annotation.VisibleForTesting;
import com.example.zooseeker_cse_110_team_59.Utilities;
import com.example.zooseeker_cse_110_team_59.ZooData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExhibitList implements ExhibitSubject
{
    ArrayList<ExhibitObserver> observers = new ArrayList<ExhibitObserver>();
    private Map<String, String> userEntryToID;
    private ArrayList<String> enteredExhibits;
    Activity listActivity;

    public ExhibitList(Activity activity) {
        userEntryToID = new HashMap<>();
        ZooData.vertexData.forEach((id, datum) -> {
            if (datum.kind.equals(ZooData.VertexInfo.Kind.EXHIBIT)) userEntryToID.put(datum.name, id);
        });


        listActivity = activity;
        enteredExhibits = new ArrayList<>();
    }

    //region validity check methods
    public String checkInput(String input)
    {
        if (isValid(input) && isNew(input))
        {
            enteredExhibits.add(userEntryToID.get(input));
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

    //region implemented subject interface methods
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

    //<editor-fold desc="Getters For Testing">
    @VisibleForTesting
    public List<String> getEnteredExhibits() {
        return enteredExhibits;
    }
    //</editor-fold>
}