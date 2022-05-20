package com.example.zooseeker_cse_110_team_59.MS1;

import static org.junit.Assert.assertEquals;

import android.content.Context;
import android.content.Intent;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.zooseeker_cse_110_team_59.FilesToLoad;
import com.example.zooseeker_cse_110_team_59.LoadingActivity;
import com.example.zooseeker_cse_110_team_59.RoutePoint;
import com.example.zooseeker_cse_110_team_59.ZooData;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;

@RunWith(AndroidJUnit4.class)
public class UnitTestsUserStory5 {

    private Context context = ApplicationProvider.getApplicationContext();

    private Intent loadingIntent = new Intent(context, LoadingActivity.class)
            .putExtra("enteredExhibits", new ArrayList<>(Arrays.asList("arctic_foxes")));

    @Rule
    public ActivityScenarioRule<LoadingActivity> scenarioRule = new ActivityScenarioRule<>(loadingIntent);

    @BeforeClass
    public static void setTestData() {
        FilesToLoad.injectNewFiles(new String[]{"test_zoo_graph_ms1.json", "test_node_info_ms1.json", "test_edge_info_ms1.json"});
        ZooData.setZooData();
    }

    @Test
    public void testsRoutePointOneEdge() {
        ActivityScenario<LoadingActivity> scenario = scenarioRule.getScenario();

        scenario.onActivity(activity -> {
            RoutePoint test = new RoutePoint("Gorillas",
                    "1. Proceed on Africa Rocks Street 200.0 ft towards Gorillas.\n",
                    200.0);

            GraphPath<String, ZooData.Graph.Edge> path = DijkstraShortestPath.findPathBetween(ZooData.loadZooGraphJSON(FilesToLoad.getGraphFile()),
                                                                                            "entrance_plaza",
                                                                                                "gorillas");

            RoutePoint result = activity.createRoutePointFromPath(path);

            assertEquals(test.exhibitName, result.exhibitName);
            assertEquals(test.directions, result.directions);
            assertEquals(test.distance, result.distance, 0.0);
        });
    }

    @Test
    public void testsRoutePointManyEdge() {
        ActivityScenario<LoadingActivity> scenario = scenarioRule.getScenario();

        scenario.onActivity(activity -> {
            RoutePoint test = new RoutePoint("Gorillas",
                    "1. Proceed on Entrance Way 10.0 ft towards Africa Rocks Street.\n"
                            + "2. Proceed on Africa Rocks Street 200.0 ft towards Gorillas.\n",
                    210.0);

            GraphPath<String, ZooData.Graph.Edge> path = DijkstraShortestPath.findPathBetween(ZooData.loadZooGraphJSON(FilesToLoad.getGraphFile()),
                    "entrance_exit_gate",
                    "gorillas");

            RoutePoint result = activity.createRoutePointFromPath(path);

            assertEquals(test.exhibitName, result.exhibitName);
            assertEquals(test.directions, result.directions);
            assertEquals(test.distance, result.distance, 0.0);
        });
    }

    @Test
    public void testsNonCombinePath() {
        ActivityScenario<LoadingActivity> scenario = scenarioRule.getScenario();

        scenario.onActivity(activity -> {
            ArrayList<RoutePoint> test = new ArrayList<>();
            test.add(new RoutePoint("Gorillas",
                    "1. Proceed on Entrance Way 10.0 ft towards Africa Rocks Street.\n"
                            + "2. Proceed on Africa Rocks Street 200.0 ft towards Gorillas.\n",
                    210.0));
            test.add(new RoutePoint("Entrance and Exit Gate",
                    "1. Proceed on Africa Rocks Street 200.0 ft towards Entrance Way.\n"
                            + "2. Proceed on Entrance Way 10.0 ft towards Entrance and Exit Gate.\n",
                    210.0));

            ArrayList<RoutePoint> result = activity.generateRoute(new ArrayList<String>(Arrays.asList("gorillas")));

            assertEquals(test.get(0).exhibitName, result.get(0).exhibitName);
            assertEquals(test.get(0).directions, result.get(0).directions);
            assertEquals(test.get(0).distance, result.get(0).distance, 0.0);

            assertEquals(test.get(1).exhibitName, result.get(1).exhibitName);
            assertEquals(test.get(1).directions, result.get(1).directions);
            assertEquals(test.get(1).distance, result.get(1).distance, 0.0);
        });
    }

    @Test
    public void testsCombinePath() {
        ActivityScenario<LoadingActivity> scenario = scenarioRule.getScenario();

        scenario.onActivity(activity -> {
            ArrayList<RoutePoint> test = new ArrayList<>();
            test.add(new RoutePoint("Gorillas",
                    "1. Proceed on Entrance Way 10.0 ft towards Africa Rocks Street.\n"
                            + "2. Proceed on Africa Rocks Street 200.0 ft towards Gorillas.\n",
                    210.0));
            test.add(new RoutePoint("Elephant Odyssey",
                    "1. Proceed on Africa Rocks Street 400.0 ft towards Elephant Odyssey.\n",
                    400.0));
            test.add(new RoutePoint("Entrance and Exit Gate",
                    "1. Proceed on Africa Rocks Street 200.0 ft towards Sharp Teeth Shortcut.\n"
                            + "2. Proceed on Sharp Teeth Shortcut 200.0 ft towards Reptile Road.\n"
                            + "3. Proceed on Reptile Road 100.0 ft towards Entrance Way.\n"
                            + "4. Proceed on Entrance Way 10.0 ft towards Entrance and Exit Gate.\n",
                    510.0));

            ArrayList<RoutePoint> result = activity.generateRoute(new ArrayList<String>(Arrays.asList("gorillas", "elephant_odyssey")));

            assertEquals(test.get(0).exhibitName, result.get(0).exhibitName);
            assertEquals(test.get(0).directions, result.get(0).directions);
            assertEquals(test.get(0).distance, result.get(0).distance, 0.0);

            assertEquals(test.get(1).exhibitName, result.get(1).exhibitName);
            assertEquals(test.get(1).directions, result.get(1).directions);
            assertEquals(test.get(1).distance, result.get(1).distance, 0.0);

            assertEquals(test.get(2).exhibitName, result.get(2).exhibitName);
            assertEquals(test.get(2).directions, result.get(2).directions);
            assertEquals(test.get(2).distance, result.get(2).distance, 0.0);
        });
    }
}