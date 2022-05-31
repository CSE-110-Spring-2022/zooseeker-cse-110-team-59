package com.example.zooseeker_cse_110_team_59.MS2;

import static org.junit.Assert.assertEquals;

import android.content.Context;
import android.content.Intent;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.zooseeker_cse_110_team_59.Directions.DirectionsActivity;
import com.example.zooseeker_cse_110_team_59.Data.FilesToLoad;
import com.example.zooseeker_cse_110_team_59.R;
import com.example.zooseeker_cse_110_team_59.Data.ZooData;
import com.example.zooseeker_cse_110_team_59.Utilities.TestSettings;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;

@RunWith(AndroidJUnit4.class)
public class UnitTestsUserStoryMS2_4 {

    private Context context = ApplicationProvider.getApplicationContext();

    private ArrayList<String> route = new ArrayList<String>(Arrays.asList("entrance_exit_gate", "hippo", "gorilla", "entrance_exit_gate"));

    private Intent directionsIntent = new Intent(context, DirectionsActivity.class).putExtra("IDs in Order", route).putExtra("Start Index", 1).putExtra("Detail Level", "BRIEF");

    @Rule
    public ActivityScenarioRule<DirectionsActivity> scenarioRule = new ActivityScenarioRule<>(directionsIntent);

    //region INCLUDE THIS IN EVERY UNIT TEST. Change file names to desired test files.
    @BeforeClass
    public static void setTestData() {
        FilesToLoad.injectNewFiles(new String[]{"test_zoo_graph_ms2.json", "test_node_info_ms2.json", "test_edge_info_ms2.json"});
        TestSettings.setTestClearing(true);
        TestSettings.setTestPositioning(true);
        ZooData.setZooData();
    }
    //endregion

    @Test
    public void testPreviousButtonIsClicked() {
        ActivityScenario<DirectionsActivity> scenario = scenarioRule.getScenario();

        scenario.moveToState(Lifecycle.State.CREATED);

        scenario.onActivity(activity -> {
            Button finishBtn = activity.findViewById(R.id.finish_btn);
            Button prevBtn = activity.findViewById(R.id.previous_button);

            prevBtn.performClick();

            assertEquals(0, activity.getPlanDirections().getDestinationIndex());
            assertEquals(View.INVISIBLE, finishBtn.getVisibility());
        });
    }

    @Test
    public void testPrevManyTimesAtBeginning() {
        ActivityScenario<DirectionsActivity> scenario = scenarioRule.getScenario();

        scenario.moveToState(Lifecycle.State.CREATED);

        scenario.onActivity(activity -> {
            Button nextBtn = activity.findViewById(R.id.next_btn);
            Button prevBtn = activity.findViewById(R.id.previous_button);

            nextBtn.performClick();
            nextBtn.performClick();
            prevBtn.performClick();
            prevBtn.performClick();
            prevBtn.performClick();


            assertEquals(0, activity.getPlanDirections().getDestinationIndex());
            assertEquals(View.INVISIBLE, prevBtn.getVisibility());
        });
    }

    @Test
    public void testPreviousButtonDirections() {
        ActivityScenario<DirectionsActivity> scenario = scenarioRule.getScenario();

        scenario.moveToState(Lifecycle.State.CREATED);

        scenario.onActivity(activity -> {
            Button prevBtn = activity.findViewById(R.id.previous_button);

            activity.mockLocationUpdate(Pair.create(32.73459618734685, -117.14936));

            assertEquals("Previous: Entrance and Exit Gate, 0.0ft", prevBtn.getText());
        });
    }

    @Test
    public void testPreviousButtonUpdatesDirections() {
        ActivityScenario<DirectionsActivity> scenario = scenarioRule.getScenario();

        scenario.moveToState(Lifecycle.State.CREATED);

        scenario.onActivity(activity -> {
            Button prevBtn = activity.findViewById(R.id.previous_button);
            Button nextBtn = activity.findViewById(R.id.next_btn);
            nextBtn.performClick();
            nextBtn.performClick();
            prevBtn.performClick();
            TextView directions = activity.findViewById(R.id.directions_text);
            TextView currExhTitle = activity.findViewById(R.id.place_name);

            activity.mockLocationUpdate(Pair.create(32.73459618734685, -117.14936));

            assertEquals("1. Proceed on Gate Path 1100.0 ft towards Treetops Way.\n" +
                    "2. Proceed on Treetops Way 4400.0 ft towards Hippo Trail.\n" +
                    "3. Proceed on Hippo Trail 4500.0 ft towards Monkey Trail.\n" +
                    "4. Proceed on Monkey Trail 2400.0 ft towards Gorillas.\n", directions.getText());
            assertEquals("Directions to Gorillas", currExhTitle.getText());
        });

    }
}
