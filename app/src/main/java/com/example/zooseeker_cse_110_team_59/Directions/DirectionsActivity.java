package com.example.zooseeker_cse_110_team_59.Directions;

import androidx.annotation.VisibleForTesting;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.zooseeker_cse_110_team_59.Retention.ActivityOverflow;
import com.example.zooseeker_cse_110_team_59.EndRouteActivity;
import com.example.zooseeker_cse_110_team_59.MainActivity;
import com.example.zooseeker_cse_110_team_59.R;

import java.util.ArrayList;

public class DirectionsActivity extends ActivityOverflow implements DirectionsObserver {

    private PlanDirections planDirections;
    private TextView directionsTV;
    private TextView currExhibitTV;
    private Button nextButton;
    private Button finishButton;
    private Button previousButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directions);

        Bundle bundle = getIntent().getExtras();
        ArrayList<String> startIDs = bundle.getStringArrayList("IDs in Order");
        int startIndex = bundle.getInt("Start Index");

        directionsTV = findViewById(R.id.directions_text);
        currExhibitTV = findViewById(R.id.place_name);
        nextButton = findViewById(R.id.next_btn);
        previousButton = findViewById(R.id.previous_button);
        finishButton = findViewById(R.id.finish_btn);

        planDirections = new PlanDirections(this, startIDs);
        planDirections.registerDO(this);

        if (startIndex == 0) {
            planDirections.nextClicked();
            planDirections.previousClicked();
        } else {
            for (int i = 0; i < startIndex; i++) planDirections.nextClicked();
        }

        saveSharedPreferences();
    }

    //region Button Listeners
    public void onNextClicked(View view) {
        planDirections.nextClicked();
    }

    public void onPreviousClicked(View view) {
        planDirections.previousClicked();
    }

    public void onFinishClicked(View view) {
        Intent intent = new Intent(this, EndRouteActivity.class);
        finish();
        startActivity(intent);
    }
    //endregion

    //region DirectionsObserver Interface Methods
    @Override
    public void update(ArrayList<String> prevStrings, ArrayList<String> currStrings, ArrayList<String> nextStrings) {
        updatePrev(prevStrings);
        updateCurr(currStrings);
        updateNext(nextStrings);
    }
    //endregion

    //region View Updaters
    private void updatePrev(ArrayList<String> prevStrings) {
        if (prevStrings.get(0).equals("hide")) {
            previousButton.setVisibility(View.INVISIBLE);
        }
        else {
            previousButton.setVisibility(View.VISIBLE);
            previousButton.setText(prevStrings.get(1));
        }
    }

    private void updateCurr(ArrayList<String> currStrings) {
        currExhibitTV.setText(currStrings.get(0));
        directionsTV.setText(currStrings.get(1));
    }

    private void updateNext(ArrayList<String> nextStrings) {
        if (nextStrings.get(0).equals("hide")) {
            finishButton.setVisibility(View.VISIBLE);
            nextButton.setVisibility(View.INVISIBLE);
        } else {
            finishButton.setVisibility(View.INVISIBLE);
            nextButton.setVisibility(View.VISIBLE);
            nextButton.setText(nextStrings.get(1));
        }
    }
    //endregion

    //region ActivityOverflow Abstract Methods
    @Override
    protected void startMainActivity() {
        finish();
        startActivity(new Intent(this, MainActivity.class));
    }
    //endregion

    //region SharedPreferencesSaver Interface Methods
    private void saveSharedPreferences() {
        SharedPreferences preferences = getSharedPreferences("shared_preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        if (preferences.contains("storedActivity")) editor.remove("storedActivity");
        editor.commit();
        editor.putString("storedActivity", "DirectionsActivity");
        editor.commit();
    }
    //endregion

    //region Getters for Tests
    @VisibleForTesting
    public PlanDirections getPlanDirections() {
        return planDirections;
    }

    @VisibleForTesting
    public TextView getDirectionsTV() {
        return directionsTV;
    }
    //endregion
}