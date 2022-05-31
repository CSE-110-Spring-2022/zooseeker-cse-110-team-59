package com.example.zooseeker_cse_110_team_59.Overflow;

import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.example.zooseeker_cse_110_team_59.R;

public abstract class ActivityOverflow extends AppCompatActivity {
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.overflow, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.clear_option:
                clearSharedPreferences();
                startMainActivity();
                return true;
            case R.id.mock_location:
                onMockOptionClicked();
                return true;
            case R.id.directions_detail:
                onDirectionsDetailClicked();
                return true;
            case R.id.skip_exhibit:
                onSkipClicked();
                return true;
            default:
                return false;
        }
    }


    protected void clearSharedPreferences() {
        SharedPreferences preferences = getSharedPreferences("shared_preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.clear();
        editor.apply();
    }

    protected void onMockOptionClicked() {}

    protected void onDirectionsDetailClicked() {}

    protected void onSkipClicked() {}

    protected abstract void startMainActivity();
}
