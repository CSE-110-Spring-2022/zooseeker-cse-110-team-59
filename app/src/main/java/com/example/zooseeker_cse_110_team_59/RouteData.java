package com.example.zooseeker_cse_110_team_59;

import java.util.ArrayList;

public class RouteData {
    private ArrayList<RoutePoint> route;

    public RouteData() {
        route = new ArrayList<RoutePoint>();
    }

    public void addRoutePoint(String nodeName, float nextEdgeWeight, String nextEdgeID, String nextNodeName) {
        route.add(new RoutePoint(nodeName, nextEdgeWeight, nextEdgeID, nextNodeName));
    }

    public String printRoutePoint(int point) {
        return String.format("  %d. Walk %.0f meters along %s from '%s' to '%s'.",
                point + 1,
                route.get(point).nextEdgeWeight,
                route.get(point).nextEdgeID,
                route.get(point).nodeName,
                route.get(point).nextNodeName);
    }

    public int getRouteSize() {
        return route.size();
    }
}
