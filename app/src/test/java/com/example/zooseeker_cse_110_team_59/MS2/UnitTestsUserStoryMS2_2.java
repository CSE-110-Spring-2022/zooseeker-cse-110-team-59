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
public class UnitTestsUserStoryMS2_2 {
    private Context context = ApplicationProvider.getApplicationContext();

    private ArrayList<String> route = new ArrayList<String>(Arrays.asList("entrance_exit_gate", "flamingo", "koi", "entrance_exit_gate"));

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
    //endregion

    @Test
    public void testFindClosestIdToCoords() {
        ActivityScenario<DirectionsActivity> scenario = scenarioRule.getScenario();

        scenario.moveToState(Lifecycle.State.CREATED);

        scenario.onActivity(activity -> {
            Pair<Double, Double> testNWCorner = Pair.create(32.7250121, -117.18369);
            assertEquals("fern_canyon", RouteGenerator.findClosestIdToCoords(testNWCorner));

            Pair<Double, Double> testSWCorner = Pair.create(32.721098, -117.18369);
            assertEquals("fern_canyon", RouteGenerator.findClosestIdToCoords(testSWCorner));

            Pair<Double, Double> testSECorner = Pair.create(32.721098, -117.14936);
            assertEquals("intxn_front_lagoon_1", RouteGenerator.findClosestIdToCoords(testSECorner));

            Pair<Double, Double> testNECorner = Pair.create(32.7250121, -117.14936);
            assertEquals("intxn_front_lagoon_1", RouteGenerator.findClosestIdToCoords(testNECorner));
        });
    }

    @Test
    public void testPositionStaysWhenDestinationChanges() {
        ActivityScenario<DirectionsActivity> scenario = scenarioRule.getScenario();

        scenario.moveToState(Lifecycle.State.CREATED);

        scenario.onActivity(activity -> {
            Button prevBtn = activity.findViewById(R.id.previous_button);
            Button nextBtn = activity.findViewById(R.id.next_btn);
            TextView directions = activity.findViewById(R.id.directions_text);
            TextView currExhTitle = activity.findViewById(R.id.place_name);

            activity.mockLocationUpdate(Pair.create(32.73459618734685, -117.14936));

            assertEquals("1. Proceed on Gate Path 1100.0 ft towards Front Street.\n" +
                    "2. Proceed on Front Street 2700.0 ft towards Monkey Trail.\n" +
                    "3. Proceed on Monkey Trail 1500.0 ft towards Flamingos.\n", directions.getText().toString());
            assertEquals("Directions to Flamingos", currExhTitle.getText());
            assertEquals("Previous: Entrance and Exit Gate, 0.0ft", prevBtn.getText().toString());
            assertEquals("Next: Koi Fish, 6500.0ft", nextBtn.getText().toString());

            nextBtn.performClick();

            assertEquals("1. Proceed on Gate Path 1100.0 ft towards Front Street.\n" +
                    "2. Proceed on Front Street 3200.0 ft towards Terrace Lagoon Loop.\n" +
                    "3. Proceed on Terrace Lagoon Loop 2200.0 ft towards Koi Fish.\n", directions.getText().toString());
            assertEquals("Directions to Koi Fish", currExhTitle.getText());
            assertEquals("Previous: Flamingos, 5300.0ft", prevBtn.getText().toString());
            assertEquals("Next: Entrance and Exit Gate, 0.0ft", nextBtn.getText().toString());
        });
    }

    @Test
    public void testApproachDestination() {
        ActivityScenario<DirectionsActivity> scenario = scenarioRule.getScenario();

        scenario.moveToState(Lifecycle.State.CREATED);

        scenario.onActivity(activity -> {
            Button prevBtn = activity.findViewById(R.id.previous_button);
            Button nextBtn = activity.findViewById(R.id.next_btn);
            TextView directions = activity.findViewById(R.id.directions_text);
            TextView currExhTitle = activity.findViewById(R.id.place_name);

            activity.mockLocationUpdate(Pair.create(32.73459618734685, -117.14936));

            assertEquals("1. Proceed on Gate Path 1100.0 ft towards Front Street.\n" +
                    "2. Proceed on Front Street 2700.0 ft towards Monkey Trail.\n" +
                    "3. Proceed on Monkey Trail 1500.0 ft towards Flamingos.\n", directions.getText().toString());
            assertEquals("Directions to Flamingos", currExhTitle.getText());
            assertEquals("Previous: Entrance and Exit Gate, 0.0ft", prevBtn.getText().toString());
            assertEquals("Next: Koi Fish, 6500.0ft", nextBtn.getText().toString());

            activity.mockLocationUpdate(Pair.create(32.73453269952234, -117.1526194979576));

            assertEquals("1. Proceed on Front Street 2700.0 ft towards Monkey Trail.\n" +
                    "2. Proceed on Monkey Trail 1500.0 ft towards Flamingos.\n", directions.getText().toString());
            assertEquals("Directions to Flamingos", currExhTitle.getText());
            assertEquals("Previous: Entrance and Exit Gate, 1100.0ft", prevBtn.getText().toString());
            assertEquals("Next: Koi Fish, 5400.0ft", nextBtn.getText().toString());

            activity.mockLocationUpdate(Pair.create(32.74132437308792, -117.15599314253906));

            assertEquals("1. Proceed on Monkey Trail 1500.0 ft towards Flamingos.\n", directions.getText().toString());
            assertEquals("Directions to Flamingos", currExhTitle.getText());
            assertEquals("Previous: Entrance and Exit Gate, 3800.0ft", prevBtn.getText().toString());
            assertEquals("Next: Koi Fish, 8100.0ft", nextBtn.getText().toString());

            activity.mockLocationUpdate(Pair.create(32.7440416465169, -117.15952052282296));

            assertEquals("You have arrived at Flamingos.\n", directions.getText().toString());
            assertEquals("Directions to Flamingos", currExhTitle.getText());
            assertEquals("Previous: Entrance and Exit Gate, 5300.0ft", prevBtn.getText().toString());
            assertEquals("Next: Koi Fish, 9600.0ft", nextBtn.getText().toString());
        });
    }

    @Test
    public void testAwayFromDestination() {
        ActivityScenario<DirectionsActivity> scenario = scenarioRule.getScenario();

        scenario.moveToState(Lifecycle.State.CREATED);

        scenario.onActivity(activity -> {
            Button prevBtn = activity.findViewById(R.id.previous_button);
            Button nextBtn = activity.findViewById(R.id.next_btn);
            TextView directions = activity.findViewById(R.id.directions_text);
            TextView currExhTitle = activity.findViewById(R.id.place_name);

            activity.mockLocationUpdate(Pair.create(32.73459618734685, -117.14936));

            assertEquals("1. Proceed on Gate Path 1100.0 ft towards Front Street.\n" +
                    "2. Proceed on Front Street 2700.0 ft towards Monkey Trail.\n" +
                    "3. Proceed on Monkey Trail 1500.0 ft towards Flamingos.\n", directions.getText().toString());
            assertEquals("Directions to Flamingos", currExhTitle.getText());
            assertEquals("Previous: Entrance and Exit Gate, 0.0ft", prevBtn.getText().toString());
            assertEquals("Next: Koi Fish, 6500.0ft", nextBtn.getText().toString());

            activity.mockLocationUpdate(Pair.create(32.73453269952234, -117.1526194979576));

            assertEquals("1. Proceed on Front Street 2700.0 ft towards Monkey Trail.\n" +
                    "2. Proceed on Monkey Trail 1500.0 ft towards Flamingos.\n", directions.getText().toString());
            assertEquals("Directions to Flamingos", currExhTitle.getText());
            assertEquals("Previous: Entrance and Exit Gate, 1100.0ft", prevBtn.getText().toString());
            assertEquals("Next: Koi Fish, 5400.0ft", nextBtn.getText().toString());

            activity.mockLocationUpdate(Pair.create(32.72624997716322, -117.15599314253906));

            assertEquals("1. Proceed on Front Street 5900.0 ft towards Monkey Trail.\n" +
                    "2. Proceed on Monkey Trail 1500.0 ft towards Flamingos.\n", directions.getText().toString());
            assertEquals("Directions to Flamingos", currExhTitle.getText());
            assertEquals("Previous: Entrance and Exit Gate, 4300.0ft", prevBtn.getText().toString());
            assertEquals("Next: Koi Fish, 2200.0ft", nextBtn.getText().toString());

            activity.mockLocationUpdate(Pair.create(32.72109826903826, -117.15952052282296));

            assertEquals("1. Proceed on Terrace Lagoon Loop 2200.0 ft towards Front Street.\n" +
                    "2. Proceed on Front Street 5900.0 ft towards Monkey Trail.\n" +
                    "3. Proceed on Monkey Trail 1500.0 ft towards Flamingos.\n", directions.getText().toString());
            assertEquals("Directions to Flamingos", currExhTitle.getText());
            assertEquals("Previous: Entrance and Exit Gate, 6500.0ft", prevBtn.getText().toString());
            assertEquals("Next: Koi Fish, 0.0ft", nextBtn.getText().toString());
        });
    }
}
