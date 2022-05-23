package com.example.zooseeker_cse_110_team_59;

import androidx.annotation.NonNull;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;

import java.util.ArrayList;
import java.util.List;

public class RouteGenerator {

    public static ArrayList<RoutePoint> generateRoute(ArrayList<String> enteredExhibits) {
        return generateRoute("entrance_exit_gate", enteredExhibits, "entrance_exit_gate");
    }

    public static ArrayList<RoutePoint> generateRoute(@NonNull String start_id, ArrayList<String> enteredExhibits, @NonNull String end_id) {
        ArrayList<String> unvisited = enteredExhibits;

        ArrayList<RoutePoint> route = new ArrayList<>();
        double cumDistance = 0.0;
        String currentNode = start_id;

        while (unvisited.size() != 0) {
            String closestExhibit = findClosest(currentNode, unvisited);
            GraphPath<String, ZooData.Graph.Edge> shortestPath = DijkstraShortestPath.findPathBetween(ZooData.graphData, currentNode, closestExhibit);

            RoutePoint rp = createRoutePointFromPath(shortestPath);
            rp.cumDistance += cumDistance;
            route.add(rp);

            currentNode = closestExhibit;
            unvisited.remove(closestExhibit);
            cumDistance += shortestPath.getWeight();
        }

        GraphPath<String, ZooData.Graph.Edge> toEnd = DijkstraShortestPath.findPathBetween(ZooData.graphData, currentNode, end_id);

        RoutePoint rp = createRoutePointFromPath(toEnd);
        rp.cumDistance += cumDistance;
        route.add(rp);

        return route;
    }

    public static String findClosest (@NonNull String from, ArrayList<String> to) {
        double shortestPathWeight = Float.MAX_VALUE;
        String closestExhibit = null;

        for (String vertex : to) {
            GraphPath<String, ZooData.Graph.Edge> path = DijkstraShortestPath.findPathBetween(ZooData.graphData, from, vertex);
            if (path.getWeight() < shortestPathWeight) {
                shortestPathWeight = path.getWeight();
                closestExhibit = vertex;
            }
        }

        return closestExhibit;
    }

    public static RoutePoint createRoutePointFromPath(GraphPath<String, ZooData.Graph.Edge> pathToUse) {
        return new RoutePoint(getDestNameFromPath(pathToUse),
                getDirectionsFromPath(pathToUse),
                getStreetNameFromPath(pathToUse),
                getDistanceFromPath(pathToUse));
    }

    //region "FromId" getters
    public static String getNameFromId(@NonNull String place_id){
        return ZooData.vertexData.get(place_id).name;
    }
    //endregion

    //region "FromPath" getters
    public static double getDistanceFromPath(GraphPath<String, ZooData.Graph.Edge> pathToUse) {
        return pathToUse.getWeight();
    }

    public static String getDirectionsFromPath(GraphPath<String, ZooData.Graph.Edge> pathToUse) {
        int i = 1;
        List<ZooData.Graph.Edge> edgesInPath = pathToUse.getEdgeList();
        String currentStreet = ZooData.edgeData.get(edgesInPath.get(0).getId()).street;
        String directions = "";
        double currentStreetDist = 0.0;
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

        return directions;
    }

    public static String getDestNameFromPath(GraphPath<String, ZooData.Graph.Edge> pathToUse) {
        return getNameFromId(pathToUse.getEndVertex());
    }

    public static String getStreetNameFromPath(GraphPath<String, ZooData.Graph.Edge> pathToUse) {
        List<ZooData.Graph.Edge> edgesInPath = pathToUse.getEdgeList();
        return ZooData.edgeData.get(edgesInPath.get(edgesInPath.size() - 1).getId()).street;
    }
    //endregion

    //region "Between" getters
    public static double getDistanceBetween(@NonNull String start_id, @NonNull String end_id) {
        GraphPath<String, ZooData.Graph.Edge> pathToUse = DijkstraShortestPath.findPathBetween(ZooData.graphData, start_id, end_id);
        return getDistanceFromPath(pathToUse);
    }

    public static String getDirectionsBetween(@NonNull String start_id, @NonNull String end_id) {
        GraphPath<String, ZooData.Graph.Edge> pathToUse = DijkstraShortestPath.findPathBetween(ZooData.graphData, start_id, end_id);
        return getDirectionsFromPath(pathToUse);
    }
    //endregion
}
