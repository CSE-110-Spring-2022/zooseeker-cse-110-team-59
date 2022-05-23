package com.example.zooseeker_cse_110_team_59.MS1;

import static org.junit.Assert.assertEquals;

import android.content.Context;
import android.content.Intent;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.zooseeker_cse_110_team_59.FilesToLoad;
import com.example.zooseeker_cse_110_team_59.LoadingActivity;
import com.example.zooseeker_cse_110_team_59.RouteGenerator;
import com.example.zooseeker_cse_110_team_59.RoutePoint;
import com.example.zooseeker_cse_110_team_59.ZooData;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
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
        RoutePoint test = new RoutePoint("Gorillas",
                "1. Proceed on Africa Rocks Street 200.0 ft towards Gorillas.\n",
                "Africa Rocks Street", 200.0);

        GraphPath<String, ZooData.Graph.Edge> path = RouteGenerator.getPathBetween("entrance_plaza", "gorillas");

        RoutePoint result = RouteGenerator.createRoutePointFromPath(path);

        assertEquals(test.exhibitName, result.exhibitName);
        assertEquals(test.directions, result.directions);
        assertEquals(test.imDistance, result.imDistance, 0.0);
        assertEquals(test.streetName, result.streetName);
        assertEquals(200.0, result.cumDistance, 0.0);
    }

    @Test
    public void testsRoutePointManyEdge() {
        RoutePoint test = new RoutePoint("Gorillas",
                "1. Proceed on Entrance Way 10.0 ft towards Africa Rocks Street.\n"
                        + "2. Proceed on Africa Rocks Street 200.0 ft towards Gorillas.\n",
                "Africa Rocks Street", 210.0);

        GraphPath<String, ZooData.Graph.Edge> path = RouteGenerator.getPathBetween("entrance_exit_gate", "gorillas");

        RoutePoint result = RouteGenerator.createRoutePointFromPath(path);

        assertEquals(test.exhibitName, result.exhibitName);
        assertEquals(test.directions, result.directions);
        assertEquals(test.imDistance, result.imDistance, 0.0);
        assertEquals(test.streetName, result.streetName);
        assertEquals(210.0, result.cumDistance, 0.0);
    }

    @Test
    public void testsNonCombinePath() {
        ArrayList<RoutePoint> test = new ArrayList<>();
        test.add(new RoutePoint("Gorillas",
                "1. Proceed on Entrance Way 10.0 ft towards Africa Rocks Street.\n"
                        + "2. Proceed on Africa Rocks Street 200.0 ft towards Gorillas.\n",
                "Africa Rocks Street", 210.0));
        test.add(new RoutePoint("Entrance and Exit Gate",
                "1. Proceed on Africa Rocks Street 200.0 ft towards Entrance Way.\n"
                        + "2. Proceed on Entrance Way 10.0 ft towards Entrance and Exit Gate.\n",
                "Entrance Way", 210.0));

        ArrayList<RoutePoint> result = RouteGenerator.generateRoute(new ArrayList<String>(Arrays.asList("gorillas")));

        assertEquals(test.get(0).exhibitName, result.get(0).exhibitName);
        assertEquals(test.get(0).directions, result.get(0).directions);
        assertEquals(test.get(0).imDistance, result.get(0).imDistance, 0.0);
        assertEquals(test.get(0).streetName, result.get(0).streetName);
        assertEquals(210.0, result.get(0).cumDistance, 0.0);


        assertEquals(test.get(1).exhibitName, result.get(1).exhibitName);
        assertEquals(test.get(1).directions, result.get(1).directions);
        assertEquals(test.get(1).imDistance, result.get(1).imDistance, 0.0);
        assertEquals(test.get(1).streetName, result.get(1).streetName);
        assertEquals(420.0, result.get(1).cumDistance, 0.0);
    }

    @Test
    public void testsCombinePath() {
        ArrayList<RoutePoint> test = new ArrayList<>();
        test.add(new RoutePoint("Gorillas",
                "1. Proceed on Entrance Way 10.0 ft towards Africa Rocks Street.\n"
                        + "2. Proceed on Africa Rocks Street 200.0 ft towards Gorillas.\n",
                "Africa Rocks Street", 210.0));
        test.add(new RoutePoint("Elephant Odyssey",
                "1. Proceed on Africa Rocks Street 400.0 ft towards Elephant Odyssey.\n",
                "Africa Rocks Street", 400.0));
        test.add(new RoutePoint("Entrance and Exit Gate",
                "1. Proceed on Africa Rocks Street 200.0 ft towards Sharp Teeth Shortcut.\n"
                        + "2. Proceed on Sharp Teeth Shortcut 200.0 ft towards Reptile Road.\n"
                        + "3. Proceed on Reptile Road 100.0 ft towards Entrance Way.\n"
                        + "4. Proceed on Entrance Way 10.0 ft towards Entrance and Exit Gate.\n",
                "Entrance Way", 510.0));

        ArrayList<RoutePoint> result = RouteGenerator.generateRoute(new ArrayList<String>(Arrays.asList("gorillas", "elephant_odyssey")));

        assertEquals(test.get(0).exhibitName, result.get(0).exhibitName);
        assertEquals(test.get(0).directions, result.get(0).directions);
        assertEquals(test.get(0).imDistance, result.get(0).imDistance, 0.0);
        assertEquals(test.get(0).streetName, result.get(0).streetName);
        assertEquals(210.0, result.get(0).cumDistance, 0.0);

        assertEquals(test.get(1).exhibitName, result.get(1).exhibitName);
        assertEquals(test.get(1).directions, result.get(1).directions);
        assertEquals(test.get(1).imDistance, result.get(1).imDistance, 0.0);
        assertEquals(test.get(1).streetName, result.get(1).streetName);
        assertEquals(610.0, result.get(1).cumDistance, 0.0);

        assertEquals(test.get(2).exhibitName, result.get(2).exhibitName);
        assertEquals(test.get(2).directions, result.get(2).directions);
        assertEquals(test.get(2).imDistance, result.get(2).imDistance, 0.0);
        assertEquals(test.get(2).streetName, result.get(2).streetName);
        assertEquals(1120.0, result.get(2).cumDistance, 0.0);
    }
}
