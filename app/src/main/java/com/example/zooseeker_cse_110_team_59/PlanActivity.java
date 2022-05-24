package com.example.zooseeker_cse_110_team_59;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.zooseeker_cse_110_team_59.Directions.DirectionsActivity;

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

    ArrayList<RoutePoint> route;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        Bundle bundle = getIntent().getExtras();
        route = bundle.getParcelableArrayList("RoutePoints in Order");
    }

    public void onDirectionsClicked(View view) {
        Intent intent = new Intent(this, DirectionsActivity.class);
        intent.putParcelableArrayListExtra("RoutePoints in Order", route);
        startActivity(intent);
    }
}