package com.example.zooseeker_cse_110_team_59;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import android.text.NoCopySpan;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class UnitTestsUserStory2 {
    @Rule
    public ActivityScenarioRule<MainActivity> scenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testListStartsEmpty() {
        ActivityScenario<MainActivity> scenario = scenarioRule.getScenario();

        scenario.moveToState(Lifecycle.State.CREATED);

        scenario.onActivity(activity -> {
            ArrayList<String> testAgainst = new ArrayList<>();
            assertEquals(testAgainst, activity.getEnteredExhibits());
        });
    }

    @Test
    public void testAddToEmptyList() {
        ActivityScenario<MainActivity> scenario = scenarioRule.getScenario();

        scenario.moveToState(Lifecycle.State.CREATED);

        scenario.onActivity(activity -> {
            activity.addToLists("Elephant Odyssey");

            ArrayList<String> testAgainst = new ArrayList<>(Arrays.asList("elephant_odyssey"));

            assertEquals(testAgainst, activity.getEnteredExhibits());
        });
    }

    @Test
    public void testAddToNonEmptyList() {
        ActivityScenario<MainActivity> scenario = scenarioRule.getScenario();

        scenario.moveToState(Lifecycle.State.CREATED);

        scenario.onActivity(activity -> {
            activity.addToLists("Alligators");
            activity.addToLists("Arctic Foxes");

            ArrayList<String> testAgainst = new ArrayList<>(Arrays.asList("gators", "arctic_foxes"));

            assertEquals(testAgainst, activity.getEnteredExhibits());
        });
    }

    @Test
    public void testIsNewOnEmptyList() {
        ActivityScenario<MainActivity> scenario = scenarioRule.getScenario();

        scenario.moveToState(Lifecycle.State.CREATED);

        scenario.onActivity(activity -> {
            assertTrue(activity.isNew("Gorillas"));
        });
    }

    @Test
    public void testIsNewOnNonEmptyList() {
        ActivityScenario<MainActivity> scenario = scenarioRule.getScenario();

        scenario.moveToState(Lifecycle.State.CREATED);

        scenario.onActivity(activity -> {
            activity.addToLists("Gorillas");

            assertFalse(activity.isNew("Gorillas"));
        });
    }

    @Test
    public void testIncreaseCount() {
        ActivityScenario<MainActivity> scenario = scenarioRule.getScenario();

        scenario.moveToState(Lifecycle.State.CREATED);

        scenario.onActivity(activity -> {
            TextView listCountTextView = activity.findViewById(R.id.list_count_text_view);
            int listCountBefore = Integer.valueOf(listCountTextView.getText().toString());

            activity.addToLists("Gorillas");
            activity.increaseListsCount();
            int listCountAfter = Integer.valueOf(listCountTextView.getText().toString());

            assertEquals(listCountBefore + 1, listCountAfter);
        });
    }
}
