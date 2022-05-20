package com.example.zooseeker_cse_110_team_59;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import android.widget.AutoCompleteTextView;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.zooseeker_cse_110_team_59.List.ListActivity;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class UnitTestsUserStory1 {

    @Rule
    public ActivityScenarioRule<ListActivity> scenarioRule = new ActivityScenarioRule<>(ListActivity.class);

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
            String input = activity.checkSearchBar();
            assertEquals("", input);

            AutoCompleteTextView searchBar = activity.findViewById(R.id.search_bar);
            assertEquals("", searchBar.getText().toString());
        });
    }

    @Test
    public void testEnterWord() {
        ActivityScenario<ListActivity> scenario = scenarioRule.getScenario();

        scenario.moveToState(Lifecycle.State.CREATED);

        scenario.onActivity(activity -> {
            AutoCompleteTextView searchBar = activity.findViewById(R.id.search_bar);

            searchBar.setText("One 2 red BLUE");
            String input = activity.checkSearchBar();
            assertEquals("One 2 red BLUE", input);

            assertEquals("", searchBar.getText().toString());
        });
    }

    @Test
    public void testIsValidOnValid() {
        ActivityScenario<ListActivity> scenario = scenarioRule.getScenario();

        scenario.moveToState(Lifecycle.State.CREATED);

        scenario.onActivity(activity -> {
            assertTrue(activity.isValid("Elephant Odyssey"));
        });
    }

    @Test
    public void testIsValidOnInvalid() {
        ActivityScenario<ListActivity> scenario = scenarioRule.getScenario();

        scenario.moveToState(Lifecycle.State.CREATED);

        scenario.onActivity(activity -> {
            assertFalse(activity.isValid("Table"));
        });
    }
}