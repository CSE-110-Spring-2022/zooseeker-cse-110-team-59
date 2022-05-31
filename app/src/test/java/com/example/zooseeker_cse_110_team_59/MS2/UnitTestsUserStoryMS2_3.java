package com.example.zooseeker_cse_110_team_59.MS2;

import static org.junit.Assert.assertEquals;

import android.content.Context;
import android.content.Intent;
import android.util.Pair;
import android.widget.Button;
import android.widget.TextView;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.zooseeker_cse_110_team_59.Data.FilesToLoad;
import com.example.zooseeker_cse_110_team_59.Data.ZooData;
import com.example.zooseeker_cse_110_team_59.Directions.DirectionsActivity;
import com.example.zooseeker_cse_110_team_59.R;
import com.example.zooseeker_cse_110_team_59.Route.RouteGenerator;
import com.example.zooseeker_cse_110_team_59.Utilities.TestSettings;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;

@RunWith(AndroidJUnit4.class)
public class UnitTestsUserStoryMS2_3 {
    private Context context = ApplicationProvider.getApplicationContext();

    private ArrayList<String> route = new ArrayList<String>(Arrays.asList("entrance_exit_gate", "koi", "gorilla", "entrance_exit_gate"));

    private Intent directionsIntent = new Intent(context, DirectionsActivity.class).putExtra("IDs in Order", route).putExtra("Start Index", 1);

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

    @Test
    public void testReplanDirections() {
        ActivityScenario<DirectionsActivity> scenario = scenarioRule.getScenario();

        scenario.moveToState(Lifecycle.State.CREATED);

        scenario.onActivity(activity -> {

            TextView directions = activity.findViewById(R.id.directions_text);
            TextView currExhTitle = activity.findViewById(R.id.place_name);
            Button prevBtn = activity.findViewById(R.id.previous_button);
            Button nextBtn = activity.findViewById(R.id.next_btn);

            activity.mockLocationUpdate(Pair.create(32.73459618734685, -117.14936));

            assertEquals("1. Proceed on Gate Path 1100.0 ft towards Front Street.\n" +
                    "2. Proceed on Front Street 3200.0 ft towards Terrance Lagoon Loop.\n" +
                    "3. Proceed on Terrance Lagoon Loop 2200.0 ft towards Koi Fish.\n", directions.getText().toString());
            assertEquals("Directions to Koi Fish", currExhTitle.getText());
            assertEquals("Previous: Entrance and Exit Gate, 0.0ft", nextBtn.getText().toString());
            assertEquals("Next: Gorillas, 12400.0ft", nextBtn.getText().toString());


            /**
             * Here's how I think you should do this test instead, to test specifically replanRoute()
             *
             * 1. First, do the above mock location line to move yourself to the entrance exit gate
             * 2. Then, do activity.getPlanDirections().setDeniedReplan(true); so that the app doesn't try to
             * replan itself automatically. You wanna do it manually.
             * 3. Then, move yourself to a location that is closer to Gorillas than to Koi Fish
             * 4. Then, run replanRoute(), and check that routeIDs in PlanDirections is now {entrance_exit_gate,
             * gorilla, koi, entrance_exit_gate}.
             *
             * I don't think yall actually have to do another test, since replanning only ever occurs if a new
             * place is closer, which means the order would always change when actually replanning. Just this one
             * is enough probably.
             */

            activity.getPlanDirections().setDeniedReplan(false);

            activity.mockLocationUpdate(Pair.create(32.74711745394194, -117.18047982358976));

            assertEquals("1. Proceed on Monkey Trail 2400.0 ft towards Hippo Trail.\n" +
                    "2. Proceed on Hippo Trail 4500.0 ft towards Treetops Way.\n" +
                    "3. Proceed on Treetops Way 4400.0 ft towards Gate path.\n",
                    "4. Proceed on Gate Path 1100.0 ft towards Entrance and Exit Gate.\n", directions.getText().toString());
            assertEquals("Directions to Entrance and Exit Gate", currExhTitle.getText());
            assertEquals("Next: Koi Fish, 16700.0ft", nextBtn.getText().toString());


       });

    }
}
    //endregion

