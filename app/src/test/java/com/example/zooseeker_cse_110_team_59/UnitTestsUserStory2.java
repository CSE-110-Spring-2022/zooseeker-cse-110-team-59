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
            assertEquals(testAgainst, activity.getEnteredAnimals());
        });
    }

    @Test
    public void testAddToEmptyList() {
        ActivityScenario<MainActivity> scenario = scenarioRule.getScenario();

        scenario.moveToState(Lifecycle.State.CREATED);

        scenario.onActivity(activity -> {
            activity.addToLists("Elephant Odyssey");

            ArrayList<String> testAgainst = new ArrayList<>(Arrays.asList("Elephant Odyssey"));

            assertEquals(testAgainst, activity.getEnteredAnimals());
        });
    }

    @Test
    public void testAddToNonEmptyList() {
        ActivityScenario<MainActivity> scenario = scenarioRule.getScenario();

        scenario.moveToState(Lifecycle.State.CREATED);

        scenario.onActivity(activity -> {
            activity.addToLists("Elephant Odyssey");
            activity.addToLists("Arctic Fox Viewpoint");

            ArrayList<String> testAgainst = new ArrayList<>(Arrays.asList("Elephant Odyssey", "Arctic Fox Viewpoint"));

            assertEquals(testAgainst, activity.getEnteredAnimals());
        });
    }

    @Test
    public void testIncreaseCount() {
        ActivityScenario<MainActivity> scenario = scenarioRule.getScenario();

        scenario.moveToState(Lifecycle.State.CREATED);

        scenario.onActivity(activity -> {
            TextView listCountTextView = activity.findViewById(R.id.list_count_text_view);
            int listCountBefore = Integer.valueOf(listCountTextView.getText().toString());

            activity.addToLists("Gorilla Viewpoint 1");
            activity.increaseListsCount();
            int listCountAfter = Integer.valueOf(listCountTextView.getText().toString());

            assertEquals(listCountBefore + 1, listCountAfter);
        });
    }
}
