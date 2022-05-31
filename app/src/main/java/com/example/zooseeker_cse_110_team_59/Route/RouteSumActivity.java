package com.example.zooseeker_cse_110_team_59.Route;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.zooseeker_cse_110_team_59.Directions.DirectionsActivity;
import com.example.zooseeker_cse_110_team_59.MainActivity;
import com.example.zooseeker_cse_110_team_59.R;
import com.example.zooseeker_cse_110_team_59.Overflow.ActivityOverflow;

/**
 * Class:            PlanActivity
 * Description:      sets up the onCreate method and is the entry point for the planning of the route
 *
 * Public functions: onCreate          - creates the application for the PlanActivity
 */
public class RouteSumActivity extends ActivityOverflow {
    /**
     * The onCreate method creates the PlanActivity for the application
     * super is used to call the parent class constructor
     * setContentView is used to set the xml
     * @param Bundle is used to save and recover state information in activity
     *
     * @return None
     */

    ArrayList<RoutePoint> route;

    public RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_sum);

        RouteSummaryAdapter adapter = new RouteSummaryAdapter();

        recyclerView = findViewById(R.id.route_summary);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        Bundle bundle = getIntent().getExtras();
        route = bundle.getParcelableArrayList("RoutePoints in Order");
        adapter.setRoutePoints(route);
    }

    public void onDirectionsClicked(View view) {
        ArrayList<String> IDs = new ArrayList<String>();
        IDs.add("entrance_exit_gate");
        IDs.addAll(RouteGenerator.getIdsFromRoute(route));

        Intent intent = new Intent(this, DirectionsActivity.class);
        intent.putStringArrayListExtra("IDs in Order", IDs);
        intent.putExtra("Start Index", 1);
        intent.putExtra("Detail Level", "BRIEF");
        startActivity(intent);
    }

    //region ActivityOverflow Abstract Methods
    @Override
    protected void startMainActivity() {
        finish();
        startActivity(new Intent(this, MainActivity.class));
    }
    //endregion
}