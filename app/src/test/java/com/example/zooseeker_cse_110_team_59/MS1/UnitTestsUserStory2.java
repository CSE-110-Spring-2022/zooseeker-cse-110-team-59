package com.example.zooseeker_cse_110_team_59.MS1;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import android.content.Context;
import android.content.Intent;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.zooseeker_cse_110_team_59.Data.FilesToLoad;
import com.example.zooseeker_cse_110_team_59.List.ListActivity;
import com.example.zooseeker_cse_110_team_59.Data.ZooData;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class UnitTestsUserStory2 {

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
    public void testListStartsEmpty() {
        ActivityScenario<ListActivity> scenario = scenarioRule.getScenario();

        scenario.moveToState(Lifecycle.State.CREATED);

        scenario.onActivity(activity -> {
            ArrayList<String> testAgainst = new ArrayList<>();
            assertEquals(testAgainst, activity.getExhibitList().getEnteredExhibits());
        });
    }

    @Test
    public void testAddToEmptyList() {
        ActivityScenario<ListActivity> scenario = scenarioRule.getScenario();

        scenario.moveToState(Lifecycle.State.CREATED);

        scenario.onActivity(activity -> {
            ArrayList<String> test = new ArrayList<>(Arrays.asList("elephant_odyssey"));

            activity.getSearchBarTextView().setText("Elephant Odyssey");
            activity.checkSearchBar();

            assertEquals(test, activity.getExhibitList().getEnteredExhibits());
        });
    }

    @Test
    public void testAddToNonEmptyList() {
        ActivityScenario<ListActivity> scenario = scenarioRule.getScenario();

        scenario.moveToState(Lifecycle.State.CREATED);

        scenario.onActivity(activity -> {
            ArrayList<String> test = new ArrayList<>(Arrays.asList("gators", "arctic_foxes"));

            activity.getSearchBarTextView().setText("Alligators");
            activity.checkSearchBar();
            activity.getSearchBarTextView().setText("Arctic Foxes");
            activity.checkSearchBar();

            assertEquals(test, activity.getExhibitList().getEnteredExhibits());
        });
    }

    @Test
    public void testNewOnEmptyList() {
        ActivityScenario<ListActivity> scenario = scenarioRule.getScenario();

        scenario.moveToState(Lifecycle.State.CREATED);

        scenario.onActivity(activity -> {
            assertTrue(activity.getExhibitList().isNew("Gorillas"));
        });
    }

    @Test
    public void testNewOnNonEmptyList() {
        ActivityScenario<ListActivity> scenario = scenarioRule.getScenario();

        scenario.moveToState(Lifecycle.State.CREATED);

        scenario.onActivity(activity -> {
            activity.getSearchBarTextView().setText("Gorillas");
            activity.checkSearchBar();

            assertTrue(activity.getExhibitList().isNew("Elephant Odyssey"));
        });
    }

    @Test
    public void testNotNewOnNonEmptyList() {
        ActivityScenario<ListActivity> scenario = scenarioRule.getScenario();

        scenario.moveToState(Lifecycle.State.CREATED);

        scenario.onActivity(activity -> {
            activity.getSearchBarTextView().setText("Gorillas");
            activity.checkSearchBar();

            assertFalse(activity.getExhibitList().isNew("Gorillas"));
        });
    }

    @Test
    public void testIncreaseCountFromZero() {
        ActivityScenario<ListActivity> scenario = scenarioRule.getScenario();

        scenario.moveToState(Lifecycle.State.CREATED);

        scenario.onActivity(activity -> {
            int listCountBefore = Integer.valueOf(activity.getListCount().getText().toString());
            assertEquals(0, listCountBefore);

            activity.getSearchBarTextView().setText("Gorillas");
            activity.checkSearchBar();
            int listCountAfter = Integer.valueOf(activity.getListCount().getText().toString());

            assertEquals(1, listCountAfter);
        });
    }

    @Test
    public void testIncreaseCountFromNonZero() {
        ActivityScenario<ListActivity> scenario = scenarioRule.getScenario();

        scenario.moveToState(Lifecycle.State.CREATED);

        scenario.onActivity(activity -> {
            activity.getSearchBarTextView().setText("Gorillas");
            activity.checkSearchBar();

            int listCountBefore = Integer.valueOf(activity.getListCount().getText().toString());

            activity.getSearchBarTextView().setText("Alligators");
            activity.checkSearchBar();
            int listCountAfter = Integer.valueOf(activity.getListCount().getText().toString());

            assertEquals(listCountBefore + 1, listCountAfter);
        });
    }
}
