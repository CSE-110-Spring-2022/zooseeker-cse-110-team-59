package com.example.zooseeker_cse_110_team_59.MS1;

import static org.junit.Assert.assertEquals;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

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
public class UnitTestsUserStory6 {

    private Context context = ApplicationProvider.getApplicationContext();

    private ArrayList<String> route = new ArrayList<String>(Arrays.asList("entrance_exit_gate", "gorillas", "lions", "elephant_odyssey", "entrance_exit_gate"));

    private Intent planIntent = new Intent(context, DirectionsActivity.class).putExtra("IDs in Order", route).putExtra("Start Index", 1);

    @Rule
    public ActivityScenarioRule<DirectionsActivity> scenarioRule = new ActivityScenarioRule<>(planIntent);

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
    public void testFirstInstanceOfDirections() {
        ActivityScenario<DirectionsActivity> scenario = scenarioRule.getScenario();

        scenario.moveToState(Lifecycle.State.CREATED);

        scenario.onActivity(activity -> {
            Button finishBtn = activity.findViewById(R.id.finish_btn);

            assertEquals(1, activity.getPlanDirections().getDestinationIndex());
            assertEquals(View.INVISIBLE, finishBtn.getVisibility());
        });
    }

    @Test
    public void testNextButtonIsClicked() {
        ActivityScenario<DirectionsActivity> scenario = scenarioRule.getScenario();

        scenario.moveToState(Lifecycle.State.CREATED);

        scenario.onActivity(activity -> {
            Button finishBtn = activity.findViewById(R.id.finish_btn);
            Button nextBtn = activity.findViewById(R.id.next_btn);

            nextBtn.performClick();

            assertEquals(2, activity.getPlanDirections().getDestinationIndex());
            assertEquals(View.INVISIBLE, finishBtn.getVisibility());
        });
    }

    @Test
    public void testEndOfRouteDirections() {
        ActivityScenario<DirectionsActivity> scenario = scenarioRule.getScenario();

        scenario.moveToState(Lifecycle.State.CREATED);

        scenario.onActivity(activity -> {
            Button finishBtn = activity.findViewById(R.id.finish_btn);
            Button nextBtn = activity.findViewById(R.id.next_btn);

            nextBtn.performClick();
            nextBtn.performClick();
            nextBtn.performClick();

            assertEquals(4, activity.getPlanDirections().getDestinationIndex());
            assertEquals(View.VISIBLE, finishBtn.getVisibility());
        });
    }
}
