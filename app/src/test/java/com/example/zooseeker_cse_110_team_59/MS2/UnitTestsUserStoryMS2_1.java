package com.example.zooseeker_cse_110_team_59.MS2;

import static org.junit.Assert.assertEquals;

import android.content.Context;
import android.content.Intent;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.zooseeker_cse_110_team_59.Data.FilesToLoad;
import com.example.zooseeker_cse_110_team_59.Data.ZooData;
import com.example.zooseeker_cse_110_team_59.LoadingActivity;
import com.example.zooseeker_cse_110_team_59.Route.RouteGenerator;
import com.example.zooseeker_cse_110_team_59.Utilities.TestSettings;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;

@RunWith(AndroidJUnit4.class)
public class UnitTestsUserStoryMS2_1 {
    private Context context = ApplicationProvider.getApplicationContext();

    private Intent loadingIntent = new Intent(context, LoadingActivity.class)
            .putExtra("enteredExhibits", new ArrayList<>(Arrays.asList("gorilla")));

    @Rule
    public ActivityScenarioRule<LoadingActivity> scenarioRule = new ActivityScenarioRule<>(loadingIntent);

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
    public void testDetailedDirectionsOneEdge() {
        String testDir = "1. Proceed on Orangutan Trail 1100.0 ft towards Orangutans.\n";

        String result = RouteGenerator.getDirectionsBetween("siamang", "orangutan", "DETAILED");

        assertEquals(testDir, result);
    }

    @Test
    public void testDetailedDirectionsManyEdges() {
        String testDir = "1. Proceed on Orangutan Trail 1100.0 ft towards Orangutans.\n" +
                "2. Continue on Orangutan Trail 1500.0 ft towards Parker Aviary.\n" +
                "3. Proceed on Aviary Trail 1300.0 ft towards Owens Aviary.\n";


        String result = RouteGenerator.getDirectionsBetween("siamang", "dove", "DETAILED");

        assertEquals(testDir, result);
    }
}
