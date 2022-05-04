package com.example.zooseeker_cse_110_team_59;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class PlanActivity extends AppCompatActivity {

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