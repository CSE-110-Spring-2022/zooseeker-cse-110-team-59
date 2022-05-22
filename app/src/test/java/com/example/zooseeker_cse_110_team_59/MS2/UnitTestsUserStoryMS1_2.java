package com.example.zooseeker_cse_110_team_59.MS2;

import static org.junit.Assert.assertEquals;

import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.example.zooseeker_cse_110_team_59.FilesToLoad;
import com.example.zooseeker_cse_110_team_59.List.ListActivity;
import com.example.zooseeker_cse_110_team_59.R;
import com.example.zooseeker_cse_110_team_59.ZooData;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;

public class UnitTestsUserStoryMS1_2 {
    @Rule
    public ActivityScenarioRule<ListActivity> scenarioRule = new ActivityScenarioRule<>(ListActivity.class);

    @BeforeClass
    public static void setTestData() {
        FilesToLoad.injectNewFiles(new String[]{"test_zoo_graph_ms1.json", "test_node_info_ms1.json", "test_edge_info_ms1.json"});
        ZooData.setZooData();
    }


}
