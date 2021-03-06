package com.example.zooseeker_cse_110_team_59.Directions;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zooseeker_cse_110_team_59.Overflow.ActivityOverflow;
import com.example.zooseeker_cse_110_team_59.EndRouteActivity;
import com.example.zooseeker_cse_110_team_59.MainActivity;
import com.example.zooseeker_cse_110_team_59.R;
import com.example.zooseeker_cse_110_team_59.Utilities.TestSettings;

import java.util.ArrayList;
import java.util.function.Consumer;

public class DirectionsActivity extends ActivityOverflow implements DirectionsObserver {
    private PlanDirections planDirections;
    private TextView directionsTV;
    private TextView currExhibitTV;
    private Button nextButton;
    private Button finishButton;
    private Button previousButton;
    private Button skipButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directions);

        Bundle bundle = getIntent().getExtras();
        ArrayList<String> startIDs = bundle.getStringArrayList("IDs in Order");
        int startIndex = bundle.getInt("Start Index");
        String startDetailLevel = bundle.getString("Detail Level");

        directionsTV = findViewById(R.id.directions_text);
        currExhibitTV = findViewById(R.id.place_name);
        nextButton = findViewById(R.id.next_btn);
        previousButton = findViewById(R.id.previous_button);
        finishButton = findViewById(R.id.finish_btn);

        planDirections = new PlanDirections(this, startIDs, startIndex, startDetailLevel);
        planDirections.registerDO(this);

        if (!TestSettings.isTestPositioning()) setupLocationListener(this::updateLastKnownCoords);

        planDirections.updateData();

        saveSharedPreferences();
    }

    //region Location Methods
    @SuppressLint("MissingPermission")
    private void setupLocationListener(Consumer<Pair<Double, Double>> handleNewCoords) {
        // Connect location listener to the model.
        var provider = LocationManager.GPS_PROVIDER;
        var locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        var locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                var coords = Pair.create(
                        location.getLatitude(),
                        location.getLongitude()
                );
                handleNewCoords.accept(coords);
            }
        };
        locationManager.requestLocationUpdates(provider, 0, 0f, locationListener);
    }

    public void updateLastKnownCoords(Pair<Double, Double> coords) {
        planDirections.setLastKnownCoords(coords);
    }

    @VisibleForTesting
    public void mockLocationUpdate(Pair<Double, Double> coords) {
        updateLastKnownCoords(coords);
    }
    //endregion

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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.overflow, menu);

        menu.findItem(R.id.mock_location).setVisible(true);
        menu.findItem(R.id.directions_detail).setVisible(true);
        menu.findItem(R.id.skip_exhibit).setVisible(true);
        return true;
    }

    @Override
    protected void startMainActivity() {
        finish();
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    @SuppressLint("SetTextI18n")
    public void onMockOptionClicked() {
        // TODO: could define this layout in an XML and inflate it, instead of defining in code...
        // But you know we won't! Thanks for the code!

        //region Create Layout
        var inputType = EditorInfo.TYPE_CLASS_NUMBER
                | EditorInfo.TYPE_NUMBER_FLAG_SIGNED
                | EditorInfo.TYPE_NUMBER_FLAG_DECIMAL;

        final EditText latInput = new EditText(this);
        latInput.setInputType(inputType);
        latInput.setLongClickable(true);
        latInput.setHint("Latitude");
        latInput.setText("32.73459618734685");

        final EditText lngInput = new EditText(this);
        lngInput.setInputType(inputType);
        lngInput.setLongClickable(true);
        lngInput.setHint("Longitude");
        lngInput.setText("-117.14936");

        final LinearLayout layout = new LinearLayout(this);
        layout.setDividerPadding(8);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(latInput);
        layout.addView(lngInput);
        //endregion

        //region Show Mock Dialog
        var builder = new AlertDialog.Builder(this)
                .setTitle("Inject a Mock Location")
                .setView(layout)
                .setPositiveButton("Submit", (dialog, which) -> {
                    var lat = Double.parseDouble(latInput.getText().toString());
                    var lng = Double.parseDouble(lngInput.getText().toString());
                    updateLastKnownCoords(Pair.create(lat, lng));
                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                    dialog.cancel();
                });
        builder.show();
        //endregion
    }

    @Override
    protected void onDirectionsDetailClicked() {
        var builder = new AlertDialog.Builder(this)
                .setTitle("Directions Detail")
                .setMessage("Please select the level of detail you would like directions to be displayed in.")
                .setPositiveButton("Brief", (dialog, which) -> {
                    planDirections.detailLevelBriefClicked();
                })
                .setNegativeButton("Detailed", (dialog, which) -> {
                    planDirections.detailLevelDetailedClicked();
                });
        builder.show();
    }

    @Override
    protected void onSkipClicked() {
        planDirections.skipClicked();
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