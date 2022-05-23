package com.example.zooseeker_cse_110_team_59;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class DirectionsActivity extends AppCompatActivity {

    private ArrayList<RoutePoint> route;
    private int routePointNum;
    private TextView directions;
    private TextView currExhibit;
    private Button nextButton;
    private Button finishButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directions);

        Bundle bundle = getIntent().getExtras();
        route = bundle.getParcelableArrayList("RoutePoints in Order");

        routePointNum = 0;

        directions = findViewById(R.id.directions_text);
        currExhibit = findViewById(R.id.place_name);
        nextButton = findViewById(R.id.next_btn);
        finishButton = findViewById(R.id.finish_btn);

        updateDirections();
    }

    public void onNextClicked(View view) {
        routePointNum++;
        updateDirections();
    }

    public void onFinishClicked(View view) {
        Intent intent = new Intent(this, EndRouteActivity.class);
        finish();
        startActivity(intent);
    }

    /*
    public void onPreviousClicked(View view) {
        routePointNum--;
        updateDirections();
    }
     */

    public void updateDirections() {
        directions.setText(route.get(routePointNum).directions);

        String currExhibitText = "Directions to " + route.get(routePointNum).exhibitName;
        currExhibit.setText(currExhibitText);

        if (routePointNum == route.size() - 1) {
            finishButton.setVisibility(View.VISIBLE);
            nextButton.setVisibility(View.INVISIBLE);
        } else {
            finishButton.setVisibility(View.INVISIBLE);
            nextButton.setVisibility(View.VISIBLE);
            String nextBtnText = "Next: " + route.get(routePointNum + 1).exhibitName + ", " + route.get(routePointNum + 1).imDistance + "ft";
            nextButton.setText(nextBtnText);
        }
    }

    // This is for testing purposes
    public int getRoutePointNum() {
        return routePointNum;
    }

}