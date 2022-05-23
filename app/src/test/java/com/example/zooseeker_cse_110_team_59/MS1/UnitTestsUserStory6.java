package com.example.zooseeker_cse_110_team_59.MS1;

import static org.junit.Assert.assertEquals;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.zooseeker_cse_110_team_59.DirectionsActivity;
import com.example.zooseeker_cse_110_team_59.FilesToLoad;
import com.example.zooseeker_cse_110_team_59.R;
import com.example.zooseeker_cse_110_team_59.RoutePoint;
import com.example.zooseeker_cse_110_team_59.ZooData;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;

@RunWith(AndroidJUnit4.class)
public class UnitTestsUserStory6 {

    private Context context = ApplicationProvider.getApplicationContext();

    // route with Gorillas, Lions, and Elephant Odyssey
    private ArrayList<RoutePoint> route = new ArrayList<>(Arrays.asList(
            new RoutePoint("Gorillas", "1. Proceed on Entrance Way 10.0 ft towards Africa Rocks Street.\n" +
                    "2. Proceed on Africa Rocks Street 200.0 ft towards Gorillas.\n", 210.0, "Africa Rocks Street"),
            new RoutePoint("Lions", "1. Proceed on Africa Rocks Street 200.0 ft towards Lions.\n", 200.0, "Africa Rocks Street"),
            new RoutePoint("Elephant Odyssey", "1. Proceed on Africa Rocks Street 200.0 ft towards Elephant Odyssey.\n", 200.0, "Africa Rocks Street"),
            new RoutePoint("Entrance and Exit Gate",
                    "1. Proceed on Africa Rocks Street 200.0 ft towards Sharp Teeth Shortcut.\n"
                            + "2. Proceed on Sharp Teeth Shortcut 200.0 ft towards Reptile Road.\n"
                            + "3. Proceed on Reptile Road 100.0 ft towards Entrance Way.\n"
                            + "4. Proceed on Entrance Way 10.0 ft towards Entrance and Exit Gate.\n",
                    510.0, "Entrance Way")
    ));

    // MIGHT NEED TO CHANGE THIS
    private Intent planIntent = new Intent(context, DirectionsActivity.class).putExtra("RoutePoints in Order", route);

    @Rule
    public ActivityScenarioRule<DirectionsActivity> scenarioRule = new ActivityScenarioRule<>(planIntent);

    @BeforeClass
    public static void setTestData() {
        FilesToLoad.injectNewFiles(new String[]{"test_zoo_graph_ms1.json", "test_node_info_ms1.json", "test_edge_info_ms1.json"});
        ZooData.setZooData();
    }

    @Test
    public void testFirstInstanceOfDirections() {
        ActivityScenario<DirectionsActivity> scenario = scenarioRule.getScenario();

        scenario.onActivity(activity -> {
            Button finishBttn = activity.findViewById(R.id.finish_btn);

            assertEquals(0, activity.getRoutePointNum());
            assertEquals(View.INVISIBLE, finishBttn.getVisibility());
        });
    }

    @Test
    public void testNextButtonIsClicked() {
        ActivityScenario<DirectionsActivity> scenario = scenarioRule.getScenario();

        scenario.onActivity(activity -> {
            Button finishBttn = activity.findViewById(R.id.finish_btn);
            Button nextBttn = activity.findViewById(R.id.next_btn);

            nextBttn.performClick();

            assertEquals(1, activity.getRoutePointNum());
            assertEquals(View.INVISIBLE, finishBttn.getVisibility());
        });
    }

    @Test
    public void testEndOfRouteDirections() {
        ActivityScenario<DirectionsActivity> scenario = scenarioRule.getScenario();

        scenario.onActivity(activity -> {
            Button finishBttn = activity.findViewById(R.id.finish_btn);
            Button nextBttn = activity.findViewById(R.id.next_btn);

            nextBttn.performClick();
            nextBttn.performClick();
            nextBttn.performClick();

            assertEquals(3, activity.getRoutePointNum());
            assertEquals(View.VISIBLE, finishBttn.getVisibility());
        });
    }
}
