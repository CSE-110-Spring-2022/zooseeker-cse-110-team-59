package com.example.zooseeker_cse_110_team_59;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class DirectionsActivity extends AppCompatActivity {

    ArrayList<RoutePoint> route;
    int routePointNum;
    TextView directions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directions);

        Bundle bundle = getIntent().getExtras();
        route = bundle.getParcelableArrayList("RoutePoints in Order");

        routePointNum = 0;

        directions = findViewById(R.id.directions_text);
    }

    public void onNextClicked(View view) {
        if (routePointNum == route.size()) {
            Intent intent = new Intent(this, EndRouteActivity.class);
            startActivity(intent);
        }

        directions.setText(route.get(routePointNum).directions);

        routePointNum++;
    }
}