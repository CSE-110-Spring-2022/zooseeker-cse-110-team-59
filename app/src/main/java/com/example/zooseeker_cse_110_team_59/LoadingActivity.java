package com.example.zooseeker_cse_110_team_59;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Class:           LoadingActivity
 * Description:     The activity started when the clicks on the plan button to generate the route
 *
 * Public functions:
 *
 * onCreate   - responsible for creating a new intent and create a new execute object to
 *              run and manage a new thread for this activity
 * generateRoute - Performs Djikstra algorithm to compute the shortest path given a list of exhibits
 *                 and their edge weights. Returns a list of type Routepoint that goes back to the
 *                 entrance and exit gate
 * createRoutePointFromPath - returns a Routepoint object that shows which path and direction to proceed
 *                              and is called in the <code>generateRoute</code> method
 * setGraphData  - loads the graph data from the json files into an empty graph, edge, and vertex respectively
 */
public class LoadingActivity extends ActivityOverflow {

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        Bundle bundle = getIntent().getExtras();
        ArrayList<String> enteredExhibits = bundle.getStringArrayList("enteredExhibits");

        Intent planIntent = new Intent(this, PlanActivity.class);

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            planIntent.putParcelableArrayListExtra("RoutePoints in Order", RouteGenerator.generateRoute(enteredExhibits));
            finish();
            startActivity(planIntent);
        });
    }

    //region ActivityOverflow Abstract Methods
    @Override
    protected void startMainActivity() {
        finish();
        startActivity(new Intent(this, MainActivity.class));
    }
    //endregion
}