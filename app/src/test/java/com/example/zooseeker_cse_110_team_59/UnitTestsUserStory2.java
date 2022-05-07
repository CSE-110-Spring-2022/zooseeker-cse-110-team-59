package com.example.zooseeker_cse_110_team_59;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.NoCopySpan;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class UnitTestsUserStory2 {

    @Rule
    public ActivityScenarioRule<ListActivity> scenarioRule = new ActivityScenarioRule<>(ListActivity.class);

    @Before
    public void setTestData() {
        FilesToLoad.injectNewFiles(new String[]{"test_zoo_graph.json", "test_node_info.json", "test_edge_info.json"});
    }

    @Test
    public void testListStartsEmpty() {
        ActivityScenario<ListActivity> scenario = scenarioRule.getScenario();

        scenario.moveToState(Lifecycle.State.CREATED);

        scenario.onActivity(activity -> {
            ArrayList<String> testAgainst = new ArrayList<>();
            assertEquals(testAgainst, activity.getEnteredExhibits());
        });
    }

    @Test
    public void testAddToEmptyList() {
        ActivityScenario<ListActivity> scenario = scenarioRule.getScenario();

        scenario.moveToState(Lifecycle.State.CREATED);

        scenario.onActivity(activity -> {
            ArrayList<String> test = new ArrayList<>(Arrays.asList("elephant_odyssey"));

            List<String> result = activity.addToLists("Elephant Odyssey");

            assertEquals(test, result);
        });
    }

    @Test
    public void testAddToNonEmptyList() {
        ActivityScenario<ListActivity> scenario = scenarioRule.getScenario();

        scenario.moveToState(Lifecycle.State.CREATED);

        scenario.onActivity(activity -> {
            ArrayList<String> test = new ArrayList<>(Arrays.asList("gators", "arctic_foxes"));

            activity.addToLists("Alligators");
            List<String> result = activity.addToLists("Arctic Foxes");

            assertEquals(test, result);
        });
    }

    @Test
    public void testNewOnEmptyList() {
        ActivityScenario<ListActivity> scenario = scenarioRule.getScenario();

        scenario.moveToState(Lifecycle.State.CREATED);

        scenario.onActivity(activity -> {
            assertTrue(activity.isNew("Gorillas"));
        });
    }

    @Test
    public void testNewOnNonEmptyList() {
        ActivityScenario<ListActivity> scenario = scenarioRule.getScenario();

        scenario.moveToState(Lifecycle.State.CREATED);

        scenario.onActivity(activity -> {
            activity.addToLists("Gorillas");

            assertTrue(activity.isNew("Elephant Odyssey"));
        });
    }

    @Test
    public void testNotNewOnNonEmptyList() {
        ActivityScenario<ListActivity> scenario = scenarioRule.getScenario();

        scenario.moveToState(Lifecycle.State.CREATED);

        scenario.onActivity(activity -> {
            activity.addToLists("Gorillas");

            assertFalse(activity.isNew("Gorillas"));
        });
    }

    @Test
    public void testIncreaseCountFromZero() {
        ActivityScenario<ListActivity> scenario = scenarioRule.getScenario();

        scenario.moveToState(Lifecycle.State.CREATED);

        scenario.onActivity(activity -> {
            TextView listCountTextView = activity.findViewById(R.id.list_count_text_view);
            int listCountBefore = Integer.valueOf(listCountTextView.getText().toString());
            assertEquals(0, listCountBefore);

            activity.addToLists("Gorillas");
            activity.increaseListsCount();
            int listCountAfter = Integer.valueOf(listCountTextView.getText().toString());

            assertEquals(1, listCountAfter);
        });
    }

    @Test
    public void testIncreaseCountFromNonZero() {
        ActivityScenario<ListActivity> scenario = scenarioRule.getScenario();

        scenario.moveToState(Lifecycle.State.CREATED);

        scenario.onActivity(activity -> {
            activity.addToLists("Gorillas");
            activity.increaseListsCount();

            TextView listCountTextView = activity.findViewById(R.id.list_count_text_view);
            int listCountBefore = Integer.valueOf(listCountTextView.getText().toString());

            activity.addToLists("Alligators");
            activity.increaseListsCount();
            int listCountAfter = Integer.valueOf(listCountTextView.getText().toString());

            assertEquals(listCountBefore + 1, listCountAfter);
        });
    }
}
