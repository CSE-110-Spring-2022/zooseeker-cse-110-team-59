package com.example.zooseeker_cse_110_team_59.Route;

import android.util.Pair;

import androidx.annotation.NonNull;

import com.example.zooseeker_cse_110_team_59.Data.ZooData;
import com.example.zooseeker_cse_110_team_59.Utilities.ToFeetConvert;

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
        String currentStreetName = "Gate Path";

        while (unvisited.size() != 0) {
            String closestExhibit = findClosestIdToId(currentNode, unvisited);
            GraphPath<String, ZooData.Graph.Edge> shortestPath = getPathBetween(currentNode, closestExhibit);

            RoutePoint rp;
            if (getDistanceBetween(currentNode, closestExhibit) == 0.0) {
                rp = createRoutePointAtDest(closestExhibit, currentStreetName);
            } else {
                rp = createRoutePointFromPath(shortestPath, closestExhibit);
            }
            rp.cumDistance += cumDistance;
            route.add(rp);

            currentNode = rp.ID;
            currentStreetName = rp.streetName;
            unvisited.remove(closestExhibit);
            cumDistance += shortestPath.getWeight();
        }

        GraphPath<String, ZooData.Graph.Edge> toEnd = getPathBetween(currentNode, end_id);

        RoutePoint rp = createRoutePointFromPath(toEnd);
        rp.cumDistance += cumDistance;
        route.add(rp);

        return route;
    }

    //region "findClosest" Methods
    public static String findClosestIdToId(@NonNull String from) {
        ArrayList<String> to = new ArrayList<>();
        to.addAll(ZooData.vertexData.keySet());
        return findClosestIdToId(from, to);
    }

    public static String findClosestIdToId(@NonNull String from, ArrayList<String> to) {
        double shortestPathWeight = Double.MAX_VALUE;
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
    public static String findClosestIdToCoords(@NonNull Pair<Double, Double> from) {
        ArrayList<String> to = new ArrayList<>();
        to.addAll(ZooData.vertexData.keySet());
        return findClosestIdToCoords(from, to);
    }

    public static String findClosestIdToCoords(@NonNull Pair<Double, Double> from, ArrayList<String> to) {
        double shortestDistance = Double.MAX_VALUE;
        String closestExhibit = null;

        for (String vertex : to) {
            var vertexInfo = ZooData.vertexData.get(getParentIdFromId(vertex));
            double currDistance = Math.sqrt(Math.pow(ToFeetConvert.FromLat(Math.abs(from.first)) - ToFeetConvert.FromLat(Math.abs(vertexInfo.lat)), 2)
                                            + Math.pow(ToFeetConvert.FromLng(Math.abs(from.second)) - ToFeetConvert.FromLng(Math.abs(vertexInfo.lng)), 2));
            if (currDistance < shortestDistance) {
                shortestDistance = currDistance;
                closestExhibit = vertex;
            }
        }

        return closestExhibit;
    }
    //endregion

    public static RoutePoint createRoutePointFromPath(GraphPath<String, ZooData.Graph.Edge> pathToUse) {
        return createRoutePointFromPath(pathToUse, getDestIdFromPath(pathToUse));
    }
    public static RoutePoint createRoutePointFromPath(GraphPath<String, ZooData.Graph.Edge> pathToUse, String destID) {
        return new RoutePoint(getNameFromId(destID),
                getStreetNameFromPath(pathToUse),
                destID,
                getDistanceFromPath(pathToUse));
    }

    public static RoutePoint createRoutePointAtDest(String destID, String streetName) {
        return new RoutePoint(getNameFromId(destID),
                streetName,
                destID,
                0);
    }
    //endregion

    //region "FromId" getters
    public static String getNameFromId(@NonNull String place_id){
        return ZooData.vertexData.get(place_id).name;
    }

    public static String getParentIdFromId(@NonNull String place_id) {
        return (ZooData.vertexData.get(place_id).hasGroup()) ? (ZooData.vertexData.get(place_id).group_id) : place_id;
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

    public static String getDestIdFromPath(GraphPath<String, ZooData.Graph.Edge> pathToUse) {
        return ZooData.vertexData.get(pathToUse.getEndVertex()).id;
    }

    public static String getStreetNameFromPath(GraphPath<String, ZooData.Graph.Edge> pathToUse) {
        List<ZooData.Graph.Edge> edgesInPath = pathToUse.getEdgeList();
        return ZooData.edgeData.get(edgesInPath.get(edgesInPath.size() - 1).getId()).street;
    }
    //endregion

    //region "FromRoute" getters
    public static ArrayList<String> getIdsFromRoute (ArrayList<RoutePoint> route) {
        return (ArrayList<String>) route.stream().map(item -> item.ID).collect(Collectors.toList());
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
