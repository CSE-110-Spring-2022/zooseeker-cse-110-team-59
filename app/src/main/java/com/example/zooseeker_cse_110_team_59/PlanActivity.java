package com.example.zooseeker_cse_110_team_59;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;

import android.os.Bundle;

import android.os.Bundle;
/**
 * Class:            PlanActivity
 * Description:      sets up the onCreate method and is the entry point for the planning of the route
 *
 * Public functions: onCreate          - creates the application for the PlanActivity
 */
public class PlanActivity extends AppCompatActivity {
    /**
     * The onCreate method creates the PlanActivity for the application
     * super is used to call the parent class constructor
     * setContentView is used to set the xml
     * @param Bundle is used to save and recover state information in activity
     *
     * @return None
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);
    }
}