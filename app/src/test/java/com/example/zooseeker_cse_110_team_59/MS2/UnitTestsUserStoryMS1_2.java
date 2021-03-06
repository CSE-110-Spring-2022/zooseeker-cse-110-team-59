package com.example.zooseeker_cse_110_team_59.MS2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import android.content.Context;
import android.content.Intent;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.zooseeker_cse_110_team_59.Data.FilesToLoad;
import com.example.zooseeker_cse_110_team_59.List.AutoCompleteAdapter;
import com.example.zooseeker_cse_110_team_59.List.ListActivity;
import com.example.zooseeker_cse_110_team_59.Data.ZooData;
import com.example.zooseeker_cse_110_team_59.Utilities.TestSettings;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;

@RunWith(AndroidJUnit4.class)
public class UnitTestsUserStoryMS1_2 {

    private Context context = ApplicationProvider.getApplicationContext();

    private ArrayList<String> enteredExhibits = new ArrayList<String>();

    private Intent listIntent = new Intent(context, ListActivity.class).putExtra("Entered Exhibits", enteredExhibits);

    @Rule
    public ActivityScenarioRule<ListActivity> scenarioRule = new ActivityScenarioRule<>(listIntent);

    //region INCLUDE THIS IN EVERY UNIT TEST. Change file names to desired test files.
    @BeforeClass
    public static void setTestData() {
        FilesToLoad.injectNewFiles(new String[]{"test_zoo_graph_ms1.json", "test_node_info_ms1.json", "test_edge_info_ms1.json"});
        TestSettings.setTestClearing(true);
        TestSettings.setTestPositioning(true);
        ZooData.setZooData();
    }
    //endregion

    //Input: "table"
    //Output: empty arraylist
    @Test
    public void TestForInvalidInput()
    {
        ActivityScenario<ListActivity> scenario = scenarioRule.getScenario();
        scenario.moveToState(Lifecycle.State.CREATED);

        scenario.onActivity(activity ->
        {
            ArrayList<String> test = new ArrayList<>();
            activity.getSearchBarTextView().setText("table");

            AutoCompleteAdapter testAdapter = (AutoCompleteAdapter) activity.getSearchBarTextView().getAdapter();

            for (String animal: test)
            {
                assertTrue(testAdapter.getAutoCompleteList().contains(animal));
            }

            assertEquals(test.size(), testAdapter.getAutoCompleteList().size());
        });
    }

    @Test
    public void TestForValidInput()
    {
        ActivityScenario<ListActivity> scenario = scenarioRule.getScenario();
        scenario.moveToState(Lifecycle.State.CREATED);

        scenario.onActivity(activity ->
        {
            ArrayList<String> test = new ArrayList<>(Arrays.asList("Gorillas", "Lions", "Elephant Odyssey", "Arctic Foxes"));

            activity.getSearchBarTextView().setText("mam");
            AutoCompleteAdapter testAdapter = (AutoCompleteAdapter) activity.getSearchBarTextView().getAdapter();

            for (String animal: test)
            {
                assertTrue(testAdapter.getAutoCompleteList().contains(animal));
            }

            assertEquals(test.size(), testAdapter.getAutoCompleteList().size());
        });

    }

    @Test
    public void TestForAllTags()
    {
        ActivityScenario<ListActivity> scenario = scenarioRule.getScenario();
        scenario.moveToState(Lifecycle.State.CREATED);

        scenario.onActivity(activity ->
        {
            ArrayList<String> test = new ArrayList<>(Arrays.asList("Gorillas", "Alligators", "Lions", "Elephant Odyssey", "Arctic Foxes"));

            activity.getSearchBarTextView().setText("a");
            AutoCompleteAdapter testAdapter = (AutoCompleteAdapter) activity.getSearchBarTextView().getAdapter();

            for (String animal: test)
            {
                assertTrue(testAdapter.getAutoCompleteList().contains(animal));
            }

            assertEquals(test.size(), testAdapter.getAutoCompleteList().size());
        });
    }
}
