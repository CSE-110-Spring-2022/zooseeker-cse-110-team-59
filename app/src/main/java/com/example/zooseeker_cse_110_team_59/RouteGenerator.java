package com.example.zooseeker_cse_110_team_59;

import androidx.annotation.NonNull;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RouteGenerator {

    //region Overloaded generateRoute methods
    public static ArrayList<RoutePoint> generateRoute(ArrayList<String> enteredExhibits) {
        return generateRoute("entrance_exit_gate", enteredExhibits, "entrance_exit_gate");
    }

    public static ArrayList<RoutePoint> generateRoute(@NonNull String start_id, ArrayList<String> enteredExhibits) {
        return generateRoute(start_id, enteredExhibits, "entrance_exit_gate");
    }

    public static ArrayList<RoutePoint> generateRoute(ArrayList<String> enteredExhibits, @NonNull String end_id) {
        return generateRoute("entrance_exit_gate", enteredExhibits, end_id);
    }
    //endregion

    public static ArrayList<RoutePoint> generateRoute(@NonNull String start_id, ArrayList<String> enteredExhibits, @NonNull String end_id) {
        ArrayList<String> unvisited = enteredExhibits;

        ArrayList<RoutePoint> route = new ArrayList<>();
        double cumDistance = 0.0;
        String currentNode = start_id;

        while (unvisited.size() != 0) {
            String closestExhibit = findClosest(currentNode, unvisited);
            GraphPath<String, ZooData.Graph.Edge> shortestPath = getPathBetween(currentNode, closestExhibit);

            RoutePoint rp = createRoutePointFromPath(shortestPath);
            rp.cumDistance += cumDistance;
            route.add(rp);

            currentNode = closestExhibit;
            unvisited.remove(closestExhibit);
            cumDistance += shortestPath.getWeight();
        }

        GraphPath<String, ZooData.Graph.Edge> toEnd = getPathBetween(currentNode, end_id);

        RoutePoint rp = createRoutePointFromPath(toEnd);
        rp.cumDistance += cumDistance;
        route.add(rp);

        return route;
    }

    public static String findClosest (@NonNull String from, ArrayList<String> to) {
        double shortestPathWeight = Float.MAX_VALUE;
        String closestExhibit = null;

        for (String vertex : to) {
            GraphPath<String, ZooData.Graph.Edge> path = getPathBetween(from, vertex);
            if (path.getWeight() < shortestPathWeight) {
                shortestPathWeight = path.getWeight();
                closestExhibit = vertex;
            }
        }

        return closestExhibit;
    }

    public static RoutePoint createRoutePointFromPath(GraphPath<String, ZooData.Graph.Edge> pathToUse) {
        return new RoutePoint(getDestNameFromPath(pathToUse),
                getStreetNameFromPath(pathToUse),
                getDestIdFromPath(pathToUse),
                getDistanceFromPath(pathToUse));
    }

    public static String getDestIdFromPath(GraphPath<String, ZooData.Graph.Edge> pathToUse) {
        return ZooData.vertexData.get(pathToUse.getEndVertex()).id;
    }

    //region "FromId" getters
    public static String getNameFromId(@NonNull String place_id){
        return ZooData.vertexData.get(place_id).name;
    }

    public static String getParentIdFromId(@NonNull String place_id) {
        // Stub for converting a node ID to its parent ID if it has one, otherwise returning the input ID if it does not
        return place_id;
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

    //region "FromRoute" getters
    public static ArrayList<String> getIdsFromRoute (ArrayList<RoutePoint> route) {
        // Stub for getting the ids of the routepoints in a route in the same order. Once we
        // modify RoutePoint to hold Ids, change exhibitName to id and delete this comment
        return (ArrayList<String>) route.stream().map(item -> item.exhibitName).collect(Collectors.toList());
    }
    //endregion

    //region "Between" getters
    public static GraphPath<String, ZooData.Graph.Edge> getPathBetween(@NonNull String start_id, @NonNull String end_id) {
        return DijkstraShortestPath.findPathBetween(ZooData.graphData, getParentIdFromId(start_id), getParentIdFromId(end_id));
    }

    public static double getDistanceBetween(@NonNull String start_id, @NonNull String end_id) {
        GraphPath<String, ZooData.Graph.Edge> pathToUse = getPathBetween(start_id, end_id);
        return getDistanceFromPath(pathToUse);
    }

    public static String getDirectionsBetween(@NonNull String start_id, @NonNull String end_id) {
        GraphPath<String, ZooData.Graph.Edge> pathToUse = getPathBetween(start_id, end_id);
        return getDirectionsFromPath(pathToUse);
    }
    //endregion
}
