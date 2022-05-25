package com.example.zooseeker_cse_110_team_59.Directions;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.zooseeker_cse_110_team_59.EndRouteActivity;
import com.example.zooseeker_cse_110_team_59.R;
import com.example.zooseeker_cse_110_team_59.RoutePoint;

import java.util.ArrayList;

public class DirectionsActivity extends AppCompatActivity implements DirectionsObserver {


    private PlanDirections myDirections;
    private ArrayList<RoutePoint> route;
    private ArrayList<String> IDs;
    private TextView directions;
    private TextView currExhibit;
    private Button nextButton;
    private Button finishButton;
    private Button previousButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directions);

        Bundle bundle = getIntent().getExtras();
        route = bundle.getParcelableArrayList("RoutePoints in Order");
        IDs = bundle.getStringArrayList("IDs in Order");

        directions = findViewById(R.id.directions_text);
        currExhibit = findViewById(R.id.place_name);
        nextButton = findViewById(R.id.next_btn);
        previousButton = findViewById(R.id.previous_button);
        finishButton = findViewById(R.id.finish_btn);

        myDirections = new PlanDirections(route, IDs);
        myDirections.registerDO(this);
        myDirections.nextClicked();
    }

    public void onNextClicked(View view) {
        myDirections.nextClicked();
    }

    public void onPreviousClicked(View view) {
        myDirections.previousClicked();
    }

    public void onFinishClicked(View view) {
        Intent intent = new Intent(this, EndRouteActivity.class);
        finish();
        startActivity(intent);
    }

    @Override
    public void update(ArrayList<String> currStrings, ArrayList<String> nextStrings) {
        if (nextStrings.get(0).equals("finished")) {
            finishButton.setVisibility(View.VISIBLE);
            nextButton.setVisibility(View.INVISIBLE);
        } else {
            finishButton.setVisibility(View.INVISIBLE);
            nextButton.setVisibility(View.VISIBLE);
            nextButton.setText(nextStrings.get(1));
        }
        currExhibit.setText(currStrings.get(0));
        directions.setText(currStrings.get(1));
    }

    public PlanDirections getPlanDirections() {
        return myDirections;
    }
}