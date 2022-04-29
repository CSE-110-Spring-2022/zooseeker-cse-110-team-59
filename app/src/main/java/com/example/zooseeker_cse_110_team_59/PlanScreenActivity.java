package com.example.zooseeker_cse_110_team_59;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class PlanScreenActivity extends AppCompatActivity {

    private ArrayList<String> enteredExhibits;
    private RouteData route;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_screen);

        Bundle bundle = getIntent().getExtras();
        enteredExhibits = bundle.getStringArrayList("enteredExhibits");

        enteredExhibits.add(0, "entrance_exit_gate");
        enteredExhibits.add("entrance_exit_gate");

        Graph<String, IdentifiedWeightedEdge> g = ZooData.loadZooGraphJSON("sample_zoo_graph.json");
        Map<String, ZooData.VertexInfo> vInfo = ZooData.loadVertexInfoJSON("sample_node_info.json");
        Map<String, ZooData.EdgeInfo> eInfo = ZooData.loadEdgeInfoJSON("sample_edge_info.json");

        route = new RouteData();

        for (int i = 0; i < enteredExhibits.size() - 1; i++) {
            GraphPath<String, IdentifiedWeightedEdge> path = DijkstraShortestPath.findPathBetween(g, enteredExhibits.get(i), enteredExhibits.get(i + 1));

            for (IdentifiedWeightedEdge e : path.getEdgeList()) {
                route.addRoutePoint(vInfo.get(g.getEdgeSource(e).toString()).name, (float) g.getEdgeWeight(e),
                        eInfo.get(e.getId()).street, vInfo.get(g.getEdgeTarget(e).toString()).name);
            }
        }

        for (int i = 0; i < route.getRouteSize(); i++) {
            System.out.println(route.printRoutePoint(i));
        }
    }
}