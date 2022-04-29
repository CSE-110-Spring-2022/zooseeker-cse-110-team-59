package com.example.zooseeker_cse_110_team_59;

public class RoutePoint {
    public String nodeName;
    public float nextEdgeWeight;
    public String nextEdgeID;
    public String nextNodeName;

    public RoutePoint(String nodeName, float nextEdgeWeight, String nextEdgeID, String nextNodeName) {
        this.nodeName = nodeName;
        this.nextEdgeWeight = nextEdgeWeight;
        this.nextEdgeID = nextEdgeID;
        this.nextNodeName = nextNodeName;
    }
}
