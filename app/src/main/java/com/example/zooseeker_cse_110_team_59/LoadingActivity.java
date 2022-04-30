package com.example.zooseeker_cse_110_team_59;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoadingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        Bundle bundle = getIntent().getExtras();
        ArrayList<String> enteredExhibits = bundle.getStringArrayList("enteredExhibits");

        Intent planIntent = new Intent(this, PlanActivity.class);

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            planIntent.putParcelableArrayListExtra("RoutePoints in Order", generateRoute(enteredExhibits));
            finish();
            startActivity(planIntent);
        });
    }

    public ArrayList<RoutePoint> generateRoute (ArrayList<String> enteredExhibits) {
        ArrayList<String> unvisited = enteredExhibits;

        Graph<String, IdentifiedWeightedEdge> g = ZooData.loadZooGraphJSON(FilesToLoad.getGraphFile());
        Map<String, ZooData.VertexInfo> vInfo = ZooData.loadVertexInfoJSON(FilesToLoad.getVertexFile());
        Map<String, ZooData.EdgeInfo> eInfo = ZooData.loadEdgeInfoJSON(FilesToLoad.getEdgeFile());

        ArrayList<RoutePoint> route = new ArrayList<>();

        String currentNode = "entrance_exit_gate";

        while (unvisited.size() != 0) {
            double shortestPathWeight = Float.MAX_VALUE;
            GraphPath<String, IdentifiedWeightedEdge> shortestPath = null;
            String closestExhibit = null;

            for (String vertex : unvisited) {
                GraphPath<String, IdentifiedWeightedEdge> path = DijkstraShortestPath.findPathBetween(g, currentNode, vertex);
                if (path.getWeight() < shortestPathWeight) {
                    shortestPathWeight = path.getWeight();
                    shortestPath = path;
                    closestExhibit = vertex;
                }
            }


            int i = 1;
            List<IdentifiedWeightedEdge> edgesInPath = shortestPath.getEdgeList();
            String currentStreet = eInfo.get(edgesInPath.get(0).getId()).street;
            String directions = "";
            double currentStreetDist = g.getEdgeWeight(edgesInPath.get(0));
            edgesInPath.remove(0);
            for (IdentifiedWeightedEdge e : edgesInPath) {
                if (!currentStreet.equals(eInfo.get(e.getId()).street)) {
                    directions += i + ". Proceed on "
                            + currentStreet + " "
                            + currentStreetDist + " ft towards "
                            + eInfo.get(e.getId()).street + ".\n";
                    i++;
                    currentStreet = eInfo.get(e.getId()).street;
                    currentStreetDist = 0.0;
                }
                currentStreetDist += g.getEdgeWeight(e);
            }
            directions += i + ". Proceed on "
                    + currentStreet + " "
                    + currentStreetDist + " ft towards "
                    + vInfo.get(closestExhibit).name + ".\n";

            route.add(new RoutePoint(vInfo.get(closestExhibit).name, directions, shortestPathWeight));

            currentNode = closestExhibit;
            unvisited.remove(closestExhibit);
        }

        GraphPath<String, IdentifiedWeightedEdge> backToExit = DijkstraShortestPath.findPathBetween(g, currentNode, "entrance_exit_gate");

        int i = 1;
        List<IdentifiedWeightedEdge> edgesInPath = backToExit.getEdgeList();
        String currentStreet = eInfo.get(edgesInPath.get(0).getId()).street;
        String directions = "";
        double currentStreetDist = g.getEdgeWeight(edgesInPath.get(0));
        edgesInPath.remove(0);
        for (IdentifiedWeightedEdge e : edgesInPath) {
            if (!currentStreet.equals(eInfo.get(e.getId()).street)) {
                directions += i + ". Proceed on "
                        + currentStreet + " "
                        + currentStreetDist + " ft towards "
                        + eInfo.get(e.getId()).street + ".\n";
                i++;
                currentStreet = eInfo.get(e.getId()).street;
                currentStreetDist = 0.0;
            }
            currentStreetDist += g.getEdgeWeight(e);
        }
        directions += i + ". Proceed on "
                + currentStreet + " "
                + currentStreetDist + " ft towards "
                + vInfo.get("entrance_exit_gate").name + ".\n";

        route.add(new RoutePoint(vInfo.get("entrance_exit_gate").name, directions, backToExit.getWeight()));

        return route;
    }
}