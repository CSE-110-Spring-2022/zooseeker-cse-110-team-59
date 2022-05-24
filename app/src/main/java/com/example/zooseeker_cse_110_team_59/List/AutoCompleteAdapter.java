package com.example.zooseeker_cse_110_team_59.List;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.VisibleForTesting;

import java.util.ArrayList;

// Link: https://stackoverflow.com/questions/24545345/how-to-bind-autocompletetextview-from-hashmap
// Title: How to bind AutoCompleteTextView from HashMap
// Date Captured: May 20th, 2022
// Usage: For the structure of creating a custom ArrayAdapter. Obviously, the structure of the below code is very
// similar to the top answer given, but that top answer is for very specific key-value pairs, and you can't check
// if part of a key is equal to something, one if the entire key is equal to something. So, we had to remodel
// the code to work for our needs, since the exact keys cannot be known ahead of time and would change across datasets.
// The answer we came up with modified the given code to use ArrayList<ArrayList<String>> instead of ArrayList<HashMap<String,String>>,
// and we additionally loaded our exhibitTagList in a much neater fashion than the given solution.

public class AutoCompleteAdapter extends ArrayAdapter<String> implements Filterable {

    ArrayList<ArrayList<String>> exhibitTagList;
    ArrayList<String> autoCompleteList;

    public AutoCompleteAdapter(Context context, int textViewResourceId, ArrayList<ArrayList<String>> data) {
        super(context, textViewResourceId);
        exhibitTagList = data;
    }

    @Override
    public int getCount() {
        return autoCompleteList.size();
    }

    @Override
    public String getItem(int position) {
        return autoCompleteList.get(position);
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(final CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null) {
                    autoCompleteList = new ArrayList<String>();
                    for (int i = 0; i < exhibitTagList.size(); i++) {
                        boolean found = false;
                        for (String validEntry : exhibitTagList.get(i)) {
                            for (String word : validEntry.split(" ")) {
                                if (word.toLowerCase().startsWith(constraint.toString().toLowerCase())) {
                                    found = true;
                                }
                            }
                        }
                        if (found) {
                            autoCompleteList.add(exhibitTagList.get(i).get(0));
                        }
                    }

                    filterResults.values = autoCompleteList;

                    filterResults.count = autoCompleteList.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };
        return filter;
    }

    //<editor-fold desc="Getters for Testing">
    @VisibleForTesting
    public ArrayList<String> getAutoCompleteList() { return autoCompleteList; }
    //</editor-fold>
}
