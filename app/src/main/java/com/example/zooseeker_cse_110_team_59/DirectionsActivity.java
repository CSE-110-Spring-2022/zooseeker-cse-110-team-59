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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directions);

        Bundle bundle = getIntent().getExtras();
        route = bundle.getParcelableArrayList("RoutePoints in Order");

        routePointNum = 0;

        directions = findViewById(R.id.directions_text);
        currExhibit = findViewById(R.id.current_exhibit);
        nextButton = findViewById(R.id.next_btn);

        updateDirections();
        routePointNum++;
    }

    public void onNextClicked(View view) {
        updateDirections();
        routePointNum++;
    }

    /*
    public void onPreviousClicked(View view) {
        updateDirections();
        routePointNum--;
    }
     */

    private void updateDirections() {
        if (routePointNum == route.size()) {
            Intent intent = new Intent(this, EndRouteActivity.class);
            startActivity(intent);
        }

        directions.setText(route.get(routePointNum).directions);

        String currExhibitText = "Directions to " + route.get(routePointNum).exhibitName;
        currExhibit.setText(currExhibitText);

        String nextBtnText;
        if (routePointNum + 1 >= route.size()) {
            nextBtnText = "Finish";
        }
        else {
            nextBtnText = "Next: " + route.get(routePointNum + 1).exhibitName + ", " + route.get(routePointNum + 1).distance + "ft";
        }
        nextButton.setText(nextBtnText);
    }
}