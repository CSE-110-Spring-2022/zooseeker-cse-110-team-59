package com.example.zooseeker_cse_110_team_59.MS1;

import static org.junit.Assert.assertEquals;

import android.content.Context;
import android.content.Intent;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.zooseeker_cse_110_team_59.Data.FilesToLoad;
import com.example.zooseeker_cse_110_team_59.LoadingActivity;
import com.example.zooseeker_cse_110_team_59.Route.RouteGenerator;
import com.example.zooseeker_cse_110_team_59.Route.RoutePoint;
import com.example.zooseeker_cse_110_team_59.Data.ZooData;
import com.example.zooseeker_cse_110_team_59.Utilities.TestSettings;

import org.jgrapht.GraphPath;
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

    //region INCLUDE THIS IN EVERY UNIT TEST. Change file names to desired test files.
    @BeforeClass
    public static void setTestData() {
        FilesToLoad.injectNewFiles(new String[]{"test_zoo_graph_ms1.json", "test_node_info_ms1.json", "test_edge_info_ms1.json"});
        TestSettings.setTestClearing(true);
        TestSettings.setTestPositioning(true);
        ZooData.setZooData();
    }
    //endregion

    @Test
    public void testBriefDirectionsOneEdge() {
        String testDir = "1. Proceed on Africa Rocks Street 200.0 ft towards Gorillas.\n";

        String result = RouteGenerator.getDirectionsBetween("entrance_plaza", "gorillas", "BRIEF");

        assertEquals(testDir, result);
    }

    @Test
    public void testBriefDirectionsManyEdges() {
        String testDir = "1. Proceed on Entrance Way 10.0 ft towards Africa Rocks Street.\n"
                + "2. Proceed on Africa Rocks Street 200.0 ft towards Gorillas.\n";

        String result = RouteGenerator.getDirectionsBetween("entrance_exit_gate", "gorillas", "BRIEF");

        assertEquals(testDir, result);
    }

    @Test
    public void testBriefDirectionsCombine() {
        String testDir = "1. Proceed on Africa Rocks Street 400.0 ft towards Elephant Odyssey.\n";

        String result = RouteGenerator.getDirectionsBetween("gorillas", "elephant_odyssey", "BRIEF");

        assertEquals(testDir, result);
    }

    @Test
    public void testsRoutePointOneEdge() {
        RoutePoint test = new RoutePoint("Gorillas", "Africa Rocks Street", "gorillas", 200.0);

        GraphPath<String, ZooData.Graph.Edge> path = RouteGenerator.getPathBetween("entrance_plaza", "gorillas");

        RoutePoint result = RouteGenerator.createRoutePointFromPath(path);

        assertEquals(test.exhibitName, result.exhibitName);
        assertEquals(test.streetName, result.streetName);
        assertEquals(test.ID, result.ID);
        assertEquals(200.0, result.cumDistance, 0.0);
    }

    @Test
    public void testsRoutePointManyEdge() {
        RoutePoint test = new RoutePoint("Gorillas", "Africa Rocks Street", "gorillas", 210.0);

        GraphPath<String, ZooData.Graph.Edge> path = RouteGenerator.getPathBetween("entrance_exit_gate", "gorillas");

        RoutePoint result = RouteGenerator.createRoutePointFromPath(path);

        assertEquals(test.exhibitName, result.exhibitName);
        assertEquals(test.streetName, result.streetName);
        assertEquals(test.ID, result.ID);
        assertEquals(210.0, result.cumDistance, 0.0);
    }

    @Test
    public void testsNonCombinePath() {
        ArrayList<RoutePoint> test = new ArrayList<>();
        test.add(new RoutePoint("Gorillas", "Africa Rocks Street", "gorillas", 210.0));
        test.add(new RoutePoint("Entrance and Exit Gate", "Entrance Way", "entrance_exit_gate", 420.0));

        ArrayList<RoutePoint> result = RouteGenerator.generateRoute(new ArrayList<String>(Arrays.asList("gorillas")));

        assertEquals(test.get(0).exhibitName, result.get(0).exhibitName);
        assertEquals(test.get(0).streetName, result.get(0).streetName);
        assertEquals(test.get(0).ID, result.get(0).ID);
        assertEquals(210.0, result.get(0).cumDistance, 0.0);


        assertEquals(test.get(1).exhibitName, result.get(1).exhibitName);
        assertEquals(test.get(1).streetName, result.get(1).streetName);
        assertEquals(test.get(1).ID, result.get(1).ID);
        assertEquals(420.0, result.get(1).cumDistance, 0.0);
    }

    @Test
    public void testsCombinePath() {
        ArrayList<RoutePoint> test = new ArrayList<>();
        test.add(new RoutePoint("Gorillas", "Africa Rocks Street", "gorillas", 210.0));
        test.add(new RoutePoint("Elephant Odyssey", "Africa Rocks Street", "elephant_odyssey", 610.0));
        test.add(new RoutePoint("Entrance and Exit Gate", "Entrance Way", "entrance_exit_gate", 1120.0));

        ArrayList<RoutePoint> result = RouteGenerator.generateRoute(new ArrayList<String>(Arrays.asList("gorillas", "elephant_odyssey")));

        assertEquals(test.get(0).exhibitName, result.get(0).exhibitName);
        assertEquals(test.get(0).streetName, result.get(0).streetName);
        assertEquals(test.get(0).ID, result.get(0).ID);
        assertEquals(210.0, result.get(0).cumDistance, 0.0);

        assertEquals(test.get(1).exhibitName, result.get(1).exhibitName);
        assertEquals(test.get(1).streetName, result.get(1).streetName);
        assertEquals(test.get(1).ID, result.get(1).ID);
        assertEquals(610.0, result.get(1).cumDistance, 0.0);

        assertEquals(test.get(2).exhibitName, result.get(2).exhibitName);
        assertEquals(test.get(2).streetName, result.get(2).streetName);
        assertEquals(test.get(2).ID, result.get(2).ID);
        assertEquals(1120.0, result.get(2).cumDistance, 0.0);
    }
}
