package com.example.zooseeker_cse_110_team_59;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;

import java.util.ArrayList;
import java.util.List;

public class RouteGenerator {

    public static RoutePoint createRoutePointFromPath(GraphPath<String, ZooData.Graph.Edge> pathToUse) {

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

        return new RoutePoint(ZooData.vertexData.get(pathToUse.getEndVertex()).name, directions, pathToUse.getWeight(),currentStreet);
    }

    public static ArrayList<RoutePoint> generateRoute(ArrayList<String> enteredExhibits) {
        ArrayList<String> unvisited = enteredExhibits;

        ArrayList<RoutePoint> route = new ArrayList<>();
        double cumdistance = 0.0;
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
            RoutePoint rp = createRoutePointFromPath(shortestPath);

            rp.cumDistance += cumdistance;
            route.add(rp);

            currentNode = closestExhibit;
            unvisited.remove(closestExhibit);
            cumdistance += shortestPathWeight;
        }

        GraphPath<String, ZooData.Graph.Edge> backToExit = DijkstraShortestPath.findPathBetween(ZooData.graphData, currentNode, "entrance_exit_gate");

        RoutePoint rp = createRoutePointFromPath(backToExit);

        rp.cumDistance += cumdistance;
        route.add(rp);
        return route;
    }
}
