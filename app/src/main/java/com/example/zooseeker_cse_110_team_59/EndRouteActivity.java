package com.example.zooseeker_cse_110_team_59;

import android.content.Intent;
import android.os.Bundle;

import com.example.zooseeker_cse_110_team_59.Overflow.ActivityOverflow;

public class EndRouteActivity extends ActivityOverflow {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_route);
    }

    //region ActivityOverflow Abstract Methods
    @Override
    protected void startMainActivity() {
        finish();
        startActivity(new Intent(this, MainActivity.class));
    }
    //endregion
}