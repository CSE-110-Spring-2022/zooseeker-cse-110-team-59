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

/**
 * Class:           LoadingActivity
 * Description:     The activity started when the clicks on the plan button to generate the route
 *
 * Public functions:
 *
 * onCreate   - responsible for creating a new intent and create a new execute object to
 *              run and manage a new thread for this activity
 * generateRoute - Performs Djikstra algorithm to compute the shortest path given a list of exhibits
 *                 and their edge weights. Returns a list of type Routepoint that goes back to the
 *                 entrance and exit gate
 * createRoutePointFromPath - returns a Routepoint object that shows which path and direction to proceed
 *                              and is called in the <code>generateRoute</code> method
 * setGraphData  - loads the graph data from the json files into an empty graph, edge, and vertex respectively
 */
public class LoadingActivity extends AppCompatActivity {

    /**
     * @param savedInstanceState
     */
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

        ArrayList<RoutePoint> route = new ArrayList<>();

        String currentNode = "entrance_exit_gate";

        while (unvisited.size() != 0) {
            double shortestPathWeight = Float.MAX_VALUE;
            GraphPath<String, ZooData.Graph.Edge> shortestPath = null;
            String closestExhibit = null;

            for (String vertex : unvisited) {
                GraphPath<String, ZooData.Graph.Edge> path = DijkstraShortestPath.findPathBetween(ZooData.graphData, currentNode, vertex);
                if (path.getWeight() < shortestPathWeight) {
                    shortestPathWeight = path.getWeight();
                    shortestPath = path;
                    closestExhibit = vertex;
                }
            }

            route.add(createRoutePointFromPath(shortestPath));

            currentNode = closestExhibit;
            unvisited.remove(closestExhibit);
        }

        GraphPath<String, ZooData.Graph.Edge> backToExit = DijkstraShortestPath.findPathBetween(ZooData.graphData, currentNode, "entrance_exit_gate");

        route.add(createRoutePointFromPath(backToExit));

        return route;
    }

    public RoutePoint createRoutePointFromPath(GraphPath<String, ZooData.Graph.Edge> pathToUse) {

        int i = 1;
        List<ZooData.Graph.Edge> edgesInPath = pathToUse.getEdgeList();
        String currentStreet = ZooData.edgeData.get(edgesInPath.get(0).getId()).street;
        String directions = "";
        double currentStreetDist = ZooData.graphData.getEdgeWeight(edgesInPath.get(0));
        edgesInPath.remove(0);
        for (ZooData.Graph.Edge e : edgesInPath) {
            if (!currentStreet.equals(ZooData.edgeData.get(e.getId()).street)) {
                directions += i + ". Proceed on "
                        + currentStreet + " "
                        + currentStreetDist + " ft towards "
                        + ZooData.edgeData.get(e.getId()).street + ".\n";
                i++;
                currentStreet = ZooData.edgeData.get(e.getId()).street;
                currentStreetDist = 0.0;
            }
            currentStreetDist += ZooData.graphData.getEdgeWeight(e);
        }
        directions += i + ". Proceed on "
                + currentStreet + " "
                + currentStreetDist + " ft towards "
                + ZooData.vertexData.get(pathToUse.getEndVertex()).name + ".\n";

        return new RoutePoint(ZooData.vertexData.get(pathToUse.getEndVertex()).name, directions, pathToUse.getWeight());
    }
}