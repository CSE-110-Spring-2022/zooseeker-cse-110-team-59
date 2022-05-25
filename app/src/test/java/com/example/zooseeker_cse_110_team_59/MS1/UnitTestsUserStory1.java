package com.example.zooseeker_cse_110_team_59.MS1;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import android.content.Context;
import android.content.Intent;
import android.widget.AutoCompleteTextView;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.zooseeker_cse_110_team_59.Directions.DirectionsActivity;
import com.example.zooseeker_cse_110_team_59.FilesToLoad;
import com.example.zooseeker_cse_110_team_59.List.ExhibitList;
import com.example.zooseeker_cse_110_team_59.List.ListActivity;
import com.example.zooseeker_cse_110_team_59.R;
import com.example.zooseeker_cse_110_team_59.ZooData;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class UnitTestsUserStory1 {

    private Context context = ApplicationProvider.getApplicationContext();

    private ArrayList<String> enteredExhibits = new ArrayList<String>();

    private Intent planIntent = new Intent(context, ListActivity.class).putExtra("Entered Exhibits", enteredExhibits);

    @Rule
    public ActivityScenarioRule<ListActivity> scenarioRule = new ActivityScenarioRule<>(planIntent);

    @BeforeClass
    public static void setTestData() {
        FilesToLoad.injectNewFiles(new String[]{"test_zoo_graph_ms1.json", "test_node_info_ms1.json", "test_edge_info_ms1.json"});
        ZooData.setZooData();
    }

    @Test
    public void testEnterBlank() {
        ActivityScenario<ListActivity> scenario = scenarioRule.getScenario();

        scenario.moveToState(Lifecycle.State.CREATED);

        scenario.onActivity(activity -> {
            //this checks the initial state of the searchBar
            String input = activity.getSearchBar();
            assertEquals("", input);

            //check if ends empty
            AutoCompleteTextView searchBar = activity.getSearchBarTextView();
            assertEquals("", searchBar.getText().toString());
        });
    }

    @Test
    public void testEnterWord() {
        ActivityScenario<ListActivity> scenario = scenarioRule.getScenario();

        scenario.moveToState(Lifecycle.State.CREATED);

        scenario.onActivity(activity -> {
            AutoCompleteTextView searchBar = activity.getSearchBarTextView();

            searchBar.setText("One 2 red BLUE");
            String input = activity.getSearchBar();
            assertEquals("One 2 red BLUE", input);

            //check if end empty
            assertEquals("", searchBar.getText().toString());
        });
    }

    @Test
    public void testIsValidOnValid() {
        ActivityScenario<ListActivity> scenario = scenarioRule.getScenario();

        scenario.moveToState(Lifecycle.State.CREATED);

        scenario.onActivity(activity -> {

            assertTrue(activity.getExhibitList().isValid("Elephant Odyssey"));
        });
    }

    @Test
    public void testIsValidOnInvalid() {
        ActivityScenario<ListActivity> scenario = scenarioRule.getScenario();

        scenario.moveToState(Lifecycle.State.CREATED);

        scenario.onActivity(activity -> {
            assertFalse(activity.getExhibitList().isValid("Table"));
        });
    }
}