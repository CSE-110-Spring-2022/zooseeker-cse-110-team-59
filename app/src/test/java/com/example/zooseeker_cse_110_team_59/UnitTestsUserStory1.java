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

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class UnitTestsUserStory1 {
    @Rule
    public ActivityScenarioRule<MainActivity> scenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testEnterBlank() {
        ActivityScenario<MainActivity> scenario = scenarioRule.getScenario();

        scenario.moveToState(Lifecycle.State.CREATED);

        scenario.onActivity(activity -> {
            String input = activity.checkSearchBar();
            assertEquals("", input);
        });
    }

    @Test
    public void testEnterWord() {
        ActivityScenario<MainActivity> scenario = scenarioRule.getScenario();

        scenario.moveToState(Lifecycle.State.CREATED);

        scenario.onActivity(activity -> {
            AutoCompleteTextView searchBar = activity.findViewById(R.id.search_bar);

            searchBar.setText("One 2 red BLUE");
            String input = activity.checkSearchBar();
            assertEquals("One 2 red BLUE", input);
        });
    }

    @Test
    public void testIsValidOnValid() {
        ActivityScenario<MainActivity> scenario = scenarioRule.getScenario();

        scenario.moveToState(Lifecycle.State.CREATED);

        scenario.onActivity(activity -> {
            assertTrue(activity.isValid("Elephant Odyssey"));
        });
    }

    @Test
    public void testIsValidOnInvalid() {
        ActivityScenario<MainActivity> scenario = scenarioRule.getScenario();

        scenario.moveToState(Lifecycle.State.CREATED);

        scenario.onActivity(activity -> {
            assertFalse(activity.isValid("Table"));
        });
    }
}